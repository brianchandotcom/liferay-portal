/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {

	// Mock this because we can't evaluate React JSX from a Metal-using module
	// like dynamic-data-mapping-form-builder.

	openModal: () => {},
};
