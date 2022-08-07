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

import {Field as FormikField, FieldProps} from 'formik';
import React from 'react';

interface IProps {
	component: JSX.Element;
	name: string;
}

const Field = ({component, name, ...props}: IProps & any) => (
	<FormikField name={name}>
		{({field, meta}: FieldProps) =>
			React.createElement(component, {field, meta, ...props})
		}
	</FormikField>
);

export default Field;
