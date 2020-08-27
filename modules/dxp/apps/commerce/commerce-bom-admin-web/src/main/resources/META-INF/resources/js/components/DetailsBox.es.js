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

import React from 'react';

import {StoreContext} from './StoreContext.es';

function DetailsListElement(props) {
	const {actions, state} = React.useContext(StoreContext);

	const highlightedModifierClass =
		state.area.highlightedDetail &&
		state.area.highlightedDetail.number === props.number
			? 'table-active'
			: '';

	return (
		<tr
			className={highlightedModifierClass}
			onMouseOut={() => actions.highlightDetail(null)}
			onMouseOver={() => actions.highlightDetail(props.number)}
		>
			<td className="number">{props.number}</td>
			<td className="name">{props.name}</td>
			<td className="sku">{props.sku}</td>
		</tr>
	);
}

export default function DetailsBox() {
	const {state} = React.useContext(StoreContext);

	const spotsFilteredByNumbers = state.area.spots.reduce(
		(filtered, spot, i) => {
			if (i && filtered[filtered.length - 1].number === spot.number) {
				return filtered;
			}

			return filtered.concat(spot);
		},
		[]
	);

	const list = spotsFilteredByNumbers.map((spot) => {
		const relatedProduct = state.area.mappedProducts.reduce(
			(acc, prod) => acc || (prod.id === spot.productId ? prod : false),
			false
		);

		return {
			name: relatedProduct.name,
			number: spot.number,
			sku: relatedProduct.sku,
			url: relatedProduct.url,
		};
	});

	return (
		<div className="h-100 panel panel-secondary">
			<div className="panel-header panel-heading">
				<span className="panel-title">
					{Liferay.Language.get('mapped-products')}
				</span>
			</div>
			<div className="panel-body">
				{list && list.length ? (
					<div className="table-responsive-sm">
						<table className="show-quick-actions-on-hover table table-autofit table-list">
							<thead>
								<tr>
									<th>{Liferay.Language.get('n')}</th>
									<th>{Liferay.Language.get('name')}</th>
									<th>{Liferay.Language.get('sku')}</th>
								</tr>
							</thead>
							<tbody>
								{list &&
									list.map((detail, i) => (
										<DetailsListElement
											key={i}
											{...detail}
										/>
									))}
							</tbody>
						</table>
					</div>
				) : (
					<div className="my-5 p-5 text-center w-100">
						<h3>
							{Liferay.Language.get('no-products-mapped-yet')}
						</h3>
						<p>
							{Liferay.Language.get(
								'click-on-the-picture-to-start-mapping-products'
							)}
						</p>
					</div>
				)}
			</div>
		</div>
	);
}
