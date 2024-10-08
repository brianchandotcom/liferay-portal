/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import userEvent from '@testing-library/user-event';

// @ts-ignore

import fetchMock from 'fetch-mock';

import {
	ICountryAPIResponse,
	IPostalAddress,
	IPostalAddressAPIResponse,
} from '../../../src/main/resources/META-INF/resources/js/multi_shipping/Types';

import '@testing-library/jest-dom/extend-expect';
import {
	RenderResult,
	cleanup,
	fireEvent,
	render,
	waitFor,
} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import DeliveryGroupModal from '../../../src/main/resources/META-INF/resources/js/multi_shipping/DeliveryGroupModal';
import {
	IFormFields as IFormFieldsAddressLocator,
	locateFields as addressSelectorLocateFields,
} from '../AddressSelector/AddressSelector.spec';

export interface IFormFields extends IFormFieldsAddressLocator {
	cancelButton: HTMLButtonElement;
	deliveryDateInput: HTMLInputElement;
	deliveryGroupNameInput: HTMLInputElement;
	saveButton: HTMLButtonElement;
}

export function locateFields(renderedComponent: RenderResult): IFormFields {
	const fields = {
		...addressSelectorLocateFields(renderedComponent),
		cancelButton: renderedComponent.getByRole('button', {name: 'cancel'}),
		deliveryDateInput: renderedComponent.getByLabelText('date'),
		deliveryGroupNameInput: renderedComponent.getByLabelText('group-name'),
		saveButton: renderedComponent.getByRole('button', {name: 'save'}),
	};

	return fields as IFormFields;
}

export async function setFieldValue(
	field: HTMLInputElement | HTMLSelectElement,
	value: string
) {
	if (field instanceof HTMLSelectElement) {
		userEvent.selectOptions(field, value);
	}
	else {
		if (value) {
			await userEvent.type(field, value);
			field.value = value;
		}
		else {
			field.value = '';
		}
	}
	act(() => {
		fireEvent.change(field);
	});

	expect(field).toHaveValue(value);
}

