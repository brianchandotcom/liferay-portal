/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isMappedToCollection from './isMappedToCollection';
import isMappedToInfoItem from './isMappedToInfoItem';
import isMappedToLayout from './isMappedToLayout';
import isMappedToStructure from './isMappedToStructure';

export default function isMapped(editable) {
	return (
		isMappedToCollection(editable) ||
		isMappedToInfoItem(editable) ||
		isMappedToStructure(editable) ||
		isMappedToLayout(editable)
	);
}
