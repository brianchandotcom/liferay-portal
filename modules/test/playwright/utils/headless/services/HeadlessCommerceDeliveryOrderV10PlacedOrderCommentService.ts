/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderComment} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderComment';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryOrderV10PlacedOrderCommentService {

	/**
	 * @param placedOrderId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderPlacedOrderCommentsPage(
		placedOrderId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-comments',
			path: {
				placedOrderId: placedOrderId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param placedOrderId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10PostPlacedOrderPlacedOrderCommentsPageExportBatch(
		placedOrderId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-comments/export-batch',
			path: {
				placedOrderId: placedOrderId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param placedOrderCommentId
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderComment(
		placedOrderCommentId: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-order-comments/{placedOrderCommentId}',
			path: {
				placedOrderCommentId: placedOrderCommentId,
			},
		});
	}
}
