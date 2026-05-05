import * as data from 'test/data';
import DynamicSegmentEdit from '../Dynamic';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {List} from 'immutable';
import {Provider} from 'react-redux';
import {Segment} from 'shared/util/records';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

jest.mock('contacts/hoc/segment/WithBaseEdit', () => Component => Component);
jest.mock(
	'segment/segment-editor/dynamic/hoc/WithPropertyGroups',
	() => Component => Component
);
jest.mock('uuid', () => ({
	v4: () => '00000000-0000-0000-0000-000000000000'
}));

describe('DynamicSegmentEdit', () => {
	afterEach(cleanup);

	it('should render in BATCH mode', () => {
		const SEGMENT_ID = '123';
		const GROUP_ID = '23';

		const batchSegmentMock = data.getImmutableMock(
			Segment,
			data.mockSegment,
			SEGMENT_ID,
			{segmentType: 'BATCH'}
		);

		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<DynamicSegmentEdit
							groupId={GROUP_ID}
							id={SEGMENT_ID}
							propertyGroupsIList={new List()}
							segment={batchSegmentMock}
						/>
					</DndProvider>
				</Provider>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
