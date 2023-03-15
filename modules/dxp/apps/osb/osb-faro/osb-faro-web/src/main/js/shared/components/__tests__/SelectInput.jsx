import Promise from 'metal-promise';
import React from 'react';
import SelectInput from '../SelectInput';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SelectInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<SelectInput
				dataSourceFn={() => Promise.resolve([])}
				itemRenderer={jest.fn()}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with the selected item', () => {
		const {queryByText} = render(
			<SelectInput
				dataSourceFn={() => Promise.resolve([])}
				itemRenderer={({name}) => name}
				selectedItem={{name: 'foo'}}
			/>
		);

		expect(queryByText('foo')).toBeTruthy();
	});
});
