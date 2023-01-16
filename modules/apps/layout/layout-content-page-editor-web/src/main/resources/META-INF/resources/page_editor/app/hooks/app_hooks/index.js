/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	useProductMenuHandler();
	usePreviewURL();
	useURLParser();

	return null;
}
