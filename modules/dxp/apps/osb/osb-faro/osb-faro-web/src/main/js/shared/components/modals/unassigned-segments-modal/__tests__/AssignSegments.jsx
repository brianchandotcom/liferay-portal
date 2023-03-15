import * as API from 'shared/api';
import AssignSegments from '../AssignSegments';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {
	cleanup,
	fireEvent,
	render,
	waitForDomChange
} from '@testing-library/react';
import {mockChannelContext} from 'test/mock-channel-context';
import {mockSegment} from 'test/data';
import {noop} from 'lodash';
import {StaticRouter} from 'react-router-dom';
import {UnassignedSegmentsContext} from 'shared/context/unassignedSegments';

jest.unmock('react-dom');

const mockedContext = {
	unassignedSegments: [
		mockSegment(1, {channelId: null}),
		mockSegment(2, {channelId: null})
	],
	unassignedSegmentsDispatch: jest.fn(),
	unassignedSegmentsTriggered: false
};

const DefaultComponent = props => (
	<UnassignedSegmentsContext.Provider value={mockedContext}>
		<ChannelContext.Provider value={mockChannelContext()}>
			<StaticRouter>
				<AssignSegments groupId='123' onClose={noop} {...props} />
			</StaticRouter>
		</ChannelContext.Provider>
	</UnassignedSegmentsContext.Provider>
);

describe('AssignSegments', () => {
	afterEach(() => {
		cleanup();
		jest.useRealTimers();
	});

	it('should render', () => {
		jest.useFakeTimers();
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should run close from OnClose prop', async () => {
		const spy = jest.fn();

		const {getByTestId, getByText} = render(
			<DefaultComponent onClose={spy} />
		);

		fireEvent.click(getByText('Skip for Now'));

		fireEvent.click(getByTestId('submit-button'));

		expect(spy).toBeCalled();
	});

	it('it should enable done button when a valid value is selected', async () => {
		const {getByTestId} = render(<DefaultComponent />);

		fireEvent.change(getByTestId('select-1'), {
			target: {value: '1'}
		});

		const button = getByTestId('submit-button');

		expect(button).not.toBeDisabled();
	});

	it('should call api functions with proper args', async () => {
		const {container, getByTestId} = render(<DefaultComponent />);

		fireEvent.change(getByTestId('select-1'), {
			target: {value: '1'}
		});

		fireEvent.change(getByTestId('select-2'), {
			target: {value: 'DELETE'}
		});

		fireEvent.click(getByTestId('submit-button'));

		await waitForDomChange(container.querySelector('.assign-segments'));

		expect(API.individualSegment.updateChannel).toBeCalledWith({
			channelId: '1',
			groupId: '123',
			id: '1'
		});

		expect(API.individualSegment.delete).toBeCalledWith({
			groupId: '123',
			id: '2'
		});
	});
});
