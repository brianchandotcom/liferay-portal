import AssociatedSegmentsList from '../AssociatedSegmentsList';
import React from 'react';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('AssociatedSegmentsList', () => {
	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<AssociatedSegmentsList
					channelId='123123'
					dataSourceFn={() => Promise.resolve({})}
					groupId='23'
					id='test'
					total={2}
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
