import FieldDropDown from '../FieldDropDown';
import React from 'react';
import {Map} from 'immutable';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<FieldDropDown
		buttonPlaceholder='Search'
		dataIMap={new Map()}
		inputPlaceholder='Search here'
		searchItems={[]}
		{...props}
	/>
);

describe('FieldDropDown', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a title', () => {
		const {getByText} = render(<DefaultComponent title='FOO BAR' />);

		expect(getByText('FOO BAR')).toBeTruthy();
	});

	it('should render with data', () => {
		const {getByText} = render(
			<DefaultComponent dataIMap={new Map({name: 'foo', value: 450})} />
		);

		expect(getByText('foo')).toBeTruthy();
		expect(getByText('450')).toBeTruthy();
	});
});
