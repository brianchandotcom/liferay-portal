export const Liferay = window.Liferay || {
	BREAKPOINTS: {
		PHONE: 0,
		TABLET: 0,
	},
	ThemeDisplay: {
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => 0,
		getPathThemeImages: () => null,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
	},
	authToken: '',
};

export function getLiferaySiteName() {
	let siteName = '/web/raylife';

	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);

	siteName = `/${pathSplit.slice(0, pathSplit.length - 1).join('/')}`;

	return siteName;
}
