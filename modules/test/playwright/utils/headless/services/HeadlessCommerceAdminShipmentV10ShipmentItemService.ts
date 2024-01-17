/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_PageShipmentItem} from '../models/HeadlessCommerceAdminShipment_v1_0_PageShipmentItem';
import type {HeadlessCommerceAdminShipment_v1_0_ShipmentItem} from '../models/HeadlessCommerceAdminShipment_v1_0_ShipmentItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminShipmentV10ShipmentItemService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentByExternalReferenceCodeItem(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShipmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10DeleteShipmentItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PatchShipmentItemByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_ShipmentItem
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShipmentItem> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/by-externalReferenceCode/{externalReferenceCode}',
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
	 * @returns HeadlessCommerceAdminShipment_v1_0_PageShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentByExternalReferenceCodeItemsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_PageShipmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/items',
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
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PutShipmentByExternalReferenceCodeItem(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_ShipmentItem
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShipmentItem> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/items',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param shipmentItemId
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentItem(
		shipmentItemId: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShipmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/{shipmentItemId}',
			path: {
				shipmentItemId: shipmentItemId,
			},
		});
	}

	/**
	 * @param shipmentItemId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10DeleteShipmentItem(
		shipmentItemId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/{shipmentItemId}',
			path: {
				shipmentItemId: shipmentItemId,
			},
		});
	}

	/**
	 * @param shipmentItemId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PatchShipmentItem(
		shipmentItemId: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_ShipmentItem
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShipmentItem> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/{shipmentItemId}',
			path: {
				shipmentItemId: shipmentItemId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param shipmentId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminShipment_v1_0_PageShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentItemsPage(
		shipmentId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_PageShipmentItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/items',
			path: {
				shipmentId: shipmentId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShipmentItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentItem(
		shipmentId: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_ShipmentItem
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShipmentItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/items',
			path: {
				shipmentId: shipmentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10DeleteShipmentItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-shipment/v1.0/shipment-items/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
