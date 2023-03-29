import Header from '../Header';
import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {StaticRouter} from 'react-router';
jest.unmock('react-dom');

const WrappedComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<Header {...props}></Header>
		</StaticRouter>
	</Provider>
);

describe('BasePage.Header', () => {
	afterEach(cleanup);

	it('renders Header', () => {
		const {container} = render(
			<WrappedComponent>{'Test Test'}</WrappedComponent>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders Header w/ Breadcrumbs', () => {
		const {container} = render(
			<WrappedComponent
				breadcrumbs={[
					{active: false, label: 'Foo'},
					{active: true, label: 'Bar'}
				]}
			>
				{'Test Test'}
			</WrappedComponent>
		);

		expect(container).toMatchSnapshot();
	});

	describe('NavBar', () => {
		it('renders NavBar', () => {
			const {container} = render(
				<BrowserRouter>
					<Header.NavBar
						items={[
							{
								exact: true,
								label: 'Test',
								route: Routes.CONTACTS_ACCOUNT
							}
						]}
						routeParams={{groupId: '123', id: '321'}}
						routeQueries={{rangeKey: '30'}}
					/>
				</BrowserRouter>
			);

			expect(container).toMatchSnapshot();
		});

		it('renders NavBar w/o routeQueries', () => {
			const {container} = render(
				<BrowserRouter>
					<Header.NavBar
						items={[
							{
								exact: true,
								label: 'Test',
								route: Routes.CONTACTS_ACCOUNT
							}
						]}
						routeParams={{groupId: '123', id: '321'}}
					/>
				</BrowserRouter>
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('PageActions', () => {
		it('renders PageActions', () => {
			const {container} = render(
				<Header.PageActions
					actions={[{label: 'Test Action'}]}
					label='Test Label'
				/>
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('Section', () => {
		it('renders Section', () => {
			const {container} = render(
				<Header.Section>{'Test Test'}</Header.Section>
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('TitleSection', () => {
		it('renders TitleSection w/ Title', () => {
			const {container} = render(
				<Header.TitleSection>{'Test Test'}</Header.TitleSection>
			);

			expect(container).toMatchSnapshot();
		});

		it('renders TitleSection w/ title & no children', () => {
			const {container} = render(
				<Header.TitleSection title='Test Title' />
			);

			expect(container).toMatchSnapshot();
		});

		it('renders TitleSection w/ subtitle', () => {
			const {container} = render(
				<Header.TitleSection subtitle='Test Subtitle'>
					{'Test Test'}
				</Header.TitleSection>
			);

			expect(container).toMatchSnapshot();
		});
	});
});
