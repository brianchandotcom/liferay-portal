/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import React, {useCallback, useState} from 'react';

import {Rule, RuleType} from './Rule';
import {TagRule} from './TagSelector';

const DEFAULT_RULE: TagRule = {
	queryAndOperator: false,
	queryContains: true,
	type: 'assetTags',
};

export interface Props {
	categorySelectorURL: string;
	groupIds: string;
	id: string;
	namespace: string;
	rules: Rule[];
	tagSelectorURL: string;
	vocabularyIds: string;
}

export default function AutoField({
	categorySelectorURL,
	groupIds,
	id,
	namespace,
	rules: initialRules,
	tagSelectorURL,
	vocabularyIds,
}: Props) {
	const [rules, setRules] = useState<Rule[]>(() => initialRules);

	const onAddRuleButtonClick = useCallback(() => {
		setRules((previousRules) => [...previousRules, DEFAULT_RULE]);
	}, []);

	const onDeleteRule = useCallback((ruleIndex: number) => {
		setRules((previousRules) => {
			if (ruleIndex >= previousRules.length) {
				return previousRules;
			}

			const nextRules = [...previousRules];
			nextRules.splice(ruleIndex, 1);

			return nextRules;
		});
	}, []);

	const onRuleChange = useCallback(
		(ruleIndex: number, changes: Partial<Rule<RuleType>>) => {
			setRules((previousRules) => {
				const nextRules = [...previousRules];

				if ('type' in changes) {
					nextRules[ruleIndex] = {
						...DEFAULT_RULE,
						...changes,
					} as Rule;
				}
				else {
					nextRules[ruleIndex] = {
						...nextRules[ruleIndex],
						...changes,
					} as Rule;
				}

				return nextRules;
			});
		},
		[]
	);

	return (
		<div id={id}>
			<ClayInput
				name={`${namespace}queryLogicIndexes`}
				type="hidden"
				value={Object.keys(rules).join(',')}
			/>

			<ul className="timeline">
				<li className="timeline-item">
					<div className="panel panel-default">
						<div className="d-flex flex-wrap panel-body">
							<div className="h4 panel-title">
								{Liferay.Language.get('rules')}
							</div>

							<div className="timeline-increment">
								<span className="timeline-icon"></span>
							</div>
						</div>
					</div>
				</li>

				{rules.map((rule, index) => (
					<Rule
						categorySelectorURL={categorySelectorURL}
						groupIds={groupIds}
						index={index}
						key={index}
						namespace={namespace}
						onChange={onRuleChange}
						onDelete={onDeleteRule}
						rule={rule}
						tagSelectorURL={tagSelectorURL}
						vocabularyIds={vocabularyIds}
					/>
				))}
			</ul>

			<div className="addbutton-timeline-item">
				<div className="add-condition timeline-increment-icon">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('add-rule')}
						className="form-builder-rule-add-condition form-builder-timeline-add-item"
						onClick={onAddRuleButtonClick}
						size="xs"
						symbol="plus"
						type="button"
					/>
				</div>
			</div>
		</div>
	);
}
