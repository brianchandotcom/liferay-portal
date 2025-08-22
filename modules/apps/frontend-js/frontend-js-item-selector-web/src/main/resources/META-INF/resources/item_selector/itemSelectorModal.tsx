/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {InternalDispatch, useControlledState} from '@clayui/shared';
import {
	FrontendDataSet,
	IFrontendDataSetProps,
} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useMemo} from 'react';

export interface IItemSelectorModalProps<T> {

	/**
	 * Configuration properties of the Frontend Data Set used to display data.
	 */
	fdsProps: Omit<IFrontendDataSetProps, 'selectedItems'>;

	/**
	 * Fieldname from apiURL response used to display selection value in the modal.
	 */
	itemNameLocator: string | ((item: T) => any);

	/**
	 * Fieldname from item selector to pass item's value into the FDS.
	 */
	itemValueLocator: string | ((item: T) => any);

	/**
	 * Set the default selected items, always an array (controlled).
	 */
	items: T[];

	/**
	 * Expects the 'observer' property from the Clay useModal hook.
	 */
	observer: any;

	/**
	 * Expects the 'onOpenChange' property from the Clay useModal hook.
	 */
	onOpenChange: (value: boolean) => void;

	/**
	 * Callback function called when item selection is confirmed, always an array (controlled).
	 */
	onSelectedItemsChange: InternalDispatch<T[]>;

	/**
	 * Expects the 'open' property from the Clay useModal hook.
	 */
	open: boolean;

	/**
	 * Type of item to be selected. Used to display modal title.
	 */
	type: string;
}

function ItemSelectorModal<T extends Record<string, any>>({
	fdsProps,
	itemNameLocator,
	itemValueLocator,
	items: externalItems,
	observer,
	onOpenChange,
	onSelectedItemsChange,
	open,
	type,
}: IItemSelectorModalProps<T>) {
	const [selectedItems, setSelectedItems] = useControlledState({
		defaultName: 'defaultItems',
		defaultValue: undefined,
		handleName: 'onSelectedItemsChange',
		name: 'items',
		onChange: onSelectedItemsChange,
		value: externalItems,
	});

	const getSelectedItemName = function (selectedItem: T) {
		if (typeof itemNameLocator === 'string') {
			return selectedItem[itemNameLocator];
		}
		else {
			return itemNameLocator(selectedItem);
		}
	};

	const externalSelectedItems = useMemo(() => {
		const getSelectedItemValue = function (selectedItem: T) {
			if (typeof itemValueLocator === 'string') {
				return selectedItem[itemValueLocator];
			}
			else {
				return itemValueLocator(selectedItem);
			}
		};

		if (Array.isArray(externalItems)) {
			return externalItems.map(getSelectedItemValue);
		}

		return [getSelectedItemValue(externalItems)];
	}, [externalItems, itemValueLocator]);

	return open ? (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{sub(Liferay.Language.get('select-x'), type)}
			</ClayModal.Header>

			<ClayModal.Body className="p-0">
				<FrontendDataSet
					{...fdsProps}
					onSelect={({
						selectedItems: newSelectedItems,
					}: {
						selectedItems: T[];
					}) => {
						if (
							fdsProps.selectionType === 'single' &&
							newSelectedItems.length > 1
						) {
							setSelectedItems(newSelectedItems.slice(0, 1));
						}
						else {
							setSelectedItems(newSelectedItems);
						}
					}}
					selectedItems={externalSelectedItems}
					style="fluid"
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				className={classNames({
					'bg-primary-l3 border-primary border-top': selectedItems,
				})}
				first={
					selectedItems.length ? (
						<>
							{sub(
								Liferay.Language.get('x-selected'),
								<strong>
									{fdsProps.selectionType === 'single'
										? getSelectedItemName(selectedItems[0])
										: selectedItems.length}
								</strong>
							)}
						</>
					) : undefined
				}
				last={
					<ClayButton.Group spaced>
						<ClayButton
							className="btn-cancel"
							displayType="secondary"
							onClick={() => {
								setSelectedItems([]);

								onOpenChange(false);
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className="item-preview selector-button"
							disabled={selectedItems.length < 1}
							onClick={() => {
								onSelectedItemsChange(
									fdsProps.selectionType === 'single'
										? selectedItems.slice(0, 1)
										: selectedItems
								);

								onOpenChange(false);
							}}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : (
		<></>
	);
}

export default ItemSelectorModal;
