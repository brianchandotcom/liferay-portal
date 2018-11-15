import {addClasses, contains, removeClasses} from 'metal-dom';
import Component from 'metal-component';
import {isFunction, isObject} from 'metal';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './FragmentEntryLinkContent.es';
import FragmentEditableField from './FragmentEditableField.es';
import FragmentStyleEditor from './FragmentStyleEditor.es';
import {DROP_TARGET_ITEM_TYPES} from '../../reducers/placeholders.es';
import Store from '../../store/store.es';
import {
	CLEAR_ACTIVE_ITEM,
	CLEAR_HOVERED_ITEM,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_ACTIVE_ITEM,
	UPDATE_HOVERED_ITEM,
  REMOVE_FRAGMENT_ENTRY_LINK,
  UPDATE_EDITABLE_VALUE,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import {getItemMoveDirection} from '../../utils/FragmentsEditorGetUtils.es';
import templates from './FragmentEntryLink.soy';

/**
 * FragmentEntryLink
 * @review
 */
class FragmentEntryLink extends Component {

	/**
	 * Callback executed when a fragment lose the focus
	 * @private
	 */
	_handleFragmentBlur() {
		this.store.dispatchAction(CLEAR_ACTIVE_ITEM);
	}

	/**
	 * Callback executed when a fragment is clicked
	 * @private
	 */
	_handleFragmentClick() {
		event.stopPropagation();

		this.store.dispatchAction(
			UPDATE_ACTIVE_ITEM,
			{
				activeItemId: this.fragmentEntryLinkId,
				activeItemType: DROP_TARGET_ITEM_TYPES.fragment
			}
		);
	}

	/**
	 * Callback executed when a fragment starts being hovered.
	 * @param {object} event
	 * @private
	 */
	_handleFragmentHoverStart(event) {
		event.stopPropagation();

		if (this.store) {
			this.store.dispatchAction(
				UPDATE_HOVERED_ITEM,
				{
					hoveredItemId: this.fragmentEntryLinkId,
					hoveredItemType: DROP_TARGET_ITEM_TYPES.fragment
				}
			);
		}
	}

  /**
	 * Create instances of FragmentStyleEditor for each element styled with
	 * background-image.
	 */
	_createBackgroundImageStyleEditors() {
		if (this._backgroundImageStyleEditors) {
			this._backgroundImageStyleEditors.forEach(
				styleEditor => styleEditor.dispose()
			);

			this._backgroundImageStyleEditors = [];
		}

		this._backgroundImageStyleEditors = this._styles.flatMap(
			style => {
				let value = [];

				if (style.css.backgroundImage !== '') {
					value = style.nodes.map(
						node => {
							const styleEditor = new FragmentStyleEditor(
								{
									cssText: style.cssText,

									editorsOptions: {
										imageSelectorURL: this.imageSelectorURL
									},

									fragmentEntryLinkId: this.fragmentEntryLinkId,
									node: node,
									portletNamespace: this.portletNamespace,
									selectorText: style.selectorText,
									showMapping: this.showMapping,
									store: this.store,
									type: 'backgroundImage'
								}
							);

							styleEditor.on('openTooltip', this._handleOpenStyleTooltip.bind(this));

							styleEditor.on('styleChanged', this._handleStyleChanged.bind(this));

							return styleEditor;
						}
					);
				}

				return value;
			}
		);
	}

	_handleStyleChanged(event) {
		this.store.dispatchAction(
			UPDATE_EDITABLE_VALUE,
			{
				editableId: event.name,
				editableValue: event.value,
				editableValueId: this.languageId,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			}
		);
	}

	_handleOpenStyleTooltip() {
		if (!this._backgroundImageStyleEditors) {
			return;
		}

		this._backgroundImageStyleEditors.forEach(
			styleEditor => styleEditor.disposeStyleTooltip()
		);
	}

	/**
	 * Create instances of FragmentEditableField for each editable.
	 */
	_createEditables() {
		this._destroyEditables();

		this._editables = [
			...this.refs.content.querySelectorAll('lfr-editable')
		].map(
			editable => {
				let editableValues = (
					this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR] &&
					this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.id]
				) ? this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.id] :
					{defaultValue: editable.innerHTML};

				const defaultEditorConfiguration = this
					.defaultEditorConfigurations[editable.getAttribute('type')] ||
					this.defaultEditorConfigurations.text;

				return new FragmentEditableField(
					{
						content: editable.innerHTML,
						defaultLanguageId: this.defaultLanguageId,
						editableId: editable.id,
						editableValues,
						element: editable,

						events: {
							mapButtonClicked: this._handleMapButtonClick
						},

						fragmentEntryLinkId: this.fragmentEntryLinkId,
						languageId: this.languageId,
						portletNamespace: this.portletNamespace,

						processorsOptions: {
							defaultEditorConfiguration,
							imageSelectorURL: this.imageSelectorURL
						},

						showMapping: this.showMapping,
						store: this.store,
						type: editable.getAttribute('type')
					}
				);
			}
		);
	}

	_createStyles() {
		const elements = [];

		for (let styleIndex = 0; styleIndex < document.styleSheets.length; styleIndex++) {
			const cssStyle = document.styleSheets[styleIndex];

			if (contains(this.refs.content, cssStyle.ownerNode)) {
				for (let ruleIndex = 0; ruleIndex < cssStyle.rules.length; ruleIndex++) {
					const cssRule = cssStyle.rules[ruleIndex];

					elements.push(
						{
							css: cssRule.style,
							cssText: cssRule.cssText,
							nodes: [
								...this.refs.content.querySelectorAll(
									cssRule.selectorText
								)
							],
							selectorText: cssRule.selectorText
						}
					);
				}
			}
		}

		this._styles = elements;
	}

	/**
	 * Destroy existing FragmentEditableField instances.
	 */
	_destroyEditables() {
		if (this._editables) {
			this._editables.forEach(
				editable => editable.dispose()
			);

			this._editables = [];
		}
	}

	/**
   * Callback executed when a fragment ends being hovered.
   * @private
	 */
	_handleFragmentHoverEnd() {
		if (this.store) {
			this.store.dispatchAction(CLEAR_HOVERED_ITEM);
		}
	}

	/**
	 * Handle fragment keyup event so it can emit when it
	 * should be moved or selected.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentKeyUp(event) {
		event.stopPropagation();

		const direction = getItemMoveDirection(event.which);

		this.emit(
			'moveFragment',
			{
				direction,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			}
		);
	}

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * @private
	 */
	_handleFragmentRemoveButtonClick() {
		event.stopPropagation();

		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				REMOVE_FRAGMENT_ENTRY_LINK,
				{
					fragmentEntryLinkId: this.fragmentEntryLinkId
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

	/**
	 * Renders the FragmentEntryLink content parsing with AUI
	 * @param {string} content
	 * @private
	 * @review
	 */
	_renderContent(content) {
		if (this.refs.content) {
			AUI().use(
				'aui-parse-content',
				A => {
					const contentNode = A.one(this.refs.content);
					contentNode.plug(A.Plugin.ParseContent);
					contentNode.setContent(content);

					this._createStyles();

					this._createEditables();

					this._createBackgroundImageStyleEditors();

					this._update(
						this.languageId,
						this.defaultLanguageId,
						[this._updateEditableStatus]
					);
				}
			);
		}
	}

	/**
	 * Runs a set of update functions through the collection of editable values
	 * inside this fragment entry link.
	 * @param {string} languageId The current language id
	 * @param {string} defaultLanguageId The default language id
	 * @param {Array<Function>} updateFunctions The set of update functions to execute for each editable value
	 * @private
	 * @review
	 */
	_update(languageId, defaultLanguageId, updateFunctions) {
		const editableValues = this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];

		Object.keys(editableValues).forEach(
			editableId => {
				const editableValue = editableValues[editableId];

				const defaultValue = editableValue[defaultLanguageId] || editableValue.defaultValue;
				const mappedField = editableValue.mappedField || '';
				const value = editableValue[languageId];

				updateFunctions.forEach(
					updateFunction => updateFunction(
						editableId,
						value,
						defaultValue,
						mappedField
					)
				);
			}
		);
	}

	/**
	 * Flags a DOM editable section as translated or untranslated compared to
	 * the stored default value for that same editable id.
	 * @param {string} editableId The editable id
	 * @param {string} value The value for the editable section
	 * @param {string} defaultValue
	 * @param {string} mappedField
	 * @private
	 * @review
	 */
	_updateEditableStatus(editableId, value, defaultValue, mappedField) {
		const element = this.element.querySelector(`lfr-editable[id="${editableId}"]`);

		if (element) {
			removeClasses(
				element,
				'mapped',
				'translated',
				'unmapped',
				'untranslated'
			);

			const mapped = !!mappedField;
			const translated = !mappedField && !!value;

			addClasses(element, mapped ? 'mapped' : 'unmapped');
			addClasses(element, translated ? 'translated' : 'untranslated');
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLink.STATE = {

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Fragment name
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */
	name: Config.string().value(''),

	/**
	 * Shows FragmentEntryLink control toolbar
	 * @default true
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!bool}
	 */
	showControlBar: Config.bool().value(true),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * CSS class to modify style
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	styleModifier: Config.string(),

	/**
	 * Fragment spritemap
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */
	spritemap: Config.string().required()
};

Soy.register(FragmentEntryLink, templates);

export {FragmentEntryLink};

export default FragmentEntryLink;
