/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {FRAGMENT_ENTRY_TYPES} from '../../utils/fragmentEntryTypes';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesFragmentEntryApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/fragment.fragmententry';
	}

	async addFragmentEntry({
		configuration = {fieldSets: []},
		css = '',
		fragmentCollectionId,
		groupId,
		html = '',
		js = '',
		name,
		type = 'component',
	}: {
		configuration?: FragmentConfiguration;
		css?: string;
		fragmentCollectionId: string;
		groupId: string;
		html?: string;
		js?: string;
		name: string;
		type?: FragmentEntryType;
	}): Promise<FragmentEntry> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('fragmentCollectionId', fragmentCollectionId);
		urlSearchParams.append('fragmentEntryKey', '');
		urlSearchParams.append('name', name);
		urlSearchParams.append('css', css);
		urlSearchParams.append('html', html);
		urlSearchParams.append('js', js);
		urlSearchParams.append('configuration', JSON.stringify(configuration));
		urlSearchParams.append('previewFileEntryId', '0');
		urlSearchParams.append('type', FRAGMENT_ENTRY_TYPES[type]);
		urlSearchParams.append('status', '0');
		urlSearchParams.append('serviceContext', JSON.stringify({}));

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-fragment-entry`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
