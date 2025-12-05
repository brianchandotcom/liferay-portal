/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import {usePrevious} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import React, {MutableRefObject, useEffect, useRef, useState} from 'react';

import {useRuleValidation} from '../../../app/contexts/RulesModalContext';
import {getSelectOptions} from '../../../common/getSelectOptions';
import {RuleError} from '../../../types/Rule';
import RuleField from './RuleField';

const TriggerLabel = React.forwardRef<HTMLButtonElement, any>(
	(
		{children, className: _className, onClick, triggerRef, ...otherProps},
		ref
	) => {
		useEffect(() => {
			if (ref && triggerRef) {

				// @ts-ignore
				// False positive - react-compiler/react-compiler
				// eslint-disable-next-line react-compiler/react-compiler
				triggerRef.current = ref.current;
			}
		});

		return (
			<ClayButton
				className={classNames(
					'form-control form-control-select form-control-sm'
				)}
				displayType="secondary"
				onClick={onClick}
				ref={ref}
				size="sm"
				{...otherProps}
			>
				{children}
			</ClayButton>
		);
	}
);

interface RuleSelectProps<T> {
	'aria-label'?: string;
	'items': ReadonlyArray<{label: string; value: T}>;
	'onErrorChange': (error: RuleError | null) => void;
	'onSelectionChange': (selection: T) => void;
	'readOnly'?: boolean;
	'selectedKey'?: string;
	'triggerRef'?: MutableRefObject<HTMLButtonElement | undefined>;
}

export default function RuleSelect<T extends string>({
	'aria-label': label = '',
	items,
	onErrorChange,
	onSelectionChange,
	readOnly,
	selectedKey,
	triggerRef,
	...otherProps
}: RuleSelectProps<T>) {
	const [error, setError] = useState<boolean>(false);
	const id = useId();
	const inputRef = useRef<HTMLInputElement | null>(null);
	const previousSelectedKey = usePrevious(selectedKey);

	const fieldRef = inputRef || triggerRef;

	useEffect(() => {
		if (!previousSelectedKey && fieldRef?.current) {
			onErrorChange(
				selectedKey ? null : {field: fieldRef.current, label}
			);
		}
	}, [label, id, fieldRef, onErrorChange, previousSelectedKey, selectedKey]);

	useRuleValidation(() => setError(!selectedKey && !previousSelectedKey));

	if (readOnly) {
		const item = items.find(({value}) => value === selectedKey);

		return (
			<ClayInput
				aria-label={item?.label}
				className="w-auto"
				readOnly
				sizing="sm"
				value={item?.label}
			/>
		);
	}

	return (
		<RuleField
			className="mb-0 page-editor__rule-builder-select w-100"
			error={error}
			errorLabel={label}
			fieldId={id}
		>
			{items.length ? (
				<Picker
					aria-label={label}
					as={TriggerLabel}
					id={id}
					items={getSelectOptions(items)}
					key={
						selectedKey === undefined && previousSelectedKey ? 0 : 1
					}
					messages={{
						itemDescribedby: Liferay.Language.get(
							'you-are-currently-on-a-text-element,-inside-of-a-list-box'
						),
						itemSelected: Liferay.Language.get('x-selected'),
						scrollToBottomAriaLabel:
							Liferay.Language.get('scroll-to-bottom'),
						scrollToTopAriaLabel:
							Liferay.Language.get('scroll-to-top'),
					}}
					onSelectionChange={(selection: React.Key) => {
						onSelectionChange(selection as T);

						setError(false);
					}}
					placeholder={Liferay.Language.get('select')}
					selectedKey={selectedKey}
					triggerRef={fieldRef}
					{...(error && {'aria-describedby': `${id}-error`})}
					{...otherProps}
				>
					{(item) => <Option key={item.value}>{item.label}</Option>}
				</Picker>
			) : (
				<ClayInput
					aria-label={label}
					className="w-auto"
					id={id}
					readOnly
					ref={inputRef}
					sizing="sm"
					value={Liferay.Language.get('no-options-available')}
					{...(error && {'aria-describedby': `${id}-error`})}
				/>
			)}
		</RuleField>
	);
}
