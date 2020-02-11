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

import {cleanup} from '@testing-library/react';

import '@testing-library/jest-dom/extend-expect';

import {numberFormat} from '../../../src/main/resources/META-INF/resources/js/util/numberFormat.es';

describe('numberFormat', () => {
	afterEach(cleanup);

	it('renders numbers splitting by dots (".") with languageTag es-ES', () => {
		const number = 9812345;

		const formatted = numberFormat('es-ES', number);

		expect(formatted).toBe('9.812.345');
	});

	it('renders numbers splitting by commas (",") with languageTag en-US', () => {
		const number = 9812345;

		const formatted = numberFormat('en-US', number);

		expect(formatted).toBe('9,812,345');
	});
});
