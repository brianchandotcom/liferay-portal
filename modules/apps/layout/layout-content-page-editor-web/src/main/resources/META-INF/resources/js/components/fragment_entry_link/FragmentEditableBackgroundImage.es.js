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

import Component from 'metal-component';
import {Config} from 'metal-state';

import {Store} from '../../store/store.es';

import '../floating_toolbar/fragment_background_image/FloatingToolbarFragmentBackgroundImagePanel.es';
import {updateEditableValueContentAction} from '../../actions/updateEditableValue.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {openImageSelector} from '../../utils/FragmentsEditorDialogUtils';
import {getAssetFieldValue} from '../../utils/FragmentsEditorFetchUtils.es';
import {
	editableShouldBeHighlighted,
	editableIsMapped,
	editableIsMappedToInfoItem
} from '../../utils/FragmentsEditorGetUtils.es';
import {computeEditableValue} from '../../utils/computeValues.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	FLOATING_TOOLBAR_BUTTONS,
	FRAGMENTS_EDITOR_ITEM_TYPES
} from '../../utils/constants';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import EditableBackgroundImageProcessor from '../fragment_processors/EditableBackgroundImageProcessor.es';
import FragmentProcessors from '../fragment_processors/FragmentProcessors.es';

/**
 * FragmentEditableBackgroundImage
 */
class FragmentEditableBackgroundImage extends Component {
	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleFloatingToolbarButtonClicked = this._handleFloatingToolbarButtonClicked.bind(
			this
		);

		this._handleEditableBackgroundImageClick = this._handleEditableBackgroundImageClick.bind(
			this
		);

		this.element.classList.add(
			'fragments-editor__background-image-editable'
		);

		this.element.addEventListener(
			'dblclick',
			this._handleEditableBackgroundImageClick
		);

		this._setEditableAttributes();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._disposeFloatingToolbar();

