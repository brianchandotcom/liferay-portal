import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './PageLayout.soy.js';

class PageLayout extends Component {
	static STATE = {

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {?string}
		 */
		description: Config.string(),

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {?string}
		 */
		descriptionPlaceholder: Config.string()
			.value(Liferay.Language.get('add-a-short-description-for-this-page')),

		/**
		 * @default 1
		 * @instance
		 * @memberof FormPage
		 * @type {?number}
		 */
		pageIndex: Config.number().value(0),

		/**
		 * @default undefined
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {!string}
		 */ 

		spritemap: Config.string().required(),

		/**
		 * @default 1
		 * @instance
		 * @memberof FormPage
		 * @type {?number}
		 */
		total: Config.number().value(1),

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {?string}
		 */
		title: Config.string(),

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {?string}
		 */
		titlePlaceholder: Config.string(),

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {function}
		 */
		_handleChangePageTitle: Config.func().required(),

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {function}
		 */
		_handleChangePageDescription: Config.func().required()
	}

	willAttach() {
		this.titlePlaceholder = this._getTitlePlaceholder();
	}

	willReceiveState(){
		this.titlePlaceholder = this._getTitlePlaceholder();
	}

	/**
	 * @param {number} pageIndex
	 * @private
	 */
	_getTitlePlaceholder() {
		return Liferay.Language.get(`untitled-page-${this.pageIndex + 1}-of-${this.total}`);
	}
}

Soy.register(PageLayout, templates);

export default PageLayout;