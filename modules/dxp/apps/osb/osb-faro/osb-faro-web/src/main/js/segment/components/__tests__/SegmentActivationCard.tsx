import React from 'react';
import {render} from '@testing-library/react';
import {SegmentActivationCard} from '../SegmentActivationCard';
import {
	SegmentActivationFrequencyTypes,
	SegmentActivationScheduleTypes,
	SegmentTypes
} from 'shared/util/constants';

jest.unmock('react-dom');

const mockData = {
	segmentActivationId: '1'
};

describe('SegmentActivationCard', () => {
	it('should render when frequency type is batch', () => {
		const {container} = render(
			<SegmentActivationCard
				segmentActivation={{
					frequencyType: SegmentActivationFrequencyTypes.Indefinitely,
					scheduleType: SegmentActivationScheduleTypes.Batch,
					...mockData
				}}
				segmentType={SegmentTypes.Batch}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render when frequency type is real time', () => {
		const {container} = render(
			<SegmentActivationCard
				segmentActivation={{
					frequencyType: SegmentActivationFrequencyTypes.Indefinitely,
					scheduleType: SegmentActivationScheduleTypes.RealTime,
					...mockData
				}}
				segmentType={SegmentTypes.Batch}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render with dates when frequency type is between', () => {
		const {container} = render(
			<SegmentActivationCard
				segmentActivation={{
					frequencyType: SegmentActivationFrequencyTypes.Between,
					scheduleEndDate: '1757818800000',
					scheduleStartDate: '1756004400000',
					scheduleType: SegmentActivationScheduleTypes.Batch,
					...mockData
				}}
				segmentType={SegmentTypes.Batch}
			/>
		);
		expect(container).toMatchSnapshot();
	});
});
