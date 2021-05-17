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

import './RuleList.scss';

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import {LangUtil} from 'data-engine-taglib';
import {RulesSupport} from 'dynamic-data-mapping-form-builder';
import React, {useMemo} from 'react';

const LOGICAL_OPERATOR = {
	AND: Liferay.Language.get('and'),
	OR: Liferay.Language.get('or'),
};

const OPERATORS = {
	'belongs-to': Liferay.Language.get('belongs-to'),
	contains: Liferay.Language.get('contains'),
	'equals-to': Liferay.Language.get('is-equal-to'),
	'greater-than': Liferay.Language.get('is-greater-than'),
	'greater-than-equals': Liferay.Language.get('is-greater-than-or-equal-to'),
	'is-empty': Liferay.Language.get('is-empty'),
	'less-than': Liferay.Language.get('is-less-than'),
	'less-than-equals': Liferay.Language.get('is-less-than-or-equal-to'),
	'not-contains': Liferay.Language.get('does-not-contain'),
	'not-equals-to': Liferay.Language.get('is-not-equal-to'),
	'not-is-empty': Liferay.Language.get('is-not-empty'),
};

const OPERAND_TEXT = {
	field: Liferay.Language.get('field'),
	list: Liferay.Language.get('list'),
	user: Liferay.Language.get('user'),
	value: Liferay.Language.get('value'),
};

const EmptyState = () => (
	<ClayLayout.Sheet className="taglib-empty-result-message">
		<div className="taglib-empty-result-message-header"></div>
		<div className="sheet-text text-center text-muted">
			<p className="text-default">
				{Liferay.Language.get(
					'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'
				)}
			</p>
		</div>
	</ClayLayout.Sheet>
);

const ClayLabelCustom = ({children, ...otherProps}) => (
	<ClayLabel {...otherProps} withClose={false}>
		<div className="text-truncate-inline">
			<div className="text-truncate">{children}</div>
		</div>
	</ClayLabel>
);

const getOperandTypeJson = (json, field) => {
	let label = '';

	for (const key in json) {
		const keyLabel = field.rows.find((row) => row.value === key).label;
		const valueLabel = field.columns.find(
			(column) => column.value === json[key]
		).label;

		label += keyLabel + ':' + valueLabel + ', ';
	}

	const lastCommaPosition = label.lastIndexOf(', ');

	if (lastCommaPosition != -1) {
		label = label.substr(0, lastCommaPosition);
	}

	return label;
};

const Operand = ({field, left, type, value}) => {
	const text = useMemo(() => {
		switch (type) {
			case 'user':
				return Liferay.Language.get('user');
			case 'option':
				return left.field.options.find(
					(option) => value === option.value
				)?.label;
			case 'field':
				return field?.label;
			case 'list':
				return value;
			case 'json':
				return getOperandTypeJson(JSON.parse(value), left.field);
			default:
				return value;
		}
	}, [left, type, value, field]);

	return (
		<span className="inline-item">
			<span className="inline-item-before">
				{OPERAND_TEXT[type] ?? OPERAND_TEXT.value}
			</span>
			<ClayLabelCustom displayType="secondary" large>
				{text}
			</ClayLabelCustom>
		</span>
	);
};

const Condition = ({operands: [left, right], operator}) => (
	<>
		<Operand {...left} />
		<b className="inline-item inline-item-after inline-item-before text-lowercase">
			<em>{OPERATORS[operator] || operator}</em>
		</b>
		{right && <Operand {...right} left={left} />}
	</>
);

const ActionAutoFill = ({
	dataProvider,
	ddmDataProviderInstanceUUID,
	fields,
	outputs,
}) => {
	const dataProviderName = dataProvider?.find(
		({uuid}) => uuid === ddmDataProviderInstanceUUID
	)?.label;

	const labels = Object.values(outputs).map((output, index, array) => {
		const field = fields.find(({value}) => value === output);

		return (
			<>
				<ClayLabelCustom displayType="secondary" key={index} large>
					{field?.label ?? output}
				</ClayLabelCustom>
				{array.length - 1 !== index && `, `}
			</>
		);
	});

	return (
		<span className="inline-item">
			<b>
				{LangUtil.subComp(
					Liferay.Language.get('autofill-x-from-data-provider-x'),
					[labels, dataProviderName]
				)}
			</b>
		</span>
	);
};

const ActionJumpToPage = ({pages, target}) => (
	<span className="inline-item">
		<b className="inline-item-before">
			{Liferay.Language.get('jump-to-page')}
		</b>
		<ClayLabelCustom displayType="secondary" large>
			{pages[target]?.label}
		</ClayLabelCustom>
	</span>
);

const ActionCalculate = ({expression, fields, target}) => (
	<span className="inline-item">
		<b>
			{LangUtil.subComp(Liferay.Language.get('calculate-field-x-as-x'), [
				<ClayLabelCustom displayType="secondary" key={expression} large>
					{RulesSupport.replaceFieldNameByFieldLabel(
						expression,
						fields
					) ?? expression}
				</ClayLabelCustom>,
				<ClayLabelCustom displayType="secondary" key={target} large>
					{fields.find(({value}) => value === target)?.label ??
						target}
				</ClayLabelCustom>,
			])}
		</b>
	</span>
);

const ACTIONS_LABELS = {
	enable: Liferay.Language.get('enable'),
	require: Liferay.Language.get('require'),
	show: Liferay.Language.get('show'),
};

