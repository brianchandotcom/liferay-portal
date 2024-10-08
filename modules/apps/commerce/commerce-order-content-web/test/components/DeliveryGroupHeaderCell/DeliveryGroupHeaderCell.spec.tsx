/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import fetchMock from 'fetch-mock';

import {
	ICountryAPIResponse,
	IPostalAddressAPIResponse,
} from '../../../src/main/resources/META-INF/resources/js/multi_shipping/Types';

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, waitFor} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import DeliveryGroupHeaderCell from '../../../src/main/resources/META-INF/resources/js/multi_shipping/DeliveryGroupHeaderCell';
import {
	locateFields as deliveryGroupModalLocateFields,
	setFieldValue,
} from '../DeliveryGroupModal/DeliveryGroupModal.spec';

describe('DeliveryGroupHeaderCell', () => {
	const handleDelete = jest.fn();
	const handleSubmit = jest.fn();

	beforeEach(async () => {
		(window as any).Liferay = {
			...(window as any).Liferay,
			CustomDialogs: {},
		};

		fetchMock.get(
			/headless-admin-address\/.*\/countries/i,
			(): ICountryAPIResponse => {
				return {
					items: [
						{
							a2: 'US',
							a3: 'USA',
							active: true,
							id: 1,
							name: 'united-states',
							regions: [
								{
									active: true,
									countryId: 1,
									id: 100,
									name: 'Alabama',
									regionCode: 'AL',
									title_i18n: {
										en_US: 'Alabama',
									},
								},
							],
							title_i18n: {
								en_US: 'United States',
							},
						},
					],
				};
			}
		);

		fetchMock.get(
			/headless-admin-user\/.*\/accounts\/\d+\/postal-addresses$/i,
			(): IPostalAddressAPIResponse => {
				return {
					items: [
						{
							addressCountry: 'United States',
							addressLocality: 'addressLocality1',
							addressRegion: 'Alabama',
							addressType: 'billing-and-shipping',
							externalReferenceCode:
								'71061669-ba97-943c-70a3-96cdc0c8305a',
							id: 101,
							name: 'name1',
							phoneNumber: 'phoneNumber1',
							postalCode: 'postalCode1',
							primary: false,
							streetAddressLine1: 'streetAddressLine11',
							streetAddressLine2: 'streetAddressLine21',
							streetAddressLine3: 'streetAddressLine31',
						},
					],
				};
			}
		);
	});

	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('Must display name, date and action menu', async () => {
		const renderedComponent = render(
			<DeliveryGroupHeaderCell
				accountId={10}
				deliveryGroup={{
					addressId: 101,
					deliveryDate: '2024-12-12',
					id: 100,
					name: 'deliveryGroupName',
				}}
				handleDeleteDeliveryGroup={handleDelete}
				handleSubmitDeliveryGroup={handleSubmit}
			/>
		);

		expect(renderedComponent.getByText('deliveryGroupName')).toBeVisible();
		expect(renderedComponent.getByText('12/12/24')).toBeVisible();

		const actionsButton = renderedComponent.getByRole('button', {
			name: 'actions',
		}) as HTMLButtonElement;

		await act(async () => {
			actionsButton.click();
		});

		expect(
			renderedComponent.getByRole('menuitem', {name: 'edit'})
		).toBeVisible();
		expect(
			renderedComponent.getByRole('menuitem', {name: 'delete'})
		).toBeVisible();
	});

	it('Must delete the delivery group', async () => {
		jest.spyOn(window, 'confirm')
			.mockImplementationOnce(() => false)
			.mockImplementation(() => true);

		const deliveryGroup = {
			addressId: 101,
			deliveryDate: '2024-12-12',
			id: 100,
			name: 'deliveryGroupName',
		};

		const renderedComponent = render(
			<DeliveryGroupHeaderCell
				accountId={10}
				deliveryGroup={deliveryGroup}
				handleDeleteDeliveryGroup={handleDelete}
				handleSubmitDeliveryGroup={handleSubmit}
			/>
		);

		const actionsButton = renderedComponent.getByRole('button', {
			name: 'actions',
		}) as HTMLButtonElement;

		await act(async () => {
			actionsButton.click();
		});

		const deleteButton = renderedComponent.getByRole('menuitem', {
			name: 'delete',
		}) as HTMLButtonElement;

		await act(async () => {
			deleteButton.click();
		});

		expect(window.confirm).toHaveBeenCalled();
		expect(handleDelete).not.toBeCalled();

		await act(async () => {
			deleteButton.click();
		});

		expect(window.confirm).toBeCalled();
		expect(handleDelete).toBeCalledWith(deliveryGroup);
	});

	it('Must update the delivery group', async () => {
		const deliveryGroup = {
			addressId: 101,
			deliveryDate: '2024-12-12',
			id: 100,
			name: 'deliveryGroup',
		};

		const renderedComponent = render(
			<DeliveryGroupHeaderCell
				accountId={10}
				deliveryGroup={deliveryGroup}
				handleDeleteDeliveryGroup={handleDelete}
				handleSubmitDeliveryGroup={handleSubmit}
			/>
		);

		const actionsButton = renderedComponent.getByRole('button', {
			name: 'actions',
		}) as HTMLButtonElement;

		await act(async () => {
			actionsButton.click();
		});

		const editButton = renderedComponent.getByRole('menuitem', {
			name: 'edit',
		}) as HTMLButtonElement;

		await act(async () => {
			editButton.click();
		});

		await waitFor(() => {
			expect(
				renderedComponent.getByRole('button', {name: 'save'})
			).toBeVisible();
		});

		const {deliveryGroupNameInput, saveButton} =
			deliveryGroupModalLocateFields(renderedComponent);

		await setFieldValue(deliveryGroupNameInput, 'deliveryGroupName');

		await act(async () => {
			saveButton.click();
		});

		expect(handleSubmit).toBeCalledWith({
			address: {
				addressCountry: 'United States',
				addressLocality: 'addressLocality1',
				addressRegion: 'Alabama',
				addressType: 'billing-and-shipping',
				externalReferenceCode: '71061669-ba97-943c-70a3-96cdc0c8305a',
				id: 101,
				name: 'name1',
				phoneNumber: 'phoneNumber1',
				postalCode: 'postalCode1',
				primary: false,
				streetAddressLine1: 'streetAddressLine11',
				streetAddressLine2: 'streetAddressLine21',
				streetAddressLine3: 'streetAddressLine31',
			},
			addressId: 101,
			deliveryDate: '2024-12-12',
			id: 100,
			name: 'deliveryGroupName',
		});
	});
});
