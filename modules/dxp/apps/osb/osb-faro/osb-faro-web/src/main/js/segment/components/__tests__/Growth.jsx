import * as data from 'test/data';
import React from 'react';
import SegmentGrowthWithList, {
	SegmentGrowthChart,
	SelectedPointInfo
} from '../Growth';
import {MemoryRouter, Route} from 'react-router-dom';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

describe('SegmentGrowthWithList', () => {
	it('should render', () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={[
					'/workspace/23/123123/contacts/segments/321321/membership'
				]}
			>
				<Route path={Routes.CONTACTS_SEGMENT_MEMBERSHIP}>
					<SegmentGrowthWithList
						channelId='123'
						data={[
							{
								added: 1,
								modifiedDate: data.getTimestamp(),
								removed: 3
							}
						]}
						groupId='23'
						id='3'
						onPointSelect={jest.fn()}
					/>
				</Route>
			</MemoryRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

describe('SegmentGrowthChart', () => {
	it('should render', () => {
		const {container} = render(
			<SegmentGrowthChart data={[]} onPointSelect={jest.fn()} />
		);

		expect(container).toMatchSnapshot();
	});
});

describe('SelectedPointInfo', () => {
	it('should render', () => {
		const {container} = render(
			<SelectedPointInfo
				data={[
					{
						added: 1,
						modifiedDate: data.getTimestamp(),
						removed: 3
					}
				]}
				hasSelectedPoint
				onClearSelection={jest.fn()}
				selectedPoint={0}
			/>
		);
		expect(container).toMatchSnapshot();
	});
});
