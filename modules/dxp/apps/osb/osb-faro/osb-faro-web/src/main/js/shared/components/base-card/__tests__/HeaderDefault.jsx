import HeaderDefault from '../HeaderDefault';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {MockedProvider} from '@apollo/react-testing';
import {mockTimeRangeReq} from 'test/graphql-data';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<MockedProvider mocks={[mockTimeRangeReq()]}>
		<HeaderDefault label='Title' {...props} />
	</MockedProvider>
);
describe('HeaderDefault', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should call the onChangeInterval prop fn with "day" if the rangekey is changed to an hourly value', () => {
		const spy = jest.fn();
		const {getByText} = render(
			<DefaultComponent
				onChangeInterval={spy}
				rangeSelectors={{rangeKey: '30'}}
				showInterval
			/>
		);

		jest.runAllTimers();

		fireEvent.click(getByText('Last 24 hours'));

		expect(spy).toHaveBeenCalledWith(INTERVAL_KEY_MAP.day);
	});
});
