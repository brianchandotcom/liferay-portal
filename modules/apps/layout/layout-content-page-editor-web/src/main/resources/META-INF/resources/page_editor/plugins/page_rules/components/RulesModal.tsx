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
	useTriggerRuleValidation,
} from '../../../app/contexts/RulesModalContext';
import {useDispatch} from '../../../app/contexts/StoreContext';
import addRule from '../../../app/thunks/addRule';
import updateRule from '../../../app/thunks/updateRule';
import {
	RuleBuilderActionSection,
	RuleBuilderConditionSection,
} from './RuleBuilderSection';
import RuleField from './RuleField';

export type RuleError = {
	field: HTMLButtonElement | HTMLInputElement;
	label: string;
};

export default function RulesModal() {
	const {editingRule, visible} = useRulesModalState();
	const triggerRuleValidation = useTriggerRuleValidation();

	const {closeRulesModal, updateEditingRule} = useRulesModal();

	const dispatch = useDispatch();
	const nameId = useId();
	const nameInputRef = useRef<HTMLInputElement | null>(null);

	const [nameError, setNameError] = useState(false);
	const [ruleErrors, setRuleErrors] = useState<RuleError[]>([]);

	const {observer, onClose} = useModal({
		onClose: () => {
			setRuleErrors([]);
			closeRulesModal();
		},
	});

	const onSave = useCallback(() => {
		const errors: RuleError[] = [];

		if (!editingRule.name) {
			setNameError(true);

			errors.push({
				field: nameInputRef.current!,
				label: sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('rule-name')
				),
			});
		}

		[...editingRule.conditions, ...editingRule.actions].forEach((item) => {
			if (item.error) {
				errors.push(item.error);
			}
		});

		if (errors.length) {
			setRuleErrors(errors);
			triggerRuleValidation();

			return;
		}

		// Remove readOnly prop so it's not persisted

		const rule = {
			...editingRule,
			actions: editingRule.actions.map(
				({readOnly: _, ...action}) => action
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
	}, [dispatch, editingRule, onClose, triggerRuleValidation]);

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
					error={nameError}
					errorLabel={Liferay.Language.get('this-field-is-required')}
					fieldId={nameId}
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

							updateEditingRule({name: event.target.value});
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
							conditionType={editingRule.conditionType}
							conditions={editingRule.conditions}
							setConditionType={(conditionType) =>
								updateEditingRule({conditionType})
							}
							setConditions={(conditions) => {
								updateEditingRule({conditions});
							}}
						/>
					</div>

					<div
						aria-label={Liferay.Language.get('actions')}
						role="group"
					>
						<RuleBuilderActionSection
							actions={editingRule.actions}
							setActions={(actions) => {
								updateEditingRule({actions});
							}}
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
							<li key={error.field.id}>
								<a
									className="text-danger text-underline"
									href={`#${error.field.id}`}
									onClick={(event) => {
										event.preventDefault();

										error.field?.focus();
									}}
								>
									{error.label}
								</a>
							</li>
						))}
					</ul>
				) : null}
			</ClayAlert>
		</div>
	);
}
