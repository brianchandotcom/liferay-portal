(function() {
	var LiferayAUI = Liferay.AUI;

	var combine = LiferayAUI.getCombine();

	window.__CONFIG__ = {
		basePath: '',
		combine: combine,
		url: combine ? LiferayAUI.getComboPath() : Liferay.ThemeDisplay.getCDNBaseURL()
	};

	if (!combine) {
		__CONFIG__.defaultURLParams = {
			languageId: themeDisplay.getLanguageId()
		};
	}

	__CONFIG__.namespace = 'Liferay';

	__CONFIG__.explainResolutions = Liferay.EXPLAIN_RESOLUTIONS;

	__CONFIG__.exposeGlobal = Liferay.EXPOSE_GLOBAL;

	__CONFIG__.resolvePath = Liferay.RESOLVE_PATH;

	__CONFIG__.waitTimeout = Liferay.WAIT_TIMEOUT;
}());