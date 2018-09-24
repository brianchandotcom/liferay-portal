import Client from '../client';

/**
 * Client used to abstract communication with the Analytics Asah Endpoint. It exposes
 * the send and use methods as only valid entry points for sending and modifiying
 * requests.
 * @class
 */
class AsahClient extends Client {
	/**
	 * Constructor
	 * @param {*} uri The Endpoint URI where the data should be sent
	 */
	constructor(uri) {
		super();

		this.uri = uri;
	}
}

export {AsahClient};
export default AsahClient;