/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/image_properties/FloatingToolbarImagePropertiesPanel.es';

import '../floating_toolbar/link/FloatingToolbarLinkPanel.es';

import '../floating_toolbar/mapping/FloatingToolbarMappingPanel.es';

import './FragmentEditableFieldTooltip.es';
import {UPDATE_CONFIG_ATTRIBUTES} from '../../actions/actions.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../../actions/saveChanges.es';
import {updateEditableValueContentAction} from '../../actions/updateEditableValue.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {
	shouldUpdateOnChangeProperties,
	shouldUpdatePureComponent
} from '../../utils/FragmentsEditorComponentUtils.es';
import {
	editableIsMapped,
	editableIsMappedToInfoItem,
	editableShouldBeHighlighted,
	getItemPath
} from '../../utils/FragmentsEditorGetUtils.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {computeEditableValue} from '../../utils/computeValues.es';
import {
	EDITABLE_FIELD_CONFIG_KEYS,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FLOATING_TOOLBAR_BUTTONS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	CREATE_PROCESSOR_EVENT_TYPES
} from '../../utils/constants';
import debouncedAlert from '../../utils/debouncedAlert.es';
import {isNullOrUndefined} from '../../utils/isNullOrUndefined.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import FragmentProcessors from '../fragment_processors/FragmentProcessors.es';
import templates from './FragmentEditableField.soy';

/**
 * @type {number}
 * @review
 */
const EDITABLE_FIELD_CHANGE_DELAY = 500;

/**
 * FragmentEditableField
 */
class FragmentEditableField extends PortletBase {
	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._clearEditor = this._clearEditor.bind(this);
		this._createProcessor = this._createProcessor.bind(this);
		this._handleEditableChanged = this._handleEditableChanged.bind(this);
		this._handleProcessorDestroyed = this._handleProcessorDestroyed.bind(
			this
		);
		this._handleFloatingToolbarButtonClicked = this._handleFloatingToolbarButtonClicked.bind(
			this
		);

		if (['link', 'rich-text', 'text'].includes(this.type)) {
			this._handleEditableChanged = debouncedAlert(
				this._handleEditableChanged.bind(this),
				EDITABLE_FIELD_CHANGE_DELAY
			);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._destroyProcessors();
		this._disposeFloatingToolbar();
		this.element.removeEventListener('click', this._createProcessor);
	}

	/**
	 * @inheritDoc
	 * @param {!object} state
	 * @returns {object}
	 */
	prepareStateForRender(state) {
		const segmentedValue = computeEditableValue(this.editableValues, {
			defaultLanguageId: this.defaultLanguageId,
			selectedExperienceId: this.segmentsExperienceId,
			selectedLanguageId: this.languageId
		});

		const value = this._mapped
			? isNullOrUndefined(this._mappedFieldValue)
				? this.editableValues.defaultValue
				: this._mappedFieldValue
			: segmentedValue;

		const processor =
			FragmentProcessors[this.type] || FragmentProcessors.fallback;

		const content = Soy.toIncDom(
			processor.render(this.content, value, this.editableValues)
		);

		const translated =
			!this._mapped && Boolean(segmentedValue[this.languageId]);

		let nextState = state;

		nextState = setIn(nextState, ['_translated'], translated);
		nextState = setIn(nextState, ['content'], content);

		return nextState;
	}

	/**
	 * @inheritDoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		if (this._processorEnabled) {
			return shouldUpdateOnChangeProperties(changes, [
				'_active',
				'languageId',
				'segmentsExperienceId'
			]);
		}

		return shouldUpdatePureComponent(changes);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	sync_active() {
		const eventName = this.type === 'image' ? 'dblclick' : 'click';

		if (this.hasUpdatePermissions && this._active) {
			this._createFloatingToolbar();

			this.element.addEventListener(eventName, this._createProcessor);
		} else {
			this._disposeFloatingToolbar();
			this._destroyProcessors();

			this.element.removeEventListener(eventName, this._createProcessor);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncEditableValues() {
		this._loadMappedFieldLabel();
		this._updateMappedFieldValue();

		if (!this._processorEnabled && this._active) {
			this._createFloatingToolbar();
		}
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
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		const processor =
			FragmentProcessors[this.type] || FragmentProcessors.fallback;

		let buttons = processor.getFloatingToolbarButtons(this.editableValues);

		if (this.selectedItems.length > 1) {
			buttons = buttons.map(button => {
				if (button.id === FLOATING_TOOLBAR_BUTTONS.map.id) {
					return button;
				}

				return {
					...button,

					cssClass: `${button.cssClass} disabled fragments-editor__floating-toolbar--disabled`
				};
			});
		}

		const config = {
			anchorElement: this.element,
			buttons,
			events: {
				buttonClicked: this._handleFloatingToolbarButtonClicked,
				clearEditor: this._clearEditor,
				createProcessor: this._createProcessor
			},
			item: {
				editableId: this.editableId,
				editableValues: this.editableValues,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				type: this.type
			},
			itemId: this._itemId,
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
			const floatingToolbar = this._floatingToolbar;

			this._floatingToolbar = null;

			requestAnimationFrame(() => {
				floatingToolbar.dispose();
			});
		}
	}

	/**
	 * Enables the corresponding processor
	 * @private
	 * @review
	 */
	_createProcessor(event, type = CREATE_PROCESSOR_EVENT_TYPES.editable) {
		if (event) {
			event.preventDefault();
		}

		if (
			!this._processorEnabled &&
			!this.editableValues.fieldId &&
			!this.editableValues.mappedField
		) {
			this._processorEnabled = true;

			this._disposeFloatingToolbar();

			const {init} =
				FragmentProcessors[this.type] || FragmentProcessors.fallback;

			init(
				this.refs.editable,
				this.fragmentEntryLinkId,
				this.portletNamespace,
				this.processorsOptions,
				this._handleEditableChanged,
				this._handleProcessorDestroyed,
				event,
				type
			);
		}
	}

