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
	useContext,
	useEffect,
	useLayoutEffect,
	useState,
	useRef,
	useCallback,
	useMemo
} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {ConfigContext} from '../../config/index';
import Processors from '../../processors/index';
import selectEditableValue from '../../selectors/selectEditableValue';
import selectEditableValueConfig from '../../selectors/selectEditableValueConfig';
import selectEditableValueContent from '../../selectors/selectEditableValueContent';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import InfoItemService from '../../services/InfoItemService';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import {useIsActive, useActiveItemId} from '../Controls';
import FloatingToolbar from '../FloatingToolbar';
import UnsafeHTML from '../UnsafeHTML';
import EditableDecoration from './EditableDecoration';

function FragmentContent({fragmentEntryLink, itemId}, ref) {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const activeItemId = useActiveItemId();
	const isActive = useIsActive();
	const isMounted = useIsMounted();
	const state = useSelector(state => state);

	const defaultContent = fragmentEntryLink.content.value.content;
	const {fragmentEntryLinkId} = fragmentEntryLink;

	const [content, setContent] = useState(defaultContent);
	const [editablesIds, setEditablesIds] = useState([]);

	const getEditableId = editableUniqueId => {
		const [, ...editableId] = editableUniqueId.split('-');

		return editableId.join('-');
	};

	const getEditableUniqueId = useCallback(
		editableId => `${fragmentEntryLinkId}-${editableId}`,
		[fragmentEntryLinkId]
	);

	const canUpdateLayoutContent = useSelector(
		({permissions}) =>
			!permissions.LOCKED_SEGMENTS_EXPERIMENT &&
			permissions.UPDATE_LAYOUT_CONTENT
	);

	const showEditableDecoration = useMemo(
		() =>
			canUpdateLayoutContent
				? [itemId, ...editablesIds.map(getEditableUniqueId)].some(
						isActive
				  )
				: true,
		[
			canUpdateLayoutContent,
			editablesIds,
			getEditableUniqueId,
			itemId,
			isActive
		]
	);

	useLayoutEffect(() => {
		setEditablesIds(
			Array.from(ref.current.querySelectorAll('lfr-editable')).map(
				element => element.id
			)
		);
	}, [content, ref]);

	useEffect(() => {
		const activeEditable = ref.current.querySelector(
			`[id="${getEditableId(activeItemId || '')}"]`
		);

		if (activeEditable) {
			destroyProcessor(
				activeEditable,
				activeEditable.getAttribute('type')
			);
		}
	}, [activeItemId, ref]);

	ref = useRef(null);

	useEffect(() => {
		const element = ref.current;

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

		element.addEventListener('click', handleFragmentEntryLinkContentClick);

		return () => {
			element.removeEventListener(
				'click',
				handleFragmentEntryLinkContentClick
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

	const initProcessor = editableElement => {
		const editableId = editableElement.id;
		const editableType = editableElement.getAttribute('type');
		const processor = Processors[editableType] || Processors.fallback;
		const processorType = EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

		const editableConfig = selectEditableValueConfig(
			state,
			fragmentEntryLinkId,
			editableId,
			processorType
		);

		processor.createEditor(
			editableElement,
			value => {
				processor.render(editableElement, value, editableConfig);

				const {editableValues} = fragmentEntryLink;
				const editableValue =
					editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
						editableId
					];
				const prefixedSegmentsExperienceId = selectPrefixedSegmentsExperienceId(
					state
				);

				if (state.segmentsExperienceId) {
					editableValue[prefixedSegmentsExperienceId] = {
						...editableValue[prefixedSegmentsExperienceId],
						[state.languageId]: value
					};
				}
				else {
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
			() => processor.destroyEditor(editableElement, editableConfig),
			config
		);
	};

	const destroyProcessor = (element, editableType) => {
		const processor = Processors[editableType] || Processors.fallback;

		processor.destroyEditor(element);
	};

	return (
		<>
			<UnsafeHTML
				className="page-editor__fragment"
				markup={content}
				ref={ref}
			/>

			{editablesIds
				.filter(editableId => isActive(getEditableUniqueId(editableId)))
				.map(editableId => {
					const editableElement = ref.current.querySelector(
						`[id="${editableId}"]`
					);
					const editableRef = React.createRef();
					const editableType = editableElement.getAttribute('type');

					editableRef.current = editableElement;

					const showLinkButton =
						editableType == EDITABLE_TYPES.text ||
						editableType == EDITABLE_TYPES.image ||
						editableType == EDITABLE_TYPES.link;

					const buttons = [
						{icon: 'pencil', panelId: 'panel'},
						EDITABLE_FLOATING_TOOLBAR_BUTTONS.map
					];

					if (showLinkButton) {
						buttons.push(EDITABLE_FLOATING_TOOLBAR_BUTTONS.link);
					}

					return (
						<FloatingToolbar
							buttons={buttons}
							item={{
								editableId,
								editableType,
								fragmentEntryLinkId,
								itemId: getEditableUniqueId(editableId)
							}}
							itemRef={editableRef}
							key={getEditableUniqueId(editableId)}
						/>
					);
				})}

			{showEditableDecoration &&
				editablesIds.map(editableId => (
					<EditableDecoration
						editableId={editableId}
						fragmentEntryLinkId={fragmentEntryLinkId}
						itemId={getEditableUniqueId(editableId)}
						key={editableId}
						onEditableDoubleClick={initProcessor}
						parentItemId={itemId}
						parentRef={ref}
						siblingsItemIds={editablesIds.map(siblingId =>
							getEditableUniqueId(siblingId)
						)}
					/>
				))}
		</>
	);
}

export default React.forwardRef(FragmentContent);

const editableIsMappedToInfoItem = editableValue =>
	editableValue &&
	editableValue.classNameId &&
	editableValue.classPK &&
	editableValue.fieldId;

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
