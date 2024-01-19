import client from 'shared/apollo/client';
import DateInput from '../DateInput';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {MockedProvider} from '@apollo/react-testing';
import {mockForm} from 'test/data';
import {mockPreferenceReq} from 'test/graphql-data';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<ApolloProvider client={client}>
		<MockedProvider mocks={[mockPreferenceReq()]}>
			<DateInput field={{name: 'foo'}} form={mockForm()} {...props} />
		</MockedProvider>
	</ApolloProvider>
);

describe('DateInput', () => {
	const labelContent = 'Foo Date';

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render with label', () => {
		const {queryByText} = render(<DefaultComponent label={labelContent} />);

		expect(queryByText(labelContent)).toBeTruthy();
	});

	it('should render as required', () => {
		const {queryByText} = render(
			<DefaultComponent label={labelContent} required />
		);

		expect(queryByText(labelContent).closest('label')).toHaveClass(
			'required'
		);
	});
});
