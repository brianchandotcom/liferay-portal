/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import React, {Dispatch, SetStateAction} from 'react';

// @ts-ignore

import {v4 as uuidv4} from 'uuid';

import ActionComponent, {Action} from './Action';
import ConditionComponent, {Condition} from './Condition';

const TriggerLabel = React.forwardRef<HTMLButtonElement, any>(
	({children, className: _className, onClick, ...otherProps}, ref) => (
		<ClayButton
			className="form-control-select"
			displayType="secondary"
			onClick={onClick}
			ref={ref}
			size="sm"
			{...otherProps}
		>
			{children}
		</ClayButton>
	)
);

type RuleBuilderActionProps = {
	actions: Action[];
	layoutDataItems: {label: string; value: string}[];
	setActions: Dispatch<SetStateAction<Action[]>>;
};

export function RuleBuilderActionSection({
	actions,
	layoutDataItems,
	setActions,
}: RuleBuilderActionProps) {
	return (
		<ClayPanel
			className="page-editor__rule-builder-section"
			collapsable
			defaultExpanded
			displayTitle={
				<ClayPanel.Title className="py-2">
					<div className="align-items-center d-flex">
						<ClayIcon
							className="mr-3 text-purple"
							symbol="arrow-start"
						/>

						<span className="font-weight-bold mr-3">
							{Liferay.Language.get(
								'execute-the-following-actions'
							)}
						</span>
					</div>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon
		>
			<ClayPanel.Body>
				{actions.map((action, index) => (
					<ActionComponent
						action={action}
						key={action.id}
						layoutDataItems={layoutDataItems}
						onActionChange={(action) =>
							setActions((previousActions) => {
								const newActions = [...previousActions];

								newActions[index] = action;

								return newActions;
							})
						}
						onDeleteAction={() => {
							if (actions.length === 1) {
								setActions([{id: action.id} as Action]);
							}
							else {
								setActions((previousActions) =>
									previousActions.filter(
										(_action, currentIndex) =>
											currentIndex !== index
									)
								);
							}
						}}
						showDeleteButton={actions.length > 1 || !!action.type}
					/>
				))}

				<ClayButton
					className="mt-4"
					displayType="secondary"
					onClick={() =>
						setActions((previousActions) => [
							...previousActions,
							{id: uuidv4()} as Action,
						])
					}
					size="sm"
				>
					{Liferay.Language.get('add-action')}
				</ClayButton>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

type ConditionType = 'all' | 'any';

type RuleBuilderConditionProps = {
	conditionType: ConditionType;
	conditions: Condition[];
	setConditionType: Dispatch<SetStateAction<ConditionType>>;
	setConditions: Dispatch<SetStateAction<Condition[]>>;
};

export function RuleBuilderConditionSection({
	conditionType,
	conditions,
	setConditionType,
	setConditions,
}: RuleBuilderConditionProps) {
	return (
		<ClayPanel
			className="page-editor__rule-builder-section"
			displayTitle={
				<ClayPanel.Title className="p-3 page-editor__rule-builder-section-title text-3">
					<div className="align-items-center d-flex">
						<ClayIcon
							className="arrow-icon mr-3"
							symbol="arrow-split"
						/>

						<span className="font-weight-bold mr-3">
							{Liferay.Language.get('if')}
						</span>

						<div>
							<Picker
								as={TriggerLabel}
								items={[
									{
										label: Liferay.Language.get('any'),
										value: 'any',
									},
									{
										label: Liferay.Language.get('all'),
										value: 'all',
									},
								]}
								onSelectionChange={(key: any) =>
									setConditionType(key)
								}
								selectedKey={conditionType}
							>
								{(item) => (
									<Option key={item.value}>
										{item.label}
									</Option>
								)}
							</Picker>

							<span className="font-weight-bold ml-3 mr-3">
								{Liferay.Language.get(
									'of-the-following-conditions-are-met'
								)}
							</span>
						</div>
					</div>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon
		>
			<ClayPanel.Body>
				{conditions.map((condition, index, conditions) => (
					<ConditionComponent
						condition={condition}
						key={condition.id}
						onConditionChange={(condition) =>
							setConditions((previousConditions) => {
								const newConditions = [...previousConditions];

								newConditions[index] = condition;

								return newConditions;
							})
						}
						onDeleteCondition={() => {
							if (conditions.length === 1) {
								setConditions([
									{id: condition.id} as Condition,
								]);
							}
							else {
								setConditions((previousConditions) =>
									previousConditions.filter(
										(_condition, currentIndex) =>
											currentIndex !== index
									)
								);
							}
						}}
						showDeleteButton={
							conditions.length > 1 || !!condition.type
						}
					/>
				))}

				<ClayButton
					className="mt-4"
					displayType="secondary"
					onClick={() =>
						setConditions((previousConditions) => [
							...previousConditions,
							{id: uuidv4()} as Condition,
						])
					}
					size="sm"
				>
					{Liferay.Language.get('add-condition')}
				</ClayButton>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
