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

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import PropTypes from 'prop-types';
import React from 'react';

function BusyButton(props) {
	const {busy, ...rest} = props;

	return busy ? (
		<ClayButton {...rest}>
			<ClayLoadingIndicator className="d-inline-block m-0" size="sm" />

			<span className="ml-2">{props.children}</span>
		</ClayButton>
	) : (
		<ClayButton {...rest} />
	);
}

BusyButton.propTypes = {
	busy: PropTypes.bool,
};

export default BusyButton;
