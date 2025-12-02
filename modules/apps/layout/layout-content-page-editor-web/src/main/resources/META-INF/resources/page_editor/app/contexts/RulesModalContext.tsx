/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';
import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useCallback,
	useContext,
	useState,
} from 'react';
import {v4 as uuidv4} from 'uuid';

import {Rule} from '../../types/Rule';
import selectPageRules from '../selectors/selectPageRules';
import {useSelector} from './StoreContext';

const RulesModalContext = React.createContext<{
	editingRule: Rule;
	setEditingRule: Dispatch<SetStateAction<Rule>>;
	setShouldValidate: Dispatch<SetStateAction<boolean>>;
	setTrigger: Dispatch<SetStateAction<HTMLButtonElement | null>>;
	setVisible: Dispatch<SetStateAction<boolean>>;
	shouldValidate: boolean;
	trigger: HTMLElement | null;
	visible: boolean;
}>({
	editingRule: getDefaultRule([]),
	setEditingRule: () => {},
	setShouldValidate: () => false,
	setTrigger: () => {},
	setVisible: () => {},
	shouldValidate: false,
	trigger: null,
	visible: false,
});

function RulesModalContextProvider({children}: {children: ReactNode}) {
	const rules = useSelector(selectPageRules);

	const [shouldValidate, setShouldValidate] = useState<boolean>(false);
	const [visible, setVisible] = useState<boolean>(false);
	const [editingRule, setEditingRule] = useState<Rule>(getDefaultRule(rules));
	const [trigger, setTrigger] = useState<HTMLButtonElement | null>(null);

	return (
		<RulesModalContext.Provider
			value={{
				editingRule,
				setEditingRule,
				setShouldValidate,
				setTrigger,
				setVisible,
				shouldValidate,
				trigger,
				visible,
			}}
		>
			{children}
		</RulesModalContext.Provider>
	);
}

function useRulesModal() {
	const {editingRule, setEditingRule, setTrigger, setVisible, trigger} =
		useContext(RulesModalContext);

	const rules = useSelector(selectPageRules);

	const openRulesModal = useCallback(
		({
			rule,
			trigger,
		}: {rule?: Partial<Rule>; trigger?: HTMLButtonElement | null} = {}) => {
			if (rule) {
				setEditingRule((previous) => ({
					...previous,
					...rule,
				}));
			}

			if (trigger) {
				setTrigger(trigger);
			}

			setVisible(true);
		},
		[setEditingRule, setTrigger, setVisible]
	);

	const closeRulesModal = useCallback(() => {
		setEditingRule(getDefaultRule([...rules, editingRule]));

		setVisible(false);

		if (trigger) {
			trigger.focus();
		}

		setTrigger(null);
	}, [editingRule, rules, setEditingRule, setTrigger, setVisible, trigger]);

	const updateEditingRule = useCallback(
		({
			actions,
			conditionType,
			conditions,
			name,
		}: {
			actions?: Rule['actions'];
			conditionType?: Rule['conditionType'];
			conditions?: Rule['conditions'];
			name?: Rule['name'];
		}) => {
			setEditingRule((rule) => {
				if (!rule) {
					return rule;
				}

				return {
					...rule,
					...(!isNullOrUndefined(name) ? {name} : {}),
					...(!isNullOrUndefined(actions) ? {actions} : {}),
					...(!isNullOrUndefined(conditions) ? {conditions} : {}),
					...(!isNullOrUndefined(conditionType)
						? {conditionType}
						: {}),
				};
			});
		},
		[setEditingRule]
	);

	return {closeRulesModal, openRulesModal, updateEditingRule};
}

function useRulesModalState() {
	const {editingRule, visible} = useContext(RulesModalContext);

	return {editingRule, visible};
}

function getDefaultRule(rules: Rule[]): Rule {
	const nameIsUsed = (rules: Rule[], name: string) =>
		rules.some((rule) => rule.name === name);

	let name = Liferay.Language.get('rule');
	let suffix = 0;

	while (nameIsUsed(rules, name)) {
		suffix++;

		name = `${Liferay.Language.get('rule')} ${suffix}`;
	}

	return {
		actions: [{id: uuidv4(), type: undefined}],
		conditionType: 'all',
		conditions: [{id: uuidv4(), type: undefined}],
		name,
	};
}

export {
	RulesModalContext,
	RulesModalContextProvider,
	useRulesModal,
	useRulesModalState,
};
