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
	 * Configuration properties of the FDS used to display data
	 */
	fdsProps: IFrontendDataSetProps;

	/**
	 *
	 */
	observer: any;

	/**
	 * Callback function called when after using the selection modal primary button
	 */
	onItemSelectorSave: Function;

	/**
	 *
	 */
	onOpenChange: (value: boolean) => void;

	/**
	 * Flag that controls if modal is open
	 */
	open: boolean;

	/**
	 * Fieldname from apiURL response used to display selection value in the modal
	 */
	selectedItemDescriptionKey: string;

	/**
	 * Type of asset to be selected. Used to display modal title
	 */
	type: string;
}

function ItemSelectorModal({
	fdsProps,
	observer,
	onItemSelectorSave,
	onOpenChange,
	open,
	selectedItemDescriptionKey,
	type,
}: IItemSelectorModalProps) {
	const [selectedItem, setSelectedItem] = useState<any | null>(null);

	return (
		open && (
			<ClayModal observer={observer} size="full-screen">
				<ClayModal.Header>
					{sub(Liferay.Language.get('select-x'), type)}
				</ClayModal.Header>

				<ClayModal.Body>
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
								<b>
									{selectedItem[selectedItemDescriptionKey]}
								</b>{' '}
								{sub(Liferay.Language.get('x-selected'), type)}
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
									onItemSelectorSave(selectedItem);
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
