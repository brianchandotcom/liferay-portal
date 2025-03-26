/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../index';

		import {CompanyTestEntity} from '../models/CompanyTestEntity';
		import {PageCompanyTestEntity} from '../models/PageCompanyTestEntity';

import {HttpError} from '../index';

/**
 * @author Alejandro Tardín
 * @generated
 */

export class CompanyTestEntityAPI {
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
		 * @param headers Optional custom request headers
		 */
		public async getCompanyTestEntitiesPage(
			headers?: {[name: string]: string},
		): Promise<{
				body: PageCompanyTestEntity;
			response: Response;
		}> {

			const path = this._basePath + '/test/v1.0/company-test-entities'
;

			const queryParameters: any = {};

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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageCompanyTestEntity"), response};
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
				 * @param companyTestEntityId
		 * @param headers Optional custom request headers
		 */
		public async getCompanyTestEntity(
						companyTestEntityId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: CompanyTestEntity;
			response: Response;
		}> {

			const path = this._basePath + '/test/v1.0/company-test-entities/{companyTestEntityId}'
						.replace('{companyTestEntityId}',encodeURIComponent(companyTestEntityId))
				;

			const queryParameters: any = {};

						if (companyTestEntityId === null || companyTestEntityId === undefined) {
							throw new Error('Required parameter companyTestEntityId was null or undefined when calling getCompanyTestEntity.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CompanyTestEntity"), response};
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
		public async getCompanyTestEntityByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: CompanyTestEntity;
			response: Response;
		}> {

			const path = this._basePath + '/test/v1.0/company-test-entities/by-external-reference-code/{externalReferenceCode}'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getCompanyTestEntityByExternalReferenceCode.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CompanyTestEntity"), response};
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
				 * @param companyTestEntityId
				 * @param roleNames
		 * @param headers Optional custom request headers
		 */
		public async getCompanyTestEntityPermissionsPage(
						companyTestEntityId: number,
						roleNames?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/test/v1.0/company-test-entities/{companyTestEntityId}/permissions'
						.replace('{companyTestEntityId}',encodeURIComponent(companyTestEntityId))
								;

			const queryParameters: any = {};

						if (companyTestEntityId === null || companyTestEntityId === undefined) {
							throw new Error('Required parameter companyTestEntityId was null or undefined when calling getCompanyTestEntityPermissionsPage.');
						}

						if (roleNames !== undefined) {
							queryParameters['roleNames'] = JSON.stringify(ObjectSerializer.serialize(roleNames, "string"));
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
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postCompanyTestEntityWithContentType(
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										companyTestEntity?: CompanyTestEntity
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										companyTestEntity?: CompanyTestEntity
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CompanyTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.companyTestEntity, "CompanyTestEntity"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.companyTestEntity, "CompanyTestEntity"));
						}

			const path = this._basePath + '/test/v1.0/company-test-entities'
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CompanyTestEntity"), response};
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
						 * @param companyTestEntity
					 */
					public async postCompanyTestEntity(
							companyTestEntity?: CompanyTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: CompanyTestEntity;
						response: Response;
					}> {
						return this.postCompanyTestEntityWithContentType(
							{
								type: 'application/json',
								parameters: {
										companyTestEntity: companyTestEntity
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param companyTestEntityId
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putCompanyTestEntityWithContentType(
						companyTestEntityId: number,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										companyTestEntity?: CompanyTestEntity
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										companyTestEntity?: CompanyTestEntity
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CompanyTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.companyTestEntity, "CompanyTestEntity"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.companyTestEntity, "CompanyTestEntity"));
						}

			const path = this._basePath + '/test/v1.0/company-test-entities/{companyTestEntityId}'
						.replace('{companyTestEntityId}',encodeURIComponent(companyTestEntityId))
				;

			const queryParameters: any = {};

						if (companyTestEntityId === null || companyTestEntityId === undefined) {
							throw new Error('Required parameter companyTestEntityId was null or undefined when calling putCompanyTestEntity.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CompanyTestEntity"), response};
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
							 * @param companyTestEntityId
						 * @param companyTestEntity
					 */
					public async putCompanyTestEntity(
									companyTestEntityId: number,
							companyTestEntity?: CompanyTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: CompanyTestEntity;
						response: Response;
					}> {
						return this.putCompanyTestEntityWithContentType(
										companyTestEntityId,
							{
								type: 'application/json',
								parameters: {
										companyTestEntity: companyTestEntity
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
		public async putCompanyTestEntityByExternalReferenceCodeWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								type: 'application/xml',
								parameters: {
										companyTestEntity?: CompanyTestEntity
								}
							}
								|
							{
								type: 'application/json',
								parameters: {
										companyTestEntity?: CompanyTestEntity
								}
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: CompanyTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === 'application/xml') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.companyTestEntity, "CompanyTestEntity"));
						}
						if (requestBody.type === 'application/json') {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.companyTestEntity, "CompanyTestEntity"));
						}

			const path = this._basePath + '/test/v1.0/company-test-entities/by-external-reference-code/{externalReferenceCode}'
						.replace('{externalReferenceCode}',encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling putCompanyTestEntityByExternalReferenceCode.');
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
						return {body: ObjectSerializer.deserialize(await response.json(), "CompanyTestEntity"), response};
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
						 * @param companyTestEntity
					 */
					public async putCompanyTestEntityByExternalReferenceCode(
									externalReferenceCode: string,
							companyTestEntity?: CompanyTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: CompanyTestEntity;
						response: Response;
					}> {
						return this.putCompanyTestEntityByExternalReferenceCodeWithContentType(
										externalReferenceCode,
							{
								type: 'application/json',
								parameters: {
										companyTestEntity: companyTestEntity
								}
							},
							headers
						);
					}
		/**
		 * 
				 * @param companyTestEntityId
		 * @param headers Optional custom request headers
		 */
		public async putCompanyTestEntityPermissionsPage(
						companyTestEntityId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + '/test/v1.0/company-test-entities/{companyTestEntityId}/permissions'
						.replace('{companyTestEntityId}',encodeURIComponent(companyTestEntityId))
				;

			const queryParameters: any = {};

						if (companyTestEntityId === null || companyTestEntityId === undefined) {
							throw new Error('Required parameter companyTestEntityId was null or undefined when calling putCompanyTestEntityPermissionsPage.');
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

}