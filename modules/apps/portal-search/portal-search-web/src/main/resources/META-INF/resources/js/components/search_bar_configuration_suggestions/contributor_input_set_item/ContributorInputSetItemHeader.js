/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';

import LearnMessage from '../../../shared/LearnMessage';
import {CONTRIBUTOR_TYPES} from '../../../utils/types/contributorTypes';

function ContributorInputSetItemHeader({contributorName, learnMessages}) {
	if (contributorName === CONTRIBUTOR_TYPES.BASIC) {
		return (
			<div className="contributor-input-set-item-header-root">
				<h3 className="contributor-name sheet-subtitle">
					{Liferay.Language.get('basic-suggestions-contributor')}
				</h3>

				<div className="contributor-description sheet-text">
					{Liferay.Language.get('basic-suggestions-contributor-help')}
				</div>
			</div>
		);
	}

	if (contributorName === CONTRIBUTOR_TYPES.SXP_BLUEPRINT) {
		return (
			<div className="contributor-input-set-item-header-root">
				<h3 className="contributor-name sheet-subtitle">
					{Liferay.Language.get('blueprint-suggestions-contributor')}
				</h3>

				<div className="contributor-description sheet-text">
					{Liferay.Language.get(
						'blueprint-suggestions-contributor-help'
					)}

					<LearnMessage
						className="ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-blueprints"
					/>
				</div>
			</div>
		);
	}

	if (
		contributorName === CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS ||
		contributorName === CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS
	) {
		return (
			<div className="contributor-input-set-item-header-root">
				<h3 className="contributor-name sheet-subtitle">
					{Liferay.Language.get(
						'site-activities-suggestions-contributor'
					)}
				</h3>

				<div className="contributor-description sheet-text">
					{Liferay.Language.get(
						'site-activities-suggestions-contributor-help'
					)}

					<LearnMessage
						className="ml-1"
						learnMessages={learnMessages}
						resourceKey="search-bar-suggestions-site-activities"
					/>
				</div>
			</div>
		);
	}

	return (
		<div className="contributor-input-set-item-header-root">
			<h3 className="contributor-name sheet-subtitle">
				{contributorName}
			</h3>
		</div>
	);
}

export default ContributorInputSetItemHeader;
