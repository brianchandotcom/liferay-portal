/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	deployParentProjectClientExtensions,
	deployParentProjectDeployDir,
	deployParentProjectOSGiModules,
	deployProjectClientExtensions,
	deployProjectDeployDir,
	deployProjectOSGiModules,
	startAppServer,
	updatePortalExtProperties,
	waitForStartedAppServer,
} from './appServerUtil.env';

deployParentProjectClientExtensions();

deployParentProjectDeployDir();

deployParentProjectOSGiModules();

deployProjectClientExtensions();

deployProjectDeployDir();

deployProjectOSGiModules();

updatePortalExtProperties();

startAppServer();

waitForStartedAppServer();
