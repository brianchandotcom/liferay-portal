/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm, {ClayInput} from '@clayui/form';
import classnames from 'classnames';
import React, {useState} from 'react';

import Asterisk from './Asterisk';

const ProductOptionDate = ({
	id,
	label,
	name,
	onChange,
	productOptionValues,
	required,
}) => {
	const [date, setDate] = useState(productOptionValues[0].value);
	const [errors, setErrors] = useState({});

	const handleBlur = ({target: {value}}) => {
		if (required && value === '') {
			setErrors({emptyField: true});
		}
		else {
			setErrors({});
			onChange(date);
		}
	};

	return (
		<ClayForm.Group
			className={classnames({'has-error': errors.emptyField})}
		>
			<label htmlFor={id}>
				{label}

				<Asterisk required={required} />
			</label>

			<ClayInput
				id={id}
				name={name}
				onBlur={handleBlur}
				onChange={({target: {value}}) => {
					setDate(value);
				}}
				type="date"
				value={date}
			/>

			{errors.emptyField && (
				<ClayForm.FeedbackItem>
					<ClayForm.FeedbackIndicator symbol="exclamation-full" />

					{Liferay.Language.get('this-field-is-required')}
				</ClayForm.FeedbackItem>
			)}
		</ClayForm.Group>
	);
};

export default ProductOptionDate;
