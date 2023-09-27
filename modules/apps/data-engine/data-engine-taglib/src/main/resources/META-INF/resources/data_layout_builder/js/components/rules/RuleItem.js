/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {useFormState} from 'data-engine-js-components-web';
import {openConfirmModal} from 'frontend-js-web';
import React from 'react';

import {
	forEachDataDefinitionField,
	getDataDefinitionField,
	getFieldLabel,
	getOptionLabel,
} from '../../utils/dataDefinition.es';
import {getLocalizedValue} from '../../utils/lang.es';
import DropDown from '../drop-down/DropDown.es';

const ACTION_LABELS = {
	autofill: Liferay.Language.get('autofill'),
	calculate: Liferay.Language.get('calculate'),
	enable: Liferay.Language.get('enable'),
	require: Liferay.Language.get('require'),
	show: Liferay.Language.get('show'),
};

const OPERATOR_LABELS = {
	'belongs-to': Liferay.Language.get('belongs-to'),
	'contains': Liferay.Language.get('contains'),
	'does-not-contain': Liferay.Language.get('does-not-contain'),
	'equals-to': Liferay.Language.get('equals-to'),
	'is-empty': Liferay.Language.get('is-empty'),
	'not-equals-to': Liferay.Language.get('not-equals-to'),
	'not-is-empty': Liferay.Language.get('not-is-empty'),
};

const Text = ({capitalize = false, children = '', lowercase = false}) => (
	<span
		className={classNames('pr-1', {
			'text-capitalize': capitalize,
			'text-lowercase': lowercase,
		})}
	>
		{children}
	</span>
);

export default function RuleItem({loc, onDeleteRule, onEditRule, rule}) {
	const {actions, conditions, logicalOperator, name: ruleName} = rule;
	const {dataDefinition} = useFormState({schema: ['dataDefinition']});
	const name = getLocalizedValue(dataDefinition.defaultLanguageId, ruleName);

	const dropDownActions = [
		{
			action: () => onEditRule(loc),
			name: Liferay.Language.get('edit'),
		},
		{
			action: () => {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-delete-this'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							onDeleteRule(loc);
						}
					},
				});
			},
			name: Liferay.Language.get('delete'),
		},
	];

	const replaceExpressionLabels = (expression) => {
		forEachDataDefinitionField(dataDefinition, ({name}) => {
			expression = expression.replace(
				new RegExp(`\\[${name}\\]`, 'g'),
				getFieldLabel(dataDefinition, name)
			);
		});

		return expression;
	};

	return (
		<div className="rule-list__panel">
			<ClayPanel
				className="collapsable-panel panel-unstyled"
				collapsable
				collapseClassNames="panel-body"
				displayTitle={name}
				displayType="secondary"
			>
				<ClayButton
					displayType="unstyled"
					onClick={() => onEditRule(loc)}
				>
					<Text capitalize>{Liferay.Language.get('if')}</Text>

					{conditions.map(({operands, operator}, index) => {
						const [first, last] = operands;
						const lastValue = last?.value;

						const _getFieldLabel = () => {
							const field = getDataDefinitionField(
								dataDefinition,
								lastValue
							);

							if (field) {
								return getFieldLabel(dataDefinition, lastValue);
							}

							const parent = getDataDefinitionField(
								dataDefinition,
								first.value
							);

							if (parent) {
								const optionLabel = getOptionLabel(
									parent.customProperties?.options,
									lastValue,
									dataDefinition.defaultLanguageId
								);

								return optionLabel || lastValue;
							}

							return lastValue;
						};

						return (
							<React.Fragment key={index}>
								<Text lowercase>
									{Liferay.Language.get('field')}
								</Text>

								<ClayLabel displayType="success">
									{getFieldLabel(dataDefinition, first.value)}
								</ClayLabel>

								<ClayLabel displayType="secondary">
									{OPERATOR_LABELS[operator] || operator}
								</ClayLabel>

								{lastValue && (
									<ClayLabel displayType="info">
										{_getFieldLabel()}
									</ClayLabel>
								)}

								{index + 1 !== conditions.length && (
									<ClayLabel displayType="warning">
										{logicalOperator}
									</ClayLabel>
								)}
							</React.Fragment>
						);
					})}

					{actions.map(({action, expression, target}, index) => (
						<React.Fragment key={index}>
							<Text lowercase>
								{ACTION_LABELS[action] || action}
							</Text>

							{expression && (
								<>
									<ClayLabel displayType="secondary">
										{replaceExpressionLabels(expression)}
									</ClayLabel>

									<Text lowercase>
										{Liferay.Language.get('into')}
									</Text>
								</>
							)}

							<ClayLabel displayType="success">
								{getFieldLabel(dataDefinition, target)}
							</ClayLabel>

							{index + 1 !== actions.length && (
								<ClayLabel displayType="warning">
									{Liferay.Language.get('and')}
								</ClayLabel>
							)}
						</React.Fragment>
					))}
				</ClayButton>
			</ClayPanel>

			<DropDown
				actions={dropDownActions}
				className="rule-list__options"
			/>
		</div>
	);
}
