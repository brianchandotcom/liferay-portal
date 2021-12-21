/**
 * @returns {string} Liferay Group Id
 */
export function getLiferayGroupId() {
	try {
		// eslint-disable-next-line no-undef
		return Liferay.ThemeDisplay.getSiteGroupId();
	}
	catch (error) {
		console.warn('Not able to find Liferay Group Id\n', error);

		return '';
	}
}

/**
 * @returns {string} Liferay Scope Group Id
 */
export function getScopeGroupId() {
	try {
		// eslint-disable-next-line no-undef
		return Liferay.ThemeDisplay.getScopeGroupId();
	}
	catch (error) {
		console.warn('Not able to find Liferay Scope Group Id\n', error);

		return '';
	}
}

/**
 * @returns {string} Liferay Scope Group Id
 */
export function getCompanyGroupId() {
	try {
		// eslint-disable-next-line no-undef
		return Liferay.ThemeDisplay.getCompanyGroupId();
	}
	catch (error) {
		console.warn('Not able to find Liferay Scope Group Id\n', error);

		return '';
	}
}
