import mockStore from 'test/mock-store';
import React from 'react';
import SegmentEditStatic from '../SegmentEditStatic';
import {Changeset} from 'shared/util/records';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

describe('SegmentEditStatic', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={[
					'/workspace/23/123123/contacts/segments/create?type=STATIC'
				]}
			>
				<Route path={Routes.CONTACTS_SEGMENT_CREATE}>
					<Provider store={mockStore()}>
						<SegmentEditStatic
							changeset={new Changeset()}
							groupId='23'
						/>
					</Provider>
				</Route>
			</MemoryRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
