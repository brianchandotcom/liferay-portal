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
import {useId} from 'frontend-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import selectLayoutDataItemLabel from '../../../app/selectors/selectLayoutDataItemLabel';
import addRule from '../../../app/thunks/addRule';
import updateRule from '../../../app/thunks/updateRule';
import {
	RuleBuilderActionSection,
	RuleBuilderConditionSection,
} from './RuleBuilderSection';

export default function RulesModal({editingRule, onCloseModal}) {
	const {observer, onClose} = useModal({
		onClose: () => onCloseModal(editingRule?.id),
	});

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);

	const rules = layoutData.pageRules;

	const dispatch = useDispatch();
	const nameId = useId();

	const [name, setName] = useState(
		editingRule?.name || getDefaultName(rules)
	);

	const [nameError, setNameError] = useState(false);
	const [ruleError, setRuleError] = useState(false);

	const [actions, setActions] = useState(
		() => editingRule?.actions || [{id: uuidv4()}]
	);
	const [conditions, setConditions] = useState(
		() => editingRule?.conditions || [{id: uuidv4()}]
	);
	const [conditionType, setConditionType] = useState('all');

	const layoutDataItems = useMemo(() => {
		const items = [];

		Object.values(layoutData.items).forEach((item) => {
			if (
				item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.root
			) {
				items.push({
					label: selectLayoutDataItemLabel(
						{fragmentEntryLinks, layoutData},
						item
					),
					value: item.itemId,
				});
			}
		});

		return items;
	}, [layoutData, fragmentEntryLinks]);

	const onSave = () => {
		if (!name) {
			setNameError(true);

			return;
		}

		if (
			actions.some((action) => !action.itemId) ||
			conditions.some((condition) => !condition.value)
		) {
			setRuleError(true);

			return;
		}

		if (editingRule) {
			dispatch(
				updateRule({
					actions,
					conditionType,
					conditions,
					name,
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
			dispatch(
				addRule({
					actions,
					conditionType,
					conditions,
					name,
				})
			).then(() =>
				openToast({
					message: Liferay.Language.get(
						'the-rule-was-created-successfully'
					),
					type: 'success',
				})
			);
		}

		onClose();
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

				<ScreenReaderAnnouncerContextProvider>
					<div
						aria-label={Liferay.Language.get('conditions')}
						role="group"
					>
						<RuleBuilderConditionSection
							conditionType={conditionType}
							conditions={conditions}
							setConditionType={setConditionType}
							setConditions={(conditions) => {
								setRuleError(false);

								setConditions(conditions);
							}}
						/>
					</div>

					<div
						aria-label={Liferay.Language.get('actions')}
						role="group"
					>
						<RuleBuilderActionSection
							actions={actions}
							layoutDataItems={layoutDataItems}
							setActions={(actions) => {
								setRuleError(false);

								setActions(actions);
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

function ErrorAlert({setVisible, visible}) {
	const alertRef = useRef();

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
