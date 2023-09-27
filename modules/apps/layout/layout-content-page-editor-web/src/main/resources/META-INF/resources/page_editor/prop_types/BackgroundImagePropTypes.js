/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

const BackgroundImagePropTypes = PropTypes.oneOfType([
	PropTypes.shape({
		title: PropTypes.string,
		url: PropTypes.string,
	}),
	PropTypes.shape({
		classNameId: PropTypes.string,
		classPK: PropTypes.string,
		fieldId: PropTypes.string,
	}),
	PropTypes.shape({
		mappedField: PropTypes.string,
	}),
]);

export default BackgroundImagePropTypes;
