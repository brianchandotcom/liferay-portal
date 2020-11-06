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

function runJSFromText(text) {
	const scriptElement = document.createElement('script');

	scriptElement.text = text;

	document.head.appendChild(scriptElement);

	scriptElement.remove();

	return scriptElement;
}

function runJSFromFile(src) {
	const scriptElement = document.createElement('script');

	scriptElement.src = src;

	const callback = function () {
		scriptElement.remove();
	};

	scriptElement.addEventListener('load', callback);
	scriptElement.addEventListener('error', callback);

	document.head.appendChild(scriptElement);

	return scriptElement;
}

function runScript(scriptElement) {
	if (scriptElement.type && scriptElement.type !== 'text/javascript') {
		return;
	}

	scriptElement.remove();

	if (scriptElement.src) {
		return runJSFromFile(scriptElement.src);
	} else {
		return runJSFromText(scriptElement.text);
	}
}

export default function (element) {
	const scripts = element.querySelectorAll('script');

	if (!scripts.length) {
		return;
	}

	for (let index = 0; index < scripts.length; index++) {
		const scriptElement = scripts[index];

		runScript(scriptElement);
	}
}
