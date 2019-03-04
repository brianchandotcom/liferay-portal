import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import '../floating_toolbar/text_properties/FloatingToolbarTextPropertiesPanel.es';
import './FragmentEditableFieldTooltip.es';

import {CLEAR_ACTIVE_ITEM, OPEN_MAPPING_FIELDS_DIALOG, UPDATE_ACTIVE_ITEM, UPDATE_EDITABLE_VALUE, UPDATE_HOVERED_ITEM, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_TRANSLATION_STATUS} from '../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {setIn, shouldClearFocus} from '../../utils/FragmentsEditorUpdateUtils.es';
import {shouldUpdateOnChangeProperties, shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import FragmentProcessors from '../fragment_processors/FragmentProcessors.es';
import templates from './FragmentEditableField.soy';

/**
 * Default key used for translated values when there is no languageId
 * @review
 * @type {!string}
 */
const DEFAULT_LANGUAGE_ID_KEY = 'defaultValue';

const FLOATING_TOOLBAR_EDIT_PANEL_ID = 'edit';

const FLOATING_TOOLBAR_MAP_PANEL_ID = 'map';

const FLOATING_TOOLBAR_TEXT_PROPERTIES_PANEL_ID = 'text_properties';

/**
 * List of available panels
 * @review
 * @type {object[]}
 */
const FLOATING_TOOLBAR_PANELS = [
	{
		icon: 'pencil',
		panelId: FLOATING_TOOLBAR_EDIT_PANEL_ID,
		title: Liferay.Language.get('edit')
	},
	{
		icon: 'bolt',
		panelId: FLOATING_TOOLBAR_MAP_PANEL_ID,
		title: Liferay.Language.get('map')
	}
];

/**
 * List of available panels after mapping
 * @review
 * @type {object[]}
 */
const FLOATING_TOOLBAR_PANELS_MAPPED = [
	{
		icon: 'format',
		panelId: FLOATING_TOOLBAR_TEXT_PROPERTIES_PANEL_ID,
		title: Liferay.Language.get('text-properties')
	},
	{
		icon: 'bolt',
		panelId: FLOATING_TOOLBAR_MAP_PANEL_ID,
		title: Liferay.Language.get('map')
	}
];

/**
 * Delay to save changes of an editable field
 * @review
 * @type {!number}
 */
const SAVE_CHANGES_DELAY = 1500;

/**
 * FragmentEditableField
 */
class FragmentEditableField extends Component {

	/**
	 * Gets a translated image content of a given HTML replacing
	 * the url inside the HTML content with the translated one
	 * @param {string} content Original HTML content
	 * @param {string} translatedValue Translated image URL
	 * @private
	 * @return {string} Translated HTML
	 * @review
	 */
	static _getImageContent(content, translatedValue) {
		const wrapper = document.createElement('div');

		wrapper.innerHTML = content;

		const image = wrapper.querySelector('img');

		if (image) {
			image.src = translatedValue;
		}

		return wrapper.innerHTML;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleBeforeNavigate = this._handleBeforeNavigate.bind(this);
		this._handleBeforeUnload = this._handleBeforeUnload.bind(this);
		this._handleEditableChanged = this._handleEditableChanged.bind(this);
		this._handleEditableDestroyed = this._handleEditableDestroyed.bind(this);
		this._handleFloatingToolbarPanelSelected = this._handleFloatingToolbarPanelSelected.bind(this);

		this._beforeNavigateHandler = Liferay.on(
			'beforeNavigate',
			this._handleBeforeNavigate
		);

		window.addEventListener('beforeunload', this._handleBeforeUnload);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		clearTimeout(this._saveChangesTimeout);

		this._destroyProcessors();
		this._disposeFloatingToolbar();

		this._beforeNavigateHandler.detach();

		window.removeEventListener('beforeunload', this._handleBeforeUnload);
	}

	/**
	 * @inheritDoc
	 * @param {!object} state
	 * @returns {object}
	 */
	prepareStateForRender(state) {
		const segmentedValue = this.editableValues[this.segmentId] ||
			this.editableValues[this.defaultSegmentId] ||
			this.editableValues;

		const translatedValue = segmentedValue[this.languageId] ||
			segmentedValue[this.defaultLanguageId];

		const value = this.editableValues.mappedField ?
			this.editableValues.defaultValue :
			(translatedValue || this.editableValues.defaultValue);

		const content = Soy.toIncDom(
			this.type === 'image' ?
				FragmentEditableField._getImageContent(this.content, value) :
				value
		);

		let nextState = state;

		const fragmentEntryLinkEditableId = `${this.fragmentEntryLinkId}-${this.editableId}`;
		const mapped = Boolean(this.editableValues.mappedField);
		const translated = !mapped && Boolean(segmentedValue[this.languageId]);

		nextState = setIn(nextState, ['_mapped'], mapped);
		nextState = setIn(nextState, ['_translated'], translated);
		nextState = setIn(nextState, ['content'], content);
		nextState = setIn(nextState, ['fragmentEntryLinkEditableId'], fragmentEntryLinkEditableId);
		nextState = setIn(nextState, ['itemTypes'], FRAGMENTS_EDITOR_ITEM_TYPES);

		return nextState;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (
			(`${this.fragmentEntryLinkId}-${this.editableId}` === this.activeItemId) &&
			(this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable)
		) {
			this._createFloatingToolbar();
		}
		else {
			this._disposeFloatingToolbar();
		}
	}

	/**
	 * @inheritDoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return this._editing ?
			shouldUpdateOnChangeProperties(changes, ['languageId', 'segmentId']) :
			shouldUpdatePureComponent(changes);
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		const config = {
			anchorElement: this.element,
			classes: this.editableValues.mappedField ? 'fragments-editor__floating-toolbar--mapped-field' : '',
			events: {
				panelSelected: this._handleFloatingToolbarPanelSelected
			},
			item: {
				editableValues: this.editableValues,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			},
			itemId: this.editableId,
			panels: this.editableValues.mappedField ? FLOATING_TOOLBAR_PANELS_MAPPED : FLOATING_TOOLBAR_PANELS,
			portalElement: document.body,
			store: this.store
		};

		if (this._floatingToolbar) {
			this._floatingToolbar.setState(config);
		}
		else {
			this._floatingToolbar = new FloatingToolbar(config);
		}
	}

	/**
	 * Call destroy method on all processors
	 * @private
	 * @review
	 */
	_destroyProcessors() {
		Object.values(FragmentProcessors).forEach(
			fragmentProcessor => fragmentProcessor.destroy()
		);
	}

	/**
	 * Disposes an existing instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_disposeFloatingToolbar() {
		if (this._floatingToolbar) {
			this._floatingToolbar.dispose();

			this._floatingToolbar = null;
		}
	}

	/**
	 * Enables the corresponding editor
	 * @private
	 * @review
	 */
	_enableEditor() {
		const {init} = FragmentProcessors[this.type] ||
			FragmentProcessors.fallback;

		init(
			this.refs.editable,
			this.fragmentEntryLinkId,
			this.portletNamespace,
			this.processorsOptions,
			this._handleEditableChanged,
			this._handleEditableDestroyed
		);

		this._editing = true;
	}

	/**
	 * Handle beforeNavigate SPA event
	 * and destroy all existing processors.
	 * @private
	 * @review
	 */
	_handleBeforeNavigate(event) {
		if (this._unsavedChanges) {
			const msg = Liferay.Language.get('do-you-want-to-leave-this-site');

			if (!confirm(msg)) {
				event.originalEvent.preventDefault();
			}
		}
	}

	/**
	 * Handle beforeunload event and show confirmation dialog
	 * if there are unsaved changes
	 * @private
	 * @review
	 */
	_handleBeforeUnload(event) {
		const confirmationMessage = '';

		if (this._unsavedChanges) {
			event.returnValue = confirmationMessage;
		}

		return confirmationMessage;
	}

	/**
	 * Callback executed when an editable lose the focus
	 * @private
	 * @review
	 */
	_handleEditableBlur() {
		requestAnimationFrame(
			() => {
				if (shouldClearFocus(this.element)) {
					this.store.dispatchAction(CLEAR_ACTIVE_ITEM);
					this._editing = false;
				}
			}
		);
	}

	/**
	 * Handle editable click event
	 * @param {Event} event
	 * @private
	 */
	_handleEditableClick(event) {
		event.preventDefault();
		event.stopPropagation();

		if (!this._editing) {
			this.store.dispatchAction(
				UPDATE_ACTIVE_ITEM,
				{
					activeItemId: `${this.fragmentEntryLinkId}-${this.editableId}`,
					activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.editable
				}
			);
		}
	}

	/**
	 * Callback executed when the exiting editor is destroyed
	 * @private
	 * @review
	 */
	_handleEditableDestroyed() {
		this._editing = false;
	}

	/**
	 * Callback executed when cursor enters editable element
	 * @private
	 * @review
	 */
	_handleEditableHoverStart(event) {
		event.stopPropagation();

		if (this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: `${this.fragmentEntryLinkId}-${this.editableId}`,
					hoveredItemType: FRAGMENTS_EDITOR_ITEM_TYPES.editable
				}
			);
		}
	}

	/**
	 * Callback executed when an editable value changes
	 * @param {string} newValue
	 * @private
	 */
	_handleEditableChanged(newValue) {
		this._unsavedChanges = true;

		clearTimeout(this._saveChangesTimeout);

		this._saveChangesTimeout = setTimeout(
			() => {
				this._saveChanges(newValue);
			},
			SAVE_CHANGES_DELAY
		);
	}

	_handleFloatingToolbarPanelSelected(event, data) {
		const {panelId} = data;

		if (panelId === FLOATING_TOOLBAR_EDIT_PANEL_ID) {
			event.preventDefault();
			this._enableEditor();
		}
		else if (panelId === FLOATING_TOOLBAR_MAP_PANEL_ID) {
			event.preventDefault();
			this.store
				.dispatchAction(
					OPEN_MAPPING_FIELDS_DIALOG,
					{
						editableId: this.editableId,
						editableType: this.type,
						fragmentEntryLinkId: this.fragmentEntryLinkId,
						mappedFieldId: this.editableValues.mappedField || ''
					}
				);
		}
	}

	/**
	 * Saves editable value changes
	 * @param {string} newValue
	 */
	_saveChanges(newValue) {
		this._unsavedChanges = false;

		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				UPDATE_EDITABLE_VALUE,
				{
					editableId: this.editableId,
					editableValue: newValue,
					editableValueId: this.languageId || DEFAULT_LANGUAGE_ID_KEY,
					editableValueSegmentId: this.segmentId || this.defaultSegmentId,
					fragmentEntryLinkId: this.fragmentEntryLinkId
				}
			)
			.dispatchAction(
				UPDATE_TRANSLATION_STATUS,
				{
					segmentId: this.segmentId || this.defaultSegmentId
				}
			)
			.dispatchAction(
				UPDATE_LAST_SAVE_DATE,
				{
					lastSaveDate: new Date()
				}
			)
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: false
				}
			);
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEditableField.STATE = {

	/**
	 * Editable content to be rendered
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	content: Config
		.string()
		.required(),

	/**
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	editableId: Config
		.string()
		.required(),

	/**
	 * Editable values
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!object}
	 */
	editableValues: Config
		.object()
		.required(),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config
		.string()
		.required(),

	/**
	 * Set of options that are sent to the processors.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!object}
	 */
	processorsOptions: Config
		.object()
		.required(),

	/**
	 * Editable type
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	type: Config.oneOf(['html', 'image', 'rich-text', 'text']).required(),

	/**
	 * Internal FloatingToolbar instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {object|null}
	 */
	_floatingToolbar: Config
		.internal()
		.value(null),

	/**
	 * Flag indicating if the editable editor is active.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_editing: Config
		.internal()
		.bool()
		.value(false),

	/**
	 * Id of the timeout to save changes
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {number}
	 */

	_saveChangesTimeout: Config.number().internal(),

	/**
	 * Flag indicating if there are unsaved changes
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_unsavedChanges: Config
		.internal()
		.bool()
		.value(false)
};

const ConnectedFragmentEditableField = getConnectedComponent(
	FragmentEditableField,
	[
		'activeItemId',
		'activeItemType',
		'defaultLanguageId',
		'defaultSegmentId',
		'hoveredItemId',
		'hoveredItemType',
		'languageId',
		'portletNamespace',
		'segmentId'
	]
);

Soy.register(ConnectedFragmentEditableField, templates);

export {ConnectedFragmentEditableField, FragmentEditableField};
export default ConnectedFragmentEditableField;