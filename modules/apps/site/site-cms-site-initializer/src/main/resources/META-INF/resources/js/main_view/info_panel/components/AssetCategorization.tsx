/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React, {ComponentProps, useEffect, useState} from 'react';

import {IAssetObjectEntry} from '../../../structure_builder/types/AssetType';
import ObjectEntryService, {
	Categorization,
} from '../services/ObjectEntryService';
import AssetCategories from './AssetCategories';
import AssetTags from './AssetTags';

export type CategorizationInputSize = ComponentProps<
	typeof ClayInput
>['sizing'];

export default function AssetCategorization({
	cmsGroupId,
	getObjectEntryURL,
	inputSize,
	updateObjectEntryURL,
}: {
	cmsGroupId: string;
	getObjectEntryURL: string;
	inputSize?: CategorizationInputSize;
	updateObjectEntryURL: string;
}) {
	const [objectEntry, setObjectEntry] = useState<IAssetObjectEntry | null>(
		null
	);

	const updateObjectEntry = async ({
		keywords,
		taxonomyCategoryIds,
	}: Categorization): Promise<void> => {
		const {data, error} = await ObjectEntryService.patchObjectEntry(
			{
				keywords: keywords || objectEntry?.keywords!,
				...(taxonomyCategoryIds ? {taxonomyCategoryIds} : {}),
			},
			updateObjectEntryURL
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
			const {data, error} =
				await ObjectEntryService.getObjectEntry(getObjectEntryURL);

			if (data) {
				setObjectEntry(data);
			}
			else if (error) {
				console.error(error);
			}
		})();
	}, [getObjectEntryURL]);

	if (!objectEntry) {
		return null;
	}

	return (
		<>
			<AssetCategories
				cmsGroupId={cmsGroupId}
				inputSize={inputSize}
				objectEntry={objectEntry}
				updateObjectEntry={updateObjectEntry}
			/>

			<AssetTags
				cmsGroupId={cmsGroupId}
				inputSize={inputSize}
				objectEntry={objectEntry}
				updateObjectEntry={updateObjectEntry}
			/>
		</>
	);
}
