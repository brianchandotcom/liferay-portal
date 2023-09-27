/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

import Loader from './Loader';

export default function Button({children, loading = false, ...props}) {
	return (
		<ClayButton {...props}>
			<span className="d-inline-flex page-editor__button">
				{loading && <Loader />}

				{children}
			</span>
		</ClayButton>
	);
}

Button.propTypes = {
	children: PropTypes.node.isRequired,
	loading: PropTypes.bool,
};
