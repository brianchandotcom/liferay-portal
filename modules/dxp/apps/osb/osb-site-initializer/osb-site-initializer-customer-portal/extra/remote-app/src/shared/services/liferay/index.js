const getUserId = () => {
	try {
		// eslint-disable-next-line no-undef
		if (!themeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		return themeDisplay.getUserId();
	} catch (error) {
		console.warn(error.message);
	}
};

export const LiferayTheme = {
    getUserId
};