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

import QuestionRow from '../../../src/main/resources/META-INF/resources/js/components/QuestionRow.es';

import '@testing-library/jest-dom/extend-expect';
import {cleanup} from '@testing-library/react';

import {renderComponent} from '../../helpers.es';

const mockQuestionWithValidAnswer = {
	aggregateRating: null,
	articleBody:
		'<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus\n  dictum ex eu tellus iaculis tristique. Etiam sapien sem, pharetra et\n  congue quis, efficitur pulvinar arcu.</p>',
	creator: {
		id: 20126,
		image: null,
		name: 'Test Test',
	},
	dateModified: '2020-07-31T12:48:56Z',
	friendlyUrlPath: 'new-question',
	hasValidAnswer: true,
	headline: 'new question',
	id: 36695,
	keywords: ['test'],
	messageBoardSection: {
		numberOfMessageBoardSections: 0,
		parentMessageBoardSectionId: null,
		title: 'Portal',
	},
	numberOfMessageBoardMessages: 2,
	seen: true,
	viewCount: 48,
};

const mockQuestionWithNoValidAnswer = {
	aggregateRating: null,
	articleBody:
		'<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus\n  dictum ex eu tellus iaculis tristique. Etiam sapien sem, pharetra et\n  congue quis, efficitur pulvinar arcu.</p>',
	creator: {
		id: 20126,
		image: null,
		name: 'Test Test',
	},
	dateModified: '2020-07-31T12:48:56Z',
	friendlyUrlPath: 'new-question',
	hasValidAnswer: false,
	headline: 'new question',
	id: 36695,
	keywords: ['test'],
	messageBoardSection: {
		numberOfMessageBoardSections: 0,
		parentMessageBoardSectionId: null,
		title: 'Portal',
	},
	numberOfMessageBoardMessages: 2,
	seen: true,
	viewCount: 54,
};

const render = ({currentSection, mock}) => ({
	...renderComponent({
		ui: (
			<QuestionRow
				currentSection={currentSection}
				key={mock.id}
				question={mock}
				showSectionLabel={!!mock.numberOfMessageBoardSections}
			/>
		),
	}),
});

describe('QuestionRow', () => {
	afterEach(() => {
		cleanup();
	});

	it('Shows a check icon if the question has a valid answer', () => {
		const {getByTestId} = render({
			currentSection: 'portal',
			mock: mockQuestionWithValidAnswer,
		});

		const hasValidAnswerBadge = getByTestId('has-valid-answer-badge');
		const checkSymbolDivElement = hasValidAnswerBadge.querySelector('div')
			.className;

		expect(
			checkSymbolDivElement.includes('alert-success border-0')
		).toBeTruthy();

		const checkSymbol = hasValidAnswerBadge.querySelector('svg');
		expect(checkSymbol.className.baseVal).toMatch(
			'lexicon-icon-check-circle-full'
		);
	});

	it('Shows a message icon if the question does not have a valid answer', () => {
		const {getByTestId} = render({
			currentSection: 'portal',
			mock: mockQuestionWithNoValidAnswer,
		});

		const hasValidAnswerBadge = getByTestId('has-valid-answer-badge');
		const checkSymbolStyle = hasValidAnswerBadge.className;

		expect(checkSymbolStyle.includes('alert-success border-0')).toBeFalsy();

		const checkSymbol = hasValidAnswerBadge.querySelector('svg');
		expect(checkSymbol.className.baseVal).toMatch('lexicon-icon-message');
	});
});
