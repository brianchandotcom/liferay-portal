/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {InternalDispatch} from '@clayui/shared';
import {
	FrontendDataSet,
	IFrontendDataSetProps,
} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {getObjectValueFromPath, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export interface IItemSelectorModalProps<T> {

	/**
	 * Configuration properties of the Frontend Data Set used to display data.
	 */
	fdsProps: Omit<IFrontendDataSetProps, 'selectedItems' | 'selectedItemsKey'>;

	/**
	 * Items that are currently selected (controlled).
	 */
	items: T[];

	/**
	 * A string key used to locate the id, label, or value within each item.
	 * Can be used as a period separated path (e.g.: 'embedded.id').
	 */
	locator?: {
		id: string;
		label: string;
		value: string;
	};

	/**
	 * Flag for if multiple items can be selected.
	 */
	multiSelect?: boolean;

	/**
	 * Expects the 'observer' property from the Clay useModal hook.
	 */
	observer: any;

	/**
	 * Callback for when items are added or removed. Only called when modal selection is confirmed (controlled).
	 */
	onItemsChange: InternalDispatch<T[]>;

	/**
	 * Expects the 'onOpenChange' property from the Clay useModal hook.
	 */
	onOpenChange: (value: boolean) => void;

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
	items: externalItems,
	locator = {
		id: 'id',
		label: 'title',
		value: 'id',
	},
	multiSelect = false,
	observer,
	onItemsChange,
	onOpenChange,
	open,
	type,
}: IItemSelectorModalProps<T>) {
	const [selectedItems, setSelectedItems] = useState(externalItems);

	useEffect(() => {
		if (!open) {
			setSelectedItems(externalItems);
		}
	}, [externalItems, open]);

	const getSelectedItemLabel = function (selectedItem: T) {
		return getObjectValueFromPath({
			object: selectedItem,
			path: locator.label,
		});
	};

	const hasSelectedItems = !!selectedItems.length;

	return open ? (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{sub(Liferay.Language.get('select-x'), type)}
			</ClayModal.Header>

			<ClayModal.Body className="p-0">
				<FrontendDataSet
					{...fdsProps}
					onSelectedItemsChange={setSelectedItems}
					selectedItems={selectedItems}
					selectedItemsKey={locator.value}
					selectionType={multiSelect ? 'multiple' : 'single'}
					showNavBarWhenSelected={true}
					style="fluid"
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				className={classNames({
					'bg-primary-l3 border-primary border-top': hasSelectedItems,
				})}
				first={
					hasSelectedItems ? (
						<div className="align-items-center d-flex">
							{selectedItems.length > 1
								? sub(
										Liferay.Language.get(
											'x-items-selected'
										),
										selectedItems.length
									)
								: sub(
										Liferay.Language.get('x-selected'),
										getSelectedItemLabel(selectedItems[0])
									)}

							<ClayButton
								className="ml-3 text-secondary"
								displayType="link"
								onClick={() => {
									setSelectedItems([]);
								}}
							>
								<strong>{Liferay.Language.get('clear')}</strong>
							</ClayButton>
						</div>
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
							disabled={!hasSelectedItems}
							onClick={() => {
								onItemsChange(
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
