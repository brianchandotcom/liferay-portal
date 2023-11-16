import * as data from 'test/data';
import AssociatedSegments from '../AssociatedSegments';
import mockStore from 'test/mock-store';
import React from 'react';
import {Individual} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('IndividualAssociatedSegments', () => {
	it('should render', async () => {
		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<AssociatedSegments
						groupId='23'
						id='test'
						individual={data.getImmutableMock(
							Individual,
							data.mockIndividual
						)}
					/>
				</Provider>
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
