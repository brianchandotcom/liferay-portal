import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, getByTestId, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {Project} from 'shared/util/records';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {SubscriptionStatuses} from 'shared/util/constants';
import {UsageOverview} from '../UsageOverview';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';

jest.unmock('react-dom');

jest.mock('shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'})
}));

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn()
}));

const defaultProps = {
	groupId: '23',
	project: new Project(
		data.mockProject(23, {
			faroSubscription: fromJS(data.mockSubscription())
		})
	)
};

const WrappedComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<UsageOverview {...props} />
		</StaticRouter>
	</Provider>
);

describe('UsageOverview', () => {
	it('should render', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container} = render(<WrappedComponent {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a warning type and danger type warning if one metric is approaching limit and the other is over', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						individualsStatus: SubscriptionStatuses.Approaching,
						pageViewsStatus: SubscriptionStatuses.Over
					})
				)
			})
		);

		const {container} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(container.querySelector('.alert-warning')).toBeInTheDocument();
	});

	it('should render with an approaching limit warning if a metric is approaching plan limit', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						pageViewsStatus: SubscriptionStatuses.Approaching
					})
				)
			})
		);

		const {container} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(container.querySelector('.alert-warning')).toBeInTheDocument();
	});

	it('should render the total number of INDIVIDUALS since last anniversary and the percentage used in the plan', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						individualsCountSinceLastAnniversary: 1000,
						individualsStatus: SubscriptionStatuses.Ok
					})
				)
			})
		);

		const {getByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(getByText('1,000')).toBeInTheDocument();

		expect(getByText('1% Since Jul 08, 2018')).toBeInTheDocument();
	});

	it('should display the limit of INDIVIDUALS and PAGE VIEWS. Also, it should render a warning if INDIVIDUALS is over the limit. Also, it should render the current plan name.', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						individualsStatus: SubscriptionStatuses.Over
					})
				)
			})
		);

		const {container, getByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(getByText('105,000')).toBeInTheDocument();

		expect(
			getByText('Enterprise Plan 95,000 + 5,000 Add-On (2x)')
		).toBeInTheDocument();

		expect(getByText('7,000,000')).toBeInTheDocument();

		expect(
			getByText('Enterprise Plan 2,000,000 + 5,000,000 Add-On (1x)')
		).toBeInTheDocument();

		expect(container.querySelector('.alert-warning')).toBeInTheDocument();

		expect(getByText('Enterprise')).toBeInTheDocument();
	});

	it('should render the total of PAGE VIEWS since the last anniversary and the percentage used in the plan', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						individualsStatus: SubscriptionStatuses.Ok,
						pageViewsCountSinceLastAnniversary: 111123
					})
				)
			})
		);

		const {getByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(getByText('111,123')).toBeInTheDocument();

		expect(getByText('1.6% Since Jul 08, 2018')).toBeInTheDocument();
	});

	it('should render with an overage warning if the PAGE VIEWS metric has exceeded the plan limit', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						pageViewsStatus: SubscriptionStatuses.Over
					})
				)
			})
		);

		const {container} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(container.querySelector('.alert-warning')).toBeInTheDocument();
	});

	it('should render with a member-specific message overage warning if a metric is approaching plan limit and the user is a member role', () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => false
		}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						pageViewsStatus: SubscriptionStatuses.Approaching
					})
				)
			})
		);

		const {container, getByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(container.querySelector('.alert-warning')).toBeInTheDocument();
		expect(
			getByText(
				'Usage limit is approaching. Please contact your workspace administrator at the earliest convenience.'
			)
		).toBeInTheDocument();
	});

	it('not renders next anniversary date for BASIC PLAN', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Basic'
					})
				)
			})
		);

		const {queryByTestId} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByTestId('next-anniversary-date')).toBeNull();
	});

	it('renders next anniversary date for ENTERPRISE PLAN', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Enterprise'
					})
				)
			})
		);

		const {queryByTestId} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByTestId('next-anniversary-date').textContent).toEqual(
			'Plan usage resets on Jul 08, 2019.'
		);
	});

	it('renders next anniversary date for BUSINESS PLAN', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Business'
					})
				)
			})
		);

		const {queryByTestId} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByTestId('next-anniversary-date').textContent).toEqual(
			'Plan usage resets on Jul 08, 2019.'
		);
	});

	it('not renders management button for Member view', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(data.mockSubscription())
			})
		);

		const {queryByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(
			queryByText('Manage Subscriptions(Opens a new window)')
		).toBeNull();
	});

	it('not renders management button for Admin view', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(data.mockSubscription())
			})
		);

		const {queryByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByText('Manage Subscriptions')).toBeInTheDocument();
	});
});

describe('Subscription Details', () => {
	afterEach(cleanup);

	it('renders subscription details for BASIC PLAN', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Basic'
					})
				)
			})
		);

		const {container, queryByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByText('PURCHASED ADD-ONS')).toBeNull();

		expect(
			getByTestId(container, 'subscription-details')
		).toMatchSnapshot();
	});

	it('renders subscription details for BUSINESS PLAN', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Business'
					})
				)
			})
		);

		const {container, queryByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByText('PURCHASED ADD-ONS')).toBeInTheDocument();

		expect(
			getByTestId(container, 'subscription-details')
		).toMatchSnapshot();
	});

	it('renders subscription details for ENTERPRISE PLAN', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Enterprise'
					})
				)
			})
		);

		const {container, queryByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		expect(queryByText('PURCHASED ADD-ONS')).toBeInTheDocument();

		expect(
			getByTestId(container, 'subscription-details')
		).toMatchSnapshot();
	});
});
