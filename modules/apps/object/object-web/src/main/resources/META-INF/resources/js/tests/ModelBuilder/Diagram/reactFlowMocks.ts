/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// From: https://reactflow.dev/learn/advanced-use/testing#using-jest
// To make sure that the tests are working, it's important that you are using
// this implementation of ResizeObserver and DOMMatrixReadOnly

class ResizeObserver {
	callback: globalThis.ResizeObserverCallback;

	constructor(callback: globalThis.ResizeObserverCallback) {
		this.callback = callback;
	}

	observe(target: Element) {
		this.callback([{target} as globalThis.ResizeObserverEntry], this);
	}

	unobserve() {}

	disconnect() {}
}

class DOMMatrixReadOnly {
	m22: number;
	constructor(transform: string) {
		const scale = transform?.match(/scale\(([1-9.])\)/)?.[1];
		this.m22 = scale !== undefined ? +scale : 1;
	}
}

// Only run the shim once when requested

let init = false;

export function mockReactFlow() {
	if (init) {
		return;
	}
	init = true;

	// @ts-ignore

	global.ResizeObserver = ResizeObserver;

	// @ts-ignore

	global.DOMMatrixReadOnly = DOMMatrixReadOnly;

	// @ts-ignore

	Object.defineProperties(global.HTMLElement.prototype, {
		offsetHeight: {
			get() {
				return parseFloat(this.style.height) || 1;
			},
		},
		offsetWidth: {
			get() {
				return parseFloat(this.style.width) || 1;
			},
		},
	});

	// @ts-ignore

	(global.SVGElement as any).prototype.getBBox = () => ({
		height: 0,
		width: 0,
		x: 0,
		y: 0,
	});
}
