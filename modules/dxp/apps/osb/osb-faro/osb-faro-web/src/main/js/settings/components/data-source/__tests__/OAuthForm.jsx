import * as data from 'test/data';
import React from 'react';
import {DataSource} from 'shared/util/records';
import {
	DataSourceStates,
	DataSourceStatuses,
	DataSourceTypes
} from 'shared/util/constants';
import {fireEvent, render} from '@testing-library/react';
import {OAUTH_CALLBACK_URL} from 'shared/util/oauth';
import {OAuthForm} from '../OAuthForm';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

React.createRef = jest.fn();

const getMockRef = () => ({
	current: {
		validateForm: jest.fn()
	}
});

const defaultProps = {
	authorized: true,
	callbackUrl: OAUTH_CALLBACK_URL,
	dataSource: data.getImmutableMock(
		DataSource,
		data.mockLiferayDataSource,
		0
	),
	defaultUrl: 'https://foo.com',
	groupId: '23',
	onAuthorize: jest.fn(),
	onSubmit: jest.fn(),
	type: DataSourceTypes.Liferay
};

describe('OAuthForm', () => {
	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<OAuthForm {...defaultProps} />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with oauth owner', () => {
		const {queryByText} = render(
			<StaticRouter>
				<OAuthForm
					{...defaultProps}
					dataSource={data.getImmutableMock(
						DataSource,
						data.mockLiferayDataSource,
						23,
						{
							credentials: {
								oAuthOwner: {
									emailAddress: 'test@liferay.com',
									name: 'test test'
								}
							}
						}
					)}
				/>
			</StaticRouter>
		);

		expect(queryByText('test test').parentElement).toHaveClass(
			'oauth-owner'
		);
	});

	it('should render with "remove" button', () => {
		const {queryByText} = render(
			<StaticRouter>
				<OAuthForm
					{...defaultProps}
					dataSource={data.getImmutableMock(
						DataSource,
						data.mockLiferayDataSource,
						23,
						{
							credentials: {
								oAuthOwner: {
									emailAddress: 'test@liferay.com',
									name: 'test test'
								}
							}
						}
					)}
				/>
			</StaticRouter>
		);

		expect(queryByText('Remove')).toBeTruthy();
	});

	it('should render without an edit button if authorized is false', () => {
		const {queryByText} = render(
			<StaticRouter>
				<OAuthForm {...defaultProps} authorized={false} />
			</StaticRouter>
		);

		expect(queryByText('Edit')).toBeNull();
	});

	it('should render by default as disabled with an edit button if this is an existing data source', () => {
		const {getByText} = render(
			<StaticRouter>
				<OAuthForm {...defaultProps} authorized id='23' />
			</StaticRouter>
		);

		expect(
			getByText('Authorize to add a new owner.').parentElement
		).toHaveClass('disabled');
		expect(getByText('Edit')).toBeTruthy();
	});

	it('should render with an "Authorize & Save" and "Cancel" button if the edit button is clicked', () => {
		React.createRef.mockReturnValueOnce(getMockRef('foo'));

		const {getByText} = render(
			<StaticRouter>
				<OAuthForm
					{...defaultProps}
					authorized
					dataSource={data.getImmutableMock(
						DataSource,
						data.mockSalesforceDataSource
					)}
					id='23'
					type={DataSourceTypes.Salesforce}
				/>
			</StaticRouter>
		);

		fireEvent.click(getByText('Edit'));

		expect(getByText('Authorize & Save')).toBeTruthy();
		expect(getByText('Cancel')).toBeTruthy();
	});

	it('should render with all inputs disabled if the user is not authorized and the datasource has an UNDEFINED_ERROR state', () => {
		const {container} = render(
			<StaticRouter>
				<OAuthForm
					{...defaultProps}
					authorized={false}
					dataSource={data.getImmutableMock(
						DataSource,
						data.mockLiferayDataSource,
						{
							state: DataSourceStates.UndefinedError,
							status: DataSourceStatuses.Inactive
						}
					)}
					id='23'
					type={DataSourceTypes.Liferay}
				/>
			</StaticRouter>
		);

		container
			.querySelectorAll('input')
			.forEach(element => expect(element).toBeDisabled());
	});
});
