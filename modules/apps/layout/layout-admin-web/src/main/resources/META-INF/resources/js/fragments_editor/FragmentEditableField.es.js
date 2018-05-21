import {Align} from 'metal-position';
import debounce from 'metal-debounce';
import Component from 'metal-component';
import {Config} from 'metal-state';
import dom from 'metal-dom';
import {object} from 'metal';
import Soy from 'metal-soy';

import {showImageEditing} from './fragment_processors/EditableImageFragmentProcessor.es';
import {createTextEditor, destroyTextEditor, getActiveEditableElement} from './fragment_processors/EditableTextFragmentProcessor.es';
import templates from './FragmentEditableField.soy';

class FragmentEditableField extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this._handleDocumentClick = this._handleDocumentClick.bind(this);
		this._handleEditableChanged = this._handleEditableChanged.bind(this);
		this._handleWindowResize = debounce(this._handleWindowResize.bind(this), 100);

		this._windowResizeHandler = dom.on(
			window,
			'resize',
			this._handleWindowResize
		);

		this._documentClickHandler = dom.on(
			document.body,
			'click',
			this._handleDocumentClick
		);
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

		if (this._windowResizeHandler) {
			this._windowResizeHandler.removeListener();
			this._windowResizeHandler = null;
		}
	}

	/**
	 * @inheritDoc
	 * @param {!object} state
	 * @returns {object}
	 */

	prepareStateForRender(state) {
		const translatedContent = this.editableValues[this.languageId] ||
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

		return object.mixin(
			{},
			state,
			{content}
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

		this._alignTooltip();
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
	 * Align tooltip position acording to editable field
	 * @private
	 * @review
	 */

	_alignTooltip() {
		if (this.refs.editable && this.refs.tooltip) {
			Align.align(
				this.refs.tooltip,
				this.refs.editable,
				Align.Top
			);
		}
	}

	/**
	 * Enables the corresponding editor
	 * @private
	 * @review
	 */

	_enableEditor() {
		if (this.type === 'image') {
			showImageEditing(
				this.refs.editable,
				this.portletNamespace,
				this.imageSelectorURL,
				this.fragmentEntryLinkId,
				this._handleEditableChanged
			);
		}
		else {
			createTextEditor(
				this.refs.editable,
				this.defaultEditorConfiguration,
				this.portletNamespace,
				this.fragmentEntryLinkId,
				this._handleEditableChanged
			);
		}
	}

	/**
	 * Hide tooltip on document click when it is outside the tooltip
	 * @param {MouseEvent} event
	 */

	_handleDocumentClick(event) {
		if (this.refs.tooltip && !this.refs.tooltip.contains(event.target)) {
			this._showTooltip = false;
		}
	}

	/**
	 * Handle editable click event
	 * @param {Event} event
	 * @private
	 */

	_handleEditableClick(event) {
		event.preventDefault();
		event.stopPropagation();

		if (!this.showMapping) {
			this._showTooltip = false;
			this._enableEditor();
		}
		else if (getActiveEditableElement() !== this.refs.editable) {
			this._showTooltip = !this._showTooltip;
		}
	}

	/**
	 * Handle edit button click event
	 * @private
	 */

	_handleEditButtonClick() {
		destroyTextEditor();

		this._showTooltip = false;
		this._showEditor = true;
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
	 * Handle map button click event
	 * @private
	 */

	_handleMapButtonClick() {
		this._showTooltip = false;

		this.emit(
			'mapButtonClicked',
			{editableId: this.editableId}
		);
	}

	/**
	 * Handle window resize event
	 * @private
	 * @review
	 */

	_handleWindowResize() {
		this._alignTooltip();
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
	 * Default configuration for AlloyEditor instances.
	 * @default {}
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {object}
	 */

	defaultEditorConfiguration: Config.object().value({}),

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
	 * Image selector url
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */

	imageSelectorURL: Config.string().required(),

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
	 * If true, asset mapping is enabled
	 * @default false
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {bool}
	 */

	showMapping: Config.bool().value(false),

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

	_showTooltip: Config.internal().bool().value(false)
};

Soy.register(FragmentEditableField, templates);

export {FragmentEditableField};
export default FragmentEditableField;