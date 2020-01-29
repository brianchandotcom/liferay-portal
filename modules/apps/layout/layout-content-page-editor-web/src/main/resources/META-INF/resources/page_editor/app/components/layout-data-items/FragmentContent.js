/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useIsMounted} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import React, {useContext, useEffect, useState, useRef} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {ConfigContext} from '../../config/index';
import Processors from '../../processors/index';
import selectEditableValue from '../../selectors/selectEditableValue';
import selectEditableValueConfig from '../../selectors/selectEditableValueConfig';
import selectEditableValueContent from '../../selectors/selectEditableValueContent';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import InfoItemService from '../../services/InfoItemService';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import {useSelectItem} from '../Controls';
import UnsafeHTML from '../UnsafeHTML';
import {showFloatingToolbar} from '../showFloatingToolbar';

const editableIsMapped = editableValue =>
	(editableValue.classNameId &&
		editableValue.classPK &&
		editableValue.fieldId) ||
	editableValue.mappedField;

const resolveEditableValue = (
	state,
	config,
	fragmentEntryLinkId,
	editableId,
	processorType
) => {
	const editableValue = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processorType
	);

	if (editableIsMapped(editableValue)) {
		return InfoItemService.getAssetFieldValue({
			classNameId: editableValue.classNameId,
			classPK: editableValue.classPK,
			config,
			fieldId: editableValue.fieldId,
			onNetworkStatus: () => {}
		}).then(response => {
			const {fieldValue} = response;

			return [fieldValue, editableValue.config];
		});
	}

	return new Promise(resolve => {
		resolve([
			selectEditableValueContent(
				state,
				config,
				fragmentEntryLinkId,
				editableId,
				processorType
			),
			selectEditableValueConfig(
				state,
				fragmentEntryLinkId,
				editableId,
				processorType
			)
		]);
	});
};

function FragmentContent({fragmentEntryLink}, ref) {
	const config = useContext(ConfigContext);
	const defaultContent = fragmentEntryLink.content.value.content;
	const {fragmentEntryLinkId} = fragmentEntryLink;
	const isMounted = useIsMounted();
	const state = useSelector(state => state);
	const dispatch = useDispatch();

	const selectItem = useSelectItem();
	const activeEditable = useRef(null);

	const [content, setContent] = useState(defaultContent);

	const [hasEditableActive, setHasEditableActive] = useState(false);

	useEffect(() => {
		let element = document.createElement('div');
		element.innerHTML = defaultContent;

		const updateContent = debounce(() => {
			if (isMounted() && element) {
				setContent(element.innerHTML);
			}
		}, 50);

		Array.from(
			element.querySelectorAll('[data-lfr-background-image-id]')
		).map(editable => {
			resolveEditableValue(
				state,
				config,
				fragmentEntryLinkId,
				editable.dataset.lfrBackgroundImageId,
				BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			).then(([value, _editableConfig]) => {
				const processor = Processors['background-image'];

				processor.render(editable, value);
			});
		});

		Array.from(element.querySelectorAll('lfr-editable')).forEach(
			editable => {
				editable.classList.add('page-editor__editable');

				resolveEditableValue(
					state,
					config,
					fragmentEntryLinkId,
					editable.getAttribute('id'),
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				).then(([value, editableConfig]) => {
					if (element) {
						const processor =
							Processors[editable.getAttribute('type')] ||
							Processors.fallback;

						processor.render(editable, value, editableConfig);
					}
				});
			}
		);

		updateContent();

		return () => {
			element = null;
		};
	}, [state, config, defaultContent, fragmentEntryLinkId, isMounted]);

	const onDoubleClick = event => {
		const editable = event.target.closest('lfr-editable');
		const backgroundImageEditable = event.target.closest(
			'[data-lfr-background-image-id]'
		);

		if (editable) {
			const editableId = `${fragmentEntryLinkId}-${editable.getAttribute(
				'id'
			)}`;

			if (activeEditable.current) {
				destroyProcessor(
					activeEditable.current,
					activeEditable.current.getAttribute('type')
				);
			}

			activeEditable.current = editable;

			initProcessor({
				config,
				editableConfig: selectEditableValueConfig(
					state,
					fragmentEntryLinkId,
					editable.getAttribute('id'),
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				),
				editableType: editable.getAttribute('type'),
				element: editable,
				processorType: EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			});

			selectItem(editableId);
			setHasEditableActive(true);
		} else if (backgroundImageEditable) {
			if (activeEditable.current) {
				destroyProcessor(activeEditable.current, 'background-image');
			}

			activeEditable.current = backgroundImageEditable;

			initProcessor({
				config,
				editableConfig: selectEditableValueConfig(
					state,
					fragmentEntryLinkId,
					backgroundImageEditable.dataset.lfrBackgroundImageId,
					BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
				),
				editableType: 'background-image',
				element: backgroundImageEditable,
				processorType: BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			});

			selectItem(backgroundImageEditable.dataset.lfrBackgroundImageId);

			setHasEditableActive(true);
		} else {
			if (activeEditable.current) {
				destroyProcessor(
					activeEditable.current,
					activeEditable.current.getAttribute('type')
				);
				activeEditable.current = null;

				setHasEditableActive(false);

				selectItem(null);
			}
		}
	};

	const initProcessor = ({
		config,
		editableConfig,
		editableType,
		element,
		processorType
	}) => {
		const processor = Processors[editableType] || Processors.fallback;

		const id =
			processorType === EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				? element.id
				: element.dataset.lfrBackgroundImageId;

		processor.createEditor(
			element,
			value => {
				processor.render(element, value, editableConfig);

				const {editableValues} = fragmentEntryLink;
				const editableValue = editableValues[processorType][id];
				const prefixedSegmentsExperienceId = selectPrefixedSegmentsExperienceId(
					state
				);

				if (state.segmentsExperienceId) {
					editableValue[prefixedSegmentsExperienceId] = {
						...editableValue[prefixedSegmentsExperienceId],
						[state.languageId]: value
					};
				} else {
					editableValue[state.languageId] = value;
				}

				dispatch(
					updateEditableValues({
						config,
						editableValues,
						fragmentEntryLinkId,
						segmentsExperienceId: state.segmentsExperienceId
					})
				);
			},
			() => processor.destroyEditor(element, editableConfig),
			config
		);
	};

	const destroyProcessor = (element, editableType) => {
		const processor = Processors[editableType] || Processors.fallback;

		processor.destroyEditor(element);
	};

	return (
		<>
			{hasEditableActive &&
				showFloatingToolbar(activeEditable, fragmentEntryLinkId)}
			<UnsafeHTML
				markup={content}
				onDoubleClick={onDoubleClick}
				ref={ref}
			/>
		</>
	);
}

export default React.forwardRef(FragmentContent);
