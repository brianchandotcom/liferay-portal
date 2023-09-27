/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
