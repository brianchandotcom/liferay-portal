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

import Answer from '../../../src/main/resources/META-INF/resources/js/components/Answer.es';

import '@testing-library/jest-dom/extend-expect';
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup} from '@testing-library/react';

import {markAsAnswerMessageBoardMessageQuery} from '../../../src/main/resources/META-INF/resources/js/utils/client.es';
import {renderComponent} from '../../helpers.es';

const mockAnswer = {
	actions: {
		'delete': {
			operation: 'deleteMessageBoardMessage',
			type: 'mutation',
		},
		'get': {
			operation: 'messageBoardMessage',
			type: 'query',
		},
		'replace': {
			operation: 'updateMessageBoardMessage',
			type: 'mutation',
		},
		'reply-to-message': {
			operation: 'createMessageBoardMessageMessageBoardMessage',
			type: 'mutation',
		},
		'subscribe': {
			operation: 'updateMessageBoardMessageSubscribe',
			type: 'mutation',
		},
		'unsubscribe': {
			operation: 'updateMessageBoardMessageSubscribe',
			type: 'mutation',
		},
		'update': {
			operation: 'patchMessageBoardMessage',
			type: 'mutation',
		},
	},
	aggregateRating: null,
	articleBody: '<p>my awesome answer</p>',
	creator: {
		id: 20126,
		image: null,
		name: 'Test Test',
	},
	creatorStatistics: {
		joinDate: '2020-07-30T09:44:49Z',
		lastPostDate: '2020-07-31T09:27:31Z',
		postsNumber: 12,
		rank: 'Youngling',
	},
	encodingFormat: 'html',
	friendlyUrlPath: 're-new-question',
	id: 36801,
	messageBoardMessages: {
		items: [],
	},
	myRating: null,
	showAsAnswer: true,
};

const apolloMocks = [
	{
		request: {
			query: markAsAnswerMessageBoardMessageQuery,
			variables: {
				messageBoardMessageId: mockAnswer.id,
				showAsAnswer: false,
			},
		},
		result: {
			data: {
				patchMessageBoardMessage: {
					id: 36801,
					showAsAnswer: false,
				},
			},
		},
	},
];

describe('Answer', () => {
	afterEach(cleanup);

	it('Show as a valid answer in the case that it is', async () => {
		const mockIsSignedIn = jest.fn();
		window.Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;

		global.fetch.mockImplementationOnce(() =>
			Promise.resolve({
				json: () => Promise.resolve(apolloMocks),
				ok: true,
				text: () => Promise.resolve(JSON.stringify(apolloMocks)),
			})
		);

		const {getByTestId} = renderComponent({
			fetch,
			ui: (
				<Answer
					answer={mockAnswer}
					canMarkAsAnswer={true}
					key={mockAnswer.id}
				/>
			),
		});

		let markAsAnswerButton = getByTestId('mark-as-answer-button');
		expect(markAsAnswerButton.textContent).toMatch('Unmark as answer');

		let markAsAnswerStyle = getByTestId('mark-as-answer-style');
		expect(
			markAsAnswerStyle.className.includes('questions-answer-success')
		).toBeTruthy();

		const markAsAnswerCheck = getByTestId('mark-as-answer-check');
		expect(markAsAnswerCheck).toBeInTheDocument();
		expect(
			markAsAnswerCheck.querySelectorAll(
				'.lexicon-icon-check-circle-full'
			).length
		).toBe(1);

		markAsAnswerButton.click();

		await waitForElementToBeRemoved(() =>
			getByTestId('mark-as-answer-check')
		);

		markAsAnswerButton = getByTestId('mark-as-answer-button');
		expect(markAsAnswerButton.textContent).toMatch('Mark as answer');

		markAsAnswerStyle = getByTestId('mark-as-answer-style');
		expect(
			markAsAnswerStyle.className.includes('questions-answer-success')
		).toBeFalsy();
	});
});
