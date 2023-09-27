/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import {config} from '../../app/config/index';
import {useSelectorCallback} from '../../app/contexts/StoreContext';
import {selectPageContentDropdownItems} from '../../app/selectors/selectPageContentDropdownItems';
import findPageContent from '../../app/utils/findPageContent';
import getEditableId from '../../app/utils/getEditableId';
import {openItemSelector} from '../openItemSelector';

const DEFAULT_BEFORE_ITEM_SELECT = () => {};

const DEFAULT_OPTIONS_MENU_ITEMS = [];

const DEFAULT_QUICK_MAPPED_INFO_ITEMS = [];

export default function ItemSelector({
	className,
	eventName,
	helpText,
	itemSelectorURL,
	label,
	modalProps,
	onBeforeItemSelect = DEFAULT_BEFORE_ITEM_SELECT,
	onItemSelect,
	optionsMenuItems = DEFAULT_OPTIONS_MENU_ITEMS,
	quickMappedInfoItems = DEFAULT_QUICK_MAPPED_INFO_ITEMS,
	selectedItem,
	showEditControls = true,
	showMappedItems = true,
	transformValueCallback,
}) {
	const helpTextId = useId();
	const itemSelectorInputId = useId();

	const openModal = useCallback(() => {
		let defaultPrevented = false;

		onBeforeItemSelect({
			preventDefault: () => {
				defaultPrevented = true;
			},
		});

		if (defaultPrevented) {
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
		onBeforeItemSelect,
		transformValueCallback,
	]);

	const mappedItemsMenu = useSelectorCallback(
		(state) => {
			let transformedMappedItems = [];

			if (!showMappedItems) {
				return transformedMappedItems;
			}

			const transformMappedItem = (item) => ({
				'data-item-id': getEditableId(item),
				'label': item.title,
				'onClick': () => onItemSelect(item),
			});

			if (quickMappedInfoItems.length) {
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
				transformedMappedItems = [
					{
						items: transformedMappedItems,
						label: Liferay.Language.get('recent'),
						type: 'group',
					},
				];

				transformedMappedItems.push(
					{
						type: 'divider',
					},
					{
						label: `${sub(
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

			if (selectedItem?.classPK || selectedItem?.externalReferenceCode) {
				const contentMenuItems = selectPageContentDropdownItems(
					selectedItem,
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
				label: sub(Liferay.Language.get('remove-x'), label),
				onClick: () => onItemSelect({}),
				symbolLeft:
					label === Liferay.Language.get('collection')
						? 'trash'
						: null,
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

			const content = findPageContent(
				[
					...(quickMappedInfoItems || []),
					...(state.pageContents || []),
				],
				selectedItem
			);

			return content?.title || selectedItem.title || '';
		},
		[quickMappedInfoItems, selectedItem]
	);

	const selectContentButtonIcon = selectedItem?.title ? 'change' : 'plus';

	const selectContentButtonLabel = sub(
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
						aria-describedby={helpText ? helpTextId : null}
						className={classNames({
							'page-editor__item-selector__content-input': showEditControls,
						})}
						id={itemSelectorInputId}
						onClick={() => {
							if (showEditControls) {
								openModal();
							}
						}}
						placeholder={sub(
							Liferay.Language.get('select-x'),
							label
						)}
						readOnly
						sizing="sm"
						title={selectedItemTitle}
						type="text"
						value={selectedItemTitle}
					/>
				</ClayInput.GroupItem>

				{showEditControls &&
					(mappedItemsMenu.length ? (
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
										size="sm"
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
								size="sm"
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
									aria-label={sub(
										Liferay.Language.get('view-x-options'),
										label
									)}
									displayType="secondary"
									size="sm"
									symbol="ellipsis-v"
									title={sub(
										Liferay.Language.get('view-x-options'),
										label
									)}
								/>
							}
						/>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>

			{helpText ? (
				<div className="mt-1 text-secondary" id={helpTextId}>
					{helpText}
				</div>
			) : null}
		</ClayForm.Group>
	);
}

ItemSelector.propTypes = {
	className: PropTypes.string,
	eventName: PropTypes.string,
	helpText: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	label: PropTypes.string.isRequired,
	modalProps: PropTypes.object,
	onBeforeItemSelect: PropTypes.func,
	onItemSelect: PropTypes.func.isRequired,
	optionsMenuItems: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string,
			label: PropTypes.string.isRequired,
			onClick: PropTypes.func,
		})
	),
	quickMappedInfoItems: PropTypes.arrayOf(
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			externalReferenceCode: PropTypes.string,
			title: PropTypes.string,
		})
	),
	selectedItem: PropTypes.shape({title: PropTypes.string}),
	showEditControls: PropTypes.bool,
	showMappedItems: PropTypes.bool,
	transformValueCallback: PropTypes.func.isRequired,
};
