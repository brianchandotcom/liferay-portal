/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useEffect, useMemo} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {TEXT_EDITABLE_TYPES} from '../../config/constants/textEditableTypes';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {config} from '../../config/index';
import {useToControlsId} from '../../contexts/CollectionItemContext';
import {
	useActivationOrigin,
	useActiveItemType,
	useHoverItem,
	useHoveredItemId,
	useHoveredItemType,
	useHoveringOrigin,
	useIsActive,
	useIsHovered,
	useSelectItem,
} from '../../contexts/ControlsContext';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from '../../contexts/EditableProcessorContext';
import {useSelector, useSelectorCallback} from '../../contexts/StoreContext';
import selectCanUpdateEditables from '../../selectors/selectCanUpdateEditables';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectLanguageId from '../../selectors/selectLanguageId';
import canActivateEditable from '../../utils/canActivateEditable';
import {deepEqual} from '../../utils/checkDeepEqual';
import isMapped from '../../utils/editable_value/isMapped';
import getEditableId from '../../utils/getEditableId';
import {fromControlsId} from '../layout_data_items/Collection';
import {getEditableElement} from './getEditableElement';

const EDITABLE_CLASS_NAMES = {
	active: 'page-editor__editable--active',
	contentHovered: 'page-editor__editable--content-hovered',
	hovered: 'page-editor__editable--hovered',
	mapped: 'page-editor__editable--mapped',
	translated: 'page-editor__editable--translated',
};

const isTranslated = (defaultLanguageId, languageId, editableValue) =>
	defaultLanguageId !== languageId && editableValue?.[languageId];

