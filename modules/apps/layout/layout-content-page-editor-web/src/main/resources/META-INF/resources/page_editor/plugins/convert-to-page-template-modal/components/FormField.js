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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const FormField = ({children, error, id, name}) => {
	const hasError = Boolean(error);

	return (
		<ClayForm.Group className={classNames({'has-error': hasError})}>
			<label htmlFor={id}>
				{name}

				<ClayIcon className="reference-mark" symbol="asterisk" />
			</label>

			{children}

			{hasError && (
				<ClayForm.FeedbackGroup role="alert">
					<ClayForm.FeedbackItem>{error}</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

FormField.propTypes = {
	error: PropTypes.string,
	id: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
};

export {FormField};
export default FormField;
