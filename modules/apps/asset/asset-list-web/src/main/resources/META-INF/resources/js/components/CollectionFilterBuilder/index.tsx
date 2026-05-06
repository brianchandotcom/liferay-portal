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

import type {
	ConditionType,
	FilterCondition,
	GenericProperty,
} from './condition_builder/types';

interface CollectionFilterBuilderProps {
	initialConditionType?: ConditionType;
	initialConditions?: Array<Omit<FilterCondition, 'id'>>;
	namespace: string;
	onChange?: (state: {
		conditionType: ConditionType;
		conditions: FilterCondition[];
	}) => void;
	properties: GenericProperty[];
	propertiesURL?: string;
}

/**
 * Collections-specific wrapper around the generic ConditionBuilder.
 *
 * Serializes the current value into a hidden input so the typeSettings handler
 * picks it up on form submit.
 */
export default function CollectionFilterBuilder({
	initialConditionType = 'all',
	initialConditions,
	namespace,
	onChange,
	properties: initialProperties,
	propertiesURL,
}: CollectionFilterBuilderProps) {
	const [conditions, setConditions] = useState<FilterCondition[]>(
		initialConditions?.length
			? initialConditions.map((condition) => ({
					...condition,
					id: generateConditionId(),
				}))
			: [{id: generateConditionId()}]
	);

	const [conditionType, setConditionType] =
		useState<ConditionType>(initialConditionType);

	const [properties, setProperties] = useState<GenericProperty[]>(
		initialProperties || []
	);

	const filterValuesAndOmitID = (conditions: FilterCondition[]) =>
		conditions
			.filter(
				({operatorName, propertyName, value}) =>
					operatorName && propertyName && value
			)
			.map(({id: _id, ...props}) => props);

	useEffect(() => {
		if (!propertiesURL) {
			return undefined;
		}

		const assetTypeListenerHandler = () => {
			const assetTypeSelector = document.getElementById(
				`${namespace}anyAssetType`
			) as HTMLSelectElement | null;
			const assetTypeValue = assetTypeSelector?.value || '';

			let classNameIds: string[] = [];

			if (assetTypeValue === 'false') {
				const multiSelector = document.getElementById(
					`${namespace}currentClassNameIds`
				) as HTMLSelectElement | null;

				classNameIds = Array.from(multiSelector?.options || []).map(
					(option) => option.value
				);
			}
			else if (assetTypeValue && assetTypeValue !== 'true') {
				classNameIds = [assetTypeValue];
			}

			let classTypeIds: string[] = [];

			if (classNameIds.length === 1) {
				const subtypeContainer = document.querySelector(
					'.asset-subtype:not(.hide)'
				);
				const subtypeSelector = subtypeContainer?.querySelector(
					`[id^="${namespace}anyClassType"]`
				) as HTMLSelectElement | null;
				const subtypeValue = subtypeSelector?.value;

				if (subtypeValue === 'false' && subtypeContainer) {
					const className = subtypeContainer.id.slice(
						namespace.length,
						-'Options'.length
					);
					const multiSubtypeSelect = document.getElementById(
						`${namespace}${className}currentClassTypeIds`
					) as HTMLSelectElement | null;

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

	const handleChange = (
		newConditions: FilterCondition[],
		newType: ConditionType
	) => {
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
				name={`${namespace}TypeSettingsProperties--filters--`}
				type="hidden"
				value={JSON.stringify(filterValuesAndOmitID(conditions))}
			/>

			{/* Use for Developer Viewing. TO-DO: Remove */}
			<div className="mt-4">
				<div className="text-secondary">
					<code>{namespace}TypeSettingsProperties--filters</code>
				</div>

				<pre
					style={{
						background: '#f5f5f5',
						borderRadius: 4,
						fontSize: 11,
						marginTop: 8,
						padding: 12,
					}}
				>
					{JSON.stringify(filterValuesAndOmitID(conditions), null, 2)}
				</pre>
			</div>
		</>
	);
}
