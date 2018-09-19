window.Liferay = {
	component: (name) => {
		let component;
		if (name.endsWith('settingsDDMForm')) {
			component = {
				getField: () => {
					return {
						getValue: () => true
					}
				}
			};
		} else if (name.endsWith('translationManager')) {
			component = {
				get: (arg) => 'en_US'
			};
		}
		return component;
	},
	DDM: {
		FormSettings: {
			spritemap: '/lexicon/icons.svg',
			strings: {
				'publish-form': 'Publish Form',
				'the-form-was-published-successfully-access-it-with-this-url-x': 'the-form-was-published-successfully-access-it-with-this-url-x',
				'unpublish-form': 'Unpublish Form'
			}
		}
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