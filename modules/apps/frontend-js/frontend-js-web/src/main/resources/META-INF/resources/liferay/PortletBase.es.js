/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import objectToFormData from './util/form/object_to_form_data.es';

function toElementHelper(elementOrSelector) {
	if (typeof elementOrSelector === 'string') {
		elementOrSelector = document.querySelector(elementOrSelector);
	}

	return elementOrSelector;
}

/**
 * The purpose of this class is to mimic the lifecycles from metal-state.
 * There are several classes that extend PortletBase and expect some of these
 * lifecycles to exist. This pseudo-lifecycle class is meant to provide backwards
 * compatibility for classes extending PortletBase.
 */
class LifeCycles {
	get namespace() {
		return this._STATE_.namespace;
	}
	get portletNamespace() {
		return this._STATE_.portletNamespace;
	}
	get rootNode() {
		return this._STATE_.rootNode;
	}
	set portletNamespace(portletNamespace) {
		this.rootNode = `#p_p_id${portletNamespace}`;

		this._STATE_.portletNamespace = portletNamespace;
	}
	set namespace(namespace) {
		this.rootNode = `#p_p_id${namespace}`;

		this._STATE_.namespace = namespace;
	}
	set rootNode(rootNode) {
		if (typeof rootNode === 'string') {
			rootNode = document.getElementById(
				rootNode[0] === '#' ? rootNode.slice(1) : rootNode
			);
		}

		this._STATE_.rootNode = rootNode;
	}
	constructor(props) {
		this._EVENTS_ = {};
		this._STATE_ = {
			namespace: null,
			portletNamespace: null,
			rootNode: null,
		};

		const {namespace, portletNamespace, rootNode} = props;

		if (namespace) {
			this.namespace = namespace;
		}

		if (portletNamespace) {
			this.portletNamespace = portletNamespace;
		}

		if (rootNode) {
			this.rootNode = rootNode;
		}

		this.created(props);
		this.attached(props);
	}
	dispose() {
		this.disposeInternal();

		this.detached();

		this.disposed();
	}
	attached() {}
	created() {}
	detached() {}
	disposed() {}
	disposeInternal() {}
	emit(eventName, data) {
		const callbacks = this._EVENTS_[eventName];

		if (callbacks && callbacks.length) {
			for (let i = 0; i < callbacks.length; i++) {
				callbacks[i](data);
			}
		}
	}

	on(eventName, callback) {
		if (!this._EVENTS_[eventName]) {
			this._EVENTS_[eventName] = [];
		}

		this._EVENTS_[eventName].push(callback);
	}
}

/**
 * Provides helper functions that simplify querying the DOM for elements related
 * to a specific portlet.
 *
 * @abstract
 */
class PortletBase extends LifeCycles {

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
		root = toElementHelper(root) || this.rootNode || document;

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
	 * @deprecated As of Athanasius (7.3.x), replaced by `Liferay.Util.fetch`.
	 * @param      {!string} url The URL to send the post request to.
	 * @param      {!Object|!FormData} body The request body.
	 * @return     {Promise} A promise.
	 */
	fetch(url, body) {
		const requestBody = this.getRequestBody_(body);

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		return fetch(url, {
			body: requestBody,
			credentials: 'include',
			method: 'POST',
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
	ns(object) {
		return Liferay.Util.ns(this.portletNamespace || this.namespace, object);
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
		root = toElementHelper(root) || this.rootNode || document;

		return root.querySelector(
			this.namespaceSelectors_(
				this.portletNamespace || this.namespace,
				selectors
			)
		);
	}
}

export default PortletBase;
