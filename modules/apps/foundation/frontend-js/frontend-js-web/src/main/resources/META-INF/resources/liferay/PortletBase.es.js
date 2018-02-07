import core from 'metal';
import dom from 'metal-dom';
import Component from 'metal-component';

/**
 * PortletBase provides some helper functions that simplify querying the DOM
 * for elements related to a specific portlet.
 * @abstract
 * @extends {Component}
 * @review
 */

class PortletBase extends Component {

	/**
	 * Returns a NodeList containing all of the matching Element nodes within
	 * the subtrees of the root object, in tree order. If there are no matching
	 * nodes, the method returns an empty NodeList.
	 * @param {string} selectors List of one or more CSS relative selectors
	 * @param {(string|Element|Document)=} root Root node of the search. If not
	 * specified, the element search will start in the portlet's root node or in
	 * the document
	 * @return {NodeList<Element>} List of Elements matching the selectors in
	 * tree order
	 * @review
	 */

	all(selectors, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelectorAll(
			this.namespaceSelectors_(this.namespace, selectors)
		);
	}

	/**
	 * Namespaces the list of selectors appending the portlet namespace to the
	 * selectors of type id. Selectors of other types remain unaltered.
	 * @param {string} namespace The portlet's namespace
	 * @param {string} selectors List of one or more CSS relative selectors
	 * @protected
	 * @return {string} Namespaced id selectors
	 * @review
	 */

	namespaceSelectors_(namespace, selectors) {
		return selectors.replace(
			new RegExp('(#|\\[id=(\\"|\\\'))(?!' + namespace + ')', 'g'),
			'$1' + namespace
		);
	}

	/**
	 * Appends the portlet's namespace to the given string or object properties.
	 * @param {!Object|string} obj The object or string to be namespaced
	 * @return {Object|string} An object with its properties namespaced using
	 * the portlet namespace or a namespaced string
	 * @review
	 */

	ns(obj) {
		return Liferay.Util.ns(this.namespace, obj);
	}

	/**
	 * Returns the first matching Element node within the subtrees of the
	 * root object. If there is no matching Element, the method returns null.
	 * @param {string} selectors List of one or more CSS relative selectors
	 * @param {(string|Element|Document)=} root Root node of the search. If not
	 * specified, the element search will start in the portlet's root node or in
	 * the document
	 * @return {Element|null} List of First Element matching the selectors or null
	 * @review
	 */

	one(selectors, root) {
		root = dom.toElement(root) || this.rootNode || document;

		return root.querySelector(
			this.namespaceSelectors_(this.namespace, selectors)
		);
	}

	/**
	 * Returns the default portlet root node element. By default, this is the
	 * element with id "p_p_id{portletNamespace}".
	 * @protected
	 * @return {Element} The portlet's default root node element
	 * @review
	 */

	rootNodeValueFn_() {
		return dom.toElement('#p_p_id' + this.namespace);
	}
}

/**
 * State definition.
 * @ignore
 * @review
 * @static
 * @type {!Object}
 */

PortletBase.STATE = {

	/**
	 * Portlet's namespace
	 * @instance
	 * @memberof PortletBase
	 * @review
	 * @type {string}
	 */

	namespace: {
		validator: core.isString
	},

	/**
	 * Portlet's root node element
	 * @instance
	 * @memberof PortletBase
	 * @review
	 * @type {Element}
	 */

	rootNode: {
		setter: dom.toElement,
		valueFn: 'rootNodeValueFn_'
	}
};

export default PortletBase;