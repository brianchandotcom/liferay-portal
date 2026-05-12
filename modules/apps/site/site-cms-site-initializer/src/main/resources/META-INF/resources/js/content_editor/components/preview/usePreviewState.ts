/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSessionState} from 'frontend-js-components-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {Status} from '../../../common/components/AsyncPicker';
import ApiHelper from '../../../common/services/ApiHelper';
import {
	PREVIEW_CHANNEL_SESSION_KEY,
	PREVIEW_DISPLAY_PAGE_SESSION_KEY,
} from './sessionKeys';

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

export default function usePreviewState(
	getPreviewDataURL: string,
	languageId: Liferay.Language.Locale
) {
	const [selectedChannelKey, setSelectedChannelKey] =
		useSessionState<React.Key>(PREVIEW_CHANNEL_SESSION_KEY, '');
	const [selectedDisplayPageKey, setSelectedDisplayPageKey] =
		useSessionState<React.Key>(PREVIEW_DISPLAY_PAGE_SESSION_KEY, '');
	const [sites, setSites] = useState<Site[]>([]);
	const [sitesStatus, setSitesStatus] = useState<Status>('saving');

	const loadSites = useCallback(async () => {
		setSitesStatus('saving');

		const {data, error} = await ApiHelper.get<Site[]>(getPreviewDataURL);

		setSitesStatus('saved');

		if (data) {
			setSites(data);

			return data;
		}

		throw new Error(error);
	}, [getPreviewDataURL]);

	useEffect(() => {
		loadSites();
	}, [loadSites]);

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

	const selectChannel = useCallback(
		(key: React.Key) => {
			setSelectedChannelKey(key);
			setSelectedDisplayPageKey('');
		},
		[setSelectedChannelKey, setSelectedDisplayPageKey]
	);

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
