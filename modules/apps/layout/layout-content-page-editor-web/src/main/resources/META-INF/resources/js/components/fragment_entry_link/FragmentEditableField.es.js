import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import '../floating_toolbar/image_properties/FloatingToolbarImagePropertiesPanel.es';
import '../floating_toolbar/link/FloatingToolbarLinkPanel.es';
import '../floating_toolbar/mapping/FloatingToolbarMappingPanel.es';
import '../floating_toolbar/text_properties/FloatingToolbarTextPropertiesPanel.es';
import './FragmentEditableFieldTooltip.es';

import {
	CLEAR_FRAGMENT_EDITOR,
	DISABLE_FRAGMENT_EDITOR,
	ENABLE_FRAGMENT_EDITOR,
	OPEN_ASSET_TYPE_DIALOG,
	UPDATE_CONFIG_ATTRIBUTES
} from '../../actions/actions.es';
import {
	DEFAULT_LANGUAGE_ID_KEY,
	EDITABLE_FIELD_CONFIG_KEYS,
	FLOATING_TOOLBAR_BUTTONS,
	FRAGMENTS_EDITOR_ITEM_TYPES
} from '../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../actions/saveChanges.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {
	getItemPath,
	itemIsInPath
} from '../../utils/FragmentsEditorGetUtils.es';
import {prefixSegmentsExperienceId} from '../../utils/prefixSegmentsExperienceId.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	shouldUpdateOnChangeProperties,
	shouldUpdatePureComponent
} from '../../utils/FragmentsEditorComponentUtils.es';
import {updateEditableValueAction} from '../../actions/updateEditableValue.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import FragmentProcessors from '../fragment_processors/FragmentProcessors.es';
import templates from './FragmentEditableField.soy';

/**
 * FragmentEditableField
 */
class FragmentEditableField extends PortletBase {
	/**
	 * Checks if the given editable should be highlighted
	 * @param {string} activeItemId
	 * @param {string} activeItemType
	 * @param {string} hoveredItemId
	 * @param {string} hoveredItemType
	 * @param {object} structure
	 * @private
	 * @return {boolean}
	 * @review
	 */
	static _isHighlighted(
		activeItemId,
		activeItemType,
		fragmentEntryLinkId,
		hoveredItemId,
		hoveredItemType,
		structure
	) {
		const fragmentInActivePath =
			itemIsInPath(
				getItemPath(activeItemId, activeItemType, structure),
				fragmentEntryLinkId,
				FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			) && activeItemType !== FRAGMENTS_EDITOR_ITEM_TYPES.editable;

		const fragmentInHoveredPath = itemIsInPath(
			getItemPath(hoveredItemId, hoveredItemType, structure),
			fragmentEntryLinkId,
			FRAGMENTS_EDITOR_ITEM_TYPES.fragment
		);

		return fragmentInActivePath || fragmentInHoveredPath;
	}

	/**
	 * Checks if the given editable is mapped
	 * @param {object} editableValues
	 * @private
	 * @return {boolean}
	 * @review
	 */
	static _isMapped(editableValues) {
		return Boolean(
			editableValues.mappedField ||
				(editableValues.classNameId &&
					editableValues.classPK &&
					editableValues.fieldId)
		);
	}

