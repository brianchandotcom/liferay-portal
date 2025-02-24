/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
import { Authentication, Interceptor, ObjectSerializer, VoidAuth } from '../model/models';

		import { CTRemote } from '../model/cTRemote';
		import { PageCTRemote } from '../model/pageCTRemote';

import { HttpError } from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author David Truong
 * @generated
 */

export enum CTRemoteApiApiKeys {
}

export class CTRemoteApi {
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

	public setApiKey(key: CTRemoteApiApiKeys, value: string) {
		(this.authentications as any)[CTRemoteApiApiKeys[key]].apiKey = value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
	}

		/**
		 * 
				 * @param id 
		 */
		public async deleteCTRemote (
					id: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace(
							'{' + 'id' + '}',
							encodeURIComponent(String(id))
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

						// verify required parameter 'id' is not null or undefined
						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling deleteCTRemote.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
				headers: localVarHeaderParams,
				json: true,
				method: 'DELETE',
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
				return new Promise<{  body?: any; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
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
				 * @param id 
		 */
		public async getCTRemote (
					id: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: CTRemote;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace(
							'{' + 'id' + '}',
							encodeURIComponent(String(id))
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

						// verify required parameter 'id' is not null or undefined
						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling getCTRemote.');
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
				return new Promise<{  body: CTRemote; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "CTRemote");
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
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
		 */
		public async getCTRemotesPage (
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageCTRemote;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-remotes'
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

					if (page !== undefined) {
						localVarQueryParameters['page'] = ObjectSerializer.serialize(page, "number");
					}
					if (pageSize !== undefined) {
						localVarQueryParameters['pageSize'] = ObjectSerializer.serialize(pageSize, "number");
					}
					if (search !== undefined) {
						localVarQueryParameters['search'] = ObjectSerializer.serialize(search, "string");
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
				return new Promise<{  body: PageCTRemote; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageCTRemote");
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
				 * @param id 
				 * @param CTRemote 
		 */
		public async patchCTRemote (
					id: number,
					CTRemote?: CTRemote,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: CTRemote;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace(
							'{' + 'id' + '}',
							encodeURIComponent(String(id))
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

						// verify required parameter 'id' is not null or undefined
						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling patchCTRemote.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTRemote, "CTRemote"),
				headers: localVarHeaderParams,
				json: true,
				method: 'PATCH',
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
				return new Promise<{  body: CTRemote; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "CTRemote");
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
				 * @param CTRemote 
		 */
		public async postCTRemote (
					CTRemote?: CTRemote,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: CTRemote;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-remotes'
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


			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTRemote, "CTRemote"),
				headers: localVarHeaderParams,
				json: true,
				method: 'POST',
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
				return new Promise<{  body: CTRemote; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "CTRemote");
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
				 * @param id 
				 * @param CTRemote 
		 */
		public async putCTRemote (
					id: number,
					CTRemote?: CTRemote,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: CTRemote;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-remotes/{id}'
						.replace(
							'{' + 'id' + '}',
							encodeURIComponent(String(id))
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

						// verify required parameter 'id' is not null or undefined
						if (id === null || id === undefined) {
							throw new Error('Required parameter id was null or undefined when calling putCTRemote.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTRemote, "CTRemote"),
				headers: localVarHeaderParams,
				json: true,
				method: 'PUT',
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
				return new Promise<{  body: CTRemote; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "CTRemote");
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