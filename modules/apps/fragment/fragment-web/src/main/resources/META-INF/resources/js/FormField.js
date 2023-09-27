/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function FormField({
	children,
	error,
	id,
	name,
	required = false,
}) {
	const hasError = Boolean(error);

	return (
		<ClayForm.Group className={classNames({'has-error': hasError})}>
			<label htmlFor={id}>
				{name}

				{required && (
					<ClayIcon className="reference-mark" symbol="asterisk" />
				)}
			</label>

			{children}

			{hasError && (
				<ClayForm.FeedbackGroup role="alert">
					<ClayForm.FeedbackItem>{error}</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}

FormField.propTypes = {
	error: PropTypes.string,
	id: PropTypes.string.isRequired,
	name: PropTypes.string.isRequired,
};
