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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayCheckbox, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {NESTING_MARGIN} from '../constants/nestingMargin';
import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {useConstants} from '../contexts/ConstantsContext';
import {useItems, useSetItems} from '../contexts/ItemsContext';
import {
	useSelectedMenuItemId,
	useSetSelectedMenuItemId,
} from '../contexts/SelectedMenuItemIdContext';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';
import getFlatItems from '../utils/getFlatItems';
import getItemPath from '../utils/getItemPath';
import {useDragItem, useDropTarget} from '../utils/useDragAndDrop';

const DELETION_TYPES = {
	bulk: 0,
	single: 1,
};

export function MenuItem({item}) {
	const setItems = useSetItems();
	const setSelectedMenuItemId = useSetSelectedMenuItemId();
	const setSidebarPanelId = useSetSidebarPanelId();
	const {
		deleteSiteNavigationMenuItemURL,
		editSiteNavigationMenuItemParentURL,
		languageId,
		portletNamespace,
	} = useConstants();

	const items = useItems();
	const {siteNavigationMenuItemId, title, type} = item;
	const itemPath = getItemPath(siteNavigationMenuItemId, items);
	const selected = useSelectedMenuItemId() === siteNavigationMenuItemId;

	const [deletionModalVisible, setDeletionModalVisible] = useState(false);
	const [deletionType, setDeletionType] = useState(DELETION_TYPES.single);

	const deleteMenuItem = () => {
		fetch(deleteSiteNavigationMenuItemURL, {
			body: objectToFormData({
				[`${portletNamespace}siteNavigationMenuItemId`]: siteNavigationMenuItemId,
				[`${portletNamespace}deleteChildren`]:
					deletionType === DELETION_TYPES.bulk,
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({siteNavigationMenuItems}) => {
				const newItems = getFlatItems(siteNavigationMenuItems);

				setItems(newItems);

				setSidebarPanelId(null);
			})
			.catch(({error}) => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	};

	const updateMenuItemParent = (itemId, parentId) => {
		const order = items
			.filter((item) => item.parentSiteNavigationMenuItemId === parentId)
			.findIndex((item) => item.siteNavigationMenuItemId === itemId);

		fetch(editSiteNavigationMenuItemParentURL, {
			body: objectToFormData({
				[`${portletNamespace}siteNavigationMenuItemId`]: itemId,
				[`${portletNamespace}parentSiteNavigationMenuItemId`]: parentId,
				[`${portletNamespace}order`]: order,
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({siteNavigationMenuItems}) => {
				const newItems = getFlatItems(siteNavigationMenuItems);

				setItems(newItems);
			})
			.catch(({error}) => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	};

	const {handlerRef, isDragging} = useDragItem(item, updateMenuItemParent);
	const {targetRef} = useDropTarget(item);

	const rtl = Liferay.Language.direction[languageId] === 'rtl';
	const itemStyle = rtl
		? {marginRight: (itemPath.length - 1) * NESTING_MARGIN}
		: {marginLeft: (itemPath.length - 1) * NESTING_MARGIN};

	const parentItemId =
		itemPath.length > 1 ? itemPath[itemPath.length - 2] : '0';

	return (
		<>
			<div
				aria-label={`${title} (${type})`}
				aria-level={itemPath.length}
				data-item-id={item.siteNavigationMenuItemId}
				data-parent-item-id={parentItemId}
				ref={targetRef}
				role="listitem"
			>
				<ClayCard
					className={classNames(
						'site_navigation_menu_editor_MenuItem',
						{
							'dragging': isDragging,
							'site_navigation_menu_editor_MenuItem--selected': selected,
						}
					)}
					selectable
					style={itemStyle}
				>
					<ClayCheckbox
						aria-label={Liferay.Util.sub(
							Liferay.Language.get('select-x'),
							`${title} (${type})`
						)}
						checked={selected}
						onChange={() => {
							setSelectedMenuItemId(siteNavigationMenuItemId);
							setSidebarPanelId(
								SIDEBAR_PANEL_IDS.menuItemSettings
							);
						}}
					>
						<ClayCard.Body className="px-0">
							<ClayCard.Row>
								<ClayLayout.ContentCol gutters ref={handlerRef}>
									<ClayIcon symbol="drag" />
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol expand>
									<ClayCard.Description
										displayType="title"
										title={title}
									>
										{title}

										{item.icon && (
											<ClayIcon
												className="ml-2 text-warning"
												symbol={item.icon}
											/>
										)}
									</ClayCard.Description>

									<div className="d-flex">
										<ClayLabel
											className="mt-1"
											displayType="secondary"
										>
											{type}
										</ClayLabel>

										{item.dynamic && (
											<ClayLabel
												className="mt-1"
												displayType="info"
											>
												{Liferay.Language.get(
													'dynamic'
												)}
											</ClayLabel>
										)}
									</div>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol gutters>
									<ClayButtonWithIcon
										aria-label={Liferay.Util.sub(
											Liferay.Language.get('delete-x'),
											`${title} (${type})`
										)}
										displayType="unstyled"
										onClick={() =>
											item.children.length
												? setDeletionModalVisible(true)
												: deleteMenuItem()
										}
										small
										symbol="times-circle"
									/>
								</ClayLayout.ContentCol>
							</ClayCard.Row>
						</ClayCard.Body>
					</ClayCheckbox>
				</ClayCard>
			</div>

			{deletionModalVisible && (
				<DeletionModal
					deletionType={deletionType}
					onCloseModal={() => setDeletionModalVisible(false)}
					onDeleteItem={deleteMenuItem}
					setDeletionType={setDeletionType}
				/>
			)}
		</>
	);
}

MenuItem.propTypes = {
	item: PropTypes.shape({
		children: PropTypes.array.isRequired,
		siteNavigationMenuItemId: PropTypes.string.isRequired,
		title: PropTypes.string.isRequired,
		type: PropTypes.string.isRequired,
	}),
};

function DeletionModal({
	deletionType,
	onCloseModal,
	onDeleteItem,
	setDeletionType,
}) {
	const isMounted = useIsMounted();

	const {observer, onClose} = useModal({
		onClose: () => {
			if (isMounted()) {
				onCloseModal();
			}
		},
	});

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="lg"
		>
			<ClayModal.Header>
				{Liferay.Language.get('delete-item')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="font-weight-semi-bold">
					{Liferay.Language.get(
						'the-item-you-want-to-delete-has-children-that-also-can-be-removed'
					)}
				</p>

				<p className="text-secondary">
					{Liferay.Language.get('what-action-do-you-want-to-take')}
				</p>

				<ClayRadioGroup
					onSelectedValueChange={(type) => setDeletionType(type)}
					selectedValue={deletionType}
				>
					<ClayRadio
						label={Liferay.Language.get('only-delete-this-item')}
						value={DELETION_TYPES.single}
					/>

					<ClayRadio
						label={Liferay.Language.get('delete-item-and-children')}
						value={DELETION_TYPES.bulk}
					/>
				</ClayRadioGroup>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={onDeleteItem}
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
