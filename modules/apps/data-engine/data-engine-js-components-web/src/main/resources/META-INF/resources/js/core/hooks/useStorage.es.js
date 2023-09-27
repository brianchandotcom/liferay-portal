/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const localDataStorage = {
	components: new Map(),
};

/**
 * Simple implementation to create a local storage that can be shared
 * independently of the React tree, Form Renderer is not an application
 * and can be reused more than once on the same page, so we need to share
 * requests and fields, they are loaded on demand, so as not to make
 * unnecessary requests. Use the `useStorage` hook as a way to cache data.
 */
export function useStorage() {
	return localDataStorage;
}
