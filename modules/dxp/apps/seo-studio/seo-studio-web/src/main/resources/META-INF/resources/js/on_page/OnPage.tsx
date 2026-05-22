/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import SectionHeader from '../components/SectionHeader';
import InsightDetailView from '../insights_view/InsightDetailView';
import InsightsView from '../insights_view/InsightsView';

import './OnPage.scss';

export default function OnPage({
	apiURL,
	emptyState,
	fdsId,
	lastScanDate,
	views,
}: {
	apiURL: string;
	emptyState: Record<string, unknown>;
	fdsId: string;
	lastScanDate: string | null;
	views: any[];
}) {
	const [selectedInsightName, setSelectedInsightName] = useState<
		string | null
	>(null);

	return (
		<div className="seo-studio-on-page">
			{selectedInsightName ? (
				<InsightDetailView
					insightName={selectedInsightName}
					onBack={() => setSelectedInsightName(null)}
				/>
			) : (
				<>
					<SectionHeader
						lastScanDate={lastScanDate}
						title={Liferay.Language.get('on-page')}
					/>

					<InsightsView
						apiURL={apiURL}
						emptyState={emptyState}
						fdsId={fdsId}
						onSelectInsight={setSelectedInsightName}
						views={views}
					/>
				</>
			)}
		</div>
	);
}
