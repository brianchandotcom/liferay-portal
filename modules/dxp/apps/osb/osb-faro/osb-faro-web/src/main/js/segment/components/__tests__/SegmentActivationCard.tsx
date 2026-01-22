import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {fireEvent, render} from '@testing-library/react';
import {Provider} from 'react-redux';
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

const WrapperComponent: React.FC<{children: React.ReactNode}> = ({
	children
}) => (
	<Provider store={mockStore()}>
		<BrowserRouter>{children}</BrowserRouter>
	</Provider>
);

jest.mock(
	'shared/components/DateRangeInput',
	() =>
		function MockDateInput({value}) {
			return (
				<div data-testid='mock-date-input'>
					{value.start} {'-'} {value.end}
				</div>
			);
		}
);

describe('SegmentActivationCard', () => {
	it('should render when frequency type is batch', () => {
		const {container} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={{
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.Batch,
						...mockData
					}}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render when frequency type is real time', () => {
		const {container} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={{
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.RealTime,
						...mockData
					}}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render with dates when frequency type is between', () => {
		const {container} = render(
			<WrapperComponent>
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
			</WrapperComponent>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render the modal if the edit button is clicked', async () => {
		const {findByTestId, findByText, getByTestId} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={{
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.Batch,
						...mockData
					}}
					segmentType={SegmentTypes.Batch}
				/>
			</WrapperComponent>
		);

		const editButton = getByTestId('edit-activation-button');
		fireEvent.click(editButton);

		const modal = await findByTestId('segment-activation-modal');
		expect(modal).toBeInTheDocument();

		const modalHeader = await findByText('Configure Activation');
		expect(modalHeader).toBeInTheDocument();
	});

	it('should render the modal with the expected configurations - indefinitely', async () => {
		const {findByTestId, findByText, getByTestId} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={{
						frequencyType:
							SegmentActivationFrequencyTypes.Indefinitely,
						scheduleType: SegmentActivationScheduleTypes.RealTime,
						...mockData
					}}
					segmentType={SegmentTypes.RealTime}
				/>
			</WrapperComponent>
		);

		const editButton = getByTestId('edit-activation-button');
		fireEvent.click(editButton);

		const modal = await findByTestId('segment-activation-modal');
		expect(modal).toBeInTheDocument();

		expect(await findByText('Configure Activation')).toBeInTheDocument();

		expect(await findByText('indefinitely')).toBeInTheDocument();

		expect(await findByText('Real-Time')).toBeInTheDocument();
	});

	it('should render the modal with the expected configurations - between', async () => {
		const {findByTestId, findByText, getByTestId} = render(
			<WrapperComponent>
				<SegmentActivationCard
					segmentActivation={{
						frequencyType: SegmentActivationFrequencyTypes.Between,
						scheduleEndDate: '1757818800000',
						scheduleStartDate: '1756004400000',
						scheduleType: SegmentActivationScheduleTypes.RealTime,
						...mockData
					}}
					segmentType={SegmentTypes.RealTime}
				/>
			</WrapperComponent>
		);

		const editButton = getByTestId('edit-activation-button');
		fireEvent.click(editButton);

		const modal = await findByTestId('segment-activation-modal');
		expect(modal).toBeInTheDocument();

		expect(await findByText('Configure Activation')).toBeInTheDocument();

		expect(await findByText('between')).toBeInTheDocument();

		expect(getByTestId('mock-date-input')).toBeInTheDocument();

		expect(await findByText('Real-Time')).toBeInTheDocument();
	});
});
