/* eslint-disable @liferay/no-absolute-import */
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

// @ts-ignore

import RefreshRuntime from '/@react-refresh';

/**
 * @description This file is used ONLY and EXCLUSIVE for development
 * When setting up the remote app, add this import
 * http://localhost:3000/@vite/refresh.ts
 */

RefreshRuntime.injectIntoGlobalHook(window);
window.$RefreshReg$ = () => {};
window.$RefreshSig$ = () => (type) => type;

window.__vite_plugin_react_preamble_installed__ = true;

declare global {
	interface Window {
		$RefreshReg$: () => void;
		$RefreshSig$: () => (type: string) => string;
		__vite_plugin_react_preamble_installed__: boolean;
	}
}
