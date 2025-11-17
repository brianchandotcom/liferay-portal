/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {ITEM_INTERACTION_ORIGINS} from '../../../app/config/constants/itemInteractionOrigins';
import {LIST_ITEM_TYPES} from '../../../app/config/constants/listItemTypes';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import {
	useHoverMultipleItems,
	useKeyboardNavigation,
} from '../../../app/js-index';
import selectLayoutDataItemLabel from '../../../app/selectors/selectLayoutDataItemLabel';
import deleteRule from '../../../app/thunks/deleteRule';
import updateRule from '../../../app/thunks/updateRule';
import useActionValues, {
	ActionValues,
} from '../../../app/utils/useActionValues';
import useConditionValues, {
	ConditionValues,
} from '../../../app/utils/useConditionValues';
import {Rule} from '../../../types/Rule';
import RulesModal from './RulesModal';

const MAX_RULES = 20;

export default function RulesList({
	isSearching,
	rules,
}: {
	isSearching: boolean;
	rules: Rule[];
}) {
	const [modalVisible, setModalVisible] = useState(false);
	const [editingRule, setEditingRule] = useState<Rule | null>(null);
	const [savedRuleId, setSavedRuleId] = useState<string | null>(null);

	const dispatch = useDispatch();

	const onCreateRule = () => setModalVisible(true);

	const onDeleteRule = (rule: Rule) => {
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

	const onEditRule = (rule: Rule) => {
		setEditingRule(rule);

		setModalVisible(true);
	};

	return (
		<>
			{!isSearching ? (
				<ClayButton
					className="mb-3 mx-3"
					displayType="secondary"
					onClick={onCreateRule}
					size="sm"
				>
					<ClayIcon className="mr-2" symbol="plus" />

					{Liferay.Language.get('new-rule')}
				</ClayButton>
			) : null}

			<div className="border-top overflow-auto p-3">
				{!isSearching && rules.length >= MAX_RULES ? (
					<ClayAlert
						className="mb-4 mt-2"
						displayType="warning"
						title="Warning"
					>
						{Liferay.Language.get(
							'excessive-rules-may-affect-page-performance'
						)}
					</ClayAlert>
				) : null}

				<ClayList role="menubar">
					{rules.map((rule) => (
						<RuleItem
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
			</div>
		</>
	);
}

function RuleItem({
	onDelete,
	onEdit,
	rule,
	savedRuleId,
	setSavedRuleId,
}: {
	onDelete: (rule: Rule) => void;
	onEdit: (rule: Rule) => void;
	rule: Rule;
	savedRuleId: string | null;
	setSavedRuleId: (id: string | null) => void;
}) {
	const hoverMultipleItems = useHoverMultipleItems();
	const {isTarget, setElement} = useKeyboardNavigation({
		type: LIST_ITEM_TYPES.listItem,
	});
	const [triggerElement, setTriggerElement] =
		useState<HTMLButtonElement | null>(null);

	const [editing, setEditing] = useState(false);
	const [name, setName] = useState(rule.name);

	const inputRef = useRef<HTMLInputElement>(null);

	const dispatch = useDispatch();

	useEffect(() => {
		if (savedRuleId === rule.id) {
			triggerElement?.focus();

			setSavedRuleId(null);
		}
	}, [savedRuleId, triggerElement, rule, setSavedRuleId]);

	useEffect(() => {
		if (editing && inputRef.current) {
			inputRef.current.focus();
		}
	}, [editing]);

	const onSave = useCallback(() => {
		dispatch(
			updateRule({
				...rule,
				name,
				ruleId: rule.id,
			})
		);
	}, [dispatch, name, rule]);

	const items = useSelector((state) =>
		Object.values(state.layoutData.items).map((item) => ({
			label: selectLayoutDataItemLabel(state, item),
			value: item.itemId,
		}))
	);

	const conditions = useConditionValues({...rule, items});
	const actions = useActionValues({...rule, items});

	const hoveredItemIds = useMemo(() => {
		const actionsItemIds = rule.actions.map(({itemId}) => itemId);
		const conditionsItemIds = rule.conditions.map(({field}) => field);

		return [...actionsItemIds, ...conditionsItemIds];
	}, [rule.actions, rule.conditions]);

	const onHighlightItems = () => {
		hoverMultipleItems(hoveredItemIds, {
			origin: ITEM_INTERACTION_ORIGINS.rules,
		});
	};

	const onUnhighlightItems = () => {
		hoverMultipleItems([]);
	};

	const onScroll = () => {
		const fragment = document.querySelector('.highlighted-from-rule');

		fragment?.scrollIntoView({
			behavior: 'instant' as ScrollBehavior,
			block: 'center',
			inline: 'nearest',
		});
	};

	return (
		<ClayList.Item
			aria-description={Liferay.Language.get(
				'press-enter-or-space-to-scroll-to-the-first-fragment-under-this-rule'
			)}
			aria-label={getRuleAriaLabel(rule.name, conditions, actions)}
			className="p-2 page-editor__rule"
			key={rule.id}
			onBlurCapture={onUnhighlightItems}
			onClick={onScroll}
			onFocusCapture={onHighlightItems}
			onKeyDown={({key}) => {
				if (key === 'Enter' || key === ' ') {
					onScroll();
				}
			}}
			onMouseLeave={onUnhighlightItems}
			onMouseOver={onHighlightItems}
			ref={setElement}
			role="menuitem"
			tabIndex={isTarget ? 0 : -1}
		>
			<ClayList.ItemField expand>
				<div className="align-items-center d-flex">
					{editing ? (
						<input
							onBlur={() => {
								setEditing(false);

								onSave();
							}}
							onChange={(event) => {
								setName(event.target.value);
							}}
							onFocus={() => {
								if (!inputRef.current) {
									return;
								}

								inputRef.current.setSelectionRange(
									0,
									name.length
								);
							}}
							onKeyDown={(event) => {
								if (
									event.key === 'Enter' ||
									event.key === 'Escape' ||
									event.key === 'Tab'
								) {
									setEditing(false);

									onSave();
								}

								if (!event.key.match(/[a-z0-9-_ ]/gi)) {
									event.preventDefault();
								}

								event.stopPropagation();
							}}
							ref={inputRef}
							type="text"
							value={name}
						/>
					) : (
						<span
							aria-hidden="true"
							className="flex-grow-1 font-weight-semi-bold"
							onDoubleClick={() => setEditing(true)}
						>
							{name}
						</span>
					)}

					<ClayDropDown
						hasLeftSymbols={true}
						onMouseOver={(event) => event.stopPropagation()}
						trigger={
							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('view-x-options'),
									rule.name
								)}
								borderless
								displayType="secondary"
								onClick={(event) => event.stopPropagation()}
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
							<ClayDropDown.Item
								onClick={() => onEdit(rule)}
								symbolLeft="pencil"
							>
								{Liferay.Language.get('edit')}
							</ClayDropDown.Item>

							<ClayDropDown.Divider />

							<ClayDropDown.Item
								onClick={() => onDelete(rule)}
								symbolLeft="trash"
							>
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

function Condition({
	condition,
	index,
}: {
	condition: ConditionValues;
	index: number;
}) {
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

function Action({action}: {action: ActionValues}) {
	return (
		<>
			{action.prefix ? (
				<span className="font-weight-semi-bold text-uppercase">
					{action.prefix}
				</span>
			) : null}

			<span className="font-weight-semi-bold">{action.type}</span>

			<ClayLabel className="m-0" displayType="secondary">
				{action.item}
			</ClayLabel>
		</>
	);
}

function getRuleAriaLabel(
	name: string,
	conditions: ConditionValues[],
	actions: ActionValues[]
) {
	const conditionsDescription = conditions
		.map((condition) => condition.description)
		.join(' ');

	const actionsDescription = actions
		.map((action) => action.description)
		.join(' ');

	return `${name}: ${conditionsDescription} ${actionsDescription}`;
}
