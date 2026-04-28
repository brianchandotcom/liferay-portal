/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	ConditionBuilder,
	generateConditionId,
} from './condition_builder/ConditionBuilder';
import {DefaultValueInput} from './condition_builder/DefaultValueInput';
import {getCollectionOperators} from './operators';

/**
 * Collections-specific wrapper around the generic ConditionBuilder.
 *
 * Serializes the current value into a hidden input so the typeSettings handler
 * picks it up on form submit.
 *
 * @param {Object} props
 * @param {'all'|'any'} [props.initialConditionType='all']
 * @param {Array<{id:string, propertyName?:string, operatorName?:string, value?:string}>} [props.initialConditions]
 * @param {string} props.namespace - portlet namespace, used for the hidden input name
 * @param {(state: {conditionType:'all'|'any', conditions:Array}) => void} [props.onChange]
 * @param {Array<{name:string, label:string, type:string, options?:Array}>} props.properties
 */
export default function CollectionFilterBuilder({
	initialConditionType = 'all',
	initialConditions,
	namespace,
	onChange,
	properties: initialProperties,
	propertiesURL,
}) {
	const [conditions, setConditions] = useState(
		initialConditions?.length
			? initialConditions.map((condition) => ({
					...condition,
					id: generateConditionId(),
				}))
			: [{id: generateConditionId()}]
	);

	const [conditionType, setConditionType] = useState(initialConditionType);

	const [properties, setProperties] = useState(initialProperties || []);

	useEffect(() => {
		if (!propertiesURL) {
			return undefined;
		}

		const assetTypeListenerHandler = () => {
			const assetTypeSelector = document.getElementById(
				`${namespace}anyAssetType`
			);
			const assetTypeValue = assetTypeSelector?.value || '';

			let classNameIds = [];

			if (assetTypeValue === 'false') {
				const multiSelector = document.getElementById(
					`${namespace}currentClassNameIds`
				);

				classNameIds = Array.from(multiSelector?.options || []).map(
					(option) => option.value
				);
			}
			else if (assetTypeValue && assetTypeValue !== 'true') {
				classNameIds = [assetTypeValue];
			}

			let classTypeIds = [];

			if (classNameIds.length === 1) {
				const subtypeContainer = document.querySelector(
					'.asset-subtype:not(.hide)'
				);
				const subtypeSelector = subtypeContainer?.querySelector(
					`[id^="${namespace}anyClassType"]`
				);
				const subtypeValue = subtypeSelector?.value;

				if (subtypeValue === 'false') {
					const className = subtypeContainer.id.slice(
						namespace.length,
						-'Options'.length
					);
					const multiSubtypeSelect = document.getElementById(
						`${namespace}${className}currentClassTypeIds`
					);

					classTypeIds = Array.from(
						multiSubtypeSelect?.options || []
					).map((option) => option.value);
				}
				else if (subtypeValue && subtypeValue !== 'true') {
					classTypeIds = [subtypeValue];
				}
			}

			fetch(
				addParams(
					{
						[`${namespace}classNameIds`]: classNameIds.join(','),
						[`${namespace}classTypeIds`]: classTypeIds.join(','),
					},
					propertiesURL
				)
			)
				.then((response) => response.json())
				.then((data) => setProperties(data || []))
				.catch(() => {});
		};

		const eventName = `${namespace}sourceChange`;

		Liferay.on(eventName, assetTypeListenerHandler);

		return () => {
			Liferay.detach(eventName, assetTypeListenerHandler);
		};
	}, [namespace, propertiesURL]);

	const handleChange = (newConditions, newType) => {
		setConditions(newConditions);
		setConditionType(newType);

		onChange?.({conditionType: newType, conditions: newConditions});
	};

	return (
		<>
			<ConditionBuilder
				conditionType={conditionType}
				conditions={conditions}
				getOperators={getCollectionOperators}
				onChange={handleChange}
				properties={properties}
				renderValueInput={DefaultValueInput}
				showConjunctionPicker={false}
			/>

			<input
				name={`${namespace}TypeSettingsProperties--conditions--`}
				type="hidden"
				value={JSON.stringify(
					conditions
						.filter(
							({operatorName, propertyName, value}) =>
								operatorName && propertyName && value
						)
						.map(({_id, ...props}) => props)
				)}
			/>
		</>
	);
}
