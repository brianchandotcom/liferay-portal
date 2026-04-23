import BatchActionModal from '../BatchActionModal';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false}>
				<BatchActionModal
					actionOptions={{
						actionCountString: '',
						options: [],
						optionsLabel: ''
					}}
					columns={[]}
					editableAttr=''
					fitContent={false}
					items={[]}
					onClose={jest.fn()}
					onSave={jest.fn()}
					title='Batch Action'
					{...props}
				/>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('BatchActionModal', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
