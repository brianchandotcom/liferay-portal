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

import {
	ALIGN_POSITIONS,
	align,
} from '../../src/main/resources/META-INF/resources/liferay/align';
import buildFragment from '../../src/main/resources/META-INF/resources/liferay/util/build_fragment';

let center;
let element;
let mutable;
let offsetParent;

window.scrollTo = jest.fn();

describe('Align', () => {
	afterEach(() => {
		center.parentNode.removeChild(center);
		element.parentNode.removeChild(element);
		mutable.parentNode.removeChild(mutable);
		offsetParent.parentNode.removeChild(offsetParent);
		window.scrollTo(0, 0);
	});

	beforeEach(() => {
		document.body.appendChild(
			buildFragment(
				'<div id="center" style="position:absolute;top:100px;left:100px;width:50px;height:50px;"></div>'
			)
		);
		document.body.appendChild(
			buildFragment(
				'<div id="element" style="position:absolute;height:25px;width:25px;"></div>'
			)
		);
		document.body.appendChild(
			buildFragment(
				'<div id="mutable" style="position:absolute;width:50px;height:50px;"></div>'
			)
		);
		document.body.appendChild(
			buildFragment(
				'<div id="offsetParent" style="position:absolute;width:500px;height:500px;top:100px;left:100px;"></div>'
			)
		);

		center = document.getElementById('center');
		element = document.getElementById('element');

		mutable = document.getElementById('mutable');
		mutable.style.top = '100px';
		mutable.style.left = '100px';
		mutable.style.bottom = '';
		mutable.style.right = '';

		offsetParent = document.getElementById('offsetParent');
	});

	it('defines constants and aliases', () => {
		expect(ALIGN_POSITIONS.BottomCenter).toBeDefined();
		expect(ALIGN_POSITIONS.BottomLeft).toBeDefined();
		expect(ALIGN_POSITIONS.BottomRight).toBeDefined();
		expect(ALIGN_POSITIONS.LeftCenter).toBeDefined();
		expect(ALIGN_POSITIONS.RightCenter).toBeDefined();
		expect(ALIGN_POSITIONS.TopCenter).toBeDefined();
		expect(ALIGN_POSITIONS.TopLeft).toBeDefined();
		expect(ALIGN_POSITIONS.TopRight).toBeDefined();

		expect(ALIGN_POSITIONS.Bottom).toBeDefined();
		expect(ALIGN_POSITIONS.Bottom).toBe(ALIGN_POSITIONS.BottomCenter);

		expect(ALIGN_POSITIONS.Left).toBeDefined();
		expect(ALIGN_POSITIONS.Left).toBe(ALIGN_POSITIONS.LeftCenter);

		expect(ALIGN_POSITIONS.Right).toBeDefined();
		expect(ALIGN_POSITIONS.Right).toBe(ALIGN_POSITIONS.RightCenter);

		expect(ALIGN_POSITIONS.Top).toBeDefined();
		expect(ALIGN_POSITIONS.Top).toBe(ALIGN_POSITIONS.TopCenter);
	});

	it('align to the bottom', () => {
		const position = align(element, center, ALIGN_POSITIONS.Bottom);
		expect(position).toBe(ALIGN_POSITIONS.Bottom);
	});

	it('does not try to find a better region to align', () => {
		mutable.style.left = '0px';

		const position = align(element, mutable, ALIGN_POSITIONS.Left, false);
		expect(position).toBe(ALIGN_POSITIONS.Left);
	});

	it('aligns to the right', () => {
		const position = align(element, center, ALIGN_POSITIONS.Right);
		expect(position).toBe(ALIGN_POSITIONS.Right);
	});

	it('aligns to the top', () => {
		const position = align(element, center, ALIGN_POSITIONS.Top);
		expect(position).toBe(ALIGN_POSITIONS.Top);
	});

	it('aligns to the left', () => {
		const position = align(element, center, ALIGN_POSITIONS.Left);
		expect(position).toBe(ALIGN_POSITIONS.Left);
	});
});
