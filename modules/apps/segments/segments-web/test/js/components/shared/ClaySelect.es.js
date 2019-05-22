import ClaySelect from 'components/shared/ClaySelect.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe('ClaySelect', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(
			<ClaySelect onChange={jest.fn()} options={[]} />
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render with an item selected', () => {
		const {container} = render(
			<ClaySelect
				onChange={jest.fn()}
				options={[
					{
						label: 'aLabel',
						value: 'aValue'
					},
					{
						label: 'aLabel',
						value: 'bValue'
					}
				]}
				selected='bValue'
			/>
		);

		expect(container.firstChild.value).toEqual('bValue');
	});
});
