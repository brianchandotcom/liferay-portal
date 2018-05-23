import middlewares from '../middlewares/defaults';

const ASAH_ENDPOINT = 'https://osbasahcerebropublisher-asahlfr.lfr.io/';

/**
 * Client used to abstract communication with the Analytics Asah Endpoint. It exposes
 * the send and use methods as only valid entry points for sending and modifiying
 * requests.
 * @class
 */
class AsahClient {
	/**
	 * Constructor
	 * @param {*} uri The Endpoint URI where the data should be sent
	 */
	constructor(uri = ASAH_ENDPOINT) {
		this.uri = uri;
	}

	/**
	 * Returns a Request object with all data from the analytics instance
	 * includin the batched event objects
	 * @param {object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @protected
	 * @return {object} Request
	 */
	_getAsahRequest(analytics, userId) {
		const body = JSON.stringify(this._getRequestBody(analytics, userId));
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
	 * predefined request specification of the Asah endpoint
	 * @param {object} analytics The Analytics instance from which the data is extracted
	 * @param {string} userId The userId string representation
	 * @protected
	 * @return {object} Body of the request to be sent to Asah
	 */
	_getRequestBody(analytics, userId) {
		const requestBody = {
			analyticsKey: analytics.config.analyticsKey,
			context: {},
			events: analytics.events.filter(event => {
				return event.applicationId && event.eventId;
			}),
			protocolVersion: '1.0',
			userId,
		};

		return middlewares.reduce(
			(request, middleware) => middleware(request, analytics),
			requestBody
		);
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
		const request = this._getAsahRequest(analytics, userId);

		return fetch(this.uri, request)
			.then(this._validateResponse)
			.catch(() => Promise.resolve());
	}

	/**
	 * Adds a middleware function to provide ability to transform the request
	 * that is sent to Asah endpoint
	 * @param {Function} middleware A function to alter request
	 * @example
	 * AsahClient.use(
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

export {AsahClient};
export default AsahClient;