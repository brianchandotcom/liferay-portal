/**
 * This fallback config exists so that users who haven't installed the
 * NPM modules yet (eg. in a pre-build, clean checkout) will still have
 * standard Prettier config in their development environments.
 */
const FALLBACK_CONFIG = {
	bracketSpacing: false,
	endOfLine: 'lf',
	jsxSingleQuote: true,
	singleQuote: true,
	tabWidth: 4,
	trailingComma: 'none',
	useTabs: true
};

function getConfig() {
	let config;

	try {
		config = require('liferay-npm-scripts/src/config/prettier');
	} catch (error) {
		console.log(`info: using fallback config in ${__filename}`);
		return FALLBACK_CONFIG;
	}

	if (JSON.stringify(FALLBACK_CONFIG) !== JSON.stringify(config)) {
		console.warn(
			`warning: The fallback config in ${__filename} is out of sync ` +
				'with the one in liferay-npm-scripts and should be updated'
		);
	}

	return config;
}

module.exports = getConfig();
