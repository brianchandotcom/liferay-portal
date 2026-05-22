/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {useId} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import {useRuleValidation} from '../../../app/contexts/RulesModalContext';
import {RuleError} from '../../../types/Rule';
import RuleField from './RuleField';

interface RuleMultiSelectFieldProps {
	onBlur: () => void;
	onChange: (value: string[]) => void;
	onErrorChange: (error: RuleError | null) => void;
	options: {label: string; value: string}[];
	value: string[];
}

export default function RuleMultiSelectField({
	onBlur,
	onChange,
	onErrorChange,
	options,
	value,
}: RuleMultiSelectFieldProps) {
	const [hasError, setHasError] = useState(false);
	const id = useId();
	const inputRef = useRef<HTMLInputElement | null>(null);

	const errorMessage = value.length
		? ''
		: Liferay.Language.get('select-a-value');

	const selection = value
		.map((key) => options.find((option) => option.value === key))
		.filter((option): option is {label: string; value: string} =>
			Boolean(option)
		);

	useEffect(() => {
		if (inputRef.current) {
			onErrorChange(
				errorMessage
					? {element: inputRef.current, message: errorMessage}
					: null
			);
		}
	}, [errorMessage, onErrorChange]);

	useRuleValidation(() => setHasError(Boolean(errorMessage)));

	return (
		<RuleField
			className="mb-0 page-editor__rule-builder-multi-select w-auto"
			errorMessage={errorMessage}
			fieldId={id}
			hasError={hasError}
		>
			{options.length ? (
				<ClayMultiSelect
					items={selection}
					onItemsChange={(
						items: {label: string; value: string}[]
					) => {
						const next = items
							.map((item) =>
								options.find(
									(option) =>
										option.label.toLowerCase() ===
										item.label.toLowerCase()
								)
							)
							.filter(
								(
									option
								): option is {label: string; value: string} =>
									Boolean(option)
							);

						onChange(next.map((option) => option.value));

						setHasError(false);

						onBlur();
					}}
					ref={inputRef}
					size="sm"
					sourceItems={options}
				/>
			) : (
				<ClayInput
					aria-label={Liferay.Language.get('no-options-available')}
					className="w-auto"
					id={id}
					readOnly
					ref={inputRef}
					sizing="sm"
					value={Liferay.Language.get('no-options-available')}
				/>
			)}
		</RuleField>
	);
}
