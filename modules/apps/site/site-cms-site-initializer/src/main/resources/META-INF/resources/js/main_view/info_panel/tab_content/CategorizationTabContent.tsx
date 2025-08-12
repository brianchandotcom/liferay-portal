/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';

import {IAssetObjectEntry} from '../../../structure_builder/types/AssetType';
import AssetCategories from '../components/AssetCategories';
import AssetTags from '../components/AssetTags';
import {AssetTypeInfoPanelContext} from '../context';
import ObjectEntryService from '../services/ObjectEntryService';

const CategorizationTabContent = () => {
	const {cmsGroupId, objectEntries = []} = useContext(
		AssetTypeInfoPanelContext
	);

	const [
		{
			actions: {get, update},
		},
	] = objectEntries;

	const [objectEntry, setObjectEntry] = useState({} as IAssetObjectEntry);

	const updateObjectEntry = async ({
		keywords,
		taxonomyCategoryIds,
	}: Pick<
		IAssetObjectEntry,
		'keywords' | 'taxonomyCategoryIds'
	>): Promise<void> => {
		const {data, error} = await ObjectEntryService.updateObjectEntry(
			{
				keywords: keywords || objectEntry.keywords,
				...(taxonomyCategoryIds ? {taxonomyCategoryIds} : {}),
			},
			update.href
		);

		if (data) {
			setObjectEntry(data);
		}
		else if (error) {
			console.error(error);
		}
	};

	useEffect(() => {
		(async () => {
			const {data, error} = await ObjectEntryService.getObjectEntry(
				get.href
			);

			if (data) {
				setObjectEntry(data);
			}
			else if (error) {
				console.error(error);
			}
		})();
	}, [get.href]);

	return (
		<>
			<AssetCategories
				cmsGroupId={cmsGroupId}
				objectEntry={objectEntry}
				updateObjectEntry={updateObjectEntry}
			/>

			<AssetTags
				cmsGroupId={cmsGroupId}
				objectEntry={objectEntry}
				updateObjectEntry={updateObjectEntry}
			/>
		</>
	);
};

export default CategorizationTabContent;