	/**
	 * Callback executed when the exiting processor is destroyed
	 * @private
	 * @review
	 */
	_handleProcessorDestroyed() {
		this._processorEnabled = false;

		if (this._active) {
			this._createFloatingToolbar();
		}
	}

	/**
	 * Callback executed when an editable value changes
	 * @param {string} newValue
	 * @private
	 */
	_handleEditableChanged(newValue) {
		if (this.type === 'image') {
			this.store
				.dispatch(enableSavingChangesStatusAction())
				.dispatch({
					config: {
						[EDITABLE_FIELD_CONFIG_KEYS.imageSource]: newValue.url,
						[EDITABLE_FIELD_CONFIG_KEYS.imageTitle]: newValue.title
					},
					editableId: this.editableId,
					fragmentEntryLinkId: this.fragmentEntryLinkId,
					type: UPDATE_CONFIG_ATTRIBUTES
				})
				.dispatch(updateLastSaveDateAction())
				.dispatch(disableSavingChangesStatusAction());
		}

		this.store.dispatch(
			updateEditableValueContentAction(
				this.fragmentEntryLinkId,
				EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				this.editableId,
				newValue
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
		const {type} = data;

		if (type === 'editor') {
			this._createProcessor(event, CREATE_PROCESSOR_EVENT_TYPES.button);
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
			editableIsMappedToInfoItem(this.editableValues)
		) {
			this.fetch(this.getAssetFieldValueURL, {
				classNameId: this.editableValues.classNameId,
				classPK: this.editableValues.classPK,
				fieldId: this.editableValues.fieldId
			})
				.then(response => response.json())
				.then(response => {
					const {fieldValue} = response;

					if (!isNullOrUndefined(fieldValue)) {
						if (
							this.type === 'image' &&
							typeof fieldValue.url === 'string'
						) {
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
	_activable: Config.internal()
		.bool()
		.value(false),
	_active: Config.internal()
		.bool()
		.value(false),
	_floatingToolbar: Config.internal().value(null),
	_highlighted: Config.internal()
		.bool()
		.value(false),
	_hovered: Config.internal()
		.bool()
		.value(false),
	_itemId: Config.internal()
		.string()
		.value(''),
	_mapped: Config.internal()
		.bool()
		.value(false),
	_mappedFieldLabel: Config.internal().string(),
	_mappedFieldValue: Config.internal().string(),
	_mappedItemHovered: Config.internal()
		.bool()
		.value(false),
	_processorEnabled: Config.internal()
		.bool()
		.value(false),
	content: Config.string().required(),
	editableId: Config.string().required(),
	editableValues: Config.object().required(),
	fragmentEntryLinkId: Config.string().required(),
	processor: Config.string().required(),
	processorsOptions: Config.object().required(),
	type: Config.oneOf([
		'html',
		'image',
		'link',
		'rich-text',
		'text'
	]).required()
};

const ConnectedFragmentEditableField = getConnectedComponent(
	FragmentEditableField,
	[
		'activeItemId',
		'activeItemType',
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'getAssetFieldValueURL',
		'getAssetMappingFieldsURL',
		'hasUpdatePermissions',
		'hoveredItemId',
		'hoveredItemType',
		'languageId',
		'layoutData',
		'mappingFieldsURL',
		'portletNamespace',
		'segmentsExperienceId',
		'selectedMappingTypes',
		'selectedItems'
	],
	(state, props) => {
		const _itemId = `${props.fragmentEntryLinkId}-${props.editableId}`;

		const _activable =
			!state.hasUpdatePermissions ||
			getItemPath(
				state.activeItemId,
				state.activeItemType,
				state.layoutData.structure
			).some(
				({itemId, itemType}) =>
					itemId === props.fragmentEntryLinkId &&
					itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment
			);

		const _active =
			state.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable &&
			state.activeItemId === _itemId;

		const _highlighted = editableShouldBeHighlighted(
			state.activeItemId,
			state.activeItemType,
			props.fragmentEntryLinkId,
			state.layoutData.structure
		);

		const _hovered =
			state.hoveredItemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable &&
			state.hoveredItemId === _itemId;

		const _mapped = editableIsMapped(props.editableValues);

		const _mappedItemHovered =
			state.hoveredItemType === FRAGMENTS_EDITOR_ITEM_TYPES.mappedItem &&
			state.hoveredItemId ===
				`${props.editableValues.classNameId}-${props.editableValues.classPK}`;

		const _selected = state.selectedItems.some(
			selectedItem =>
				selectedItem.itemId === _itemId &&
				selectedItem.itemType === FRAGMENTS_EDITOR_ITEM_TYPES.editable
		);

		return {
			...state,
			_activable,
			_active,
			_highlighted,
			_hovered,
			_itemId,
			_mapped,
			_mappedItemHovered,
			_selected
		};
	}
);

Soy.register(ConnectedFragmentEditableField, templates);

export {ConnectedFragmentEditableField, FragmentEditableField};
export default ConnectedFragmentEditableField;
