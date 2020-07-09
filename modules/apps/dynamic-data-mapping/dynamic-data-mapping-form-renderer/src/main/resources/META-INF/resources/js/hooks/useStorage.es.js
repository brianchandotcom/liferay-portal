/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
export const useStorage = () => {
	return localDataStorage;
};
