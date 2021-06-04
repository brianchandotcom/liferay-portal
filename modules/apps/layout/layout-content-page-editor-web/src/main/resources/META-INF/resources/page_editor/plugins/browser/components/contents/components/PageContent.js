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

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {useToControlsId} from '../../../../../app/contexts/CollectionItemContext';
import {
	useHoverItem,
	useHoveredItemId,
	useSelectItem,
} from '../../../../../app/contexts/ControlsContext';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from '../../../../../app/contexts/EditableProcessorContext';
import {
	useSelector,
	useSelectorCallback,
} from '../../../../../app/contexts/StoreContext';
import {selectPageContentDropdownItems} from '../../../../../app/selectors/selectPageContentDropdownItems';

export default function PageContent({
	classNameId,
	classPK,
	editableId,
	icon,
	subtype,
	title,
}) {
	const dropdownItems = useSelectorCallback(
		selectPageContentDropdownItems(classPK),
		[classPK]
	);
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [isHovered, setIsHovered] = useState(false);
	const [
		nextEditbleProcessorUniqueId,
		setEditableNextProcessorUniqueId,
	] = useState(null);
	const selectItem = useSelectItem();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const toControlsId = useToControlsId();

	const isBeingEdited = useMemo(
		() => toControlsId(editableId) === editableProcessorUniqueId,
		[toControlsId, editableId, editableProcessorUniqueId]
	);

	useEffect(() => {
		if (editableProcessorUniqueId || !nextEditbleProcessorUniqueId) {
			return;
		}

		setEditableProcessorUniqueId(nextEditbleProcessorUniqueId);
		setEditableNextProcessorUniqueId(null);
	}, [
		editableProcessorUniqueId,
		nextEditbleProcessorUniqueId,
		setEditableProcessorUniqueId,
	]);

	useEffect(() => {
		if (hoveredItemId) {
			if (editableId) {
				setIsHovered(editableId === hoveredItemId);
			}
			else {
				const [
					fragmentEntryLinkId,
					...editableId
				] = hoveredItemId.split('-');

				if (fragmentEntryLinks[fragmentEntryLinkId]) {
					const fragmentEntryLink =
						fragmentEntryLinks[fragmentEntryLinkId];

					const editableValue =
						fragmentEntryLink.editableValues[
							EDITABLE_FRAGMENT_ENTRY_PROCESSOR
						];

					const editable = editableValue[editableId.join('-')];

					if (editable) {
						setIsHovered(editable.classPK === classPK);
					}
				}
			}
		}
		else {
			setIsHovered(false);
		}
	}, [fragmentEntryLinks, hoveredItemId, classPK, editableId]);

	const handleMouseOver = () => {
		setIsHovered(true);

		if (editableId) {
			hoverItem(editableId, {
				itemType: ITEM_TYPES.inlineContent,
				origin: ITEM_ACTIVATION_ORIGINS.contents,
			});
		}

		if (classNameId && classPK) {
			hoverItem(`${classNameId}-${classPK}`, {
				itemType: ITEM_TYPES.mappedContent,
				origin: ITEM_ACTIVATION_ORIGINS.contents,
			});
		}
	};

	const handleMouseLeave = () => {
		setIsHovered(false);
		hoverItem(null);
	};

	const onClickEditInlineText = () => {
		if (isBeingEdited) {
			return;
		}

		selectItem(`${editableId}`, {
			itemType: ITEM_TYPES.editable,
			origin: ITEM_ACTIVATION_ORIGINS.sidebar,
		});

		setEditableNextProcessorUniqueId(toControlsId(editableId));
	};

	return (
		<li
			className={classNames('page-editor__page-contents__page-content', {
				'page-editor__page-contents__page-content--mapped-item-hovered': isHovered,
			})}
			onMouseLeave={handleMouseLeave}
			onMouseOver={handleMouseOver}
		>
			<div
				className={classNames('d-flex', {
					'align-items-center': !subtype,
				})}
			>
				<ClayIcon
					className={classNames('mr-3', {
						'mt-1': subtype,
					})}
					focusable="false"
					monospaced="true"
					role="presentation"
					symbol={icon || 'document-text'}
				/>
				<ClayLayout.ContentCol expand>
					<span className="font-weight-semi-bold text-truncate">
						{title}
					</span>

					{subtype && (
						<span className="text-secondary">{subtype}</span>
					)}
				</ClayLayout.ContentCol>

				{dropdownItems ? (
					<ClayDropDownWithItems
						items={dropdownItems}
						trigger={
							<ClayButton
								className="btn-monospaced btn-sm text-secondary"
								displayType="unstyled"
							>
								<span className="sr-only">
									{Liferay.Language.get('open-actions-menu')}
								</span>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					/>
				) : (
					<ClayButton
						className={classNames('btn-sm mr-2 text-secondary', {
							'not-allowed': isBeingEdited,
						})}
						disabled={isBeingEdited}
						displayType="unstyled"
						onClick={onClickEditInlineText}
					>
						<span className="sr-only">
							{Liferay.Language.get('edit-inline-text')}
						</span>
						<ClayIcon symbol="pencil" />
					</ClayButton>
				)}
			</div>
		</li>
	);
}

PageContent.propTypes = {
	actions: PropTypes.object,
	icon: PropTypes.string,
	name: PropTypes.string,
	subtype: PropTypes.string,
	title: PropTypes.string.isRequired,
};
