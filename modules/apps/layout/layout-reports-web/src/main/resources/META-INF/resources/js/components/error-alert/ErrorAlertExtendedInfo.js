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

import PropTypes from 'prop-types';
import React from 'react';

const ErrorAlertExtendedInfo = ({error = {}}) => {
	const {code: statusCode, message, status} = error;

	if (!Object.keys(error).length) {
		return null;
	}

	return (
		<dl className="mb-0 p-0">
			{statusCode && (
				<>
					<dt>{Liferay.Language.get('error-code')}</dt>{' '}
					<dd>{statusCode}</dd>
				</>
			)}
			{message && (
				<>
					<dt>{Liferay.Language.get('error-message')}</dt>{' '}
					<dd>{message}</dd>
				</>
			)}
			{status && (
				<>
					<dt>{Liferay.Language.get('error-status')}</dt>{' '}
					<dd>{status}</dd>
				</>
			)}
		</dl>
	);
};

ErrorAlertExtendedInfo.propTypes = {
	error: PropTypes.object.isRequired,
};

export default ErrorAlertExtendedInfo;
