/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React from 'react';

import {Condition as ConditionType, RuleError} from '../../../types/Rule';
import {DEFAULT_OPERATORS} from './FieldFragmentTypeSelector';
import RuleSelect from './RuleSelect';

export default function FormFragmentTypeSelector({
	condition,
	inputFragmentItems,
	onConditionChange,
	onErrorChange,
	sendMessage,
}: {
	condition: ConditionType;
	inputFragmentItems: {label: string; value: string}[];
	onConditionChange: (condition: ConditionType) => void;
	onErrorChange: (error: RuleError | null) => void;
	sendMessage: (message: string) => void;
}) {
	const selectedKey = inputFragmentItems.some(
		(item) => item.value === condition.field
	)
		? condition.field
		: undefined;

	return (
		<>
			<RuleSelect
				aria-label={sub(
					Liferay.Language.get('select-x-for-the-condition'),
					Liferay.Language.get('fragment')
				)}
				items={inputFragmentItems}
				onErrorChange={onErrorChange}
				onSelectionChange={(selectedFragment) => {
					onConditionChange({
						...condition,
						field: selectedFragment,
						options: undefined,
					});
				}}
				selectedKey={selectedKey}
			/>

			{condition.field ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('type')
					)}
					items={DEFAULT_OPERATORS}
					onErrorChange={onErrorChange}
					onSelectionChange={(type) => {
						onConditionChange({
							...condition,
							options: {
								type,
							},
						} as ConditionType);
					}}
					selectedKey={condition.options?.type}
				/>
			) : null}

			{condition.options?.type ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('type')
					)}
					items={[
						{
							label: Liferay.Language.get('value'),
							value: 'value',
						},
					]}
					onErrorChange={onErrorChange}
					onSelectionChange={() => {}}
					selectedKey="value"
				/>
			) : null}

			{condition.options?.type ? (
				<RuleSelect
					aria-label={sub(
						Liferay.Language.get('select-x'),
						Liferay.Language.get('value')
					)}
					items={[
						{label: Liferay.Language.get('true'), value: 'true'},
						{
							label: Liferay.Language.get('false'),
							value: 'false',
						},
					]}
					onErrorChange={onErrorChange}
					onSelectionChange={(value) => {
						onConditionChange({
							...condition,
							options: {
								...condition.options!,
								value,
							},
						} as ConditionType);

						sendMessage(
							Liferay.Language.get('condition-completed')
						);
					}}
					selectedKey={
						Array.isArray(condition.options?.value)
							? undefined
							: condition.options?.value
					}
				/>
			) : null}
		</>
	);
}
