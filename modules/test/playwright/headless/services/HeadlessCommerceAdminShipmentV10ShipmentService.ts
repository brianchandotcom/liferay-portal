/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_PageShipment} from '../models/HeadlessCommerceAdminShipment_v1_0_PageShipment';
import type {HeadlessCommerceAdminShipment_v1_0_Shipment} from '../models/HeadlessCommerceAdminShipment_v1_0_Shipment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminShipmentV10ShipmentService {

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminShipment_v1_0_PageShipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_PageShipment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments',
			query: {
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipment(
		requestBody?: HeadlessCommerceAdminShipment_v1_0_Shipment
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrive information of the given Shipment.
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PutShipmentByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_Shipment
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10DeleteShipmentByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PatchShipmentByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_Shipment
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentByExternalReferenceCodeStatusShipped(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/status-shipped',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/batch',
			query: {
				callbackURL: callbackUrl,
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
	public static headlessCommerceAdminShipmentV10DeleteShipmentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param shipmentId
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentStatusFinishProcessing(
		shipmentId: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/status-finish-processing',
			path: {
				shipmentId: shipmentId,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentStatusDelivered(
		shipmentId: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/status-delivered',
			path: {
				shipmentId: shipmentId,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipment(
		shipmentId: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}',
			path: {
				shipmentId: shipmentId,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10DeleteShipment(
		shipmentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}',
			path: {
				shipmentId: shipmentId,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PatchShipment(
		shipmentId: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_Shipment
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}',
			path: {
				shipmentId: shipmentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentByExternalReferenceCodeStatusFinishProcessing(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/status-finish-processing',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentStatusShipped(
		shipmentId: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/status-shipped',
			path: {
				shipmentId: shipmentId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminShipment_v1_0_Shipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentByExternalReferenceCodeStatusDelivered(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_Shipment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/status-delivered',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PostShipmentsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/export-batch',
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
