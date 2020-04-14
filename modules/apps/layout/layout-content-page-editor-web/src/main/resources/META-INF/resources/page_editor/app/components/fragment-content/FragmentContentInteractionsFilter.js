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

import PropTypes from 'prop-types';
import React, {useEffect, useMemo} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {config} from '../../config/index';
import selectCanUpdate from '../../selectors/selectCanUpdate';
import selectCanUpdateLayoutContent from '../../selectors/selectCanUpdateLayoutContent';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../../store/index';
import {
	useActivationOrigin,
	useActiveItemId,
	useActiveItemType,
	useHoverItem,
	useHoveredItemId,
	useHoveredItemType,
	useIsActive,
	useIsHovered,
	useSelectItem,
} from '../Controls';
import {useSetEditableProcessorUniqueId} from './EditableProcessorContext';
import {getEditableElement} from './getEditableElement';
import getEditableElementId from './getEditableElementId';
import getEditableUniqueId from './getEditableUniqueId';
import isMapped from './isMapped';

const EDITABLE_CLASS_NAMES = {
	active: 'page-editor__editable--active',
	hovered: 'page-editor__editable--hovered',
	mapped: 'page-editor__editable--mapped',
	translated: 'page-editor__editable--translated',
};

const isTranslated = (
	defaultLanguageId,
	languageId,
	editableValue,
	prefixedSegmentsExperienceId
) =>
	defaultLanguageId !== languageId &&
	(editableValue[languageId] ||
		(editableValue[prefixedSegmentsExperienceId] &&
			editableValue[prefixedSegmentsExperienceId][languageId]));

