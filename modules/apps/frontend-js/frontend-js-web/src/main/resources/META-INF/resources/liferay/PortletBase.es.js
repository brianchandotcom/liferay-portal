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

import core from 'metal';
import Component from 'metal-component';
import dom from 'metal-dom';

import objectToFormData from './util/form/object_to_form_data.es';

/**
 * Provides helper functions that simplify querying the DOM for elements related
 * to a specific portlet.
 *
 * @abstract
 * @extends {Component}
 */
class PortletBase extends Component {
	/**
	 * Returns a Node List containing all the matching element nodes within the
	 * subtrees of the root object, in tree order. If there are no matching
	 * nodes, the method returns an empty Node List.
	 *
	 * @param  {string} selectors A list of one or more CSS relative selectors.
	 * @param  {(string|Element|Document)=} root The root node of the search. If
	 *         not specified, the element search will start in the portlet's
	 *         root node or in the document.
	 * @return {NodeList<Element>} A list of elements matching the selectors, in
	 *         tree order.
	 */
	all(selectors, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelectorAll(
			this.namespaceSelectors_(
				this.portletNamespace || this.namespace,
				selectors
			)
		);
	}

	/**
	 * Performs an HTTP POST request to the given URL with the given body.
	 *
	 * @deprecated since 7.3, use <code>Liferay.Util.fetch</code>.
	 * @param      {!string} url The URL to send the post request to.
	 * @param      {!Object|!FormData} body The request body.
	 * @return     {Promise} A promise.
	 */
	fetch(url, body) {
		const requestBody = this.getRequestBody_(body);

		// eslint-disable-next-line liferay-portal/no-global-fetch
		return fetch(url, {
			body: requestBody,
			credentials: 'include',
			method: 'POST'
		});
	}

	/**
	 * Transforms the given body into a valid <code>FormData</code> element.
	 *
	 * @param  {!FormData|!HTMLFormElement|!Object} body The original data.
	 * @return {FormData} The transformed form data.
	 */
	getRequestBody_(body) {
		let requestBody;

		if (body instanceof FormData) {
			requestBody = body;
		}
		else if (body instanceof HTMLFormElement) {
			requestBody = new FormData(body);
		}
		else if (typeof body === 'object') {
			requestBody = objectToFormData(this.ns(body));
		}
		else {
			requestBody = body;
		}

		return requestBody;
	}

	/**
	 * Namespaces the list of selectors, appending the portlet namespace to the
	 * selectors of type ID. Selectors of other types remain unaltered.
	 *
	 * @param {string} namespace The portlet's namespace.
	 * @param {string} selectors A list of one or more CSS relative selectors.
	 * @protected
	 * @return {string} The namespaced ID selectors.
	 */
	namespaceSelectors_(namespace, selectors) {
		return selectors.replace(
			new RegExp('(#|\\[id=(\\"|\\\'))(?!' + namespace + ')', 'g'),
			'$1' + namespace
		);
	}

	/**
	 * Appends the portlet's namespace to the given string or object properties.
	 *
	 * @param  {!Object|string} obj The object or string to namespace.
	 * @return {Object|string} An object with its properties namespaced, using
	 *         the portlet namespace or a namespaced string.
	 */
	ns(obj) {
		return Liferay.Util.ns(this.portletNamespace || this.namespace, obj);
	}

	/**
	 * Returns the first matching Element node within the subtrees of the
	 * root object. If there is no matching Element, the method returns null.
	 *
	 * @param  {string} selectors A list of one or more CSS relative selectors.
	 * @param  {(string|Element|Document)=} root The root node of the search. If
	 *         not specified, the element search will start in the portlet's
	 *         root node or in the document.
	 * @return {Element|null} A list of the first element matching the selectors
	 *         or <code>null</code>.
	 */
	one(selectors, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelector(
			this.namespaceSelectors_(
				this.portletNamespace || this.namespace,
				selectors
			)
		);
	}

	/**
	 * Returns the default portlet root node element. By default, this is the
	 * element with ID <code>p_p_id{portletNamespace}</code>.
	 *
	 * @protected
	 * @return {Element} The portlet's default root node element.
	 */
	rootNodeValueFn_() {
		return dom.toElement(
			`#p_p_id${this.portletNamespace || this.namespace}`
		);
	}
}

/**
 * State definition.
 *
 * @ignore
 * @static
 * @type {!Object}
 */
PortletBase.STATE = {
	/**
	 * Portlet's namespace.
	 *
	 * @deprecated since 7.1
	 * @instance
	 * @memberof PortletBase
	 * @type {string}
	 */
	namespace: {
		validator: core.isString
	},

	/**
	 * Portlet's namespace.
	 *
	 * @instance
	 * @memberof PortletBase
	 * @type {string}
	 */
	portletNamespace: {
		validator: core.isString
	},

	/**
	 * Portlet's root node element.
	 *
	 * @instance
	 * @memberof PortletBase
	 * @type {Element}
	 */
	rootNode: {
		setter: dom.toElement,
		valueFn: 'rootNodeValueFn_'
	}
};

export default PortletBase;
