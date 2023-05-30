import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {DynamicSegmentEdit} from '../Dynamic';
import {HTML5Backend} from 'react-dnd-html5-backend';
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
					<DndProvider backend={HTML5Backend}>
						<DynamicSegmentEdit
							groupId='23'
							id='123'
							propertyGroupsIList={new List()}
							segment={data.getImmutableMock(
								Segment,
								data.mockSegment
							)}
						/>
					</DndProvider>
				</Provider>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
