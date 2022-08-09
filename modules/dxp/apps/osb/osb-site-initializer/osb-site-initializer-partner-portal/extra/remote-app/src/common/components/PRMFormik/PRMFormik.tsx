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

import {Form, Formik, FormikConfig, FormikValues} from 'formik';
import React from 'react';

import Array from './components/Array';
import Field from './components/Field';
import PRMFormikPageProps from './interfaces/prmFormikPageProps';

const PRMFormik = <T extends FormikValues>({
	children,
	...props
}: FormikConfig<T>) => {
	const currentChild = React.Children.only(children) as React.ReactElement<
		PRMFormikPageProps
	>;

	return (
		<Formik
			{...props}
			validationSchema={currentChild.props.validationSchema}
		>
			<Form>{currentChild}</Form>
		</Formik>
	);
};

PRMFormik.Array = Array;
PRMFormik.Field = Field;

export default PRMFormik;
