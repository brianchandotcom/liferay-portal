/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useLiferayState} from '@liferay/frontend-js-state-web';
import classnames from 'classnames';
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

const ProductOptionSelect = ({
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
	const [hasErrors, setHasErrors] = useState(false);
	const isMounted = useIsMounted();

	const [skuOptionsAtomState, setSkuOptionsAtomState] = useLiferayState(
		skuOptionsAtom
	);

	const currentJSONObject = json
		? JSON.parse(json).filter(
				(jsonObject) => jsonObject.key === productOption.key
		  )[0]
		: null;

	const initialProductOptionValue = isAdmin
		? {key: currentJSONObject?.value[0]}
		: getInitialProductOptionValue(productOption);

	const [
		selectedProductOptionValue,
		setSelectedProductOptionValue,
	] = useState({
		productOptionValueId: initialProductOptionValue?.id,
		skuId: sku?.id,
	});
	const [
		selectedProductOptionValueKey,
		setSelectedProductOptionValueKey,
	] = useState(initialProductOptionValue?.key);

	const [productOptionValues, setProductOptionValues] = useState(
		productOption.productOptionValues
	);

	const DeliveryCatalogAPIServiceProvider = ServiceProvider.DeliveryCatalogAPI(
		'v1'
	);

	useEffect(
		() =>
			setSkuOptionsAtomState({
				...skuOptionsAtomState,
				errors: getSkuOptionsErrors(
					hasErrors,
					productOption,
					skuOptionsAtomState
				),
			}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[hasErrors]
	);

	useEffect(() => {
		const required =
			(isAdmin && forceRequired && !initialProductOptionValue?.key) ||
			(productOption.required &&
				!productOption.skuContributor &&
				!initialProductOptionValue);

		if (required) {
			setHasErrors(true);
		}

		setSkuOptionsAtomState({
			...skuOptionsAtomState,
			errors: getSkuOptionsErrors(
				required,
				productOption,
				skuOptionsAtomState
			),
			namespace,
			skuOptions: [
				...skuOptionsAtomState.skuOptions,
				{
					key: productOption.key,
					price: initialProductOptionValue?.price,
					priceType: initialProductOptionValue?.priceType,
					quantity: initialProductOptionValue?.quantity,
					skuId: initialProductOptionValue?.skuId,
					skuOptionKey: productOption.key,
					skuOptionValueKey: initialProductOptionValue?.key,
					value: initialProductOptionValue?.key || '',
				},
			],
		});

		return () => setSkuOptionsAtomState(initialSkuOptionsAtomState);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleChange = ({target: {value}}) => {
		if (skuOptionsAtomState.updating) {
			return;
		}

		setSkuOptionsAtomState({...skuOptionsAtomState, updating: true});

		const valueArray = value.split('[$SEPARATOR$]');

		if (isAdmin) {
			setSelectedProductOptionValueKey(valueArray[1]);

			const required = forceRequired && !valueArray[1];

			setHasErrors(required);

			return setSkuOptionsAtomState({
				...skuOptionsAtomState,
				errors: getSkuOptionsErrors(
					required,
					productOption,
					skuOptionsAtomState
				),
				updating: false,
			});
		}

		const currentProductOptionValue = productOptionValues.filter(
			(productOptionValue) => productOptionValue.key === valueArray[1]
		)[0];

		if (!currentProductOptionValue) {
			const required =
				forceRequired ||
				productOption.skuContributor ||
				productOption.required;

			setHasErrors(required);

			return setSkuOptionsAtomState({
				...skuOptionsAtomState,
				errors: getSkuOptionsErrors(
					required,
					productOption,
					skuOptionsAtomState
				),
				updating: false,
			});
		}

		setSelectedProductOptionValueKey(valueArray[1]);

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
				value: valueArray[1],
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
					value: valueArray[1],
				},
			];
		}

		if (!productOption.skuContributor && !currentProductOptionValue.skuId) {
			setHasErrors(false);

			return setSkuOptionsAtomState({
				...skuOptionsAtomState,
				errors: getSkuOptionsErrors(
					false,
					productOption,
					skuOptionsAtomState
				),
				skuOptions: currentSkuOptions,
				updating: false,
			});
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
					setHasErrors(false);
					setSkuOptionsAtomState({
						...skuOptionsAtomState,
						errors: getSkuOptionsErrors(
							false,
							productOption,
							skuOptionsAtomState
						),
						skuOptions: currentSkuOptions,
						updating: false,
					});
				}
			});
	};

	useEffect(() => {
		if (
			!selectedProductOptionValue.productOptionValueId ||
			selectedProductOptionValue.productOptionValueId <= 0 ||
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
		<ClayForm.Group className={classnames({'has-error': hasErrors})}>
			<label htmlFor={componentId}>
				{getProductOptionName(productOption.name)}

				<Asterisk
					required={isRequired(forceRequired, isAdmin, productOption)}
				/>
			</label>

			<ClaySelect
				data-sku-contributor={productOption.skuContributor}
				disabled={skuOptionsAtomState.updating}
				id={componentId}
				name={productOption.key}
				onChange={handleChange}
			>
				<ClaySelect.Option
					label={Liferay.Language.get('choose-an-option')}
					selected={!selectedProductOptionValueKey}
				/>

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
								<ClaySelect.Option
									key={id}
									label={getName(
										key,
										name,
										selectedProductOptionValueKey,
										skuId,
										relativePriceFormatted
									)}
									selected={
										selectedProductOptionValueKey === key
									}
									value={id + '[$SEPARATOR$]' + key}
								/>
							);
						}
					}
				)}
			</ClaySelect>

			{hasErrors && (
				<ClayForm.FeedbackItem>
					<ClayForm.FeedbackIndicator symbol="exclamation-full" />

					{Liferay.Language.get('this-field-is-required')}
				</ClayForm.FeedbackItem>
			)}
		</ClayForm.Group>
	);
};

export default ProductOptionSelect;