		if (this.element) {
			this.element.removeEventListener(
				'dblclick',
				this._handleEditableBackgroundImageClick
			);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncActiveItemId() {
		if (
			this.hasUpdatePermissions &&
			this.activeItemId === this._getItemId() &&
			this.activeItemType ===
				FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		) {
			this._active = true;
			this.element.classList.add(
				'fragments-editor__background-image-editable--active'
			);
			this._createFloatingToolbar();
		} else {
			this._active = false;
			this.element.classList.remove(
				'fragments-editor__background-image-editable--active'
			);
			this._disposeFloatingToolbar();
		}

		this._setHighlightedState();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncEditableValues(editableValues) {
		if (this._floatingToolbar) {
			this._createFloatingToolbar();
		}

		if (editableIsMapped(editableValues)) {
			this._updateMappedFieldValue();
			this.element.classList.add(
				'fragments-editor__background-image-editable--mapped'
			);
		} else {
			this._renderBackgroundImage();
			this.element.classList.remove(
				'fragments-editor__background-image-editable--mapped'
			);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncDefaultLanguageId() {
		this._renderBackgroundImage();
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
	 * @inheritDoc
	 * @review
	 */
	syncHoveredItemId() {
		if (
			this.hoveredItemId === this._getItemId() &&
			this.hoveredItemType ===
				FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		) {
			this.element.classList.add(
				'fragments-editor__background-image-editable--hovered'
			);
		} else {
			this.element.classList.remove(
				'fragments-editor__background-image-editable--hovered'
			);
		}

		this._setHighlightedState();
	}

	/**
	 * Creates tooltip instance
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		const processor = FragmentProcessors['backgroundImage'];

		const config = {
			anchorElement: this.element,
			buttons: processor.getFloatingToolbarButtons(this.editableValues),
			events: {
				buttonClicked: this._handleFloatingToolbarButtonClicked
			},
			item: {
				backgroundImage: this._getBackgroundImageValue(),
				editableId: this.editableId,
				editableValues: this.editableValues,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				type: 'image'
			},
			itemId: this._getItemId(),
			itemType: FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable,
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
	 * Get background image translated value
	 * @private
	 * @review
	 */
	_getBackgroundImageValue() {
		const segmentedValue = computeEditableValue(this.editableValues, {
			defaultLanguageId: this.defaultLanguageId,
			selectedExperienceId: this.segmentsExperienceId,
			selectedLanguageId: this.languageId
		});

		return segmentedValue;
	}

	/**
	 * @private
	 * @return {string} Valid FragmentsEditor itemId for its
	 * 	fragmentEntryLinkId and editableId
	 * @review
	 */
	_getItemId() {
		return `${this.fragmentEntryLinkId}-${this.editableId}`;
	}

	/**
	 * Callback executed when the background image editable is clicked
	 * @param {Event} event
	 * @private
	 */
	_handleEditableBackgroundImageClick(event) {
		const item = event.target.closest('[data-fragments-editor-item-id]');

		if (
			this.hasUpdatePermissions &&
			item === this.element &&
			this._active
		) {
			openImageSelector(image =>
				this._updateFragmentBackgroundImage(image)
			);
		}
	}

	/**
	 * Callback executed when an floating toolbar button is clicked
	 * @param {Event} event
	 * @param {Object} data
	 * @private
	 */
	_handleFloatingToolbarButtonClicked(event, data) {
		const {panelId} = data;

		if (
			!this._getBackgroundImageValue() &&
			panelId === FLOATING_TOOLBAR_BUTTONS.fragmentBackgroundImage.panelId
		) {
			openImageSelector(image =>
				this._updateFragmentBackgroundImage(image)
			);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_renderBackgroundImage() {
		const backgroundImageValue = editableIsMapped(this.editableValues)
			? this._mappedFieldValue
			: this._getBackgroundImageValue();

		EditableBackgroundImageProcessor.render(
			this.element,
			backgroundImageValue
		);
	}

	/**
	 * @private
	 * @review
	 */
	_setEditableAttributes() {
		this.element.setAttribute(
			'data-fragments-editor-item-id',
			this._getItemId()
		);

		this.element.setAttribute(
			'data-fragments-editor-item-type',
			FRAGMENTS_EDITOR_ITEM_TYPES.backgroundImageEditable
		);

		this.element.setAttribute(
			'data-fragment-entry-link-id',
			this.fragmentEntryLinkId
		);
	}

	/**
	 * Add highlighted class to the editable if necessary
	 * @private
	 * @review
	 */
	_setHighlightedState() {
		if (
			editableShouldBeHighlighted(
				this.activeItemId,
				this.activeItemType,
				this.fragmentEntryLinkId,
				this.layoutData.structure
			)
		) {
			this.element.classList.add(
				'fragments-editor__background-image-editable--highlighted'
			);
		} else {
			this.element.classList.remove(
				'fragments-editor__background-image-editable--highlighted'
			);
		}
	}

	/**
	 * Dispatches action to update editableValues with new background image url
	 * @param {string} backgroundImageURL
	 */
	_updateFragmentBackgroundImage(image) {
		this.store.dispatch(
			updateEditableValueContentAction(
				this.fragmentEntryLinkId,
				BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
				this.editableId,
				image
			)
		);
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
			getAssetFieldValue(
				this.editableValues.classNameId,
				this.editableValues.classPK,
				this.editableValues.fieldId
			).then(response => {
				const {fieldValue} = response;

				if (fieldValue) {
					this._mappedFieldValue = fieldValue.url;

					this._renderBackgroundImage();
				}
			});
		}
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEditableBackgroundImage.STATE = {
	/**
	 * Wether the editable is active
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @private
	 * @review
	 * @type {string}
	 */
	_active: Config.internal()
		.bool()
		.value(false),

	/**
	 * Mapped asset field value
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @private
	 * @review
	 * @type {string}
	 */
	_mappedFieldValue: Config.internal().string(),

	/**
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	editableId: Config.string().required(),

	/**
	 * Editable values that should be used instead of the default ones inside
	 * editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {!Object}
	 */
	editableValues: Config.object().required(),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	processor: Config.string().required(),

	/**
	 * Store instance.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {Store}
	 */
	store: Config.instanceOf(Store)
};

const ConnectedFragmentEditableBackgroundImage = getConnectedComponent(
	FragmentEditableBackgroundImage,
	[
		'activeItemId',
		'activeItemType',
		'hasUpdatePermissions',
		'hoveredItemId',
		'hoveredItemType',
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'getAssetFieldValueURL',
		'languageId',
		'layoutData',
		'mappingFieldsURL',
		'segmentsExperienceId',
		'selectedMappingTypes'
	]
);

export {
	ConnectedFragmentEditableBackgroundImage,
	FragmentEditableBackgroundImage
};
export default ConnectedFragmentEditableBackgroundImage;
