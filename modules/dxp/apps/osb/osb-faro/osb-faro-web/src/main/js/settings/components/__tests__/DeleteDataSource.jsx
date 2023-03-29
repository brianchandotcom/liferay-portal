import 'test/mock-modal';

import * as data from 'test/data';
import * as Router from 'shared/util/router';
import React from 'react';
import {
	cleanup,
	fireEvent,
	render,
	waitForElement
} from '@testing-library/react';
import {DataSource} from 'shared/util/records';
import {DeleteDataSource} from '../DeleteDataSource';
import {EntityTypes} from 'shared/util/constants';
import {open} from 'shared/actions/modals';
import {StaticRouter} from 'react-router';

Router.navigate = jest.fn();

jest.unmock('react-dom');

describe('DeleteDataSource', () => {
	const dataSource = data.getImmutableMock(
		DataSource,
		data.mockLiferayDataSource
	);

	const entitiesCount = {
		[EntityTypes.Account]: 123,
		[EntityTypes.Asset]: 23,
		[EntityTypes.Individual]: 1,
		[EntityTypes.IndividualsSegment]: 12,
		[EntityTypes.Page]: 1234
	};

	const groupId = '23';

	afterEach(() => {
		jest.clearAllMocks();

		cleanup();
	});

	const DefaultComponent = props => (
		<StaticRouter>
			<DeleteDataSource
				dataSource={dataSource}
				deleteMessage='Test delete message'
				deletePhrase={Liferay.Language.get('remove-x')}
				entitiesCount={entitiesCount}
				groupId={groupId}
				id='123'
				pageActionText={Liferay.Language.get('delete-data-source')}
				{...props}
			/>
		</StaticRouter>
	);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should successfully validate if the user typed in the full string to delete the datasource', () => {
		const {queryByTestId, queryByText} = render(<DefaultComponent />);

		const deleteButton = queryByText('Delete Data Source');

		expect(deleteButton).toBeDisabled();

		fireEvent.change(queryByTestId('confirmation-input'), {
			target: {value: `remove ${dataSource.name}`}
		});

		expect(deleteButton).toBeEnabled();
	});

	it('should not enable delete button if validation is unsuccessful', async () => {
		const {queryByTestId, queryByText} = render(<DefaultComponent />);

		const deleteButton = queryByText('Delete Data Source');

		expect(deleteButton).toBeDisabled();

		const confirmationInput = queryByTestId('confirmation-input');

		fireEvent.change(confirmationInput, {
			target: {value: 'remove'}
		});

		const form = await waitForElement(() => queryByTestId('form'));

		// Firing submit to trigger validation.
		fireEvent.submit(form);

		expect(deleteButton).toBeDisabled();
	});

	it('should open the data source entities modal', () => {
		expect(open).not.toBeCalled();

		const {queryByTestId, queryByText} = render(
			<DefaultComponent open={open} />
		);

		fireEvent.change(queryByTestId('confirmation-input'), {
			target: {value: `remove ${dataSource.name}`}
		});

		fireEvent.click(queryByText('12 Segments'));

		expect(open).toBeCalled();
	});

	// TODO: LRAC-4906
	it.skip('should open the confirmation modal', async () => {
		expect(open).not.toBeCalled();

		const {queryByTestId} = render(<DefaultComponent open={open} />);

		fireEvent.change(queryByTestId('confirmation-input'), {
			target: {value: `remove ${dataSource.name}`}
		});

		const form = await waitForElement(() => queryByTestId('form'));

		fireEvent.submit(form);

		// Setting timeout to wait for validation to complete
		setTimeout(() => {
			expect(open).toBeCalled();
		}, 1000);
	});

	// TODO: LRAC-4906
	it.skip('should enable "Delete Data Source" button after delete click', async () => {
		expect(open).not.toBeCalled();

		const {queryByTestId, queryByText} = render(
			<DefaultComponent open={open} />
		);

		fireEvent.change(queryByTestId('confirmation-input'), {
			target: {value: `remove ${dataSource.name}`}
		});

		const form = await waitForElement(() => queryByTestId('form'));

		fireEvent.submit(form);

		// Setting timeout to wait for validation to complete
		setTimeout(() => {
			expect(open).toBeCalled();

			expect(queryByText('Delete Data Source').toBeEnabled());
		}, 1000);
	});
});
