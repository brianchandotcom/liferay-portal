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

export default function ItemSelector({
	eventName,
	itemSelectorURL,
	label,
	onItemSelect,
	quickMappedInfoItems = [],
	modalProps,
	selectedItem,
	showAddButton = true,
	showMappedItems = true,
	transformValueCallback,
}) {
	const itemSelectorInputId = useId();

	const openModal = useCallback(
		() =>
			openItemSelector({
				callback: onItemSelect,
				eventName:
					eventName || `${config.portletNamespace}selectInfoItem`,
				itemSelectorURL: itemSelectorURL || config.infoItemSelectorURL,
				modalProps,
				transformValueCallback,
			}),
		[
			eventName,
			itemSelectorURL,
			modalProps,
			onItemSelect,
			transformValueCallback,
		]
	);

	const mappedItemsMenu = useSelectorCallback(
		(state) => {
			let transformedMappedItems = [];

			if (!showMappedItems) {
				return transformedMappedItems;
			}

			const transformMappedItem = (item) => ({
				itemId: `${item.classNameId}-${item.classPK}`,
				label: item.title,
				onClick: () => onItemSelect(item),
			});

			if (quickMappedInfoItems.length > 0) {
				transformedMappedItems = quickMappedInfoItems.map(
					transformMappedItem
				);
			}
			else if (state.mappedInfoItems?.length > 0) {
				transformedMappedItems = state.mappedInfoItems.map(
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
			a.every((item, index) => item.itemId === b[index].itemId)
	);

	const optionsMenu = useSelectorCallback(
		(state) => {
			const menuItems = [];

			if (config.contentBrowsingEnabled && selectedItem?.classPK) {
				const contentMenuItems = selectPageContentDropdownItems(
					selectedItem.classPK,
					label
				)(state);

				if (contentMenuItems?.length) {
					menuItems.push(...contentMenuItems, {type: 'divider'});
				}
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
		[label, onItemSelect, selectedItem]
	);

	const selectContentButtonIcon = selectedItem?.title ? 'change' : 'plus';

	const selectContentButtonLabel = Liferay.Util.sub(
		selectedItem?.title
			? Liferay.Language.get('change-x')
			: Liferay.Language.get('select-x'),
		label
	);

	return (
		<ClayForm.Group className="mb-2" small>
			<label htmlFor={itemSelectorInputId}>{label}</label>

			<div className="d-flex">
				<ClayInput
					className={classNames('mr-2', {
						'page-editor__item-selector__content-input': showAddButton,
					})}
					id={itemSelectorInputId}
					onClick={() => {
						if (showAddButton) {
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
					value={selectedItem?.title || ''}
				/>

				{showAddButton &&
					(mappedItemsMenu.length > 0 ? (
						<ClayDropDownWithItems
							items={mappedItemsMenu}
							trigger={
								<ClayButtonWithIcon
									aria-label={selectContentButtonLabel}
									className="page-editor__item-selector__content-button"
									displayType="secondary"
									small
									symbol={selectContentButtonIcon}
									title={selectContentButtonLabel}
								/>
							}
						/>
					) : (
						<ClayButtonWithIcon
							aria-label={selectContentButtonLabel}
							className="page-editor__item-selector__content-button"
							displayType="secondary"
							onClick={openModal}
							small
							symbol={selectContentButtonIcon}
							title={selectContentButtonLabel}
						/>
					))}

				{selectedItem?.title && (
					<ClayDropDownWithItems
						items={optionsMenu}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Util.sub(
									Liferay.Language.get('view-x-options'),
									label
								)}
								className="ml-2 page-editor__item-selector__content-button"
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
				)}
			</div>
		</ClayForm.Group>
	);
}

ItemSelector.propTypes = {
	eventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	label: PropTypes.string.isRequired,
	onItemSelect: PropTypes.func.isRequired,
	selectedItem: PropTypes.shape({title: PropTypes.string}),
	transformValueCallback: PropTypes.func.isRequired,
};
