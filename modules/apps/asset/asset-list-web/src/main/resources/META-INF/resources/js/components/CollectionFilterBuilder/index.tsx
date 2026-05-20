/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import {ConditionBuilder} from './ConditionBuilder';
import {Config, initializeConfig} from './config';
import {RELATIVE_DATE_VALUES} from './operators';
import {getPropertyKey} from './types';

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

	if (typeof value === 'string' && RELATIVE_DATE_VALUES.includes(value)) {
		return value;
	}

	if (Array.isArray(value)) {
		return value.map((entry) =>
			typeof entry === 'string' ? normalizeDateTime(entry) : entry
		);
	}

	return typeof value === 'string' ? normalizeDateTime(value) : value;
}

interface CollectionFilterBuilderProps extends Config {
	initialConditions?: Array<Omit<FilterCondition, 'id'>>;
	onChange?: (state: FilterCondition[]) => void;
	properties: FilterPropertyGroup[];
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
	initializeConfig({
		categorySelectorURL,
		groupIds,
		namespace,
		propertiesURL,
		tagSelectorURL,
		vocabularyIds,
	});

	const [conditions, setConditions] = useState<FilterCondition[]>(
		initialConditions?.length
			? initialConditions.map((condition) => ({
					...condition,
					id: uuidv4(),
				}))
			: [{id: uuidv4()}]
	);

	const [properties, setProperties] = useState<FilterPropertyGroup[]>(
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
						type: 'text',
					},
				],
				label: '',
			},
			...properties,
		],
		[properties]
	);

	const propertiesMap = useMemo(
		() =>
			new Map(
				propertiesWithAssetFields
					.flatMap((group) => group.items ?? [])
					.map((property) => [
						getPropertyKey(
							property.classNameId,
							property.classTypeId,
							property.name
						),
						property,
					])
			),
		[propertiesWithAssetFields]
	);

	const filterValuesAndOmitID = (conditions: FilterCondition[]) =>
		conditions
			.map(({id: _id, ...props}) => {
				const property = propertiesMap.get(
					getPropertyKey(
						props.classNameId,
						props.classTypeId,
						props.propertyName
					)
				);

				return {...props, value: serializeValue(property, props.value)};
			})
			.filter(({operatorName, propertyName, value}) => {
				if (!operatorName || !propertyName || !value) {
					return false;
				}

				if (Array.isArray(value)) {
					return value.every(Boolean) && !!value.length;
				}

				return true;
			});

	useEffect(() => {
		if (!propertiesURL) {
			return undefined;
		}

		// Refetches the filterable properties whenever the user changes the
		// collection's asset source (type / subtype selectors), fired in
		// event sourceChange. The available fields depend on the selected
		// class names + class types.

		const assetTypeListenerHandler = () => {

			// The `anyAssetType` select holds 'true' (any type), 'false'
			// (multi-selection), or a single classNameId value.

			const assetTypeSelector = document.getElementById(
				`${namespace}anyAssetType`
			) as HTMLSelectElement | null;

			const assetTypeValue = assetTypeSelector?.value || '';

			let classNameIds: string[] = [];

			if (assetTypeValue === 'false') {

				// Multi-selection: collect every option out of the hidden
				// <select> the JSP populates with the user's picks.

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

			// Subtypes only make sense when exactly one asset type is
			// selected — the subtype UI is hidden otherwise.

			if (classNameIds.length === 1) {
				const subtypeContainer = document.querySelector(
					'.asset-subtype:not(.hide)'
				);

				const subtypeSelector = subtypeContainer?.querySelector(
					`[id^="${namespace}anyClassType"]`
				) as HTMLSelectElement | null;

				const subtypeValue = subtypeSelector?.value;

				if (subtypeValue === 'false' && subtypeContainer) {

					// Multi-subtype selection: same pattern as
					// classNameIds above, but the element id is
					// namespaced with the class name.

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
				conditions={conditions}
				onChange={handleChange}
				properties={propertiesWithAssetFields}
				propertiesMap={propertiesMap}
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
