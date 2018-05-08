(function() {
	var LiferayAUI = Liferay.AUI;

	var combine = LiferayAUI.getCombine();

	window.__CONFIG__ = {
		basePath: '',
		combine: combine,
		reportMismatchedAnonymousModules: 'warn',
		url: combine ? LiferayAUI.getComboPath() : Liferay.ThemeDisplay.getCDNBaseURL()
	};

	if (!combine) {
		__CONFIG__.defaultURLParams = {
			languageId: themeDisplay.getLanguageId()
		};
	}

	__CONFIG__.maps = Liferay.MAPS;

	__CONFIG__.modules = Liferay.MODULES;

	__CONFIG__.paths = Liferay.PATHS;

	__CONFIG__.namespace = 'Liferay';

	__CONFIG__.explainResolutions = Liferay.EXPLAIN_RESOLUTIONS;

	__CONFIG__.exposeGlobal = Liferay.EXPOSE_GLOBAL;
}());