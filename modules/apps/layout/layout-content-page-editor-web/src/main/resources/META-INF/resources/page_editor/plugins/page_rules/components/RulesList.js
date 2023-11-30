/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {openToast, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import selectLayoutDataItemLabel from '../../../app/selectors/selectLayoutDataItemLabel';
import deleteRule from '../../../app/thunks/deleteRule';
import useActionValues from '../../../app/utils/useActionValues';
import useConditionValues from '../../../app/utils/useConditionValues';
import RulesModal from './RulesModal';

export default function RulesList() {
	const [modalVisible, setModalVisible] = useState(false);
	const [editingRule, setEditingRule] = useState(null);
	const [savedRuleId, setSavedRuleId] = useState(null);

	const rules = useSelector((state) => state.layoutData.pageRules);
	const dispatch = useDispatch();

	const onCreateRule = () => setModalVisible(true);

	const onDeleteRule = (rule) => {
		dispatch(
			deleteRule({
				ruleId: rule.id,
			})
		).then(() =>
			openToast({
				message: Liferay.Language.get(
					'the-rule-was-deleted-successfully'
				),
				type: 'success',
			})
		);
	};

	const onEditRule = (rule) => {
		setEditingRule(rule);

		setModalVisible(true);
	};

	return (
		<>
			<ClayButton
				className="w-100"
				displayType="secondary"
				onClick={onCreateRule}
				size="sm"
			>
				<ClayIcon className="mr-2" symbol="plus" />

				{Liferay.Language.get('new-rule')}
			</ClayButton>

			<ClayList className="pt-3">
				{rules.map((rule) => (
					<Rule
						key={rule.id}
						onDelete={onDeleteRule}
						onEdit={onEditRule}
						rule={rule}
						savedRuleId={savedRuleId}
						setSavedRuleId={setSavedRuleId}
					/>
				))}
			</ClayList>

			{modalVisible && (
				<RulesModal
					editingRule={editingRule}
					onCloseModal={(ruleId) => {
						if (ruleId) {
							setSavedRuleId(ruleId);
						}

						setEditingRule(null);

						setModalVisible(false);
					}}
				/>
			)}
		</>
	);
}

function Rule({onDelete, onEdit, rule, savedRuleId, setSavedRuleId}) {
	const [triggerElement, setTriggerElement] = useState();

	useEffect(() => {
		if (savedRuleId === rule.id) {
			triggerElement.focus();

			setSavedRuleId(null);
		}
	}, [savedRuleId, triggerElement, rule, setSavedRuleId]);

	const items = useSelector((state) =>
		Object.values(state.layoutData.items).map((item) => ({
			label: selectLayoutDataItemLabel(state, item),
			value: item.itemId,
		}))
	);

	const conditions = useConditionValues(rule);
	const actions = useActionValues({...rule, items});

	return (
		<ClayList.Item
			aria-label={getRuleAriaLabel(rule.name, conditions, actions)}
			className="p-2 page-editor__rule"
			key={rule.id}
		>
			<ClayList.ItemField expand>
				<div className="align-items-center d-flex">
					<span
						aria-hidden="true"
						className="flex-grow-1 font-weight-semi-bold"
					>
						{rule.name}
					</span>

					<ClayDropDown
						onMouseOver={(event) => event.stopPropagation()}
						trigger={
							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('view-x-options'),
									rule.name
								)}
								borderless
								displayType="secondary"
								ref={setTriggerElement}
								size="sm"
								symbol="ellipsis-v"
								title={sub(
									Liferay.Language.get('view-x-options'),
									rule.name
								)}
							/>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item onClick={() => onEdit(rule)}>
								<ClayIcon className="mr-2" symbol="pencil" />

								{Liferay.Language.get('edit')}
							</ClayDropDown.Item>

							<ClayDropDown.Divider />

							<ClayDropDown.Item onClick={() => onDelete(rule)}>
								<ClayIcon className="mr-2" symbol="trash" />

								{Liferay.Language.get('delete')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			</ClayList.ItemField>

			<ClayList.ItemField className="mt-3" expand>
				<p
					aria-hidden="true"
					className="align-items-center c-gap-2 d-flex flex-wrap"
				>
					{conditions.map((condition, index) => (
						<Condition
							condition={condition}
							index={index}
							key={condition.id}
						/>
					))}

					{actions.map((action) => (
						<Action action={action} key={action.id} />
					))}
				</p>
			</ClayList.ItemField>
		</ClayList.Item>
	);
}

function Condition({condition, index}) {
	return (
		<>
			<span
				className={classNames('font-weight-semi-bold', {
					'text-uppercase': index > 0,
				})}
			>
				{condition.prefix}
			</span>

			<ClayLabel className="m-0" displayType="secondary">
				{condition.type}
			</ClayLabel>

			{condition.condition}

			<ClayLabel className="m-0" displayType="secondary">
				{condition.value}
			</ClayLabel>
		</>
	);
}

function Action({action}) {
	return (
		<>
			{action.prefix ? (
				<span className="font-weight-semi-bold text-uppercase">
					{action.prefix}
				</span>
			) : null}

			<span className="font-weight-semi-bold">{action.type}</span>

			{action.action}

			<ClayLabel className="m-0" displayType="secondary">
				{action.item}
			</ClayLabel>
		</>
	);
}

function getRuleAriaLabel(name, conditions, actions) {
	const conditionsDescription = conditions
		.map((condition) => condition.description)
		.join(' ');

	const actionsDescription = actions
		.map((action) => action.description)
		.join(' ');

	return `${name}: ${conditionsDescription} ${actionsDescription}`;
}
