import DeleteConfirmationModal from '../DeleteConfirmationModal';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render, screen} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const DefaultComponent = ({children, ...props}) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false}>
				<DeleteConfirmationModal
					deleteConfirmationText='delete me'
					disabled={false}
					onClose={jest.fn()}
					onSubmit={jest.fn()}
					{...props}
				>
					{children}
				</DeleteConfirmationModal>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('DeleteConfirmationModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with custom title and message', () => {
		render(
			<DefaultComponent title='Custom Title'>
				{'Custom Delete Message'}
			</DefaultComponent>
		);

		expect(screen.getByText('Custom Title')).toBeInTheDocument();
		expect(screen.getByText('Custom Delete Message')).toBeInTheDocument();
	});
});
