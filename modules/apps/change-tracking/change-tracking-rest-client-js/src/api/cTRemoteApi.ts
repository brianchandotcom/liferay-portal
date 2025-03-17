/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../model/models';

		import {CTRemote} from '../model/cTRemote';
		import {PageCTRemote} from '../model/pageCTRemote';

import {HttpError} from './apis';

/**
 * @author David Truong
 * @generated
 */

export class CTRemoteApi {
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
				 * @param id
		 * @param headers Optional custom request headers
		 */
		public async deleteCTRemote(
						id: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace('{id}',encodeURIComponent(id))
				;

			const queryParameters: any = {};

						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling deleteCTRemote.');
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
					} else {
						return {body: await response.text(), response};
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
				 * @param id
		 * @param headers Optional custom request headers
		 */
		public async getCTRemote(
						id: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTRemote;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace('{id}',encodeURIComponent(id))
				;

			const queryParameters: any = {};

						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling getCTRemote.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CTRemote"), response};
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
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param sort
		 * @param headers Optional custom request headers
		 */
		public async getCTRemotesPage(
						page?: number,
						pageSize?: number,
						search?: string,
						sort?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageCTRemote;
			response: Response;
		}> {

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-remotes'
																;

			const queryParameters: any = {};

						if (page !== undefined) {
							queryParameters['page'] = JSON.stringify(ObjectSerializer.serialize(page, "number"));
						}

						if (pageSize !== undefined) {
							queryParameters['pageSize'] = JSON.stringify(ObjectSerializer.serialize(pageSize, "number"));
						}

						if (search !== undefined) {
							queryParameters['search'] = JSON.stringify(ObjectSerializer.serialize(search, "string"));
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageCTRemote"), response};
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
				 * @param id
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchCTRemoteWithContentType(
						id: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTRemote?: CTRemote
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTRemote?: CTRemote
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTRemote;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTRemote, "CTRemote"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTRemote, "CTRemote"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace('{id}',encodeURIComponent(id))
				;

			const queryParameters: any = {};

						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling patchCTRemote.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CTRemote"), response};
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
					 *  - Default method for JSON body
							 * @param id
						 * @param cTRemote
					 */
					public async patchCTRemote(
									id: number,
							cTRemote?: CTRemote,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTRemote;
						response: Response;
					}> {
						return this.patchCTRemoteWithContentType(
										id,
							{
								type: 'application/json',
								parameters: {
										cTRemote: cTRemote
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
		public async postCTRemoteWithContentType(
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTRemote?: CTRemote
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTRemote?: CTRemote
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTRemote;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTRemote, "CTRemote"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTRemote, "CTRemote"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-remotes'
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CTRemote"), response};
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
					 *  - Default method for JSON body
						 * @param cTRemote
					 */
					public async postCTRemote(
							cTRemote?: CTRemote,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTRemote;
						response: Response;
					}> {
						return this.postCTRemoteWithContentType(
							{
								type: 'application/json',
								parameters: {
										cTRemote: cTRemote
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param id
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putCTRemoteWithContentType(
						id: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										cTRemote?: CTRemote
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										cTRemote?: CTRemote
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CTRemote;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTRemote, "CTRemote"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.cTRemote, "CTRemote"));
						}

			const path = this._basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace('{id}',encodeURIComponent(id))
				;

			const queryParameters: any = {};

						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling putCTRemote.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CTRemote"), response};
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
					 *  - Default method for JSON body
							 * @param id
						 * @param cTRemote
					 */
					public async putCTRemote(
									id: number,
							cTRemote?: CTRemote,
						headers?: {[name: string]: string}
					): Promise<{
							body: CTRemote;
						response: Response;
					}> {
						return this.putCTRemoteWithContentType(
										id,
							{
								type: 'application/json',
								parameters: {
										cTRemote: cTRemote
								}
							},
							headers
						);
					}
}