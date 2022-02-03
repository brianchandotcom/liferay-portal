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

import {getDefaultDataLayout} from '../../../../src/main/resources/META-INF/resources/data_layout_builder/js/utils/dataConverter.es';

describe('dataConverter', () => {
	it('is getting defaultDataLayout', () => {
		const dataDefinition = {
			dataDefinitionFields: [],
		};

		expect(getDefaultDataLayout(dataDefinition)).toMatchObject({
			dataLayoutPages: [{dataLayoutRows: []}],
		});
	});
});
