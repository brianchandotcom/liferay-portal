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
	 * @inheritDoc
	 */
	constructor(config) {
		super(config);

		let namespace = config.namespace;

		this.NS = namespace;
		this.ID = namespace.replace(/^_(.*)_$/, '$1');

		this.rootNode = config.rootNode;
	}

	/**
	 * Returns the portlet's namespace.
	 *
	 * @return {String} namespace
	 * @protected
	 */
	getNS_() {
		return this.NS;
	}

	/**
	 * Returns the root node element.
	 *
	 * @return {DOM element} The root node
	 * @protected
	 */
	getRootNode_() {
		return this.rootNode;
	}

	/**
	 * Converts the given rootNodeId to a DOM element.
	 *
	 * @param {!String} nodeId The node id
	 * @return {DOM element} The dom element with that id
	 * @protected
	 */
	setRootNode_(nodeId) {
		return dom.toElement(nodeId);
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
	formatSelectorNamespace_(ns, selector) {
		return selector.replace(new RegExp('(#|\\[id=(\\\"|\\\'))(?!' + ns + ')', 'g'), '$1' + ns);
	}

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

		return root.querySelectorAll(this.formatSelectorNamespace_(this.NS, selector));
	}

	/**
	 * Appends the portlet's namespace to the
	 * given string.
	 *
	 * @param  {!String} str string
	 * @return {String} namespace + str.
	 */
	ns(str) {
		return Liferay.Util.ns(this.NS, str);
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

		return root.querySelector(this.formatSelectorNamespace_(this.NS, selector));
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
		validator: core.isString,
		getter: 'getNS_'
	},
	/**
	 * The portlet container
	 * @type {String} Id of the DOM Element
	 */
	rootNode: {
		getter: 'getRootNode_',
		setter: 'setRootNode_'
	}
};

export default PortletBase;