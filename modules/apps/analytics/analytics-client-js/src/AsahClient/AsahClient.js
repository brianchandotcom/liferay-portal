import Client from '../client';

const ASAH_ENDPOINT = 'https://osbasahcerebropublisher-asahlfr.lfr.io/';

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
	constructor(uri = ASAH_ENDPOINT) {
		super();

		this.uri = uri;
	}
}

export {AsahClient};
export default AsahClient;