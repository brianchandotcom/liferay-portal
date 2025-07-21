/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import {
	ISearchAssetObjectEntry,
	ISearchAssetTypeInformation,
} from '../../structure_builder/types/AssetType';
import AssetTypeInfoPanelBody from './AssetTypeInfoPanelBody';
import AssetTypeInfoPanelHeader from './AssetTypeInfoPanelHeader';
import {AssetTypeInfoPanelContext, IAssetTypeInfoPanelContext} from './context';

import '../../../css/components/AssetTypeInfoPanel.scss';
import {getBaseAssetInformation} from './util';
import {EVENTS} from './util/constants';

const AssetTypeInfoPanelContent = () => {
	const [assetInfo, setAssetInfo] = useState(
		{} as ISearchAssetTypeInformation
	);
	const [objectEntries, setObjectEntries] = useState(
		[] as ISearchAssetObjectEntry[]
	);

	useEffect(() => {
		const handler = ({items}: {items: ISearchAssetObjectEntry[]}): void => {
			setObjectEntries(items as ISearchAssetObjectEntry[]);
		};

		Liferay.on(EVENTS.ASSET_DATA, handler);

		return () => {
			Liferay.detach(EVENTS.ASSET_DATA, handler);
		};
	}, [setObjectEntries]);

	useEffect(() => {
		if (objectEntries.length === 1) {
			setAssetInfo(getBaseAssetInformation(objectEntries[0]));
		}
	}, [objectEntries]);

	return (
		<>
			<AssetTypeInfoPanelContext.Provider
				value={
					{
						objectEntries,
						...assetInfo,
					} as IAssetTypeInfoPanelContext
				}
			>
				<AssetTypeInfoPanelHeader />

				<AssetTypeInfoPanelBody />
			</AssetTypeInfoPanelContext.Provider>
		</>
	);
};

export default AssetTypeInfoPanelContent;
