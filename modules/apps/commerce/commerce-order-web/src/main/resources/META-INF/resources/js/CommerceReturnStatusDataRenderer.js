/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

export function CommerceReturnStatusDataRenderer(props) {
	const getLabelType = (label) => {
		switch (label) {
			case 'approved':
			case 'completed':
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
				return 'label-secondary';
		}
	};

	return props.value ? (
		<span className="taglib-workflow-status">
			<span className="workflow-status">
				<strong className={`label ${getLabelType(props.value.key)}`}>
					{props.value.name}
				</strong>
			</span>
		</span>
	) : null;
}

CommerceReturnStatusDataRenderer.propTypes = {
	value: PropTypes.shape({
		key: PropTypes.string,
		name: PropTypes.string,
	}),
};
