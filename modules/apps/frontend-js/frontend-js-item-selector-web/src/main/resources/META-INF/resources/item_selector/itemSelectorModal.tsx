/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {FrontendDataSet, TView} from '@liferay/frontend-data-set-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import getDefaultItemSelectorModalViews, {
	EItemSelectorModalViewsConfig,
} from '../utils/getDefaultItemSelectorModalView';
import getRandomId from '../utils/getRandomId';

export interface IItemSelectorModalProps {

	/**
	 * The URL that will be fetched to return the items.
	 */
	apiURL: string;

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
	 * Collection of items to be selected when opening the item selector
	 */
	selectedItems?: Array<any>;

	/**
	 * Fielname from apiURL response used to select items. Defaults to 'id'
	 */
	selectedItemsKey?: string;

	/**
	 * Type of selecton allowed. Defaults to'single'
	 */
	selectionType?: 'single' | 'multiple';

	/**
	 * Type of asset to be selected. Used to display modal title
	 */
	type: string;

	/**
	 * Collection of views used by the FDS component to display data fetched from apiURL
	 */
	viewsConfig?: `${EItemSelectorModalViewsConfig}` | TView[];
}

function ItemSelectorModal({
	apiURL,
	observer,
	onItemSelectorSave,
	open,
	onOpenChange,
	selectedItemDescriptionKey,
	selectedItems,
	selectedItemsKey = 'id',
	selectionType = 'single',
	type,
	viewsConfig = EItemSelectorModalViewsConfig.DOCUMENTS_AND_MEDIA,
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
						apiURL={apiURL}
						id={`itemSelectorModal-${getRandomId()}`}
						onSelect={({
							selectedItems,
						}: {
							selectedItems: Array<any>;
						}) => {
							selectionType === 'single'
								? setSelectedItem(selectedItems[0])
								: setSelectedItem(selectedItems);
						}}
						pagination={{
							deltas: [{label: 20}, {label: 40}, {label: 60}],
							initialDelta: 20,
						}}
						selectedItems={selectedItems}
						selectedItemsKey={selectedItemsKey}
						selectionType={selectionType}
						views={getDefaultItemSelectorModalViews({viewsConfig})}
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
