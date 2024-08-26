/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';
import getCollectionDefinition from './getCollectionDefinition';
import getFragmentDefinition from './getFragmentDefinition';
import getPageDefinition from './getPageDefinition';

const FRAGMENT_FIELDS = [
	{
		id: 'element-text',
		value: {
			text: {
				mapping: {
					fieldKey: 'JournalArticle_title',
					itemReference: {
						contextSource: 'CollectionItem',
					},
				},
			},
		},
	},
];

export default async function createPageWithCollectionAndFilterCollection({
	apiHelpers,
	classPK,
	collectionFilterId,
	siteId,
}: {
	apiHelpers: ApiHelpers;
	classPK: number;
	collectionFilterId: string;
	siteId: string;
}) {
	const collectionFilterDefinition = getFragmentDefinition({
		id: collectionFilterId,
		key: 'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer',
	});

	const collectionFragmentDefinition = getFragmentDefinition({
		fragmentFields: FRAGMENT_FIELDS,
		id: getRandomString(),
		key: 'BASIC_COMPONENT-heading',
	});

	const collectionDefinition = getCollectionDefinition({
		classPK,
		id: getRandomString(),
		pageElements: [collectionFragmentDefinition],
	});

	return await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			collectionFilterDefinition,
			collectionDefinition,
		]),
		siteId,
		title: getRandomString(),
	});
}
