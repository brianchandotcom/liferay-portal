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