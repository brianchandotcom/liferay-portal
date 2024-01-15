/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_ShippingAddress} from '../models/HeadlessCommerceAdminShipment_v1_0_ShippingAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminShipmentV10ShippingAddressService {

	/**
	 * @param shipmentId
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentShippingAddress(
		shipmentId: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/shipping-address',
			path: {
				shipmentId: shipmentId,
			},
		});
	}

	/**
	 * @param shipmentId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PatchShipmentShippingAddress(
		shipmentId: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_ShippingAddress
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/shipping-address',
			path: {
				shipmentId: shipmentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetShipmentByExternalReferenceCodeShippingAddress(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/shipping-address',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminShipment_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10PatchShipmentByExternalReferenceCodeShippingAddress(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminShipment_v1_0_ShippingAddress
	): CancelablePromise<HeadlessCommerceAdminShipment_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/shipping-address',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
