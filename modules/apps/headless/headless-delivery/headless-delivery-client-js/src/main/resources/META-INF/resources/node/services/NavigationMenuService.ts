/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NavigationMenu } from '../models/NavigationMenu';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class NavigationMenuService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the navigation menu and returns a 204 if the operation succeeds
     * @returns void
     * @throws ApiError
     */
    public deleteNavigationMenu({
        navigationMenuId,
    }: {
        navigationMenuId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}',
            path: {
                'navigationMenuId': navigationMenuId,
            },
        });
    }
    /**
     * @returns NavigationMenu
     * @throws ApiError
     */
    public getNavigationMenu({
        navigationMenuId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        navigationMenuId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<NavigationMenu> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}',
            path: {
                'navigationMenuId': navigationMenuId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Replaces the navigation menu with the information sent in the request body. Any missing fields are deleted, unless they are required.
     * @returns NavigationMenu
     * @throws ApiError
     */
    public putNavigationMenu({
        navigationMenuId,
        requestBody,
    }: {
        navigationMenuId: number,
        requestBody?: NavigationMenu,
    }): CancelablePromise<NavigationMenu> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}',
            path: {
                'navigationMenuId': navigationMenuId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getNavigationMenuPermissionsPage({
        navigationMenuId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        navigationMenuId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}/permissions',
            path: {
                'navigationMenuId': navigationMenuId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putNavigationMenuPermissionsPage({
        navigationMenuId,
        requestBody,
    }: {
        navigationMenuId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/navigation-menus/{navigationMenuId}/permissions',
            path: {
                'navigationMenuId': navigationMenuId,
            },
            body: requestBody,
        });
    }
    /**
     * @returns NavigationMenu
     * @throws ApiError
     */
    public getSiteNavigationMenusPage({
        siteId,
        fields,
        nestedFields,
        restrictFields,
        page,
        pageSize,
    }: {
        siteId: number,
        fields?: string,
        nestedFields?: number,
        restrictFields?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<NavigationMenu>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * Creates a new navigation menu.
     * @returns NavigationMenu
     * @throws ApiError
     */
    public postSiteNavigationMenu({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: NavigationMenu,
    }): CancelablePromise<NavigationMenu> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteNavigationMenuPermissionsPage({
        siteId,
        fields,
        nestedFields,
        restrictFields,
        roleNames,
    }: {
        siteId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus/permissions',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteNavigationMenuPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-delivery/v1.0/sites/{siteId}/navigation-menus/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
}
