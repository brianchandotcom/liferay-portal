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

import {ArrayHelpers, FieldArray} from 'formik';
import React from 'react';

interface IProps {
	component: JSX.Element;
	name: string;
}

const Array = ({component, name, ...props}: IProps & any) => (
	<FieldArray name={name}>
		{(arrayHelpers: ArrayHelpers) =>
			React.createElement(component, {arrayHelpers, ...props})
		}
	</FieldArray>
);

export default Array;
