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

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import QuantitySelector from 'commerce-frontend-js/components/quantity_selector/QuantitySelector';
import React from 'react';

import Price from '../components/Price';
import {formatLabel} from '../utilities/index';

export default function MappedProductRow({
	handleMouseEnter,
	handleMouseLeave,
	handleTitleClicked,
	isAdmin,
	product,
	quantity,
	selectedSkusId,
	setNewQuantity,
	setSelectedSkusId,
}) {
	const available = product.availability?.label === 'available';

	return (
		<ClayTable.Row
			key={product.id}
			onMouseEnter={() => handleMouseEnter(product)}
			onMouseLeave={() => handleMouseLeave(product)}
		>
			{!isAdmin && (
				<ClayTable.Cell>
					<ClayCheckbox
						checked={selectedSkusId.includes(product.skuId)}
						disabled={
							product.type !== 'sku' ||
							(product.type === 'sku' && !available)
						}
						onChange={(event) => {
							if (event.target.checked) {
								setSelectedSkusId([
									...selectedSkusId,
									product.skuId,
								]);
							}
							else {
								setSelectedSkusId(
									selectedSkusId.filter(
										(selectedSkuId) =>
											selectedSkuId !== product.skuId
									)
								);
							}
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
					</ClayButton>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell className={classNames(isAdmin && 'text-right')}>
				{isAdmin && product.type === 'sku' && product.quantity}

				{!isAdmin &&
					product.productConfiguration &&
					product.type === 'sku' && (
						<QuantitySelector
							{...product.productConfiguration}
							disabled={!available}
							onUpdate={setNewQuantity}
							quantity={quantity}
							size="sm"
						/>
					)}
			</ClayTable.Cell>

			{!isAdmin && (
				<ClayTable.Cell className="text-right">
					{product.price && <Price {...product.price} />}
				</ClayTable.Cell>
			)}
		</ClayTable.Row>
	);
}
