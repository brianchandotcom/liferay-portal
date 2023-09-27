/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {generateFieldName} from '../utils/fields';

/**
 * This is a literary copy of the logic of the old LayoutProvider,
 * check the documentation for more details.
 */
const getFieldNameGenerator = (pages, generateFieldNameUsingFieldLabel) => (
	preferredName,
	currentName,
	blacklist = []
) =>
	generateFieldName(
		pages,
		preferredName,
		currentName,
		blacklist,
		generateFieldNameUsingFieldLabel
	);

export const INITIAL_CONFIG_STATE = {
	cache: {},
	generateFieldNameUsingFieldLabel: false,
	getFieldNameGenerator,
};
