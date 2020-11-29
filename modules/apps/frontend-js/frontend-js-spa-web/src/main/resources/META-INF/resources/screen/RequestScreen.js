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

import {fetch} from 'frontend-js-web';

import errors from '../errors/errors';
import globals from '../globals/globals';
import {getUrlPath} from '../util/utils';
import Screen from './Screen';

class RequestScreen extends Screen {

	/**
	 * Request screen abstract class to perform io operations on descendant
	 * screens.
	 */
	constructor() {
		super();

		/**
		 * @inheritDoc
		 * @default true
		 */
		this.cacheable = true;

		/**
		 * Holds default http headers to set on request.
		 * @type {?Object=}
		 * @default {
		 *   'X-PJAX': 'true',
		 *   'X-Requested-With': 'XMLHttpRequest'
		 * }
		 * @protected
		 */
		this.httpHeaders = {
			'X-PJAX': 'true',
			'X-Requested-With': 'XMLHttpRequest',
		};

		/**
		 * Holds default http method to perform the request.
		 * @type {!string}
		 * @default RequestScreen.GET
		 * @protected
		 */
		this.httpMethod = RequestScreen.GET;

		/**
		 * Holds the XHR object responsible for the request.
		 * @type {XMLHttpRequest}
		 * @default null
		 * @protected
		 */
		this.request = null;

		/**
		 * Holds the request timeout in milliseconds.
		 * @type {!number}
		 * @default 30000
		 * @protected
		 */
		this.timeout = 30000;
	}

	/**
	 * Asserts that response status code is valid.
	 * @param {number} status
	 * @protected
	 */
	assertValidResponseStatusCode(status) {
		if (!this.isValidResponseStatusCode(status)) {
			var error = new Error(errors.INVALID_STATUS);
			error.invalidStatus = true;
			error.statusCode = status;
			throw error;
		}
	}

	/**
	 * @inheritDoc
	 */
	beforeUpdateHistoryPath(path) {
		var redirectPath = this.getRequestPath();
		if (redirectPath && redirectPath !== path) {
			return redirectPath;
		}

		return path;
	}

	/**
	 * @inheritDoc
	 */
	beforeUpdateHistoryState(state) {

		// If state is ours and navigate to post-without-redirect-get set
		// history state to null, that way Senna will reload the page on
		// popstate since it cannot predict post data.

		if (state.senna && state.form && state.redirectPath === state.path) {
			return null;
		}

		return state;
	}

	/**
	 * Formats load path before invoking ajax call.
	 * @param {string} path
	 * @return {string} Formatted path;
	 * @protected
	 */
	formatLoadPath(path) {
		var uri = new URL(path, window.location.origin);

		uri.hostname = window.location.hostname;
		uri.protocol = window.location.protocol;

		if (window.location.port) {
			uri.port = window.location.port;
		}

		return uri.toString();
	}

	/**
	 * Gets the http headers.
	 * @return {?Object=}
	 */
	getHttpHeaders() {
		return this.httpHeaders;
	}

	/**
	 * Gets the http method.
	 * @return {!string}
	 */
	getHttpMethod() {
		return this.httpMethod;
	}

	/**
	 * Gets request path.
	 * @return {string=}
	 */
	getRequestPath() {
		var request = this.getRequest();

		if (request) {
			var requestPath = request.url;

			var response = this.getResponse();

			if (response) {
				var responseUrl = response.url;

				if (responseUrl) {
					requestPath = responseUrl;
				}
			}

			return getUrlPath(requestPath);
		}

		return null;
	}

	/**
	 * Gets the request object.
	 * @return {?Object}
	 */
	getResponse() {
		return this.response;
	}

	/**
	 * Gets the request object.
	 * @return {?Object}
	 */
	getRequest() {
		return this.request;
	}

	/**
	 * Gets the request timeout.
	 * @return {!number}
	 */
	getTimeout() {
		return this.timeout;
	}

