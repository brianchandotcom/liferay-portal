import * as data from 'test/data';
import React from 'react';
import SegmentProfileCard from '../ProfileCard';
import {render} from '@testing-library/react';
import {Segment} from 'shared/util/records';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<StaticRouter>
		<SegmentProfileCard
			channelId='123'
			groupId='23'
			id='3'
			segment={data.getImmutableMock(Segment, data.mockSegment, '3')}
			{...props}
		/>
	</StaticRouter>
);

describe('SegmentProfileCard', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
