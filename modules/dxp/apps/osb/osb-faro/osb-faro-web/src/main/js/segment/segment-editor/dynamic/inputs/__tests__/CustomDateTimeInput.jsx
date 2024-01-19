import client from 'shared/apollo/client';
import CustomDateTimeInput from '../CustomDateTimeInput';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {Property} from 'shared/util/records';
import {RelationalOperators} from '../../utils/constants';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.GT,
				propertyName: 'completeDate',
				value: '2020-01-17T00:00:00.000Z'
			}
		]
	}
]);

describe('CustomDateTimeInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<ApolloProvider client={client}>
				<MockedProvider mocks={[mockPreferenceReq()]}>
					<CustomDateTimeInput
						property={new Property()}
						timeZoneId='UTC'
						value={mockValue}
					/>
				</MockedProvider>
			</ApolloProvider>
		);
		fireEvent.click(getByText('is after'));

		expect(getByText('is before')).toBeTruthy();
		expect(getByText('is')).toBeTruthy();
		expect(getAllByText('is after')[1]).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
