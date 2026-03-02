import ContextualInformation from '../ContextualInformation';
import React from 'react';
import {fromJS} from 'immutable';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('ContextualInformation', () => {
	const mockContext = {
		browserName: 'Chrome',
		city: 'Sidney',
		country: 'Australia',
		deviceType: 'Desktop',
		region: 'AEST',
		timeZoneOffset: '-03:00'
	};

	it('should render the snapshot', () => {
		const {container} = render(
			<ContextualInformation
				contactId='contact-1'
				contextData={fromJS(mockContext)}
				email='test@example.com'
				userId='123456'
				uuid='12345'
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render the empty state when showEmptyState is true', () => {
		const {getByText, queryByText} = render(
			<ContextualInformation
				contextData={fromJS(mockContext)}
				showEmptyState
			>
				<div>{'empty state rendered'}</div>
			</ContextualInformation>
		);

		expect(getByText('empty state rendered')).toBeTruthy();
		expect(queryByText('browser')).toBeNull();
	});

	it('should correctly format the time zone offset string', () => {
		const {getByText} = render(
			<ContextualInformation contextData={fromJS(mockContext)} />
		);

		expect(getByText('UTC -03:00 (AEST)')).toBeTruthy();
	});

	it('should show email and uuid when passed as props', () => {
		const {getByText} = render(
			<ContextualInformation
				contextData={fromJS({})}
				email='test@example.com'
				uuid='1234-abcde-67890'
			/>
		);

		expect(getByText('test@example.com')).toBeTruthy();
		expect(getByText('1234-abcde-67890')).toBeTruthy();
	});

	it('should display the fallback dash for missing context values', () => {
		const {getAllByText} = render(
			<ContextualInformation contextData={fromJS({})} />
		);

		const dashes = getAllByText('-');
		expect(dashes.length).toBeGreaterThan(0);
	});
});
