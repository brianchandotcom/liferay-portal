/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
import {
	Authentication,
	Interceptor,
	ObjectSerializer,
	VoidAuth,
} from '../model/models';
		import {CTCollection} from '../model/cTCollection';
		import {PageCTCollection} from '../model/pageCTCollection';

import {HttpError} from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author David Truong
 * @generated
 */

export enum CTCollectionApiApiKeys {}

export class CTCollectionApi {
	protected _basePath = defaultBasePath;
	protected _defaultHeaders: any = {};
	protected _useQuerystring: boolean = false;

	protected authentications = {
		default: <Authentication>new VoidAuth(),
	};

	protected interceptors: Interceptor[] = [];

	constructor(basePath?: string);
	constructor(
		basePathOrUsername: string,
		password?: string,
		basePath?: string
	) {
		if (password) {
			if (basePath) {
				this.basePath = basePath;
			}
		}
		else {
			if (basePathOrUsername) {
				this.basePath = basePathOrUsername;
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

	public setApiKey(key: CTCollectionApiApiKeys, value: string) {
		(this.authentications as any)[CTCollectionApiApiKeys[key]].apiKey =
			value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
	}

		/**
		 * 
				 * @param ctCollectionId 
		 */
		public async deleteCTCollection(
					ctCollectionId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling deleteCTCollection.');
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
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param externalReferenceCode 
		 */
		public async deleteCTCollectionByExternalReferenceCode(
					externalReferenceCode: string,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling deleteCTCollectionByExternalReferenceCode.');
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
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
		 */
		public async getCTCollection(
					ctCollectionId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: CTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCTCollection.');
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
				return new Promise<{  body: CTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param externalReferenceCode 
		 */
		public async getCTCollectionByExternalReferenceCode(
					externalReferenceCode: string,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: CTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getCTCollectionByExternalReferenceCode.');
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
				return new Promise<{  body: CTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param externalReferenceCode 
		 */
		public async getCTCollectionByExternalReferenceCodeShareLink(
					externalReferenceCode: string,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: string;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/share-link'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['text/plain'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getCTCollectionByExternalReferenceCodeShareLink.');
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
				return new Promise<{  body: string; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
		 */
		public async getCTCollectionShareLink(
					ctCollectionId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: string;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/b{ctCollectionId}/share-link'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['text/plain'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling getCTCollectionShareLink.');
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
				return new Promise<{  body: string; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
				 * @param status 
		 */
		public async getCTCollectionsPage(
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
					status?: Array<number>,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: PageCTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections'
;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
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
					if (status !== undefined) {
						localVarQueryParameters['status'] = ObjectSerializer.serialize(status, "Array<number>");
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
				return new Promise<{  body: PageCTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
				 * @param CTCollection 
		 */
		public async patchCTCollection(
					ctCollectionId: number,
					CTCollection?: CTCollection,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: CTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling patchCTCollection.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTCollection, "CTCollection"),
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
				return new Promise<{  body: CTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param externalReferenceCode 
				 * @param CTCollection 
		 */
		public async patchCTCollectionByExternalReferenceCode(
					externalReferenceCode: string,
					CTCollection?: CTCollection,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: CTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling patchCTCollectionByExternalReferenceCode.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTCollection, "CTCollection"),
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
				return new Promise<{  body: CTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param CTCollection 
		 */
		public async postCTCollection(
					CTCollection?: CTCollection,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: CTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections'
;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};


			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTCollection, "CTCollection"),
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
				return new Promise<{  body: CTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param externalReferenceCode 
		 */
		public async postCTCollectionByExternalReferenceCodePublish(
					externalReferenceCode: string,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/publish'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postCTCollectionByExternalReferenceCodePublish.');
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
				return new Promise<{  body?: any; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param externalReferenceCode 
				 * @param publishDate 
		 */
		public async postCTCollectionByExternalReferenceCodeSchedulePublish(
					externalReferenceCode: string,
					publishDate?: Date,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/by-external-reference-code/{externalReferenceCode}/schedule-publish'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postCTCollectionByExternalReferenceCodeSchedulePublish.');
						}
					if (publishDate !== undefined) {
						localVarQueryParameters['publishDate'] = ObjectSerializer.serialize(publishDate, "Date");
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
				return new Promise<{  body?: any; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
		 */
		public async postCTCollectionCheckout(
					ctCollectionId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/checkout'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling postCTCollectionCheckout.');
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
				return new Promise<{  body?: any; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
		 */
		public async postCTCollectionPublish(
					ctCollectionId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/publish'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling postCTCollectionPublish.');
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
				return new Promise<{  body?: any; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
				 * @param publishDate 
		 */
		public async postCTCollectionSchedulePublish(
					ctCollectionId: number,
					publishDate?: Date,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}/schedule-publish'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling postCTCollectionSchedulePublish.');
						}
					if (publishDate !== undefined) {
						localVarQueryParameters['publishDate'] = ObjectSerializer.serialize(publishDate, "Date");
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
				return new Promise<{  body?: any; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
		/**
		 * 
				 * @param ctCollectionId 
				 * @param CTCollection 
		 */
		public async putCTCollection(
					ctCollectionId: number,
					CTCollection?: CTCollection,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: CTCollection;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/change-tracking-rest/v1.0/ct-collections/{ctCollectionId}'
						.replace(
							'{' + 'ctCollectionId' + '}',
							encodeURIComponent(String(ctCollectionId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const produces = ['application/json', 'application/xml'];
				if (produces.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = produces.join(',');
				}
			const localVarFormParams: any = {};

						if (ctCollectionId === null || ctCollectionId === undefined) {
							throw new Error('Required parameter ctCollectionId was null or undefined when calling putCTCollection.');
						}

			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
					body: ObjectSerializer.serialize(CTCollection, "CTCollection"),
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
				return new Promise<{  body: CTCollection; response: http.IncomingMessage;}>((resolve, reject) => {
					localVarRequest(localVarRequestOptions, (error, response, body) => {
						if (error) {
							reject(error);
						}
						else {
							if (
								response.statusCode &&
								response.statusCode >= 200 &&
								response.statusCode <= 299
							) {
								resolve({body, response});
							}
							else {
								reject(
									new HttpError(
										body,
										response,
										response.statusCode
									)
								);
							}
						}
					}
				);
			});
		});
	}
}