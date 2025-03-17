/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../model/models';

		import {ObjectAction} from '../model/objectAction';
		import {PageObjectAction} from '../model/pageObjectAction';

import {HttpError} from './apis';

/**
 * @author Javier Gamarra
 * @generated
 */

export class ObjectActionApi {
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
				 * @param objectActionId
		 * @param headers Optional custom request headers
		 */
		public async deleteObjectAction(
						objectActionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/object-admin/v1.0/object-actions/{objectActionId}'
						.replace('{objectActionId}',encodeURIComponent(objectActionId))
				;

			const queryParameters: any = {};

						if (objectActionId === null || objectActionId === undefined) {
							throw new Error('Required parameter objectActionId was null or undefined when calling deleteObjectAction.');
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
				 * @param objectActionId
		 * @param headers Optional custom request headers
		 */
		public async getObjectAction(
						objectActionId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: ObjectAction;
			response: Response;
		}> {

			const path = this._basePath + '/object-admin/v1.0/object-actions/{objectActionId}'
						.replace('{objectActionId}',encodeURIComponent(objectActionId))
				;

			const queryParameters: any = {};

						if (objectActionId === null || objectActionId === undefined) {
							throw new Error('Required parameter objectActionId was null or undefined when calling getObjectAction.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ObjectAction"), response};
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
				 * @param externalReferenceCode
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param sort
		 * @param headers Optional custom request headers
		 */
		public async getObjectDefinitionByExternalReferenceCodeObjectActionsPage(
						externalReferenceCode: string,
						page?: number,
						pageSize?: number,
						search?: string,
						sort?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageObjectAction;
			response: Response;
		}> {

			const path = this._basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-actions'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
																				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getObjectDefinitionByExternalReferenceCodeObjectActionsPage.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageObjectAction"), response};
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
				 * @param objectDefinitionId
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param sort
		 * @param headers Optional custom request headers
		 */
		public async getObjectDefinitionObjectActionsPage(
						objectDefinitionId: number,
						page?: number,
						pageSize?: number,
						search?: string,
						sort?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageObjectAction;
			response: Response;
		}> {

			const path = this._basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions'
						.replace('{objectDefinitionId}',encodeURIComponent(objectDefinitionId))
																				;

			const queryParameters: any = {};

						if (objectDefinitionId === null || objectDefinitionId === undefined) {
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling getObjectDefinitionObjectActionsPage.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageObjectAction"), response};
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
				 * @param objectActionId
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchObjectActionWithContentType(
						objectActionId: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ObjectAction;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}

			const path = this._basePath + '/object-admin/v1.0/object-actions/{objectActionId}'
						.replace('{objectActionId}',encodeURIComponent(objectActionId))
				;

			const queryParameters: any = {};

						if (objectActionId === null || objectActionId === undefined) {
							throw new Error('Required parameter objectActionId was null or undefined when calling patchObjectAction.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ObjectAction"), response};
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
							 * @param objectActionId
						 * @param objectAction
					 */
					public async patchObjectAction(
									objectActionId: number,
							objectAction?: ObjectAction,
						headers?: {[name: string]: string}
					): Promise<{
							body: ObjectAction;
						response: Response;
					}> {
						return this.patchObjectActionWithContentType(
										objectActionId,
							{
								type: 'application/json',
								parameters: {
										objectAction: objectAction
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
		public async postObjectDefinitionByExternalReferenceCodeObjectActionWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ObjectAction;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}

			const path = this._basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-actions'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postObjectDefinitionByExternalReferenceCodeObjectAction.');
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
								,{'Content-Type': requestBody.type}
					,headers || {}
					)
					,body: body
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ObjectAction"), response};
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
							 * @param externalReferenceCode
						 * @param objectAction
					 */
					public async postObjectDefinitionByExternalReferenceCodeObjectAction(
									externalReferenceCode: string,
							objectAction?: ObjectAction,
						headers?: {[name: string]: string}
					): Promise<{
							body: ObjectAction;
						response: Response;
					}> {
						return this.postObjectDefinitionByExternalReferenceCodeObjectActionWithContentType(
										externalReferenceCode,
							{
								type: 'application/json',
								parameters: {
										objectAction: objectAction
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param objectDefinitionId
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postObjectDefinitionObjectActionWithContentType(
						objectDefinitionId: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ObjectAction;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}

			const path = this._basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-actions'
						.replace('{objectDefinitionId}',encodeURIComponent(objectDefinitionId))
				;

			const queryParameters: any = {};

						if (objectDefinitionId === null || objectDefinitionId === undefined) {
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling postObjectDefinitionObjectAction.');
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
								,{'Content-Type': requestBody.type}
					,headers || {}
					)
					,body: body
			});

			if (response.ok) {
				const contentType = response.headers.get('content-type') || '';

					if (contentType.includes('application/json')) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ObjectAction"), response};
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
							 * @param objectDefinitionId
						 * @param objectAction
					 */
					public async postObjectDefinitionObjectAction(
									objectDefinitionId: number,
							objectAction?: ObjectAction,
						headers?: {[name: string]: string}
					): Promise<{
							body: ObjectAction;
						response: Response;
					}> {
						return this.postObjectDefinitionObjectActionWithContentType(
										objectDefinitionId,
							{
								type: 'application/json',
								parameters: {
										objectAction: objectAction
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param objectActionId
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putObjectActionWithContentType(
						objectActionId: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										objectAction?: ObjectAction
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ObjectAction;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.objectAction, "ObjectAction"));
						}

			const path = this._basePath + '/object-admin/v1.0/object-actions/{objectActionId}'
						.replace('{objectActionId}',encodeURIComponent(objectActionId))
				;

			const queryParameters: any = {};

						if (objectActionId === null || objectActionId === undefined) {
							throw new Error('Required parameter objectActionId was null or undefined when calling putObjectAction.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ObjectAction"), response};
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
							 * @param objectActionId
						 * @param objectAction
					 */
					public async putObjectAction(
									objectActionId: number,
							objectAction?: ObjectAction,
						headers?: {[name: string]: string}
					): Promise<{
							body: ObjectAction;
						response: Response;
					}> {
						return this.putObjectActionWithContentType(
										objectActionId,
							{
								type: 'application/json',
								parameters: {
										objectAction: objectAction
								}
							},
							headers
						);
					}
}