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

import {Config} from 'metal-state';
import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './MapContentForm.soy';
import {
	COMPATIBLE_TYPES,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_TYPES
} from '../../utils/constants';
import {getItemPath} from '../../utils/FragmentsEditorGetUtils.es';
import {getContentStructureMappingFields} from '../../utils/FragmentsEditorFetchUtils.es';

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
	syncDdmStructure() {
		if (this.ddmStructure) {
			this._fields = null;

			getContentStructureMappingFields(this.ddmStructure.id).then(
				response => {
					const compatibleTypes = {};

					Object.keys(COMPATIBLE_TYPES).forEach(editableType => {
						const ddmTypes = COMPATIBLE_TYPES[editableType];

						ddmTypes.forEach(ddmType => {
							compatibleTypes[ddmType] = editableType;
						});
					});

					const fields = [
						{
							key: '-',
							label: Liferay.Language.get('unmapped'),
							type: '-'
						}
					];

					response.forEach(field => {
						field.type = compatibleTypes[field.type];

						fields.push(field);
					});

					this._fields = fields;
				}
			);
		}
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncSelectedItems() {
		if (this.selectedItems) {
			this.selectedItems.map(selectedItem => {
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

				const languageId = this.languageId || this.defaultLanguageId;
				const segmentsExperienceId = `segments-experience-id-${this
					.segmentsExperienceId || this.defaultSegmentsExperienceId}`;

				const editableId = selectedItem.itemId
					.split('-')
					.slice(1)
					.join('-');

				selectedItem.editableId = editableId;
				selectedItem.fragmentEntryLinkId = fragmentEntryLinkId;
				selectedItem.itemValue = editableValues[editableId][
					segmentsExperienceId
				]
					? editableValues[editableId][segmentsExperienceId][
							languageId
					  ]
					: editableValues[editableId].defaultValue.trim();

				return selectedItem;
			});
		}
	}

	_handleFieldChange(event) {
		const targetElement = event.delegateTarget;

		const fieldKey =
			targetElement.options[targetElement.selectedIndex].value;
		const editableId = targetElement.dataset.itemEditableId;

		if (fieldKey === '-') {
			this._fields = this._fields.map(field => {
				if (field.editableId === editableId) {
					delete field.editableId;
				}

				return field;
			});
		} else {
			this._fields = this._fields.map(field => {
				if (field.key === fieldKey) {
					field.editableId = editableId;
				}

				return field;
			});
		}
	}

	getSerializedFields() {
		const ddmForm = {
			availableLanguagesIds: [this.languageId],
			defaultLanguageId: this.languageId,
			fieldValues: this._fields.map(field => {
				let itemValue = '';

				this.selectedItems.forEach(selectedItem => {
					if (selectedItem.editableId === field.editableId) {
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
	 * List of available structure fields
	 * @default null
	 * @instance
	 * @memberOf MapContentForm
	 * @private
	 * @review
	 * @type {Array<{
	 *   key: !string,
	 *   label: !string,
	 *   type: !string
	 * }>}
	 */
	_fields: Config.arrayOf(
		Config.shapeOf({
			editableId: Config.string().value(''),
			key: Config.string().required(),
			label: Config.string().required(),
			type: Config.string().required()
		})
	)
		.internal()
		.value(null),

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
	})
};

const ConnectedMapContentForm = getConnectedComponent(MapContentForm, [
	'defaultLanguageId',
	'defaultSegmentsExperienceId',
	'fragmentEntryLinks',
	'languageId',
	'layoutData',
	'portletNamespace',
	'segmentsExperienceId',
	'selectedItems'
]);

Soy.register(ConnectedMapContentForm, templates);

export {ConnectedMapContentForm, MapContentForm};
export default ConnectedMapContentForm;
