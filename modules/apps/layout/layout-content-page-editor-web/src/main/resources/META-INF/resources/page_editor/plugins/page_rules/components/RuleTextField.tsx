/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {useId} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import {useRuleValidation} from '../../../app/contexts/RulesModalContext';
import {RuleError} from '../../../types/Rule';
import RuleField from './RuleField';

interface RuleTextFieldProps {
	isNumber?: boolean;
	onBlur: () => void;
	onChange: (value: string) => void;
	onErrorChange: (error: RuleError | null) => void;
	value: string;
}

export default function RuleTextField({
	isNumber,
	onBlur,
	onChange,
	onErrorChange,
	value,
}: RuleTextFieldProps) {
	const [hasError, setHasError] = useState(false);
	const id = useId();
	const inputRef = useRef<HTMLInputElement | null>(null);

	const errorMessage = value ? '' : Liferay.Language.get('select-a-value');

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
			className="mb-0 w-auto"
			errorMessage={errorMessage}
			fieldId={id}
			hasError={hasError}
		>
			<ClayInput
				aria-label={Liferay.Language.get('value')}
				className="w-auto"
				onBlur={onBlur}
				onChange={(event) => {
					onChange(event.target.value);

					setHasError(false);
				}}
				onKeyDown={(event) => {
					if (event.key === 'Enter') {
						onBlur();
					}
				}}
				ref={inputRef}
				sizing="sm"
				step={isNumber ? 'any' : undefined}
				type={isNumber ? 'number' : 'text'}
				value={value}
			/>
		</RuleField>
	);
}
