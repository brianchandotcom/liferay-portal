/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildFragment from '../../../src/main/resources/META-INF/resources/liferay/util/build_fragment';

describe('Liferay.Util.buildFragment', () => {
	it('creates a document fragment from a string', () => {
		const html = '<div>Hello World 1</div><div>Hello World 2</div>';
		const fragment = buildFragment(html);

		expect(fragment).toBeTruthy();
		expect(fragment.nodeType).toBe(11);
		expect(fragment.childNodes.length).toBe(2);
		expect(fragment.childNodes[0].innerHTML).toBe('Hello World 1');
		expect(fragment.childNodes[1].innerHTML).toBe('Hello World 2');
	});
});