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

'use strict';

import runScriptsInElement from '../../../src/main/resources/META-INF/resources/liferay/util/run_scripts_in_element.es';

describe('runScriptsInElement', () => {
	afterEach(() => {
		window.testScript = undefined;
	});

	it('evaluates script code in global scope', () => {
		const scriptElement = document.createElement('div');

		scriptElement.innerHTML = '<script>window.testScript = 2 + 2;</script>';

		runScriptsInElement(scriptElement);

		expect(window.testScript).toEqual(4);
	});

	it('evaluates multiple scripts in order', () => {
		const scriptElement = document.createElement('div');

		scriptElement.innerHTML =
			'<script>window.testScript = [1];</script><script>window.testScript.push(2);</script><script>window.testScript.push(3);</script>';

		runScriptsInElement(scriptElement);

		expect(window.testScript).toEqual([1, 2, 3]);
	});

	it('does not leave created script tag in document after code is evaluated', () => {
		const scriptElement = document.createElement('script');

		scriptElement.text = 'window.testScript = 2 + 2;';

		runScriptsInElement(scriptElement);

		expect(scriptElement.parentNode).toBeNull();
	});

	it('does not evaluate script element with type different from javascript', () => {
		const scriptElement = document.createElement('script');

		scriptElement.text = 'window.testScript = 2 + 2;';
		scriptElement.type = 'text/plain';

		runScriptsInElement(scriptElement);

		expect(window.testScript).toBe(undefined);
	});

	it.skip('evaluate script file in global scope', () => {
		const scriptElement = document.createElement('script');

		scriptElement.src = '/some/path/to/script.js';

		runScriptsInElement(scriptElement);

		expect(window.testScript).toBe(5);
	});
});
