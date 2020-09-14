/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
