import ConfigureWorkspace from '../ConfigureWorkspace';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, fireEvent, render, waitFor} from '@testing-library/react';
import {fromJS} from 'immutable';
import {noop} from 'lodash';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import {updateProject} from 'shared/actions/projects';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useRequest} from 'shared/hooks/useRequest';

jest.unmock('react-dom');

jest.mock('shared/actions/alerts', () => ({
	actionTypes: {},
	addAlert: jest.fn()
}));

jest.mock('shared/actions/projects', () => ({
	actionTypes: {},
	updateProject: jest.fn()
}));

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn()
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn()
}));

const mockGroupId = '23';

const mockProject = {
	friendlyURL: '/foo',
	groupId: mockGroupId,
	incidentReportEmailAddresses: [],
	name: 'Foo Project',
	timeZoneId: 'UTC'
};

const defaultStore = mockStore(
	fromJS({
		projects: {
			[mockGroupId]: {
				data: mockProject
			}
		}
	})
);

const WrapperComponent = ({store = defaultStore, ...props}) => (
	<Provider store={store}>
		<StaticRouter>
			<ConfigureWorkspace
				groupId={mockGroupId}
				onClose={noop}
				onNext={noop}
				{...props}
			/>
		</StaticRouter>
	</Provider>
);

describe('ConfigureWorkspace', () => {
	beforeEach(() => {
		(updateProject as jest.Mock).mockReturnValue(Promise.resolve());

		(useCurrentUser as jest.Mock).mockReturnValue({
			isAdmin: () => true
		});

		(useRequest as jest.Mock).mockReturnValue({
			data: ['liferay.com'],
			loading: false
		});
	});

	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(<WrapperComponent />);

		expect(container).toMatchSnapshot();
	});

	it('validates friendly url', async () => {
		const {getByTestId, getByText} = render(<WrapperComponent />);

		const friendlyURLInput = getByTestId('friendly-url-input');

		fireEvent.change(friendlyURLInput, {target: {value: 'Invalid URL!'}});

		fireEvent.blur(friendlyURLInput);

		await waitFor(() => {
			expect(
				getByText(/Workspace URL must only contain/i)
			).toBeInTheDocument();
		});

		fireEvent.click(getByText('Next'));

		expect(updateProject).not.toHaveBeenCalled();
	});

	it('validates email address domains', async () => {
		const {container, getByText} = render(<WrapperComponent />);

		const emailDomainInput = container.querySelectorAll(
			'input[type="text"]'
		)[1];

		fireEvent.change(emailDomainInput, {target: {value: 'invalid-domain'}});
		fireEvent.keyDown(emailDomainInput, {key: 'Enter', keyCode: 13});

		await waitFor(() => {
			expect(
				getByText(/Please enter the domain in this format: domain.com/i)
			).toBeInTheDocument();
		});

		fireEvent.click(getByText('Next'));

		expect(updateProject).not.toHaveBeenCalled();
	});

	it('should have the next button enabled by default', () => {
		const {getByText} = render(<WrapperComponent />);

		expect(getByText('Next').closest('button')).not.toBeDisabled();
	});

	it('should disable the next button if there is an error', async () => {
		const {getByTestId, getByText} = render(<WrapperComponent />);

		const nextButton = getByText('Next').closest('button');

		expect(nextButton).not.toBeDisabled();

		const friendlyURLInput = getByTestId('friendly-url-input');

		fireEvent.change(friendlyURLInput, {target: {value: 'Invalid URL!'}});
		fireEvent.blur(friendlyURLInput);

		await waitFor(() => {
			expect(nextButton).toBeDisabled();
		});
	});
});
