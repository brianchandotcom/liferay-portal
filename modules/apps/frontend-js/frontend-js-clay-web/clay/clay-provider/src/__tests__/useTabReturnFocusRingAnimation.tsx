/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, renderHook} from '@testing-library/react';

import {useTabReturnFocusRingAnimation} from '../useTabReturnFocusRingAnimation';

function dispatchVisibilityChange(hidden: boolean) {
	Object.defineProperty(document, 'hidden', {
		configurable: true,
		get: () => hidden,
	});

	act(() => {
		document.dispatchEvent(new Event('visibilitychange'));
	});
}

describe('useTabReturnFocusRingAnimation', () => {
	let addEventListenerSpy: jest.SpyInstance;
	let removeEventListenerSpy: jest.SpyInstance;

	beforeEach(() => {
		addEventListenerSpy = jest.spyOn(document, 'addEventListener');
		removeEventListenerSpy = jest.spyOn(document, 'removeEventListener');
	});

	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
		document.body.classList.remove('c-prefers-focus-ring', 'c-tab-returning');
	});

	describe('BasicRendering', () => {
		it('registers a visibilitychange listener on mount', () => {
			renderHook(() => useTabReturnFocusRingAnimation());

			expect(addEventListenerSpy).toHaveBeenCalledWith(
				'visibilitychange',
				expect.any(Function)
			);
		});

		it('removes listener on unmount when no other instances remain', () => {
			const {unmount} = renderHook(() => useTabReturnFocusRingAnimation());

			unmount();

			expect(removeEventListenerSpy).toHaveBeenCalledWith(
				'visibilitychange',
				expect.any(Function)
			);
		});
	});

	describe('IncrementalInteractions', () => {
		it('registers listener only once for multiple mounted instances', () => {
			const {unmount: unmount1} = renderHook(() =>
				useTabReturnFocusRingAnimation()
			);
			const {unmount: unmount2} = renderHook(() =>
				useTabReturnFocusRingAnimation()
			);

			expect(addEventListenerSpy).toHaveBeenCalledTimes(1);

			unmount1();
			unmount2();
		});

		it('keeps listener alive while any instance remains mounted', () => {
			const {unmount: unmount1} = renderHook(() =>
				useTabReturnFocusRingAnimation()
			);
			const {unmount: unmount2} = renderHook(() =>
				useTabReturnFocusRingAnimation()
			);

			unmount1();

			expect(removeEventListenerSpy).not.toHaveBeenCalled();

			unmount2();

			expect(removeEventListenerSpy).toHaveBeenCalledTimes(1);
		});

		it('adds c-tab-returning when tab becomes visible with focus ring active', () => {
			document.body.classList.add('c-prefers-focus-ring');

			renderHook(() => useTabReturnFocusRingAnimation());

			dispatchVisibilityChange(false);

			expect(document.body.classList.contains('c-tab-returning')).toBe(
				true
			);
		});

		it('does not add c-tab-returning when c-prefers-focus-ring is absent', () => {
			renderHook(() => useTabReturnFocusRingAnimation());

			dispatchVisibilityChange(false);

			expect(document.body.classList.contains('c-tab-returning')).toBe(
				false
			);
		});

		it('does not add c-tab-returning when tab becomes hidden', () => {
			document.body.classList.add('c-prefers-focus-ring');

			renderHook(() => useTabReturnFocusRingAnimation());

			dispatchVisibilityChange(true);

			expect(document.body.classList.contains('c-tab-returning')).toBe(
				false
			);
		});
	});
});
