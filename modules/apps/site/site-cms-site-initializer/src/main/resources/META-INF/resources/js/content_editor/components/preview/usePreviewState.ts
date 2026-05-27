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
	PREVIEW_EXTERNAL_URL_SESSION_KEY,
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

const EXTERNAL_URL_CHANNEL_ID = 1;

export default function usePreviewState(
	getPreviewDataURL: string,
	languageId: Liferay.Language.Locale
) {
	const [externalURL = '', setExternalURL] = useSessionState<string>(
		PREVIEW_EXTERNAL_URL_SESSION_KEY,
		''
	);
	const [selectedChannelKey, setSelectedChannelKey] =
		useSessionState<React.Key>(PREVIEW_CHANNEL_SESSION_KEY, '');
	const [selectedDisplayPageKey, setSelectedDisplayPageKey] =
		useSessionState<React.Key>(PREVIEW_DISPLAY_PAGE_SESSION_KEY, '');
	const [sites, setSites] = useState<Site[]>([]);
	const [sitesStatus, setSitesStatus] = useState<Status>('saving');

	const isExternalURL =
		Number(selectedChannelKey) === EXTERNAL_URL_CHANNEL_ID;

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
				id: EXTERNAL_URL_CHANNEL_ID,
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
		if (isExternalURL) {
			return externalURL || undefined;
		}

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
	}, [
		displayPageTemplates,
		externalURL,
		isExternalURL,
		languageId,
		selectedDisplayPageKey,
	]);

	const selectChannel = useCallback(
		(key: React.Key) => {
			setSelectedChannelKey(key);
			setSelectedDisplayPageKey('');
			setExternalURL('');
		},
		[setExternalURL, setSelectedChannelKey, setSelectedDisplayPageKey]
	);

	const isDisplayPageTemplatesListEmpty =
		displayPageTemplates !== undefined && !displayPageTemplates.length;

	return {
		channels,
		displayPageTemplates,
		externalURL,
		isDisplayPageTemplatesListEmpty,
		isExternalURL,
		loadSites,
		previewURL,
		selectChannel,
		selectedChannelKey,
		selectedDisplayPageKey,
		setExternalURL,
		setSelectedDisplayPageKey,
		sitesStatus,
	};
}
