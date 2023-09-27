import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {fromJS} from 'immutable';
import {Project, User} from 'shared/util/records';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {SubscriptionStatuses, UserRoleNames} from 'shared/util/constants';
import {UsageOverview} from '../UsageOverview';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: new User(data.mockUser()),
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
		const {container} = render(<WrappedComponent {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a warning type and danger type warning if one metric is approaching limit and the other is over', () => {
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
		expect(container.querySelector('.alert-danger')).toBeInTheDocument();
	});

	it('should render with an approaching limit warning if a metric is approaching plan limit', () => {
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

		expect(getByText('1% since July 8, 2018')).toBeInTheDocument();
	});

	it('should display the limit of INDIVIDUALS and PAGE VIEWS. Also, it should render a warning if INDIVIDUALS is over the limit. Also, it should render the current plan name.', () => {
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

		expect(getByText('105,000')).toHaveClass('metric-limit');

		expect(
			getByText('Enterprise Plan 95,000 + 5,000 Add-On (2x)')
		).toBeInTheDocument();

		expect(getByText('7,000,000')).toBeInTheDocument();

		expect(getByText('7,000,000')).toHaveClass('metric-limit');

		expect(
			getByText('Enterprise Plan 2,000,000 + 5,000,000 Add-On (1x)')
		).toBeInTheDocument();

		expect(container.querySelector('.alert-danger')).toBeInTheDocument();

		expect(getByText('Enterprise')).toBeInTheDocument();

		expect(getByText('Enterprise')).toHaveClass('plan-name');

		expect(getByText('Current Plan')).toBeInTheDocument();
	});

	it('should render the total of PAGE VIEWS since the last anniversary and the percentage used in the plan', () => {
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

		expect(getByText('1.6% since July 8, 2018')).toBeInTheDocument();
	});

	it('should render with an overage warning if the PAGE VIEWS metric has exceeded the plan limit', () => {
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

		expect(container.querySelector('.alert-danger')).toBeInTheDocument();
	});

	it('should render with a member-specific message overage warning if a metric is approaching plan limit and the user is a member role', () => {
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
			<WrappedComponent
				{...defaultProps}
				currentUser={
					new User(data.mockUser(0, {roleName: UserRoleNames.Member}))
				}
				project={mockProject}
			/>
		);

		expect(container.querySelector('.alert-warning')).toBeInTheDocument();
		expect(getByText('Usage Limit Approaching:')).toBeInTheDocument();
	});

	it('should use default addons for basic plans', () => {
		const mockProject = new Project(
			data.mockProject(23, {
				faroSubscription: fromJS(
					data.mockSubscription({
						name: 'Liferay Analytics Cloud Basic'
					})
				)
			})
		);

		const {getAllByText} = render(
			<WrappedComponent {...defaultProps} project={mockProject} />
		);

		jest.runAllTimers();

		expect(getAllByText('Add-ons')[0]).toBeInTheDocument();
	});
});
