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
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {fromControlsId} from '../../../app/components/layout-data-items/Collection';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {
	useHoverItem,
	useHoveredItemId,
	useSelectItem,
} from '../../../app/contexts/ControlsContext';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from '../../../app/contexts/EditableProcessorContext';
import {
	useSelector,
	useSelectorCallback,
} from '../../../app/contexts/StoreContext';
import selectCanUpdateEditables from '../../../app/selectors/selectCanUpdateEditables';
import {selectPageContentDropdownItems} from '../../../app/selectors/selectPageContentDropdownItems';
import getFirstControlsId from '../../../app/utils/getFirstControlsId';
import getFragmentItem from '../../../app/utils/getFragmentItem';
import ImageEditorModal from './ImageEditorModal';

export default function PageContent({
	classNameId,
	classPK,
	editableId,
	icon,
	subtype,
	title,
}) {
	const [activeActions, setActiveActions] = useState(false);
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const canUpdateEditables = useSelector(selectCanUpdateEditables);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [isHovered, setIsHovered] = useState(false);
	const layoutData = useSelector((state) => state.layoutData);
	const [
		nextEditableProcessorUniqueId,
		setNextEditableProcessorUniqueId,
	] = useState(null);
	const selectItem = useSelectItem();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const [imageEditorParams, setImageEditorParams] = useState(null);

	const isBeingEdited = useMemo(
		() => editableId === fromControlsId(editableProcessorUniqueId),
		[editableId, editableProcessorUniqueId]
	);

	const dropdownItems = useSelectorCallback(
		(state) => {
			const pageContentDropdownItems = selectPageContentDropdownItems(
				classPK
			)(state);

			return pageContentDropdownItems?.map((item) => {
				if (item.label === Liferay.Language.get('edit-image')) {
					const {
						editImageURL,
						fileEntryId,
						previewURL,
						...editImageItem
					} = item;

					return {
						...editImageItem,
						onClick: () => {
							setImageEditorParams({
								editImageURL,
								fileEntryId,
								previewURL,
							});
						},
					};
				}

				return item;
			});
		},
		[classPK]
	);

	useEffect(() => {
		if (editableProcessorUniqueId || !nextEditableProcessorUniqueId) {
			return;
		}

		setEditableProcessorUniqueId(nextEditableProcessorUniqueId);
		setNextEditableProcessorUniqueId(null);
	}, [
		editableProcessorUniqueId,
		nextEditableProcessorUniqueId,
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
						] || {};

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

		const itemId = getFirstControlsId({
			item: {
				id: editableId,
				itemType: ITEM_TYPES.editable,
				parentId: getFragmentItem(layoutData, editableId.split('-')[0])
					?.itemId,
			},
			layoutData,
		});

		selectItem(itemId, {
			itemType: ITEM_TYPES.editable,
			origin: ITEM_ACTIVATION_ORIGINS.sidebar,
		});

		setNextEditableProcessorUniqueId(itemId);
	};

	return (
		<li
			className={classNames(
				'page-editor__page-contents__page-content mb-1 py-2',
				{
					'page-editor__page-contents__page-content--mapped-item-hovered':
						isHovered || activeActions || isBeingEdited,
				}
			)}
			onMouseLeave={handleMouseLeave}
			onMouseOver={handleMouseOver}
		>
			<div
				className={classNames('d-flex', {
					'align-items-center': !subtype,
				})}
			>
				<ClayIcon
					className={classNames('mr-3 flex-shrink-0', {
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

				{dropdownItems?.length ? (
					<ClayDropDownWithItems
						active={activeActions}
						className="align-self-center"
						items={dropdownItems}
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setActiveActions}
						trigger={
							<ClayButton
								aria-label={sub(
									Liferay.Language.get('actions-for-x'),
									title
								)}
								className="btn-sm mr-2 page-editor__page-contents__button"
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
						aria-label={sub(
							Liferay.Language.get('edit-inline-text-x'),
							title
						)}
						className={classNames(
							'btn-sm mr-2 page-editor__page-contents__button',
							{
								'not-allowed':
									isBeingEdited || !canUpdateEditables,
							}
						)}
						disabled={isBeingEdited || !canUpdateEditables}
						displayType="unstyled"
						onClick={onClickEditInlineText}
					>
						<ClayIcon symbol="pencil" />
					</ClayButton>
				)}
			</div>

			{imageEditorParams && (
				<ImageEditorModal
					editImageURL={imageEditorParams.editImageURL}
					fileEntryId={imageEditorParams.fileEntryId}
					fragmentEntryLinks={fragmentEntryLinks}
					onCloseModal={() => setImageEditorParams(null)}
					previewURL={imageEditorParams.previewURL}
				/>
			)}
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
