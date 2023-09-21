import client from 'shared/apollo/client';
import ExperimentOverviewPage from '../ExperimentOverviewPage';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {ExperimentResolver as Experiment} from 'shared/apollo/resolvers';
import {fireEvent, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockExperimentReq,
	mockExperimentRootReq,
	mockTimeRangeReq
} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const WrappedComponent = ({
	publishable = false,
	publishedDXPVariantId = null,
	status
}) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore() as any}>
			<MemoryRouter
				initialEntries={['/workspace/1000/2000/tests/overview/123']}
			>
				<Route path={Routes.TESTS_OVERVIEW}>
					<MockedProvider
						mocks={[
							mockTimeRangeReq(),
							mockExperimentRootReq({publishable, status}),
							mockExperimentReq({
								publishedDXPVariantId
							})
						]}
						resolvers={{Experiment}}
					>
						<ExperimentOverviewPage
							router={{
								params: {
									channelId: '2000',
									groupId: '1000',
									id: '123'
								},
								query: {}
							}}
						/>
					</MockedProvider>
				</Route>
			</MemoryRouter>
		</Provider>
	</ApolloProvider>
);

describe('ExperimentOverviewPage', () => {
	it('renders review and delete button in the DRAFT status', async () => {
		const {container, findByRole} = render(
			<WrappedComponent status='DRAFT' />
		);

		await waitForLoadingToBeRemoved(container);

		const reviewButton = (await findByRole('link', {
			name: /review/i
		})) as HTMLAnchorElement;
		const deleteButton = (await findByRole('link', {
			name: /delete/i
		})) as HTMLAnchorElement;

		const header = container.querySelector('.header-root');

		expect(header.contains(reviewButton)).toBeTruthy();
		expect(header.contains(deleteButton)).toBeTruthy();

		expect(reviewButton).toBeInTheDocument();
		expect(reviewButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=reviewAndRun'
		);

		expect(deleteButton).toBeInTheDocument();
		expect(deleteButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=delete'
		);
	});

	it('renders terminate button in the RUNNING status', async () => {
		const {container, findByRole} = render(
			<WrappedComponent status='RUNNING' />
		);

		await waitForLoadingToBeRemoved(container);

		const terminateButton = (await findByRole('link', {
			name: /terminate/i
		})) as HTMLAnchorElement;

		const header = container.querySelector('.header-root');

		expect(header.contains(terminateButton)).toBeTruthy();

		expect(terminateButton).toBeInTheDocument();
		expect(terminateButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=terminate'
		);
	});

	it('renders publish and delete button to experiment to status FINISHED_NO_WINNER', async () => {
		const {container, findByRole} = render(
			<WrappedComponent status='FINISHED_NO_WINNER' />
		);

		await waitForLoadingToBeRemoved(container);

		const publishButton = (await findByRole('link', {
			name: /publish/i
		})) as HTMLAnchorElement;
		const deleteButton = (await findByRole('link', {
			name: /delete/i
		})) as HTMLAnchorElement;

		const header = container.querySelector('.header-root');

		expect(header.contains(publishButton)).toBeTruthy();
		expect(header.contains(deleteButton)).toBeTruthy();

		expect(publishButton).toBeInTheDocument();
		expect(publishButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=publish'
		);

		expect(deleteButton).toBeInTheDocument();
		expect(deleteButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=delete'
		);
	});

	it('renders publishabel and delete buttons to experiment to status TERMINATED', async () => {
		const {container, findByRole} = render(
			<WrappedComponent publishable status='TERMINATED' />
		);

		await waitForLoadingToBeRemoved(container);

		const publishButton = (await findByRole('link', {
			name: /publish/i
		})) as HTMLAnchorElement;
		const deleteButton = (await findByRole('link', {
			name: /delete/i
		})) as HTMLAnchorElement;

		const header = container.querySelector('.header-root');

		expect(header.contains(publishButton)).toBeTruthy();
		expect(header.contains(deleteButton)).toBeTruthy();

		expect(publishButton).toBeInTheDocument();
		expect(publishButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=publish'
		);

		expect(deleteButton).toBeInTheDocument();
		expect(deleteButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=delete'
		);
	});

	it('renders publishabel and delete buttons to experiment to status FINISHED_WINNER', async () => {
		const {container, findByRole} = render(
			<WrappedComponent publishable status='FINISHED_WINNER' />
		);

		await waitForLoadingToBeRemoved(container);

		const publishButton = (await findByRole('link', {
			name: /publish/i
		})) as HTMLAnchorElement;
		const deleteButton = (await findByRole('link', {
			name: /delete/i
		})) as HTMLAnchorElement;

		const header = container.querySelector('.header-root');

		expect(header.contains(publishButton)).toBeTruthy();
		expect(header.contains(deleteButton)).toBeTruthy();

		expect(publishButton).toBeInTheDocument();
		expect(publishButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=publish'
		);

		expect(deleteButton).toBeInTheDocument();
		expect(deleteButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=delete'
		);
	});

	it('renders publishabel and delete buttons to experiment to status FINISHED_NO_WINNER', async () => {
		const {container, findByRole} = render(
			<WrappedComponent publishable status='FINISHED_NO_WINNER' />
		);

		await waitForLoadingToBeRemoved(container);

		const publishButton = (await findByRole('link', {
			name: /publish/i
		})) as HTMLAnchorElement;
		const deleteButton = (await findByRole('link', {
			name: /delete/i
		})) as HTMLAnchorElement;

		const header = container.querySelector('.header-root');

		expect(header.contains(publishButton)).toBeTruthy();
		expect(header.contains(deleteButton)).toBeTruthy();

		expect(publishButton).toBeInTheDocument();
		expect(publishButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=publish'
		);

		expect(deleteButton).toBeInTheDocument();
		expect(deleteButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=delete'
		);
	});

	it('renders delete button to experiment to status TERMINATED', async () => {
		const {container, findByRole} = render(
			<WrappedComponent status='TERMINATED' />
		);

		await waitForLoadingToBeRemoved(container);

		const deleteButton = await findByRole('button', {name: /delete/i});

		expect(deleteButton).toBeInTheDocument();

		fireEvent.click(deleteButton);

		expect(document.querySelector('.modal')).toBeInTheDocument();
	});

	it('renders published label to the control variant', async () => {
		const {container, findByText} = render(
			<WrappedComponent
				publishable
				publishedDXPVariantId='DEFAULT'
				status='TERMINATED'
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(await findByText(/published/i)).toBeInTheDocument();
	});

	it('renders published label to the second variant', async () => {
		const {container, findByText} = render(
			<WrappedComponent
				publishable
				publishedDXPVariantId='44167'
				status='TERMINATED'
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(await findByText(/published/i)).toBeInTheDocument();
	});
});
