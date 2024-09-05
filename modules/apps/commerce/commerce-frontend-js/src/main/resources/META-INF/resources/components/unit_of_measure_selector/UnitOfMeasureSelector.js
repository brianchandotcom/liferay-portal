/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import {useLiferayState} from '@liferay/frontend-js-state-web';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import skuOptionsAtom from '../../utilities/atoms/skuOptionsAtom';
import {
	CP_INSTANCE_CHANGED,
	CP_QUANTITY_SELECTOR_CHANGED,
	CP_UNIT_OF_MEASURE_SELECTOR_CHANGED,
} from '../../utilities/eventsDefinitions';
import {getMinQuantity} from '../../utilities/quantities';
import Asterisk from '../product_options/Asterisk';

import './unit_of_measure_selector.scss';

function UnitOfMeasureSelector({
	accountId,
	channelId,
	cpInstanceId,
	disabled,
	label,
	loadFinalPrice,
	name,
	namespace,
	options,
	panelLabel,
	productConfiguration,
	productId,
	resetQuantity,
	size,
	useQuantity,
	value,
}) {
	const [inputProperties, setInputProperties] = useState({
		fireEvent: false,
		quantity: getMinQuantity(productConfiguration?.minOrderQuantity, 1),
		resetQuantity,
		unitOfMeasures: [],
		value,
	});
	const [skuId, setSkuId] = useState(cpInstanceId);
	const [skuOptionsAtomState] = useLiferayState(skuOptionsAtom);

	const DeliveryCatalogAPIServiceProvider =
		ServiceProvider.DeliveryCatalogAPI('v1');

	const postChannelProductSkuBySkuOption = useCallback(
		(quantity = 1, skuUnitOfMeasureKey) => {
			DeliveryCatalogAPIServiceProvider.postChannelProductSkuBySkuOption(
				channelId,
				productId,
				accountId,
				quantity,
				skuUnitOfMeasureKey,
				options || skuOptionsAtomState.skuOptions
			).then((cpInstance) => {
				cpInstance.skuOptions =
					options || skuOptionsAtomState.skuOptions;
				cpInstance.skuId = parseInt(cpInstance.id, 10);

				const dispatchedPayload = {
					cpInstance,
					namespace,
				};

				Liferay.fire(
					`${namespace}${CP_INSTANCE_CHANGED}`,
					dispatchedPayload
				);
			});
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[accountId, channelId, options, productId, skuOptionsAtomState]
	);

	useEffect(() => {
		if (channelId && cpInstanceId && productId) {
			DeliveryCatalogAPIServiceProvider.getChannelProductSku(
				channelId,
				productId,
				cpInstanceId,
				accountId
			).then((cpInstance) => {
				const skuUnitOfMeasures = cpInstance.skuUnitOfMeasures || [];

				let skuUnitOfMeasure = skuUnitOfMeasures[0];

				if (inputProperties.value) {
					skuUnitOfMeasure = skuUnitOfMeasures.find(
						(skuUnitOfMeasure) =>
							inputProperties.value === skuUnitOfMeasure.key
					);

					if (!skuUnitOfMeasure) {
						skuUnitOfMeasure = skuUnitOfMeasures[0];
					}
				}

				const quantity = getMinQuantity(
					productConfiguration?.minOrderQuantity,
					skuUnitOfMeasure?.incrementalOrderQuantity || 1,
					skuUnitOfMeasure?.precision || 0
				);

				setInputProperties((inputProperties) => ({
					...inputProperties,
					fireEvent: true,
					quantity,
					resetQuantity,
					unitOfMeasures: skuUnitOfMeasures,
					value: skuUnitOfMeasure?.key || '',
				}));

				if (skuUnitOfMeasure?.key) {
					postChannelProductSkuBySkuOption(
						(useQuantity
							? quantity
							: skuUnitOfMeasure?.incrementalOrderQuantity) || 1,
						skuUnitOfMeasure?.key
					);
				}
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountId, channelId, cpInstanceId, resetQuantity, productId]);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const handleCPInstanceChanged = ({cpInstance}) => {
		if (cpInstance.id === skuId) {
			return;
		}

		const skuUnitOfMeasures = cpInstance.skuUnitOfMeasures || [];

		setInputProperties((inputProperties) => ({
			...inputProperties,
			fireEvent: true,
			unitOfMeasures: skuUnitOfMeasures,
			value: skuUnitOfMeasures[0]?.key || '',
		}));

		setSkuId(cpInstance.id);
	};

	useEffect(() => {
		const handleCPQuantitySelectorChanged = function ({quantity}) {
			setInputProperties((inputProperties) => ({
				...inputProperties,
				quantity,
			}));
		};

		Liferay.on(
			`${namespace}${CP_INSTANCE_CHANGED}`,
			handleCPInstanceChanged
		);

		if (loadFinalPrice) {
			Liferay.on(
				`${namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
				handleCPQuantitySelectorChanged
			);
		}

		return () => {
			Liferay.detach(
				`${namespace}${CP_INSTANCE_CHANGED}`,
				handleCPInstanceChanged
			);

			if (loadFinalPrice) {
				Liferay.detach(
					`${namespace}${CP_QUANTITY_SELECTOR_CHANGED}`,
					handleCPQuantitySelectorChanged
				);
			}
		};
	}, [loadFinalPrice, handleCPInstanceChanged, namespace]);

	const fireSelectorChangedEvent = useCallback(() => {
		Liferay.fire(`${namespace}${CP_UNIT_OF_MEASURE_SELECTOR_CHANGED}`, {
			resetQuantity: inputProperties.resetQuantity,
			unitOfMeasure: inputProperties.unitOfMeasures.find(
				(unitOfMeasure) => {
					return unitOfMeasure.key === inputProperties.value;
				}
			),
		});
		setInputProperties((inputProperties) => ({
			...inputProperties,
			fireEvent: false,
			resetQuantity: false,
		}));
	}, [inputProperties, namespace]);

	useEffect(() => {
		if (inputProperties.fireEvent) {
			fireSelectorChangedEvent();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [inputProperties.fireEvent]);

	return (
		!!inputProperties.unitOfMeasures.length && (
			<>
				{panelLabel && (
					<div className="panel panel-unstyled">
						<div className="panel-header">
							<span className="panel-title">{panelLabel}</span>
						</div>
					</div>
				)}

				{label && (
					<label htmlFor="minicart-uom-selector">
						{label}

						<Asterisk required={true} />
					</label>
				)}

				<ClaySelectWithOption
					className={classnames({
						[`form-control-${size}`]: size,
						'ml-3': true,
						'unit-of-measure-selector': true,
					})}
					disabled={
						disabled || inputProperties.unitOfMeasures.length <= 1
					}
					name={name}
					onChange={({target}) => {
						setInputProperties((inputProperties) => ({
							...inputProperties,
							fireEvent: true,
							value: target.value,
						}));

						const selectedUnitOfMeasure =
							inputProperties.unitOfMeasures.find(
								(unitOfMeasure) => {
									return unitOfMeasure.key === target.value;
								}
							);

						postChannelProductSkuBySkuOption(
							(useQuantity
								? getMinQuantity(
										productConfiguration?.minOrderQuantity,
										selectedUnitOfMeasure?.incrementalOrderQuantity ||
											1,
										selectedUnitOfMeasure?.precision || 0
									)
								: selectedUnitOfMeasure?.incrementalOrderQuantity) ||
								1,
							target.value
						);
					}}
					options={inputProperties.unitOfMeasures.map(
						(unitOfMeasure) => ({
							label: unitOfMeasure.name,
							value: unitOfMeasure.key,
						})
					)}
					value={inputProperties.value}
				/>
			</>
		)
	);
}

UnitOfMeasureSelector.defaultProps = {
	disabled: false,
	loadFinalPrice: false,
	resetQuantity: true,
	size: 'lg',
	useQuantity: false,
};

UnitOfMeasureSelector.propTypes = {
	accountId: PropTypes.number,
	channelId: PropTypes.number.isRequired,
	cpInstanceId: PropTypes.number.isRequired,
	disabled: PropTypes.bool,
	label: PropTypes.string,
	loadFinalPrice: PropTypes.bool,
	name: PropTypes.string,
	namespace: PropTypes.string,
	panelLabel: PropTypes.string,
	productConfiguration: PropTypes.shape({
		allowedOrderQuantities: PropTypes.arrayOf(PropTypes.number),
		maxOrderQuantity: PropTypes.number,
		minOrderQuantity: PropTypes.number,
		multipleOrderQuantity: PropTypes.number,
	}),
	productId: PropTypes.number.isRequired,
	resetQuantity: PropTypes.bool,
	size: PropTypes.oneOf(['lg', 'md', 'sm']),
	useQuantity: PropTypes.bool,
	value: PropTypes.string,
};

export default UnitOfMeasureSelector;
