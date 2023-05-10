/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {MockedProvider} from '@apollo/client/testing';
import '@testing-library/jest-dom';
import {act, render, screen} from '@testing-library/react';
import AccountSubscriptionModal from '.';
import {GET_ORDER_ITEMS} from '../../../../../../../../../../../common/services/liferay/graphql/order-items/queries/useGetOrderItems';

describe('Account Subscription Modal', () => {
	const functionMock = jest.fn();
	const observerMock = {
		dispatch: jest.fn(),
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

		const subscriptionStartDate = screen.getByText('08/24/2017', {
			exact: false,
		});
		expect(subscriptionStartDate).toHaveTextContent('08/24/2017');

		const subscriptionEndDate = screen.getByText('07/24/2018', {
			exact: false,
		});
		expect(subscriptionEndDate).toHaveTextContent('07/24/2018');
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
						title="Title Test"
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

		const subscriptionStatus = screen.getByText(/active/i);
		expect(subscriptionStatus).toHaveTextContent('Active');
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
});
