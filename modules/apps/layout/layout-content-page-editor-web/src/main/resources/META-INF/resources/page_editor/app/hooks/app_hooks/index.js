/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useBackURL from './useBackURL';
import useExtendSession from './useExtendSession';
import useLanguageDirection from './useLanguageDirection';
import usePortletConfigurationListener from './usePortletConfigurationListener';
import usePreviewURL from './usePreviewURL';
import useProductMenuHandler from './useProductMenuHandler';
import useURLParser from './useURLParser';

export default function AppHooks() {
	useBackURL();
	useExtendSession();
	useLanguageDirection();
	usePortletConfigurationListener();
	usePreviewURL();
	useProductMenuHandler();
	useURLParser();

	return null;
}
