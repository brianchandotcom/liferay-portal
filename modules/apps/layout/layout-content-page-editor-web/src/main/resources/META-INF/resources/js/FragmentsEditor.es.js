import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './components/mapping_type/SelectMappingDialog.es';
import './components/mapping_type/SelectMappingTypeDialog.es';
import './components/fragment_entry_link/FragmentEntryLinkList.es';
import './components/sidebar/FragmentsEditorSidebar.es';
import './components/toolbar/FragmentsEditorToolbar.es';
import {CLEAR_ACTIVE_ITEM, CLEAR_HOVERED_ITEM, UPDATE_ACTIVE_ITEM, UPDATE_HOVERED_ITEM} from './actions/actions.es';
import {INITIAL_STATE} from './store/state.es';
import {startListeningWidgetConfigurationChange} from './utils/FragmentsEditorDialogUtils';
import {Store} from './store/store.es';
import templates from './FragmentsEditor.soy';

/**
 * FragmentsEditor
 * @review
 */
class FragmentsEditor extends Component {

	/**
	 * @param {MouseEvent} event
	 * @return {{fragmentsEditorItemId: string|null, fragmentsEditorItemType: string|null}}
	 * @private
	 * @review
	 */
	static _getItemTarget(event) {
		let {fragmentsEditorItemId = null, fragmentsEditorItemType = null} = event.target.dataset || {};

		if (!fragmentsEditorItemId || !fragmentsEditorItemType) {
			const parent = dom.closest(event.target, '[data-fragments-editor-item-id]');

			if (parent) {
				fragmentsEditorItemId = parent.dataset.fragmentsEditorItemId;
				fragmentsEditorItemType = parent.dataset.fragmentsEditorItemType;
			}
		}

		return {
			fragmentsEditorItemId,
			fragmentsEditorItemType
		};
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);
		this._handleDocumentKeyUp = this._handleDocumentKeyUp.bind(this);
		this._handleDocumentMouseOver = this._handleDocumentMouseOver.bind(this);

		document.addEventListener('click', this._handleDocumentClick, true);
		document.addEventListener('keyup', this._handleDocumentKeyUp);
		document.addEventListener('mouseover', this._handleDocumentMouseOver);

		startListeningWidgetConfigurationChange(this.store);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		document.removeEventListener('click', this._handleDocumentClick, true);
		document.removeEventListener('keyup', this._handleDocumentKeyUp);
		document.removeEventListener('mouseover', this._handleDocumentMouseOver);
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentClick(event) {
		this._updateActiveItem(event);
	}

	/**
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentKeyUp(event) {
		this._updateActiveItem(event);
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleDocumentMouseOver(event) {
		const {fragmentsEditorItemId, fragmentsEditorItemType} = FragmentsEditor._getItemTarget(event);

		if (fragmentsEditorItemId && fragmentsEditorItemType && this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: fragmentsEditorItemId,
					hoveredItemType: fragmentsEditorItemType
				}
			);
		}
		else if (this.store) {
			this.store.dispatchAction(
				CLEAR_HOVERED_ITEM
			);
		}
	}

	/**
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_updateActiveItem(event) {
		if (this._activeElement !== document.activeElement) {
			const {
				fragmentsEditorItemId,
				fragmentsEditorItemType
			} = FragmentsEditor._getItemTarget(event);

			if (fragmentsEditorItemId && fragmentsEditorItemType) {
				this.store.dispatchAction(
					UPDATE_ACTIVE_ITEM,
					{
						activeItemId: fragmentsEditorItemId,
						activeItemType: fragmentsEditorItemType
					}
				);
			}
			else if (event.target instanceof HTMLElement &&
				event.target.parentElement !== document.body &&
				!dom.closest(event.target, '.modal')) {

				this.store.dispatchAction(
					CLEAR_ACTIVE_ITEM
				);
			}
		}

		this._activeElement = document.activeElement;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {object}
 */
FragmentsEditor.STATE = Object.assign(
	{

		/**
		 * Previous document active element
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {object}
		 */
		_activeElement: Config.object(),

		/**
		 * Store instance
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {Store}
		 */
		store: Config.instanceOf(Store)

	},
	INITIAL_STATE
);

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;