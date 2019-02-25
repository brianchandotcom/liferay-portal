import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FragmentEditableFieldTooltip.es';

import {CLEAR_ACTIVE_ITEM, OPEN_MAPPING_FIELDS_DIALOG, UPDATE_ACTIVE_ITEM, UPDATE_EDITABLE_VALUE, UPDATE_HOVERED_ITEM, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_TRANSLATION_STATUS} from '../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import {getActiveEditableElement} from '../fragment_processors/EditableTextFragmentProcessor.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {setIn, shouldClearFocus} from '../../utils/FragmentsEditorUpdateUtils.es';
import {Store} from '../../store/store.es';
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

		window.removeEventListener('beforeunload', this._handleBeforeUnload);
	}

	/**
	 * @inheritDoc
	 * @param {!object} state
	 * @returns {object}
	 */
	prepareStateForRender(state) {
		const defaultSegmentedContent = this.editableValues[this.defaultSegmentId] ||
			{};
		const segmentedContent = this.editableValues[this.segmentId] ||
			defaultSegmentedContent ||
			{};

		const translatedContent = segmentedContent[this.languageId] ||
			defaultSegmentedContent[this.languageId] ||
			segmentedContent[this.defaultLanguageId] ||
			this.editableValues.defaultValue;

		let content = Soy.toIncDom(translatedContent || this.content);

		if (this.type === 'image' && translatedContent) {
			const tempContent = document.createElement('div');

			tempContent.innerHTML = this.content;

			const tempImage = tempContent.querySelector('img');

			if (tempImage) {
				tempImage.src = translatedContent;
			}

			content = Soy.toIncDom(tempContent.innerHTML);
		}

		const nextState = setIn(state, ['content'], content);

		return setIn(nextState, ['itemTypes'], FRAGMENTS_EDITOR_ITEM_TYPES);
			}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (this._showEditor) {
			this._showEditor = false;
			this._enableEditor();
		}

		if (this.refs.editable !== this._tooltipAlignElement) {
			this._tooltipAlignElement = this.refs.editable;
		}

		if (
			(this.editableId === this.activeItemId) &&
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
	 * @param {{_showTooltip: bool}} changes
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return Boolean(changes._showTooltip);
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		if (this._floatingToolbar) {
			this._floatingToolbar.forceUpdate();
		}
		else {
			this._floatingToolbar = new FloatingToolbar(
				{
					anchorElement: this.element,
					events: {
						panelSelected: this._handleFloatingToolbarPanelSelected
					},
					item: this.editableId,
					itemId: this.editableId,
					panels: FLOATING_TOOLBAR_PANELS,
					portalElement: document.body,
					store: this.store
				}
			);
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
		else {
			if (this._beforeNavigateHandler) {
				this._beforeNavigateHandler.detach();
				this._beforeNavigateHandler = null;
			}

			this._destroyProcessors();
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
					activeItemId: this.editableId,
					activeItemType: FRAGMENTS_EDITOR_ITEM_TYPES.editable
				}
			);
		}

		if (this._tooltipLabel) {
			this._showTooltip = false;
			this._tooltipLabel = '';
		}

		if (
			(this.showMapping && !this._editing) ||
			(getActiveEditableElement() !== this.refs.editable)
		) {
			if (this.showMapping) {
				this._showTooltip = !this._showTooltip;
			}
			else {
				this._showTooltip = false;
				this._enableEditor();
			}

			if (!this._showTooltip) {
				this._handleEditableMouseEnter();
			}
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
					hoveredItemId: this.editableId,
					hoveredItemType: FRAGMENTS_EDITOR_ITEM_TYPES.editable
				}
			);
		}
	}

	/**
	 * Callback executed when cursor enters editable element
	 * @private
	 * @review
	 */
	_handleEditableMouseEnter() {
		if (
			!this._editing &&
			this.editableValues.mappedField &&
			!this._showTooltip
		) {
			this._showTooltip = true;
			this._tooltipLabel = this.editableValues.mappedField;
		}
	}

	/**
	 * Callback executed when cursor leaves editable element
	 * @private
	 * @review
	 */
	_handleEditableMouseLeave() {
		if (this._tooltipLabel && !this._editing) {
			this._showTooltip = false;
			this._tooltipLabel = '';
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
		event.preventDefault();

		const {panelId} = data;

		if (panelId === FLOATING_TOOLBAR_EDIT_PANEL_ID) {
			this._showEditor = true;
		}
		else if (panelId === FLOATING_TOOLBAR_MAP_PANEL_ID) {
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

		this._showTooltip = false;
	}

	/**
	 * Handle clicks outside tooltip element
	 * @private
	 * @review
	 */
	_handleOutsideTooltipClick() {
		this._showTooltip = false;
	}

	/**
	 * Handle tooltip button click event
	 * @param {{buttonId: string}} event
	 * @private
	 * @review
	 */
	_handleTooltipButtonClick(event) {
		const {buttonId} = event;

		if (
			buttonId === TOOLTIP_BUTTONS.edit.id ||
			buttonId === TOOLTIP_BUTTONS.selectImage.id
		) {
			this._showEditor = true;
		}
		else if (buttonId === TOOLTIP_BUTTONS.map.id) {
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

		this._showTooltip = false;
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
	content: Config.string().required(),

	/**
	 * Default language id.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */
	defaultLanguageId: Config.string().required(),

	/**
	 * Default segment id.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */
	defaultSegmentId: Config.string().required(),

	/**
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	editableId: Config.string().required(),

	/**
	 * Editable values
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!object}
	 */
	editableValues: Config.object().required(),

	/**
	 * Internal FloatingToolbar instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal()
		.value(null),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Currently selected language id.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */
	languageId: Config.string().required(),

	/**
	 * Currently selected segment id.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */
	segmentId: Config.string(),

	/**
	 * Portlet namespace
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * Set of options that are sent to the processors.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!object}
	 */
	processorsOptions: Config.object().required(),

	/**
	 * Editable type
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	type: Config.string().required(),

	/**
	 * True if mapping is activated
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!boolean}
	 */
	showMapping: Config.bool().required(),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

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
	 * Flag indicating if the editable editor should be enabled.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_showEditor: Config
		.internal()
		.bool()
		.value(false),

	/**
	 * Flag indicating if the click tooltip should be visible.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_showTooltip: Config
		.internal()
		.bool()
		.value(false),

	/**
	 * Reference element used for aligning the tooltip
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {HTMLElement}
	 */
	_tooltipAlignElement: Config.internal().object(),

	/**
	 * Label shown inside editable's tooltip instead of action buttons
	 * @default ''
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {string}
	 */
	_tooltipLabel: Config
		.internal()
		.string()
		.value(''),

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
		'activeItemType'
	]
);

Soy.register(ConnectedFragmentEditableField, templates);

export {ConnectedFragmentEditableField, FragmentEditableField};
export default ConnectedFragmentEditableField;