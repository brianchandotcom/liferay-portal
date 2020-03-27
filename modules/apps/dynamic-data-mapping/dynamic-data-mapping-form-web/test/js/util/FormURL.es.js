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

import FormURL from '../../../src/main/resources/META-INF/resources/admin/js/util/FormURL.es';

const formInstanceId = 1;

describe('FormURL', () => {
	it('gets a blank url', () => {
		const published = false;
		const requireAuthentication = false;

		const formURL = new FormURL(
			formInstanceId,
			published,
			requireAuthentication
		);

		expect(formURL.create()).toBe('');
	});

	it('gets a valid restricted url', () => {
		const published = true;
		const requireAuthentication = true;

		const formURL = new FormURL(
			formInstanceId,
			published,
			requireAuthentication
		);

		expect(formURL.create()).toBe(
			Liferay.DDM.FormSettings.restrictedFormURL + formInstanceId
		);
	});

	it('gets a valid shared url', () => {
		const published = true;
		const requireAuthentication = false;

		const formURL = new FormURL(
			formInstanceId,
			published,
			requireAuthentication
		);

		expect(formURL.create()).toBe(
			Liferay.DDM.FormSettings.sharedFormURL + formInstanceId
		);
	});
});