	/**
	 * Checks if the given editable is mapped to an asset entry
	 * @param {object} editableValues
	 * @private
	 * @return {boolean}
	 * @review
	 */
	static _isMappedToAssetEntry(editableValues) {
		return Boolean(
			editableValues.classNameId &&
				editableValues.classPK &&
				editableValues.fieldId
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleEditableChanged = this._handleEditableChanged.bind(this);
		this._handleEditableDestroyed = this._handleEditableDestroyed.bind(
			this
		);
		this._handleFloatingToolbarButtonClicked = this._handleFloatingToolbarButtonClicked.bind(
			this
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._destroyProcessors();
		this._disposeFloatingToolbar();
	}

	/**
	 * @inheritDoc
	 * @param {!object} state
	 * @returns {object}
	 */
	prepareStateForRender(state) {
		const defaultSegmentsExperienceId = prefixSegmentsExperienceId(
			this.defaultSegmentsExperienceId
		);
		const segmentsExperienceId = prefixSegmentsExperienceId(
			this.segmentsExperienceId
		);

		const segmentedValue =
			this.editableValues[segmentsExperienceId] ||
			this.editableValues[defaultSegmentsExperienceId] ||
			this.editableValues;

		const translatedValue =
			segmentedValue[this.languageId] ||
			segmentedValue[this.defaultLanguageId];

		const mapped = FragmentEditableField._isMapped(this.editableValues);

		let value = mapped
			? this._mappedFieldValue || this.editableValues.defaultValue
			: translatedValue || this.editableValues.defaultValue;

		const processor =
			FragmentProcessors[this.type] || FragmentProcessors.fallback;

		const content = Soy.toIncDom(
			processor.render(this.content, value, this.editableValues)
		);

		const highlighted = FragmentEditableField._isHighlighted(
			state.activeItemId,
			state.activeItemType,
			state.fragmentEntryLinkId,
			state.hoveredItemId,
			state.hoveredItemType,
			state.layoutData.structure
		);
		const itemId = this._getItemId();
		const translated = !mapped && Boolean(segmentedValue[this.languageId]);

		let nextState = state;

		nextState = setIn(nextState, ['_highlighted'], highlighted);
		nextState = setIn(nextState, ['_mapped'], mapped);
		nextState = setIn(nextState, ['_translated'], translated);
		nextState = setIn(nextState, ['content'], content);
		nextState = setIn(nextState, ['itemId'], itemId);
		nextState = setIn(
			nextState,
			['itemTypes'],
			FRAGMENTS_EDITOR_ITEM_TYPES
		);

		return nextState;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (
			this._getItemId() === this.activeItemId &&
			this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable
		) {
			this._createFloatingToolbar();
		} else {
			this._disposeFloatingToolbar();
		}

		if (this._getItemId() === this.fragmentEditorClear) {
			this._clearEditor();

			this._handleEditableDestroyed();
		} else if (this._getItemId() === this.fragmentEditorEnabled) {
			this._enableEditor();

			this._disposeFloatingToolbar();
		}
	}

	/**
	 * @inheritDoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return this._getItemId() === this.fragmentEditorEnabled
			? shouldUpdateOnChangeProperties(changes, [
					'fragmentEditorEnabled',
					'languageId',
					'segmentsExperienceId'
			  ])
			: shouldUpdatePureComponent(changes);
	}

	/**
	 * Handle editableValues changed
	 * @inheritDoc
	 * @review
	 */
	syncEditableValues() {
		this._loadMappedFieldLabel();
		this._updateMappedFieldValue();
	}

	/**
	 * Handle getAssetFieldValueURL changed
	 * @inheritDoc
	 * @review
	 */
	syncGetAssetFieldValueURL() {
		this._updateMappedFieldValue();
	}

	/**
	 * Clears the corresponding editor
	 * @private
	 * @review
	 */
	_clearEditor() {
		this._handleEditableChanged('');

		this.store.dispatch({
			itemId: '',
			type: CLEAR_FRAGMENT_EDITOR
		});
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		const processor =
			FragmentProcessors[this.type] || FragmentProcessors.fallback;

		const config = {
			anchorElement: this.element,
			buttons: processor.getFloatingToolbarButtons(this.editableValues),
			classes:
				this.editableValues.mappedField || this.editableValues.fieldId
					? 'fragments-editor__floating-toolbar--mapped-field'
					: '',
			events: {
				buttonClicked: this._handleFloatingToolbarButtonClicked
			},
			item: {
				editableId: this.editableId,
				editableValues: this.editableValues,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				type: this.type
			},
			itemId: this._getItemId(),
			itemType: FRAGMENTS_EDITOR_ITEM_TYPES.editable,
			portalElement: document.body,
			store: this.store
		};

		if (this._floatingToolbar) {
			this._floatingToolbar.setState(config);
		} else {
			this._floatingToolbar = new FloatingToolbar(config);
		}
	}

	/**
	 * Call destroy method on all processors
	 * @private
	 * @review
	 */
	_destroyProcessors() {
		Object.values(FragmentProcessors).forEach(fragmentProcessor =>
			fragmentProcessor.destroy()
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
		const {init} =
			FragmentProcessors[this.type] || FragmentProcessors.fallback;

		init(
			this.refs.editable,
			this.fragmentEntryLinkId,
			this.portletNamespace,
			this.processorsOptions,
			this._handleEditableChanged,
			this._handleEditableDestroyed
		);
	}

	/**
	 * @private
	 * @return {string} Valid FragmentsEditor itemId for it's
	 * 	fragmentEntryLinkId and editableId
	 * @review
	 */
	_getItemId() {
		return `${this.fragmentEntryLinkId}-${this.editableId}`;
	}

	/**
	 * Handle editable click event
	 * @private
	 * @review
	 */
	_handleEditableClick() {
		if (this._preventEditableClick) {
			this._preventEditableClick = false;
		} else {
			this.store.dispatch({
				itemId: `${this.fragmentEntryLinkId}-${this.editableId}`,
				type: ENABLE_FRAGMENT_EDITOR
			});
		}
	}

	/**
	 * Handle editable focus event
	 * @private
	 * @review
	 */
	_handleEditableFocus() {
		this._preventEditableClick = true;
	}

	/**
	 * Callback executed when the exiting editor is destroyed
	 * @private
	 * @review
	 */
	_handleEditableDestroyed() {
		this.store.dispatch({
			type: DISABLE_FRAGMENT_EDITOR
		});
	}

	/**
	 * Callback executed when an editable value changes
	 * @param {string} newValue
	 * @private
	 */
	_handleEditableChanged(newValue) {
		const editableValueSegmentsExperienceId =
			prefixSegmentsExperienceId(this.segmentsExperienceId) ||
			prefixSegmentsExperienceId(this.defaultSegmentsExperienceId);

		if (this.type === 'image') {
			this.store
				.dispatch(enableSavingChangesStatusAction())
				.dispatch({
					config: {
						[EDITABLE_FIELD_CONFIG_KEYS.imageSource]: newValue
					},
					editableId: this.editableId,
					fragmentEntryLinkId: this.fragmentEntryLinkId,
					type: UPDATE_CONFIG_ATTRIBUTES
				})
				.dispatch(updateLastSaveDateAction())
				.dispatch(disableSavingChangesStatusAction());
		}

		this.store.dispatch(
			updateEditableValueAction(
				this.fragmentEntryLinkId,
				this.editableId,
				this.languageId || DEFAULT_LANGUAGE_ID_KEY,
				newValue,
				editableValueSegmentsExperienceId
			)
		);
	}

	/**
	 * Callback executed when an floating toolbar button is clicked
	 * @param {Event} event
	 * @param {Object} data
	 * @private
	 */
	_handleFloatingToolbarButtonClicked(event, data) {
		const {panelId, type} = data;

		if (type === 'editor') {
			this.store.dispatch({
				itemId: this._getItemId(),
				type: ENABLE_FRAGMENT_EDITOR
			});
		} else if (
			type === 'panel' &&
			panelId === FLOATING_TOOLBAR_BUTTONS.map.panelId &&
			this.mappingFieldsURL &&
			!this.selectedMappingTypes.type
		) {
			event.preventDefault();

			this.store.dispatch({
				type: OPEN_ASSET_TYPE_DIALOG
			});
		}
	}

	/**
	 * Load mapped field label
	 * @private
	 * @review
	 */
	_loadMappedFieldLabel() {
		let promise;
		let mappedFieldId;

		if (this.editableValues.mappedField && this.selectedMappingTypes.type) {
			const data = {
				classNameId: this.selectedMappingTypes.type.id
			};

			if (this.selectedMappingTypes.subtype) {
				data.classTypeId = this.selectedMappingTypes.subtype.id;
			}

			mappedFieldId = this.editableValues.mappedField;
			promise = this.fetch(this.mappingFieldsURL, data);
		} else if (
			this.editableValues.classNameId &&
			this.editableValues.classPK &&
			this.editableValues.fieldId &&
			this.getAssetMappingFieldsURL
		) {
			mappedFieldId = this.editableValues.fieldId;
			promise = this.fetch(this.getAssetMappingFieldsURL, {
				classNameId: this.editableValues.classNameId,
				classPK: this.editableValues.classPK
			});
		}

		if (promise) {
			promise
				.then(response => response.json())
				.then(response => {
					const field = response.find(
						field => field.key === mappedFieldId
					);

					if (field) {
						this._mappedFieldLabel = field.label;
					}
				});
		}
	}

	/**
	 * Updates mapped field value
	 * @private
	 * @review
	 */
	_updateMappedFieldValue() {
		if (
			this.getAssetFieldValueURL &&
			FragmentEditableField._isMappedToAssetEntry(this.editableValues)
		) {
			this.fetch(this.getAssetFieldValueURL, {
				classNameId: this.editableValues.classNameId,
				classPK: this.editableValues.classPK,
				fieldId: this.editableValues.fieldId
			})
				.then(response => response.json())
				.then(response => {
					const {fieldValue} = response;

					if (fieldValue) {
						if (this.type === 'image' && fieldValue.url) {
							this._mappedFieldValue = fieldValue.url;
						} else {
							this._mappedFieldValue = fieldValue;
						}
					}
				});
		}
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
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {!string}
	 */
	processor: Config.string().required(),

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
	type: Config.oneOf([
		'html',
		'image',
		'link',
		'rich-text',
		'text'
	]).required(),

	/**
	 * Internal FloatingToolbar instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEditableField
	 * @review
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal().value(null),

	/**
	 * Translated label of the mapped field
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {string}
	 */
	_mappedFieldLabel: Config.internal().string(),

	/**
	 * Mapped asset field value
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {string}
	 */
	_mappedFieldValue: Config.internal().string(),

	/**
	 * Prevent editable click effect
	 * @instance
	 * @memberOf FragmentEditableField
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_preventEditableClick: Config.bool().value()
};

const ConnectedFragmentEditableField = getConnectedComponent(
	FragmentEditableField,
	[
		'activeItemId',
		'activeItemType',
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'fragmentEditorClear',
		'fragmentEditorEnabled',
		'getAssetFieldValueURL',
		'getAssetMappingFieldsURL',
		'hoveredItemId',
		'hoveredItemType',
		'languageId',
		'layoutData',
		'mappingFieldsURL',
		'portletNamespace',
		'segmentsExperienceId',
		'selectedMappingTypes'
	]
);

Soy.register(ConnectedFragmentEditableField, templates);

export {ConnectedFragmentEditableField, FragmentEditableField};
export default ConnectedFragmentEditableField;
