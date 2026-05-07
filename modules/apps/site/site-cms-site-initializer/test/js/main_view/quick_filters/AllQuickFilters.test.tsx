/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import AllQuickFilters from '../../../../src/main/resources/META-INF/resources/js/main_view/quick_filters/AllQuickFilters';
import {mockFetch} from '../../__mocks__/frontend-js-web';

const mockSetAllFDSState = jest.fn();

let mockFDSState: any = {
	filters: [
		{active: false, id: 'status', selectedData: {selectedItems: []}},
		{active: false, id: 'dateExpiration', selectedData: {}},
		{active: false, id: 'dateReview', selectedData: {}},
	],
	search: {query: ''},
};

jest.mock('@liferay/frontend-js-state-web/react', () => ({
	useLiferayState: () => [mockFDSState, mockSetAllFDSState],
}));

const liferayEventHandlers: Record<string, Function[]> = {};

(global as any).Liferay = {
	Language: {
		get: jest.fn((key: string) => key),
	},
	detach: jest.fn((event: string, handler: Function) => {
		liferayEventHandlers[event] = (
			liferayEventHandlers[event] ?? []
		).filter((registered) => registered !== handler);
	}),
	fire: jest.fn((event: string, payload?: unknown) => {
		(liferayEventHandlers[event] ?? []).forEach((handler) =>
			handler(payload)
		);
	}),
	on: jest.fn((event: string, handler: Function) => {
		liferayEventHandlers[event] = [
			...(liferayEventHandlers[event] ?? []),
			handler,
		];
	}),
};

function findChipButton(label: string) {
	return screen.getByText(label).closest('button') as HTMLButtonElement;
}

