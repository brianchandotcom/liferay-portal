/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
import { Authentication, Interceptor, ObjectSerializer, VoidAuth } from '../model/models';

		import { CTEntry } from '../model/cTEntry';
		import { PageCTEntry } from '../model/pageCTEntry';

import { HttpError } from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author David Truong
 * @generated
 */

export enum CTEntryApiApiKeys {
}

export class CTEntryApi {
	protected _basePath = defaultBasePath;
	protected _defaultHeaders : any = {};
	protected _useQuerystring : boolean = false;

	protected authentications = {
		'default': <Authentication>new VoidAuth(),
	}

	protected interceptors: Interceptor[] = [];

	constructor(basePath?: string);
	constructor(basePathOrUsername: string, password?: string, basePath?: string) {
		if (password) {
			if (basePath) {
				this.basePath = basePath;
			}
		} else {
			if (basePathOrUsername) {
				this.basePath = basePathOrUsername
			}
		}
	}

	set useQuerystring(value: boolean) {
		this._useQuerystring = value;
	}

	set basePath(basePath: string) {
		this._basePath = basePath;
	}

	set defaultHeaders(defaultHeaders: any) {
		this._defaultHeaders = defaultHeaders;
	}

	get defaultHeaders() {
		return this._defaultHeaders;
	}

	get basePath() {
		return this._basePath;
	}

	public setDefaultAuthentication(auth: Authentication) {
		this.authentications.default = auth;
	}

	public setApiKey(key: CTEntryApiApiKeys, value: string) {
		(this.authentications as any)[CTEntryApiApiKeys[key]].apiKey = value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
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
		 */
		public async getCtCollectionCTEntriesPage (
					ctCollectionId: number,
					filter?: string,
					page?: number,
					pageSize?: number,
					search?: string,
					showHideable?: boolean,
					sort?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageCTEntry;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/ct-entries'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				// give precedence to 'application/json'
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						// verify required parameter 'ctCollectionId' is not null or undefined
						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCtCollectionCTEntriesPage.');
						}
					if (filter !== undefined) {
						localVarQueryParameters['filter'] = ObjectSerializer.serialize(filter, "string");
					}
					if (page !== undefined) {
						localVarQueryParameters['page'] = ObjectSerializer.serialize(page, "number");
					}
					if (pageSize !== undefined) {
						localVarQueryParameters['pageSize'] = ObjectSerializer.serialize(pageSize, "number");
					}
					if (search !== undefined) {
						localVarQueryParameters['search'] = ObjectSerializer.serialize(search, "string");
					}
					if (showHideable !== undefined) {
						localVarQueryParameters['showHideable'] = ObjectSerializer.serialize(showHideable, "boolean");
					}
					if (sort !== undefined) {
						localVarQueryParameters['sort'] = ObjectSerializer.serialize(sort, "string");
					}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
				headers: localVarHeaderParams,
				json: true,
				method: 'GET',
				qs: localVarQueryParameters,
				uri: localVarPath,
				useQuerystring: this._useQuerystring
			};

			let authenticationPromise = Promise.resolve();
			authenticationPromise = authenticationPromise.then(() => this.authentications.default.applyToRequest(localVarRequestOptions));

			let interceptorPromise = authenticationPromise;
			for (const interceptor of this.interceptors) {
				interceptorPromise = interceptorPromise.then(() => interceptor(localVarRequestOptions));
			}

