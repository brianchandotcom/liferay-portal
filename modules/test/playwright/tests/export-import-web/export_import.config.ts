/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const exportImportConfig = {
	environment: {
		tomcatTempDir: process.env.TOMCAT_DIR
			? process.env.TOMCAT_DIR
			: '/home/me/Projects/eng/bundles/tomcat-9.0.102/temp'
	}
};
