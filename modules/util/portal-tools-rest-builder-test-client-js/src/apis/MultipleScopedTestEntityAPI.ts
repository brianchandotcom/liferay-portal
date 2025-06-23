/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {MultipleScopedTestEntity} from '../models/MultipleScopedTestEntity';
		import {PageMultipleScopedTestEntity} from '../models/PageMultipleScopedTestEntity';

/**
 * @author Alejandro Tardín
 * @generated
 */

export class MultipleScopedTestEntityAPI {
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
				 * @param assetLibraryId
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async deleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
						assetLibraryId: number,
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling deleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling deleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "DELETE",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async deleteMultipleScopedTestEntityByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling deleteMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "DELETE",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param siteId
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async deleteSiteMultipleScopedTestEntityByExternalReferenceCode(
						siteId: number,
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/sites/{siteId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{siteId}",encodeURIComponent(siteId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling deleteSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling deleteSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "DELETE",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param assetLibraryId
		 * @param headers Optional custom request headers
		 */
		public async getAssetLibraryMultipleScopedTestEntitiesPage(
						assetLibraryId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageMultipleScopedTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryId}/multiple-scoped-test-entities"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
				;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling getAssetLibraryMultipleScopedTestEntitiesPage.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "PageMultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param assetLibraryId
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
						assetLibraryId: number,
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
		 * @param headers Optional custom request headers
		 */
		public async getMultipleScopedTestEntitiesPage(
			headers?: {[name: string]: string},
		): Promise<{
				body: PageMultipleScopedTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/multiple-scoped-test-entities"
;

			const queryParameters: any = {};

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "PageMultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getMultipleScopedTestEntityByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling getMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param siteId
		 * @param headers Optional custom request headers
		 */
		public async getSiteMultipleScopedTestEntitiesPage(
						siteId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageMultipleScopedTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/sites/{siteId}/multiple-scoped-test-entities"
						.replace("{siteId}",encodeURIComponent(siteId))
				;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling getSiteMultipleScopedTestEntitiesPage.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "PageMultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param siteId
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getSiteMultipleScopedTestEntityByExternalReferenceCode(
						siteId: number,
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/sites/{siteId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{siteId}",encodeURIComponent(siteId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling getSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling getSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param assetLibraryId
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						assetLibraryId: number,
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PATCH",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param assetLibraryId
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
									assetLibraryId: number,
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										assetLibraryId,
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
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
		public async patchMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling patchMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PATCH",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async patchMultipleScopedTestEntityByExternalReferenceCode(
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.patchMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param siteId
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchSiteMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						siteId: number,
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/sites/{siteId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{siteId}",encodeURIComponent(siteId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling patchSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling patchSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PATCH",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param siteId
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async patchSiteMultipleScopedTestEntityByExternalReferenceCode(
									siteId: number,
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.patchSiteMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										siteId,
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param assetLibraryId
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						assetLibraryId: number,
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "POST",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param assetLibraryId
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
									assetLibraryId: number,
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.postAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										assetLibraryId,
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
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
		public async postMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling postMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "POST",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async postMultipleScopedTestEntityByExternalReferenceCode(
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.postMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param siteId
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postSiteMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						siteId: number,
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/sites/{siteId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{siteId}",encodeURIComponent(siteId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling postSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling postSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "POST",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param siteId
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async postSiteMultipleScopedTestEntityByExternalReferenceCode(
									siteId: number,
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.postSiteMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										siteId,
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param assetLibraryId
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						assetLibraryId: number,
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PUT",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param assetLibraryId
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
									assetLibraryId: number,
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.putAssetLibraryMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										assetLibraryId,
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
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
		public async putMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling putMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PUT",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async putMultipleScopedTestEntityByExternalReferenceCode(
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.putMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param siteId
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putSiteMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
						siteId: number,
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										multipleScopedTestEntity?: MultipleScopedTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: MultipleScopedTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.multipleScopedTestEntity, "MultipleScopedTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/sites/{siteId}/multiple-scoped-test-entities/by-external-reference-code/{externalReferenceCode}"
						.replace("{siteId}",encodeURIComponent(siteId))
										.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling putSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling putSiteMultipleScopedTestEntityByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PUT",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "MultipleScopedTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

					/**
					 *  - Default method for JSON body
							 * @param siteId
							 * @param externalReferenceCode
						 * @param multipleScopedTestEntity
					 */
					public async putSiteMultipleScopedTestEntityByExternalReferenceCode(
									siteId: number,
									externalReferenceCode: string,
							multipleScopedTestEntity?: MultipleScopedTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: MultipleScopedTestEntity;
						response: Response;
					}> {
						return this.putSiteMultipleScopedTestEntityByExternalReferenceCodeWithContentType(
										siteId,
										externalReferenceCode,
							{
								parameters: {
										multipleScopedTestEntity: multipleScopedTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
}