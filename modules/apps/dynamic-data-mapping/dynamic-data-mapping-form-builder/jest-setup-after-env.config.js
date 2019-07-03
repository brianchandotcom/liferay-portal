const strings = {
	actions: 'actions',
	'add-field': 'add-field',
	and: 'and',
	'are-you-sure-you-want-to-delete-this-action':
		'are-you-sure-you-want-to-delete-this-action',
	'are-you-sure-you-want-to-delete-this-condition':
		'are-you-sure-you-want-to-delete-this-condition',
	cancel: 'cancel',
	condition: 'condition',
	'copied-to-clipboard': 'Copied to clipboard.',
	'define-condition-and-action-to-change-fields-and-elements-on-the-form':
		'define-condition-and-action-to-change-fields-and-elements-on-the-form',
	'delete-action': 'delete-action',
	'delete-condition': 'delete-condition',
	delete: 'delete',
	dismiss: 'dismiss',
	do: 'do',
	if: 'if',
	or: 'or',
	'publish-form': 'Publish Form',
	rule: 'rule',
	save: 'save',
	'the-form-was-published-successfully-access-it-with-this-url-x':
		'the-form-was-published-successfully-access-it-with-this-url-x',
	'unpublish-form': 'Unpublish Form'
};

window.Liferay = {
	component: name => {
		let component;

		if (name.endsWith('settingsDDMForm')) {
			component = {
				getField: () => {
					return {
						getValue: () => true
					};
				}
			};
		} else if (name.endsWith('translationManager')) {
			component = {
				get: arg => 'en_US'
			};
		}

		return component;
	},
	DDM: {
		FormSettings: {
			spritemap: '/lexicon/icons.svg'
		}
	},
	Language: {
		get: arg => strings[arg] || arg
	},
	Session: {
		get: arg => 'active'
	},
	ThemeDisplay: {
		getLanguageId: () => 'en_US'
	},
	Util: {
		ns: (str, obj) => obj
	}
};

window.themeDisplay = window.Liferay.ThemeDisplay;
