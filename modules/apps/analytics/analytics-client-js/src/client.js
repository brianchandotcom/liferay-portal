import hash from './utils/hash';
import middlewares from './middlewares/defaults';

/**
 * Client used to abstract communication with the Analytics Endpoint. It exposes
 * the send and use methods as only valid entry points for sending and modifiying
 * requests.
 * @class
 */
class Client {
	_getContextEvents(analytics, context) {
		return analytics.events.filter(event => {
			return (
				event.applicationId &&
				event.contextHash ===
					hash(context, {unorderedObjects: false}) &&
				event.eventId
			);
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
			mode: 'cors',
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

		return {
			analyticsKey: analytics.config.analyticsKey,
			context,
			events,
			protocolVersion: '1.0',
			userId,
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