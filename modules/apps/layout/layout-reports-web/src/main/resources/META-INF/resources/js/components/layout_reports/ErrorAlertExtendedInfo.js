/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

const ErrorAlertExtendedInfo = ({error = {}}) => {
	const {code: statusCode, message, status} = error;

	if (!Object.keys(error).length) {
		return null;
	}

	return (
		<dl className="mb-0 mt-2 p-0">
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