	/**
	 * Checks if response succeeded. Any status code 2xx or 3xx is considered
	 * valid.
	 * @param {number} statusCode
	 */
	isValidResponseStatusCode(statusCode) {
		return statusCode >= 200 && statusCode <= 399;
	}

	/**
	 * Returns the form data
	 * This method can be extended in order to have a custom implementation of the form params
	 * @param {!Element} formElement
	 * @param {!Element} submittedButtonElement
	 * @return {!FormData}
	 */
	getFormData(formElement, submittedButtonElement) {
		const formData = new FormData(formElement);
		this.maybeAppendSubmitButtonValue_(formData, submittedButtonElement);

		return formData;
	}

	/**
	 * @inheritDoc
	 */
	load(path) {
		const cache = this.getCache();
		if (cache) {
			return Promise.resolve(cache);
		}
		let body = null;
		let httpMethod = this.httpMethod;
		const requestHeaders = {'X-PJAX': 'true', ...this.httpHeaders};
		if (globals.capturedFormElement) {
			body = this.getFormData(
				globals.capturedFormElement,
				globals.capturedFormButtonElement
			);
			httpMethod = RequestScreen.POST;
		}

		const url = this.formatLoadPath(path);

		this.setRequest({
			method: httpMethod,
			requestBody: body,
			requestHeaders,
			url,
		});

		return Promise.race([
			fetch(url, {
				body,
				headers: requestHeaders,
				method: httpMethod,
				mode: 'cors',
				redirect: 'follow',
				referrer: 'client',
			})
				.then((resp) => {
					this.assertValidResponseStatusCode(resp.status);

					this.setResponse(resp);

					return resp.clone().text();
				})
				.then((text) => {
					if (
						httpMethod === RequestScreen.GET &&
						this.isCacheable()
					) {
						this.addCache(text);
					}

					return text;
				}),
			new Promise((_, reject) => {
				setTimeout(
					() => reject(new Error(errors.REQUEST_TIMEOUT)),
					this.timeout
				);
			}),
		]).catch((reason) => {
			switch (reason.message) {
				case errors.REQUEST_TIMEOUT:
					reason.timeout = true;
					break;
				case errors.REQUEST_PREMATURE_TERMINATION:
					reason.requestError = true;
					reason.requestPrematureTermination = true;
					break;
				case errors.REQUEST_ERROR:
				default:
					reason.requestError = true;
					break;
			}
			throw reason;
		});
	}

	/**
	 * Adds aditional data to the body of the request in case a submit button
	 * is captured during form submission.
	 * @param {!FormData} body The FormData containing the request body.
	 * @param {!Element} submittedButtonElement
	 * @protected
	 */
	maybeAppendSubmitButtonValue_(formData, submittedButtonElement) {
		if (submittedButtonElement && submittedButtonElement.name) {
			formData.append(
				submittedButtonElement.name,
				submittedButtonElement.value
			);
		}
	}

	/**
	 * Sets the http headers.
	 * @param {?Object=} httpHeaders
	 */
	setHttpHeaders(httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	/**
	 * Sets the http method.
	 * @param {!string} httpMethod
	 */
	setHttpMethod(httpMethod) {
		this.httpMethod = httpMethod.toLowerCase();
	}

	/**
	 * Sets the request object.
	 * @param {?Object} request
	 */
	setRequest(request) {
		this.request = request;
	}

	/**
	 * Sets the request object.
	 * @param {?Object} request
	 */
	setResponse(response) {
		this.response = response;
	}

	/**
	 * Sets the request timeout in milliseconds.
	 * @param {!number} timeout
	 */
	setTimeout(timeout) {
		this.timeout = timeout;
	}
}

/**
 * Holds value for method get.
 * @type {string}
 * @default 'get'
 * @static
 */
RequestScreen.GET = 'get';

/**
 * Holds value for method post.
 * @type {string}
 * @default 'post'
 * @static
 */
RequestScreen.POST = 'post';

export default RequestScreen;
