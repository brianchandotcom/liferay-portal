interface ThemeDisplay {
	getCompanyGroupId: () => number;
	getScopeGroupId: () => number;
	getSiteGroupId: () => number;
}

interface ILiferay {
	authToken: string;
	themeDisplay: ThemeDisplay;
}

declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

export const Liferay = window.Liferay || {
	ThemeDisplay: {
		getCompanyGroupId: () => 0,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
	},
	authToken: '',
};
