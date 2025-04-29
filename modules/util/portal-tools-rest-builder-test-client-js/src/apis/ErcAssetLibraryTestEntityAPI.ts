/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {ErcAssetLibraryTestEntity} from '../models/ErcAssetLibraryTestEntity';
		import {PageErcAssetLibraryTestEntity} from '../models/PageErcAssetLibraryTestEntity';

/**
 * @author Alejandro Tardín
 * @generated
 */

export class ErcAssetLibraryTestEntityAPI {
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
				 * @param assetLibraryExternalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getAssetLibraryErcAssetLibraryTestEntitiesPage(
						assetLibraryExternalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageErcAssetLibraryTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryExternalReferenceCode}/erc-asset-library-test-entities"
						.replace("{assetLibraryExternalReferenceCode}",encodeURIComponent(assetLibraryExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryExternalReferenceCode === null || assetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter assetLibraryExternalReferenceCode was null or undefined when calling getAssetLibraryErcAssetLibraryTestEntitiesPage.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageErcAssetLibraryTestEntity"), response};
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
				 * @param ercAssetLibraryTestEntityExternalReferenceCode
				 * @param AssetLibraryExternalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getAssetLibraryErcAssetLibraryTestEntity(
						ercAssetLibraryTestEntityExternalReferenceCode: string,
						AssetLibraryExternalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: ErcAssetLibraryTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/asset-libraries/{AssetLibraryExternalReferenceCode}/erc-asset-library-test-entities/{ercAssetLibraryTestEntityExternalReferenceCode}"
						.replace("{ercAssetLibraryTestEntityExternalReferenceCode}",encodeURIComponent(ercAssetLibraryTestEntityExternalReferenceCode))
										.replace("{AssetLibraryExternalReferenceCode}",encodeURIComponent(AssetLibraryExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (ercAssetLibraryTestEntityExternalReferenceCode === null || ercAssetLibraryTestEntityExternalReferenceCode === undefined) {
							throw new Error("Required parameter ercAssetLibraryTestEntityExternalReferenceCode was null or undefined when calling getAssetLibraryErcAssetLibraryTestEntity.");
						}

						if (AssetLibraryExternalReferenceCode === null || AssetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter AssetLibraryExternalReferenceCode was null or undefined when calling getAssetLibraryErcAssetLibraryTestEntity.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ErcAssetLibraryTestEntity"), response};
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
				 * @param AssetLibraryExternalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postAssetLibraryErcAssetLibraryTestEntityWithContentType(
						AssetLibraryExternalReferenceCode: string,
					requestBody:
							{
								parameters: {
										ercAssetLibraryTestEntity?: ErcAssetLibraryTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										ercAssetLibraryTestEntity?: ErcAssetLibraryTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ErcAssetLibraryTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.ercAssetLibraryTestEntity, "ErcAssetLibraryTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.ercAssetLibraryTestEntity, "ErcAssetLibraryTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/asset-libraries/{assetLibraryExternalReferenceCode}/erc-asset-library-test-entities"
						.replace("{AssetLibraryExternalReferenceCode}",encodeURIComponent(AssetLibraryExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (AssetLibraryExternalReferenceCode === null || AssetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter AssetLibraryExternalReferenceCode was null or undefined when calling postAssetLibraryErcAssetLibraryTestEntity.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ErcAssetLibraryTestEntity"), response};
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
							 * @param AssetLibraryExternalReferenceCode
						 * @param ercAssetLibraryTestEntity
					 */
					public async postAssetLibraryErcAssetLibraryTestEntity(
									AssetLibraryExternalReferenceCode: string,
							ercAssetLibraryTestEntity?: ErcAssetLibraryTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: ErcAssetLibraryTestEntity;
						response: Response;
					}> {
						return this.postAssetLibraryErcAssetLibraryTestEntityWithContentType(
										AssetLibraryExternalReferenceCode,
							{
								parameters: {
										ercAssetLibraryTestEntity: ercAssetLibraryTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param ercAssetLibraryTestEntityExternalReferenceCode
				 * @param AssetLibraryExternalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putAssetLibraryErcAssetLibraryTestEntityWithContentType(
						ercAssetLibraryTestEntityExternalReferenceCode: string,
						AssetLibraryExternalReferenceCode: string,
					requestBody:
							{
								parameters: {
										ercAssetLibraryTestEntity?: ErcAssetLibraryTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										ercAssetLibraryTestEntity?: ErcAssetLibraryTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ErcAssetLibraryTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.ercAssetLibraryTestEntity, "ErcAssetLibraryTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.ercAssetLibraryTestEntity, "ErcAssetLibraryTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/asset-libraries/{AssetLibraryExternalReferenceCode}/erc-asset-library-test-entities/{ercAssetLibraryTestEntityExternalReferenceCode}"
						.replace("{ercAssetLibraryTestEntityExternalReferenceCode}",encodeURIComponent(ercAssetLibraryTestEntityExternalReferenceCode))
										.replace("{AssetLibraryExternalReferenceCode}",encodeURIComponent(AssetLibraryExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (ercAssetLibraryTestEntityExternalReferenceCode === null || ercAssetLibraryTestEntityExternalReferenceCode === undefined) {
							throw new Error("Required parameter ercAssetLibraryTestEntityExternalReferenceCode was null or undefined when calling putAssetLibraryErcAssetLibraryTestEntity.");
						}

						if (AssetLibraryExternalReferenceCode === null || AssetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter AssetLibraryExternalReferenceCode was null or undefined when calling putAssetLibraryErcAssetLibraryTestEntity.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ErcAssetLibraryTestEntity"), response};
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
							 * @param ercAssetLibraryTestEntityExternalReferenceCode
							 * @param AssetLibraryExternalReferenceCode
						 * @param ercAssetLibraryTestEntity
					 */
					public async putAssetLibraryErcAssetLibraryTestEntity(
									ercAssetLibraryTestEntityExternalReferenceCode: string,
									AssetLibraryExternalReferenceCode: string,
							ercAssetLibraryTestEntity?: ErcAssetLibraryTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: ErcAssetLibraryTestEntity;
						response: Response;
					}> {
						return this.putAssetLibraryErcAssetLibraryTestEntityWithContentType(
										ercAssetLibraryTestEntityExternalReferenceCode,
										AssetLibraryExternalReferenceCode,
							{
								parameters: {
										ercAssetLibraryTestEntity: ercAssetLibraryTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
}