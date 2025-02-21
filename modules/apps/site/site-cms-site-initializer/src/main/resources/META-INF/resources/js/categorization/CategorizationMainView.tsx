/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import '../../css/categorization/Categorization.scss';
import CategorizationToolbar from './CategorizationToolbar';
import TagsView from './tags/TagsView';
import VocabulariesView from './vocabulary/VocabulariesView';

const TABS: string[] = [
	Liferay.Language.get('vocabularies'),
	Liferay.Language.get('tags'),
];

export default function CategorizationMainView() {
	const [tab, setTab] = useState(TABS[0]);

	const handleTabChange = (tab: string) => {
		setTab(tab);
	};

	const renderTabContent = () => {
		switch (tab) {
			case 'tags':
				return <TagsView />;
			default:
				return <VocabulariesView />;
		}
	};

	return (
		<div className="categorization-section">
			<CategorizationToolbar
				activeTab={tab}
				onChangeTab={handleTabChange}
				tabs={TABS}
			/>

			{renderTabContent()}
		</div>
	);
}
