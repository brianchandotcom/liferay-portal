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

import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import {shouldUpdateOnChangeProperties} from '../../utils/FragmentsEditorComponentUtils.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {getComputedEditableValue} from '../../utils/computeValues.es';
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
	 * @inheritDoc
	 */
	disposed() {
		this._destroyEditables();
	}

	/**
	 * @inheritDoc
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (state.languageId && Liferay.Language.direction) {
			nextState = setIn(
				nextState,
				['_languageDirection'],
				Liferay.Language.direction[state.languageId] || 'ltr'
			);
		}

		nextState = setIn(
			nextState,
			['content'],
			this.content ? Soy.toIncDom(this.content) : null
		);

		return nextState;
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		requestAnimationFrame(() => {
			if (this.content) {
				this._renderContent(this.content, {evaluateJs: true});
			}
		});
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdateOnChangeProperties(changes, [
			'content',
			'languageId',
			'segmentsExperienceId',
			'selectedMappingTypes'
		]);
	}

	/**
	 * Renders the content if it is changed.
	 * @inheritDoc
	 * @param {string} newContent The new content to render.
	 * @param {string} prevContent
	 */
	syncContent(newContent, prevContent) {
		if (newContent && newContent !== prevContent) {
			requestAnimationFrame(() => {
				this._renderContent(newContent, {evaluateJs: true});
			});
		}
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

			this._update({
				defaultLanguageId: this.defaultLanguageId,
				defaultSegmentsExperienceId: this.defaultSegmentsExperienceId,
				languageId: this.languageId,
				segmentsExperienceId: this.segmentsExperienceId,
				updateFunctions: []
			});
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
				this.defaultEditorConfigurations[
					editable.getAttribute('type')
				] || this.defaultEditorConfigurations.text;

			return new FragmentEditableField({
				content: editable.innerHTML,
				editableId: editable.id,
				editableValues,
				element: editable,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				processor: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,

				processorsOptions: {
					defaultEditorConfiguration,
					imageSelectorURL: this.imageSelectorURL
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
	 * @param {string} content
	 * @param {object} [options={}]
	 * @param {boolean} [options.evaluateJs]
	 * @private
	 * @review
	 */
	_renderContent(content, options = {}) {
		if (content && this.refs.content) {
			this.refs.content.innerHTML = content;

			if (options.evaluateJs) {
				globalEval.runScriptsInElement(this.refs.content);
			}

			if (this.editableValues) {
				this._createEditables();

				this._update({
					defaultLanguageId: this.defaultLanguageId,
					defaultSegmentsExperienceId: this
						.defaultSegmentsExperienceId,
					languageId: this.languageId,
					segmentsExperienceId: this.segmentsExperienceId,
					updateFunctions: []
				});
			}
		}
	}

	/**
	 * Runs a set of update functions through the editable values inside this
	 * fragment entry link.
	 * @param {string} languageId The current language ID.
	 * @param {string} defaultLanguageId The default language ID.
	 * @param {Array<Function>} updateFunctions The set of update functions to
	 * execute for each editable value.
	 * @private
	 */
	_update({
		defaultLanguageId,
		defaultSegmentsExperienceId: _defaultSegmentsExperienceId,
		languageId,
		segmentsExperienceId,
		updateFunctions
	}) {
		const editableValues =
			this.editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR] || {};

		Object.keys(editableValues).forEach(editableId => {
			const editableValue = editableValues[editableId];

			const {defaultValue, value} = getComputedEditableValue(
				editableValue,
				{
					defaultLanguageId,
					selectedExperienceId: segmentsExperienceId,
					selectedLanguageId: languageId
				}
			);

			const mappedField = editableValue.mappedField || '';

			updateFunctions.forEach(updateFunction =>
				updateFunction(editableId, value, defaultValue, mappedField)
			);
		});
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEntryLinkContent.STATE = {
	/**
	 * Fragment content to be rendered.
	 * @default ''
	 * @instance
	 * @memberOf FragmentEntryLink
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
	 * Editable values that should be used instead of the default ones inside
	 * editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLink
	 * @type {!Object}
	 */
	editableValues: Config.object().required(),

	/**
	 * Fragment entry link ID.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEntryLinkContent
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required()
};

const ConnectedFragmentEntryLinkContent = getConnectedComponent(
	FragmentEntryLinkContent,
	[
		'defaultEditorConfigurations',
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'imageSelectorURL',
		'languageId',
		'portletNamespace',
		'selectedMappingTypes',
		'segmentsExperienceId',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkContent, templates);

export {ConnectedFragmentEntryLinkContent, FragmentEntryLinkContent};
export default ConnectedFragmentEntryLinkContent;
