/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useMemo, useState} from 'react';

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
	const [languageId, setLanguageId] = useState<string>(
		Liferay.ThemeDisplay.getDefaultLanguageId()
	);
	const [selectedChannelKey, setSelectedChannelKey] = useState<React.Key>('');
	const [selectedDisplayPageKey, setSelectedDisplayPageKey] =
		useState<React.Key>('');

	useEffect(() => {
		const handleLocaleChanged = ({
			languageId,
		}: {
			languageId: Liferay.Language.Locale;
		}) => setLanguageId(languageId);

		Liferay.on('localizationSelect:localeChanged', handleLocaleChanged);

		return () =>
			Liferay.detach(
				'localizationSelect:localeChanged',
				handleLocaleChanged
			);
	}, []);

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

	const previewURL = useMemo(() => {
		const url = displayPageTemplates?.find(
			({plid}) => plid === selectedDisplayPageKey
		)?.url;

		if (
			!url ||
			languageId === Liferay.ThemeDisplay.getDefaultLanguageId()
		) {
			return url;
		}

		const localizedURL = new URL(url, window.location.origin);

		localizedURL.searchParams.set('languageId', languageId);

		return localizedURL.toString();
	}, [displayPageTemplates, languageId, selectedDisplayPageKey]);

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
