/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

import FieldLabel from './FieldLabel.es';
import FieldStatus from './FieldStatus.es';

const FormGroupWithStatus = ({
	children,
	className,
	description,
	error,
	htmlFor,
	label,
	requiredLabel,
	success,
	...otherProps
}) => (
	<ClayForm.Group
		className={`${className} ${
			error ? 'has-error' : success ? 'has-success' : ''
		}`}
		{...otherProps}
	>
		<FieldLabel htmlFor={htmlFor} required={requiredLabel} text={label} />

		{children}

		{error && <FieldStatus error status={error} />}

		{description && (
			<ClayForm.FeedbackGroup>
				<ClayForm.FeedbackItem>
					<ClayForm.Text>{description}</ClayForm.Text>
				</ClayForm.FeedbackItem>
			</ClayForm.FeedbackGroup>
		)}

		{success && <FieldStatus status={success} />}
	</ClayForm.Group>
);

export default FormGroupWithStatus;
