/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {IAssetObjectEntry} from '../../../structure_builder/types/AssetType';
import AssetCategories from '../components/AssetCategories';
import AssetTags from '../components/AssetTags';
import {AssetTypeInfoPanelContext} from '../context';

const CategorizationTabContent = () => {
	const {objectEntries = []} = useContext(AssetTypeInfoPanelContext);

	const [
		{
			actions: {get, update},
		},
	] = objectEntries;

	const [objectEntry, setObjectEntry] = useState({} as IAssetObjectEntry);

	const updateObjectEntry = useCallback(
		async ({
			keywords,
			taxonomyCategoryIds,
		}: Pick<
			IAssetObjectEntry,
			'keywords' | 'taxonomyCategoryIds'
		>): Promise<any> => {
			try {
				const response = await fetch(
					`${update.href}?nestedFields=embeddedTaxonomyCategory`,
					{
						body: JSON.stringify({
							...objectEntry,
							actions: undefined,
							keywords: keywords || objectEntry.keywords,
							taxonomyCategoryIds:
								taxonomyCategoryIds ||
								objectEntry.taxonomyCategoryIds,
						} as any),
						headers: {
							'Accept': 'application/json',
							'Content-Type': 'application/json',
							'x-csrf-token': Liferay.authToken,
						},
						method: 'PATCH',
					}
				);

				const json = await response.json();

				if (!response.ok) {
					throw new Error(json);
				}

				setObjectEntry(json);
			}
			catch (error) {
				console.error(error);
			}
		},
		[objectEntry, update]
	);

	const getObjectEntry = useCallback(async () => {
		try {
			const response = await fetch(
				`${get.href}?nestedFields=embeddedTaxonomyCategory`,
				{
					headers: {
						'Accept': 'application/json',
						'Content-Type': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
					method: 'GET',
				}
			);

			const json = await response.json();

			if (!response.ok) {
				throw new Error(json);
			}

			setObjectEntry(json);
		}
		catch (error) {
			console.error(error);
		}
	}, [get, setObjectEntry]);

	useEffect(() => {
		getObjectEntry();
	}, [getObjectEntry]);

	return (
		<>
			<AssetCategories
				objectEntry={objectEntry}
				updateObjectEntry={updateObjectEntry}
			/>

			<AssetTags
				objectEntry={objectEntry}
				updateObjectEntry={updateObjectEntry}
			/>
		</>
	);
};

export default CategorizationTabContent;
