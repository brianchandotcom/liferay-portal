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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import AJAX from '../../utilities/AJAX/index';
import {DATASET_DISPLAY_UPDATED} from '../../utilities/eventsDefinitions';

function SummaryItemDividerVariant() {
	return (
		<div className="col-12">
			<hr />
		</div>
	);
}

const baseItemDefaultProps = {
	label: PropTypes.string,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
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
				<h4 className="my-2 summary-table-item-big">{props.label}</h4>
			</div>
			<div className="col-6 col-md-3">
				<h4 className="my-2 summary-table-item-big">{props.value}</h4>
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
	style: PropTypes.string,
};

function Summary({
	apiUrl,
	dataMapper,
	datasetDisplayId,
	isLoading,
	items = [],
	summaryData,
}) {
	const [summaryItems, updateSummaryItems] = useState(items);

	const mapDataToLayout = useCallback(
		(data) => (typeof dataMapper === 'function' ? dataMapper(data) : data),
		[dataMapper]
	);

	const refreshData = useCallback(
		({id = null}) => {
			if (!id || datasetDisplayId !== id) {
				return AJAX.GET(apiUrl).then((data) =>
					updateSummaryItems(mapDataToLayout(data))
				);
			}
		},
		[apiUrl, datasetDisplayId, mapDataToLayout]
	);

	useEffect(() => {
		if (!!apiUrl && !!datasetDisplayId) {
			Liferay.on(DATASET_DISPLAY_UPDATED, refreshData);

			refreshData({});

			return () => Liferay.detach(DATASET_DISPLAY_UPDATED, refreshData);
		}

		return () => {};
	}, [apiUrl, datasetDisplayId, refreshData]);

	useEffect(() => {
		if (!!summaryData && Object.keys(summaryData).length > 0) {
			updateSummaryItems(mapDataToLayout(summaryData));
		}

		return () => {};
	}, [mapDataToLayout, summaryData]);

	return (
		<div className="row summary-table text-right">
			{summaryItems.map((item, i) => (
				<SummaryItem key={i} {...item} />
			))}

			{isLoading && (
				<div className="summary-table-loader">
					<ClayLoadingIndicator />
				</div>
			)}
		</div>
	);
}

Summary.defaultProps = {
	dataMapper: (jsonData) => {
		return [
			{
				label: Liferay.Language.get('subtotal'),
				value: jsonData.subtotalFormatted,
			},
			{
				label: Liferay.Language.get('subtotal-discount'),
				value: jsonData.subtotalDiscountAmountFormatted,
			},
			{
				label: Liferay.Language.get('total-discount'),
				value: jsonData.totalDiscountAmountFormatted,
			},
			{
				label: Liferay.Language.get('promotion-code'),
				value: jsonData.couponCode || '--',
			},
			{
				label: Liferay.Language.get('tax'),
				value: jsonData.taxAmountFormatted,
			},
			{
				label: Liferay.Language.get('delivery'),
				value: jsonData.shippingAmountFormatted,
			},
			{
				label: Liferay.Language.get('delivery-discount'),
				value: jsonData.shippingDiscountAmountFormatted,
			},
			{
				style: 'divider',
			},
			{
				label: Liferay.Language.get('total'),
				style: 'big',
				value: jsonData.totalFormatted,
			},
		];
	},
	isLoading: false,
};

Summary.propTypes = {
	apiUrl: PropTypes.string,
	dataMapper: PropTypes.func,
	datasetDisplayId: PropTypes.string,
	isLoading: PropTypes.bool,
	items: PropTypes.array,
	summaryData: PropTypes.oneOfType([PropTypes.array, PropTypes.object]),
};

export default Summary;
