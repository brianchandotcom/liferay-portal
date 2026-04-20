/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useMemo, useState} from 'react';

import ApiHelper from '../../../common/services/ApiHelper';
import {useCache} from '../../../structure_builder/contexts/CacheContext';

export type Site = {
	displayPageTemplates: {
		name: string;
		plid: string;
		url: string;
	}[];
	groupId: string;
	logoURL: string;
	name: string;
};

export default function usePreviewState(getPreviewDataURL: string) {
	const [selectedChannelKey, setSelectedChannelKey] = useState<React.Key>('');
	const [selectedDisplayPageKey, setSelectedDisplayPageKey] =
		useState<React.Key>('');

	const getSites = useCallback(async () => {
		const {data, error} = await ApiHelper.get<Site[]>(getPreviewDataURL);

		if (data) {
			return data;
		}

		throw new Error(error);
	}, [getPreviewDataURL]);

	const {
		data: sites = [],
		load: loadSites,
		status: sitesStatus,
	} = useCache<Site[]>('sites', getSites);

	const channels = useMemo(
		() => [
			...sites.map(({groupId, logoURL, name}) => ({
				id: Number(groupId),
				logoURL,
				name,
			})),
			{
				icon: 'chain-broken',
				id: 1,
				name: Liferay.Language.get('external-url'),
			},
		],
		[sites]
	);

	const displayPageTemplates = useMemo(
		() =>
			sites.find(({groupId}) => selectedChannelKey === groupId)
				?.displayPageTemplates,
		[selectedChannelKey, sites]
	);

	const previewURL = useMemo(
		() =>
			displayPageTemplates?.find(
				({plid}) => plid === selectedDisplayPageKey
			)?.url,
		[displayPageTemplates, selectedDisplayPageKey]
	);

	const selectChannel = useCallback((key: React.Key) => {
		setSelectedChannelKey(key);
		setSelectedDisplayPageKey('');
	}, []);

	const showDisplayPageTemplateAlert =
		displayPageTemplates !== undefined && !displayPageTemplates.length;

	return {
		channels,
		displayPageTemplates,
		loadSites,
		previewURL,
		selectChannel,
		selectedChannelKey,
		selectedDisplayPageKey,
		setSelectedDisplayPageKey,
		showDisplayPageTemplateAlert,
		sitesStatus,
	};
}
