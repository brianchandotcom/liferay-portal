/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {
	FrontendDataSet,
	IFrontendDataSetProps,
} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

export interface IItemSelectorModalProps<T> {

	/**
	 * Configuration properties of the Frontend Data Set used to display data.
	 */
	fdsProps: IFrontendDataSetProps;

	/**
	 * Fieldname from apiURL response used to display selection value in the modal.
	 */
	itemNameLocator: string | ((item: T) => any);

	/**
	 * Expects the 'observer' property from the Clay useModal hook.
	 */
	observer: any;

	/**
	 * Expects the 'onOpenChange' property from the Clay useModal hook.
	 */
	onOpenChange: (value: boolean) => void;

	/**
	 * Callback function called when item selection is confirmed.
	 */
	onSelection: (item?: T | null) => void;

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
	observer,
	onOpenChange,
	onSelection,
	open,
	type,
}: IItemSelectorModalProps<T>) {
	const [selectedItem, setSelectedItem] = useState<T | null>();

	const getSelectedItemName = function (selectedItem: T) {
		if (typeof itemNameLocator === 'string') {
			return selectedItem[itemNameLocator];
		}
		else {
			return itemNameLocator(selectedItem);
		}
	};

	return open ? (
		<ClayModal observer={observer} size="full-screen">
			<ClayModal.Header>
				{sub(Liferay.Language.get('select-x'), type)}
			</ClayModal.Header>

			<ClayModal.Body className="p-0">
				<FrontendDataSet
					{...fdsProps}
					onSelect={({
						selectedItems,
					}: {
						selectedItems: Array<T> | T;
					}) => {
						fdsProps.selectionType === 'single'
							? setSelectedItem((selectedItems as Array<T>)[0])
							: setSelectedItem(selectedItems as T);
					}}
					style="fluid"
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				className={classNames({
					'bg-primary-l3 border-primary border-top': selectedItem,
				})}
				first={
					selectedItem ? (
						<>
							{sub(
								Liferay.Language.get('x-selected'),
								<strong>
									{getSelectedItemName(selectedItem)}
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
								setSelectedItem(null);

								onOpenChange(false);
							}}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className="item-preview selector-button"
							disabled={!selectedItem}
							onClick={() => {
								onSelection(selectedItem);

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
