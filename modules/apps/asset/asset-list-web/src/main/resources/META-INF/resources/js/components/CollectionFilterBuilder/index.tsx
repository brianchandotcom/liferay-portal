/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import renderValueInput from './ValueInput';
import {
	ConditionBuilder,
	getRandomID,
} from './condition_builder/ConditionBuilder';
import {
	getCollectionOperators,
	getCollectionQuantifierOptions,
} from './operators';

import type {
	ConditionType,
	FilterCondition,
	GenericProperty,
	PropertyGroup,
} from './condition_builder/types';

interface CollectionFilterBuilderProps {
	categorySelectorURL?: string;
	groupIds?: string[];
	initialConditionType?: ConditionType;
	initialConditions?: Array<Omit<FilterCondition, 'id'>>;
	namespace: string;
	onChange?: (state: {
		conditionType: ConditionType;
		conditions: FilterCondition[];
	}) => void;
	properties: GenericProperty[];
	propertiesURL?: string;
	tagSelectorURL?: string;
	vocabularyIds?: string[];
}

/**
 * Collections-specific wrapper around the generic ConditionBuilder.
 *
 * Serializes the current value into a hidden input so the typeSettings handler
 * picks it up on form submit.
 */
export default function CollectionFilterBuilder({
	categorySelectorURL,
	groupIds,
	initialConditionType = 'all',
	initialConditions,
	namespace,
	onChange,
	properties: initialProperties,
	propertiesURL,
	tagSelectorURL,
	vocabularyIds,
}: CollectionFilterBuilderProps) {
	const [conditions, setConditions] = useState<FilterCondition[]>(
		initialConditions?.length
			? initialConditions.map((condition) => ({
					...condition,
					id: getRandomID(),
				}))
			: [{id: getRandomID()}]
	);

	const [conditionType, setConditionType] =
		useState<ConditionType>(initialConditionType);

	const [properties, setProperties] = useState<GenericProperty[]>(
		initialProperties || []
	);

	const propertiesWithAssetFields = useMemo<PropertyGroup[]>(
		() => [
			{
				items: [
					{
						label: Liferay.Language.get('tags'),
						name: 'assetTags',
						type: 'asset-tags',
					},
					{
						label: Liferay.Language.get('categories'),
						name: 'assetCategories',
						type: 'asset-categories',
					},
					{
						label: Liferay.Language.get('keywords'),
						name: 'keywords',
						type: 'string',
					},
				],
				label: '',
			},
			{
				items: properties,
				label: Liferay.Language.get('common-fields'),
			},
		],
		[properties]
	);

	const renderCustomValueInput = useMemo(
		() =>
			renderValueInput({
				categorySelectorURL,
				groupIds,
				namespace,
				tagSelectorURL,
				vocabularyIds,
			}),
		[
			categorySelectorURL,
			groupIds,
			namespace,
			tagSelectorURL,
			vocabularyIds,
		]
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
					const className =
						subtypeContainer.getAttribute('data-class-name');
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
				.catch((error) =>
					console.error('Failed to fetch type properties: ', error)
				);
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
				getQuantifierOptions={getCollectionQuantifierOptions}
				onChange={handleChange}
				properties={propertiesWithAssetFields}
				renderValueInput={renderCustomValueInput}
				showConjunctionPicker={false}
			/>

			<input
				name={`${namespace}TypeSettingsProperties--filters--`}
				type="hidden"
				value={JSON.stringify(filterValuesAndOmitID(conditions))}
			/>

			{process.env.NODE_ENV === 'development' && (
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
						{JSON.stringify(
							filterValuesAndOmitID(conditions),
							null,
							2
						)}
					</pre>
				</div>
			)}
		</>
	);
}
