/**
 * @returns {string} Liferay Group Id
 */
export const getLiferayGroupId = () => {
	try {
		// eslint-disable-next-line no-undef
		const groupId = Liferay.ThemeDisplay.getSiteGroupId();

		return groupId;
	} catch (error) {
		console.warn('Not able to find Liferay Group Id\n', error);

		return '';
	}
};

/**
 * @returns {string} Liferay Scope Group Id
 */
export const getScopeGroupId = () => {
	try {
		// eslint-disable-next-line no-undef
		const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();

		return scopeGroupId;
	} catch (error) {
		console.warn('Not able to find Liferay Scope Group Id\n', error);

		return '';
	}
};
