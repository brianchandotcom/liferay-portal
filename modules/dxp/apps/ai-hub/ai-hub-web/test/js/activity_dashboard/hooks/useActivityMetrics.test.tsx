/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook, waitFor} from '@testing-library/react';

import '@testing-library/jest-dom';

import useActivityMetrics from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/hooks/useActivityMetrics';
import {ActivityMetrics} from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/types/ActivityMetrics';

const mockGetActivityMetrics = jest.fn();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/activity_dashboard/services/ActivityMetricsService',
	() => ({
		getActivityMetrics: (...args: any[]) => mockGetActivityMetrics(...args),
	})
);

const SAMPLE_METRICS: ActivityMetrics = {
	agentsCount: 18,
	averageResponseTimeMs: 2600,
	chatbotsCount: 4,
	monthlyAllowanceLRT: 10_000,
	monthlyConsumedLRT: 8972,
	prepaidBalanceLRT: 2500,
	prepaidExpiresAt: '2027-05-21T00:00:00Z',
	totalInteractions: 1245,
};

function setTabVisible(visible: boolean) {
	Object.defineProperty(document, 'visibilityState', {
		configurable: true,
		value: visible ? 'visible' : 'hidden',
	});

	document.dispatchEvent(new Event('visibilitychange'));
}

describe('useActivityMetrics', () => {
	beforeEach(() => {
		jest.useFakeTimers();
		mockGetActivityMetrics.mockReset();
		mockGetActivityMetrics.mockResolvedValue(SAMPLE_METRICS);
		setTabVisible(true);
	});

	afterEach(() => {
		jest.useRealTimers();
	});

	it('does not fetch when the account ERC is undefined', async () => {
		const {result} = renderHook(() => useActivityMetrics(undefined));

		await waitFor(() => expect(result.current.loading).toBe(false));

		expect(mockGetActivityMetrics).not.toHaveBeenCalled();
		expect(result.current.data).toBeNull();
		expect(result.current.error).toBeNull();
	});

	it('exposes a refetch function that triggers an extra fetch', async () => {
		const {result} = renderHook(() => useActivityMetrics('ACCOUNT_ERC'));

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(1)
		);

		await act(async () => {
			await result.current.refetch();
		});

		expect(mockGetActivityMetrics).toHaveBeenCalledTimes(2);
	});

	it('fetches metrics on mount and exposes the result', async () => {
		const {result} = renderHook(() => useActivityMetrics('ACCOUNT_ERC'));

		await waitFor(() => expect(result.current.loading).toBe(false));

		expect(result.current.data).toEqual(SAMPLE_METRICS);
		expect(result.current.error).toBeNull();
		expect(mockGetActivityMetrics).toHaveBeenCalledWith('ACCOUNT_ERC');
	});

	it('pauses polling while the tab is hidden', async () => {
		renderHook(() => useActivityMetrics('ACCOUNT_ERC'));

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(1)
		);

		act(() => {
			setTabVisible(false);
		});

		act(() => {
			jest.advanceTimersByTime(120_000);
		});

		expect(mockGetActivityMetrics).toHaveBeenCalledTimes(1);
	});

	it('polls every 30 seconds', async () => {
		renderHook(() => useActivityMetrics('ACCOUNT_ERC'));

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(1)
		);

		act(() => {
			jest.advanceTimersByTime(30_000);
		});

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(2)
		);

		act(() => {
			jest.advanceTimersByTime(30_000);
		});

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(3)
		);
	});

	it('refetches when the tab becomes visible again', async () => {
		renderHook(() => useActivityMetrics('ACCOUNT_ERC'));

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(1)
		);

		act(() => {
			setTabVisible(false);
		});

		act(() => {
			setTabVisible(true);
		});

		await waitFor(() =>
			expect(mockGetActivityMetrics).toHaveBeenCalledTimes(2)
		);
	});
});
