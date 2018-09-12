window.Liferay = {
	component: (name) => {
		let component;
		if (name.endsWith('translationManager')) {
			component = {
				get: (arg) => 'en_US'
			};
		}
		return component;
	},
	Language: {
		get: key => key
	},
	Session: {
		get: (arg) => 'active'
	},
	ThemeDisplay: {
		getLanguageId: () => 'en_US'
	},
	Util: {
		ns: (str, obj) => obj
	}
};

window.themeDisplay = window.Liferay.ThemeDisplay;