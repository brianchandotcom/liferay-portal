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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useMemo} from 'react';

import {ALLOWED_INPUT_TYPES} from '../../../../../../app/config/constants/allowedInputTypes';
import {FRAGMENT_ENTRY_TYPES} from '../../../../../../app/config/constants/fragmentEntryTypes';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
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
const SHOW_HELP_TEXT_CONFIGURATION_KEY = 'inputShowHelpText';

const INPUT_COMMON_CONFIGURATION = [
	{
		defaultValue: false,
		label: Liferay.Language.get('mark-as-required'),
		name: 'inputRequired',
		type: 'checkbox',
	},
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
		name: 'inputLabel',
		type: 'text',
	},
	{
		defaultValue: true,
		label: Liferay.Language.get('show-help-text'),
		name: SHOW_HELP_TEXT_CONFIGURATION_KEY,
		type: 'checkbox',
		typeOptions: {displayType: 'toggle'},
	},
	{
		defaultValue: '',
		label: Liferay.Language.get('help-text'),
		localizable: true,
		name: HELP_TEXT_CONFIGURATION_KEY,
		type: 'text',
	},
];

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

	const fields = useMemo(() => {
		let nextFields = INPUT_COMMON_CONFIGURATION;

		if (configurationValues[SHOW_HELP_TEXT_CONFIGURATION_KEY] === false) {
			nextFields = nextFields.filter(
				(field) => field.name !== HELP_TEXT_CONFIGURATION_KEY
			);
		}

		return nextFields;
	}, [configurationValues]);

	const handleValueSelect = (key, value) => {
		const keyPath = [FREEMARKER_FRAGMENT_ENTRY_PROCESSOR, key];

		const localizable =
			fields.find((field) => field.name === key)?.localizable || false;

		if (localizable) {
			keyPath.push(languageId);
		}

		dispatch(
			updateEditableValues({
				editableValues: setIn(
					fragmentEntryLinkRef.current.editableValues,
					keyPath,
					value
				),
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
					<FormInputMappingOptions
						configurationValues={configurationValues}
						item={item}
						onValueSelect={handleValueSelect}
					/>

					<FieldSet
						fields={fields}
						item={item}
						label=""
						languageId={languageId}
						onValueSelect={handleValueSelect}
						values={configurationValues}
					/>
				</Collapse>
			</div>

			<FragmentGeneralPanel item={item} />
		</>
	);
}

function FormInputMappingOptions({configurationValues, item, onValueSelect}) {
	const {classNameId, classTypeId} = useSelectorCallback(
		(state) =>
			selectFormConfiguration(item, state.layoutData) ||
			DEFAULT_FORM_CONFIGURATION,
		[item.itemId]
	);

	const inputType = useSelectorCallback(
		(state) => {
			const element = document.createElement('div');
			element.innerHTML = selectFragmentEntryLink(state, item).content;

			if (element.querySelector('select')) {
				return 'select';
			}
			else if (element.querySelector('textarea')) {
				return 'textarea';
			}

			return element.querySelector('input')?.type || 'text';
		},
		[item.itemId]
	);

	const fields = useCache({
		fetcher: () => FormService.getFormFields({classNameId, classTypeId}),
		key: [CACHE_KEYS.formFields, classNameId, classTypeId],
	});

	const filteredFields = useSelectorCallback(
		(state) => {
			if (!fields) {
				return fields;
			}

			let nextFields = fields;

			const selectedFields = (() => {
				const findFormItemId = (itemId) => {
					const formItem = state.layoutData.items[itemId];

					if (formItem?.type === LAYOUT_DATA_ITEM_TYPES.form) {
						return itemId;
					}
					else if (formItem?.parentId) {
						return findFormItemId(formItem.parentId);
					}

					return null;
				};

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
							editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR][
								FIELD_ID_CONFIGURATION_KEY
							]
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

				findSelectedFields(findFormItemId(item.itemId));

				return selectedFields;
			})();

			nextFields = nextFields
				.map((fieldset) => ({
					...fieldset,
					fields: fieldset.fields.filter(
						(field) =>
							ALLOWED_INPUT_TYPES[field.type]?.includes(
								inputType
							) && !selectedFields.includes(field.key)
					),
				}))
				.filter((fieldset) => fieldset.fields.length);

			return nextFields;
		},
		[item.itemId, fields, inputType]
	);

	if (!classNameId || !classTypeId) {
		return null;
	}

	return filteredFields ? (
		<MappingFieldSelector
			fieldType={inputType}
			fields={filteredFields}
			onValueSelect={(event) =>
				onValueSelect(FIELD_ID_CONFIGURATION_KEY, event.target.value)
			}
			value={configurationValues[FIELD_ID_CONFIGURATION_KEY] || ''}
		/>
	) : (
		<ClayLoadingIndicator />
	);
}