function FragmentContentInteractionsFilter({
	children,
	editables,
	fragmentEntryLinkId,
	itemId,
}) {
	const activationOrigin = useActivationOrigin();
	const activeItemType = useActiveItemType();
	const canUpdateEditables = useSelector(selectCanUpdateEditables);
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const hoveredItemId = useHoveredItemId();
	const hoveredItemType = useHoveredItemType();
	const hoveringOrigin = useHoveringOrigin();
	const hoverItem = useHoverItem();
	const isActive = useIsActive();
	const isHovered = useIsHovered();
	const languageId = useSelector(selectLanguageId);
	const selectItem = useSelectItem();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const toControlsId = useToControlsId();

	const editableValues = useSelectorCallback(
		(state) => {
			const fragmentEntryLink =
				state.fragmentEntryLinks[fragmentEntryLinkId];

			return fragmentEntryLink
				? {
						...fragmentEntryLink.editableValues[
							EDITABLE_FRAGMENT_ENTRY_PROCESSOR
						],
						...fragmentEntryLink.editableValues[
							BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
						],
				  }
				: {};
		},
		[fragmentEntryLinkId],
		deepEqual
	);

	const siblingIds = useMemo(
		() => [itemId, ...editables.map((editable) => editable.itemId)],
		[itemId, editables]
	);

	useEffect(() => {
		editables.forEach((editable) => {
			if (editableValues) {
				const editableValue = editableValues[editable.editableId];
				const {element} = editable;

				if (isMapped(editableValue)) {
					element.classList.add(EDITABLE_CLASS_NAMES.mapped);
				}
				else if (
					isTranslated(
						config.defaultLanguageId,
						languageId,
						editableValue
					)
				) {
					element.classList.add(EDITABLE_CLASS_NAMES.translated);
				}
				else {
					element.classList.remove(EDITABLE_CLASS_NAMES.mapped);
					element.classList.remove(EDITABLE_CLASS_NAMES.translated);
				}
			}
		});
	}, [editables, editableValues, languageId]);

	useEffect(() => {
		editables.forEach((editable) => {
			const isBeingEdited =
				editable.itemId === fromControlsId(editableProcessorUniqueId);

			if (isActive(editable.itemId)) {
				editable.element.classList.add(EDITABLE_CLASS_NAMES.active);

				if (isBeingEdited) {
					editable.element.removeAttribute('title');
				}
				else if (TEXT_EDITABLE_TYPES.has(editable.type)) {
					editable.element.setAttribute(
						'title',
						Liferay.Language.get('edit-text')
					);
				}
			}
			else {
				editable.element.classList.remove(EDITABLE_CLASS_NAMES.active);
				editable.element.removeAttribute('title');
			}
		});
	}, [editables, isActive, editableProcessorUniqueId]);

	useEffect(() => {
		editables.forEach((editable) => {
			if (editableValues) {
				const editableValue = editableValues[editable.editableId] || {};

				const localizedEditableValue = editableValue[languageId] || {};

				const editableId =
					hoveredItemType === ITEM_TYPES.mappedContent
						? editableValue.classNameId
							? getEditableId(editableValue)
							: getEditableId(localizedEditableValue)
						: editable.itemId;

				const hovered =
					([
						ITEM_TYPES.mappedContent,
						ITEM_TYPES.inlineContent,
					].includes(hoveredItemType) &&
						hoveredItemId === editableId) ||
					((siblingIds.some(isActive) || !canUpdatePageStructure) &&
						isHovered(editable.itemId));

				const hoveredClass =
					hoveringOrigin === ITEM_ACTIVATION_ORIGINS.contents
						? EDITABLE_CLASS_NAMES.contentHovered
						: EDITABLE_CLASS_NAMES.hovered;

				if (hovered) {
					editable.element.classList.add(hoveredClass);
				}
				else {
					editable.element.classList.remove(
						EDITABLE_CLASS_NAMES.hovered,
						EDITABLE_CLASS_NAMES.contentHovered
					);
				}
			}
		});
	}, [
		canUpdatePageStructure,
		editables,
		editableValues,
		fragmentEntryLinkId,
		hoveredItemId,
		hoveredItemType,
		isActive,
		isHovered,
		itemId,
		languageId,
		siblingIds,
		hoveringOrigin,
	]);

	useEffect(() => {
		let activeEditable;

		const enableProcessor = (event) => {
			const editableElement = getEditableElement(event.target);

			const editable = editables.find(
				(editable) => editable.element === editableElement
			);

			if (editable) {
				const editableValue = editableValues[editable.editableId] || {};

				if (isMapped(editableValue)) {
					return;
				}

				const editableClickPosition = {
					clientX: event.clientX,
					clientY: event.clientY,
				};

				if (isActive(editable.itemId)) {
					setEditableProcessorUniqueId(
						toControlsId(editable.itemId),
						editableClickPosition
					);
				}
			}
		};

		if (activeItemType === ITEM_TYPES.editable) {
			activeEditable = editables.find((editable) =>
				isActive(editable.itemId)
			);

			if (activeEditable) {
				if (
					canUpdateEditables &&
					selectedViewportSize === VIEWPORT_SIZES.desktop
				) {
					requestAnimationFrame(() => {
						activeEditable.element.addEventListener(
							'click',
							enableProcessor
						);
					});
				}

				if (activationOrigin === ITEM_ACTIVATION_ORIGINS.sidebar) {
					activeEditable.element.scrollIntoView({
						behavior: 'smooth',
						block: 'center',
						inline: 'nearest',
					});
				}
			}
		}

		return () => {
			if (activeEditable) {
				activeEditable.element.removeEventListener(
					'click',
					enableProcessor
				);
			}
		};
	}, [
		activationOrigin,
		activeItemType,
		canUpdateEditables,
		editables,
		editableValues,
		fragmentEntryLinkId,
		isActive,
		itemId,
		setEditableProcessorUniqueId,
		selectedViewportSize,
		toControlsId,
	]);

	const hoverEditable = (event) => {
		const editableElement = getEditableElement(event.target);

		const editable = editables.find(
			(editable) => editable.element === editableElement
		);

		if (editable) {
			event.stopPropagation();

			hoverItem(editable.itemId, {itemType: ITEM_TYPES.editable});
		}
	};

	const selectEditable = (event) => {
		const editableElement = getEditableElement(event.target);

		const editable = editables.find(
			(editable) => editable.element === editableElement
		);

		if (
			editable &&
			canUpdateEditables &&
			canActivateEditable(selectedViewportSize, editable.type)
		) {
			event.stopPropagation();

			if (isActive(editable.itemId)) {
				event.stopPropagation();
			}
			else {
				selectItem(editable.itemId, {
					itemType: ITEM_TYPES.editable,
				});
			}
		}
	};

	const props = {};

	if (siblingIds.some(isActive) || !canUpdatePageStructure) {
		props.onClickCapture = selectEditable;
		props.onMouseLeave = () => hoverItem(null);
		props.onMouseOverCapture = hoverEditable;
	}

	return <div {...props}>{children}</div>;
}

FragmentContentInteractionsFilter.propTypes = {
	element: PropTypes.object,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired,
};

export default React.memo(FragmentContentInteractionsFilter);
