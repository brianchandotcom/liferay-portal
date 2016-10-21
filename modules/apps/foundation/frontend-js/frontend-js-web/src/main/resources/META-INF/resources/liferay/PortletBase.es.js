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
	 * @param  {!String} selectors Css selector
	 * @param  {String} root Css selector for find inside a specific node.
	 * If not specified, will search in portlet's rootNode or in the whole document.
	 * @return {NodeList object} List of DOM elements
	 */
	all(selectors, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelectorAll(this.namespaceSelectors_(this.namespace, selectors));
	}

	/**
	 * If the css selector is an id starting with '#',
	 * this method appends the namespace to it.
	 * Selector could also be a class, in this case,
	 * no namespace will be appended.
	 *
	 * @param  {!String} namespace Portlet's namespace
	 * @param  {!String} selectors Css selector
	 * @return {String} namespaced id. '#id1' will return '#_namespace_id1'
	 * @protected
	 */
	namespaceSelectors_(namespace, selectors) {
		return selectors.replace(new RegExp('(#|\\[id=(\\\"|\\\'))(?!' + namespace + ')', 'g'), '$1' + namespace);
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
	 * @param  {!String} selectors Css selector
	 * @param  {String} root Css selector for find inside a specific node.
	 * If not specified, will search in portlet's rootNode or in whole document.
	 * @return {DOM element} Element
	 */
	one(selectors, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelector(this.namespaceSelectors_(this.namespace, selectors));
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