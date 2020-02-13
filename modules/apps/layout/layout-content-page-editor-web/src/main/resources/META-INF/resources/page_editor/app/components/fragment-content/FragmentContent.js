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
import {closest} from 'metal-dom';
import React, {
	useCallback,
	useContext,
	useEffect,
	useLayoutEffect,
	useState
} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {ConfigContext} from '../../config/index';
import Processors from '../../processors/index';
import selectEditableValue from '../../selectors/selectEditableValue';
import selectEditableValueConfig from '../../selectors/selectEditableValueConfig';
import selectEditableValueContent from '../../selectors/selectEditableValueContent';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import InfoItemService from '../../services/InfoItemService';
import {useSelector} from '../../store/index';
import {useActiveItemId, useIsActive} from '../Controls';
import UnsafeHTML from '../UnsafeHTML';
import {useSetEditableProcessorUniqueId} from './EditableProcessorContext';
import FragmentContentDecoration from './FragmentContentDecoration';
import FragmentContentFloatingToolbar from './FragmentContentFloatingToolbar';
import FragmentContentProcessor from './FragmentContentProcessor';
import getEditableUniqueId from './getEditableUniqueId';

function FragmentContent({fragmentEntryLink, itemId}, ref) {
	const config = useContext(ConfigContext);
	const element = ref.current;
	const activeItemId = useActiveItemId();
	const isActive = useIsActive();
	const isMounted = useIsMounted();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const state = useSelector(state => state);

	const defaultContent = fragmentEntryLink.content;
	const {defaultLanguageId} = config;
	const {fragmentEntryLinkId} = fragmentEntryLink;
	const prefixedSegmentsExperienceId = selectPrefixedSegmentsExperienceId(
		state
	);

	const [content, setContent] = useState(defaultContent);
	const [editablesIds, setEditablesIds] = useState([]);

	const canUpdateLayoutContent = useSelector(
		({permissions}) =>
			!permissions.LOCKED_SEGMENTS_EXPERIMENT &&
			permissions.UPDATE_LAYOUT_CONTENT
	);

	const editableValues = useSelector(state => {
		const values = {};

		if (fragmentEntryLink) {
			Object.keys(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				]
			).forEach(editableId => {
				const editableValue = selectEditableValue(
					state,
					fragmentEntryLinkId,
					editableId
				);

				values[editableId] = editableValue;
			});
		}

		return values;
	});

	const showFragmentContentDecoration = useCallback(
		editableValue =>
			canUpdateLayoutContent
				? [
						itemId,
						...editablesIds.map(editableId =>
							getEditableUniqueId(fragmentEntryLinkId, editableId)
						)
				  ].some(isActive) ||
				  editableIsMapped(editableValue) ||
				  editableIsTranslated(
						defaultLanguageId,
						editableValue,
						state.languageId,
						prefixedSegmentsExperienceId
				  )
				: true,
		[
			canUpdateLayoutContent,
			defaultLanguageId,
			isActive,
			itemId,
			prefixedSegmentsExperienceId,
			state.languageId,
			editablesIds,
			fragmentEntryLinkId
		]
	);

	useLayoutEffect(() => {
		setEditablesIds(
			element
				? Array.from(element.querySelectorAll('lfr-editable')).map(
						element => element.id
				  )
				: []
		);
	}, [content, element]);

	useEffect(() => {
		if (!element) {
			return;
		}

		const handleFragmentEntryLinkContentClick = event => {
			const closestElement = closest(event.target, '[href]');

			if (
				closestElement &&
				!('data-lfr-page-editor-href-enabled' in element.dataset)
			) {
				event.preventDefault();
			}
		};

		element.addEventListener(
			'click',
			handleFragmentEntryLinkContentClick,
			true
		);

		return () => {
			element.removeEventListener(
				'click',
				handleFragmentEntryLinkContentClick,
				true
			);
		};
	});

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

	const onFloatingToolbarButtonClick = buttonId => {
		if (buttonId === EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.id) {
			setEditableProcessorUniqueId(activeItemId);
		}
	};

	return (
		<>
			<UnsafeHTML
				className="page-editor__fragment"
				contentRef={ref}
				markup={content}
			/>

			<FragmentContentFloatingToolbar
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
				onButtonClick={onFloatingToolbarButtonClick}
			/>

			<FragmentContentProcessor
				element={element}
				fragmentEntryLinkId={fragmentEntryLinkId}
			/>

			{editablesIds.map(
				editableId =>
					showFragmentContentDecoration(
						editableValues[editableId]
					) && (
						<FragmentContentDecoration
							editableId={editableId}
							fragmentEntryLinkId={fragmentEntryLinkId}
							itemId={getEditableUniqueId(
								fragmentEntryLinkId,
								editableId
							)}
							key={editableId}
							onEditableDoubleClick={() =>
								setEditableProcessorUniqueId(
									getEditableUniqueId(
										fragmentEntryLinkId,
										editableId
									)
								)
							}
							parentItemId={itemId}
							parentRef={ref}
							siblingsItemIds={editablesIds.map(siblingId =>
								getEditableUniqueId(
									fragmentEntryLinkId,
									siblingId
								)
							)}
						/>
					)
			)}
		</>
	);
}

export default React.forwardRef(FragmentContent);

const editableIsMappedToInfoItem = editableValue =>
	editableValue &&
	editableValue.classNameId &&
	editableValue.classPK &&
	editableValue.fieldId;

const editableIsMapped = editableValue =>
	editableIsMappedToInfoItem(editableValue) || editableValue.mappedField;

const editableIsTranslated = (
	defaultLanguageId,
	editableValue,
	languageId,
	segmentsExperienceId
) =>
	editableValue &&
	defaultLanguageId !== languageId &&
	(editableValue[languageId] ||
		(segmentsExperienceId in editableValue &&
			editableValue[segmentsExperienceId][languageId]));

const getMappingValue = ({classNameId, classPK, config, fieldId}) =>
	InfoItemService.getAssetFieldValue({
		classNameId,
		classPK,
		config,
		fieldId,
		onNetworkStatus: () => {}
	}).then(response => {
		const {fieldValue = ''} = response;

		return fieldValue;
	});

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

	let valuePromise;

	if (editableIsMappedToInfoItem(editableValue)) {
		valuePromise = getMappingValue({
			classNameId: editableValue.classNameId,
			classPK: editableValue.classPK,
			config,
			fieldId: editableValue.fieldId
		});
	}
	else {
		valuePromise = Promise.resolve(
			selectEditableValueContent(
				state,
				config,
				fragmentEntryLinkId,
				editableId,
				processorType
			)
		);
	}

	let configPromise;

	if (editableIsMappedToInfoItem(editableValue.config)) {
		configPromise = getMappingValue({
			classNameId: editableValue.config.classNameId,
			classPK: editableValue.config.classPK,
			config,
			fieldId: editableValue.config.fieldId
		}).then(href => {
			return {...editableValue.config, href};
		});
	}
	else {
		configPromise = Promise.resolve(
			selectEditableValueConfig(
				state,
				fragmentEntryLinkId,
				editableId,
				processorType
			)
		);
	}

	return Promise.all([valuePromise, configPromise]);
};
