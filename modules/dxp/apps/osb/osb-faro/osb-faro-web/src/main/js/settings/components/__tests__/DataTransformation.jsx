import * as API from 'shared/api';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {DataTransformation, processFieldMappings} from '../DataTransformation';
import {fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {mockFieldMapping, mockMapping} from 'test/data';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23',
	id: '123',
	onSubmit: jest.fn()
};

const DefaultComponent = props => (
	<StaticRouter>
		<Provider store={mockStore()}>
			<DataTransformation {...defaultProps} {...props} />
		</Provider>
	</StaticRouter>
);

describe('processFieldMappings', () => {
	it('should return fieldMappings', () => {
		const foo = 'foo';
		const bar = 'bar';
		const baz = 'baz';

		const inputValue = fromJS([
			{source: {name: foo}, suggestion: {}},
			{source: {name: bar}, suggestion: {}},
			{source: {name: baz}, suggestion: {}}
		]);

		const result = processFieldMappings(inputValue);

		expect(result.length).toEqual(3);

		expect(result).toMatchSnapshot();
	});
});

describe('DataTransformation', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/ the done button enabled', () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Matched Field', {
					suggestions: [mockFieldMapping()]
				})
			])
		);

		const {getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(getByText('Done')).not.toBeDisabled();
	});

	xit('should render w/ a mapped field', () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Matched Field', {
					suggestions: [mockFieldMapping()]
				}),
				mockMapping('Unmatched Field')
			])
		);

		const {getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(getByText('TestMatched Field')).toBeTruthy();
	});

	xit('should hide mapped fields', () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Matched Field', {
					suggestions: [mockFieldMapping()]
				}),
				mockMapping('Unmatched Field')
			])
		);

		const {container, getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		fireEvent.click(getByText('Unmapped Fields Only'));

		expect(container.querySelector('.hidden')).toBeTruthy();
	});

	xit('should hide unmatched fields', () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Has default match 1', {
					suggestions: [mockFieldMapping(null, {name: 'foo'})]
				}),
				mockMapping('Has default match 2', {
					suggestions: [mockFieldMapping(null, {name: 'bar'})]
				}),
				mockMapping('No default match')
			])
		);

		const {queryByText} = render(
			<DefaultComponent showUnmatchedFields={false} />
		);

		jest.runAllTimers();

		expect(queryByText('TestNo default match')).toBeFalsy();
	});

	it('should render w/ the done button disabled if there are duplicate CSV field mappings', () => {
		API.dataSource.fetchMappings.mockReturnValue(
			Promise.resolve([
				mockMapping('Has default match 1', {
					suggestions: [
						mockFieldMapping(null, {name: 'foo'}),
						mockFieldMapping(null, {name: 'jack', value: 'dupe'})
					]
				}),
				mockMapping('Has default match 2', {
					suggestions: [
						mockFieldMapping(null, {name: 'bar'}),
						mockFieldMapping(null, {name: 'jack', value: 'dupe'})
					]
				}),
				mockMapping('No default match')
			])
		);

		const {getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(getByText('Done')).toBeDisabled();
	});
});
