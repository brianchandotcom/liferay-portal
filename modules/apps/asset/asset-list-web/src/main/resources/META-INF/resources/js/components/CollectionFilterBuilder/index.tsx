/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {ConditionBuilder, getRandomID} from './ConditionBuilder';

import type {
	FilterCondition,
	FilterProperty,
	FilterPropertyGroup,
} from './types';

function normalizeDateTime(value: string) {
	if (!value) {
		return value;
	}

	// The picker emits "--:--" when the time portion isn't filled. Default
	// to midnight so a date-only entry still serializes cleanly.

	const normalized = value.replace('--:--', '00:00');
	const date = new Date(normalized.replace(' ', 'T'));

	return Number.isNaN(date.getTime()) ? '' : normalized;
}

function serializeValue(
	property: FilterProperty | undefined,
	value: FilterCondition['value']
): FilterCondition['value'] {
	if (property?.type !== 'date-time' || value === null) {
		return value;
	}

	if (Array.isArray(value)) {
		return value.map((entry) =>
			typeof entry === 'string' ? normalizeDateTime(entry) : entry
		);
	}

	return typeof value === 'string' ? normalizeDateTime(value) : value;
}

interface CollectionFilterBuilderProps {
	categorySelectorURL?: string;
	groupIds?: string[];
	initialConditions?: Array<Omit<FilterCondition, 'id'>>;
	namespace: string;
	onChange?: (state: FilterCondition[]) => void;
	properties: FilterProperty[];
	propertiesURL?: string;
	tagSelectorURL?: string;
	vocabularyIds?: string[];
}

/**
 * Serializes the current value into a hidden input so the typeSettings handler
 * picks it up on form submit.
 */
export default function CollectionFilterBuilder({
	categorySelectorURL,
	groupIds,
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

	const [properties, setProperties] = useState<FilterProperty[]>(
		initialProperties || []
	);

	const propertiesWithAssetFields = useMemo<FilterPropertyGroup[]>(
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

	const flatProperties = useMemo(
		() => propertiesWithAssetFields.flatMap((group) => group.items),
		[propertiesWithAssetFields]
	);

	const filterValuesAndOmitID = (conditions: FilterCondition[]) =>
		conditions
			.map(({id: _id, ...props}) => {
				const property = flatProperties.find(
					(p) =>
						p.name === props.propertyName &&
						p.classNameId === props.classNameId &&
						p.classTypeId === props.classTypeId
				);

				return {...props, value: serializeValue(property, props.value)};
			})
			.filter(({operatorName, propertyName, value}) => {
				if (!operatorName || !propertyName || !value) {
					return false;
				}

				if (Array.isArray(value)) {
					return value.every(Boolean);
				}

				return true;
			});

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

	const handleChange = (newConditions: FilterCondition[]) => {
		setConditions(newConditions);

		onChange?.(newConditions);
	};

	return (
		<>
			<ConditionBuilder
				categorySelectorURL={categorySelectorURL}
				conditions={conditions}
				groupIds={groupIds}
				namespace={namespace}
				onChange={handleChange}
				properties={propertiesWithAssetFields}
				tagSelectorURL={tagSelectorURL}
				vocabularyIds={vocabularyIds}
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
