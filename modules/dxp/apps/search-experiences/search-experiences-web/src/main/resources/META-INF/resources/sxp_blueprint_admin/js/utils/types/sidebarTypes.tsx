/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const SIDEBAR_TYPES = {
	ADD_SXP_ELEMENT: 'addSXPElement',
	CLAUSE_CONTRIBUTORS: 'clauseContributors',
	INDEXER_CLAUSES_HELP: 'indexerClausesHelp',
	PREVIEW: 'preview',
	QUERY_CONTRIBUTORS_HELP: 'queryContributorsHelp',
};

export const SIDEBAR_INFO = {
	[SIDEBAR_TYPES.QUERY_CONTRIBUTORS_HELP]: {
		description: Liferay.Language.get(
			'search-framework-query-contributors-description'
		),
		learnMessageKey: 'query-clause-contributors-configuration',
		title: Liferay.Language.get('search-framework-query-contributors'),
	},
	[SIDEBAR_TYPES.INDEXER_CLAUSES_HELP]: {
		description: Liferay.Language.get(
			'search-framework-indexer-clauses-description'
		),
		learnMessageKey: 'query-clause-contributors-configuration',
		title: Liferay.Language.get('search-framework-indexer-clauses'),
	},
};
