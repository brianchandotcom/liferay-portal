/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {CP_INSTANCE_CHANGED} from '../../utilities/eventsDefinitions';

import './tier_price.scss';

function TierPrice({
	accountId,
	alwaysVisible,
	autoload,
	channelId,
	cpInstanceId,
	label,
	namespace,
	productId,
}) {
	const [columns, setColumns] = useState([]);
	const [rows, setRows] = useState([]);
	const [isExpanded, setIsExpanded] = useState(false);
	const cpInstanceIdRef = useRef();
	const DeliveryCatalogAPIServiceProviderRef = useRef(
		ServiceProvider.DeliveryCatalogAPI('v1')
	);

	const handleCPInstanceChanged = ({cpInstance}) => {
		if (cpInstanceIdRef.current === cpInstance.id) {
			return;
		}

		cpInstanceIdRef.current = cpInstance.id;

		const rows = [];
		const skuUnitOfMeasures = cpInstance.skuUnitOfMeasures || [];

		if (skuUnitOfMeasures.length) {
			const cols = [
				{
					classes: 'text-weight-semi-bold',
					key: 'unit',
					label: Liferay.Language.get('unit'),
				},
				{
					key: 'key',
					label: Liferay.Language.get('key'),
				},
				{
					key: 'quantity',
					label: Liferay.Language.get('quantity'),
				},
				{
					classes: 'price-col text-weight-semi-bold',
					key: 'price',
					label: Liferay.Language.get('net-price'),
				},
			];

			const havePricingQuantity = skuUnitOfMeasures.find(
				(unitOfMeasure) => {
					return unitOfMeasure.price?.pricingQuantityPriceFormatted;
				}
			);

			if (havePricingQuantity) {
				cols.push({
					classes: 'price-col text-weight-semi-bold',
					key: 'pricingQuantity',
					label:
						Liferay.Language.get('price') +
						' / ' +
						Liferay.Language.get('quantity'),
				});
			}

			setColumns(cols);

			for (const unitOfMeasure of skuUnitOfMeasures) {
				const priceOnApplication =
					unitOfMeasure.price?.priceOnApplication || false;

				if (priceOnApplication) {
					unitOfMeasure.price.priceFormatted = Liferay.Language.get(
						'price-on-application'
					);
				}

				rows.push({
					classes: priceOnApplication ? 'price-on-application' : '',
					key: unitOfMeasure.key,
					price: unitOfMeasure.price?.priceFormatted || '',
					pricingQuantity:
						unitOfMeasure.price?.pricingQuantityPriceFormatted ||
						unitOfMeasure.price?.priceFormatted +
							' / ' +
							(unitOfMeasure.incrementalOrderQuantity !== 1
								? unitOfMeasure.incrementalOrderQuantity + ' '
								: '') +
							unitOfMeasure.name,
					quantity: unitOfMeasure.incrementalOrderQuantity,
					unit: unitOfMeasure.name,
				});

				const tierPrices = unitOfMeasure.tierPrices || [];

				for (const tierPrice of tierPrices) {
					rows.push({
						classes: '',
						key: unitOfMeasure.key,
						price: tierPrice.priceFormatted,
						pricingQuantity:
							tierPrice.pricingQuantityPriceFormatted ||
							tierPrice.priceFormatted +
								' / ' +
								(unitOfMeasure.incrementalOrderQuantity !== 1
									? unitOfMeasure.incrementalOrderQuantity +
										' '
									: '') +
								unitOfMeasure.name,
						quantity: tierPrice.quantity,
						unit: unitOfMeasure.name,
					});
				}
			}

			setRows(rows);
		}
		else {
			setColumns([
				{
					key: 'quantity',
					label: Liferay.Language.get('quantity'),
				},
				{
					classes: 'price-col text-weight-semi-bold',
					key: 'price',
					label: Liferay.Language.get('net-price'),
				},
			]);

			const priceOnApplication =
				cpInstance.price?.priceOnApplication || false;

			if (priceOnApplication) {
				cpInstance.price.priceFormatted = Liferay.Language.get(
					'price-on-application'
				);
			}

			rows.push({
				classes: priceOnApplication ? 'price-on-application' : '',
				price: cpInstance.price?.priceFormatted,
				quantity: 1,
			});

			const tierPrices = cpInstance.tierPrices || [];

			for (const tierPrice of tierPrices) {
				rows.push({
					classes: '',
					price: tierPrice.priceFormatted,
					quantity: tierPrice.quantity,
				});
			}

			setRows(rows);
		}
	};

	useEffect(() => {
		if (autoload && cpInstanceId) {
			DeliveryCatalogAPIServiceProviderRef.current
				.getChannelProductSku(
					channelId,
					productId,
					cpInstanceId,
					accountId
				)
				.then((cpInstance) => {
					handleCPInstanceChanged({cpInstance});
				});
		}
	}, [accountId, autoload, channelId, cpInstanceId, productId]);

	useEffect(() => {
		Liferay.on(
			`${namespace}${CP_INSTANCE_CHANGED}`,
			handleCPInstanceChanged
		);

		return () => {
			Liferay.detach(
				`${namespace}${CP_INSTANCE_CHANGED}`,
				handleCPInstanceChanged
			);
		};
	}, [namespace]);

	return (
		<>
			{alwaysVisible || rows.length > 1 ? (
				<>
					{label && <label>{label}</label>}

					<div
						className={classNames('table-container', {
							expanded: isExpanded,
						})}
					>
						<ClayTable className="table-bordered">
							<ClayTable.Head>
								<ClayTable.Row>
									{columns.map((column, colIndex) => {
										return (
											<ClayTable.Cell
												headingCell
												key={`column-${colIndex}`}
											>
												<span>{column.label}</span>
											</ClayTable.Cell>
										);
									})}
								</ClayTable.Row>
							</ClayTable.Head>

							<ClayTable.Body>
								{rows.map((row, rowIndex) => {
									return (
										<ClayTable.Row key={`row-${rowIndex}`}>
											{columns.map((column, colIndex) => {
												return (
													<ClayTable.Cell
														className={classNames(
															column.classes,
															row.classes,
															{
																'text-nowrap': true,
															}
														)}
														key={`cell-${rowIndex}-${colIndex}`}
													>
														{row[column.key]}
													</ClayTable.Cell>
												);
											})}
										</ClayTable.Row>
									);
								})}
							</ClayTable.Body>
						</ClayTable>

						{rows.length > 5 ? (
							<div
								className="paginator"
								onClick={() => {
									setIsExpanded((prevState) => {
										return !prevState;
									});
								}}
							>
								{isExpanded
									? Liferay.Language.get('view-less')
									: Liferay.Language.get('view-more')}
							</div>
						) : (
							<></>
						)}
					</div>
				</>
			) : (
				<></>
			)}
		</>
	);
}

TierPrice.defaultProps = {
	alwaysVisible: false,
	autoload: true,
};

TierPrice.propTypes = {
	accountId: PropTypes.number,
	alwaysVisible: PropTypes.bool,
	autoload: PropTypes.bool,
	channelId: PropTypes.number.isRequired,
	cpInstanceId: PropTypes.number.isRequired,
	label: PropTypes.string,
	namespace: PropTypes.string,
	productId: PropTypes.number.isRequired,
};

export default TierPrice;
