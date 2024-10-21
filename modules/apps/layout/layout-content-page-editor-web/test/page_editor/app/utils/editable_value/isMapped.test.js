/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isMapped from '../../../../../src/main/resources/META-INF/resources/page_editor/app/utils/editable_value/isMapped';

describe('isMapped', () => {
	it('returns true if the editable is mapped to a structure', () => {
		expect(isMapped({mappedField: 'field1'})).toBe(true);
	});

	it('returns true if the editable is mapped to an info item', () => {
		expect(
			isMapped({classNameId: '1', classPK: '123', fieldId: 'title'})
		).toBe(true);
	});

	it('returns true if the editable is mapped to a collection', () => {
		expect(isMapped({collectionFieldId: 'field2'})).toBe(true);
	});

	it('returns false if the editable is not mapped', () => {
		expect(isMapped({})).toBe(false);
	});

	it('returns false if the editable is null', () => {
		expect(isMapped(null)).toBe(false);
	});

	it('returns false if the editable has empty classNameId, classPK, or fieldId', () => {
		expect(isMapped({classNameId: '', classPK: '', fieldId: ''})).toBe(
			false
		);
	});
});
