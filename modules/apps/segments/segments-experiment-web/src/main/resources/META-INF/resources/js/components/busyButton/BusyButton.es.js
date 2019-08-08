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

import React from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';

function BusyButton(props) {
	const {busy, ...rest} = props;

	return busy ? (
		<ClayButton {...rest}>
			<ClayLoadingIndicator className="m-0 d-inline-block" size="sm" />

			<span className="ml-2">{props.children}</span>
		</ClayButton>
	) : (
		<ClayButton {...rest} />
	);
}

BusyButton.propTypes = {
	busy: PropTypes.bool
};

export default BusyButton;
