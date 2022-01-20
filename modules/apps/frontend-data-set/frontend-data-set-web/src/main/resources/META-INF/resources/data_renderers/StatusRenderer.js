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
	const getLabelType = (label) => {
		switch (label) {
			case 'approved':
				return 'label-success';
			case 'denied':
				return 'label-danger';
			case 'expired':
				return 'label-warning';
			case 'draft':
			case 'pending':
			case 'scheduled':
				return 'label-info';
			default:
				return '';
		}
	};

	return props.value ? (
		<span className="taglib-workflow-status">
			<span className="workflow-status">
				<strong className={`label ${getLabelType(props.value.label)}`}>
					{props.value.label_i18n}
				</strong>
			</span>
		</span>
	) : null;
}

StatusRenderer.propTypes = {
	value: PropTypes.shape({
		label: PropTypes.string,
		label_i18n: PropTypes.string,
	}),
};

export default StatusRenderer;
