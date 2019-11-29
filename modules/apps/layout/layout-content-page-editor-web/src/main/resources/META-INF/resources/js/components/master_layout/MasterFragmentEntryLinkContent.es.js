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

import {isFunction, isObject} from 'metal';
import Component from 'metal-component';
import {closest, globalEval} from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {editableIsMapped} from '../../utils/FragmentsEditorGetUtils.es';
import {computeEditableValue} from '../../utils/computeValues.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
import EditableBackgroundImageFragmentProcessor from '../fragment_processors/EditableBackgroundImageProcessor.es';
import FragmentProcessors from '../fragment_processors/FragmentProcessors.es';
import templates from './MasterFragmentEntryLinkContent.soy';

class MasterFragmentEntryLinkContent extends Component {
	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		requestAnimationFrame(() => {
			if (this.content) {
				this._renderContent(this.content);
			}
		});
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	syncLanguageId(newLanguageId) {
		if (!this._editables || !newLanguageId || !this.defaultLanguageId) {
			return;
		}

		this._editables.forEach(editable => {
			const mapped = editableIsMapped(editable.editableValues);

			if (!mapped) {
				const value = computeEditableValue(editable.editableValues, {
					defaultLanguageId: this.defaultLanguageId,
					selectedLanguageId: newLanguageId
				});

				if (editable.type === 'backgroundImage') {
					const value = computeEditableValue(
						editable.editableValues,
						{
							defaultLanguageId: this.defaultLanguageId,
							selectedLanguageId: newLanguageId
						}
					);

					EditableBackgroundImageFragmentProcessor.render(
						editable.element,
						value
					);
				}
				else {
					const processor =
						FragmentProcessors[editable.type] ||
						FragmentProcessors.fallback;

					editable.element.innerHTML = processor.render(
						editable.content,
						value,
						editable.editableValues
					);
				}
			}
		});
	}

	/**
	 * Prevents clicks on links
	 * @private
	 * @review
	 */
	_handleMasterFragmentEntryLinkContentClick(event) {
		const element = event.srcElement;

		if (closest(element, '[href]')) {
			event.preventDefault();
		}
	}

	_renderContent(content) {
		if (content && this.refs.content) {
			this.refs.content.innerHTML = content;

			globalEval.runScriptsInElement(this.refs.content);

			if (this.editableValues) {
				this._createEditables();
			}
		}
	}

	/**
	 * Creates instances of a fragment editable field for each editable.
	 */
	_createEditables() {
		const backgroundImageEditables = Array.from(
			this.refs.content.querySelectorAll('[data-lfr-background-image-id]')
		).map(element => {
			const editableId = element.dataset.lfrBackgroundImageId;
			const editableValues =
				this.editableValues[
					BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
				] &&
				this.editableValues[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR][
					editableId
				]
					? this.editableValues[
							BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
					  ][editableId]
					: {
							defaultValue: ''
					  };

			return {
				editableValues,
				element,
				type: 'backgroundImage'
			};
		});

		const editableFields = Array.from(
			this.refs.content.querySelectorAll('lfr-editable')
		).map(editable => {
			const editableValues =
				this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR] &&
				this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
					editable.id
				]
					? this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR][
							editable.id
					  ]
					: {
							defaultValue: editable.innerHTML
					  };

			return {
				content: editable.innerHTML,
				editableValues,
				element: editable,
				type: editable.getAttribute('type')
			};
		});

		this._editables = [...backgroundImageEditables, ...editableFields];
	}
}

MasterFragmentEntryLinkContent.STATE = {
	/**
	 * Fragment content to be rendered.
	 * @default ''
	 * @instance
	 * @memberOf MasterFragmentEntryLinkContent
	 * @type {string}
	 */
	content: Config.any()
		.setter(content => {
			return !isFunction(content) && isObject(content)
				? content.value.content
				: content;
		})
		.value(''),
	/**
	 * Editable values
	 * @default undefined
	 * @instance
	 * @memberOf MasterFragmentEntryLinkContent
	 * @review
	 * @type {!object}
	 */
	editableValues: Config.object().required()
};

const ConnectedFragmentEditableField = getConnectedComponent(
	MasterFragmentEntryLinkContent,
	['defaultLanguageId', 'languageId']
);

Soy.register(ConnectedFragmentEditableField, templates);

export {ConnectedFragmentEditableField, MasterFragmentEntryLinkContent};
export default ConnectedFragmentEditableField;
