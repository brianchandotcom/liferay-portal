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
import React, {useMemo, useState} from 'react';

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
	const {observer, onClose} = useModal({onClose: () => onCloseModal()});

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);

	const rules = layoutData.pageRules;

	const dispatch = useDispatch();
	const nameId = useId();

	const [name, setName] = useState(
		editingRule?.name || getDefaultName(rules)
	);

	const [nameError, setNameError] = useState(false);

	const [actions, setActions] = useState(editingRule?.actions || []);
	const [conditions, setConditions] = useState(editingRule?.conditions || []);
	const [conditionType, setConditionType] = useState('all');

	const layoutDataItems = useMemo(() => {
		const items = [];

		Object.values(layoutData.items).forEach((item) => {
			if (
				item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
				item.type !== LAYOUT_DATA_ITEM_TYPES.root
			) {
				items.push({
					label: selectLayoutDataItemLabel(
						{fragmentEntryLinks},
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

		const filteredActions = actions.filter((action) => action.itemId);
		const filteredConditions = conditions.filter(
			(condition) => condition.value
		);

		if (editingRule) {
			dispatch(
				updateRule({
					actions: filteredActions,
					conditions: filteredConditions,
					name,
					ruleId: editingRule.id,
				})
			);
		}
		else {
			dispatch(
				addRule({
					actions: filteredActions,
					conditions: filteredConditions,
					name,
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

				<div
					aria-label={Liferay.Language.get('conditions')}
					role="group"
				>
					<RuleBuilderConditionSection
						conditionType={conditionType}
						conditions={conditions}
						setConditionType={setConditionType}
						setConditions={setConditions}
					/>
				</div>

				<div aria-label={Liferay.Language.get('actions')} role="group">
					<RuleBuilderActionSection
						actions={actions}
						layoutDataItems={layoutDataItems}
						setActions={setActions}
					/>
				</div>
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
