import React from 'react';
import ToggleSwitchModal from '../ToggleSwitchModal';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('ToggleSwitchModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<ToggleSwitchModal
				items={['foo', 'bar', 'baz']}
				message='Select which items you want to toggle'
				onClose={noop}
				onSubmit={jest.fn()}
				title='Toggle some options!'
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
