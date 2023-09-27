/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import React from 'react';

export default function TableHead({
	isAdmin,
	mappedProducts,
	setMappedProducts,
}) {
	let selectedSkusIdCounter = 0;
	let selectableSkusIdCounter = 0;

	mappedProducts.forEach((product) => {
		if (product.selectable) {
			selectableSkusIdCounter++;
		}
		if (product.selected) {
			selectedSkusIdCounter++;
		}
	});

	return (
		<ClayTable.Head>
			<ClayTable.Row>
				{!isAdmin && (
					<ClayTable.Cell headingCell>
						<ClayCheckbox
							checked={
								!!selectedSkusIdCounter &&
								selectedSkusIdCounter ===
									selectableSkusIdCounter
							}
							disabled={!selectableSkusIdCounter}
							indeterminate={
								selectedSkusIdCounter > 0 &&
								selectedSkusIdCounter < selectableSkusIdCounter
							}
							onChange={() => {
								setMappedProducts((products) =>
									products.map((product) => {
										return selectedSkusIdCounter !==
											selectableSkusIdCounter
											? {
													...product,
													selected:
														product.selectable,
											  }
											: {...product, selected: false};
									})
								);
							}}
						/>
					</ClayTable.Cell>
				)}

				<ClayTable.Cell headingCell>#</ClayTable.Cell>

				<ClayTable.Cell className="table-cell-expand-small" headingCell>
					{Liferay.Language.get('sku-or-diagram')}
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					{Liferay.Language.get('quantity')}
				</ClayTable.Cell>

				{isAdmin ? (
					<ClayTable.Cell />
				) : (
					<ClayTable.Cell className="text-right" headingCell>
						{Liferay.Language.get('price')}
					</ClayTable.Cell>
				)}
			</ClayTable.Row>
		</ClayTable.Head>
	);
}
