import React from 'react';
import SummaryCardDraft from '../index';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

const MOCK_SETUP = {
	current: 0,
	steps: [
		{
			buttonProps: {
				label: 'btn label 1',
				symbol: 'home'
			},
			Description: () => 'Step 1 Description',
			title: 'Step 1 title'
		},
		{
			buttonProps: {
				label: 'btn label 2',
				symbol: 'home'
			},
			Description: () => 'Step 2 Description',
			title: 'Step 2 title'
		}
	],
	subtitle: 'Setup subtitle',
	title: 'Setup title'
};

const MOCK_SUMMARY = {
	description: 'Summary description',
	subtitle: 'Summary subtitle',
	title: 'Summary title'
};

describe('SummaryCardDraft', () => {
	afterEach(cleanup);

	it('should render component', () => {
		const {container} = render(
			<SummaryCardDraft setup={MOCK_SETUP} summary={MOCK_SUMMARY} />
		);

		expect(container).toMatchSnapshot();
	});
});

describe('SummaryCardDraft Body', () => {
	afterEach(cleanup);

	it('should render component', () => {
		const {queryByText} = render(
			<SummaryCardDraft setup={MOCK_SETUP} summary={MOCK_SUMMARY} />
		);

		expect(queryByText('Step 1 title')).toBeTruthy();
	});
});

describe('SummaryCardDraft Footer', () => {
	afterEach(cleanup);

	it('should render component', () => {
		const {queryByText} = render(
			<SummaryCardDraft setup={MOCK_SETUP} summary={MOCK_SUMMARY} />
		);

		jest.runAllTimers();

		expect(queryByText('Summary title')).toBeTruthy();
	});
});
