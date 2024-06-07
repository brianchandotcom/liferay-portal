/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import React, {useCallback, useEffect, useState} from 'react';

import ErrorMessage from '../ErrorMessage';
import {TGenericFieldProps} from '../FieldsWrapper';

const BooleanField = ({
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
		(value: boolean = false) => {
			let hasError = false;

			setInternalValue(value);

			if (required && !value) {
				hasError = true;
				setError(Liferay.Language.get('this-field-is-required'));
			}
			else {
				setError(null);
			}

			if (onChange) {
				onChange({
					hasError,
					name,
					value,
				});
			}
		},
		[name, onChange, required]
	);

	useEffect(() => {
		if (!readOnly && mode === 'edit') {
			onChangeHandler(value);
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

			<ClayToggle
				disabled={disabled}
				id={`${namespace}${id}`}
				name={`${namespace}${name}`}
				onToggle={onChangeHandler}
				readOnly={readOnly}
				required={required}
				toggled={internalValue}
				{...specificProps}
			/>

			<ErrorMessage error={error} />
		</ClayForm.Group>
	) : (
		<div key={`${namespace}_${id}`}>
			<div className="sidebar-dt">{label}</div>

			<div className="sidebar-dd">
				{internalValue
					? Liferay.Language.get('true')
					: Liferay.Language.get('false')}
			</div>
		</div>
	);
};

export default BooleanField;
