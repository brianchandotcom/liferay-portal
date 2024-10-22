/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// Utils

export * as FormSupport from './utils/FormSupport.es';

// Form/Data Engine Core

export {EVENT_TYPES} from './core/actions/eventTypes.es';
export {Field} from './core/components/Field/Field.es';
export {FieldStateless} from './core/components/Field/FieldStateless.es';
export {FieldFeedback} from './core/components/FieldFeedback';
export * as DRAG_TYPES from './utils/dragTypes';

// Keyboard drag and drop

export {
	KeyboardDNDContextProvider,
	useText as useKeyboardDNDText,
	useSetSourceItem as useSetKeyboardDNDSourceItem,
} from './core/components/KeyboardDNDContext';
export * as FieldSetUtil from './utils/fieldSets';
export * as FieldSupport from './utils/fieldSupport';
export {Layout} from './core/components/PageRenderer/Layout.es';
export {default as Pages} from './core/components/Pages.es';
export {
	INITIAL_CONFIG_STATE,
	INITIAL_PAGES,
	INITIAL_STATE,
} from './core/config/index.es';
export {ConfigProvider, useConfig} from './core/hooks/useConfig.es';
export {FormProvider, useForm, useFormState} from './core/hooks/useForm.es';
export * as RulesSupport from './utils/rulesSupport';
export {PageProvider, usePage} from './core/hooks/usePage.es';
export * as SettingsContext from './utils/settingsContext';
export * as StringUtils from './utils/strings';
export {useFieldTypesResource} from './core/hooks/useResource.es';

export {
	dataLayoutReducer,
	dragAndDropReducer,
	fieldEditableReducer,
	languageReducer,
	historyReducer,
	pagesStructureReducer,
	activePageReducer,
} from './core/reducers/index.es';
export {elementSetAdded} from './core/thunks/elementSetAdded.es';
export {default as fieldDelete} from './core/thunks/fieldDelete.es';
export {default as sectionAdded} from './core/utils/sectionAddedHandler';
export * as DefaultVariant from './core/components/PageRenderer/DefaultVariant.es';
export {enableSubmitButton} from './core/utils/submitButtonController.es';

// Custom Form Report

export {default as FormReport} from './custom/form-report/index';

// Containers

export {FormFieldSettings} from './custom/form/FormFieldSettings.es';
export {FormView} from './custom/form/FormView.es';
export {default as PartialResults} from './custom/form/components/PartialResults';

// Custom Form

export {EVENT_TYPES as FORM_EVENT_TYPES} from './custom/form/eventTypes';
export {
	pageReducer,
	objectFieldsReducer,
} from './custom/form/reducers/index.es';
export * as FieldUtil from './core/utils/fields';
export {Token} from './utils/Token';
export {Tokenizer} from './utils/Tokenizer';
export {default as compose} from './utils/compose.es';
export {getDDMFormFieldSettingsContext} from './utils/dataConverter';

export {convertToFormData, makeFetch} from './utils/fetch.es';

export {getFields, normalizeFieldName} from './utils/fields.es';

export {getUid} from './utils/formId.es';
export {
	addObjectFields,
	getFieldsGroupedByTypes,
	getObjectFieldName,
	getSelectedValue,
	updateObjectFields,
} from './utils/objectFields';

export {parseProps} from './utils/parseProps.es';

export {generateName, getRepeatedIndex, parseName} from './utils/repeatable.es';
export {default as setDataRecord} from './utils/setDataRecord.es';

export {capitalize} from './utils/strings';

export {PagesVisitor} from './utils/visitors.es';