describe('DeliveryGroupModal', () => {
	const handleSubmit = jest.fn();

	beforeEach(async () => {
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

	it('Must check mandatory fields', async () => {
		const renderedComponent = render(
			<DeliveryGroupModal
				accountId={10}
				handleSubmit={handleSubmit}
				observerModal={{
					dispatch: jest.fn(),
					mutation: [true, true],
				}}
				onOpenModal={jest.fn()}
			/>
		);

		const {
			addressCountrySelect,
			addressIdSelect,
			deliveryGroupNameInput,
			saveButton,
		} = locateFields(renderedComponent);

		await waitFor(() => {
			expect(addressCountrySelect?.options?.length).toBe(2);
			expect(addressIdSelect?.options?.length).toBe(2);
		});

		expect(saveButton).toBeDisabled();

		await setFieldValue(deliveryGroupNameInput, 'deliveryGroupName');

		expect(saveButton).toBeDisabled();

		await setFieldValue(addressIdSelect, String(101));

		expect(saveButton).toBeEnabled();

		await setFieldValue(addressIdSelect, String(0));

		expect(saveButton).toBeDisabled();
	});

	it('Must save new delivery group using existing address', async () => {
		const renderedComponent = render(
			<DeliveryGroupModal
				accountId={10}
				handleSubmit={handleSubmit}
				observerModal={{
					dispatch: jest.fn(),
					mutation: [true, true],
				}}
				onOpenModal={jest.fn()}
			/>
		);

		const {
			addressCountrySelect,
			addressIdSelect,
			deliveryDateInput,
			deliveryGroupNameInput,
			saveButton,
		} = locateFields(renderedComponent);

		await waitFor(() => {
			expect(addressCountrySelect?.options?.length).toBe(2);
			expect(addressIdSelect?.options?.length).toBe(2);
		});

		await setFieldValue(addressIdSelect, String(101));
		await setFieldValue(deliveryDateInput, '2024-12-12');
		await setFieldValue(deliveryGroupNameInput, 'deliveryGroupName');

		expect(saveButton).toBeEnabled();

		await act(async () => {
			saveButton.click();
		});

		const handlerCallbackResult: IPostalAddress =
			handleSubmit.mock.calls[0][0];

		expect(handlerCallbackResult.addressId).toBe(101);
		expect(handlerCallbackResult.deliveryDate).toBe('2024-12-12');
		expect(handlerCallbackResult.name).toBe('deliveryGroupName');
	});

	it('Must update delivery group using existing address', async () => {
		const renderedComponent = render(
			<DeliveryGroupModal
				accountId={10}
				deliveryGroup={{
					addressId: 101,
					deliveryDate: '2024-12-12',
					id: 100,
					name: 'deliveryGroupName',
				}}
				handleSubmit={handleSubmit}
				observerModal={{
					dispatch: jest.fn(),
					mutation: [true, true],
				}}
				onOpenModal={jest.fn()}
			/>
		);

		const {
			addressCountrySelect,
			addressIdSelect,
			deliveryDateInput,
			deliveryGroupNameInput,
			saveButton,
		} = locateFields(renderedComponent);

		await waitFor(() => {
			expect(addressCountrySelect?.options?.length).toBe(2);
			expect(addressIdSelect?.options?.length).toBe(2);
		});

		await setFieldValue(deliveryDateInput, '2024-12-13');
		await setFieldValue(deliveryGroupNameInput, 'deliveryGroupName1');

		expect(saveButton).toBeEnabled();

		await act(async () => {
			saveButton.click();
		});

		const handlerCallbackResult: IPostalAddress =
			handleSubmit.mock.calls[0][0];

		expect(handlerCallbackResult.addressId).toBe(101);
		expect(handlerCallbackResult.deliveryDate).toBe('2024-12-13');
		expect(handlerCallbackResult.id).toBe(100);
		expect(handlerCallbackResult.name).toBe('deliveryGroupName1');
	});

	it('Must save new delivery group with new address', async () => {
		fetchMock.post(
			/headless-admin-user\/.*\/accounts\/\d+\/postal-addresses$/i,
			(): IPostalAddress => {
				return {
					addressCountry: 'United States',
					addressLocality: 'addressLocality',
					addressRegion: 'Alabama',
					addressType: 'shipping',
					externalReferenceCode: '2156-321-321',
					id: 1000,
					name: 'name',
					phoneNumber: 'phoneNumber',
					postalCode: 'postalCode',
					primary: false,
					streetAddressLine1: 'streetAddressLine1',
					streetAddressLine2: 'streetAddressLine2',
					streetAddressLine3: 'streetAddressLine3',
				};
			}
		);

		const renderedComponent = render(
			<DeliveryGroupModal
				accountId={10}
				handleSubmit={handleSubmit}
				observerModal={{
					dispatch: jest.fn(),
					mutation: [true, true],
				}}
				onOpenModal={jest.fn()}
			/>
		);

		const {
			addressCountrySelect,
			addressLocalityInput,
			addressRegionSelect,
			deliveryDateInput,
			deliveryGroupNameInput,
			nameInput,
			postalCodeInput,
			saveButton,
			streetAddressLine1Input,
		} = locateFields(renderedComponent);

		await waitFor(() => {
			expect(addressCountrySelect?.options?.length).toBe(2);
		});

		await setFieldValue(addressCountrySelect, 'United States');
		await setFieldValue(addressLocalityInput, 'addressLocality');
		await setFieldValue(addressRegionSelect, 'Alabama');
		await setFieldValue(deliveryDateInput, '2024-12-13');
		await setFieldValue(deliveryGroupNameInput, 'deliveryGroupName');
		await setFieldValue(nameInput, 'name');
		await setFieldValue(postalCodeInput, 'postalCode');
		await setFieldValue(streetAddressLine1Input, 'streetAddressLine1');

		expect(saveButton).toBeEnabled();

		await act(async () => {
			saveButton.click();
		});

		const handlerCallbackResult: IPostalAddress =
			handleSubmit.mock.calls[0][0];

		expect(handlerCallbackResult.addressId).toBe(1000);
		expect(handlerCallbackResult.deliveryDate).toBe('2024-12-13');
		expect(handlerCallbackResult.name).toBe('deliveryGroupName');
	});

	it('Must update delivery group with new address', async () => {
		fetchMock.post(
			/headless-admin-user\/.*\/accounts\/\d+\/postal-addresses$/i,
			(): IPostalAddress => {
				return {
					addressCountry: 'United States',
					addressLocality: 'addressLocality',
					addressRegion: 'Alabama',
					addressType: 'shipping',
					externalReferenceCode: '2156-321-321',
					id: 1000,
					name: 'name',
					phoneNumber: 'phoneNumber',
					postalCode: 'postalCode',
					primary: false,
					streetAddressLine1: 'streetAddressLine1',
					streetAddressLine2: 'streetAddressLine2',
					streetAddressLine3: 'streetAddressLine3',
				};
			}
		);

		const renderedComponent = render(
			<DeliveryGroupModal
				accountId={10}
				deliveryGroup={{
					addressId: 101,
					deliveryDate: '2024-12-12',
					id: 100,
					name: 'deliveryGroupName',
				}}
				handleSubmit={handleSubmit}
				observerModal={{
					dispatch: jest.fn(),
					mutation: [true, true],
				}}
				onOpenModal={jest.fn()}
			/>
		);

		const {
			addressCountrySelect,
			addressIdSelect,
			addressLocalityInput,
			addressRegionSelect,
			deliveryDateInput,
			deliveryGroupNameInput,
			nameInput,
			postalCodeInput,
			saveButton,
			streetAddressLine1Input,
		} = locateFields(renderedComponent);

		await waitFor(() => {
			expect(addressCountrySelect?.options?.length).toBe(2);
		});

		await setFieldValue(addressIdSelect, String(0));
		await setFieldValue(addressCountrySelect, 'United States');
		await setFieldValue(addressLocalityInput, 'addressLocality');
		await setFieldValue(addressRegionSelect, 'Alabama');
		await setFieldValue(deliveryDateInput, '2024-12-13');
		await setFieldValue(deliveryGroupNameInput, 'deliveryGroupName1');
		await setFieldValue(nameInput, 'name');
		await setFieldValue(postalCodeInput, 'postalCode');
		await setFieldValue(streetAddressLine1Input, 'streetAddressLine1');

		expect(saveButton).toBeEnabled();

		await act(async () => {
			saveButton.click();
		});

		const handlerCallbackResult: IPostalAddress =
			handleSubmit.mock.calls[0][0];

		expect(handlerCallbackResult.addressId).toBe(1000);
		expect(handlerCallbackResult.deliveryDate).toBe('2024-12-13');
		expect(handlerCallbackResult.name).toBe('deliveryGroupName1');
	});

	it('Must preload delivery group', async () => {
		const renderedComponent = render(
			<DeliveryGroupModal
				accountId={10}
				deliveryGroup={{
					addressId: 101,
					deliveryDate: '2024-12-12',
					id: 100,
					name: 'deliveryGroupName',
				}}
				handleSubmit={handleSubmit}
				observerModal={{
					dispatch: jest.fn(),
					mutation: [true, true],
				}}
				onOpenModal={jest.fn()}
			/>
		);

		const {
			addressCountrySelect,
			addressIdSelect,
			deliveryDateInput,
			deliveryGroupNameInput,
			nameInput,
			saveButton,
		} = locateFields(renderedComponent);

		await waitFor(() => {
			expect(addressCountrySelect?.options?.length).toBe(2);
		});

		expect(addressCountrySelect).toHaveValue('United States');
		expect(addressIdSelect).toHaveValue(String(101));
		expect(deliveryDateInput).toHaveValue('2024-12-12');
		expect(deliveryGroupNameInput).toHaveValue('deliveryGroupName');
		expect(nameInput).toHaveValue('name1');

		expect(saveButton).toBeEnabled();

		await act(async () => {
			saveButton.click();
		});

		const handlerCallbackResult: IPostalAddress =
			handleSubmit.mock.calls[0][0];

		expect(handlerCallbackResult.addressId).toBe(101);
		expect(handlerCallbackResult.deliveryDate).toBe('2024-12-12');
		expect(handlerCallbackResult.name).toBe('deliveryGroupName');
	});
});
