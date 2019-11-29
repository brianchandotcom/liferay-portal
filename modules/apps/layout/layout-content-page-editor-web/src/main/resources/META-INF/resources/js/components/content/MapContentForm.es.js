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

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {getItemPath} from '../../utils/FragmentsEditorGetUtils.es';
import {computeEditableValue} from '../../utils/computeValues.es';
import {
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_TYPES
} from '../../utils/constants';
import templates from './MapContentForm.soy';

/**
 * @param {string} html Removes HTML from the given text
 * @return {string} Plain text
 * @review
 */
const stripHTML = html => {
	const div = document.createElement('div');

	div.innerHTML = html;

	return div.innerText;
};

/**
 * MapContentForm
 */
class MapContentForm extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	prepareStateForRender(state) {
		if (state._structureLabel === '') {
			state._structureLabel = Liferay.Util.sub(
				Liferay.Language.get(
					'map-the-selected-content-to-the-x-structure-fields'
				),
				this.ddmStructure.label
			);
		}

		return state;
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncSelectedItems() {
		if (this.selectedItems) {
			this.selectedItems = this.selectedItems
				.filter(selectedItem => {
					return (
						selectedItem.itemType ===
						FRAGMENTS_EDITOR_ITEM_TYPES.editable
					);
				})
				.map(selectedItem => {
					const itemPath = getItemPath(
						selectedItem.itemId,
						selectedItem.itemType,
						this.layoutData.structure
					);

					const fragmentEntryLinkId = itemPath.find(
						activeItem =>
							activeItem.itemType ===
							FRAGMENTS_EDITOR_ITEM_TYPES.fragment
					).itemId;

					const fragmentEntryLink = this.fragmentEntryLinks[
						fragmentEntryLinkId
					];

					const editableValues =
						fragmentEntryLink.editableValues[
							EDITABLE_FRAGMENT_ENTRY_PROCESSOR
						] || {};

					const editableId = selectedItem.itemId
						.split('-')
						.slice(1)
						.join('-');

					selectedItem.editableId = editableId;
					selectedItem.fragmentEntryLinkId = fragmentEntryLinkId;
					selectedItem.itemValue = computeEditableValue(
						editableValues[editableId],
						{
							defaultLanguageId: this.defaultLanguageId,
							selectedExperienceId: this.segmentsExperienceId,
							selectedLanguageId: this.languageId
						}
					);

					if (selectedItem.itemValue.url) {
						selectedItem.itemValue = selectedItem.itemValue.url;
						selectedItem.displayValue = stripHTML(
							selectedItem.itemValue.title
								? selectedItem.itemValue.title
								: selectedItem.itemValue
						);
					}
					else {
						selectedItem.displayValue = stripHTML(
							selectedItem.itemValue
						);
					}

					return selectedItem;
				});
		}
	}

	_handleFieldChange(event) {
		const targetElement = event.delegateTarget;

		const fieldKey =
			targetElement.options[targetElement.selectedIndex].value;
		const editableId = targetElement.dataset.itemEditableId;
		const fragmentEntryLinkId =
			targetElement.dataset.itemFragmentEntryLinkId;

		const newFields = this.fields.map(field => {
			let newField = {...field};

			if (
				(fieldKey === '-' || field.key !== fieldKey) &&
				field.editableId === editableId &&
				field.fragmentEntryLinkId === fragmentEntryLinkId
			) {
				newField = {
					disabled: false,
					key: field.key,
					label: field.label,
					type: field.type
				};
			}
			else if (fieldKey !== '-' && field.key === fieldKey) {
				newField.disabled = true;
				newField.editableId = editableId;
				newField.fragmentEntryLinkId = fragmentEntryLinkId;
			}

			return newField;
		});

		this.emit('fieldsChanged', {
			fields: newFields,
			serializedFields: this._getSerializedFields(newFields)
		});
	}

	_getSerializedFields(fields) {
		const ddmForm = {
			availableLanguagesIds: [this.languageId],
			defaultLanguageId: this.languageId,
			fieldValues: fields.map(field => {
				let itemValue = '';

				this.selectedItems.forEach(selectedItem => {
					if (
						selectedItem.editableId === field.editableId &&
						selectedItem.fragmentEntryLinkId ===
							field.fragmentEntryLinkId
					) {
						itemValue = selectedItem.itemValue;
					}
				});

				return {
					name: field.key,
					value: itemValue
				};
			})
		};

		return JSON.stringify(ddmForm);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
MapContentForm.STATE = {
	/**
	 * Selected structure label
	 * @default null
	 * @instance
	 * @memberOf MapContentForm
	 * @private
	 * @review
	 * @type {string}
	 */
	_structureLabel: Config.string().value(''),

	/**
	 * @default undefined
	 * @instance
	 * @memberOf MapContentForm
	 * @review
	 * @type {object}
	 */
	ddmStructure: Config.shapeOf({
		id: Config.string().required(),
		label: Config.string().required()
	}),

	/**
	 * Error message
	 * @default ''
	 * @instance
	 * @memberOf MapContentForm
	 * @private
	 * @review
	 * @type {string}
	 */
	errorMessage: Config.string().value(null),

	/**
	 * List of available structure fields
	 * @default null
	 * @instance
	 * @memberOf MapContentForm
	 * @private
	 * @review
	 * @type {Array<{key: !string, label: !string, type: !string}>}
	 */
	fields: Config.arrayOf(
		Config.shapeOf({
			disabled: Config.bool().value(false),
			editableId: Config.string().value(''),
			fragmentEntryLinkId: Config.string().value(null),
			key: Config.string().required(),
			label: Config.string().required(),
			type: Config.string().required()
		})
	).value(null)
};

const ConnectedMapContentForm = getConnectedComponent(MapContentForm, [
	'defaultLanguageId',
	'defaultSegmentsExperienceId',
	'fragmentEntryLinks',
	'languageId',
	'layoutData',
	'portletNamespace',
	'savingChanges',
	'segmentsExperienceId',
	'selectedItems'
]);

Soy.register(ConnectedMapContentForm, templates);

export {ConnectedMapContentForm, MapContentForm};
export default ConnectedMapContentForm;
