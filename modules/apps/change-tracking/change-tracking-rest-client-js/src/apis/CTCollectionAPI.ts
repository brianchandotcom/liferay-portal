/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../index';

		import {CTCollection} from '../models/CTCollection';
		import {PageCTCollection} from '../models/PageCTCollection';

import {HttpError} from '../index';

/**
 * @author David Truong
 * @generated
 */

export class CTCollectionAPI {
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
		 * @param headers Optional custom request headers
		 */
		public async deleteCTCollection(
						ctCollectionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling deleteCTCollection.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'DELETE',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async deleteCTCollectionByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling deleteCTCollectionByExternalReferenceCode.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'DELETE',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
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
		 * @param headers Optional custom request headers
		 */
		public async getCTCollection(
						ctCollectionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTCollection;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCTCollection.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getCTCollectionByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTCollection;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getCTCollectionByExternalReferenceCode.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getCTCollectionByExternalReferenceCodeShareLink(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: string;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/share-link'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getCTCollectionByExternalReferenceCodeShareLink.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'GET',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'text/plain'
						}
					,headers || {}
					)
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "string"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
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
		 * @param headers Optional custom request headers
		 */
		public async getCTCollectionShareLink(
						ctCollectionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: string;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/b{ctCollectionId}/share-link'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCTCollectionShareLink.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'GET',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'text/plain'
						}
					,headers || {}
					)
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "string"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param sort
				 * @param status
		 * @param headers Optional custom request headers
		 */
		public async getCTCollectionsPage(
						page?: number,
						pageSize?: number,
						search?: string,
						sort?: string,
						status?: Array<number>,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageCTCollection;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections'
																				;

			const queryParameters: any = {};

						if (page !== undefined) {
							queryParameters['page'] = ObjectSerializer.serialize(page, "number");
						}

						if (pageSize !== undefined) {
							queryParameters['pageSize'] = ObjectSerializer.serialize(pageSize, "number");
						}

						if (search !== undefined) {
							queryParameters['search'] = ObjectSerializer.serialize(search, "string");
						}

						if (sort !== undefined) {
							queryParameters['sort'] = ObjectSerializer.serialize(sort, "string");
						}

						if (status !== undefined) {
							queryParameters['status'] = ObjectSerializer.serialize(status, "Array<number>");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageCTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
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
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchCTCollectionWithContentType(
						ctCollectionId: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTCollection;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling patchCTCollection.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'PATCH',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
								,{'Content-Type': requestBody.type}
					,headers || {}
					)
					,body: body
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "CTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param ctCollectionId
						 * @param cTCollection
					 */
					public async patchCTCollection(
									ctCollectionId: number,
							cTCollection?: CTCollection,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTCollection;
						response: Response;
					}> {
						return this.patchCTCollectionWithContentType(
										ctCollectionId,
							{
								type: 'application/json',
								parameters: {
										cTCollection: cTCollection
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchCTCollectionByExternalReferenceCodeWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTCollection;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling patchCTCollectionByExternalReferenceCode.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'PATCH',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
								,{'Content-Type': requestBody.type}
					,headers || {}
					)
					,body: body
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "CTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param externalReferenceCode
						 * @param cTCollection
					 */
					public async patchCTCollectionByExternalReferenceCode(
									externalReferenceCode: string,
							cTCollection?: CTCollection,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTCollection;
						response: Response;
					}> {
						return this.patchCTCollectionByExternalReferenceCodeWithContentType(
										externalReferenceCode,
							{
								type: 'application/json',
								parameters: {
										cTCollection: cTCollection
								}
							},
							headers
						);
					}
		/**
		 * 
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postCTCollectionWithContentType(
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTCollection;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections'
;

			const queryParameters: any = {};

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'POST',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
								,{'Content-Type': requestBody.type}
					,headers || {}
					)
					,body: body
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "CTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

					/**
					 *  - Default method for JSON body
						 * @param cTCollection
					 */
					public async postCTCollection(
							cTCollection?: CTCollection,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTCollection;
						response: Response;
					}> {
						return this.postCTCollectionWithContentType(
							{
								type: 'application/json',
								parameters: {
										cTCollection: cTCollection
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async postCTCollectionByExternalReferenceCodePublish(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/publish'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postCTCollectionByExternalReferenceCodePublish.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'POST',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
				 * @param publishDate
		 * @param headers Optional custom request headers
		 */
		public async postCTCollectionByExternalReferenceCodeSchedulePublish(
						externalReferenceCode: string,
						publishDate?: Date,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/schedule-publish'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
								;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postCTCollectionByExternalReferenceCodeSchedulePublish.');
						}

						if (publishDate !== undefined) {
							queryParameters['publishDate'] = ObjectSerializer.serialize(publishDate, "Date");
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'POST',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
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
		 * @param headers Optional custom request headers
		 */
		public async postCTCollectionCheckout(
						ctCollectionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/checkout'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling postCTCollectionCheckout.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'POST',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
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
		 * @param headers Optional custom request headers
		 */
		public async postCTCollectionPublish(
						ctCollectionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/publish'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling postCTCollectionPublish.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'POST',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
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
				 * @param publishDate
		 * @param headers Optional custom request headers
		 */
		public async postCTCollectionSchedulePublish(
						ctCollectionId: number,
						publishDate?: Date,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/schedule-publish'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
								;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling postCTCollectionSchedulePublish.');
						}

						if (publishDate !== undefined) {
							queryParameters['publishDate'] = ObjectSerializer.serialize(publishDate, "Date");
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'POST',
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
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
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
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putCTCollectionWithContentType(
						ctCollectionId: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTCollection?: CTCollection
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTCollection;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTCollection, "CTCollection"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace('{ctCollectionId}',encodeURIComponent(ctCollectionId))
				;

			const queryParameters: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling putCTCollection.');
						}

			const queryString = Object.keys(queryParameters).length
				? '?' + new URLSearchParams(queryParameters).toString()
				: '';

			const response = await fetch(path + queryString, {
				method: 'PUT',
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: 'application/json'
						}
								,{'Content-Type': requestBody.type}
					,headers || {}
					)
					,body: body
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "CTCollection"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new HttpError(
					await response.text(),
					response,
					response.status
				);
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param ctCollectionId
						 * @param cTCollection
					 */
					public async putCTCollection(
									ctCollectionId: number,
							cTCollection?: CTCollection,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTCollection;
						response: Response;
					}> {
						return this.putCTCollectionWithContentType(
										ctCollectionId,
							{
								type: 'application/json',
								parameters: {
										cTCollection: cTCollection
								}
							},
							headers
						);
					}
}