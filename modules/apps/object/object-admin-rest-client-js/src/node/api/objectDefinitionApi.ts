/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
import { Authentication, Interceptor, ObjectSerializer, VoidAuth } from '../model/models';

		import { ObjectDefinition } from '../model/objectDefinition';
		import { PageObjectDefinition } from '../model/pageObjectDefinition';

import { HttpError } from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author Javier Gamarra
 * @generated
 */

export enum ObjectDefinitionApiApiKeys {
}

export class ObjectDefinitionApi {
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

	public setApiKey(key: ObjectDefinitionApiApiKeys, value: string) {
		(this.authentications as any)[ObjectDefinitionApiApiKeys[key]].apiKey = value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
	}

		/**
		 * 
				 * @param objectDefinitionId 
		 */
		public async deleteObjectDefinition (
					objectDefinitionId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}'
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
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling deleteObjectDefinition.');
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
				 * @param objectDefinitionId 
		 */
		public async getObjectDefinition (
					objectDefinitionId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}'
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
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling getObjectDefinition.');
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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
		 */
		public async getObjectDefinitionByExternalReferenceCode (
					externalReferenceCode: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}'
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
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getObjectDefinitionByExternalReferenceCode.');
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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
				 * @param aggregationTerms 
				 * @param filter 
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
				 * @param Accept_Language 
		 */
		public async getObjectDefinitionsPage (
					aggregationTerms?: Array<string>,
					filter?: string,
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
					Accept_Language?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions'
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

					if (aggregationTerms !== undefined) {
						localVarQueryParameters['aggregationTerms'] = ObjectSerializer.serialize(aggregationTerms, "Array<string>");
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
				return new Promise<{  body: PageObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageObjectDefinition");
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
				 * @param ObjectDefinition 
		 */
		public async patchObjectDefinition (
					objectDefinitionId: number,
					ObjectDefinition?: ObjectDefinition,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}'
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
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling patchObjectDefinition.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(ObjectDefinition, "ObjectDefinition"),
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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
				 * @param ObjectDefinition 
		 */
		public async postObjectDefinition (
					ObjectDefinition?: ObjectDefinition,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions'
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
					body: ObjectSerializer.serialize(ObjectDefinition, "ObjectDefinition"),
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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
		 */
		public async postObjectDefinitionPublish (
					objectDefinitionId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/publish'
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
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling postObjectDefinitionPublish.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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
				 * @param ObjectDefinition 
		 */
		public async putObjectDefinition (
					objectDefinitionId: number,
					ObjectDefinition?: ObjectDefinition,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}'
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
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling putObjectDefinition.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(ObjectDefinition, "ObjectDefinition"),
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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
				 * @param ObjectDefinition 
		 */
		public async putObjectDefinitionByExternalReferenceCode (
					externalReferenceCode: string,
					ObjectDefinition?: ObjectDefinition,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: ObjectDefinition;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}'
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
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling putObjectDefinitionByExternalReferenceCode.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(ObjectDefinition, "ObjectDefinition"),
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
				return new Promise<{  body: ObjectDefinition; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "ObjectDefinition");
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