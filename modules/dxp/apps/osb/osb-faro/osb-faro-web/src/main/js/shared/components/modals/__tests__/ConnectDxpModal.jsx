import ConnectDXPModal from '../ConnectDXPModal';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

describe('ConnectDXPModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<ConnectDXPModal groupId='123' onClose={noop} />
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
