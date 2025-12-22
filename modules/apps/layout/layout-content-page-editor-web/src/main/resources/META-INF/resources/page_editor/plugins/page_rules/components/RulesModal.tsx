/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {ScreenReaderAnnouncerContextProvider} from '@liferay/layout-js-components-web';
import {openToast, useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {
	useRulesModal,
	useRulesModalState,
	useScriptError,
	useScriptInputRef,
	useTriggerRuleValidation,
} from '../../../app/contexts/RulesModalContext';
import {useDispatch} from '../../../app/contexts/StoreContext';
import RulesService from '../../../app/services/RulesService';
import addRule from '../../../app/thunks/addRule';
import updateRule from '../../../app/thunks/updateRule';
import {isAdvancedRule} from '../../../app/utils/isAdvancedRule';
import {Condition, RuleError} from '../../../types/Rule';
import {
	RuleBuilderActionSection,
	RuleBuilderConditionSection,
} from './RuleBuilderSection';
import RuleField from './RuleField';

export default function RulesModal() {
	const {editingRule, visible} = useRulesModalState();
	const triggerRuleValidation = useTriggerRuleValidation();

	const {closeRulesModal, updateActions, updateConditions, updateName} =
		useRulesModal();

	const dispatch = useDispatch();
	const nameId = useId();
	const nameInputRef = useRef<HTMLInputElement | null>(null);

	const [nameError, setNameError] = useState(false);
	const [ruleErrors, setRuleErrors] = useState<RuleError[]>([]);
	const {setScriptError} = useScriptError();
	const scriptInputRef = useScriptInputRef();

	const {observer, onClose} = useModal({
		onClose: () => {
			setRuleErrors([]);
			setScriptError(null);
			closeRulesModal();
		},
	});

	const onSave = useCallback(async () => {
		const errors: RuleError[] = [];

		if (!editingRule.name) {
			setNameError(true);

			errors.push({
				element: nameInputRef.current!,
				message: sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('rule-name')
				),
			});
		}

		let conditions: Condition[] = [];

		// Validate advanced rule script first

		if (isAdvancedRule(editingRule) && editingRule.script) {
			try {
				const result = await RulesService.validateScript(
					editingRule.script
				);

				if (!result.valid) {
					const message = Liferay.Language.get('syntax-error');

					setScriptError(message);

					if (scriptInputRef.current) {
						errors.push({
							element: scriptInputRef.current,
							message,
						});
					}
				}
				else {
					setScriptError(null);
				}
			}
			catch {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			}
		}
		else {
			conditions = editingRule.conditions!;
		}

		[...conditions, ...editingRule.actions].forEach((item) => {
			if (item.error) {
				errors.push(item.error);
			}
		});

		if (errors.length) {
			setRuleErrors(errors);
			triggerRuleValidation();

			return;
		}

		// Remove readOnly and error props so it's not persisted

		const rule = {
			...editingRule,
			actions: editingRule.actions.map(
				({error: _error, readOnly: _readOnly, ...action}) => action
			),
			conditions: conditions?.map(
				({error: _error, ...condition}) => condition
			),
		};

		if (rule.id) {
			dispatch(
				updateRule({
					...rule,
					ruleId: rule.id,
				})
			).then(() =>
				openToast({
					message: Liferay.Language.get(
						'the-rule-was-updated-successfully'
					),
					type: 'success',
				})
			);
		}
		else {
			dispatch(addRule(rule)).then(() =>
				openToast({
					message: Liferay.Language.get(
						'the-rule-was-created-successfully'
					),
					type: 'success',
				})
			);
		}

		onClose();
	}, [
		dispatch,
		editingRule,
		onClose,
		triggerRuleValidation,
		scriptInputRef,
		setScriptError,
	]);

	const title = editingRule.id
		? Liferay.Language.get('edit-rule')
		: Liferay.Language.get('new-rule');

	if (!visible) {
		return null;
	}

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="lg"
		>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{title}
			</ClayModal.Header>

			<ClayModal.Body>
				<ErrorAlert
					errors={ruleErrors}
					onClose={() => setRuleErrors([])}
				/>

				<RuleField
					errorMessage={Liferay.Language.get(
						'this-field-is-required'
					)}
					fieldId={nameId}
					hasError={nameError}
				>
					<label htmlFor={nameId}>
						{Liferay.Language.get('rule-name')}

						<ClayIcon
							className="ml-1 reference-mark"
							focusable="false"
							role="presentation"
							symbol="asterisk"
						/>
					</label>

					<ClayInput
						aria-describedby={`${nameId}-error`}
						id={nameId}
						onChange={(event) => {
							if (event.target.value) {
								setNameError(false);
							}

							updateName(event.target.value);
						}}
						ref={nameInputRef}
						value={editingRule.name}
					/>
				</RuleField>

				<p className="py-3">
					{Liferay.Language.get(
						'add-at-least-one-condition-and-one-action-to-complete-the-rule'
					)}
				</p>

				<ScreenReaderAnnouncerContextProvider>
					<div
						aria-label={Liferay.Language.get('conditions')}
						role="group"
					>
						<RuleBuilderConditionSection
							conditionType={editingRule.conditionType || 'all'}
							conditions={editingRule.conditions || []}
							script={editingRule.script}
							setRuleConditions={({
								conditionType,
								conditions,
								script,
							}) =>
								updateConditions({
									conditionType,
									conditions,
									script,
								})
							}
						/>
					</div>

					<div
						aria-label={Liferay.Language.get('actions')}
						role="group"
					>
						<RuleBuilderActionSection
							actions={editingRule.actions}
							setActions={updateActions}
						/>
					</div>
				</ScreenReaderAnnouncerContextProvider>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={onSave}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

function ErrorAlert({
	errors,
	onClose,
}: {
	errors: RuleError[];
	onClose: () => void;
}) {
	const alertRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		if (errors.length) {
			alertRef.current?.scrollIntoView?.({
				behavior: 'smooth',
				block: 'center',
			});
		}
	}, [errors]);

	if (!errors.length) {
		return null;
	}

	return (
		<div ref={alertRef}>
			<ClayAlert
				displayType="danger"
				hideCloseIcon={false}
				onClose={onClose}
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get(
					'please-review-the-following-fields-before-saving'
				)}

				{errors.length ? (
					<ul className="mb-0">
						{errors.map((error) => (
							<li key={error.element.id}>
								<a
									className="text-danger text-underline"
									href={`#${error.element.id}`}
									onClick={(event) => {
										event.preventDefault();

										error.element?.focus();
									}}
								>
									{error.message}
								</a>
							</li>
						))}
					</ul>
				) : null}
			</ClayAlert>
		</div>
	);
}
