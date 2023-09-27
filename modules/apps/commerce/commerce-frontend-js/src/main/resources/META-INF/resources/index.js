/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export * as CommerceComponents from './components/index';
export {default as CommerceServiceProvider} from './ServiceProvider/index';
export {default as CommerceFrontendUtils} from './utilities/interface/index';

// This is to provide a layer indirection for internal modules so that we are
// not directly relying on a global value and can import `CommerceContext`
// instead.

export const CommerceContext = Liferay.CommerceContext;
