/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

export default function getLayoutDataItemPropTypes(extraPropTypes = {}) {
	return PropTypes.shape({
		children: PropTypes.arrayOf(PropTypes.string).isRequired,
		config: PropTypes.object,
		itemId: PropTypes.string.isRequired,
		parentId: PropTypes.string,
		type: PropTypes.string.isRequired,
		...extraPropTypes,
	});
}
