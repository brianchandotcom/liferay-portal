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

function StatusRenderer(props) {
	return props.value ? (
		<span className="taglib-workflow-status">
			<span className="workflow-status">
				<strong
					className={`label status workflow-status-${props.value.label} ${props.value.label} workflow-value`}
				>
					{props.value.label_i18n}
				</strong>
			</span>
		</span>
	) : null;
}

StatusRenderer.propTypes = {
	value: PropTypes.shape({
		code: PropTypes.number,
		label: PropTypes.string,
		label_i18n: PropTypes.string,
	}),
};

export default StatusRenderer;
