/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Badge from '@clayui/badge';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {
	ILearnResourceContext,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {ENTERPRISE_URL} from '../utils/constants';
import {getImage} from '../utils/getImage';

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
				<ClayLink
					aria-label={sub(
						Liferay.Language.get('x-opens-new-window'),
						Liferay.Language.get('get-enterprise-details')
					)}
					className="btn btn-primary"
					href={ENTERPRISE_URL}
					rel="noopener noreferrer"
					target="_blank"
				>
					<span className="d-inline">
						{Liferay.Language.get('get-enterprise-details')}

						<ClayIcon className="ml-2" symbol="shortcut" />
					</span>
				</ClayLink>
			</div>
		</div>
	);
}
