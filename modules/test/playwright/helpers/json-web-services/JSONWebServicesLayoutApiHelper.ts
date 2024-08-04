/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesLayoutApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/layout';
	}

	/**
	 * Creates a visible, public, root level layout in the given group with the given name.
	 *
	 * @param title
	 * Used to set the title, name and friendlyURL
	 *
	 * @param options.publish
	 * Whether to publish the layout or leave it as draft. It can only be used with 'content' type
	 * layouts: if you provide a value other than `undefined` for any other type of layout the
	 * method will fail with an exception.
	 *
	 * @param options.type
	 * The layout type (eg: 'portlet' or 'content')
	 */
	async addLayout({
		externalReferenceCode = '',
		groupId,
		options = {type: 'portlet'},
		parentLayoutId = '0',
		title,
	}: {
		externalReferenceCode?: string;
		groupId: string;
		options?: {publish?: boolean; type: string};
		parentLayoutId?: string;
		title: string;
	}): Promise<Layout> {
		if (options.publish && options.type !== 'content') {
			throw new TypeError(
				`Publish parameter can only be 'undefined' for non content layouts`
			);
		}

		const name = title;

		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('externalReferenceCode', externalReferenceCode);
		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('privateLayout', 'false');
		urlSearchParams.append('parentLayoutId', parentLayoutId);
		urlSearchParams.append('name', name);
		urlSearchParams.append('title', title);
		urlSearchParams.append('description', '');
		urlSearchParams.append('type', options.type);
		urlSearchParams.append('hidden', 'false');
		urlSearchParams.append('friendlyURL', `/${title}`);

		const layout = await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-layout`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);

		// Publish content layouts using UI (since there's no headless method available yet)

		if (options.publish && options.type === 'content') {
			const page = this.apiHelpers.page;

			await page.goto(
				`${liferayConfig.environment.baseUrl}/group/guest/~/control_panel/manage` +
					'?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet'
			);

			await page.getByLabel(name, {exact: true}).click();
			await page.getByLabel('Publish').click();

			expect(page.getByRole('heading', {name: 'Pages'})).toBeAttached();
		}

		return layout;
	}

	async deleteLayout(plid: string): Promise<void> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('plid', plid);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/delete-layout`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
