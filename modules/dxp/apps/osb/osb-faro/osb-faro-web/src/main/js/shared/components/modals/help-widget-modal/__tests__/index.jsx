import HelpWidgetModal from '../index';
import mockStore from 'test/mock-store';
import React from 'react';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('HelpWidgetModal', () => {
	it('Should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<HelpWidgetModal onClose={jest.fn()} />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
