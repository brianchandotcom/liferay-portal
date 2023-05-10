import 'test/mock-modal';
import * as API from 'shared/api';
import * as data from 'test/data';
import mockStore from 'test/mock-store';
import Promise from 'metal-promise';
import React from 'react';
import {AccessTokenList} from '../AccessTokenList';
import {cleanup, fireEvent, getByText, render} from '@testing-library/react';
import {getISODate} from 'shared/util/date';
import {mockGetDateNow} from 'test/mock-date';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23'
};

const DefaultComponent = props => (
	<StaticRouter>
		<Provider store={mockStore()}>
			<AccessTokenList {...defaultProps} {...props} />
		</Provider>
	</StaticRouter>
);

describe('AccessTokenList', () => {
	beforeAll(() => {
		mockGetDateNow(data.getTimestamp(0));
	});

	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should show the generated token in a list and the "Generate Token" button should no longer be visible', () => {
		API.apiTokens.search.mockReturnValueOnce(Promise.resolve([]));

		const {container, getByTestId, queryByTestId} = render(
			<DefaultComponent />
		);

		jest.runAllTimers();

		expect(container.querySelector('.table-root')).toBeNull();
		expect(queryByTestId('generate-token-button')).toBeTruthy();

		fireEvent.click(getByTestId('generate-token-button'));

		jest.runAllTimers();

		expect(container.querySelector('.table-root')).toMatchSnapshot();
		expect(queryByTestId('generate-token-button')).toBeNull();
	});

	it('should open a modal to confirm revoking a token', () => {
		const {getByText} = render(<DefaultComponent />);

		jest.runAllTimers();

		fireEvent.click(getByText('Revoke'));

		jest.runAllTimers();

		expect(open).toBeCalled();
	});
	it('should display the "Generate Token" card if the token is expired', () => {
		API.apiTokens.search.mockReturnValueOnce(
			Promise.resolve([
				data.mockApiToken({
					expirationDate: getISODate(data.getTimestamp(-1))
				})
			])
		);

		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(
			getByText(container.querySelector('.card-body'), 'Generate Token')
		).toBeTruthy();
	});
});
