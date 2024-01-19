import client from 'shared/apollo/client';
import DateInput from '../DateInput';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<MockedProvider mocks={[mockPreferenceReq()]}>
			{children}
		</MockedProvider>
	</ApolloProvider>
);

describe('DateInput', () => {
	it('should render', () => {
		const {container} = render(
			<WrapperComponent>
				<DateInput />
			</WrapperComponent>
		);

		expect(container).toMatchSnapshot();
	});

	it('should use the displayFormat prop for displaying the date', () => {
		const {getByDisplayValue} = render(
			<WrapperComponent>
				<DateInput
					displayFormat='YYYY MM DD HH:mm'
					onDateInputChange={jest.fn()}
					value='1970-01-01'
				/>
			</WrapperComponent>
		);

		expect(getByDisplayValue('1970 01 01 00:00')).toBeTruthy();
	});
});
