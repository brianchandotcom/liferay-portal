import mockStore from 'test/mock-store';
import NewRuleModal from '../NewRuleModal';
import React from 'react';
import {MockedProvider} from '@apollo/react-testing';
import {mockRecommendationPageAssetsReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('NewRuleModal', () => {
	it('should render', () => {
		const {container} = render(
			<MockedProvider mocks={[mockRecommendationPageAssetsReq([])]}>
				<Provider store={mockStore()}>
					<NewRuleModal />
				</Provider>
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
