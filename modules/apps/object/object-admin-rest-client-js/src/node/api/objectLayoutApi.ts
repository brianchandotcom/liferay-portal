/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
import { Authentication, Interceptor, ObjectSerializer, VoidAuth } from '../model/models';

		import { ObjectLayout } from '../model/objectLayout';
		import { PageObjectLayout } from '../model/pageObjectLayout';

import { HttpError } from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author Javier Gamarra
 * @generated
 */

export enum ObjectLayoutApiApiKeys {
}

export class ObjectLayoutApi {
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

	public setApiKey(key: ObjectLayoutApiApiKeys, value: string) {
		(this.authentications as any)[ObjectLayoutApiApiKeys[key]].apiKey = value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
	}

		/**
		 * 
				 * @param objectLayoutId 
		 */
		public async deleteObjectLayout (
					objectLayoutId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-layouts/{objectLayoutId}'
						.replace(
							'{' + 'objectLayoutId' + '}',
							encodeURIComponent(String(objectLayoutId))
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

						// verify required parameter 'objectLayoutId' is not null or undefined
						if (objectLayoutId === null || objectLayoutId === undefined) {
							throw new Error('Required parameter objectLayoutId was null or undefined when calling deleteObjectLayout.');
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
				 * @param externalReferenceCode 
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
				 * @param Accept_Language 
		 */
		public async getObjectDefinitionByExternalReferenceCodeObjectLayoutsPage (
					externalReferenceCode: string,
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
					Accept_Language?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageObjectLayout;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-layouts'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
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

						// verify required parameter 'externalReferenceCode' is not null or undefined
						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getObjectDefinitionByExternalReferenceCodeObjectLayoutsPage.');
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
				return new Promise<{  body: PageObjectLayout; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageObjectLayout");
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
				 * @param objectDefinitionId 
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
				 * @param Accept_Language 
		 */
		public async getObjectDefinitionObjectLayoutsPage (
					objectDefinitionId: number,
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
					Accept_Language?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageObjectLayout;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts'
						.replace(
							'{' + 'objectDefinitionId' + '}',
							encodeURIComponent(String(objectDefinitionId))
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

						// verify required parameter 'objectDefinitionId' is not null or undefined
						if (objectDefinitionId === null || objectDefinitionId === undefined) {
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling getObjectDefinitionObjectLayoutsPage.');
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
				return new Promise<{  body: PageObjectLayout; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageObjectLayout");
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
				 * @param objectLayoutId 
		 */
		public async getObjectLayout (
					objectLayoutId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectLayout;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-layouts/{objectLayoutId}'
						.replace(
							'{' + 'objectLayoutId' + '}',
							encodeURIComponent(String(objectLayoutId))
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

						// verify required parameter 'objectLayoutId' is not null or undefined
						if (objectLayoutId === null || objectLayoutId === undefined) {
							throw new Error('Required parameter objectLayoutId was null or undefined when calling getObjectLayout.');
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
				return new Promise<{  body: ObjectLayout; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectLayout");
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
				 * @param externalReferenceCode 
				 * @param ObjectLayout 
		 */
		public async postObjectDefinitionByExternalReferenceCodeObjectLayout (
					externalReferenceCode: string,
					ObjectLayout?: ObjectLayout,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectLayout;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-layouts'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
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

						// verify required parameter 'externalReferenceCode' is not null or undefined
						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postObjectDefinitionByExternalReferenceCodeObjectLayout.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(ObjectLayout, "ObjectLayout"),
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
				return new Promise<{  body: ObjectLayout; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectLayout");
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
				 * @param objectDefinitionId 
				 * @param ObjectLayout 
		 */
		public async postObjectDefinitionObjectLayout (
					objectDefinitionId: number,
					ObjectLayout?: ObjectLayout,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectLayout;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-layouts'
						.replace(
							'{' + 'objectDefinitionId' + '}',
							encodeURIComponent(String(objectDefinitionId))
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

						// verify required parameter 'objectDefinitionId' is not null or undefined
						if (objectDefinitionId === null || objectDefinitionId === undefined) {
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling postObjectDefinitionObjectLayout.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(ObjectLayout, "ObjectLayout"),
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
				return new Promise<{  body: ObjectLayout; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectLayout");
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
				 * @param objectLayoutId 
				 * @param ObjectLayout 
		 */
		public async putObjectLayout (
					objectLayoutId: number,
					ObjectLayout?: ObjectLayout,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectLayout;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-layouts/{objectLayoutId}'
						.replace(
							'{' + 'objectLayoutId' + '}',
							encodeURIComponent(String(objectLayoutId))
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

						// verify required parameter 'objectLayoutId' is not null or undefined
						if (objectLayoutId === null || objectLayoutId === undefined) {
							throw new Error('Required parameter objectLayoutId was null or undefined when calling putObjectLayout.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(ObjectLayout, "ObjectLayout"),
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
				return new Promise<{  body: ObjectLayout; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectLayout");
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