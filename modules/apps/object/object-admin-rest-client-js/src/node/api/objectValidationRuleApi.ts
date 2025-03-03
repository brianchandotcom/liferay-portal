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
		import {ObjectValidationRule} from '../model/objectValidationRule';
		import {PageObjectValidationRule} from '../model/pageObjectValidationRule';

import {HttpError} from './apis';
const defaultBasePath = 'http://localhost';

/**
 * @author Javier Gamarra
 * @generated
 */

export enum ObjectValidationRuleApiApiKeys {}

export class ObjectValidationRuleApi {
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

	public setApiKey(key: ObjectValidationRuleApiApiKeys, value: string) {
		(this.authentications as any)[ObjectValidationRuleApiApiKeys[key]].apiKey =
			value;
	}

	public addInterceptor(interceptor: Interceptor) {
		this.interceptors.push(interceptor);
	}

		/**
		 * 
				 * @param objectValidationRuleId 
		 */
		public async deleteObjectValidationRule(
					objectValidationRuleId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body?: any;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}'
						.replace(
							'{' + 'objectValidationRuleId' + '}',
							encodeURIComponent(String(objectValidationRuleId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (objectValidationRuleId === null || objectValidationRuleId === undefined) {
							throw new Error('Required parameter objectValidationRuleId was null or undefined when calling deleteObjectValidationRule.');
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
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
				 * @param Accept_Language 
		 */
		public async getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage(
					externalReferenceCode: string,
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
					Accept_Language?: string,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: PageObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-validation-rules'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
																								;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling getObjectDefinitionByExternalReferenceCodeObjectValidationRulesPage.');
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
				return new Promise<{  body: PageObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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
				 * @param objectDefinitionId 
				 * @param page 
				 * @param pageSize 
				 * @param search 
				 * @param sort 
				 * @param Accept_Language 
		 */
		public async getObjectDefinitionObjectValidationRulesPage(
					objectDefinitionId: number,
					page?: number,
					pageSize?: number,
					search?: string,
					sort?: string,
					Accept_Language?: string,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: PageObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules'
						.replace(
							'{' + 'objectDefinitionId' + '}',
							encodeURIComponent(String(objectDefinitionId))
						)
																								;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (objectDefinitionId === null || objectDefinitionId === undefined) {
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling getObjectDefinitionObjectValidationRulesPage.');
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
				return new Promise<{  body: PageObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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
				 * @param objectValidationRuleId 
		 */
		public async getObjectValidationRule(
					objectValidationRuleId: number,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: ObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}'
						.replace(
							'{' + 'objectValidationRuleId' + '}',
							encodeURIComponent(String(objectValidationRuleId))
						)
				;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (objectValidationRuleId === null || objectValidationRuleId === undefined) {
							throw new Error('Required parameter objectValidationRuleId was null or undefined when calling getObjectValidationRule.');
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
				return new Promise<{  body: ObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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
				 * @param objectValidationRuleId 
				 * @param ObjectValidationRule 
		 */
		public async patchObjectValidationRule(
					objectValidationRuleId: number,
					ObjectValidationRule?: ObjectValidationRule,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: ObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}'
						.replace(
							'{' + 'objectValidationRuleId' + '}',
							encodeURIComponent(String(objectValidationRuleId))
						)
								;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (objectValidationRuleId === null || objectValidationRuleId === undefined) {
							throw new Error('Required parameter objectValidationRuleId was null or undefined when calling patchObjectValidationRule.');
						}
			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
						body: ObjectSerializer.serialize(ObjectValidationRule, "ObjectValidationRule"),
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
				return new Promise<{  body: ObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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
				 * @param ObjectValidationRule 
		 */
		public async postObjectDefinitionByExternalReferenceCodeObjectValidationRule(
					externalReferenceCode: string,
					ObjectValidationRule?: ObjectValidationRule,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: ObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/by-external-reference-code/{externalReferenceCode}/object-validation-rules'
						.replace(
							'{' + 'externalReferenceCode' + '}',
							encodeURIComponent(String(externalReferenceCode))
						)
								;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error('Required parameter externalReferenceCode was null or undefined when calling postObjectDefinitionByExternalReferenceCodeObjectValidationRule.');
						}
			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
						body: ObjectSerializer.serialize(ObjectValidationRule, "ObjectValidationRule"),
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
				return new Promise<{  body: ObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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
				 * @param objectDefinitionId 
				 * @param ObjectValidationRule 
		 */
		public async postObjectDefinitionObjectValidationRule(
					objectDefinitionId: number,
					ObjectValidationRule?: ObjectValidationRule,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: ObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-definitions/{objectDefinitionId}/object-validation-rules'
						.replace(
							'{' + 'objectDefinitionId' + '}',
							encodeURIComponent(String(objectDefinitionId))
						)
								;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (objectDefinitionId === null || objectDefinitionId === undefined) {
							throw new Error('Required parameter objectDefinitionId was null or undefined when calling postObjectDefinitionObjectValidationRule.');
						}
			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
						body: ObjectSerializer.serialize(ObjectValidationRule, "ObjectValidationRule"),
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
				return new Promise<{  body: ObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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
				 * @param objectValidationRuleId 
				 * @param ObjectValidationRule 
		 */
		public async putObjectValidationRule(
					objectValidationRuleId: number,
					ObjectValidationRule?: ObjectValidationRule,
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
				body: ObjectValidationRule;
			response: http.IncomingMessage;
		}> {
			const localVarPath = this.basePath + '/object-admin/v1.0/object-validation-rules/{objectValidationRuleId}'
						.replace(
							'{' + 'objectValidationRuleId' + '}',
							encodeURIComponent(String(objectValidationRuleId))
						)
								;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
				const responseContentTypes = ['application/json', 'application/xml'];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			const localVarFormParams: any = {};

						if (objectValidationRuleId === null || objectValidationRuleId === undefined) {
							throw new Error('Required parameter objectValidationRuleId was null or undefined when calling putObjectValidationRule.');
						}
			(<any>Object).assign(localVarHeaderParams, options.headers);

			const localVarUseFormData = false;

			const localVarRequestOptions: localVarRequest.Options = {
						body: ObjectSerializer.serialize(ObjectValidationRule, "ObjectValidationRule"),
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
				return new Promise<{  body: ObjectValidationRule; response: http.IncomingMessage;}>((resolve, reject) => {
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