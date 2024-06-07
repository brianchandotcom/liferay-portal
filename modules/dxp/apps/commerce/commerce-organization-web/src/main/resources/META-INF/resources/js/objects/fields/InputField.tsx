/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import React, {useCallback, useEffect, useState} from 'react';

import ErrorMessage from '../ErrorMessage';
import {TGenericFieldProps} from '../FieldsWrapper';

const InputField = ({
	disabled,
	id,
	label,
	mode = 'view',
	name,
	namespace,
	onChange,
	readOnly,
	required,
	specificProps,
	value,
}: TGenericFieldProps) => {
	const [internalValue, setInternalValue] = useState(value || '');
	const [error, setError] = useState<string | null>(null);

	const onChangeHandler = useCallback(
		({target}: any) => {
			let hasError = false;
			let value = target.value;

			setInternalValue(value);

			if (
				specificProps &&
				specificProps.step &&
				specificProps.step === 1
			) {
				const number = Math.round(value);

				if (isNaN(number)) {
					value = '';
				}
				else {
					value = String(number);
				}

				setInternalValue(value);
			}

			if (required && (!value || value.trim().length <= 0)) {
				hasError = true;
				setError(Liferay.Language.get('this-field-is-required'));
			}
			else {
				setError(null);
			}

			if (specificProps && specificProps.type === 'number') {
				value = Number(value) || null;
			}

			onChange({
				hasError,
				name,
				value,
			});
		},
		[name, onChange, required, specificProps]
	);

	useEffect(() => {
		if (!readOnly && mode === 'edit') {
			onChangeHandler({target: {value}});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return mode === 'edit' ? (
		<ClayForm.Group
			className={classnames({
				'has-error': !!error,
			})}
		>
			<label htmlFor={`${namespace}${name}}`}>
				{label}

				{required && (
					<ClayIcon
						className="c-ml-1 reference-mark"
						symbol="asterisk"
					/>
				)}
			</label>

			<ClayInput
				disabled={disabled}
				id={`${namespace}${id}`}
				name={`${namespace}${name}`}
				onChange={onChangeHandler}
				readOnly={readOnly}
				required={required}
				value={internalValue}
				{...specificProps}
			/>

			<ErrorMessage error={error} />
		</ClayForm.Group>
	) : (
		<div key={`${namespace}_${id}`}>
			<div className="sidebar-dt">{label}</div>

			<div className="sidebar-dd">
				{internalValue || internalValue === 0 ? internalValue : '-'}
			</div>
		</div>
	);
};

export default InputField;
