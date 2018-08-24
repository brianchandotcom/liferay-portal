import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './PageRenderer.soy.js';
import {pageStructure} from '../../util/config.es';
import {setLocalizedValue} from '../../util/internationalization.es';
import FormSupport from '../Form/FormSupport.es'; 
import {dom} from 'metal-dom';
import 'clay-button';

class PageRenderer extends Component {
	static STATE = {

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

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

		pageId: Config.number().value(0),

		/**
		 * @default []
		 * @instance
		 * @memberof FormRenderer
		 * @type {?array<object>}
		 */

		page: pageStructure,

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormRenderer
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

		titlePlaceholder: Config.string()
	}

	willAttach() {
		this.titlePlaceholder = this._getTitlePlaceholder();
	}

	willReceiveState() {
		this.titlePlaceholder = this._getTitlePlaceholder();
	}
 
	/**
	 * @param {number} pageId
	 * @private
	 */

	_getTitlePlaceholder() {
		return Liferay.Language.get(`untitled-page-${this.pageId + 1}-of-${this.total}`);
	}

	/**
	 * @param {Object} event
	 * @param {String} pageProperty
	 * @private
	 */

	_changePageForm({delegateTarget}, pageProperty) {
		const {value} = delegateTarget;
		
		const languageId = Liferay.ThemeDisplay.getLanguageId();
		const page = {...this.page};

		setLocalizedValue(page, languageId, pageProperty, value);

		return page;
	}

	/**
	 * @param {!Event} event
	 * @param {!String} mode
	 * @private
	 */

	_emitFieldClicked(event, mode) {
		const index = FormSupport.getIndexes(event);

		this.emit(
			'fieldClicked',
			{
				...index,
				mode
			}
		);
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleFieldChanged(data) {
		this.emit('fieldEdited', data);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleChangePageTitle(event) {
		const page = this._changePageForm(event, 'title');
		const { delegateTarget: { dataset } } = event;
		let { pageId } = dataset;

		pageId = parseInt(pageId)

		this.emit('updatePage', {
			pageId,
			page,
		});
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleChangePageDescription(event) {
		const page = this._changePageForm(event, 'description');
		const { delegateTarget: { dataset }} = event;
		let { pageId } = dataset;

		pageId = parseInt(pageId)

		this.emit('updatePage', {
			pageId,
			page,
		});
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleSelectFieldFocused(event) {
		this._emitFieldClicked(
			event.delegateTarget.parentElement.parentElement,
			'edit'
		);
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleDeleteButtonClicked(event) {
		const index = FormSupport.getIndexes(
			dom.closest(event.target, '.col-ddm')
		);

		this.emit(
			'deleteButtonClicked',
			{...index}
		);
	}

	/**
     * @param {!Event} event
     * @private
     */

	_handleDuplicateButtonClicked(event) {
		const index = FormSupport.getIndexes(
			dom.closest(event.target, '.col-ddm')
		);

		this.emit(
			'duplicateButtonClicked',
			{
				...index
			}
		);
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleOnClickResize() {}
}

Soy.register(PageRenderer, templates);

export default PageRenderer;