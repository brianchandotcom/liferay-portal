import mockStore from 'test/mock-store';
import NewRequestModal from '../NewRequestModal';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

describe('NewRequestModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<DndProvider backend={HTML5Backend}>
					<NewRequestModal />
				</DndProvider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it.skip('checks both Delete & Suppress checkbox when Delete is clicked', () => {
		const {getByLabelText} = render(
			<Provider store={mockStore()}>
				<DndProvider backend={HTML5Backend}>
					<NewRequestModal />
				</DndProvider>
			</Provider>
		);

		const deleteCheckbox = getByLabelText(/Delete/);
		const suppressCheckbox = getByLabelText(/Suppress/);

		expect(deleteCheckbox.checked).toBeFalse();
		expect(suppressCheckbox.checked).toBeFalse();

		fireEvent.click(deleteCheckbox);

		expect(deleteCheckbox.checked).toBeTrue();
		expect(suppressCheckbox.checked).toBeTrue();
	});
});
