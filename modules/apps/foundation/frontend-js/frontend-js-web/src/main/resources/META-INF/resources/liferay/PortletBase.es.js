import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import State from 'metal-state/src/State';

/**
 * PortletBase
 *
 * This class defines some helper functions that abstract
 * you to work with namespace
 */
class PortletBase extends State {
	/**
	 * Get all elements in the root node that matches
	 * the specified css selector.
	 *
	 * @param  {!String} selector Css selector
	 * @param  {String} root Css selector for find inside a specific node.
	 * If not specified, will search in portlet's rootNode or in the whole document.
	 * @return {NodeList object} List of DOM elements
	 */
	all(selector, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelectorAll(this.namespaceSelector_(this.namespace, selector));
	}

	/**
	 * If the css selector is an id starting with '#',
	 * this method appends the namespace to it.
	 * Selector could also be a class, in this case,
	 * no namespace will be appended.
	 *
	 * @param  {!String} ns Portlet's namespace
	 * @param  {!String} selector Css selector
	 * @return {String} namespaced id. '#id1' will return '#_namespace_id1'
	 * @protected
	 */
	namespaceSelector_(ns, selector) {
		return selector.replace(new RegExp('(#|\\[id=(\\\"|\\\'))(?!' + ns + ')', 'g'), '$1' + ns);
	}

	/**
	 * Appends the portlet's namespace to the
	 * given string.
	 *
	 * @param  {!String} str string
	 * @return {String} namespace + str.
	 */
	ns(str) {
		return Liferay.Util.ns(this.namespace, str);
	}

	/**
	 * Get the first element in the root node
	 * that matches the specified css selector.
	 *
	 * @param  {!String} selector Css selector
	 * @param  {String} root Css selector for find inside a specific node.
	 * If not specified, will search in portlet's rootNode or in whole document.
	 * @return {DOM element} Element
	 */
	one(selector, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelector(this.namespaceSelector_(this.namespace, selector));
	}
}

/**
 * PortletBase State definition.
 * @type {!Object}
 * @static
 */
PortletBase.STATE = {
	/**
	 * The portlet namespace
	 * @type {String}
	 */
	namespace: {
		validator: core.isString
	},

	/**
	 * The portlet container
	 * @type {String} Id of the DOM Element
	 */
	rootNode: {
		setter: dom.toElement
	}
};

export default PortletBase;