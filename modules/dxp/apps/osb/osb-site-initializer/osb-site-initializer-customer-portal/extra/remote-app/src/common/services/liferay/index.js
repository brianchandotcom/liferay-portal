/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const getUserId = () => {
	try {
		// eslint-disable-next-line no-undef
		if (!Liferay.ThemeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		return Liferay.ThemeDisplay.getUserId();
	}
	catch (error) {
		console.warn(error.message);
	}
};

const getScopeGroupId = () => {
	try {
		// eslint-disable-next-line no-undef
		if (!Liferay.ThemeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		return Liferay.ThemeDisplay.getScopeGroupId();
	}
	catch (error) {
		console.warn(error.message);
	}
};

const getLiferaySiteName = () => {
	let siteName = '/web/customer-portal';
	try {
		// eslint-disable-next-line no-undef
		if (!Liferay.ThemeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
		const pathSplit = pathname.split('/').filter(Boolean);
		siteName = `${(pathSplit.length > 2
			? pathSplit.slice(0, pathSplit.length - 1)
			: pathSplit
		).join('/')}`;
	}
	catch (error) {
		console.warn('Not able to find Liferay PathName\n', error);
	}

	return siteName;
};

export const LiferayTheme = {
	getLiferaySiteName,
	getScopeGroupId,
	getUserId,
};
