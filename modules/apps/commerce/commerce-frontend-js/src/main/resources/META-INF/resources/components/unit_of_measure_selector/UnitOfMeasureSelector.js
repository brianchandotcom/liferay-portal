/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import {useLiferayState} from '@liferay/frontend-js-state-web';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {forwardRef, useCallback, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import skuOptionsAtom from '../../utilities/atoms/skuOptionsAtom';
import {
	CP_INSTANCE_CHANGED,
	CP_UNIT_OF_MEASURE_SELECTOR_CHANGED,
} from '../../utilities/eventsDefinitions';
import {getMinQuantity} from '../../utilities/quantities';

const UnitOfMeasureSelector = forwardRef(
	({
		accountId,
		channelId,
		cpInstanceId,
		disabled,
		name,
		namespace,
		productConfiguration,
		productId,
		size,
	}) => {
		const [inputProperties, setInputProperties] = useState({
			fireEvent: false,
			quantity: getMinQuantity(productConfiguration?.minOrderQuantity, 1),
			resetQuantity: false,
			unitOfMeasures: [],
			value: null,
		});
		const [skuId, setSkuId] = useState(cpInstanceId);
		const [skuOptionsAtomState] = useLiferayState(skuOptionsAtom);

		const DeliveryCatalogAPIServiceProvider = ServiceProvider.DeliveryCatalogAPI(
			'v1'
		);

		const postChannelProductSkuBySkuOption = useCallback(
			(skuUnitOfMeasureKey) => {
				DeliveryCatalogAPIServiceProvider.postChannelProductSkuBySkuOption(
					channelId,
					productId,
					accountId,
					inputProperties.quantity,
					skuUnitOfMeasureKey,
					skuOptionsAtomState.skuOptions
				).then((cpInstance) => {
					cpInstance.skuOptions = skuOptionsAtomState.skuOptions;
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
			[skuOptionsAtomState]
		);

		useEffect(() => {
			if (cpInstanceId) {
				DeliveryCatalogAPIServiceProvider.getChannelProductSku(
					channelId,
					productId,
					cpInstanceId,
					accountId
				).then((cpInstance) => {
					const skuUnitOfMeasures =
						cpInstance.skuUnitOfMeasures || [];

					setInputProperties((inputProperties) => ({
						...inputProperties,
						fireEvent: true,
						quantity: getMinQuantity(
							productConfiguration?.minOrderQuantity,
							skuUnitOfMeasures[0]?.incrementalOrderQuantity || 1,
							skuUnitOfMeasures[0]?.precision || 0
						),
						resetQuantity: true,
						unitOfMeasures: skuUnitOfMeasures,
						value: skuUnitOfMeasures[0]?.key || '',
					}));

					if (skuUnitOfMeasures[0]?.key) {
						postChannelProductSkuBySkuOption(
							skuUnitOfMeasures[0]?.key
						);
					}
				});
			}
			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

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
		}, [handleCPInstanceChanged, namespace]);

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

						postChannelProductSkuBySkuOption(target.value);
					}}
					options={inputProperties.unitOfMeasures.map(
						(unitOfMeasure) => ({
							label: unitOfMeasure.name,
							value: unitOfMeasure.key,
						})
					)}
					value={inputProperties.value}
				/>
			)
		);
	}
);

UnitOfMeasureSelector.defaultProps = {
	disabled: false,
	size: 'lg',
};

UnitOfMeasureSelector.propTypes = {
	accountId: PropTypes.number,
	channelId: PropTypes.number.isRequired,
	cpInstanceId: PropTypes.number.isRequired,
	disabled: PropTypes.bool,
	name: PropTypes.string,
	namespace: PropTypes.string,
	productConfiguration: PropTypes.shape({
		allowedOrderQuantities: PropTypes.arrayOf(PropTypes.number),
		maxOrderQuantity: PropTypes.number,
		minOrderQuantity: PropTypes.number,
		multipleOrderQuantity: PropTypes.number,
	}),
	productId: PropTypes.number.isRequired,
	size: PropTypes.oneOf(['lg', 'md', 'sm']),
};

export default UnitOfMeasureSelector;
