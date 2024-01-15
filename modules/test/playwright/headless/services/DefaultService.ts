/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount';
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListAccount} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceListAccount';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListOrderType} from '../models/HeadlessCommerceAdminPricing_v2_0_PriceListOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class DefaultService {

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static cUniversitiesGetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/c/universities/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static frontendViewStateV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/frontend-view-state/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminCatalogV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-catalog/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dataEngineV20GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/data-engine/v2.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminContentV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-content/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminTaxonomyV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-taxonomy/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminInventoryV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-inventory/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleAccount(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-accounts',
			path: {
				id: id,
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
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/order-rule-account-groups/batch',
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
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/order-rule-accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleAccountGroup(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-account-groups',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPaymentV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-payment/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static notificationV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/notification/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminListTypeV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-list-type/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static batchPlannerV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/batch-planner/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminSiteSettingV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-site-setting/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static portalSearchRestV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/portal-search-rest/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceListAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListAccount(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceListAccount
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceListAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-list-accounts',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceListOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceListOrderType
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceListOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-list-order-types',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListAccountGroup(
		id: string,
		requestBody?: HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/{id}/price-list-account-groups',
			path: {
				id: id,
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
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/price-list-order-types/batch',
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
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/price-list-accounts/batch',
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
	public static headlessCommerceAdminPricingV20PostPriceListIdPriceListAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-pricing/v2.0/price-lists/price-list-account-groups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessUserNotificationV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-user-notification/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static captchaV10GetOpenApi(type: string): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/captcha/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminShipmentV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-shipment/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessBatchEngineV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-batch-engine/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCatalogV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-catalog/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static objectAdminV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/object-admin/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminAddressV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-address/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static dispatchRestV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/dispatch-rest/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static digitalSignatureRestV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/digital-signature-rest/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessPortalInstancesV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-portal-instances/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessFormV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-form/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static bulkV10GetOpenApi(type: string): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/bulk/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessSiteV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-site/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static analyticsSettingsRestV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/analytics-settings-rest/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static changeTrackingRestV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/change-tracking-rest/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}

	/**
	 * @param type
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminWorkflowV10GetOpenApi(
		type: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-workflow/v1.0/openapi.{type}',
			path: {
				type: type,
			},
		});
	}
}
