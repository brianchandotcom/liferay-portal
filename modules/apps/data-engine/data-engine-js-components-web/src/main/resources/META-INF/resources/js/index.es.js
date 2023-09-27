/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// Utils

export {default as compose} from './utils/compose.es';
export {getDDMFormFieldSettingsContext} from './utils/dataConverter';
export * as DRAG_TYPES from './utils/dragTypes';
export {convertToFormData, makeFetch} from './utils/fetch.es';
export {getFields, normalizeFieldName} from './utils/fields.es';
export * as FieldSetUtil from './utils/fieldSets';
export * as FieldSupport from './utils/fieldSupport';
export {getUid} from './utils/formId.es';
export * as FormSupport from './utils/FormSupport.es';
export {
	addObjectFields,
	getFieldsGroupedByTypes,
	getObjectFieldName,
	getSelectedValue,
	updateObjectFields,
} from './utils/objectFields';
export {parseProps} from './utils/parseProps.es';
export {getConnectedReactComponentAdapter} from './utils/ReactComponentAdapter.es';
export {generateName, getRepeatedIndex, parseName} from './utils/repeatable.es';
export * as RulesSupport from './utils/rulesSupport';
export {default as setDataRecord} from './utils/setDataRecord.es';
export * as SettingsContext from './utils/settingsContext';
export * as StringUtils from './utils/strings';
export {Token} from './utils/Token';
export {Tokenizer} from './utils/Tokenizer';
export {PagesVisitor} from './utils/visitors.es';

// Form/Data Engine Core

export {EVENT_TYPES} from './core/actions/eventTypes.es';
export {Field} from './core/components/Field/Field.es';
export {FieldFeedback} from './core/components/FieldFeedback';
export {FieldStateless} from './core/components/Field/FieldStateless.es';
export * as DefaultVariant from './core/components/PageRenderer/DefaultVariant.es';
export {Layout} from './core/components/PageRenderer/Layout.es';
export {default as Pages} from './core/components/Pages.es';
export * from './core/config/index.es';
export {ConfigProvider, useConfig} from './core/hooks/useConfig.es';
export {FormProvider, useForm, useFormState} from './core/hooks/useForm.es';
export {PageProvider, usePage} from './core/hooks/usePage.es';
export {useFieldTypesResource} from './core/hooks/useResource.es';
export {elementSetAdded} from './core/thunks/elementSetAdded.es';
export * as FieldUtil from './core/utils/fields';
export {default as sectionAdded} from './core/utils/sectionAddedHandler';
export {capitalize} from './utils/strings';
export {enableSubmitButton} from './core/utils/submitButtonController.es';
export {default as PartialResults} from './custom/form/components/PartialResults';

// Custom Form

export {EVENT_TYPES as FORM_EVENT_TYPES} from './custom/form/eventTypes';

// Custom Form Report

export {default as FormReport} from './custom/form-report/index';

// Containers

export {FormFieldSettings} from './custom/form/FormFieldSettings.es';
export {FormView} from './custom/form/FormView.es';

// Keyboard drag and drop

export {
	KeyboardDNDContextProvider,
	useSetSourceItem as useSetKeyboardDNDSourceItem,
	useText as useKeyboardDNDText,
} from './core/components/KeyboardDNDContext';
