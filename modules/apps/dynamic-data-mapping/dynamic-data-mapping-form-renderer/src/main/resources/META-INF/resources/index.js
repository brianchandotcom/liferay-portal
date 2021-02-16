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

// Utils

export {default as compose} from './js/util/compose.es';
export {normalizeFieldName} from './js/util/fields.es';
export {
	getRepeatedIndex,
	generateName,
	generateInstanceId,
} from './js/util/repeatable.es';
export {PagesVisitor, RulesVisitor} from './js/util/visitors.es';
export * as FormSupport from './js/util/FormSupport.es';
export {getConnectedReactComponentAdapter} from './js/util/ReactComponentAdapter.es';
export {default as formatFieldValue} from './js/util/formatFieldValue.es';
export {default as setDataRecord} from './js/util/setDataRecord.es';

// Form/Data Engine Core

export {default as Pages} from './js/core/components/Pages.es';
export {Field} from './js/core/components/Field/Field.es';
export {FieldStateless} from './js/core/components/Field/FieldStateless.es';
export {EVENT_TYPES, MAPPED_EVENT_TYPES} from './js/core/actions/eventTypes.es';
export {PageProvider, usePage} from './js/core/hooks/usePage.es';
export {useFieldTypesResource} from './js/core/hooks/useResource.es';
export {FormProvider, useForm, useFormState} from './js/core/hooks/useForm.es';
export {FormConfigProvider, useConfig} from './js/core/hooks/useConfig.es';
export {Layout} from './js/core/components/PageRenderer/Layout.es';
export * as DefaultVariant from './js/core/components/PageRenderer/DefaultVariant.es';

// Custom Form

export {EVENT_TYPES as FORM_EVENT_TYPES} from './js/custom/form/eventTypes.es';
export {default as FormApp} from './js/custom/form/App.es';

// Containers

export {FormView} from './js/custom/form/FormView.es';
export {default as LegacyFormBuilder} from './js/custom/form/LegacyFormBuilder.es';
export {
	FormFieldSettings,
	FormFieldSettingsAdapter,
} from './js/custom/form/FormFieldSettings.es';
