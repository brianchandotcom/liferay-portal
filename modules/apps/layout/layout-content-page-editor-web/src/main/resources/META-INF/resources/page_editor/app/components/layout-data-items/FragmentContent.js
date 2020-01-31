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
	useRef
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
import {
	useSelectItem,
	useHoverItem,
	useIsActive,
	useActiveItemId
} from '../Controls';
import FloatingToolbar from '../FloatingToolbar';
import UnsafeHTML from '../UnsafeHTML';
import EditableDecoration from './EditableDecoration';

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
	} else {
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
	} else {
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

function FragmentContent({fragmentEntryLink, itemId}, ref) {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const hoverItem = useHoverItem();
	const activeItemId = useActiveItemId();
	const isActive = useIsActive();
	const isMounted = useIsMounted();
	const selectItem = useSelectItem();
	const state = useSelector(state => state);

	const defaultContent = fragmentEntryLink.content.value.content;
	const {fragmentEntryLinkId} = fragmentEntryLink;

	const [content, setContent] = useState(defaultContent);
	const [editablesIds, setEditablesIds] = useState([]);
	const [editing, setEditing] = useState(false);

	const getEditableId = editableUniqueId => {
		const [, ...editableId] = editableUniqueId.split('-');

		return editableId.join('-');
	};

	const getEditableUniqueId = editableId =>
		`${fragmentEntryLinkId}-${editableId}`;

	const siblingEditableIsActive = id =>
		editablesIds
			.filter(editableId => editableId !== id)
			.some(siblingId => isActive(getEditableUniqueId(siblingId)));

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

	const onClick = event => {
		const editableElement = closest(event.target, 'lfr-editable');

		if (editableElement) {
			if (isActive(getEditableUniqueId(editableElement.id))) {
				event.stopPropagation();

				initProcessor({
					config,
					editableConfig: selectEditableValueConfig(
						state,
						fragmentEntryLinkId,
						editableElement.id,
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					),
					editableType: editableElement.getAttribute('type'),
					element: editableElement,
					processorType: EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				});
			}

			if (
				isActive(itemId) ||
				siblingEditableIsActive(editableElement.id)
			) {
				event.stopPropagation();

				selectItem(getEditableUniqueId(editableElement.id));
			}
		}
	};

	const onMouseOver = event => {
		const editableElement = closest(event.target, 'lfr-editable');

		if (editableElement) {
			if (
				(isActive(itemId) ||
					siblingEditableIsActive(editableElement.id)) &&
				editableElement
			) {
				event.stopPropagation();

				hoverItem(getEditableUniqueId(editableElement.id));
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
		setEditing(true);

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
		setEditing(false);

		const processor = Processors[editableType] || Processors.fallback;

		processor.destroyEditor(element);
	};

	return (
		<>
			<UnsafeHTML
				className="page-editor__fragment"
				markup={content}
				onClick={onClick}
				onMouseOver={onMouseOver}
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

			{!editing &&
				(isActive(itemId) ||
					editablesIds.some(editableId =>
						isActive(getEditableUniqueId(editableId))
					)) &&
				editablesIds.map(editableId => (
					<EditableDecoration
						editableId={editableId}
						editableUniqueId={getEditableUniqueId(editableId)}
						key={getEditableUniqueId(editableId)}
						parentItemId={itemId}
						parentRef={ref}
						siblingsIds={editablesIds
							.filter(siblingId => siblingId !== editableId)
							.map(siblingId => getEditableUniqueId(siblingId))}
					></EditableDecoration>
				))}
		</>
	);
}

export default React.forwardRef(FragmentContent);
