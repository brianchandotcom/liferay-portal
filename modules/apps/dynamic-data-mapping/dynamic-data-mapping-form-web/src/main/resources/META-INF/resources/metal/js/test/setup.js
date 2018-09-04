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
	ThemeDisplay: {
		getLanguageId: () => 'en_US'
	}
};

window.themeDisplay = window.Liferay.ThemeDisplay;