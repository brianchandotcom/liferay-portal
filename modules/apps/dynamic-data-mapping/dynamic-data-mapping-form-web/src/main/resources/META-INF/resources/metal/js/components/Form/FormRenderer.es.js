import '../Page/PageRenderer.es';
import 'clay-button';
import 'clay-dropdown';
import {Config} from 'metal-state';
import {DragDrop} from 'metal-drag-drop';
import {pageStructure} from '../../util/config.es';
import {setLocalizedValue} from '../../util/i18n.es';
import Component from 'metal-component';
import FormSupport from './FormSupport.es';
import Soy from 'metal-soy';
import templates from './FormRenderer.soy.js';

/**
 * FormRenderer.
 * @extends Component
 */

class FormRenderer extends Component {
	static STATE = {

		/**
		 * @default
		 * @instance
		 * @memberof FormRenderer
		 * @type {?number}
		 */

		activePage: Config.number().internal().value(0),

		/**
		 * @default false
		 * @instance
		 * @memberof FormRenderer
		 * @type {?bool}
		 */

		dragAndDropDisabled: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof FormRenderer
		 * @type {?bool}
		 */

		editable: Config.bool().value(false),

		/**
		 * @default grid
		 * @instance
		 * @memberof FormRenderer
		 * @type {?bool}
		 */

		modeRenderer: Config.oneOf(['grid', 'list']).value('grid'),

		/**
		 * @default array
		 * @memberof FormRenderer
		 * @type {?array<object>}
		 */

		pageSettingsItem: Config.array().value(
			[
				{
					'label': Liferay.Language.get('add-page'),
					'settingsItem': 'add-page'
				}
			]
		),

		/**
		 * @default []
		 * @instance
		 * @memberof FormRenderer
		 * @type {?array<object>}
		 */

		pages: Config.arrayOf(pageStructure).value([]),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormRenderer
		 * @type {!string}
		 */

		spritemap: Config.string().required()
	};

	/**
	 * @inheritDoc
	 */

	attached() {
		if (this.editable && !this.dragAndDropDisabled) {
			this._startDrag();
		}
	}

	/**
	 * @inheritDoc
	 */

	willReceiveState(nextState) {
		if (
			typeof nextState.pages !== 'undefined' &&
			nextState.pages.newVal.length &&
			this.editable &&
			!this.dragAndDropDisabled
		) {
			this._dragAndDrop.disposeInternal();
			this._startDrag();
		}
	}

	/**
	 * Add a page to the context
	 * @private
	 */

	_addPage() {
		const activePage = this.activePage;
		const newPageIndex = this.pages.length;

		this.pages[newPageIndex] = this.createNewPage();
		this.pages[activePage].enabled = false;

		this.activePage = newPageIndex;

		this.emit('addPage', this.pages);
	}

	/**
	 * @param {Object} data
	 * @private
	 */

	_handleSettingsPageClicked({data}) {
		const {settingsItem} = data.item;

		if (settingsItem == 'add-page') {
			this._addPage();
		}
	}

	_handleChangePage({delegateTarget: {dataset}}) {
		const {pageId} = dataset;

		this.activePage = parseInt(pageId, 10);
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleDragAndDropEnd(data) {
		if (!data.target) {
			return;
		}

		const sourceIndex = FormSupport.getIndexes(
			data.source.parentElement.parentElement
		);
		const targetIndex = FormSupport.getIndexes(data.target.parentElement);

		data.source.innerHTML = '';

		this._handleFieldMoved(
			{
				data,
				source: sourceIndex,
				target: targetIndex
			}
		);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldMoved({data, target, source}) {
		this.emit(
			'fieldMoved',
			{
				data,
				source,
				target
			}
		);
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleDeleteButtonClicked(data) {
		this.emit('deleteButtonClicked', data);
	}

	/**
     * @param {!Event} event
     * @private
     */

	_handleDuplicateButtonClicked(data) {
		this.emit('duplicateButtonClicked', data);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleUpdatePage({page, pageId}) {
		this.pages[pageId] = page;

		this.emit('pagesUpdated', this.pages);
	}

	/**
	 * @private
	 */

	_startDrag() {
		this._dragAndDrop = new DragDrop(
			{
				sources: '.ddm-drag',
				targets: '.ddm-target'
			}
		);

		this._dragAndDrop.on(
			DragDrop.Events.END,
			this._handleDragAndDropEnd.bind(this)
		);
	}

	/**
	 * Return a new page object
	 * @private
	 * @returns {object}
	 */

	createNewPage() {
		const languageId = Liferay.ThemeDisplay.getLanguageId();
		const page = {
			description: '',
			enabled: true,
			rows: [],
			showRequiredFieldsWarning: true,
			title: ''
		};

		setLocalizedValue(page, languageId, 'title', '');
		setLocalizedValue(page, languageId, 'description', '');

		return page;
	}
}

Soy.register(FormRenderer, templates);

export default FormRenderer;