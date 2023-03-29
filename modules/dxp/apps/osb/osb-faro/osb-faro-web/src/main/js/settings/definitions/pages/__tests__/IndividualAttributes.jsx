import 'test/mock-modal';

import IndividualAttributes from '../IndividualAttributes';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<IndividualAttributes groupId='23' {...props} />
		</StaticRouter>
	</Provider>
);

describe('IndividualAttributes', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should open modal after click on fielName', () => {
		const {getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		fireEvent.click(getByText('testFildName0'));

		jest.runAllTimers();

		expect(open).toBeCalled();
	});
});
