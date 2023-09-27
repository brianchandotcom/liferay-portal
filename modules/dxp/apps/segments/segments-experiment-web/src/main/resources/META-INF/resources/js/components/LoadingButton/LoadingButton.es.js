/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React from 'react';

function LoadingButton(props) {
	const {loading, ...rest} = props;

	return loading ? (
		<ClayButton {...rest}>
			<ClayLoadingIndicator className="d-inline-block m-0" size="sm" />

			<span className="ml-2">{props.children}</span>
		</ClayButton>
	) : (
		<ClayButton {...rest} />
	);
}

LoadingButton.propTypes = {
	loading: PropTypes.bool,
};

export default LoadingButton;
