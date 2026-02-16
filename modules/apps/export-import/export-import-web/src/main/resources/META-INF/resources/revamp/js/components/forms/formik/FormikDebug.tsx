/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikConsumer} from 'formik';
import React from 'react';

export function FormikDebug() {
	return (
		<div className="bg-white my-5">
			<div className="bg-primary font-weight-bold p-2 rounded-top text text-2 text-uppercase text-white">
				Formik State
			</div>

			<FormikConsumer>
				{({
					validate: _validate,
					validationSchema: _validationSchema,
					...rest
				}) => (
					<pre className="p-2 text-3">
						{JSON.stringify(rest, null, 2)}
					</pre>
				)}
			</FormikConsumer>
		</div>
	);
}
