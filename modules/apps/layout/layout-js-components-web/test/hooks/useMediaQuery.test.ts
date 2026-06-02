/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react';

import useMediaQuery from '../../src/main/resources/META-INF/resources/js/hooks/useMediaQuery';

let changeListeners: Set<() => void>;
let currentWidth: number;
let removeEventListener: jest.Mock;

function matchesWidth(query: string) {
	const minWidth = query.match(/min-width:\s*(\d+)/);
	const maxWidth = query.match(/max-width:\s*(\d+)/);

	if (minWidth) {
		return currentWidth >= Number(minWidth[1]);
	}

	if (maxWidth) {
		return currentWidth <= Number(maxWidth[1]);
	}

	return false;
}

function resizeTo(width: number) {
	currentWidth = width;

	changeListeners.forEach((listener) => listener());
}

describe('useMediaQuery', () => {
	beforeEach(() => {
		changeListeners = new Set();
		currentWidth = 0;
		removeEventListener = jest.fn((_event, listener) => {
			changeListeners.delete(listener);
		});

		Object.defineProperty(window, 'matchMedia', {
			configurable: true,
			value: jest.fn((query) => ({
				addEventListener: (_: string, listener: () => void) => {
					changeListeners.add(listener);
				},
				get matches() {
					return matchesWidth(query);
				},
				removeEventListener,
			})),
		});
	});

	it('returns true or false as the viewport is resized', () => {
		resizeTo(1200);

		const {result} = renderHook(() => useMediaQuery('(min-width: 992px)'));

		expect(result.current).toBe(true);

		act(() => resizeTo(800));

		expect(result.current).toBe(false);

		act(() => resizeTo(1024));

		expect(result.current).toBe(true);
	});

	it('removes the change listener on unmount', () => {
		resizeTo(1200);

		const {unmount} = renderHook(() => useMediaQuery('(min-width: 992px)'));

		expect(changeListeners.size).toBe(1);

		unmount();

		expect(removeEventListener).toHaveBeenCalledWith(
			'change',
			expect.any(Function)
		);
		expect(changeListeners.size).toBe(0);
	});
});
