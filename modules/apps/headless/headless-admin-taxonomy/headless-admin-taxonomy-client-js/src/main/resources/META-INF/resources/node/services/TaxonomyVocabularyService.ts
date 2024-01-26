/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TaxonomyVocabulary } from '../models/TaxonomyVocabulary';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TaxonomyVocabularyService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public getAssetLibraryTaxonomyVocabulariesPage({
        assetLibraryId,
        aggregationTerms,
        fields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        assetLibraryId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<TaxonomyVocabulary>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public postAssetLibraryTaxonomyVocabulary({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: TaxonomyVocabulary,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the asset library's taxonomy vocabulary by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the asset library's taxonomy vocabulary by external reference code.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public getAssetLibraryTaxonomyVocabularyByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
        fields,
        restrictFields,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the asset library's taxonomy vocabulary with the given external reference code, or creates it if it not exists.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public putAssetLibraryTaxonomyVocabularyByExternalReferenceCode({
        assetLibraryId,
        externalReferenceCode,
        requestBody,
    }: {
        assetLibraryId: number,
        externalReferenceCode: string,
        requestBody?: TaxonomyVocabulary,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
            path: {
                'assetLibraryId': assetLibraryId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getAssetLibraryTaxonomyVocabularyPermissionsPage({
        assetLibraryId,
        fields,
        restrictFields,
        roleNames,
    }: {
        assetLibraryId: number,
        fields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putAssetLibraryTaxonomyVocabularyPermissionsPage({
        assetLibraryId,
        requestBody,
    }: {
        assetLibraryId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/asset-libraries/{assetLibraryId}/taxonomy-vocabularies/permissions',
            path: {
                'assetLibraryId': assetLibraryId,
            },
            body: requestBody,
        });
    }
    /**
     * Retrieves a Site's taxonomy vocabularies. Results can be paginated, filtered, searched, and sorted.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public getSiteTaxonomyVocabulariesPage({
        siteId,
        aggregationTerms,
        fields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        siteId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<TaxonomyVocabulary>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies',
            path: {
                'siteId': siteId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Inserts a new taxonomy vocabulary in a Site.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public postSiteTaxonomyVocabulary({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: TaxonomyVocabulary,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes the site's taxonomy vocabulary by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteSiteTaxonomyVocabularyByExternalReferenceCode({
        siteId,
        externalReferenceCode,
    }: {
        siteId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Retrieves the site's taxonomy vocabulary by external reference code.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public getSiteTaxonomyVocabularyByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        fields,
        restrictFields,
    }: {
        siteId: number,
        externalReferenceCode: string,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates the site's taxonomy vocabulary with the given external reference code, or creates it if it not exists.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public putSiteTaxonomyVocabularyByExternalReferenceCode({
        siteId,
        externalReferenceCode,
        requestBody,
    }: {
        siteId: number,
        externalReferenceCode: string,
        requestBody?: TaxonomyVocabulary,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/by-external-reference-code/{externalReferenceCode}',
            path: {
                'siteId': siteId,
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getSiteTaxonomyVocabularyPermissionsPage({
        siteId,
        fields,
        restrictFields,
        roleNames,
    }: {
        siteId: number,
        fields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/permissions',
            path: {
                'siteId': siteId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putSiteTaxonomyVocabularyPermissionsPage({
        siteId,
        requestBody,
    }: {
        siteId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/sites/{siteId}/taxonomy-vocabularies/permissions',
            path: {
                'siteId': siteId,
            },
            body: requestBody,
        });
    }
    /**
     * Deletes the taxonomy vocabulary and returns a 204 if the operation succeeds.
     * @returns void
     * @throws ApiError
     */
    public deleteTaxonomyVocabulary({
        taxonomyVocabularyId,
    }: {
        taxonomyVocabularyId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
            path: {
                'taxonomyVocabularyId': taxonomyVocabularyId,
            },
        });
    }
    /**
     * Retrieves a taxonomy vocabulary.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public getTaxonomyVocabulary({
        taxonomyVocabularyId,
        fields,
        restrictFields,
    }: {
        taxonomyVocabularyId: number,
        fields?: string,
        restrictFields?: string,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
            path: {
                'taxonomyVocabularyId': taxonomyVocabularyId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Updates only the fields received in the request body. Any other fields are left untouched.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public patchTaxonomyVocabulary({
        taxonomyVocabularyId,
        requestBody,
    }: {
        taxonomyVocabularyId: number,
        requestBody?: TaxonomyVocabulary,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
            path: {
                'taxonomyVocabularyId': taxonomyVocabularyId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the taxonomy vocabulary with the information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns TaxonomyVocabulary
     * @throws ApiError
     */
    public putTaxonomyVocabulary({
        taxonomyVocabularyId,
        requestBody,
    }: {
        taxonomyVocabularyId: number,
        requestBody?: TaxonomyVocabulary,
    }): CancelablePromise<TaxonomyVocabulary> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}',
            path: {
                'taxonomyVocabularyId': taxonomyVocabularyId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public getTaxonomyVocabularyPermissionsPage({
        taxonomyVocabularyId,
        fields,
        restrictFields,
        roleNames,
    }: {
        taxonomyVocabularyId: number,
        fields?: string,
        restrictFields?: string,
        roleNames?: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/permissions',
            path: {
                'taxonomyVocabularyId': taxonomyVocabularyId,
            },
            query: {
                'fields': fields,
                'restrictFields': restrictFields,
                'roleNames': roleNames,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public putTaxonomyVocabularyPermissionsPage({
        taxonomyVocabularyId,
        requestBody,
    }: {
        taxonomyVocabularyId: number,
        requestBody?: any,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/permissions',
            path: {
                'taxonomyVocabularyId': taxonomyVocabularyId,
            },
            body: requestBody,
        });
    }
}
