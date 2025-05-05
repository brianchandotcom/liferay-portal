/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {SimpleTestEntity} from '../models/SimpleTestEntity';

/**
 * @author Alejandro Tardín
 * @generated
 */

export class SimpleTestEntityAPI {
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
				 * @param simpleTestEntityId
		 * @param headers Optional custom request headers
		 */
		public async getSimpleTestEntity(
						simpleTestEntityId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: SimpleTestEntity;
			response: Response;
		}> {

			const path = this._basePath + "/test/v1.0/simple-test-entities/{simpleEntityId}"
						.replace("{simpleTestEntityId}",encodeURIComponent(simpleTestEntityId))
				;

			const queryParameters: any = {};

						if (simpleTestEntityId === null || simpleTestEntityId === undefined) {
							throw new Error("Required parameter simpleTestEntityId was null or undefined when calling getSimpleTestEntity.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "SimpleTestEntity"), response};
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
				 * @param simpleTestEntityId
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchSimpleTestEntityWithContentType(
						simpleTestEntityId: number,
					requestBody:
							{
								parameters: {
										simpleTestEntity?: SimpleTestEntity
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										simpleTestEntity?: SimpleTestEntity
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: SimpleTestEntity;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.simpleTestEntity, "SimpleTestEntity"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.simpleTestEntity, "SimpleTestEntity"));
						}

			const path = this._basePath + "/test/v1.0/simple-test-entities/{simpleEntityId}"
						.replace("{simpleTestEntityId}",encodeURIComponent(simpleTestEntityId))
				;

			const queryParameters: any = {};

						if (simpleTestEntityId === null || simpleTestEntityId === undefined) {
							throw new Error("Required parameter simpleTestEntityId was null or undefined when calling patchSimpleTestEntity.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "SimpleTestEntity"), response};
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
							 * @param simpleTestEntityId
						 * @param simpleTestEntity
					 */
					public async patchSimpleTestEntity(
									simpleTestEntityId: number,
							simpleTestEntity?: SimpleTestEntity,
						headers?: {[name: string]: string}
					): Promise<{
							body: SimpleTestEntity;
						response: Response;
					}> {
						return this.patchSimpleTestEntityWithContentType(
										simpleTestEntityId,
							{
								parameters: {
										simpleTestEntity: simpleTestEntity
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param simpleTestEntityId
				 	* @param simpleTestEntity
		 * @param headers Optional custom request headers
		 */
		public async putSimpleTestEntity(
						simpleTestEntityId: number,
						simpleTestEntity?: SimpleTestEntity,
			headers?: {[name: string]: string},
		): Promise<{
				body: SimpleTestEntity;
			response: Response;
		}> {
				let body;
						const formData = new FormData();
								formData.append("simpleTestEntity", JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.simpleTestEntity, "SimpleTestEntity")));
						body = formData;

			const path = this._basePath + "/test/v1.0/simple-test-entities/{simpleEntityId}"
						.replace("{simpleTestEntityId}",encodeURIComponent(simpleTestEntityId))
				;

			const queryParameters: any = {};

						if (simpleTestEntityId === null || simpleTestEntityId === undefined) {
							throw new Error("Required parameter simpleTestEntityId was null or undefined when calling putSimpleTestEntity.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "multipart/form-data"
						}
					,headers || {}
					),
				method: "PUT",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "SimpleTestEntity"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

}