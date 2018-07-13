import Client from '../client';

const LCS_ENDPOINT =
	'https://analytics-gw.liferay.com/api/analyticsgateway/send-analytics-events';

/**
 * Client used to abstract communication with the Analytics LCS Endpoint. It exposes
 * the send and use methods as only valid entry points for sending and modifiying
 * requests.
 * @class
 */
class LCSClient extends Client {
	/**
	 * Constructor
	 * @param {*} uri The Endpoint URI where the data should be sent
	 */
	constructor(uri = LCS_ENDPOINT) {
		super();

		this.uri = uri;
	}
}

export {LCSClient};
export default LCSClient;