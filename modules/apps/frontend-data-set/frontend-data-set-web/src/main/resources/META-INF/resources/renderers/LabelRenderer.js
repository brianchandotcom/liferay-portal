/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import PropTypes from 'prop-types';
import React from 'react';

function LabelRenderer({value}) {
	return value ? (
		<ClayLabel displayType={value.displayStyle || 'info'}>
			{typeof value === 'string' ? value : value.label}
		</ClayLabel>
	) : null;
}

LabelRenderer.propTypes = {
	value: PropTypes.oneOfType([
		PropTypes.shape({
			displayStyle: PropTypes.oneOf([
				'success',
				'info',
				'secondary',
				'warning',
				'danger',
			]),
			label: PropTypes.string,
		}),
		PropTypes.string,
	]),
};

export default LabelRenderer;
