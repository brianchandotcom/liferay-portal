import core from 'metal';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';

import PortletBase from './PortletBase.es';

/**
 * DynamicInlineScroll appends list item elements to dropdown menus with inline-scrollers
 * on scroll events to improve page load performance.
 *
 * @extends {Component}
 * @review
 */

class DynamicInlineScroll extends PortletBase {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	attached() {
		let {rootNode} = this;

		rootNode = rootNode || document;

		this.eventHandler_.add(
			dom.delegate(
				rootNode,
				'scroll',
				'ul.pagination ul.inline-scroller',
				this.onScroll_.bind(this)
			)
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	detached() {
		super.detached();

		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Dynamically adds list item elements to the dropdown menu.
	 * @param {element} listElement Dom node of the list element.
	 * @param {number} pageIndex Index of page with an inline-scroller.
	 * @protected
	 * @review
	 */

	addListItem_(listElement, pageIndex) {
		const listItem = document.createElement('li');

		dom.append(
			listItem,
			`<a href="${this.getHREF_(pageIndex)}">${pageIndex}</a>`
		);

		pageIndex++;

		listElement.appendChild(listItem);
		listElement.setAttribute('data-page-index', pageIndex);

		this.eventHandler_.add(
			dom.on(listItem, 'click', this.handleListItemClick_.bind(this))
		);
	}

	/**
	 * Returns the href attribute value for each
	 * @param {number} pageIndex Index of page
	 * @protected
	 * @return {string} String value of href
	 * @review
	 */

	getHREF_(pageIndex) {
		const {curParam, formName, jsCall, namespace, url, urlAnchor} = this;

		let href = `javascript:document.${formName}.${namespace}${curParam}.value = "${
			pageIndex
		}; ${jsCall}`;

		if (this.url !== null) {
			href = `${url}${namespace}${curParam}=${pageIndex}${urlAnchor}`;
		}

		return href;
	}

	/**
	 * Returns the value of the parameter passed in as a Number object
	 * @param {string|!Object} val The string or Object to be converted to a number
	 * @protected
	 * @return {number} Number value of parameter
	 * @review
	 */

	getNumber_(val) {
		return Number(val);
	}

	/**
	 * Handles click event of dynmaically added list item and submits search
	 * container form.
	 * @param {Event} event The click event of the dynamically added list item.
	 * @protected
	 * @review
	 */

	handleListItemClick_(event) {
		if (this.forcePost) {
			event.preventDefault();

			const {curParam, namespace, randomNamespace} = this;

			const form = document.getElementById(randomNamespace + namespace + 'pageIteratorFm');

			form.elements[namespace + curParam].value = event.currentTarget.textContent;

			form.submit();
		}
	}

	/**
	 * An event triggered when a dropdown menu with an inline-scroller is scrolled.
	 * Dynamically adds list item elements to the dropdown menu as it is scrolled down.
	 * @param {Event} event The scroll event triggered by scrolling a dropdown menu
	 * with an inline-scroller
	 * @protected
	 * @review
	 */

	onScroll_(event) {
		const {cur, initialPages, pages} = this;
		const {target} = event;

		let pageIndex = this.getNumber_(target.getAttribute('data-page-index'));
		let pageIndexMax = this.getNumber_(target.getAttribute('data-max-index'));

		if (pageIndex === 0) {
			let pageIndexCurrent = this.getNumber_(
				target.getAttribute('data-current-index')
			);

			if (pageIndexCurrent === 0) {
				pageIndex = initialPages;
			}
			else {
				pageIndex = pageIndexCurrent + initialPages;
			}
		}

		if (pageIndexMax === 0) {
			pageIndexMax = pages;
		}

		if (
			cur <= pages &&
			pageIndex < pageIndexMax &&
			target.getAttribute('scrollTop') >=
				target.getAttribute('scrollHeight') - 300
		) {
			this.addListItem_(target, pageIndex);
		}
	}
}

/**
 * State definition.
 * @ignore
 * @review
 * @static
 * @type {!Object}
 */

DynamicInlineScroll.STATE = {

	/**
	 * Current page
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	cur: {
		setter: 'getNumber_',
		validator: core.isString
	},

	/**
	 * URL parameter for current page
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	curParam: {
		validator: core.isString
	},

	/**
	 * Forces a form post when a page on the dropdown menu is clicked
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {boolean}
	 */

	forcePost: {
		validator: core.isBoolean
	},

	/**
	 * Form name
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	formName: {
		validator: core.isString
	},

	/**
	 * Number of pages loaded to the inline-scroll dropdown menu for the first
	 * page load
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	initialPages: {
		setter: 'getNumber_',
		validator: core.isString
	},

	/**
	 * JavaScript call
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	jsCall: {
		validator: core.isString
	},

	/**
	 * Namespace
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	namespace: {
		validator: core.isString
	},

	/**
	 * Total number of pages
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	pages: {
		setter: 'getNumber_',
		validator: core.isString
	},

	/**
	 * Random namespace
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	randomNamespace: {
		validator: core.isString
	},

	/**
	 * URL
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	url: {
		validator: core.isString
	},

	/**
	 * URL anchor
	 * @instance
	 * @memberof DynamicInlineScroll
	 * @review
	 * @type {string}
	 */

	urlAnchor: {
		validator: core.isString
	}
};

export default DynamicInlineScroll;