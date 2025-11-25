/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {ScreenReaderAnnouncerContextProvider} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {openToast, useId} from 'frontend-js-components-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {
	useRulesModal,
	useRulesModalState,
} from '../../../app/contexts/RulesModalContext';
import {useDispatch} from '../../../app/contexts/StoreContext';
import addRule from '../../../app/thunks/addRule';
import updateRule from '../../../app/thunks/updateRule';
import {
	RuleBuilderActionSection,
	RuleBuilderConditionSection,
} from './RuleBuilderSection';

export default function RulesModal() {
	const {editingRule, visible} = useRulesModalState();

	const {closeRulesModal, updateEditingRule} = useRulesModal();

	const {observer, onClose} = useModal({
		onClose: () => closeRulesModal(),
	});

	const dispatch = useDispatch();
	const nameId = useId();

	const [nameError, setNameError] = useState(false);
	const [ruleError, setRuleError] = useState(false);

	const onSave = useCallback(() => {
		if (!editingRule.name) {
			setNameError(true);

			return;
		}

		if (
			editingRule.actions.some((action) => !action.itemId) ||
			editingRule.conditions.some(
				(condition) => !condition.options?.value
			)
		) {
			setRuleError(true);

			return;
		}

		if (editingRule.id) {
			dispatch(
				updateRule({
					...editingRule,
					ruleId: editingRule.id,
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
			dispatch(addRule(editingRule)).then(() =>
				openToast({
					message: Liferay.Language.get(
						'the-rule-was-created-successfully'
					),
					type: 'success',
				})
			);
		}

		onClose();
	}, [dispatch, editingRule, onClose]);

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
				<ErrorAlert setVisible={setRuleError} visible={ruleError} />

				<ClayForm.Group
					className={classNames({'has-error': nameError})}
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
						id={nameId}
						onChange={(event) => {
							if (event.target.value) {
								setNameError(false);
							}

							updateEditingRule({name: event.target.value});
						}}
						value={editingRule.name}
					/>

					{nameError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get('this-field-is-required')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>

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
								setRuleError(false);

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
								setRuleError(false);

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
	setVisible,
	visible,
}: {
	setVisible: (visible: boolean) => void;
	visible: boolean;
}) {
	const alertRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		if (visible) {
			alertRef.current?.scrollIntoView?.({
				behavior: 'smooth',
				block: 'center',
			});
		}
	}, [visible]);

	if (!visible) {
		return null;
	}

	return (
		<div ref={alertRef}>
			<ClayAlert
				displayType="danger"
				hideCloseIcon={false}
				onClose={() => setVisible(false)}
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get(
					'the-rule-is-incomplete.-please-check-that-the-conditions-and-actions-are-completed-before-saving'
				)}
			</ClayAlert>
		</div>
	);
}
