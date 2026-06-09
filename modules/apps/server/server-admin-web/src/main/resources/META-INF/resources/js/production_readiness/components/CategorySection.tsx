/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import React from 'react';

import {RuleResult} from '../types';
import RuleRow from './RuleRow';

interface Props {
	categoryLabel: string;
	onToggleIgnore: (result: RuleResult) => void;
	results: RuleResult[];
	togglingRuleKeys: Set<string>;
}

const CategorySection: React.FC<Props> = ({
	categoryLabel,
	onToggleIgnore,
	results,
	togglingRuleKeys,
}) => {
	if (!results.length) {
		return null;
	}

	return (
		<ClayPanel
			className="production-readiness-section"
			collapsable
			defaultExpanded
			displayTitle={categoryLabel}
			displayType="unstyled"
			showCollapseIcon
		>
			<ClayPanel.Body>
				<ul className="list-unstyled mb-0">
					{results.map((result) => (
						<RuleRow
							key={result.ruleKey}
							onToggleIgnore={onToggleIgnore}
							result={result}
							toggling={togglingRuleKeys.has(result.ruleKey)}
						/>
					))}
				</ul>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default CategorySection;
