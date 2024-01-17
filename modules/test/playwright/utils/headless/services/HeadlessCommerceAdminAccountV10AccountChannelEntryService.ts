/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry} from '../models/HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry';
import type {HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AccountChannelEntryService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelPriceListsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-price-lists',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelPriceList(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-price-lists',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelDeliveryTermsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-delivery-terms',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelDeliveryTerm(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-delivery-terms',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelPriceListId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-price-list/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelPriceListId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-price-list/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelPriceListId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-price-list/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelBillingAddressId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-billing-addresses/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelBillingAddressId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-billing-addresses/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelBillingAddressId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-billing-addresses/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelPaymentTermId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-payment-terms/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelPaymentTermId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-payment-terms/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelPaymentTermId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-payment-terms/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelShippingAddressesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-addresses',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelShippingAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-addresses',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelPaymentTermsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-terms',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelPaymentTerm(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-terms',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelDeliveryTermId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-delivery-terms/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelDeliveryTermId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-delivery-terms/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelDeliveryTermId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-delivery-terms/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelDiscountsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-discounts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelDiscount(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-discounts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelDeliveryTermsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-delivery-terms',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelDeliveryTerm(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-delivery-terms',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelDiscountId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-discounts/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelDiscountId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-discounts/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelDiscountId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-discounts/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelCurrenciesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-currencies',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelCurrency(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-currencies',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelPaymentMethodsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-methods',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelPaymentMethod(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-methods',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelCurrenciesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-currencies',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelCurrency(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-currencies',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelBillingAddressesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-billing-addresses',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelBillingAddress(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-billing-addresses',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelUsersPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-users',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelUser(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-users',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelPaymentTermsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-terms',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelPaymentTerm(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-terms',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelShippingAddressesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-addresses',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelShippingAddress(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-addresses',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelUsersPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-users',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelUser(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-users',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelUserId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-users/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelUserId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-users/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelUserId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-users/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelPaymentMethodId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-payment-methods/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelPaymentMethodId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-payment-methods/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelPaymentMethodId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-payment-methods/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelCurrencyId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-currencies/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelCurrencyId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-currencies/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelCurrencyId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-currencies/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelBillingAddressesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-billing-addresses',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelBillingAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-billing-addresses',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelDiscountsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-discounts',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelDiscount(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-discounts',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelPaymentMethodsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-methods',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelPaymentMethod(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-methods',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelShippingAddressId(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-addresses/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelShippingAddressId(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-addresses/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelShippingAddressId(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-addresses/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelPriceListsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-price-lists',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelPriceList(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-price-lists',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
