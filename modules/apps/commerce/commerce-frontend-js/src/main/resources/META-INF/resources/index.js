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

export * as CommerceComponents from './components/index';
export {default as CommerceServiceProvider} from './ServiceProvider/index';
export {default as CommerceFrontendUtils} from './utilities/interface/index';

// This is to provide a layer indirection for internal modules so that we aren't
// directly relying on a global value and can import `CommerceContext` instead.

export const CommerceContext = Liferay.CommerceContext;
