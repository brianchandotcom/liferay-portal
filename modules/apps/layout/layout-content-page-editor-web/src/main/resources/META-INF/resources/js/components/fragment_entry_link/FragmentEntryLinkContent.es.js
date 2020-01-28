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
import {closest, globalEval} from 'metal-dom';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {onPropertiesChanged} from '../../utils/FragmentsEditorComponentUtils.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
import FragmentEditableBackgroundImage from './FragmentEditableBackgroundImage.es';
import FragmentEditableField from './FragmentEditableField.es';
import templates from './FragmentEntryLinkContent.soy';

/**
 * Creates a Fragment Entry Link Content component.
 * @review
 */
class FragmentEntryLinkContent extends Component {
	/**
	 * @inheritdoc
	 */
	created() {
		onPropertiesChanged(this, ['_languageId'], () => {
			if (this.refs.content) {
				if (Liferay.Language && Liferay.Language.direction) {
					this.refs.content.dir =
						Liferay.Language.direction[this._languageId] || 'ltr';
				}

				this.refs.content.lang = this._languageId;
			}
		});

		onPropertiesChanged(
			this,
			['_defaultEditorConfigurations', 'content'],
			() => {
				this._renderContent(this.content, {evaluateJs: true});
			}
		);
	}

	/**
	 * @inheritDoc
	 */
	disposed() {
		this._destroyEditables();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		this._renderContent(this.content, {evaluateJs: true});
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate() {
		return false;
	}

	/**
	 * Handles changes to editable values.
	 * @inheritDoc
	 * @param {object} newEditableValues The updated values.
	 * @param {object} oldEditableValues The original values.
	 */
	syncEditableValues(newEditableValues, oldEditableValues) {
		if (newEditableValues !== oldEditableValues) {
			if (this._editables) {
				this._editables.forEach(editable => {
					const editableValues =
						newEditableValues[editable.processor] &&
						newEditableValues[editable.processor][
							editable.editableId
						]
							? newEditableValues[editable.processor][
									editable.editableId
							  ]
							: {
									defaultValue: editable.content
							  };

					editable.editableValues = editableValues;
				});
			}
		}
	}

	/**
	 * Propagates the store to editable fields when it's loaded.
	 */
	syncStore() {
		if (this._editables) {
			this._editables.forEach(editable => {
				editable.store = this.store;
			});
		}
	}

	/**
	 * Creates instances of a fragment editable field for each editable.
	 */
	_createEditables() {
		this._destroyEditables();

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

			return new FragmentEditableBackgroundImage({
				editableId,
				editableValues,
				element,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				processor: BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
				store: this.store
			});
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

			const defaultEditorConfiguration =
				this._defaultEditorConfigurations[
					editable.getAttribute('type')
				] || this._defaultEditorConfigurations.text;

			return new FragmentEditableField({
				content: editable.innerHTML,
				editableId: editable.id,
				editableValues,
				element: editable,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				processor: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,

				processorsOptions: {
					defaultEditorConfiguration,
					imageSelectorURL: this._imageSelectorURL
				},

				segmentsExperienceId: this.segmentsExperienceId,
				store: this.store,
				type: editable.getAttribute('type')
			});
		});

		this._editables = [...backgroundImageEditables, ...editableFields];
	}

	/**
	 * Destroys existing fragment editable field instances.
	 */
	_destroyEditables() {
		if (this._editables) {
			this._editables.forEach(editable => editable.dispose());

			this._editables = [];
		}
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleFragmentEntryLinkContentClick(event) {
		const element = event.srcElement;

		if (
			closest(element, '[href]') &&
			!('lfrPageEditorHrefEnabled' in element.dataset)
		) {
			event.preventDefault();
		}
	}

	/**
	 * Parses and renders the fragment entry link content with AUI.
	 * @param {string|object} content
	 * @param {object} [options={}]
	 * @param {boolean} [options.evaluateJs]
	 * @private
	 * @review
	 */
	_renderContent(content, options = {}) {
		let parsedContent = content;

		if (
			parsedContent &&
			parsedContent.value &&
			parsedContent.value.content
		) {
			parsedContent = parsedContent.value.content;
		}

		if (parsedContent && this.refs.content) {
			this.refs.content.innerHTML = parsedContent;

			if (options.evaluateJs) {
				globalEval.runScriptsInElement(this.refs.content);
			}

			if (this._defaultEditorConfigurations && this.editableValues) {
				this._createEditables();
			}
		}
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEntryLinkContent.STATE = {
	_defaultEditorConfigurations: Config.internal()
		.object()
		.value(null),
	_imageSelectorURL: Config.internal()
		.string()
		.value(''),
	_languageId: Config.internal()
		.string()
		.value(''),
	_segmentsExperienceId: Config.internal()
		.string()
		.value(''),
	_selectedMappingTypes: Config.internal().value(null),
	content: Config.any().value(''),
	editableValues: Config.object().required(),
	fragmentEntryLinkId: Config.string().required()
};

const ConnectedFragmentEntryLinkContent = getConnectedComponent(
	FragmentEntryLinkContent,
	[],
	state => {
		return {
			_defaultEditorConfigurations: state.defaultEditorConfigurations,
			_imageSelectorURL: state.imageSelectorURL,
			_languageId: state.languageId,
			_segmentsExperienceId: state.segmentsExperienceId,
			_selectedMappingTypes: state.selectedMappingTypes
		};
	}
);

Soy.register(ConnectedFragmentEntryLinkContent, templates);

export {ConnectedFragmentEntryLinkContent, FragmentEntryLinkContent};
export default ConnectedFragmentEntryLinkContent;
