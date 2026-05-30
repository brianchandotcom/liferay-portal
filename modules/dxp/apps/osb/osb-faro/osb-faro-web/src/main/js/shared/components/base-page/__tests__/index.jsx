import mockStore from 'test/mock-store';
import React from 'react';
import {BasePage} from '../index';
import {MemoryRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('BasePage', () => {
	it('renders BasePage', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<BasePage documentTitle='Test title'>
						{'Test test'}
					</BasePage>
				</MemoryRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
