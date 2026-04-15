/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useId, useMemo, useState} from 'react';

import ApiHelper from '../../../common/services/ApiHelper';
import AsyncPicker from '../../../structure_builder/components/AsyncPicker';
import {useCache} from '../../../structure_builder/contexts/CacheContext';

export type Site = {
	displayPageTemplates: {
		name: string;
		plid: string;
		url: string;
	}[];
	groupId: string;
	name: string;
};

export default function PreviewBody({
	getPreviewDataURL,
}: {
	getPreviewDataURL: string;
}) {
	const [selectedChannelKey, setSelectedChannelKey] = useState<React.Key>('');

	const channelPickerId = useId();

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
		status,
	} = useCache<Site[]>('sites', getSites);

	const channels = useMemo(
		() => [
			...sites.map(({groupId, name}) => ({
				id: Number(groupId),
				name,
			})),
			{id: 1, name: Liferay.Language.get('external-url')},
		],
		[sites]
	);

	return (
		<div className="align-items-center border-bottom d-flex mb-0 p-3">
			<label className="mb-0 mr-3" htmlFor={channelPickerId}>
				{Liferay.Language.get('Channel')}
			</label>

			<AsyncPicker
				aria-label={Liferay.Language.get('select-channel')}
				id={channelPickerId}
				items={channels}
				loader={loadSites}
				onSelectionChange={setSelectedChannelKey}
				placeholder={Liferay.Language.get('select-channel')}
				selectedKey={selectedChannelKey}
				status={status}
			/>
		</div>
	);
}
