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

import {
	updateEditableValueContentAction,
	updateEditableValueMappedFieldAction,
	updateEditableValueFieldIdAction,
	updateFragmentConfigurationAction
} from '../../../src/main/resources/META-INF/resources/js/actions/updateEditableValue.es';
import {
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
} from '../../../src/main/resources/META-INF/resources/js/utils/constants';
import {prefixSegmentsExperienceId} from '../../../src/main/resources/META-INF/resources/js/utils/prefixSegmentsExperienceId.es';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/utils/FragmentsEditorFetchUtils.es',
	() => ({
		updateEditableValues: jest.fn((fragmentEntryLinkId, editableValues) =>
			Promise.resolve(fragmentEntryLinkId, editableValues)
		)
	})
);

describe('updateEditableValuesAction', () => {
	let editableValues;
	const fragmentId = 'sampleFragmentId';
	let state = {};

	const dispatch = thunk =>
		thunk(
			action => {
				if (action.editableValues) {
					editableValues = action.editableValues;
				}
			},
			() => state
		);

	const languageId = 'es';
	const segmentId = prefixSegmentsExperienceId('1');

	beforeEach(() => {
		editableValues = {
			[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {
				fieldIdSample: {
					classNameId: 'classNameId.jpg',
					classPK: 'classPK.jpg',
					config: {},
					defaultValue: 'defaultValue.jpg',
					fieldId: 'fieldId.jpg'
				},

				mappedFieldSample: {
					config: {},
					defaultValue: 'defaultValue.jpg',
					mappedField: 'mappedField.jpg'
				},

				sample: {
					config: {},
					defaultValue: 'defaultValue.jpg',

					people: {
						es: 'people/es.jpg',
						uruk: 'people/uruk.jpg'
					},

					[segmentId]: {
						uruk: 'segmentId/uruk.jpg'
					}
				}
			},

			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				fieldIdSample: {
					classNameId: 'classNameId',
					classPK: 'classPK',
					config: {},
					defaultValue: 'defaultValue',
					fieldId: 'fieldId'
				},

				mappedFieldSample: {
					config: {},
					defaultValue: 'defaultValue',
					mappedField: 'mappedField'
				},

				sample: {
					config: {},
					defaultValue: 'defaultValue',

					people: {
						es: 'people/es',
						uruk: 'people/uruk'
					},

					[segmentId]: {
						uruk: 'segmentId/uruk'
					}
				}
			},

			[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
				people: {
					sampleConfig: 'people/sampleConfig'
				},

				[segmentId]: {
					sampleConfig: 'segmentId/sampleConfig'
				}
			}
		};

		state = {
			availableLanguages: {en: '', es: '', uruk: ''},
			availableSegmentsExperiences: {0: '', 1: '', people: ''},

			defaultLanguageId: 'en',
			defaultSegmentsExperienceId: '0',

			fragmentEntryLinks: {
				[fragmentId]: {
					editableValues
				}
			},

			languageId: 'es',
			segmentsExperienceId: '1'
		};
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	describe('updateEditableValueContentAction', () => {
		it('removes everything from the editable but existing segments/languages, config and defaultValue', () => {
			dispatch(
				updateEditableValueContentAction(
					fragmentId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					'fieldIdSample',
					'someNewValue'
				)
			);

			expect(
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR].fieldIdSample
			).toEqual({
				config: {},
				defaultValue: 'defaultValue',

				[segmentId]: {
					[languageId]: 'someNewValue'
				}
			});
		});

		it('ignores segmentsExperienceId when it is undefined', () => {
			delete state.defaultSegmentsExperienceId;
			delete state.segmentsExperienceId;

			dispatch(
				updateEditableValueContentAction(
					fragmentId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					'myFieldId',
					'myNewValue'
				)
			);

			expect(
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR].myFieldId
			).toEqual({
				[languageId]: 'myNewValue'
			});
		});

		it('keeps existing translations and segments', () => {
			dispatch(
				updateEditableValueContentAction(
					fragmentId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					'sample',
					'someNewValue'
				)
			);

			expect(
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR].sample
			).toEqual({
				config: {},
				defaultValue: 'defaultValue',

				people: {
					es: 'people/es',
					uruk: 'people/uruk'
				},

				[segmentId]: {
					[languageId]: 'someNewValue',
					uruk: 'segmentId/uruk'
				}
			});
		});
	});

	describe('updateEditableValueMappedFieldAction', () => {
		it('removes everything from the editable but mappedField, config and defaultValue', () => {
			dispatch(
				updateEditableValueMappedFieldAction(
					fragmentId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					'sample',
					'newMappedField'
				)
			);

			expect(
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR].sample
			).toEqual({
				config: {},
				defaultValue: 'defaultValue',
				mappedField: 'newMappedField'
			});
		});
	});

	describe('updateEditableValueFieldIdAction', () => {
		it('removes everything from the editable but defaultValue, config, classNameId, classPK and fieldId', () => {
			dispatch(
				updateEditableValueFieldIdAction(
					fragmentId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					'sample',
					{
						classNameId: 'classNameId',
						classPK: 'classPK',
						fieldId: 'fieldId'
					}
				)
			);

			expect(
				editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR].sample
			).toEqual({
				classNameId: 'classNameId',
				classPK: 'classPK',
				config: {},
				defaultValue: 'defaultValue',
				fieldId: 'fieldId'
			});
		});
	});

	describe('updateFragmentConfigurationAction', () => {
		it('sets the given configuration value', () => {
			dispatch(
				updateFragmentConfigurationAction(fragmentId, {
					configA: 'configAValue'
				})
			);

			expect(
				editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR][segmentId]
			).toEqual({configA: 'configAValue'});
		});

		it('ignores segmentsExperienceId when it is undefined', () => {
			delete state.defaultSegmentsExperienceId;
			delete state.segmentsExperienceId;

			dispatch(
				updateFragmentConfigurationAction(fragmentId, {
					configField: 'configValue'
				})
			);

			expect(editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]).toEqual(
				{
					configField: 'configValue'
				}
			);
		});
	});
});
