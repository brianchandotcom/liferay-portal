/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

import getLayoutDataItemPropTypes from './getLayoutDataItemPropTypes';

const LayoutDataPropTypes = PropTypes.shape({
	items: PropTypes.objectOf(getLayoutDataItemPropTypes()),
	rootItems: PropTypes.shape({
		main: PropTypes.string,
	}),
	version: PropTypes.number,
});

export default LayoutDataPropTypes;
