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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import {config} from '../../app/config/index';
import {useSelectorCallback} from '../../app/contexts/StoreContext';
import {selectPageContentDropdownItems} from '../../app/selectors/selectPageContentDropdownItems';
import {useId} from '../../app/utils/useId';
import {openItemSelector} from '../../core/openItemSelector';

const DEFAULT_PREVENT_ITEM_SELECT = () => false;

const DEFAULT_OPTIONS_MENU_ITEMS = [];

const DEFAULT_QUICK_MAPPED_INFO_ITEMS = [];

export default function ItemSelector({
	className,
	eventName,
	itemSelectorURL,
	label,
	modalProps,
	onItemSelect,
	optionsMenuItems = DEFAULT_OPTIONS_MENU_ITEMS,
	quickMappedInfoItems = DEFAULT_QUICK_MAPPED_INFO_ITEMS,
	selectedItem,
	shouldPreventItemSelect = DEFAULT_PREVENT_ITEM_SELECT,
	showEditControls = true,
	showMappedItems = true,
	transformValueCallback,
}) {
	const itemSelectorInputId = useId();

	const openModal = useCallback(() => {
		if (shouldPreventItemSelect()) {
			return;
		}

		openItemSelector({
			callback: onItemSelect,
			eventName: eventName || `${config.portletNamespace}selectInfoItem`,
			itemSelectorURL: itemSelectorURL || config.infoItemSelectorURL,
			modalProps,
			transformValueCallback,
		});
	}, [
		eventName,
		itemSelectorURL,
		modalProps,
		onItemSelect,
		shouldPreventItemSelect,
		transformValueCallback,
	]);

	const mappedItemsMenu = useSelectorCallback(
		(state) => {
			let transformedMappedItems = [];

			if (!showMappedItems) {
				return transformedMappedItems;
			}

			const transformMappedItem = (item) => ({
				'data-item-id': `${item.classNameId}-${item.classPK}`,
				'label': item.title,
				'onClick': () => onItemSelect(item),
			});

			if (quickMappedInfoItems.length > 0) {
				transformedMappedItems = quickMappedInfoItems.map(
					transformMappedItem
				);
			}
			else if (state.pageContents?.length > 0) {
				transformedMappedItems = state.pageContents.map(
					transformMappedItem
				);
			}

			if (transformedMappedItems.length) {
				transformedMappedItems.push(
					{
						type: 'divider',
					},
					{
						label: `${Liferay.Util.sub(
							Liferay.Language.get('select-x'),
							label
						)}...`,
						onClick: () => openModal(),
					}
				);
			}

			return transformedMappedItems;
		},
		[onItemSelect, openModal, quickMappedInfoItems, showMappedItems],
		(a, b) =>
			a.length === b.length &&
			a.every(
				(item, index) =>
					item['data-item-id'] === b[index]['data-item-id']
			)
	);

	const optionsMenu = useSelectorCallback(
		(state) => {
			const menuItems = [];

			if (selectedItem?.classPK) {
				const contentMenuItems = selectPageContentDropdownItems(
					selectedItem.classPK,
					label
				)(state)?.filter(
					(item) => item.label !== Liferay.Language.get('edit-image')
				);

				if (contentMenuItems?.length) {
					menuItems.push(...contentMenuItems, {type: 'divider'});
				}
			}

			if (optionsMenuItems.length) {
				menuItems.push(...optionsMenuItems, {type: 'divider'});
			}

			menuItems.push({
				label: Liferay.Util.sub(
					Liferay.Language.get('remove-x'),
					label
				),
				onClick: () => onItemSelect({}),
			});

			return menuItems;
		},
		[label, onItemSelect, optionsMenuItems, selectedItem]
	);

	const selectedItemTitle = useSelectorCallback(
		(state) => {
			if (!selectedItem) {
				return '';
			}

			return (
				[
					...(quickMappedInfoItems || []),
					...(state.pageContents || []),
				].find(
					(item) =>
						item.classNameId === selectedItem.classNameId &&
						item.classPK === selectedItem.classPK
				)?.title ||
				selectedItem.title ||
				''
			);
		},
		[quickMappedInfoItems, selectedItem]
	);

	const selectContentButtonIcon = selectedItem?.title ? 'change' : 'plus';

	const selectContentButtonLabel = Liferay.Util.sub(
		selectedItem?.title
			? Liferay.Language.get('change-x')
			: Liferay.Language.get('select-x'),
		label
	);

	return (
		<ClayForm.Group className={className}>
			<label htmlFor={itemSelectorInputId}>{label}</label>

			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<ClayInput
						className={classNames({
							'page-editor__item-selector__content-input': showEditControls,
						})}
						id={itemSelectorInputId}
						onClick={() => {
							if (showEditControls) {
								openModal();
							}
						}}
						placeholder={Liferay.Util.sub(
							Liferay.Language.get('select-x'),
							label
						)}
						readOnly
						sizing="sm"
						type="text"
						value={selectedItemTitle}
					/>
				</ClayInput.GroupItem>

				{showEditControls &&
					(mappedItemsMenu.length > 0 ? (
						<ClayInput.GroupItem shrink>
							<ClayDropDownWithItems
								items={mappedItemsMenu}
								menuElementAttrs={{
									containerProps: {
										className: 'cadmin',
									},
								}}
								trigger={
									<ClayButtonWithIcon
										aria-label={selectContentButtonLabel}
										displayType="secondary"
										small
										symbol={selectContentButtonIcon}
										title={selectContentButtonLabel}
									/>
								}
							/>
						</ClayInput.GroupItem>
					) : (
						<ClayInput.GroupItem shrink>
							<ClayButtonWithIcon
								aria-label={selectContentButtonLabel}
								displayType="secondary"
								onClick={openModal}
								small
								symbol={selectContentButtonIcon}
								title={selectContentButtonLabel}
							/>
						</ClayInput.GroupItem>
					))}

				{showEditControls && selectedItem?.title && (
					<ClayInput.GroupItem shrink>
						<ClayDropDownWithItems
							items={optionsMenu}
							menuElementAttrs={{
								containerProps: {
									className: 'cadmin',
								},
							}}
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Util.sub(
										Liferay.Language.get('view-x-options'),
										label
									)}
									displayType="secondary"
									small
									symbol="ellipsis-v"
									title={Liferay.Util.sub(
										Liferay.Language.get('view-x-options'),
										label
									)}
								/>
							}
						/>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

ItemSelector.propTypes = {
	className: PropTypes.string,
	eventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	label: PropTypes.string.isRequired,
	modalProps: PropTypes.object,
	onItemSelect: PropTypes.func.isRequired,
	optionsMenuItems: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			onClick: PropTypes.func.isRequired,
		})
	),
	quickMappedInfoItems: PropTypes.arrayOf(
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			title: PropTypes.string,
		})
	),
	selectedItem: PropTypes.shape({title: PropTypes.string}),
	shouldPreventItemSelect: PropTypes.func,
	showEditControls: PropTypes.bool,
	showMappedItems: PropTypes.bool,
	transformValueCallback: PropTypes.func.isRequired,
};
