/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useLiferayState} from '@liferay/frontend-js-state-web';
import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import skuOptionsAtom from 'commerce-frontend-js/utilities/atoms/skuOptionsAtom';
import {CP_INSTANCE_CHANGED} from 'commerce-frontend-js/utilities/eventsDefinitions';
import React, {useEffect, useState} from 'react';

import Asterisk from './Asterisk';
import {
	getInitialProductOptionValue,
	getName,
	getProductOptionName,
	getSkuOptionsErrors,
	initialSkuOptionsAtomState,
	isRequired,
} from './utils';

const ProductOptionRadio = ({
	accountId,
	channelId,
	componentId,
	forceRequired,
	isAdmin,
	json,
	minQuantity,
	namespace,
	productId,
	productOption,
	sku,
}) => {
	const isMounted = useIsMounted();

	const [skuOptionsAtomState, setSkuOptionsAtomState] = useLiferayState(
		skuOptionsAtom
	);

	const [productOptionValues, setProductOptionValues] = useState(
		productOption.productOptionValues
	);

	const currentJSONObject = json
		? JSON.parse(json).filter(
				(jsonObject) => jsonObject.key === productOption.key
		  )[0]
		: null;

	const initialProductOptionValue =
		isAdmin && currentJSONObject
			? {key: currentJSONObject.value[0]}
			: getInitialProductOptionValue(productOption);

	const defaultProductOptionValue = initialProductOptionValue
		? initialProductOptionValue
		: productOptionValues[0];

	useEffect(() => {
		setSkuOptionsAtomState({
			...skuOptionsAtomState,
			errors: getSkuOptionsErrors(
				!defaultProductOptionValue,
				productOption,
				skuOptionsAtomState
			),
			namespace,
			skuOptions: [
				...skuOptionsAtomState.skuOptions,
				{
					key: productOption.key,
					price: defaultProductOptionValue?.price,
					priceType: defaultProductOptionValue?.priceType,
					quantity: defaultProductOptionValue?.quantity,
					skuId: defaultProductOptionValue?.skuId,
					skuOptionKey: productOption.key,
					skuOptionValueKey: defaultProductOptionValue?.key,
					value: [defaultProductOptionValue?.key],
				},
			],
		});

		return () => setSkuOptionsAtomState(initialSkuOptionsAtomState);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const currentProductOptionValue = productOptionValues.filter(
		(productOptionValue) =>
			productOptionValue.key === defaultProductOptionValue.key
	)[0];

	const [selectedProductOption, setSelectedProductOption] = useState(
		currentProductOptionValue?.id +
			'[$SEPARATOR$]' +
			currentProductOptionValue?.key
	);

	const [
		selectedProductOptionValue,
		setSelectedProductOptionValue,
	] = useState({
		productOptionValueId: currentProductOptionValue?.id,
		skuId: sku?.id,
	});

	const [
		selectedProductOptionValueKey,
		setSelectedProductOptionValueKey,
	] = useState(currentProductOptionValue?.key);

	const DeliveryCatalogAPIServiceProvider = ServiceProvider.DeliveryCatalogAPI(
		'v1'
	);

	const handleChange = (value) => {
		if (skuOptionsAtomState.updating) {
			return;
		}

		setSkuOptionsAtomState({...skuOptionsAtomState, updating: true});

		setSelectedProductOption(value);

		const valueArray = value.split('[$SEPARATOR$]');

		setSelectedProductOptionValueKey(valueArray[1]);

		if (isAdmin) {
			return setSkuOptionsAtomState({
				...skuOptionsAtomState,
				updating: false,
			});
		}

		const currentProductOptionValue = productOptionValues.filter(
			(productOptionValue) => productOptionValue.key === valueArray[1]
		)[0];

		let currentSkuOptions = skuOptionsAtomState.skuOptions;

		const currentSkuOption = currentSkuOptions.filter(
			(skuOption) => skuOption.skuOptionKey === productOption.key
		)[0];

		if (currentSkuOption) {
			const curIndex = currentSkuOptions.findIndex(
				(skuOption) => skuOption.skuOptionKey === productOption.key
			);

			currentSkuOptions[curIndex] = {
				key: productOption.key,
				price: currentProductOptionValue.price,
				priceType: currentProductOptionValue.priceType,
				quantity: currentProductOptionValue.quantity,
				skuId: currentProductOptionValue.skuId,
				skuOptionKey: productOption.key,
				skuOptionValueKey: valueArray[1],
				value: [valueArray[1]],
			};
		}
		else {
			currentSkuOptions = [
				...currentSkuOptions,
				{
					key: productOption.key,
					price: currentProductOptionValue.price,
					priceType: currentProductOptionValue.priceType,
					quantity: currentProductOptionValue.quantity,
					skuId: currentProductOptionValue.skuId,
					skuOptionKey: productOption.key,
					skuOptionValueKey: valueArray[1],
					value: [valueArray[1]],
				},
			];
		}

		if (!productOption.skuContributor && !currentProductOptionValue.skuId) {
			setSkuOptionsAtomState({
				...skuOptionsAtomState,
				skuOptions: currentSkuOptions,
				updating: false,
			});

			return;
		}

		DeliveryCatalogAPIServiceProvider.postChannelProductSkuBySkuOption(
			channelId,
			productId,
			accountId,
			minQuantity,
			null,
			currentSkuOptions
		)
			.then((cpInstance) => {
				setSelectedProductOptionValue({
					...selectedProductOptionValue,
					productOptionValueId: valueArray[0],
					skuId: cpInstance.id,
				});

				const currentCPInstanceSkuOption = currentSkuOptions.filter(
					(skuOption) => skuOption.skuOptionKey === productOption.key
				)[0];

				if (currentCPInstanceSkuOption) {
					const curIndex = currentSkuOptions.findIndex(
						(skuOption) =>
							skuOption.skuOptionKey === productOption.key
					);

					currentSkuOptions[curIndex] = {
						...currentCPInstanceSkuOption,
						cpInstanceId: currentProductOptionValue.skuId,
						key: productOption.key,
					};
				}

				setSkuOptionsAtomState({
					...skuOptionsAtomState,
					skuOptions: currentSkuOptions,
				});

				cpInstance.skuOptions = currentSkuOptions;
				cpInstance.skuId = parseInt(cpInstance.id, 10);

				const dispatchedPayload = {
					cpInstance,
					namespace,
				};

				Liferay.fire(
					`${namespace}${CP_INSTANCE_CHANGED}`,
					dispatchedPayload
				);
			})
			.finally(() => {
				if (isMounted()) {
					setSkuOptionsAtomState({
						...skuOptionsAtomState,
						skuOptions: currentSkuOptions,
						updating: false,
					});
				}
			});
	};

	useEffect(() => {
		if (
			!selectedProductOptionValue.productOptionValueId ||
			!selectedProductOptionValue.skuId
		) {
			return;
		}

		DeliveryCatalogAPIServiceProvider.getChannelProductProductOptionProductOptionValues(
			channelId,
			productId,
			productOption.id,
			accountId,
			selectedProductOptionValue.productOptionValueId,
			selectedProductOptionValue.skuId,
			1,
			-1
		).then((responseProductOptionValues) => {
			setProductOptionValues(responseProductOptionValues.items);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedProductOptionValue]);

	return (
		<ClayForm.Group>
			<label htmlFor={componentId}>
				{getProductOptionName(productOption.name)}

				<Asterisk
					required={isRequired(forceRequired, isAdmin, productOption)}
				/>
			</label>

			<ClayRadioGroup
				data-sku-contributor={productOption.skuContributor}
				defaultValue={selectedProductOption}
				id={componentId}
				name={productOption.key}
				onChange={handleChange}
				value={selectedProductOption}
			>
				{productOptionValues.map(
					({
						id,
						key,
						name,
						relativePriceFormatted,
						skuId,
						visible,
					}) => {
						if (isAdmin || visible) {
							return (
								<ClayRadio
									id={id}
									key={key}
									label={getName(
										key,
										name,
										selectedProductOptionValueKey,
										skuId,
										relativePriceFormatted
									)}
									value={id + '[$SEPARATOR$]' + key}
								/>
							);
						}
					}
				)}
			</ClayRadioGroup>
		</ClayForm.Group>
	);
};

export default ProductOptionRadio;
