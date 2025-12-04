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
	useEffect,
	useState,
} from 'react';
import {v4 as uuidv4} from 'uuid';

import {AdvancedRule, BasicRule, Rule} from '../../types/Rule';
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
				if (isNullOrUndefined(rule.script)) {
					setEditingRule(
						(previous) =>
							({
								...previous,
								...rule,
								script: undefined,
							}) as BasicRule
					);
				}
				else {
					setEditingRule(
						(previous) =>
							({
								...previous,
								...rule,
								conditionType: undefined,
								conditions: undefined,
							}) as AdvancedRule
					);
				}
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

	const updateConditions = useCallback(
		({
			conditionType,
			conditions,
			script,
		}: {
			actions?: Rule['actions'];
			conditionType?: Rule['conditionType'];
			conditions?: Rule['conditions'];
			name?: Rule['name'];
			script?: Rule['script'];
		}) => {
			setEditingRule((rule) => {
				if (!rule) {
					return rule;
				}

				if (isNullOrUndefined(script)) {
					return {
						...rule,
						conditionType: conditionType || 'all',
						conditions: conditions || [],
						script: undefined,
					} as BasicRule;
				}
				else {
					return {
						...rule,
						conditionType: undefined,
						conditions: undefined,
						script,
					} as AdvancedRule;
				}
			});
		},
		[setEditingRule]
	);

	const updateName = useCallback(
		(name: string) => {
			setEditingRule((rule) => {
				if (!rule) {
					return rule;
				}

				return {
					...rule,
					name,
				};
			});
		},
		[setEditingRule]
	);

	const updateActions = useCallback(
		(actions: Rule['actions']) => {
			setEditingRule((rule) => {
				if (!rule) {
					return rule;
				}

				return {
					...rule,
					actions,
				};
			});
		},
		[setEditingRule]
	);

	return {
		closeRulesModal,
		openRulesModal,
		updateActions,
		updateConditions,
		updateName,
	};
}

function useRulesModalState() {
	const {editingRule, visible} = useContext(RulesModalContext);

	return {editingRule, visible};
}

function useRuleValidation(onValidate: () => void) {
	const {setShouldValidate, shouldValidate} = useContext(RulesModalContext);

	useEffect(() => {
		if (!shouldValidate) {
			return;
		}

		onValidate();
		setShouldValidate(false);
	}, [onValidate, setShouldValidate, shouldValidate]);
}

function useTriggerRuleValidation() {
	const {setShouldValidate} = useContext(RulesModalContext);

	return () => setShouldValidate(true);
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
		id: '',
		name,
	} as BasicRule;
}

export {
	RulesModalContext,
	RulesModalContextProvider,
	useRulesModal,
	useRulesModalState,
	useRuleValidation,
	useTriggerRuleValidation,
};
