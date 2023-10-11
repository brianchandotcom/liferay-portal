/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {addRule} from '../../../app/actions/index';
import updateRule from '../../../app/actions/updateRule';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import RulesService from '../../../app/services/RulesService';
import {
	RuleBuilderActionSection,
	RuleBuilderConditionSection,
} from './RuleBuilderSection';

export default function RulesModal({editingRule, onCloseModal}) {
	const {observer, onClose} = useModal({onClose: () => onCloseModal()});

	const layoutData = useSelector((state) => state.layoutData);

	const dispatch = useDispatch();
	const nameId = useId();

	const {rules = []} = layoutData;

	const [name, setName] = useState(
		editingRule?.name || getDefaultName(rules)
	);

	const [nameError, setNameError] = useState(false);

	const onSave = () => {
		if (!name) {
			setNameError(true);

			return;
		}

		if (editingRule) {
			const nextLayoutData = {
				...layoutData,
				rules: rules.map((rule) => {
					if (rule.id === editingRule.id) {
						return {id: editingRule.id, name};
					}

					return rule;
				}),
			};

			dispatch(
				updateRule({
					layoutData: nextLayoutData,
				})
			);
		}
		else {
			const nextLayoutData = {
				...layoutData,
				rules: [...rules, {id: nameId, name}],
			};

			dispatch(
				addRule({
					layoutData: nextLayoutData,
				})
			);
		}

		onCloseModal();
	};

	const title = editingRule
		? Liferay.Language.get('edit-rule')
		: Liferay.Language.get('new-rule');

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="lg"
		>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
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

							setName(event.target.value);
						}}
						value={name}
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

				<RuleBuilderConditionSection
					fetcher={() => RulesService.getUsers()}
				/>

				<RuleBuilderActionSection />
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

function getDefaultName(rules) {
	const nameIsUsed = (rules, name) =>
		rules.some((rule) => rule.name === name);

	let name = Liferay.Language.get('rule');
	let suffix = 0;

	while (nameIsUsed(rules, name)) {
		suffix++;

		name = `${Liferay.Language.get('rule')} ${suffix}`;
	}

	return name;
}
