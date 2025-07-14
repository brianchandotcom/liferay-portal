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
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

export interface IItemSelectorModalProps {

	/**
	 * Configuration properties of the FDS used to display data.
	 */
	fdsProps: IFrontendDataSetProps;

	/**
	 * Fieldname from apiURL response used to display selection value in the modal.
	 */
	itemNameLocator: string | ((item: any) => any);

	/**
	 *
	 */
	observer: any;

	/**
	 * Callback to change open state.
	 */
	onOpenChange: (value: boolean) => void;

	/**
	 * Callback function called when item selection is confirmed.
	 */
	onSelection: Function;

	/**
	 * Flag that controls if modal is open.
	 */
	open: boolean;

	/**
	 * Type of asset to be selected. Used to display modal title.
	 */
	type: string;
}

function ItemSelectorModal({
	fdsProps,
	itemNameLocator,
	observer,
	onOpenChange,
	onSelection,
	open,
	type,
}: IItemSelectorModalProps) {
	const [selectedItem, setSelectedItem] = useState<any | null>(null);

	const getSelectedItemValue = function (selectedItem: any) {
		if (typeof itemNameLocator === 'string') {
			return selectedItem[itemNameLocator];
		}
		else {
			return itemNameLocator(selectedItem);
		}
	};

	return (
		open && (
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
							selectedItems: Array<any>;
						}) => {
							fdsProps.selectionType === 'single'
								? setSelectedItem(selectedItems[0])
								: setSelectedItem(selectedItems);
						}}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					first={
						selectedItem ? (
							<>
								<strong>
									{getSelectedItemValue(selectedItem)}
								</strong>{' '}
								{Liferay.Language.get('selected')}
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
		)
	);
}

export default ItemSelectorModal;