			return interceptorPromise.then(() => {
				if (Object.keys(localVarFormParams).length) {
					if (localVarUseFormData) {
						(<any>localVarRequestOptions).formData = localVarFormParams;
					} else {
						localVarRequestOptions.form = localVarFormParams;
					}
				}
				return new Promise<{  body: PageCTEntry; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageCTEntry");
								resolve({ body: body, response: response });
							} else {
								reject(new HttpError(body, response, response.statusCode));
							}
						}
					});
				});
			});
		}
		/**
		 * 
				 * @param ctCollectionId 
				 * @param modelClassNameId 
				 * @param modelClassPK 
		 */
		public async getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK (
					ctCollectionId: number,
					modelClassNameId: number,
					modelClassPK: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: CTEntry;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/ct-entries/by-model-class-name-id/{modelClassNameId}/by-model-class-pk/{modelClassPK}'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
						.replace(
							'{' + 'modelClassNameId' + '}',
							encodeURIComponent(String(modelClassNameId))
						)
						.replace(
							'{' + 'modelClassPK' + '}',
							encodeURIComponent(String(modelClassPK))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				// give precedence to 'application/json'
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						// verify required parameter 'ctCollectionId' is not null or undefined
						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK.');
						}
						// verify required parameter 'modelClassNameId' is not null or undefined
						if (modelClassNameId === null || modelClassNameId === undefined) {
							throw new Error('Required parameter modelClassNameId was null or undefined when calling getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK.');
						}
						// verify required parameter 'modelClassPK' is not null or undefined
						if (modelClassPK === null || modelClassPK === undefined) {
							throw new Error('Required parameter modelClassPK was null or undefined when calling getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
				headers: localVarHeaderParams,
				json: true,
				method: 'GET',
				qs: localVarQueryParameters,
				uri: localVarPath,
				useQuerystring: this._useQuerystring
			};

			let authenticationPromise = Promise.resolve();
			authenticationPromise = authenticationPromise.then(() => this.authentications.default.applyToRequest(localVarRequestOptions));

			let interceptorPromise = authenticationPromise;
			for (const interceptor of this.interceptors) {
				interceptorPromise = interceptorPromise.then(() => interceptor(localVarRequestOptions));
			}

			return interceptorPromise.then(() => {
				if (Object.keys(localVarFormParams).length) {
					if (localVarUseFormData) {
						(<any>localVarRequestOptions).formData = localVarFormParams;
					} else {
						localVarRequestOptions.form = localVarFormParams;
					}
				}
				return new Promise<{  body: CTEntry; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "CTEntry");
								resolve({ body: body, response: response });
							} else {
								reject(new HttpError(body, response, response.statusCode));
							}
						}
					});
				});
			});
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
		 */
		public async getCTEntriesHistoryPage (
					classNameId: number,
					classPK?: number,
					filter?: string,
					page?: number,
					pageSize?: number,
					search?: string,
					siteId?: number,
					sort?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageCTEntry;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-entries/history'
;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				// give precedence to 'application/json'
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						// verify required parameter 'classNameId' is not null or undefined
						if (classNameId === null || classNameId === undefined) {
							throw new Error('Required parameter classNameId was null or undefined when calling getCTEntriesHistoryPage.');
						}
					if (classNameId !== undefined) {
						localVarQueryParameters['classNameId'] = ObjectSerializer.serialize(classNameId, "number");
					}
					if (classPK !== undefined) {
						localVarQueryParameters['classPK'] = ObjectSerializer.serialize(classPK, "number");
					}
					if (filter !== undefined) {
						localVarQueryParameters['filter'] = ObjectSerializer.serialize(filter, "string");
					}
					if (page !== undefined) {
						localVarQueryParameters['page'] = ObjectSerializer.serialize(page, "number");
					}
					if (pageSize !== undefined) {
						localVarQueryParameters['pageSize'] = ObjectSerializer.serialize(pageSize, "number");
					}
					if (search !== undefined) {
						localVarQueryParameters['search'] = ObjectSerializer.serialize(search, "string");
					}
					if (siteId !== undefined) {
						localVarQueryParameters['siteId'] = ObjectSerializer.serialize(siteId, "number");
					}
					if (sort !== undefined) {
						localVarQueryParameters['sort'] = ObjectSerializer.serialize(sort, "string");
					}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
				headers: localVarHeaderParams,
				json: true,
				method: 'GET',
				qs: localVarQueryParameters,
				uri: localVarPath,
				useQuerystring: this._useQuerystring
			};

			let authenticationPromise = Promise.resolve();
			authenticationPromise = authenticationPromise.then(() => this.authentications.default.applyToRequest(localVarRequestOptions));

			let interceptorPromise = authenticationPromise;
			for (const interceptor of this.interceptors) {
				interceptorPromise = interceptorPromise.then(() => interceptor(localVarRequestOptions));
			}

			return interceptorPromise.then(() => {
				if (Object.keys(localVarFormParams).length) {
					if (localVarUseFormData) {
						(<any>localVarRequestOptions).formData = localVarFormParams;
					} else {
						localVarRequestOptions.form = localVarFormParams;
					}
				}
				return new Promise<{  body: PageCTEntry; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageCTEntry");
								resolve({ body: body, response: response });
							} else {
								reject(new HttpError(body, response, response.statusCode));
							}
						}
					});
				});
			});
		}
		/**
		 * 
				 * @param ctEntryId 
		 */
		public async getCTEntry (
					ctEntryId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: CTEntry;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-entries/{ctEntryId}'
						.replace(
							'{' + 'ctEntryId' + '}',
							encodeURIComponent(String(ctEntryId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				// give precedence to 'application/json'
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						// verify required parameter 'ctEntryId' is not null or undefined
						if (ctEntryId === null || ctEntryId === undefined) {
							throw new Error('Required parameter ctEntryId was null or undefined when calling getCTEntry.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
				headers: localVarHeaderParams,
				json: true,
				method: 'GET',
				qs: localVarQueryParameters,
				uri: localVarPath,
				useQuerystring: this._useQuerystring
			};

			let authenticationPromise = Promise.resolve();
			authenticationPromise = authenticationPromise.then(() => this.authentications.default.applyToRequest(localVarRequestOptions));

			let interceptorPromise = authenticationPromise;
			for (const interceptor of this.interceptors) {
				interceptorPromise = interceptorPromise.then(() => interceptor(localVarRequestOptions));
			}

			return interceptorPromise.then(() => {
				if (Object.keys(localVarFormParams).length) {
					if (localVarUseFormData) {
						(<any>localVarRequestOptions).formData = localVarFormParams;
					} else {
						localVarRequestOptions.form = localVarFormParams;
					}
				}
				return new Promise<{  body: CTEntry; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "CTEntry");
								resolve({ body: body, response: response });
							} else {
								reject(new HttpError(body, response, response.statusCode));
							}
						}
					});
				});
			});
		}
}