import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {addClasses, removeClasses} from 'metal-dom';
import {isFunction, isObject, object} from 'metal';

import FragmentEditableField from './FragmentEditableField.es';
import MetalStore from '../../store/store.es';
import {
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../../actions/actions.es';
import templates from './FragmentEntryLink.soy';

const ARROW_DOWN_KEYCODE = 40;

const ARROW_UP_KEYCODE = 38;

const EDITABLE_FRAGMENT_ENTRY_PROCESSOR = 'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor';

/**
 * FragmentEntryLink
 * @review
 */

class FragmentEntryLink extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this._handleEditableChanged = this._handleEditableChanged.bind(this);
		this._handleMapButtonClick = this._handleMapButtonClick.bind(this);
		this._updateEditableStatus = this._updateEditableStatus.bind(this);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	disposed() {
		this._destroyEditables();
	}

	/**
	 * Returns the given editable value
	 */

	getEditableValue(editableId) {
		return this.getEditableValues()[editableId];
	}

	/**
	 * Returns the editable values property content
	 * @return {object}
	 * @review
	 */

	getEditableValues() {
		return this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR];
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	prepareStateForRender(state) {
		return Object.assign(
			{},
			state,
			{
				content: this.content ? Soy.toIncDom(this.content) : null
			}
		);
	}

	/**
	 * Returns a new object with the updated editableId
	 * @param {string} editableId
	 * @param {object} content
	 */

	setEditableValue(editableId, content) {
		const editableValues = this.getEditableValues();

		const editableValue = this.getEditableValue(editableId);

		editableValues[editableId] = object.mixin({}, editableValue, content);

		this._update(
			this.languageId,
			this.defaultLanguageId,
			[this._updateEditableStatus]
		);

		return {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: editableValues
		};
	}

	/**
	 * Handle content changed
	 * @inheritDoc
	 * @param {string} newContent
	 * @review
	 */

	syncContent(newContent) {
		if (newContent) {
			this._renderContent(newContent);
		}
	}

	/**
	 * Handle editableValues changed
	 * @inheritDoc
	 * @param {object} newEditableValues
	 * @review
	 */

	syncEditableValues(newEditableValues) {
		if (this._editables) {
			this._editables.forEach(
				editable => {
					const editableValues = (
						newEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR] &&
						newEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.editableId]
					) ? newEditableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][editable.editableId] :
						{defaultValue: editable.content};

					editable.editableValues = editableValues;
				}
			);
		}
	}

	/**
	 * Callback executed when languageId property has changed
	 * @inheritDoc
	 * @review
	 */

	syncLanguageId() {
		if (this.content) {
			this._renderContent(this.content);
		}
	}

	/**
	 * Callback executed when styleModifier property has changed
	 * @inheritDoc
	 * @review
	 */

	syncStyleModifier() {
		if (this.content) {
			this._renderContent(this.content);
		}
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
							editableChanged: this._handleEditableChanged,
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
						type: editable.getAttribute('type')
					}
				);
			}
		);
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
	 * Emits a move event with the fragmentEntryLinkId and the direction.
	 * @param {!number} direction
	 * @private
	 */

	_emitMoveEvent(direction) {
		this.emit(
			'move',
			{
				direction,
				fragmentEntryLinkId: this.fragmentEntryLinkId
			}
		);
	}

	/**
	 * Handle a changed editable event
	 * @param {{editableId: string, value: string}} event
	 * @private
	 * @review
	 */

	_handleEditableChanged(event) {
		this.emit(
			'editableChanged',
			{
				editableId: event.editableId,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				value: event.value
			}
		);
	}

	/**
	 * Handle fragment keyup event so it can emit when it
	 * should be moved or selected.
	 * @param {KeyboardEvent} event
	 * @private
	 * @review
	 */

	_handleFragmentKeyUp(event) {
		if (document.activeElement === this.refs.fragmentEntryLinkWrapper) {
			switch (event.which) {
			case ARROW_DOWN_KEYCODE:
				this._emitMoveEvent(FragmentEntryLink.MOVE_DIRECTIONS.DOWN);
				break;
			case ARROW_UP_KEYCODE:
				this._emitMoveEvent(FragmentEntryLink.MOVE_DIRECTIONS.UP);
				break;
			}
		}
	}

	/**
	 * Callback executed when the fragment move down button is clicked.
	 * It emits a 'moveDown' event with
	 * the FragmentEntryLink id.
	 * @private
	 * @review
	 */

	_handleFragmentMoveDownButtonClick() {
		this._emitMoveEvent(FragmentEntryLink.MOVE_DIRECTIONS.DOWN);
	}

	/**
	 * Callback executed when the fragment move up button is clicked.
	 * It emits a 'moveUp' event with
	 * the FragmentEntryLink id.
	 * @private
	 * @review
	 */

	_handleFragmentMoveUpButtonClick() {
		this._emitMoveEvent(FragmentEntryLink.MOVE_DIRECTIONS.UP);
	}

	/**
	 * Callback executed when the fragment remove button is clicked.
	 * @private
	 */

	_handleFragmentRemoveButtonClick() {
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
	 * Propagates mapButtonClick event.
	 */

	_handleMapButtonClick(event) {
		this.emit(
			'mappeableFieldClicked',
			{
				editableId: event.editableId,
				editableType: event.editableType,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				mappedFieldId: event.mappedFieldId
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

					this._createEditables();

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
		const editableValues = this.getEditableValues();

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
 * Directions where a fragment can be moved to
 * @review
 * @static
 * @type {!object}
 */

FragmentEntryLink.MOVE_DIRECTIONS = {
	DOWN: 1,
	UP: -1
};

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentEntryLink.STATE = {

	/**
	 * Fragment content to be rendered
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */

	content: Config.any()
		.setter(
			content => {
				return !isFunction(content) && isObject(content) ? content.value.content : content;
			}
		)
		.value(''),

	/**
	 * Default configurations for AlloyEditor instances.
	 * @default {}
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {object}
	 */

	defaultEditorConfigurations: Config.object().value({}),

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
	 * CSS class for the fragments drop target.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */

	dropTargetClass: Config.string(),

	/**
	 * Editable values that should be used instead of the default ones
	 * inside editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!Object}
	 */

	editableValues: Config.object().required(),

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
	 * Image selector url
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
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
	 * Fragment name
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {string}
	 */

	name: Config.string().value(''),

	/**
	 * Selected mapping type label
	 * @default {}
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {{
	 *   subtype: {
	 *   	id: !string,
	 *   	label: !string
	 *   },
	 *   type: {
	 *   	id: !string,
	 *   	label: !string
	 *   }
	 * }}
	 */

	selectedMappingTypes: Config
		.shapeOf(
			{
				subtype: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				),
				type: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				)
			}
		)
		.value({}),

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
	 * If true, asset mapping is enabled
	 * @default false
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {bool}
	 */

	showMapping: Config.bool().value(false),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {MetalStore}
	 */

	store: Config.instanceOf(MetalStore),

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
	 * Portlet namespace needed for prefixing Alloy Editor instances
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @review
	 * @type {!string}
	 */

	portletNamespace: Config.string().required(),

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