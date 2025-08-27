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
import React, {useEffect, useMemo, useState} from 'react';

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

	const selectedData = useMemo(() => {
		if (fdsProps.selectionType === 'single') {
			if (!selectedItems.length) {
				return {label: '', values: []};
			}

			return {
				label: getObjectValueFromPath({
					object: selectedItems[0],
					path: locator.label,
				}),
				values: [
					getObjectValueFromPath({
						object: selectedItems[0],
						path: locator.value,
					}),
				],
			};
		}

		return {
			label: selectedItems.length.toString(),
			values: selectedItems.map((item) => {
				return getObjectValueFromPath({
					object: item,
					path: locator.value,
				});
			}),
		};
	}, [fdsProps.selectionType, selectedItems, locator]);

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
					selectedItems={selectedData.values}
					selectedItemsKey={locator.id}
					style="fluid"
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				className={classNames({
					'bg-primary-l3 border-primary border-top':
						!!selectedData.values.length,
				})}
				first={
					selectedData.values.length ? (
						<>
							{sub(
								Liferay.Language.get('x-selected'),
								<strong>{selectedData.label}</strong>
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
								onOpenChange(false);
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className="item-preview selector-button"
							disabled={selectedData.values.length < 1}
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
