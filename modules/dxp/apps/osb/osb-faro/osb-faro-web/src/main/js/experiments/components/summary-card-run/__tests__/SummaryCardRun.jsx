import React from 'react';
import SummaryCardRun from '../index';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

const MOCK_ALERT = {
	description: 'Alert description',
	symbol: 'home',
	title: 'Alert title'
};

const MOCK_SECTIONS = [
	{
		Body: () => <div>{'Section 01'}</div>
	},
	{
		Body: () => <div>{'Section 02'}</div>
	},
	{
		Body: () => <div>{'Section 03'}</div>
	}
];

const MOCK_SUMMARY = {
	description: 'Summary description',
	subtitle: 'Summary subtitle',
	title: 'Summary title'
};

describe('SummaryCardRun', () => {
	afterEach(cleanup);

	it('should render component', () => {
		const {container} = render(
			<SummaryCardRun
				alert={MOCK_ALERT}
				sections={MOCK_SECTIONS}
				summary={MOCK_SUMMARY}
			/>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

describe('SummaryCardRun Paragraph', () => {
	afterEach(cleanup);

	it('should render component', () => {
		const {queryByText} = render(
			<SummaryCardRun
				alert={MOCK_ALERT}
				sections={MOCK_SECTIONS}
				summary={MOCK_SUMMARY}
			/>
		);

		jest.runAllTimers();

		expect(queryByText('Summary title')).toBeTruthy();
	});
});

describe('SummaryCardRun Alert', () => {
	afterEach(cleanup);

	it('should render component with Alert', () => {
		const {queryByText} = render(
			<SummaryCardRun
				alert={MOCK_ALERT}
				sections={MOCK_SECTIONS}
				summary={MOCK_SUMMARY}
			/>
		);

		jest.runAllTimers();

		expect(queryByText('Alert title')).toBeTruthy();
	});
});

describe('SummaryCardRun Sections', () => {
	it('should render component with Section', () => {
		afterEach(cleanup);

		const {queryByText} = render(
			<SummaryCardRun
				alert={MOCK_ALERT}
				sections={MOCK_SECTIONS}
				summary={MOCK_SUMMARY}
			/>
		);

		jest.runAllTimers();

		expect(queryByText('Section 01')).toBeTruthy();
	});
});
