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

function runJSFromText(text, next, appendFn) {
	const scriptElement = document.createElement('script');

	scriptElement.text = text;

	if (appendFn) {
		appendFn(scriptElement);
	}
	else {
		document.head.appendChild(scriptElement);
	}

	scriptElement.remove();

	next();
}

function runJSFromFile(src, next, appendFn) {
	const scriptElement = document.createElement('script');

	scriptElement.src = src;

	const callback = function () {
		scriptElement.remove();

		next();
	};

	scriptElement.addEventListener('load', callback, {once: true});
	scriptElement.addEventListener('error', callback, {once: true});

	if (appendFn) {
		appendFn(scriptElement);
	}
	else {
		document.head.appendChild(scriptElement);
	}
}

function runScriptsInOrder(scripts, i, defaultFn, appendFn) {
	const scriptElement = scripts[i];

	const runNextScript = () => {
		if (i < scripts.length - 1) {
			runScriptsInOrder(scripts, i + 1, defaultFn, appendFn);
		}
		else if (defaultFn) {
			setTimeout(defaultFn);
		}
	};

	if (!scriptElement) {
		return;
	}
	else if (scriptElement.type && scriptElement.type !== 'text/javascript') {
		runNextScript();
	}
	else {
		scriptElement.remove();

		if (scriptElement.src) {
			runJSFromFile(scriptElement.src, runNextScript, appendFn);
		}
		else {
			runJSFromText(scriptElement.text, runNextScript, appendFn);
		}
	}
}

export default function (element, defaultFn, appendFn) {
	const scripts = element.querySelectorAll('script');

	if (!scripts.length && defaultFn) {
		setTimeout(defaultFn);

		return;
	}

	runScriptsInOrder(scripts, 0, defaultFn, appendFn);
}
