import Component from 'metal-component';
import {Config} from 'metal-state';
import {object} from 'metal';
import Soy from 'metal-soy';

import './FragmentEditableFieldTooltip.es';
import FragmentProcessors from '../fragment_processors/FragmentProcessors.es';
import {getActiveEditableElement} from '../fragment_processors/EditableTextFragmentProcessor.es';
import templates from './FragmentEditableField.soy';

/**
 * Buttons rendered inside the tooltip
 * @review
 */

const TOOLTIP_BUTTONS = {
	edit: {
		id: 'edit',
		label: Liferay.Language.get('edit')
	},

	map: {
		id: 'map',
		label: Liferay.Language.get('map[relational]')
	},

	selectImage: {
		id: 'selectImage',
		label: Liferay.Language.get('select-image')
	}
};

class FragmentEditableField extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this._handleBeforeNavigate = this._handleBeforeNavigate.bind(this);
		this._handleEditableChanged = this._handleEditableChanged.bind(this);
		this._handleEditableDestroyed = this._handleEditableDestroyed.bind(this);

		this._beforeNavigateHandler = Liferay.on(
			'beforeNavigate',
			this._handleBeforeNavigate
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	disposed() {
		this._destroyProcessors();
	}

	/**
	 * @inheritDoc
	 * @param {!object} state
	 * @returns {object}
	 */

	prepareStateForRender(state) {
		const translatedContent = this.editableValues[this.languageId] ||
			this.editableValues[this.defaultLanguageId] ||
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

		const _tooltipButtons = this._getTooltipButtons(
			this.type
		);

		return object.mixin(
			{},
			state,
			{
				content,
				_tooltipButtons
			}
		);
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
	}

	/**
	 * @inheritDoc
	 * @param changes
	 * @return {boolean}
	 * @review
	 */

	shouldUpdate(changes) {
		return !!changes._showTooltip;
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
	 * Get the list of buttons that should be rendered inside the tooltip
	 * @param {string} editableType
	 * @private
	 * @review
	 */

	_getTooltipButtons(editableType) {
		const _tooltipButtons = [];

		if (this.showMapping) {
			_tooltipButtons.push(TOOLTIP_BUTTONS.map);
		}

		if (editableType === 'image') {
			_tooltipButtons.push(TOOLTIP_BUTTONS.selectImage);
		}
		else {
			_tooltipButtons.push(TOOLTIP_BUTTONS.edit);
		}

		return _tooltipButtons;
	}

	/**
	 * Handle beforeNavigate SPA event
	 * and destroy all existing processors.
	 * @private
	 * @review
	 */

	_handleBeforeNavigate() {
		if (this._beforeNavigateHandler) {
			this._beforeNavigateHandler.detach();
			this._beforeNavigateHandler = null;
		}

		this._destroyProcessors();
	}

	/**
	 * Handle editable click event
	 * @param {Event} event
	 * @private
	 */

	_handleEditableClick(event) {
		event.preventDefault();
		event.stopPropagation();

		if (this._tooltipLabel) {
			this._showTooltip = false;
			this._tooltipLabel = '';
		}

		if (
			(this.showMapping && !this._editing) ||
			(getActiveEditableElement() !== this.refs.editable)
		) {
			if (!this.showMapping) {
				this._showTooltip = false;
				this._enableEditor();
			}
			else {
				this._showTooltip = !this._showTooltip;
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
	 * Handle image editor select event
	 * @param {string} newValue
	 * @private
	 */

	_handleEditableChanged(newValue) {
		this.emit(
			'editableChanged',
			{
				editableId: this.editableId,
				value: newValue
			}
		);
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
			this.emit(
				'mapButtonClicked',
				{
					editableId: this.editableId,
					editableType: this.type,
					mappedFieldId: this.editableValues.mappedField || ''
				}
			);
		}

		this._showTooltip = false;
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
	 * Flag indicating if the editable editor is active.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */

	_editing: Config.internal().bool().value(false),

	/**
	 * Flag indicating if the editable editor should be enabled.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */

	_showEditor: Config.internal().bool().value(false),

	/**
	 * Flag indicating if the click tooltip should be visible.
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */

	_showTooltip: Config.internal().bool().value(false),

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
	 * List of buttons rendered inside the tooltip
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */

	_tooltipButtons: Config
		.internal()
		.arrayOf(
			Config.shapeOf(
				{
					id: Config.string().required(),
					label: Config.string().required()
				}
			)
		),

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
		.value('')
};

Soy.register(FragmentEditableField, templates);

export {FragmentEditableField};
export default FragmentEditableField;