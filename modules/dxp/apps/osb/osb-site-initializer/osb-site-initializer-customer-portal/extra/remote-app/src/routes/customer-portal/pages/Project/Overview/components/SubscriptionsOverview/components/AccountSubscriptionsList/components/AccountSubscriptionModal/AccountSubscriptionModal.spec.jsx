/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import '@testing-library/jest-dom';
import {act, render, screen} from '@testing-library/react';
import {vi} from 'vitest';
import AccountSubscriptionModal from '.';
import {GET_ORDER_ITEMS} from '../../../../../../../../../../../common/services/liferay/graphql/order-items/queries/useGetOrderItems';

describe('Account Subscription Modal', () => {
	const functionMock = vi.fn();

	const observerMock = {
		dispatch: vi.fn(),
		mutation: [true, true],
	};

	const mocks = {
		request: {
			query: GET_ORDER_ITEMS,
			variables: {
				filter: `customFields/accountSubscriptionERC eq 'ERC-001'`,
				page: 1,
				pageSize: 5,
			},
		},
		result: {
			data: {
				orderItems: {
					__typename: 'OrderItemPage',
					items: [
						{
							__typename: 'OrderItem',
							customFields: [
								{
									__typename: 'CustomField',
									customValue: {
										__typename: 'CustomValue',
										data: 0,
									},
									name: 'provisionedCount',
								},
								{
									__typename: 'CustomField',
									customValue: {
										__typename: 'CustomValue',
										data: 'ERC-001_test_test',
									},
									name: 'accountSubscriptionERC',
								},
								{
									__typename: 'CustomField',
									customValue: {
										__typename: 'CustomValue',
										data: 'ERC-001_test',
									},
									name: 'accountSubscriptionGroupERC',
								},
								{
									__typename: 'CustomField',
									customValue: {
										__typename: 'CustomValue',
										data: 'Active',
									},
									name: 'status',
								},
							],
							externalReferenceCode: 'ERC-001',
							options: {
								endDate: '2018-07-25T00:00:00Z',
								instanceSize: '1',
								startDate: '2017-08-25T00:00:00Z',
							},
							quantity: 5,
							reducedCustomFields: {
								accountSubscriptionERC: 'ERC-001_test_test',
								accountSubscriptionGroupERC: 'ERC-001_test',
								provisionedCount: 0,
								status: 'Active',
							},
						},
					],
					lastPage: 1,
					page: 1,
					pageSize: 5,
					totalCount: 1,
				},
			},
		},
	};

	it('Displays the Start and End Date of Subscriptions', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Title Test"
					/>
				</MockedProvider>
			);
		});

		await act(async () => {
			await new Promise((resolve) => setTimeout(resolve, 1000));
		});

		const subscriptionStartDate = screen.getByText('08/25/2017', {
			exact: false,
		});
		expect(subscriptionStartDate).toHaveTextContent('08/25/2017');

		const subscriptionEndDate = screen.getByText('07/25/2018', {
			exact: false,
		});
		expect(subscriptionEndDate).toHaveTextContent('07/25/2018');
	});

	it('Displays the number of Purchased Subscriptions', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Title Test"
					/>
				</MockedProvider>
			);
		});

		await act(async () => {
			await new Promise((resolve) => setTimeout(resolve, 1000));
		});

		const subscriptionPurchased = screen.getByText(5);
		expect(subscriptionPurchased).toHaveTextContent(5);
	});

	it('Displays the number of Instances Size of Subscriptions', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Portal Backup"
					/>
				</MockedProvider>
			);
		});

		await act(async () => {
			await new Promise((resolve) => setTimeout(resolve, 1000));
		});

		const subscriptionInstanceSize = screen.getByText('1');
		expect(subscriptionInstanceSize).toHaveTextContent('1');
	});

	it('Displays the Subscriptions Status', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Title Test"
					/>
				</MockedProvider>
			);
		});

		await act(async () => {
			await new Promise((resolve) => setTimeout(resolve, 1000));
		});

		const subscriptionTable = document.querySelector('.table');
		expect(subscriptionTable).toHaveTextContent('Active');
	});

	it('Displays Subscription Terms Table Pagination', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Title Test"
					/>
				</MockedProvider>
			);
		});

		await act(async () => {
			await new Promise((resolve) => setTimeout(resolve, 1000));
		});

		const subscriptionTermsPagination = screen.getByText(
			/showing 1 to 1 of 1 entries/i
		);
		expect(subscriptionTermsPagination).toBeInTheDocument();
	});

	it('Not Display Instance Size Column When is Liferay Experience Cloud', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Liferay Experience Cloud Enterprise"
					/>
				</MockedProvider>
			);
		});

		const instanceSizeColumn = screen.queryByText('Instance Size');
		expect(instanceSizeColumn).not.toBeInTheDocument();
	});

	it('Display Instance Size Column When is Portal Backup', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Portal Backup"
					/>
				</MockedProvider>
			);
		});

		const instanceSizeColumn = screen.queryByText('Instance Size');
		expect(instanceSizeColumn).toBeInTheDocument();
	});

	it('Display Purchased Column', async () => {
		await act(async () => {
			render(
				<MockedProvider addTypename={false} mocks={[mocks]}>
					<AccountSubscriptionModal
						externalReferenceCode="ERC-001"
						observer={observerMock}
						onClose={functionMock}
						title="Title Test"
					/>
				</MockedProvider>
			);
		});

		const quantityColumn = screen.queryByText('Purchased');
		expect(quantityColumn).toBeInTheDocument();
	});
});
