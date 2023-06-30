/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import QuantitySelector from 'commerce-frontend-js/components/quantity_selector/QuantitySelector';
import {sub} from 'frontend-js-web';
import React from 'react';

import Price from '../components/Price';
import {formatLabel} from '../utilities/index';

export default function MappedProductRow({
	handleMouseEnter,
	handleMouseLeave,
	handleTitleClicked,
	isAdmin,
	onDelete,
	product,
	quantity,
	setMappedProducts,
	setNewQuantity,
}) {
	function updateSelected(selected) {
		setMappedProducts((mappedProducts) =>
			mappedProducts.map((mappedProduct) =>
				mappedProduct.skuId === product.skuId
					? {
							...mappedProduct,
							selected,
					  }
					: mappedProduct
			)
		);
	}

	return (
		<ClayTable.Row
			key={product.id}
			onMouseEnter={() => handleMouseEnter(product)}
			onMouseLeave={() => handleMouseLeave(product)}
		>
			{!isAdmin && (
				<ClayTable.Cell>
					<ClayCheckbox
						aria-label={sub(
							Liferay.Language.get('select-sku-x-x'),
							product.sku,
							product.productName[
								Liferay.ThemeDisplay.getLanguageId()
							]
						)}
						checked={!!product.selected}
						disabled={!product.selectable}
						onChange={(event) => {
							updateSelected(event.target.checked);
						}}
					/>
				</ClayTable.Cell>
			)}

			<ClayTable.Cell>{formatLabel(product.sequence)}</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="table-list-title">
					<ClayButton
						displayType="unstyled"
						onClick={() => handleTitleClicked(product)}
					>
						{product.type === 'diagram'
							? product.productName[
									Liferay.ThemeDisplay.getLanguageId()
							  ]
							: product.sku}

						{product.type === 'sku' && (
							<p className="m-0 text-weight-light">
								{
									product.productName[
										Liferay.ThemeDisplay.getLanguageId()
									]
								}
							</p>
						)}
					</ClayButton>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				{isAdmin && product.type !== 'diagram' && product.quantity}

				{!isAdmin &&
					product.productConfiguration &&
					product.type === 'sku' && (
						<QuantitySelector
							allowedQuantities={
								product.productConfiguration
									.allowedOrderQuantities
							}
							disabled={!product.selectable}
							max={product.productConfiguration.maxOrderQuantity}
							min={product.productConfiguration.minOrderQuantity}
							onUpdate={({errors, value}) => {
								setNewQuantity(value);

								if (errors.length) {
									updateSelected(false);
								}
							}}
							quantity={quantity}
							size="sm"
							step={
								product.productConfiguration
									.multipleOrderQuantity
							}
						/>
					)}

				{!isAdmin && product.type === 'external' && product.quantity}
			</ClayTable.Cell>

			{isAdmin ? (
				<ClayTable.Cell>
					<ClayButtonWithIcon
						displayType="secondary"
						onClick={() => onDelete(product.id)}
						small
						symbol="trash"
					/>
				</ClayTable.Cell>
			) : (
				<ClayTable.Cell className="text-right">
					{product.price && <Price {...product.price} />}
				</ClayTable.Cell>
			)}
		</ClayTable.Row>
	);
}
