import AddChannelModal from '../AddChannelModal';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

describe('AddChannelModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<AddChannelModal onClose={noop} onSubmit={noop} />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
