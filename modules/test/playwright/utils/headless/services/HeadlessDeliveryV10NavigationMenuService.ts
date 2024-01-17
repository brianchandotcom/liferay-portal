/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_NavigationMenu} from '../models/HeadlessDelivery_v1_0_NavigationMenu';
import type {HeadlessDelivery_v1_0_PageNavigationMenu} from '../models/HeadlessDelivery_v1_0_PageNavigationMenu';
import type {HeadlessDelivery_v1_0_PagePermission} from '../models/HeadlessDelivery_v1_0_PagePermission';
import type {HeadlessDelivery_v1_0_Permission} from '../models/HeadlessDelivery_v1_0_Permission';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessDeliveryV10NavigationMenuService {

	/**
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param page
	 * @param pageSize
	 * @returns HeadlessDelivery_v1_0_PageNavigationMenu default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteNavigationMenusPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PageNavigationMenu> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Creates a new navigation menu.
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_NavigationMenu default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteNavigationMenu(
		siteId: string,
		requestBody?: HeadlessDelivery_v1_0_NavigationMenu
	): CancelablePromise<HeadlessDelivery_v1_0_NavigationMenu> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteNavigationMenuBatch(
		siteId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus/batch',
			path: {
				siteId: siteId,
			},
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
	public static headlessDeliveryV10PutNavigationMenuBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/navigation-menus/batch',
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
	public static headlessDeliveryV10DeleteNavigationMenuBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/navigation-menus/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetSiteNavigationMenuPermissionsPage(
		siteId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus/permissions',
			path: {
				siteId: siteId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param siteId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutSiteNavigationMenuPermissionsPage(
		siteId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus/permissions',
			path: {
				siteId: siteId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param navigationMenuId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @returns HeadlessDelivery_v1_0_NavigationMenu default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetNavigationMenu(
		navigationMenuId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string
	): CancelablePromise<HeadlessDelivery_v1_0_NavigationMenu> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}',
			path: {
				navigationMenuId: navigationMenuId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
			},
		});
	}

	/**
	 * Replaces the navigation menu with the information sent in the request body. Any missing fields are deleted, unless they are required.
	 * @param navigationMenuId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_NavigationMenu default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutNavigationMenu(
		navigationMenuId: string,
		requestBody?: HeadlessDelivery_v1_0_NavigationMenu
	): CancelablePromise<HeadlessDelivery_v1_0_NavigationMenu> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}',
			path: {
				navigationMenuId: navigationMenuId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the navigation menu and returns a 204 if the operation succeeds
	 * @param navigationMenuId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10DeleteNavigationMenu(
		navigationMenuId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}',
			path: {
				navigationMenuId: navigationMenuId,
			},
		});
	}

	/**
	 * @param siteId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PostSiteNavigationMenusPageExportBatch(
		siteId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus/export-batch',
			path: {
				siteId: siteId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param navigationMenuId
	 * @param fields
	 * @param nestedFields
	 * @param restrictFields
	 * @param roleNames
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10GetNavigationMenuPermissionsPage(
		navigationMenuId: string,
		fields?: string,
		nestedFields?: string,
		restrictFields?: string,
		roleNames?: string
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}/permissions',
			path: {
				navigationMenuId: navigationMenuId,
			},
			query: {
				fields: fields,
				nestedFields: nestedFields,
				restrictFields: restrictFields,
				roleNames: roleNames,
			},
		});
	}

	/**
	 * @param navigationMenuId
	 * @param requestBody
	 * @returns HeadlessDelivery_v1_0_PagePermission default response
	 * @throws ApiError
	 */
	public static headlessDeliveryV10PutNavigationMenuPermissionsPage(
		navigationMenuId: string,
		requestBody?: Array<HeadlessDelivery_v1_0_Permission>
	): CancelablePromise<HeadlessDelivery_v1_0_PagePermission> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}/permissions',
			path: {
				navigationMenuId: navigationMenuId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
