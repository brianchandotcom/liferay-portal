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

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useMemo} from 'react';

import {FRAGMENT_ENTRY_TYPES} from '../../../../../../app/config/constants/fragmentEntryTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
	useSelectorRef,
} from '../../../../../../app/contexts/StoreContext';
import selectFormConfiguration from '../../../../../../app/selectors/selectFormConfiguration';
import selectFragmentEntryLink from '../../../../../../app/selectors/selectFragmentEntryLink';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import FormService from '../../../../../../app/services/FormService';
import updateEditableValues from '../../../../../../app/thunks/updateEditableValues';
import {CACHE_KEYS} from '../../../../../../app/utils/cache';
import {isFormRequiredField} from '../../../../../../app/utils/isFormRequiredField';
import {setIn} from '../../../../../../app/utils/setIn';
import useCache from '../../../../../../app/utils/useCache';
import Collapse from '../../../../../../common/components/Collapse';
import MappingFieldSelector from '../../../../../../common/components/MappingFieldSelector';
import {FieldSet} from './FieldSet';
import {FragmentGeneralPanel} from './FragmentGeneralPanel';

const DEFAULT_CONFIGURATION_VALUES = {};
const DEFAULT_FORM_CONFIGURATION = {classNameId: null, classTypeId: null};

const FIELD_ID_CONFIGURATION_KEY = 'inputFieldId';
const HELP_TEXT_CONFIGURATION_KEY = 'inputHelpText';
const LABEL_CONFIGURATION_KEY = 'inputLabel';
const REQUIRED_CONFIGURATION_KEY = 'inputRequired';
const SHOW_HELP_TEXT_CONFIGURATION_KEY = 'inputShowHelpText';

function getFieldLabel(fieldKey, fields) {
	const flattenedFields = fields.flatMap((fieldSet) => fieldSet.fields);

	return flattenedFields.find((field) => field.key === fieldKey)?.label;
}

function getInputCommonConfiguration(configurationValues, formFields) {
	const fields = [];

	if (configurationValues[FIELD_ID_CONFIGURATION_KEY]) {
		const isRequiredField = isFormRequiredField(
			configurationValues[FIELD_ID_CONFIGURATION_KEY],
			formFields
		);

		fields.push({
			defaultValue: isRequiredField,
			disabled: isRequiredField,
			label: Liferay.Language.get('mark-as-required'),
			name: REQUIRED_CONFIGURATION_KEY,
			type: 'checkbox',
		});
	}

	fields.push(
		{
			defaultValue: true,
			label: Liferay.Language.get('show-label'),
			name: 'inputShowLabel',
			type: 'checkbox',
			typeOptions: {displayType: 'toggle'},
		},
		{
			defaultValue: '',
			label: Liferay.Language.get('label'),
			localizable: true,
			name: LABEL_CONFIGURATION_KEY,
			type: 'text',
		},
		{
			defaultValue: false,
			label: Liferay.Language.get('show-help-text'),
			name: SHOW_HELP_TEXT_CONFIGURATION_KEY,
			type: 'checkbox',
			typeOptions: {displayType: 'toggle'},
		},
		{
			defaultValue: Liferay.Language.get('add-your-help-text-here'),
			label: Liferay.Language.get('help-text'),
			localizable: true,
			name: HELP_TEXT_CONFIGURATION_KEY,
			type: 'text',
		}
	);

	return fields;
}

function getTypeLabels(itemTypes, classNameId, classTypeId) {
	if (!itemTypes || !classNameId) {
		return {};
	}

	const selectedType = itemTypes.find(({value}) => value === classNameId);

	const selectedSubtype = selectedType.subtypes.length
		? selectedType.subtypes.find(({value}) => value === classTypeId)
		: {};

	return {
		subtype: selectedSubtype.label,
		type: selectedType.label,
	};
}

