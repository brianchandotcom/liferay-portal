/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	SearchForm,
	SearchResultsMessage,
} from '@liferay/layout-js-components-web';
import React, {useMemo, useState} from 'react';

import {useSelector} from '../../../app/contexts/StoreContext';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import RulesEmptyState from './RulesEmptyState';
import RulesList from './RulesList';

export default function RulesSidebar() {
	const rules = useSelector((state) => state.layoutData.pageRules);
	const [search, setSearch] = useState<string>('');

	const filteredRules = useMemo(
		() =>
			rules.filter((rule) =>
				rule.name.toLowerCase().includes(search.toLowerCase())
			),
		[rules, search]
	);

	const noFilteredResults = !search.length;

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-rules')}
			</SidebarPanelHeader>

			<SearchForm
				className="mb-3 px-3"
				label={Liferay.Language.get('search-page-rules')}
				onChange={setSearch}
				size="sm"
			/>

			<SearchResultsMessage numberOfResults={filteredRules.length} />

			<div className="border-top overflow-auto p-3">
				{filteredRules.length ? (
					<RulesList
						rules={filteredRules}
						showNewRuleButton={noFilteredResults}
					/>
				) : (
					<RulesEmptyState showNewRuleButton={noFilteredResults} />
				)}
			</div>
		</>
	);
}
