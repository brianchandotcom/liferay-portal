/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
import { Authentication, Interceptor, ObjectSerializer, VoidAuth } from '../model/models';

		import { PageSiteTestEntity } from '../model/pageSiteTestEntity';
		import { SiteTestEntity } from '../model/siteTestEntity';

import { HttpError } from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author Alejandro Tardín
 * @generated
 */

export enum SiteTestEntityApiApiKeys {
}

export class SiteTestEntityApi {
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

	public setApiKey(key: SiteTestEntityApiApiKeys, value: string) {
		(this.authentications as any)[SiteTestEntityApiApiKeys[key]].apiKey = value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
	}

		/**
		 * 
				 * @param siteId 
		 */
		public async getSiteSiteTestEntitiesPage (
					siteId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: PageSiteTestEntity;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/sites/{siteId}/site-test-entities'
						.replace(
							'{' + 'siteId' + '}',
							encodeURIComponent(String(siteId))
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

						// verify required parameter 'siteId' is not null or undefined
						if (siteId === null || siteId === undefined) {
							throw new Error('Required parameter siteId was null or undefined when calling getSiteSiteTestEntitiesPage.');
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
				return new Promise<{  body: PageSiteTestEntity; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "PageSiteTestEntity");
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
				 * @param siteId 
		 */
		public async getSiteSiteTestEntityByExternalReferenceCode (
					externalReferenceCode: string,
					siteId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: SiteTestEntity;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/sites/{siteId}/site-test-entities/by-external-reference-code/{externalReferenceCode}'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
						.replace(
							'{' + 'siteId' + '}',
							encodeURIComponent(String(siteId))
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
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getSiteSiteTestEntityByExternalReferenceCode.');
						}
						// verify required parameter 'siteId' is not null or undefined
						if (siteId === null || siteId === undefined) {
							throw new Error('Required parameter siteId was null or undefined when calling getSiteSiteTestEntityByExternalReferenceCode.');
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
				return new Promise<{  body: SiteTestEntity; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "SiteTestEntity");
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
				 * @param siteTestEntityId 
		 */
		public async getSiteTestEntity (
					siteTestEntityId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: SiteTestEntity;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/site-test-entities/{siteTestEntityId}'
						.replace(
							'{' + 'siteTestEntityId' + '}',
							encodeURIComponent(String(siteTestEntityId))
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

						// verify required parameter 'siteTestEntityId' is not null or undefined
						if (siteTestEntityId === null || siteTestEntityId === undefined) {
							throw new Error('Required parameter siteTestEntityId was null or undefined when calling getSiteTestEntity.');
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
				return new Promise<{  body: SiteTestEntity; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "SiteTestEntity");
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
				 * @param siteTestEntityId 
				 * @param roleNames 
		 */
		public async getSiteTestEntityPermissionsPage (
					siteTestEntityId: number,
					roleNames?: string,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/site-test-entities/{siteTestEntityId}/permissions'
						.replace(
							'{' + 'siteTestEntityId' + '}',
							encodeURIComponent(String(siteTestEntityId))
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

						// verify required parameter 'siteTestEntityId' is not null or undefined
						if (siteTestEntityId === null || siteTestEntityId === undefined) {
							throw new Error('Required parameter siteTestEntityId was null or undefined when calling getSiteTestEntityPermissionsPage.');
						}
					if (roleNames !== undefined) {
						localVarQueryParameters['roleNames'] = ObjectSerializer.serialize(roleNames, "string");
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
				 * @param siteId 
				 * @param SiteTestEntity 
		 */
		public async postSiteSiteTestEntity (
					siteId: number,
					SiteTestEntity?: SiteTestEntity,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: SiteTestEntity;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/sites/{siteId}/site-test-entities'
						.replace(
							'{' + 'siteId' + '}',
							encodeURIComponent(String(siteId))
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

						// verify required parameter 'siteId' is not null or undefined
						if (siteId === null || siteId === undefined) {
							throw new Error('Required parameter siteId was null or undefined when calling postSiteSiteTestEntity.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(SiteTestEntity, "SiteTestEntity"),
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
				return new Promise<{  body: SiteTestEntity; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "SiteTestEntity");
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
				 * @param siteId 
				 * @param SiteTestEntity 
		 */
		public async putSiteSiteTestEntityByExternalReferenceCode (
					externalReferenceCode: string,
					siteId: number,
					SiteTestEntity?: SiteTestEntity,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: SiteTestEntity;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/sites/{siteId}/site-test-entities/by-external-reference-code/{externalReferenceCode}'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
						.replace(
							'{' + 'siteId' + '}',
							encodeURIComponent(String(siteId))
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
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling putSiteSiteTestEntityByExternalReferenceCode.');
						}
						// verify required parameter 'siteId' is not null or undefined
						if (siteId === null || siteId === undefined) {
							throw new Error('Required parameter siteId was null or undefined when calling putSiteSiteTestEntityByExternalReferenceCode.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(SiteTestEntity, "SiteTestEntity"),
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
				return new Promise<{  body: SiteTestEntity; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "SiteTestEntity");
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
				 * @param siteTestEntityId 
				 * @param SiteTestEntity 
		 */
		public async putSiteTestEntity (
					siteTestEntityId: number,
					SiteTestEntity?: SiteTestEntity,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body: SiteTestEntity;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/site-test-entities/{siteTestEntityId}'
						.replace(
							'{' + 'siteTestEntityId' + '}',
							encodeURIComponent(String(siteTestEntityId))
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

						// verify required parameter 'siteTestEntityId' is not null or undefined
						if (siteTestEntityId === null || siteTestEntityId === undefined) {
							throw new Error('Required parameter siteTestEntityId was null or undefined when calling putSiteTestEntity.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(SiteTestEntity, "SiteTestEntity"),
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
				return new Promise<{  body: SiteTestEntity; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						} else {
							if (response.statusCode && response.statusCode >= 200 && response.statusCode <= 299) {
									body = ObjectSerializer.deserialize(body, "SiteTestEntity");
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
				 * @param siteTestEntityId 
		 */
		public async putSiteTestEntityPermissionsPage (
					siteTestEntityId: number,
			options: {
				headers: {[name: string]: string}
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/test/v1.0/site-test-entities/{siteTestEntityId}/permissions'
						.replace(
							'{' + 'siteTestEntityId' + '}',
							encodeURIComponent(String(siteTestEntityId))
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

						// verify required parameter 'siteTestEntityId' is not null or undefined
						if (siteTestEntityId === null || siteTestEntityId === undefined) {
							throw new Error('Required parameter siteTestEntityId was null or undefined when calling putSiteTestEntityPermissionsPage.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
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
}