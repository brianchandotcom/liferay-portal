/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../model/models';

		import {CTEntry} from '../model/cTEntry';
		import {PageCTEntry} from '../model/pageCTEntry';

import {HttpError} from './apis';

/**
 * @author David Truong
 * @generated
 */

export class CTEntryApi {
	protected _basePath: string;
	protected _defaultHeaders: any = {};

	constructor(basePath?: string) {
		if (basePath) {
			this._basePath = basePath;
		}
	}

	set defaultHeaders(defaultHeaders: any) {
		this._defaultHeaders = defaultHeaders;
	}

		/**
		 * 
				 * @param ctCollectionId
				 * @param filter
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param showHideable
				 * @param sort
		 * @param headers Optional custom request headers
		 */
		public async getCtCollectionCTEntriesPage(
						ctCollectionId: number,
						filter?: string,
						page?: number,
						pageSize?: number,
						search?: string,
						showHideable?: boolean,
						sort?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageCTEntry;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/ct-entries'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
																												;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCtCollectionCTEntriesPage.');
						}

						if (filter !== undefined) {
							queryParameters['filter'] = JSON.stringify(ObjectSerializer.serialize(filter, "string"));
						}

						if (page !== undefined) {
							queryParameters['page'] = JSON.stringify(ObjectSerializer.serialize(page, "number"));
						}

						if (pageSize !== undefined) {
							queryParameters['pageSize'] = JSON.stringify(ObjectSerializer.serialize(pageSize, "number"));
						}

						if (search !== undefined) {
							queryParameters['search'] = JSON.stringify(ObjectSerializer.serialize(search, "string"));
						}

						if (showHideable !== undefined) {
							queryParameters['showHideable'] = JSON.stringify(ObjectSerializer.serialize(showHideable, "boolean"));
						}

						if (sort !== undefined) {
							queryParameters['sort'] = JSON.stringify(ObjectSerializer.serialize(sort, "string"));
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'GET',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
					,headers || {}
					)
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "PageCTEntry"), response};
					} else {
						return {body: await response.text() as any, response};
					}
			} else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param ctCollectionId
				 * @param modelClassNameId
				 * @param modelClassPK
		 * @param headers Optional custom request headers
		 */
		public async getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK(
						ctCollectionId: number,
						modelClassNameId: number,
						modelClassPK: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTEntry;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/ct-entries/by-model-class-name-id/{modelClassNameId}/by-model-class-pk/{modelClassPK}'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
										.replace('{modelClassNameId}',encodeURIComponent(modelClassNameId))
										.replace('{modelClassPK}',encodeURIComponent(modelClassPK))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK.');
						}

						if (modelClassNameId === null || modelClassNameId === undefined) {
							throw new Error('Required parameter modelClassNameId was null or undefined when calling getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK.');
						}

						if (modelClassPK === null || modelClassPK === undefined) {
							throw new Error('Required parameter modelClassPK was null or undefined when calling getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'GET',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
					,headers || {}
					)
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "CTEntry"), response};
					} else {
						return {body: await response.text() as any, response};
					}
			} else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param classNameId
				 * @param classPK
				 * @param filter
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param siteId
				 * @param sort
		 * @param headers Optional custom request headers
		 */
		public async getCTEntriesHistoryPage(
						classNameId: number,
						classPK?: number,
						filter?: string,
						page?: number,
						pageSize?: number,
						search?: string,
						siteId?: number,
						sort?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageCTEntry;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-entries/history'
																																;

			const queryParameters: any = {};

						if (classNameId === null || classNameId === undefined) {
							throw new Error('Required parameter classNameId was null or undefined when calling getCTEntriesHistoryPage.');
						}

						if (classNameId !== undefined) {
							queryParameters['classNameId'] = JSON.stringify(ObjectSerializer.serialize(classNameId, "number"));
						}

						if (classPK !== undefined) {
							queryParameters['classPK'] = JSON.stringify(ObjectSerializer.serialize(classPK, "number"));
						}

						if (filter !== undefined) {
							queryParameters['filter'] = JSON.stringify(ObjectSerializer.serialize(filter, "string"));
						}

						if (page !== undefined) {
							queryParameters['page'] = JSON.stringify(ObjectSerializer.serialize(page, "number"));
						}

						if (pageSize !== undefined) {
							queryParameters['pageSize'] = JSON.stringify(ObjectSerializer.serialize(pageSize, "number"));
						}

						if (search !== undefined) {
							queryParameters['search'] = JSON.stringify(ObjectSerializer.serialize(search, "string"));
						}

						if (siteId !== undefined) {
							queryParameters['siteId'] = JSON.stringify(ObjectSerializer.serialize(siteId, "number"));
						}

						if (sort !== undefined) {
							queryParameters['sort'] = JSON.stringify(ObjectSerializer.serialize(sort, "string"));
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'GET',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
					,headers || {}
					)
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "PageCTEntry"), response};
					} else {
						return {body: await response.text() as any, response};
					}
			} else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param ctEntryId
		 * @param headers Optional custom request headers
		 */
		public async getCTEntry(
						ctEntryId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTEntry;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-entries/{ctEntryId}'
						.replace('{ctEntryId}',encodeURIComponent(ctEntryId))
				;

			const queryParameters: any = {};

						if (ctEntryId === null || ctEntryId === undefined) {
							throw new Error('Required parameter ctEntryId was null or undefined when calling getCTEntry.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'GET',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
					,headers || {}
					)
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "CTEntry"), response};
					} else {
						return {body: await response.text() as any, response};
					}
			} else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

}