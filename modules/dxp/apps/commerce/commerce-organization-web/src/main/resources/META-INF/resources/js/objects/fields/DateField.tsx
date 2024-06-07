/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import moment from 'moment';
import React, {useCallback, useEffect, useState} from 'react';

import ErrorMessage from '../ErrorMessage';
import {TGenericFieldProps} from '../FieldsWrapper';

const DateField = ({
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
	const [internalValue, setInternalValue] = useState(value || null);
	const [error, setError] = useState<string | null>(null);

	const onChangeHandler = useCallback(
		(value: string) => {
			let hasError = false;
			let isoDateString = null;

			if (value) {
				value = moment(value.replace(/(--)/gi, '00')).format(
					'YYYY-MM-DD' +
						(specificProps && specificProps.time ? ' HH:mm' : '')
				);
			}

			setInternalValue(value);

			if (required && value.trim().length <= 0) {
				hasError = true;
				setError(Liferay.Language.get('this-field-is-required'));
			}
			else {
				try {
					if (value) {
						isoDateString = new Date(value).toISOString();
					}
					setError(null);
				}
				catch (error) {
					hasError = true;
					setError(
						Liferay.Language.get('the-field-value-is-invalid')
					);
				}
			}

			onChange({
				hasError,
				name,
				value: isoDateString || null,
			});
		},
		[name, onChange, required, specificProps]
	);

	useEffect(() => {
		if (!readOnly && mode === 'edit') {
			onChangeHandler(value || '');
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

			<ClayDatePicker
				disabled={disabled || readOnly}
				id={`${namespace}${id}`}
				inputName={`${namespace}${name}`}
				onChange={onChangeHandler}
				value={internalValue}
				years={{
					end: moment().year() + 100,
					start: moment().year() - 100,
				}}
				{...specificProps}
			/>

			<ErrorMessage error={error} />
		</ClayForm.Group>
	) : (
		<div key={`${namespace}_${id}`}>
			<div className="sidebar-dt">{label}</div>

			<div className="sidebar-dd">
				{internalValue
					? moment(internalValue).format(
							'YYYY-MM-DD' +
								(specificProps && specificProps.time
									? ' HH:mm'
									: '')
						)
					: '-'}
			</div>
		</div>
	);
};

export default DateField;
