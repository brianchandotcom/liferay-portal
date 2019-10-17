/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import middlewares from './middlewares/defaults';
import hash from './utils/hash';

/**
 * Client used to abstract communication with the Analytics Endpoint. It exposes
 * the send and use methods as only valid entry points for sending and modifiying
 * requests.
 * @class
 */
class Client {
	/**
	 * Constructor
	 * @param {*} uri The Endpoint URI where the data should be sent
	 */
	constructor(uri) {
		this.uri = uri;
	}

	_getContextEvents(analytics, context) {
		return analytics.events.filter(event => {
			const contextHash = hash(context, {
				unorderedObjects: false
			});

			let eventId = false;

			if (event.applicationId && event.contextHash === contextHash) {
				eventId = event.eventId;
			}

			return eventId;
		});
	}

	/**
	 * Returns a Request object with all data from the analytics instance
	 * includin the batched event objects
	 * @param {object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @protected
	 * @return {object} Request
	 */
	_getRequest(analytics, userId, context) {
		const body = JSON.stringify(
			this._getRequestBody(analytics, userId, context)
		);
		const headers = new Headers();

		headers.append('Content-Type', 'application/json');

		return {
			body,
			cache: 'default',
			credentials: 'same-origin',
			headers,
			method: 'POST',
			mode: 'cors'
		};
	}

	/**
	 * Returns the formatted version of the analytics data that complies to the
	 * predefined request specification of the endpoint
	 * @param {object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @protected
	 * @return {object} Body of the request to be sent to the endpoint.
	 */
	_getRequestBody(analytics, userId, context) {
		const events = this._getContextEvents(analytics, context);
		const {dataSourceId} = analytics.config;

		return {
			context,
			dataSourceId,
			events,
			protocolVersion: '1.0',
			userId
		};
	}

	/**
	 * Returns the Response object or a rejected Promise based on the
	 * HTTP Response Code of the Response object
	 * @param {object} response Response
	 * @return {object} Promise
	 */
	_validateResponse(response) {
		if (!response.ok) {
			response = new Promise((resolve, reject) => reject(response));
		}

		return response;
	}

	/**
	 * Returns a resolved or rejected promise as per the response status
	 * @param {object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @return {object} Promise object representing the result of the operation
	 */
	send(analytics, userId) {
		return Promise.all(
			analytics.contexts
				.filter(
					context =>
						this._getContextEvents(analytics, context).length > 0
				)
				.map(context => {
					const request = this._getRequest(
						analytics,
						userId,
						context
					);

					return fetch(this.uri, request).then(
						this._validateResponse
					);
				})
		);
	}

	/**
	 * Adds a middleware function to provide ability to transform the request
	 * that is sent to the endpoint
	 * @param {Function} middleware A function to alter request
	 * @example
	 * Client.use(
	 *   (req, analytics) => {
	 *     req.firstEvent = analytics.events[0];
	 *     req.myMetaInfo = 'myMetaInfo';
	 *
	 *     return req;
	 *   }
	 * );
	 */
	use(middleware) {
		middlewares.push(middleware);
	}
}

export {Client};
export default Client;
