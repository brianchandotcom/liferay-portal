import BaseCard from '..';
import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hoc';
import {render} from '@testing-library/react';

const MOCK_CONTEXT = {
	router: {
		query: {
			rangeKey: '0'
		}
	}
};

const WrappedComponent = props => (
	<ApolloProvider client={client}>
		<BasePage.Context.Provider value={MOCK_CONTEXT}>
			<BaseCard
				className='my-component-classname'
				label='My title'
				{...props}
			/>
		</BasePage.Context.Provider>
	</ApolloProvider>
);

jest.unmock('react-dom');

describe('BaseCard', () => {
	it('should render component', () => {
		const {container} = render(
			<WrappedComponent>
				{() => <div>{'My body component'}</div>}
			</WrappedComponent>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render component with custom Header', () => {
		const Header = () => <div>{'My custom header component'}</div>;

		const {getByText} = render(
			<WrappedComponent Header={Header}>
				{() => <div>{'My body component'}</div>}
			</WrappedComponent>
		);

		expect(getByText('My body component')).toBeTruthy();
	});

	it('should return the props in Body component', () => {
		let customBodyProps = {};

		render(
			<WrappedComponent>
				{props => {
					customBodyProps = props;

					return <div>{'My custom body component'}</div>;
				}}
			</WrappedComponent>
		);

		expect(customBodyProps).toMatchInlineSnapshot(`
		Object {
		  "filters": undefined,
		  "interval": "D",
		  "onChangeInterval": [Function],
		  "onRangeSelectorsChange": [Function],
		  "rangeSelectors": Object {
		    "rangeEnd": "",
		    "rangeKey": "0",
		    "rangeStart": "",
		  },
		  "router": Object {
		    "query": Object {
		      "rangeKey": "0",
		    },
		  },
		}
	`);
	});

	it('should render a Card Header with an interval selector', () => {
		const {container, getByText} = render(
			<WrappedComponent showInterval>
				{() => <div>{'My body component'}</div>}
			</WrappedComponent>
		);

		expect(container.querySelector('.interval-selector-root')).toBeTruthy();
		expect(getByText('D')).toBeTruthy();
		expect(getByText('W')).toBeTruthy();
		expect(getByText('M')).toBeTruthy();
	});
});
