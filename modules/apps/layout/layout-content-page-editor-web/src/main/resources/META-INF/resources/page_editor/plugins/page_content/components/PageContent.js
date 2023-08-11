/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {fromControlsId} from '../../../app/components/layout_data_items/Collection';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
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
import getEditableId from '../../../app/utils/getEditableId';
import getFirstControlsId from '../../../app/utils/getFirstControlsId';
import getFragmentItem from '../../../app/utils/getFragmentItem';
import ImageEditorModal from './ImageEditorModal';

export default function PageContent({
	classNameId,
	classPK,
	editableId,
	externalReferenceCode,
	icon,
	isRestricted = false,
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
			const pageContentDropdownItems = selectPageContentDropdownItems({
				classNameId,
				classPK,
				externalReferenceCode,
			})(state);

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
		[classNameId, classPK, externalReferenceCode]
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
						setIsHovered(
							editable.classPK === classPK ||
								editable.externalReferenceCode ===
									externalReferenceCode
						);
					}
				}
			}
		}
		else {
			setIsHovered(false);
		}
	}, [
		fragmentEntryLinks,
		hoveredItemId,
		classPK,
		editableId,
		externalReferenceCode,
	]);

	const handleOnClick = () => {
		Object.values(layoutData.items).forEach((item) => {
			if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
				const collectionConfig = item.config?.collection;

				if (
					collectionConfig.classNameId === classNameId &&
					collectionConfig.classPK === classPK
				) {
					selectItem(item.itemId, {
						itemType: ITEM_TYPES.layoutDataItem,
						origin: ITEM_ACTIVATION_ORIGINS.sidebar,
					});
				}
			}
		});
	};

	const handleMouseOver = () => {
		setIsHovered(true);

		if (editableId) {
			hoverItem(editableId, {
				itemType: ITEM_TYPES.inlineContent,
				origin: ITEM_ACTIVATION_ORIGINS.contents,
			});
		}

		if (classNameId && (classPK || externalReferenceCode)) {
			hoverItem(
				getEditableId({classNameId, classPK, externalReferenceCode}),
				{
					itemType: ITEM_TYPES.mappedContent,
					origin: ITEM_ACTIVATION_ORIGINS.contents,
				}
			);
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
				'page-editor__page-contents__page-content mb-1 p-1',
				{
					'page-editor__page-contents__page-content--mapped-item-hovered':
						isHovered || activeActions || isBeingEdited,
				}
			)}
			onClick={handleOnClick}
			onMouseLeave={handleMouseLeave}
			onMouseOver={handleMouseOver}
		>
			{Liferay.FeatureFlags['LPS-169923'] && isRestricted ? (
				<div className="align-items-center d-flex">
					<ClayIcon
						className="flex-shrink-0 mr-3"
						symbol="password-policies"
					/>

					<span className="font-weight-semi-bold">
						{Liferay.Language.get('restricted-content')}
					</span>
				</div>
			) : (
				<ClayLayout.ContentRow
					className={classNames({'align-items-center': !subtype})}
					padded
				>
					<ClayLayout.ContentCol>
						<ClayIcon
							className={subtype ? 'mt-1' : 'm-0'}
							focusable="false"
							monospaced="true"
							role="presentation"
							symbol={icon || 'document-text'}
						/>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol expand title={title}>
						<span className="font-weight-semi-bold text-truncate">
							{title}
						</span>

						{subtype && (
							<span className="text-break text-secondary">
								{subtype}
							</span>
						)}
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
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
											Liferay.Language.get(
												'actions-for-x'
											),
											title
										)}
										className={classNames(
											'page-editor__page-contents__button',
											{'mt-1': subtype}
										)}
										displayType="unstyled"
										size="sm"
										title={sub(
											Liferay.Language.get(
												'open-actions-menu'
											),
											title
										)}
									>
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
									'page-editor__page-contents__button',
									{
										'not-allowed':
											isBeingEdited ||
											!canUpdateEditables,
									}
								)}
								disabled={isBeingEdited || !canUpdateEditables}
								displayType="unstyled"
								onClick={onClickEditInlineText}
								size="sm"
							>
								<ClayIcon symbol="pencil" />
							</ClayButton>
						)}
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			)}

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
