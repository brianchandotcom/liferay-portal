import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DynamicSegmentEdit} from '../Dynamic';
import {List} from 'immutable';
import {Provider} from 'react-redux';
import {Segment} from 'shared/util/records';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('DynamicSegmentEdit', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<DynamicSegmentEdit
						groupId='23'
						id='123'
						propertyGroupsIList={new List()}
						segment={data.getImmutableMock(
							Segment,
							data.mockSegment
						)}
					/>
				</Provider>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
