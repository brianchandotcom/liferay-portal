import * as data from 'test/data';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {CONTEXT_OPTIONS, Distribution} from '../Distribution';
import {List} from 'immutable';
import {User} from 'shared/util/records';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: new User(data.mockUser()),
	distributionsKey: 'test',
	fieldDistributionIList: new List(),
	fieldMappingFieldName: 'test',
	groupId: '23',
	id: 'test',
	loading: true,
	numberOfBins: 10
};

describe('SegmentDistribution', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Distribution {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('should render a Chart component if loading is false', () => {
		const {container} = render(
			<Distribution
				{...defaultProps}
				fieldDistributionIList={
					new List([{count: 3, values: ['stuff']}])
				}
				loading={false}
			/>
		);

		expect(
			container.querySelector('.recharts-responsive-container')
		).toBeTruthy();
	});

	it('should not render a dropdown of context items if contextOptions is less than 2', () => {
		const {container, queryByText} = render(
			<Distribution
				{...defaultProps}
				contextOptions={[CONTEXT_OPTIONS[0]]}
			/>
		);

		expect(container.querySelector('.context-select')).toBeNull();
		expect(queryByText('by')).toBeNull();
	});
});
