import mockStore from 'test/mock-store';
import React from 'react';
import WorkspacesSuccessDisplay from '../SuccessDisplay';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('WorkspacesSuccessDisplay', () => {
	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<WorkspacesSuccessDisplay friendlyURL='/fooFriendlyUrl' />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
