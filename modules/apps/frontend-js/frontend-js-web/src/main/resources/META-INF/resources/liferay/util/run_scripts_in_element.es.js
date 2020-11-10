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

function runJSFromText(text, next = () => {}) {
	const scriptElement = document.createElement('script');

	scriptElement.text = text;

	document.head.appendChild(scriptElement);

	scriptElement.remove();

	next();

	return scriptElement;
}

function runJSFromFile(src, next = () => {}) {
	const scriptElement = document.createElement('script');

	scriptElement.src = src;

	const callback = function () {
		scriptElement.remove();

		next();
	};

	scriptElement.addEventListener('load', callback);
	scriptElement.addEventListener('error', callback);

	document.head.appendChild(scriptElement);

	return scriptElement;
}

function runScriptsInOrder(scripts, i) {
	const scriptElement = scripts[i];

	if (
		!scriptElement ||
		(scriptElement.type && scriptElement.type !== 'text/javascript')
	) {
		return;
	}

	const runNextScript = () => {
		if (i < scripts.length - 1) {
			runScriptsInOrder(scripts, i + 1);
		}
	};

	scriptElement.remove();

	if (scriptElement.src) {
		return runJSFromFile(scriptElement.src, runNextScript);
	}
	else {
		return runJSFromText(scriptElement.text, runNextScript);
	}
}

export default function (element) {
	const scripts = element.querySelectorAll('script');

	if (!scripts.length) {
		return;
	}

	runScriptsInOrder(scripts, 0);
}