const ActionDefault = ({action, fields, target}) => (
	<span className="inline-item">
		<b className="inline-item-before">{ACTIONS_LABELS[action]}</b>
		<ClayLabelCustom displayType="secondary" large>
			{fields.find(({value}) => value === target)?.label ?? target}
		</ClayLabelCustom>
	</span>
);

const ACTIONS = {
	'auto-fill': ActionAutoFill,
	calculate: ActionCalculate,
	enable: ActionDefault,
	'jump-to-page': ActionJumpToPage,
	require: ActionDefault,
	show: ActionDefault,
};

const LogicalOperator = ({children, logicalOperator}) => (
	<>
		{children}
		<br />
		<b className="inline-item inline-item-before text-uppercase">
			{logicalOperator}
		</b>
	</>
);

const transformConditions = ({operator, operands: [left, right]}, fields) => {

	// When the left operator is a type `user` the backend does not return
	// the two operators, only the right operator.

	if (left?.type === 'list') {
		right = left;
		left = {
			label: 'user',
			repeatable: false,
			type: 'user',
			value: 'user',
		};
	}

	const operands = [
		{
			...left,
			field: fields.find(({fieldName}) => fieldName === left.value),
		},
	];

	if (right) {
		operands.push({
			...right,
			field:
				right.type === 'field' &&
				fields.find(({fieldName}) => fieldName === right.value),
		});
	}

	return {
		operands,
		operator,
	};
};

const ConditionWithLogicalOperator = ({
	condition,
	hasLogicalOperator,
	logicalOperator,
}) => (
	<>
		<Condition {...condition} />
		{hasLogicalOperator && (
			<LogicalOperator
				logicalOperator={LOGICAL_OPERATOR[logicalOperator]}
			/>
		)}
	</>
);

const ActionWithLogicalOperator = ({
	action,
	dataProvider,
	fields,
	hasLogicalOperator,
	pages,
	...otherProps
}) => {
	const Action = ACTIONS[action];

	return (
		<>
			<Action
				action={action}
				dataProvider={dataProvider}
				fields={fields}
				pages={pages}
				{...otherProps}
			/>
			{hasLogicalOperator && (
				<LogicalOperator logicalOperator={Liferay.Language.get('and')}>
					{` , `}
				</LogicalOperator>
			)}
		</>
	);
};

const ListItem = ({dataProvider, fields, onDelete, onEdit, pages, rule}) => {
	const {actions} = rule;

	const conditions = useMemo(
		() =>
			rule.conditions.map((condition) =>
				transformConditions(condition, fields)
			),
		[rule.conditions, fields]
	);

	const invalidRule = useMemo(
		() =>
			RulesSupport.fieldNameBelongsToAction(actions, '', fields) ||
			RulesSupport.fieldNameBelongsToCondition(conditions, ''),
		[actions, conditions, fields]
	);

	const isNestedCondition = useMemo(
		() =>
			conditions.find((condition) =>
				condition.operands.find((operand) =>
					operand.value.match(/[aA-zZ]+[(].*[,]+.*[)]/)
				)
			) !== undefined,
		[conditions]
	);

	return (
		<ClayList.Item flex>
			<ClayLayout.ContentCol expand>
				<div className="px-2 py-2">
					<b className="inline-item inline-item-before">
						{Liferay.Language.get('if')}
					</b>
					{conditions.map((condition, index) => (
						<ConditionWithLogicalOperator
							condition={condition}
							hasLogicalOperator={conditions.length - 1 > index}
							key={index}
							logicalOperator={rule['logical-operator']}
						/>
					))}
					<br />
					{actions.map(({action, ...otherProps}, index) => (
						<ActionWithLogicalOperator
							action={action}
							dataProvider={dataProvider}
							fields={fields}
							hasLogicalOperator={actions.length - 1 > index}
							key={index}
							pages={pages}
							{...otherProps}
						/>
					))}
				</div>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol>
				<div className="form-rule-list-col px-2 py-2">
					{invalidRule && (
						<div
							className="form-rule-list-invalid-rule"
							title={Liferay.Language.get(
								'due-to-missing-fields'
							)}
						>
							<ClayLabelCustom displayType="danger">
								{Liferay.Language.get('broken-rule')}
							</ClayLabelCustom>
						</div>
					)}
					<ClayDropDownWithItems
						items={[
							{
								disabled: isNestedCondition,
								label: Liferay.Language.get('edit'),
								onClick: () => onEdit(),
							},
							{
								label: Liferay.Language.get('delete'),
								onClick: () => {
									if (
										!isNestedCondition ||
										window.confirm(
											Liferay.Language.get(
												'you-cannot-create-rules-with-nested-functions.-are-you-sure-you-want-to-delete-this-rule'
											)
										)
									) {
										onDelete();
									}
								},
							},
						]}
						trigger={
							<ClayButton className="component-action">
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					/>
				</div>
			</ClayLayout.ContentCol>
		</ClayList.Item>
	);
};

export const RuleList = ({rules = [], onDelete, onEdit, ...otherProps}) => (
	<div className="form-rule-list">
		<h1 className="text-default">{Liferay.Language.get('rule-builder')}</h1>

		{rules.length === 0 && <EmptyState />}
		{rules.length > 0 && (
			<ClayList className="mt-4" showQuickActionsOnHover={false}>
				{rules.map((rule, index) => (
					<ListItem
						key={index}
						onDelete={() => onDelete(index)}
						onEdit={() => onEdit(index)}
						rule={rule}
						{...otherProps}
					/>
				))}
			</ClayList>
		)}
	</div>
);

RuleList.displayName = 'RuleList';
