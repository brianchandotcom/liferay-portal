const strings = {
	'copied-to-clipboard': 'Copied to clipboard.',
	'publish-form': 'Publish Form',
	'the-form-was-published-successfully-access-it-with-this-url-x':
		'the-form-was-published-successfully-access-it-with-this-url-x',
	'unpublish-form': 'Unpublish Form'
};

window.Liferay = {
	DDM: {
		FormSettings: {
			spritemap: '/lexicon/icons.svg'
		}
	},
	Language: {
		get: arg => strings[arg]
	},
	Session: {
		get: arg => 'active'
	}
};

window.themeDisplay = window.Liferay.ThemeDisplay;
