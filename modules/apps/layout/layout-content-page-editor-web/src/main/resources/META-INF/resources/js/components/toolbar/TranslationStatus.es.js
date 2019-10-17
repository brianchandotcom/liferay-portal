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
import Soy from 'metal-soy';

import {CHANGE_LANGUAGE_ID} from '../../actions/actions.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
import {prefixSegmentsExperienceId} from '../../utils/prefixSegmentsExperienceId.es';
import templates from './TranslationStatus.soy';

/**
 * TranslationStatus
 */
class TranslationStatus extends Component {
	/**
	 * @param {object} editableValue
	 * @param {string} languageId
	 * @param {string} segmentExperienceId
	 * @return {boolean}
	 */
	static _editableValueIsTranslated(
		editableValue,
		languageId,
		segmentExperienceId
	) {
		return (
			editableValue[languageId] ||
			(segmentExperienceId in editableValue &&
				editableValue[segmentExperienceId][languageId])
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		const editableValues = Object.values(state.fragmentEntryLinks)
			.filter(fragmentEntryLink => fragmentEntryLink.editableValues)
			.map(fragmentEntryLink => [
				...Object.values(
					fragmentEntryLink.editableValues[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					] || {}
				),
				...Object.values(
					fragmentEntryLink.editableValues[
						BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
					] || {}
				)
			])
			.reduce(
				(editableValuesA, editableValuesB) => [
					...editableValuesA,
					...editableValuesB
				],
				[]
			);

		nextState = setIn(nextState, ['translationStatus'], {
			languageValues: Object.keys(state.availableLanguages).map(
				languageId => ({
					languageId,
					values: editableValues.filter(editableValue =>
						TranslationStatus._editableValueIsTranslated(
							editableValue,
							languageId,
							prefixSegmentsExperienceId(
								state.segmentsExperienceId ||
									state.defaultSegmentsExperienceId
							)
						)
					)
				})
			),

			translationKeys: editableValues
		});

		nextState.translationStatus.languageValues.sort(
			(languageValueA, languageValueB) =>
				languageValueB.values.length - languageValueA.values.length
		);

		return nextState;
	}

	/**
	 * Handles a click on a language item to notify parent components that a
	 * change in the selected language needs to be initiated.
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleLanguageChange(event) {
		event.preventDefault();

		this.store.dispatch({
			type: CHANGE_LANGUAGE_ID,
			value: event.delegateTarget.getAttribute('data-languageid')
		});
	}
}

const ConnectedTranslationStatus = getConnectedComponent(TranslationStatus, [
	'availableLanguages',
	'defaultLanguageId',
	'defaultSegmentsExperienceId',
	'fragmentEntryLinks',
	'languageId',
	'segmentsExperienceId',
	'spritemap'
]);

Soy.register(ConnectedTranslationStatus, templates);

export {ConnectedTranslationStatus};
export default ConnectedTranslationStatus;