export default function FragmentContentInteractionsFilter({
	children,
	editableElements,
	fragmentEntryLinkId,
	itemId,
}) {
	const activationOrigin = useActivationOrigin();
	const hoverItem = useHoverItem();
	const isActive = useIsActive();
	const isHovered = useIsHovered();
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();
	const hoveredItemId = useHoveredItemId();
	const hoveredItemType = useHoveredItemType();
	const selectItem = useSelectItem();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const canUpdateLayoutContent = useSelector(selectCanUpdateLayoutContent);
	const canUpdate = useSelector(selectCanUpdate);
	const prefixedSegmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);
	const languageId = useSelector(state => state.languageId);

	const canOnlyUpdateInlineContent = !canUpdate && canUpdateLayoutContent;

	const editableValues = useSelector(state =>
		state.fragmentEntryLinks[fragmentEntryLinkId]
			? state.fragmentEntryLinks[fragmentEntryLinkId].editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
			  ]
			: {}
	);

	const siblingIds = useMemo(
		() => [
			itemId,
			...editableElements.map(editableElement =>
				getEditableUniqueId(
					fragmentEntryLinkId,
					getEditableElementId(editableElement)
				)
			),
		],
		[itemId, editableElements, fragmentEntryLinkId]
	);

	useEffect(() => {
		editableElements.forEach(editableElement => {
			if (editableValues) {
				const editableValue =
					editableValues[getEditableElementId(editableElement)];

				if (isMapped(editableValue)) {
					editableElement.classList.add(EDITABLE_CLASS_NAMES.mapped);
				}
				else if (
					isTranslated(
						config.defaultLanguageId,
						languageId,
						editableValue,
						prefixedSegmentsExperienceId
					)
				) {
					editableElement.classList.add(
						EDITABLE_CLASS_NAMES.translated
					);
				}
				else {
					editableElement.classList.remove(
						EDITABLE_CLASS_NAMES.mapped
					);
					editableElement.classList.remove(
						EDITABLE_CLASS_NAMES.translated
					);
				}
			}
		});
	}, [
		editableElements,
		editableValues,
		languageId,
		prefixedSegmentsExperienceId,
	]);

	useEffect(() => {
		editableElements.forEach(editableElement => {
			const editableUniqueId = getEditableUniqueId(
				fragmentEntryLinkId,
				getEditableElementId(editableElement)
			);

			if (isActive(editableUniqueId)) {
				editableElement.classList.add(EDITABLE_CLASS_NAMES.active);
			}
			else {
				editableElement.classList.remove(EDITABLE_CLASS_NAMES.active);
			}
		});
	}, [activeItemId, editableElements, fragmentEntryLinkId, isActive]);

	useEffect(() => {
		editableElements.forEach(editableElement => {
			if (editableValues) {
				const editableValue =
					editableValues[getEditableElementId(editableElement)];

				const editableUniqueId = getEditableUniqueId(
					fragmentEntryLinkId,
					getEditableElementId(editableElement)
				);

				let hovered = false;

				if (
					hoveredItemType === ITEM_TYPES.mappedContent &&
					`${editableValue.classNameId}-${editableValue.classPK}` ===
						hoveredItemId
				) {
					hovered = true;
				}
				else if (
					siblingIds.some(isActive) &&
					isHovered(editableUniqueId)
				) {
					hovered = true;
				}

				if (hovered) {
					editableElement.classList.add(EDITABLE_CLASS_NAMES.hovered);
				}
				else {
					editableElement.classList.remove(
						EDITABLE_CLASS_NAMES.hovered
					);
				}
			}
		});
	}, [
		editableElements,
		editableValues,
		fragmentEntryLinkId,
		hoveredItemId,
		hoveredItemType,
		isActive,
		isHovered,
		itemId,
		siblingIds,
	]);

	useEffect(() => {
		let activeEditableElement;

		const enableProcessor = event => {
			const editableElement = getEditableElement(event.target);

			if (editableElement) {
				const editableElementId = getEditableElementId(editableElement);
				const editableValue = editableValues[editableElementId] || {};

				if (isMapped(editableValue)) {
					return;
				}
				const editableClickPosition = {
					clientX: event.clientX,
					clientY: event.clientY,
				};
				const editableUniqueId = getEditableUniqueId(
					fragmentEntryLinkId,
					getEditableElementId(editableElement)
				);

				if (isActive(editableUniqueId)) {
					setEditableProcessorUniqueId(
						editableUniqueId,
						editableClickPosition
					);
				}
			}
		};

		if (activeItemId && activeItemType === ITEM_TYPES.editable) {
			activeEditableElement = editableElements.find(editableElement =>
				isActive(
					getEditableUniqueId(
						fragmentEntryLinkId,
						getEditableElementId(editableElement)
					)
				)
			);

			if (activeEditableElement) {
				if (canUpdateLayoutContent) {
					requestAnimationFrame(() => {
						activeEditableElement.addEventListener(
							'dblclick',
							enableProcessor
						);
					});
				}

				if (
					activationOrigin === ITEM_ACTIVATION_ORIGINS.structureTree
				) {
					activeEditableElement.scrollIntoView({
						behavior: 'smooth',
						block: 'center',
						inline: 'nearest',
					});
				}
			}
		}

		return () => {
			if (activeEditableElement) {
				activeEditableElement.removeEventListener(
					'dblclick',
					enableProcessor
				);
			}
		};
	}, [
		activationOrigin,
		activeItemId,
		activeItemType,
		canUpdateLayoutContent,
		editableElements,
		editableValues,
		fragmentEntryLinkId,
		isActive,
		itemId,
		setEditableProcessorUniqueId,
	]);

	const hoverEditable = event => {
		const editableElement = getEditableElement(event.target);

		if (editableElement) {
			event.stopPropagation();

			hoverItem(
				getEditableUniqueId(
					fragmentEntryLinkId,
					getEditableElementId(editableElement)
				),
				{itemType: ITEM_TYPES.editable}
			);
		}
	};

	const selectEditable = event => {
		const editableElement = getEditableElement(event.target);

		if (editableElement) {
			event.stopPropagation();

			const editableUniqueId = getEditableUniqueId(
				fragmentEntryLinkId,
				getEditableElementId(editableElement)
			);

			if (isActive(editableUniqueId)) {
				event.stopPropagation();
			}
			else {
				selectItem(editableUniqueId, {
					itemType: ITEM_TYPES.editable,
					multiSelect: event.shiftKey,
				});
			}
		}
	};

	const props = {};

	if (siblingIds.some(isActive) || canOnlyUpdateInlineContent) {
		props.onClickCapture = selectEditable;
		props.onMouseOverCapture = hoverEditable;
	}

	return <div {...props}>{children}</div>;
}

FragmentContentInteractionsFilter.propTypes = {
	element: PropTypes.instanceOf(HTMLElement),
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired,
};
