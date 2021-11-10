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

const getLiferaySiteName = () => {
	let siteName = '/web/customer-portal';
	try {

		// eslint-disable-next-line no-undef
		if (!themeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		const {pathname} = new URL(themeDisplay.getCanonicalURL());
		const pathSplit = pathname.split('/').filter(Boolean);
		siteName = `/${pathSplit.slice(0, pathSplit.length - 1).join('/')}`;
	} catch (error) {
		console.warn('Not able to find Liferay PathName\n', error);
	}

	return siteName;
};

export const LiferayTheme = {
	getLiferaySiteName,
    getUserId
};