export function FormInputGeneralPanel({item}) {
	const dispatch = useDispatch();
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const fragmentEntryLinkRef = useSelectorRef((state) =>
		selectFragmentEntryLink(state, item)
	);

	const configurationValues = useSelectorCallback(
		(state) =>
			selectFragmentEntryLink(state, item).editableValues[
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			] || DEFAULT_CONFIGURATION_VALUES,
		[item.itemId]
	);

	const {classNameId, classTypeId, formId} = useSelectorCallback(
		(state) =>
			selectFormConfiguration(item, state.layoutData) ||
			DEFAULT_FORM_CONFIGURATION,
		[item.itemId]
	);

	const formFields = useCache({
		fetcher: () => FormService.getFormFields({classNameId, classTypeId}),
		key: [CACHE_KEYS.formFields, classNameId, classTypeId],
	});

	const {fragmentEntryKey} = fragmentEntryLinkRef.current;

	const allowedInputTypes = useCache({
		fetcher: () =>
			FormService.getFragmentEntryInputFieldTypes({fragmentEntryKey}),
		key: [CACHE_KEYS.allowedInputTypes, fragmentEntryKey],
	});

	const filteredFormFields = useSelectorCallback(
		(state) => {
			if (!formFields || !allowedInputTypes) {
				return [];
			}

			let nextFields = formFields;

			const selectedFields = (() => {
				const selectedFields = [];

				const findSelectedFields = (itemId) => {
					const inputItem = state.layoutData.items[itemId];

					if (
						inputItem?.itemId !== item.itemId &&
						inputItem?.type === LAYOUT_DATA_ITEM_TYPES.fragment
					) {
						const {
							editableValues,
							fragmentEntryType,
						} = selectFragmentEntryLink(state, inputItem);

						if (
							fragmentEntryType === FRAGMENT_ENTRY_TYPES.input &&
							editableValues[
								FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
							]?.[FIELD_ID_CONFIGURATION_KEY]
						) {
							selectedFields.push(
								editableValues[
									FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
								][FIELD_ID_CONFIGURATION_KEY]
							);
						}
					}

					inputItem?.children.forEach(findSelectedFields);
				};

				findSelectedFields(formId);

				return selectedFields;
			})();

			nextFields = nextFields
				.map((fieldset) => ({
					...fieldset,
					fields: fieldset.fields
						.filter(
							(field) =>
								allowedInputTypes.includes(field.type) &&
								!selectedFields.includes(field.key)
						)
						.map((field) =>
							field.required
								? {...field, label: `${field.label}*`}
								: field
						),
				}))
				.filter((fieldset) => fieldset.fields.length);

			return nextFields;
		},
		[allowedInputTypes, item.itemId, formFields]
	);

	const configFields = useMemo(() => {
		let nextFields = getInputCommonConfiguration(
			configurationValues,
			formFields
		);

		const fieldSetsWithoutLabel =
			fragmentEntryLinkRef.current.configuration?.fieldSets?.filter(
				(fieldSet) => !fieldSet.configurationRole && !fieldSet.label
			) ?? [];

		nextFields = [
			...nextFields,
			...fieldSetsWithoutLabel.flatMap((fieldSet) => fieldSet.fields),
		];

		return nextFields;
	}, [configurationValues, fragmentEntryLinkRef, formFields]);

	const handleValueSelect = (key, value) => {
		const keyPath = [FREEMARKER_FRAGMENT_ENTRY_PROCESSOR, key];

		const localizable =
			configFields.find((field) => field.name === key)?.localizable ||
			false;

		if (localizable) {
			keyPath.push(languageId);
		}

		let editableValues = fragmentEntryLinkRef.current.editableValues;

		if (key === FIELD_ID_CONFIGURATION_KEY) {
			editableValues = setIn(
				fragmentEntryLinkRef.current.editableValues,
				[
					FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					REQUIRED_CONFIGURATION_KEY,
				],
				isFormRequiredField(value, formFields)
			);

			editableValues = setIn(
				editableValues,
				[
					FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					LABEL_CONFIGURATION_KEY,
					config.defaultLanguageId,
				],
				getFieldLabel(value, formFields)
			);

			editableValues = setIn(
				editableValues,
				[
					FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					HELP_TEXT_CONFIGURATION_KEY,
				],
				null
			);
		}

		dispatch(
			updateEditableValues({
				editableValues: setIn(editableValues, keyPath, value),
				fragmentEntryLinkId:
					fragmentEntryLinkRef.current.fragmentEntryLinkId,
				languageId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<>
			<div className="mb-3">
				<Collapse
					label={Liferay.Language.get('form-input-options')}
					open
				>
					{filteredFormFields.flatMap((fieldSet) => fieldSet.fields)
						.length ? (
						<FormInputMappingOptions
							allowedInputTypes={allowedInputTypes}
							configurationValues={configurationValues}
							form={{
								classNameId,
								classTypeId,
								fields: filteredFormFields,
							}}
							item={item}
							onValueSelect={handleValueSelect}
						/>
					) : (
						<ClayAlert displayType="info">
							{Liferay.Language.get(
								'there-are-no-suitable-fields-in-the-item-to-be-mapped-to-the-fragment'
							)}
						</ClayAlert>
					)}

					{configurationValues[FIELD_ID_CONFIGURATION_KEY] && (
						<>
							<span className="sr-only">
								{Liferay.Language.get(
									'input-fragment-configuration'
								)}
							</span>

							<FieldSet
								fields={configFields}
								item={item}
								label=""
								languageId={languageId}
								onValueSelect={handleValueSelect}
								values={configurationValues}
							/>
						</>
					)}
				</Collapse>
			</div>

			<FragmentGeneralPanel item={item} />
		</>
	);
}

function FormInputMappingOptions({configurationValues, form, onValueSelect}) {
	const {classNameId, classTypeId, fields} = form;

	const formTypes = useCache({
		fetcher: () => FormService.getAvailableEditPageInfoItemFormProviders(),
		key: [CACHE_KEYS.formTypes],
	});

	const {subtype, type} = useMemo(
		() => getTypeLabels(formTypes, classNameId, classTypeId),
		[formTypes, classNameId, classTypeId]
	);

	if (!classNameId || !classTypeId) {
		return null;
	}

	return fields ? (
		<>
			<MappingFieldSelector
				fields={fields}
				onValueSelect={(event) =>
					onValueSelect(
						FIELD_ID_CONFIGURATION_KEY,
						event.target.value === 'unmapped'
							? null
							: event.target.value
					)
				}
				value={configurationValues[FIELD_ID_CONFIGURATION_KEY] || ''}
			/>
			{type && (
				<p
					className={classNames(
						'page-editor__mapping-panel__type-label',
						{
							'mb-1': subtype,
							'mb-4': !subtype,
						}
					)}
				>
					<span className="mr-1">
						{Liferay.Language.get('content-type')}:
					</span>

					{type}
				</p>
			)}

			{subtype && (
				<p className="mb-4 page-editor__mapping-panel__type-label">
					<span className="mr-1">
						{Liferay.Language.get('subtype')}:
					</span>

					{subtype}
				</p>
			)}
		</>
	) : (
		<ClayLoadingIndicator />
	);
}
