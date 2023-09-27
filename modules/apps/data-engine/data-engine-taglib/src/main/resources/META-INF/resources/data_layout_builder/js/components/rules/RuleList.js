/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import React from 'react';

import EmptyState from '../empty-state/EmptyState.es';
import RuleItem from './RuleItem';

export default function ({
	keywords,
	onAddRule,
	onDeleteRule,
	onEditRule,
	rules,
}) {
	const filteredRules = rules
		.map((rule, index) => ({loc: index, rule}))
		.filter(({rule: {name}}) =>
			new RegExp(keywords, 'ig').test(
				name[themeDisplay.getDefaultLanguageId()]
			)
		);

	return (
		<>
			{!filteredRules.length ? (
				<EmptyState
					emptyState={{
						button: () => (
							<ClayButton
								displayType="secondary"
								onClick={onAddRule}
							>
								{Liferay.Language.get('create-new-rule')}
							</ClayButton>
						),
						description: Liferay.Language.get(
							'there-are-no-rules-description'
						),
						title: Liferay.Language.get('there-are-no-rules'),
					}}
					keywords={keywords}
					small
				/>
			) : (
				<ClayLayout.ContentCol className="rule-list">
					<hr />

					{filteredRules.map(({loc, rule}, index) => (
						<RuleItem
							key={index}
							loc={loc}
							onDeleteRule={onDeleteRule}
							onEditRule={onEditRule}
							rule={rule}
						/>
					))}
				</ClayLayout.ContentCol>
			)}
		</>
	);
}
