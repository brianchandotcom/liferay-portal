import {Config} from 'metal-state';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import templates from './LayoutFinder.soy';

/**
 * KeyBoardEvent enter key
 * @review
 * @type {!string}
 */
const ENTER_KEY = 'Enter';

/**
 * LayoutFinder
 * @review
 */
class LayoutFinder extends Component {

	/**
	 * @inheritDoc
 	 * @review
	 */
	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);

		this._documentClickHandler = dom.on(document, 'click', this._handleDocumentClick);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		if (this._documentClickHandler) {
			this._documentClickHandler.removeListener();

			this._documentClickHandler = null;
		}
	}

	/**
	 * Handles close button click in order to hide the dialog
	 * @private
	 * @review
	 */
	_handleCloseDialogClick() {
		this.layouts = [];
		this.totalCount = 0;

		this._keywords = '';
		this._showFinder = false;
	}

	/**
	 * Handles document click in order to hide the dialog
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleDocumentClick(event) {
		if (
			this._showFinder &&
			this.refs.dialog &&
			!this.refs.dialog.contains(event.target)
		) {
			this._handleCloseDialogClick();
		}
	}

	/**
	 * Handles keyUp event on the filter input to filter the layouts
	 * @param {!KeyboardEvent} event
	 * @private
	 * @preview
	 */
	_handleSearchInputKeyUp(event) {
		if (event.key === ENTER_KEY) {
			event.preventDefault();
			event.stopImmediatePropagation();
		}

		let formData = new FormData();

		const keywords = event.delegateTarget.value;

		if (keywords.length < 3) {
			this.layouts = [];
			this.totalCount = 0;

			this._keywords = keywords;
		}
		else {
			formData.append(`${this.namespace}keywords`, keywords);

			fetch(
				this.findLayoutsURL,
				{
					body: formData,
					credentials: 'include',
					method: 'post'
				}
			).then(
				(response) => {
					let json = {
						layouts: [],
						totalCount: 0
					};

					if (response.ok) {
						json = response.json();
					}

					return json;

				}
			).then(
				(response) => {
					this.layouts = response.layouts;
					this.totalCount = response.totalCount;

					this._keywords = keywords;

					this._viewInPageAdministrationURL = this.administrationPortletURL + `&${this.administrationPortletNamespace}keywords=${keywords}`;
				}
			);

		}
	}

	/**
	 * Toggles layout finder dialog visivility
	 * @private
	 * @review
	 */
	_toggleDialog() {
		this._showFinder = !this._showFinder;
	}

}

/**
 * State definition
 * @review
 * @static
 * @type {!Object}
 */
LayoutFinder.STATE = {

	/**
	 * Namespace for Pages Administration portlet
	 * @default undefined
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {!string}
	 */
	administrationPortletNamespace: Config
		.string()
		.required(),

	/**
	 * URL to access Pages Administration portlet
	 * @default undefined
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {!string}
	 */
	administrationPortletURL: Config
		.string()
		.required(),

	/**
	 * URL to find layouts by keywords
	 * @default undefined
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {!string}
	 */
	findLayoutsURL: Config
		.string()
		.required(),

	/**
	 * Layouts found by current keywords
	 * @default []
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {!Array}
	 */
	layouts: Config
		.arrayOf(
			Config.shapeOf(
				{
					name: Config.string().required(),
					url: Config.string().required()
				}
			)
		)
		.required(),

	/**
	 * Current portlet namespace
	 * @default undefined
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {!string}
	 */
	namespace: Config
		.string()
		.required(),

	/**
	 * Path of the available icons
	 * @default undefined
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {!string}
	 */
	spritemap: Config
		.string()
		.required(),

	/**
	 * Total count of layouts found
	 * @default 0
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {number}
	 */
	totalCount: Config
		.number()
		.value(0),

	/**
	 * Document click handler
	 * @default null
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {Object}
	 */
	_documentClickHandler: Config
		.object()
		.internal()
		.value(null),

	/**
	 * Keywords to find layouts with
	 * @default ''
	 * @instance
	 * @memberOf LayoutFinder
	 * @private
	 * @review
	 * @type {string}
	 */
	_keywords: Config
		.string()
		.value(''),

	/**
	 * Show layout finder dialog
	 * @default false
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {boolean}
	 */
	_showFinder: Config
		.bool()
		.value(false),

	/**
	 * URL to access Pages Administration portlet with keywords parameter
	 * @default undefined
	 * @instance
	 * @memberOf LayoutFinder
	 * @review
	 * @type {string}
	 */
	_viewInPageAdministrationURL: Config
		.string()

};

Soy.register(LayoutFinder, templates);

export {LayoutFinder};
export default LayoutFinder;