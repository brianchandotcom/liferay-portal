import BatchActionModal from '../BatchActionModal';
import React from 'react';
import {noop} from 'lodash';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('BatchActionModal', () => {
	it('should render', () => {
		const {container} = render(
			<BatchActionModal
				actionOptions={{
					actionCountString: 'changing-permissions-for-x-users',
					options: ['Site Administrator', 'Site Member'],
					optionsLabel: 'Select Permission'
				}}
				onClose={noop}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with all items checked', () => {
		const columns = [
			{
				accessor: 'name',
				className: 'table-cell-expand',
				label: 'Name',
				sortable: false,
				title: true
			},
			{
				accessor: 'emailAddress',
				label: 'Email',
				sortable: false
			},
			{
				accessor: 'status',
				label: 'Status',
				sortable: false
			},
			{
				accessor: 'roleName',
				label: 'Permission',
				sortable: false
			}
		];

		const items = [
			{
				emailAddress: 'exmail@example.com',
				id: 1,
				name: 'test name',
				roleName: 'Site Owner',
				status: 'approved'
			}
		];

		const {container} = render(
			<BatchActionModal
				actionOptions={{
					actionCountString: 'changing-permissions-for-x-users',
					options: ['Site Administrator', 'Site Member'],
					optionsLabel: 'Select Permission'
				}}
				columns={columns}
				items={items}
				onClose={noop}
			/>
		);

		container
			.querySelectorAll('input[type=checkbox]')
			.forEach(node => expect(node.checked).toBe(true));
	});
});
