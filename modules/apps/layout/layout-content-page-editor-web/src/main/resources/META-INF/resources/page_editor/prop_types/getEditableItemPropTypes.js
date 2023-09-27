/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

import {EDITABLE_TYPES} from '../app/config/constants/editableTypes';

export default function getEditableItemPropTypes(extraPropTypes = {}) {
	return PropTypes.shape({
		editableId: PropTypes.string.isRequired,
		fragmentEntryLinkId: PropTypes.string.isRequired,
		itemId: PropTypes.string.isRequired,
		type: PropTypes.oneOf(Object.values(EDITABLE_TYPES)),
		...extraPropTypes,
	});
}
