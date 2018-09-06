import '../Page/PageRenderer.es';
import 'clay-button';
import 'clay-dropdown';
import {Config} from 'metal-state';
import {DragDrop} from 'metal-drag-drop';
import {pageStructure} from '../../util/config.es';
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

		activePage: Config.number().value(0),

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
		 * @default 'Untitled Page'
		 * @instance
		 * @memberof FormRenderer
		 * @type {?string}
		 */

		defaultPageTitle: Config.string().value(Liferay.Language.get('untitled-page')),

		/**
		 * @default grid
		 * @instance
		 * @memberof FormRenderer
		 * @type {?bool}
		 */

		modeRenderer: Config.oneOf(['grid', 'list']).value('grid'),

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
		if (nextState.pages) {
			if (this.editable && !this.dragAndDropDisabled) {
				if (this._dragAndDrop) {
					this._dragAndDrop.disposeInternal();
				}
				this._startDrag();
			}
		}
		return nextState;
	}

	_getPageSettingsItems() {
		const pageSettingsItems = [
			{
				'label': Liferay.Language.get('add-new-page'),
				'settingsItem': 'add-page'
			}
		];

		if (this.pages.length === 1) {
			pageSettingsItems.push(
				{
					'label': Liferay.Language.get('reset-page'),
					'settingsItem': 'reset-page'
				}
			);
		}
		else {
			pageSettingsItems.push(
				{
					'label': Liferay.Language.get('delete-current-page'),
					'settingsItem': 'delete-page'
				}
			);
		}

		return pageSettingsItems;
	}

	prepareStateForRender(state) {
		return {
			...state,
			pageSettingsItems: this._getPageSettingsItems()
		};
	}

	/**
	 * Add a page to the context
	 * @private
	 */

	_addPage() {
		this.emit('pageAdded');
	}

	/*
	 * @param {Object} data
	 * @private
	 */

	_handlePageSettingsClicked({data}) {
		const {settingsItem} = data.item;

		if (settingsItem === 'add-page') {
			this._addPage();
		}
		else if (settingsItem === 'reset-page') {
			this._resetPage();
		}
		else if (settingsItem === 'delete-page') {
			this._deletePage();
		}
	}

	_deletePage() {
		this.emit('pageDeleted', this.activePage);
	}

	_resetPage() {
		this.emit('pageReset');
	}

	_handleChangePage({delegateTarget: {dataset}}) {
		const {pageId} = dataset;

		this.activePage = parseInt(pageId, 10);

		this.emit('activePageUpdated', this.activePage);
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleDragAndDropEnd(data) {
		if (data.target) {
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
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldMoved(event) {
		this.emit('fieldMoved', event);
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleDeleteButtonClicked(data) {
		this.emit('fieldDeleted', data);
	}

	/**
     * @param {!Event} event
     * @private
     */

	_handleDuplicateButtonClicked(data) {
		this.emit('fieldDuplicated', data);
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
}

Soy.register(FormRenderer, templates);

export default FormRenderer;