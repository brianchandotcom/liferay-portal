import Ajax from 'metal-ajax';
import {isUndefined, pickBy} from 'lodash';
import {MultiMap} from 'metal-structs';

/**
 * A wrapper around `metal-ajax` to simplify it's API and also
 * provide sensible default parameters. Any business logic that is
 * specific to our application should probably be in
 * `shared/util/request` instead.
 */
export default function fetch(requestURL, config = {}) {
	const {body, data = {}, headers = {}, method, timeout} = config;

	return Ajax.request(
		requestURL,
		method,
		body,
		MultiMap.fromObject(headers),
		MultiMap.fromObject(pickBy(data, param => !isUndefined(param))),
		timeout,
		false,
		true
	);
}
