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

import * as ModuleInterface from '../../src/main/resources/META-INF/resources/index';
import ModuleDescriptor from '../../src/main/resources/META-INF/resources/moduleDescriptor';

describe('Commerce Frontend Module Interface Integrity', () => {
	Object.keys(ModuleDescriptor).forEach((interfaceName) => {
		describe(`${interfaceName}`, () => {
			const InterfaceDescriptor = ModuleDescriptor[interfaceName];

			it(`${interfaceName} descriptor is exhaustive`, () => {
				expect(InterfaceDescriptor.length).toEqual(
					Object.keys(ModuleInterface[interfaceName]).length
				);
			});

			InterfaceDescriptor.forEach((component) => {
				it(`${component}`, () => {
					expect(
						ModuleInterface[interfaceName][component]
					).toBeTruthy();
				});
			});
		});
	});
});
