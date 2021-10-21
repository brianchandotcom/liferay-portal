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

import React from 'react';

import Questions from '../../../../src/main/resources/META-INF/resources/js/pages/questions/Questions.es';

import '@testing-library/jest-dom/extend-expect';
import {cleanup} from '@testing-library/react';
import {Route} from 'react-router-dom';

import {renderComponent} from '../../../helpers.es';

const mockMessageBoardSections = {
	data: {
		messageBoardSections: {
			items: [
				{
					actions: {
						'add-subcategory': {
							operation:
								'createMessageBoardSectionMessageBoardSection',
							type: 'mutation',
						},
						'add-thread': {
							operation:
								'createMessageBoardSectionMessageBoardThread',
							type: 'mutation',
						},
						'delete': {
							operation: 'deleteMessageBoardSection',
							type: 'mutation',
						},
						'get': {
							operation: 'messageBoardSection',
							type: 'query',
						},
						'replace': {
							operation: 'updateMessageBoardSection',
							type: 'mutation',
						},
						'subscribe': {
							operation: 'updateMessageBoardSectionSubscribe',
							type: 'mutation',
						},
						'unsubscribe': {
							operation: 'updateMessageBoardSectionUnsubscribe',
							type: 'mutation',
						},
					},
					id: 37201,
					messageBoardSections: {
						items: [
							{
								id: 37203,
								numberOfMessageBoardSections: 1,
								parentMessageBoardSectionId: 37201,
								subscribed: false,
								title: 'One',
							},
							{
								id: 37207,
								numberOfMessageBoardSections: 0,
								parentMessageBoardSectionId: 37201,
								subscribed: false,
								title: 'One copy',
							},
						],
					},
					numberOfMessageBoardSections: 2,
					parentMessageBoardSectionId: null,
					subscribed: false,
					title: 'Root',
				},
			],
		},
	},
};

const mockThreads = {
	data: {
		messageBoardThreads: {
			items: [
				{
					aggregateRating: null,
					articleBody: '<p>This is the test question 1</p>',
					creator: {
						id: 20126,
						image: null,
						name: 'Test Test',
					},
					dateModified: '2020-06-19T20:51:51Z',
					friendlyUrlPath: 'test-question-1',
					hasValidAnswer: false,
					headline: 'Test Question 1',
					id: 36804,
					keywords: [],
					messageBoardSection: {
						numberOfMessageBoardSections: 0,
						title: 'Portal',
					},
					numberOfMessageBoardMessages: 0,
					seen: false,
					viewCount: 0,
				},
				{
					aggregateRating: null,
					articleBody: '<p>This is the test question 2</p>',
					creator: {
						id: 20127,
						image: null,
						name: 'John Doe',
					},
					dateModified: '2020-06-20T20:00:00Z',
					friendlyUrlPath: 'test-question-2',
					hasValidAnswer: false,
					headline: 'Test Question 2',
					id: 36805,
					keywords: [],
					messageBoardSection: {
						numberOfMessageBoardSections: 0,
						title: 'Portal',
					},
					numberOfMessageBoardMessages: 0,
					seen: false,
					viewCount: 0,
				},
			],
			page: 1,
			pageSize: 20,
			totalCount: 2,
		},
	},
};

describe('Questions', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('questions shows loading animation', async () => {
		const path = '/questions/:sectionTitle';
		const route = '/questions/portal';

		global.fetch
			.mockImplementationOnce(() =>
				Promise.resolve({
					json: () => Promise.resolve(mockMessageBoardSections),
					ok: true,
					text: () =>
						Promise.resolve(
							JSON.stringify(mockMessageBoardSections)
						),
				})
			)
			.mockImplementation(() =>
				Promise.resolve({
					json: () => Promise.resolve(mockThreads),
					ok: true,
					text: () => Promise.resolve(JSON.stringify(mockThreads)),
				})
			);

		const {container, findByText} = renderComponent({
			contextValue: {siteKey: '20020'},
			fetch,
			route,
			ui: <Route component={Questions} path={path} />,
		});

		const loading = container.querySelectorAll('.loading-animation');

		expect(loading.length).toBe(1);

		await findByText('Test Question 1');

		expect(container.querySelector('.loading-animation')).toBe(null);
	});

	it('questions shows questions created by users', async () => {
		const path = '/questions/:sectionTitle';
		const route = '/questions/portal';

		global.fetch
			.mockImplementationOnce(() =>
				Promise.resolve({
					json: () => Promise.resolve(mockMessageBoardSections),
					ok: true,
					text: () =>
						Promise.resolve(
							JSON.stringify(mockMessageBoardSections)
						),
				})
			)
			.mockImplementation(() =>
				Promise.resolve({
					json: () => Promise.resolve(mockThreads),
					ok: true,
					text: () => Promise.resolve(JSON.stringify(mockThreads)),
				})
			);

		const {findByText} = renderComponent({
			contextValue: {siteKey: '20020'},
			route,
			ui: <Route component={Questions} path={path} />,
		});

		const questionHeadline1 = await findByText('Test Question 1');
		const questionBody1 = await findByText('This is the test question 1');
		const questionHeadline2 = await findByText('Test Question 2');
		const questionBody2 = await findByText('This is the test question 2');

		expect(questionHeadline1).toBeInTheDocument();
		expect(questionBody1).toBeInTheDocument();
		expect(questionHeadline2).toBeInTheDocument();
		expect(questionBody2).toBeInTheDocument();
	});
});
