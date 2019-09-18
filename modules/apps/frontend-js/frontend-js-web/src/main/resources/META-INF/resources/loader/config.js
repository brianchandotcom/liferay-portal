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

(function() {
	var LiferayAUI = Liferay.AUI;

	var combine = LiferayAUI.getCombine();

	window.__CONFIG__ = {
		basePath: '',
		combine,
		reportMismatchedAnonymousModules: 'warn',
		url: combine
			? LiferayAUI.getComboPath()
			: Liferay.ThemeDisplay.getCDNBaseURL()
	};

	if (!combine) {
		__CONFIG__.defaultURLParams = {
			languageId: themeDisplay.getLanguageId()
		};
	}

	__CONFIG__.maps = Liferay.MAPS;

	__CONFIG__.modules = Liferay.MODULES;

	__CONFIG__.paths = Liferay.PATHS;

	__CONFIG__.resolvePath = Liferay.RESOLVE_PATH;

	__CONFIG__.namespace = 'Liferay';

	__CONFIG__.explainResolutions = Liferay.EXPLAIN_RESOLUTIONS;

	__CONFIG__.exposeGlobal = Liferay.EXPOSE_GLOBAL;

	__CONFIG__.logLevel = Liferay.LOG_LEVEL;

	__CONFIG__.waitTimeout = Liferay.WAIT_TIMEOUT;
})();
