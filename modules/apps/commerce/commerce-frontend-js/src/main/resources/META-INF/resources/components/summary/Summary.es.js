/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import PropTypes from 'prop-types';
import React, {useState, useEffect} from 'react';

import {DATASET_DISPLAY_UPDATED} from '../../utilities/eventsDefinitions.es';
import {fetchParams} from '../../utilities/index.es';

function SummaryItemDividerVariant() {
	return (
		<div className="col-12">
			<hr />
		</div>
	);
}

const baseItemDefaultProps = {
	label: PropTypes.string,
	value: PropTypes.string
};

function SummaryItemBase(props) {
	return (
		<>
			<div className="col-6 col-md-9">
				<span className="summary-table-item">{props.label}</span>
			</div>
			<div className="col-6 col-md-3">
				<span className="summary-table-item">{props.value}</span>
			</div>
		</>
	);
}

SummaryItemBase.propTypes = baseItemDefaultProps;

function SummaryItemBigVariant(props) {
	return (
		<>
			<div className="col-6 col-md-9">
				<h4 className="summary-table-item-big my-2">{props.label}</h4>
			</div>
			<div className="col-6 col-md-3">
				<h4 className="summary-table-item-big my-2">{props.value}</h4>
			</div>
		</>
	);
}

SummaryItemBigVariant.propTypes = baseItemDefaultProps;

function SummaryItemDangerVariant(props) {
	return (
		<>
			<div className="col-6 col-md-9 text-danger">
				<span className="summary-table-item">{props.label}</span>
			</div>
			<div className="col-6 col-md-3 text-danger">
				<span className="summary-table-item">{props.value}</span>
			</div>
		</>
	);
}

SummaryItemDangerVariant.propTypes = baseItemDefaultProps;

function SummaryItem(props) {
	const {style, ...itemProps} = props;

	let ItemVariant;

	switch (style) {
		case 'big':
			ItemVariant = SummaryItemBigVariant;
			break;
		case 'divider':
			ItemVariant = SummaryItemDividerVariant;
			break;
		case 'danger':
			ItemVariant = SummaryItemDangerVariant;
			break;
		default:
			ItemVariant = SummaryItemBase;
			break;
	}

	return <ItemVariant {...itemProps} />;
}

SummaryItem.propTypes = {
	style: PropTypes.string
};

function Summary(props) {
	const [items, updateItems] = useState(props.items);

	useEffect(() => {
		function getData() {
			fetch(props.apiUrl, {
				...fetchParams,
				method: 'GET'
			})
				.then(data => data.json())
				.then(
					data => (props.dataMapper && props.dataMapper(data)) || data
				)
				.then(updateItems);
		}

		function refreshItems(payload) {
			if (
				!props.datasetDisplayId ||
				!props.apiUrl ||
				payload.id !== props.datasetDisplayId
			) {
				return;
			}
			return getData();
		}

		getData();
		Liferay.on(DATASET_DISPLAY_UPDATED, refreshItems);
		return () => Liferay.detach(DATASET_DISPLAY_UPDATED, refreshItems);
	}, [props.dataMapper, props.apiUrl, props.datasetDisplayId, props]);

	return (
		<div className="row summary-table text-right">
			{items.map((item, i) => (
				<SummaryItem key={i} {...item} />
			))}
		</div>
	);
}

Summary.propTypes = {
	apiUrl: PropTypes.string,
	dataMapper: PropTypes.func,
	datasetDisplayId: PropTypes.string,
	items: PropTypes.array.isRequired
};

Summary.defaultProps = {
	dataMapper: jsonData => {
		const values = [
			{
				label: Liferay.Language.get('subtotal'),
				value: jsonData.subtotalFormatted
			},
			{
				label: Liferay.Language.get('line-item-discount'),
				value: jsonData.subtotalDiscountAmountFormatted
			},
			{
				label: Liferay.Language.get('order-discount'),
				value: jsonData.totalDiscountAmountFormatted
			},
			{
				label: Liferay.Language.get('promotion-code'),
				value: jsonData.couponCode || '--'
			},
			{
				label: Liferay.Language.get('tax'),
				value: jsonData.taxAmountFormatted
			},
			{
				label: Liferay.Language.get('shipping-and-handling'),
				value: jsonData.shippingAmountFormatted
			},
			{
				label: Liferay.Language.get('shipping-and-handling-discount'),
				value: jsonData.shippingDiscountAmountFormatted
			},
			{
				style: 'divider'
			},
			{
				label: Liferay.Language.get('grand-total'),
				style: 'big',
				value: jsonData.totalFormatted
			}
		];
		return values;
	},
	items: []
};

export default Summary;