describe('AllQuickFilters', () => {
	beforeEach(() => {
		mockFetch.mockClear();
		mockSetAllFDSState.mockClear();
		Object.keys(liferayEventHandlers).forEach(
			(key) => delete liferayEventHandlers[key]
		);

		mockFDSState = {
			filters: [
				{
					active: false,
					id: 'status',
					selectedData: {selectedItems: []},
				},
				{active: false, id: 'dateExpiration', selectedData: {}},
				{active: false, id: 'dateReview', selectedData: {}},
			],
			search: {query: ''},
		};
	});

	it('renders the four chips with localized labels and zero counts before the fetch resolves', () => {
		mockFetch.mockReturnValueOnce(new Promise(() => {}));

		render(<AllQuickFilters />);

		expect(screen.getByText('in-draft')).toBeInTheDocument();
		expect(screen.getByText('expiring-soon')).toBeInTheDocument();
		expect(screen.getByText('expired')).toBeInTheDocument();
		expect(screen.getByText('review-date-overdue')).toBeInTheDocument();

		const counts = screen.getAllByText('0');
		expect(counts).toHaveLength(4);
	});

	it('fetches the asset statistics from the headless endpoint and renders each count', async () => {
		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 5,
				expiringSoonCount: 3,
				inDraftCount: 7,
				reviewDateOverdueCount: 2,
			}),
			ok: true,
		} as Response);

		render(<AllQuickFilters />);

		await waitFor(() => {
			expect(mockFetch).toHaveBeenCalledWith(
				'/o/headless-cms/v1.0/asset-statistics',
				expect.objectContaining({
					headers: expect.objectContaining({
						Accept: 'application/json',
					}),
				})
			);
		});

		expect(await screen.findByText('7')).toBeInTheDocument();
		expect(screen.getByText('3')).toBeInTheDocument();
		expect(screen.getByText('5')).toBeInTheDocument();
		expect(screen.getByText('2')).toBeInTheDocument();
	});

	it('falls back to zero counts when the fetch fails', async () => {
		const consoleError = jest
			.spyOn(console, 'error')
			.mockImplementation(() => {});

		mockFetch.mockResolvedValueOnce({
			ok: false,
			status: 500,
		} as Response);

		render(<AllQuickFilters />);

		await waitFor(() => {
			expect(consoleError).toHaveBeenCalled();
		});

		expect(screen.getAllByText('0')).toHaveLength(4);

		consoleError.mockRestore();
	});

	it('applies the draft status filter when the In Draft chip is clicked', async () => {
		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 0,
				expiringSoonCount: 0,
				inDraftCount: 1,
				reviewDateOverdueCount: 0,
			}),
			ok: true,
		} as Response);

		render(<AllQuickFilters />);

		await screen.findByText('1');

		await userEvent.click(findChipButton('in-draft'));

		expect(mockSetAllFDSState).toHaveBeenCalledWith(
			expect.objectContaining({
				filters: expect.arrayContaining([
					expect.objectContaining({
						active: true,
						id: 'status',
						selectedData: expect.objectContaining({
							selectedItems: [{label: 'draft', value: 2}],
						}),
					}),
				]),
			})
		);
	});

	it('applies the approved status and the upcoming expiration date range when the Expiring Soon chip is clicked', async () => {
		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 0,
				expiringSoonCount: 1,
				inDraftCount: 0,
				reviewDateOverdueCount: 0,
			}),
			ok: true,
		} as Response);

		render(<AllQuickFilters />);

		await screen.findByText('1');

		await userEvent.click(findChipButton('expiring-soon'));

		const updatedState = mockSetAllFDSState.mock.calls[0][0];

		const statusFilter = updatedState.filters.find(
			(filter: {id: string}) => filter.id === 'status'
		);

		expect(statusFilter).toEqual(
			expect.objectContaining({
				active: true,
				selectedData: expect.objectContaining({
					selectedItems: [{label: 'approved', value: 0}],
				}),
			})
		);

		const expirationFilter = updatedState.filters.find(
			(filter: {id: string}) => filter.id === 'dateExpiration'
		);

		expect(expirationFilter).toEqual(
			expect.objectContaining({
				active: true,
				selectedData: expect.objectContaining({
					from: expect.objectContaining({
						day: expect.any(Number),
						month: expect.any(Number),
						year: expect.any(Number),
					}),
					to: expect.objectContaining({
						day: expect.any(Number),
						month: expect.any(Number),
						year: expect.any(Number),
					}),
				}),
			})
		);
	});

	it('applies the expired status filter when the Expired chip is clicked', async () => {
		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 1,
				expiringSoonCount: 0,
				inDraftCount: 0,
				reviewDateOverdueCount: 0,
			}),
			ok: true,
		} as Response);

		render(<AllQuickFilters />);

		await screen.findByText('1');

		await userEvent.click(findChipButton('expired'));

		expect(mockSetAllFDSState).toHaveBeenCalledWith(
			expect.objectContaining({
				filters: expect.arrayContaining([
					expect.objectContaining({
						active: true,
						id: 'status',
						selectedData: expect.objectContaining({
							selectedItems: [{label: 'expired', value: 3}],
						}),
					}),
				]),
			})
		);
	});

	it('refetches the asset statistics when the FDS display is updated', async () => {
		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 1,
				expiringSoonCount: 0,
				inDraftCount: 0,
				reviewDateOverdueCount: 0,
			}),
			ok: true,
		} as Response);

		render(<AllQuickFilters />);

		await screen.findByText('1');

		expect(mockFetch).toHaveBeenCalledTimes(1);

		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 4,
				expiringSoonCount: 0,
				inDraftCount: 0,
				reviewDateOverdueCount: 0,
			}),
			ok: true,
		} as Response);

		(global as any).Liferay.fire('fds-display-updated');

		await screen.findByText('4');

		expect(mockFetch).toHaveBeenCalledTimes(2);
	});

	it('applies the review date upper bound when the Review Date Overdue chip is clicked', async () => {
		mockFetch.mockResolvedValueOnce({
			json: async () => ({
				expiredCount: 0,
				expiringSoonCount: 0,
				inDraftCount: 0,
				reviewDateOverdueCount: 1,
			}),
			ok: true,
		} as Response);

		render(<AllQuickFilters />);

		await screen.findByText('1');

		await userEvent.click(findChipButton('review-date-overdue'));

		const updatedState = mockSetAllFDSState.mock.calls[0][0];

		const reviewFilter = updatedState.filters.find(
			(filter: {id: string}) => filter.id === 'dateReview'
		);

		expect(reviewFilter).toEqual(
			expect.objectContaining({
				active: true,
				selectedData: expect.objectContaining({
					from: null,
					to: expect.objectContaining({
						day: expect.any(Number),
						month: expect.any(Number),
						year: expect.any(Number),
					}),
				}),
			})
		);
	});
});
