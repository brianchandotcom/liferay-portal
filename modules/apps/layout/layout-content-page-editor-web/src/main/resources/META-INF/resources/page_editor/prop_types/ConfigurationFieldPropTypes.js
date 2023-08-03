/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

const ConfigurationFieldPropTypes = {
	dataType: PropTypes.string,
	defaultValue: PropTypes.oneOfType([
		PropTypes.array,
		PropTypes.bool,
		PropTypes.number,
		PropTypes.object,
		PropTypes.string,
	]),
	description: PropTypes.string,
	label: PropTypes.string,
	localizable: PropTypes.bool,
	name: PropTypes.string,
	type: PropTypes.string,
};

export default ConfigurationFieldPropTypes;
