/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
