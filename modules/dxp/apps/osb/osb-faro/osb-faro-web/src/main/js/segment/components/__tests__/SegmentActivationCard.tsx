import React from 'react';
import {render} from '@testing-library/react';
import {SegmentActivationCard} from '../SegmentActivationCard';
import {
	SegmentActivationFrequencyTypes,
	SegmentActivationScheduleTypes
} from 'shared/util/constants';

jest.unmock('react-dom');

const mockData = {
	segmentActivationId: '1'
};

describe('SegmentSticker', () => {
	it('should render when frequency type is batch', () => {
		const {container} = render(
			<SegmentActivationCard
				segmentActivation={{
					frequencyType: SegmentActivationFrequencyTypes.Batch,
					scheduleType: SegmentActivationScheduleTypes.Indefinitely,

					...mockData
				}}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render when frequency type is real time', () => {
		const {container} = render(
			<SegmentActivationCard
				segmentActivation={{
					frequencyType: SegmentActivationFrequencyTypes.RealTime,
					scheduleType: SegmentActivationScheduleTypes.Indefinitely,
					...mockData
				}}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render with dates when frequency type is between', () => {
		const {container} = render(
			<SegmentActivationCard
				segmentActivation={{
					frequencyType: SegmentActivationFrequencyTypes.Batch,
					scheduleEndDate: '1757818800000',
					scheduleStartDate: '1756004400000',
					scheduleType: SegmentActivationScheduleTypes.Between,
					...mockData
				}}
			/>
		);
		expect(container).toMatchSnapshot();
	});
});
