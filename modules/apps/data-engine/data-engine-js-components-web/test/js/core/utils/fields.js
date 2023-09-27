/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {generateFieldName} from '../../../../src/main/resources/META-INF/resources/js/core/utils/fields';
import mockPages from '../../__mock__/mockPages.es';

describe('core/utils/fields', () => {
	describe('generateFieldName(pages, desiredName, currentName)', () => {
		it('generates a name based on the desired name', () => {
			const name = generateFieldName(
				mockPages,
				'New  Name!',
				undefined,
				undefined,
				true
			);
			expect(name).toEqual('NewName');
		});

		it('generates an incremental name when desired name is already being used', () => {
			const name = generateFieldName(
				mockPages,
				'radio',
				undefined,
				undefined,
				true
			);
			expect(name).toEqual('radio1');
		});

		it('generates an incremental name when changing desired name to an already used one', () => {
			const name = generateFieldName(
				mockPages,
				'radio!!',
				undefined,
				undefined,
				true
			);
			expect(name).toEqual('radio1');
		});

		it('fallbacks to currentName when generated name is invalid', () => {
			const name = generateFieldName(
				mockPages,
				'radio!',
				'radio',
				undefined,
				true
			);
			expect(name).toEqual('radio');
		});
	});
});
