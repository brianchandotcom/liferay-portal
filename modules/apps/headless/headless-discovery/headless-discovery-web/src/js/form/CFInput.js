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
import {connect, getIn} from 'formik';
import React from 'react';

import Icon from '../Icon';
import CFErrorMessage from './CFErrorMessage';

const CFInput = (props) => {
	const {component, formik, name, required, type, ...otherProps} = props;

	const {errors, handleBlur, handleChange, touched, values} = formik;

	const error = getIn(errors, name);
	const touch = getIn(touched, name);

	return (
		<ClayForm.Group
			className={error && touch ? 'has-error' : ''}
			key={name}
		>
			<label htmlFor={name}>
				{name}
				{!!required && (
					<Icon className="reference-mark" symbol="asterisk" />
				)}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						component={component}
						name={name}
						onBlur={handleBlur}
						onChange={handleChange}
						type="text"
						value={values[name]}
						{...otherProps}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append shrink>
					<ClayInput.GroupText className="h-100">
						{type}
					</ClayInput.GroupText>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<CFErrorMessage name={name} />
		</ClayForm.Group>
	);
};

export default connect(CFInput);
