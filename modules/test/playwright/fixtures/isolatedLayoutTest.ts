/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import getRandomString from '../utils/getRandomString';
import {backendPageTest} from './backendPageTest';

export interface IsolatedLayoutOptions {

	/**
	 * Whether to publish the layout if its type is 'content' (otherwise it must be left
	 * `undefined`)
	 */
	publish?: boolean;

	/**
	 * Layout type (eg: 'content', 'portlet', ...)
	 */
	type?: string;
}

export interface IsolatedLayout {
	layout: Layout;
}

const test = mergeTests(backendPageTest);

function fillDefaultValues(options: IsolatedLayoutOptions) {
	if (options.type === undefined) {
		options.type = 'content';
	}

	if (options.publish === undefined && options.type === 'content') {
		options.publish = true;
	}
}

function isolatedLayoutTest(options: IsolatedLayoutOptions = {}) {
	fillDefaultValues(options);

	const fixtureImpl = test.extend<IsolatedLayout>({
		layout: [
			async ({backendPage}, use) => {
				const apiHelpers = new ApiHelpers(backendPage);

				let layout: Layout;

				try {

					// Get group 'guest'

					const company =
						await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
							'liferay.com'
						);

					const group =
						await apiHelpers.jsonWebServicesGroup.getGroupByKey(
							company.companyId,
							'Guest'
						);

					// Create layout

					layout = await apiHelpers.jsonWebServicesLayout.addLayout({
						groupId: group.groupId,
						options: {
							publish: options.publish,
							type: options.type,
						},
						title: getRandomString(),
					});

					// Run test

					await use(layout);
				}
				finally {

					// Delete the layout

					if (layout?.plid) {
						await apiHelpers.jsonWebServicesLayout.deleteLayout(
							layout.plid
						);
					}
				}
			},
			{auto: true},
		],
	});

	return fixtureImpl;
}

export {isolatedLayoutTest};
