/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_PageLowStockAction} from '../models/HeadlessCommerceAdminCatalog_v1_0_PageLowStockAction';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminCatalogV10LowStockActionService {

	/**
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10PostLowStockActionsPageExportBatch(
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-catalog/v1.0/low-stock-actions/export-batch',
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrive low stock actions for products.
	 * @returns HeadlessCommerceAdminCatalog_v1_0_PageLowStockAction default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetLowStockActionsPage(): CancelablePromise<HeadlessCommerceAdminCatalog_v1_0_PageLowStockAction> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/low-stock-actions',
		});
	}
}
