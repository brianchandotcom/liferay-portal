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

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

const noop = () => {};

export const Button = ({label, onClick = noop, tooltip}) => {
	return (
		<ClayButton
			borderless
			className="ddm_template_editor__App-sidebar-button font-weight-normal text-left text-truncate w-100"
			data-tooltip-align="right"
			data-tooltip-delay="0"
			displayType="unstyled"
			key={label}
			onClick={onClick}
			title={tooltip}
		>
			{label}
		</ClayButton>
	);
};

Button.propTypes = {
	label: PropTypes.string.isRequired,
	onClick: PropTypes.func,
	tooltip: PropTypes.string.isRequired,
};
