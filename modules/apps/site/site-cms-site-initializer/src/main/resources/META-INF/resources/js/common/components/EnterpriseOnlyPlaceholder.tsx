/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Badge from '@clayui/badge';
import {
	ILearnResourceContext,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import React from 'react';

import {getImage} from '../utils/getImage';
import EnterpriseLink from './EnterpriseLink';

interface EnterpriseOnlyPlaceholderProps {
	learnResources: ILearnResourceContext;
}

export default function EnterpriseOnlyPlaceholder({
	learnResources,
}: EnterpriseOnlyPlaceholderProps) {
	return (
		<div className="c-empty-state c-empty-state-animation">
			<div className="c-empty-state-image c-mb-3">
				<div className="c-empty-state-aspect-ratio">
					<img
						alt="empty-state-image"
						className="aspect-ratio-item aspect-ratio-item-fluid"
						src={getImage('unlock_dashboard.svg')}
					/>
				</div>
			</div>

			<Badge
				className="text-uppercase"
				displayType="primary"
				label={Liferay.Language.get('enterprise')}
				translucent
			/>

			<div className="c-empty-state-title mt-2">
				{Liferay.Language.get('unlock-the-dashboard')}
			</div>

			<div className="c-empty-state-text">
				{Liferay.Language.get(
					'use-the-dashboard-to-monitor-asset-usage-and-performance-across-spaces'
				)}

				<LearnResourcesContext.Provider value={learnResources}>
					<LearnMessage
						className="ml-1 text-decoration-underline"
						resource="site-cms-site-initializer"
						resourceKey="dashboard"
					/>
				</LearnResourcesContext.Provider>
			</div>

			<div className="c-empty-state-footer">
				<EnterpriseLink className="btn btn-primary" />
			</div>
		</div>
	);
}
