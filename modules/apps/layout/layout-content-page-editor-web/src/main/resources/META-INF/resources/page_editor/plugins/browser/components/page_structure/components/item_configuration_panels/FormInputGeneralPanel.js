/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

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
import FormService from '../../../../../../app/services/FormService';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateFragmentConfiguration from '../../../../../../app/thunks/updateFragmentConfiguration';
import {CACHE_KEYS} from '../../../../../../app/utils/cache';
import getMappedRelationship from '../../../../../../app/utils/editable_value/getMappedRelationship';
import {isRequiredFormField} from '../../../../../../app/utils/isRequiredFormField';
import useCache from '../../../../../../app/utils/useCache';
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

const SOURCE_TYPES = {
	mainObject: 'main-object',
	relationship: 'relationship',
};

const NOT_SELECTED_OPTION = {
	label: `-- ${Liferay.Language.get('not-selected')} --`,
	value: '',
};

function getInputCommonConfiguration(configurationValues, formFields) {
	const fields = [];

	if (configurationValues[FIELD_ID_CONFIGURATION_KEY]) {
		const isRequiredField = isRequiredFormField(
			configurationValues[FIELD_ID_CONFIGURATION_KEY],
			formFields
		);

		fields.push({
			cssClass: 'mb-4',
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
			cssClass: 'mb-4',
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
			cssClass: 'mb-4',
			defaultValue: Liferay.Language.get('add-your-help-text-here'),
			label: Liferay.Language.get('help-text'),
			localizable: true,
			name: HELP_TEXT_CONFIGURATION_KEY,
			type: 'text',
		}
	);

	return fields;
}

function getTypeLabels(classNameId, classTypeId) {
	if (!classNameId) {
		return {};
	}

	const selectedType = config.formTypes.find(
		({value}) => value === classNameId
	);

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

	const fragmentEntryLinkRef = useSelectorRef((state) =>
		selectFragmentEntryLink(state, item)
	);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
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

	const {fragmentEntryKey, groupId} = fragmentEntryLinkRef.current;

	const fragmentName = useSelectorCallback(
		(state) => {
			const fragment = state.fragments
				.flatMap((collection) => collection.fragmentEntries)
				.find(
					(fragment) => fragment.fragmentEntryKey === fragmentEntryKey
				);

			return fragment ? fragment.name : Liferay.Language.get('fragment');
		},
		[fragmentEntryKey]
	);

	const allowedInputTypes = useCache({
		fetcher: () =>
			FormService.getFragmentEntryInputFieldTypes({
				fragmentEntryKey,
				groupId,
			}),
		key: [CACHE_KEYS.allowedInputTypes, fragmentEntryKey],
	});

	const isSpecialInput = useMemo(
		() =>
			allowedInputTypes?.includes('captcha') ||
			allowedInputTypes?.includes('categorization') ||
			allowedInputTypes?.includes('formButton') ||
			allowedInputTypes?.includes('stepper'),
		[allowedInputTypes]
	);

	const state = useSelector((state) => state);

	const filterFields = useCallback(
		(initialFields, selectedRelationship, relationships) => {
			if (!initialFields || !allowedInputTypes || isSpecialInput) {
				return [];
			}

			let fields = initialFields;

			if (selectedRelationship) {
				fields = initialFields.filter(
					(fieldSet) => fieldSet.name === selectedRelationship
				);
			}

			if (relationships && !selectedRelationship) {
				fields = fields.filter(
					(fieldSet) =>
						!relationships
							.map((relationship) => relationship.name)
							.includes(fieldSet.name)
				);
			}

			const selectedFields = (() => {
				const selectedFields = [];

				const findSelectedFields = (itemId) => {
					const inputItem = state.layoutData.items[itemId];

					if (
						inputItem?.itemId !== item.itemId &&
						inputItem?.type === LAYOUT_DATA_ITEM_TYPES.fragment
					) {
						const {editableValues, fragmentEntryType} =
							selectFragmentEntryLink(state, inputItem);

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

			fields = fields
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

			return fields;
		},
		[allowedInputTypes, formId, isSpecialInput, item.itemId, state]
	);

	const configFields = useMemo(() => {
		const fieldSetsWithoutLabel =
			fragmentEntryLinkRef.current.configuration?.fieldSets
				?.filter(
					(fieldSet) => !fieldSet.configurationRole && !fieldSet.label
				)
				.flatMap((fieldSet) => fieldSet.fields) ?? [];

		if (
			Liferay.FeatureFlags['LPD-10727'] &&
			allowedInputTypes?.includes('stepper')
		) {
			return fieldSetsWithoutLabel.filter(
				(field) => field.name !== 'numberOfSteps'
			);
		}

		if (
			!Liferay.FeatureFlags['LPD-10727'] &&
			fragmentEntryLinkRef.current?.fragmentEntryKey ===
				'INPUTS-submit-button'
		) {
			return fieldSetsWithoutLabel.filter(
				(field) => field.name !== 'type'
			);
		}

		if (isSpecialInput) {
			return fieldSetsWithoutLabel;
		}

		const inputCommonFields = getInputCommonConfiguration(
			configurationValues,
			formFields
		);

		return [...inputCommonFields, ...fieldSetsWithoutLabel];
	}, [
		allowedInputTypes,
		configurationValues,
		fragmentEntryLinkRef,
		formFields,
		isSpecialInput,
	]);

	const handleValueSelect = (key, value) => {
		let configurationValues =
			fragmentEntryLinkRef.current?.editableValues?.[
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			] ?? DEFAULT_CONFIGURATION_VALUES;

		const localizable =
			configFields.find((field) => field.name === key)?.localizable ||
			false;

		if (localizable) {
			let nextValue = configurationValues[key];

			if (!nextValue || typeof nextValue !== 'object') {
				nextValue = {};
			}

			nextValue[languageId] = value;

			configurationValues = {
				...configurationValues,
				[key]: nextValue,
			};
		}
		else {
			configurationValues = {
				...configurationValues,
				[key]: value,
			};
		}

		if (key === FIELD_ID_CONFIGURATION_KEY) {
			configurationValues = {[key]: value};
		}

		return dispatch(
			updateFragmentConfiguration({
				configurationValues,
				fragmentEntryLink: fragmentEntryLinkRef.current,
			})
		);
	};

	if (isSpecialInput && !configFields.length) {
		return <FragmentGeneralPanel item={item} />;
	}

	return (
		<>
			<div className="mb-3 panel-group-sm">
				<ClayPanel
					collapsable
					defaultExpanded
					displayTitle={sub(
						Liferay.Language.get('x-options'),
						fragmentName
					)}
					displayType="unstyled"
					showCollapseIcon
				>
					<ClayPanel.Body>
						{!isSpecialInput && (
							<FormInputMappingOptions
								configurationValues={configurationValues}
								filterFields={filterFields}
								form={{
									classNameId,
									classTypeId,
									fields: formFields,
								}}
								item={item}
								onValueSelect={handleValueSelect}
							/>
						)}

						{(configurationValues[FIELD_ID_CONFIGURATION_KEY] ||
							isSpecialInput) && (
							<>
								<span className="sr-only">
									{sub(
										Liferay.Language.get('x-configuration'),
										fragmentName
									)}
								</span>

								<FieldSet
									fields={configFields}
									fragmentEntryLinks={
										fragmentEntryLinksRef.current
									}
									item={item}
									label=""
									languageId={languageId}
									onValueSelect={handleValueSelect}
									selectedViewportSize={selectedViewportSize}
									values={configurationValues}
								/>
							</>
						)}
					</ClayPanel.Body>
				</ClayPanel>
			</div>

			<FragmentGeneralPanel item={item} />
		</>
	);
}

function FormInputMappingOptions({
	configurationValues,
	filterFields,
	form,
	onValueSelect,
}) {
	const {classNameId, classTypeId, fields: formFields} = form;

	const {subtype, type} = useMemo(
		() => getTypeLabels(classNameId, classTypeId),
		[classNameId, classTypeId]
	);

	const relationshipSelectId = useId();
	const sourceSelectId = useId();

	const relationships = useCache({
		fetcher: () =>
			InfoItemService.getInfoItemRelationships({
				classNameId,
				classTypeId,
			}),
		key: [CACHE_KEYS.relationships, classNameId, classTypeId || '0'],
	});

	const [fields, setFields] = useState(formFields);

	const [selectedRelationship, setSelectedRelationship] = useState(
		getMappedRelationship(configurationValues.inputFieldId)
	);

	const [sourceType, setSourceType] = useState(
		selectedRelationship
			? SOURCE_TYPES.relationship
			: SOURCE_TYPES.mainObject
	);

	useEffect(() => {
		if (sourceType === SOURCE_TYPES.relationship && !selectedRelationship) {
			setFields([]);
		}
		else {
			setFields(
				filterFields(formFields, selectedRelationship, relationships)
			);
		}
	}, [
		filterFields,
		formFields,
		relationships,
		selectedRelationship,
		sourceType,
	]);

	if (!classNameId || !classTypeId) {
		return null;
	}

	if (!fields) {
		return <ClayLoadingIndicator />;
	}

	return (
		<>
			{relationships?.length ? (
				<>
					<ClayForm.Group small>
						<label htmlFor={sourceSelectId}>
							{Liferay.Language.get('source')}
						</label>

						<ClaySelectWithOption
							className="pr-4 text-truncate"
							id={sourceSelectId}
							onChange={(event) => {
								setSourceType(event.target.value);
								setSelectedRelationship(null);
								onValueSelect(FIELD_ID_CONFIGURATION_KEY, null);
							}}
							options={[
								{
									label: sub(
										Liferay.Language.get('x-default'),
										type
									),
									value: SOURCE_TYPES.mainObject,
								},
								{
									label: Liferay.Language.get('relationship'),
									value: SOURCE_TYPES.relationship,
								},
							]}
							value={sourceType}
						/>
					</ClayForm.Group>

					{sourceType === SOURCE_TYPES.relationship ? (
						<ClayForm.Group small>
							<label htmlFor={relationshipSelectId}>
								{Liferay.Language.get('relationship')}
							</label>

							<ClaySelectWithOption
								className="pr-4 text-truncate"
								id={relationshipSelectId}
								onChange={(event) => {
									setSelectedRelationship(event.target.value);

									onValueSelect(
										FIELD_ID_CONFIGURATION_KEY,
										null
									);
								}}
								options={[
									NOT_SELECTED_OPTION,
									...(relationships || []).map(
										({label, name}) => ({
											label,
											value: name,
										})
									),
								]}
								value={selectedRelationship}
							/>
						</ClayForm.Group>
					) : null}
				</>
			) : null}

			{fields.flatMap((fieldSet) => fieldSet.fields).length ? (
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
						value={
							configurationValues[FIELD_ID_CONFIGURATION_KEY] ||
							''
						}
					/>

					{type && (
						<p
							className={classNames(
								'page-editor__mapping-panel__type-label',
								{
									'mb-1': subtype,
									'mb-3': !subtype,
								}
							)}
						>
							<span className="mr-1">
								{Liferay.Language.get('content-type')}:
							</span>

							{selectedRelationship
								? relationships.find(
										({name}) =>
											name === selectedRelationship
									).label
								: type}
						</p>
					)}

					{subtype && (
						<p className="mb-3 page-editor__mapping-panel__type-label">
							<span className="mr-1">
								{Liferay.Language.get('subtype')}:
							</span>

							{subtype}
						</p>
					)}
				</>
			) : sourceType === SOURCE_TYPES.mainObject ||
			  selectedRelationship ? (
				<ClayAlert displayType="info">
					{Liferay.Language.get(
						'there-are-no-suitable-fields-in-the-item-to-be-mapped-to-the-fragment'
					)}
				</ClayAlert>
			) : null}
		</>
	);
}
