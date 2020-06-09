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

import {connect, getIn} from 'formik';
import React from 'react';

const CFErrorMessage = (props) => {
	const error = getIn(props.formik.errors, props.name);
	const touch = getIn(props.formik.touched, props.name);

	const errorDOM = (
		<div className="form-feedback-group">
			<div className="form-feedback-item">{error}</div>
		</div>
	);

	return touch && error ? errorDOM : null;
};

export default connect(CFErrorMessage);
