/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const SubtypeTableCell = ({itemData}) => {
	const getSubtype = () => {

		// TODO: Figure out how to find subtype displayName

		return '';
	};

	return (
		<span>
			{getSubtype(
				itemData.configuration.generalConfiguration.searchableAssetTypes
			)}
		</span>
	);
};

const TypeTableCell = ({itemData}) => {
	const [searchableTypesArray, setSearchableTypesArray] = useState([]);

	useEffect(() => {
		fetch(`/o/search-experiences-rest/v1.0/searchable-asset-names/en_US`, {
			headers: new Headers({
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
			}),
			method: 'GET',
		})
			.then((response) => {
				if (response.ok) {
					return response.json();
				}
			})
			.then(({items}) => {
				setSearchableTypesArray(items);
			});
	}, []);

	const DEFAULT_TYPE = Liferay.Language.get('asset');

	const getType = (searchableAssetTypes, searchableTypesArray) => {

		// An empty searchableAssetTypes array would default to all
		// asset types.

		if (!searchableAssetTypes.length) {
			return DEFAULT_TYPE;
		}

		const firstType = searchableAssetTypes[0].split('&&');

		const firstTypeDisplayName = searchableTypesArray.find(
			(type) => type.className === firstType[0]
		)?.displayName;

		if (!firstTypeDisplayName) {
			return DEFAULT_TYPE;
		}

		// If all subtypes belong to the same type, return the type's displayName.

		if (
			searchableAssetTypes.length === 1 ||
			searchableAssetTypes.every(
				(type) => type.split('&&')[0] === firstType[0]
			)
		) {
			return firstTypeDisplayName;
		}

		return DEFAULT_TYPE;
	};

	return (
		<span>
			{getType(
				itemData.configuration.generalConfiguration
					.searchableAssetTypes,
				searchableTypesArray
			)}
		</span>
	);
};

export {SubtypeTableCell, TypeTableCell};
