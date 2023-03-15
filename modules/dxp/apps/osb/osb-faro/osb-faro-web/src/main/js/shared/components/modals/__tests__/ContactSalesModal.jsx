import ContactSalesModal from '../ContactSalesModal';
import mockStore from 'test/mock-store';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

describe('ContactSalesModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<ContactSalesModal addAlert={addAlert} onClose={noop} />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
