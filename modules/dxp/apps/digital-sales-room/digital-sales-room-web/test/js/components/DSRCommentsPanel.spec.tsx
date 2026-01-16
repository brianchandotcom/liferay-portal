/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen, waitFor} from '@testing-library/react';

// @ts-ignore

import fetchMock from 'fetch-mock';
import React from 'react';

import DigitalSalesRoomService from '../../../src/main/resources/META-INF/resources/js/commons/DigitalSalesRoomService';
import DSRCommentsPanel from '../../../src/main/resources/META-INF/resources/js/components/DSRCommentsPanel';

const renderComponent = (roomId: number) => {
	return render(<DSRCommentsPanel roomId={roomId}></DSRCommentsPanel>);
};

describe('DSRCommentsPanel', () => {
	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('loads comments', async () => {
		const spyOnGetComments = jest.spyOn(
			DigitalSalesRoomService,
			'getComments'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-rooms\/.*\/comments.*/i,
			() => {
				return {
					items: [
						{
							creator: {
								id: 102,
								name: 'Author 1',
							},
							dateCreated: new Date(),
							id: 100,
							text: 'Comment 1',
						},
						{
							creator: {
								id: 100,
								name: 'Author 2',
							},
							dateCreated: new Date(),
							id: 101,
							text: 'Comment 2',
						},
					],
				};
			}
		);

		renderComponent(100);

		expect(spyOnGetComments).toBeCalledTimes(1);
		expect(spyOnGetComments).toBeCalledWith(100, 1);

		await waitFor(() => {
			expect(screen.getByText('Author 1')).toBeInTheDocument();
			expect(screen.getByText('Author 2')).toBeInTheDocument();
			expect(screen.getByText('Comment 1')).toBeInTheDocument();
			expect(screen.getByText('Comment 2')).toBeInTheDocument();
		});
	});

	it('Load more comments', async () => {
		const spyOnGetComments = jest.spyOn(
			DigitalSalesRoomService,
			'getComments'
		);

		fetchMock.get(
			/headless-digital-sales-room\/.*\/digital-sales-rooms\/.*\/comments.*/i,
			() => {
				return {
					items: [],
					lastPage: 2,
				};
			}
		);

		renderComponent(100);

		expect(spyOnGetComments).toBeCalledTimes(1);
		expect(spyOnGetComments).toBeCalledWith(100, 1);

		await waitFor(() => {
			expect(screen.getByTestId('loadMoreButton')).toBeInTheDocument();
		});

		await waitFor(() => {
			screen.getByTestId('loadMoreButton').click();
		});

		expect(spyOnGetComments).toBeCalledWith(100, 2);
		expect(screen.queryByTestId('loadMoreButton')).not.toBeInTheDocument();
	});
});
