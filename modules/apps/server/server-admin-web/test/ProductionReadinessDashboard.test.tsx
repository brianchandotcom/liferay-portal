/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {ProductionReadinessDashboard} from '../src/main/resources/META-INF/resources/js/production_readiness';

const BASE_URL = '/o/portal/x';

const SAMPLE_PAYLOAD = {
	results: [
		{
			category: 'security',
			categoryLabel: 'Security',
			currentValue: 'false',
			ignored: false,
			message: 'Security is not enabled',
			name: 'Security Enabled',
			recommendedValue: 'true',
			ruleKey: 'security-enabled',
			status: 'FAIL',
		},
		{
			category: 'security',
			categoryLabel: 'Security',
			currentValue: 'AES',
			ignored: false,
			message: 'Password encryption is enabled',
			name: 'Password Encryption',
			recommendedValue: 'AES',
			ruleKey: 'password-encryption',
			status: 'PASS',
		},
		{
			category: 'performance',
			categoryLabel: 'Performance',
			currentValue: '4G',
			ignored: true,
			message: 'Heap size differs from recommendation',
			name: 'Heap Size Upper Limit',
			recommendedValue: '8G',
			ruleKey: 'heap-size-upper-limit',
			status: 'FAIL',
		},
	],
	summary: {
		failed: 1,
		ignored: 1,
		passed: 1,
	},
};

jest.mock('frontend-js-web', () => ({
	createResourceURL: jest.fn(
		(baseURL: string, params: Record<string, string>) => ({
			toString: () =>
				`${baseURL}?${new URLSearchParams(params).toString()}`,
		})
	),
	fetch: jest.fn(),
}));

const {fetch: mockFetch} = jest.requireMock('frontend-js-web') as {
	fetch: jest.Mock;
};

function mockFetchOnce(payload: unknown) {
	mockFetch.mockResolvedValueOnce({
		json: () => Promise.resolve(payload),
		ok: true,
	});
}

function buildLargePayload(ruleCount: number) {
	return {
		results: Array.from({length: ruleCount}, (_, index) => ({
			category: index % 2 ? 'security' : 'performance',
			categoryLabel: index % 2 ? 'Security' : 'Performance',
			currentValue: String(index),
			ignored: false,
			message: `Rule ${index} failed`,
			name: `Rule ${String(index).padStart(2, '0')}`,
			recommendedValue: String(index + 1),
			ruleKey: `rule-${String(index).padStart(2, '0')}`,
			status: 'FAIL',
		})),
		summary: {
			failed: ruleCount,
			ignored: 0,
			passed: 0,
		},
	};
}

describe('ProductionReadinessDashboard', () => {
	beforeEach(() => {
		mockFetch.mockReset();
	});

	it('renders summary counts and rules grouped by category', async () => {
		mockFetchOnce(SAMPLE_PAYLOAD);

		render(<ProductionReadinessDashboard baseResourceURL={BASE_URL} />);

		await waitFor(() =>
			expect(screen.getByText('Security Enabled')).toBeInTheDocument()
		);

		const summary = document.querySelector(
			'[data-testid="production-readiness-summary"]'
		) as HTMLElement;

		expect(
			within(summary).getByText('3', {
				selector: '[data-testid="production-readiness-count-total"]',
			})
		).toBeInTheDocument();
		expect(
			within(summary).getByText('1', {
				selector: '[data-testid="production-readiness-count-passed"]',
			})
		).toBeInTheDocument();
		expect(
			within(summary).getByText('1', {
				selector: '[data-testid="production-readiness-count-failed"]',
			})
		).toBeInTheDocument();

		expect(
			screen.getByText('Security', {
				selector: '.panel-header .text-uppercase',
			})
		).toBeInTheDocument();
		expect(
			screen.getByText('Performance', {
				selector: '.panel-header .text-uppercase',
			})
		).toBeInTheDocument();
		expect(screen.getByText('Password Encryption')).toBeInTheDocument();
		expect(screen.getByText('Heap Size Upper Limit')).toBeInTheDocument();

		const failedRow = screen.getByText('Security Enabled').closest('li')!;

		expect(
			within(failedRow).getByText('Security is not enabled')
		).toBeInTheDocument();
		expect(
			within(failedRow).getByText('current-value: false')
		).toBeInTheDocument();
		expect(
			within(failedRow).getByText('recommended-value: true')
		).toBeInTheDocument();
	});

	it('filters rules with the single-select filter pills', async () => {
		mockFetchOnce(SAMPLE_PAYLOAD);

		render(<ProductionReadinessDashboard baseResourceURL={BASE_URL} />);

		await waitFor(() =>
			expect(screen.getByText('Security Enabled')).toBeInTheDocument()
		);

		const failedPill = screen.getByRole('button', {
			name: 'failed',
		});

		await userEvent.click(failedPill);

		expect(failedPill).toHaveAttribute('aria-pressed', 'true');
		expect(
			screen.getByRole('button', {name: 'all-validations'})
		).toHaveAttribute('aria-pressed', 'false');

		expect(screen.getByText('Security Enabled')).toBeInTheDocument();
		expect(
			screen.queryByText('Password Encryption')
		).not.toBeInTheDocument();
		expect(
			screen.queryByText('Heap Size Upper Limit')
		).not.toBeInTheDocument();

		await userEvent.click(
			screen.getByRole('button', {name: 'all-validations'})
		);

		expect(screen.getByText('Heap Size Upper Limit')).toBeInTheDocument();
	});

	it('renders an empty state when no rules are deployed', async () => {
		mockFetchOnce({
			results: [],
			summary: {failed: 0, ignored: 0, passed: 0},
		});

		render(<ProductionReadinessDashboard baseResourceURL={BASE_URL} />);

		await waitFor(() =>
			expect(
				screen.getByText('no-production-readiness-rules-are-deployed')
			).toBeInTheDocument()
		);
	});

	it('clicking the ignore button calls the ignore endpoint and refetches', async () => {
		mockFetchOnce(SAMPLE_PAYLOAD);
		mockFetchOnce({ruleKey: 'security-enabled'});
		mockFetchOnce({
			...SAMPLE_PAYLOAD,
			results: SAMPLE_PAYLOAD.results.map((result) =>
				result.ruleKey === 'security-enabled'
					? {...result, ignored: true}
					: result
			),
			summary: {failed: 1, ignored: 2, passed: 1},
		});

		render(<ProductionReadinessDashboard baseResourceURL={BASE_URL} />);

		await waitFor(() =>
			expect(screen.getByText('Security Enabled')).toBeInTheDocument()
		);

		const ignoreButton = screen.getByRole('button', {
			name: 'ignore: security-enabled',
		});

		await userEvent.click(ignoreButton);

		await waitFor(() => {
			expect(mockFetch).toHaveBeenCalledTimes(3);
		});

		const ignoreCall = mockFetch.mock.calls[1];
		const requestURL = ignoreCall[0] as string;

		expect(requestURL).toContain('ignore_production_readiness_rule');
		expect(ignoreCall[1]).toMatchObject({method: 'POST'});
	});

	it('renders every rule without pagination', async () => {
		mockFetchOnce(buildLargePayload(12));

		render(<ProductionReadinessDashboard baseResourceURL={BASE_URL} />);

		await waitFor(() =>
			expect(screen.getByText('Rule 00')).toBeInTheDocument()
		);

		expect(document.querySelectorAll('[data-rule-key]')).toHaveLength(12);

		expect(
			screen.queryByLabelText('Go to page, 2')
		).not.toBeInTheDocument();
	});
});
