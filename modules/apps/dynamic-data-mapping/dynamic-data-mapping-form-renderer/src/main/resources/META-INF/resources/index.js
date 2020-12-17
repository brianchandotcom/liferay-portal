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

// Composing Form

export {default as Pages} from './js/components/Pages.es';
export {Field} from './js/components/Field/Field.es';
export {EVENT_TYPES} from './js/actions/eventTypes.es';
export {FieldStateless} from './js/components/Field/FieldStateless.es';
export {PageProvider, usePage} from './js/hooks/usePage.es';
export {useFieldTypesResource} from './js/hooks/useResource.es';
export {FormProvider, FormNoopProvider, useForm} from './js/hooks/useForm.es';
export {Layout} from './js/components/PageRenderer/Layout.es';
export * as DefaultVariant from './js/components/PageRenderer/DefaultVariant.es';

// Containers

export {default as Form, ReactFormAdapter} from './js/containers/Form.es';
export {FormNoop} from './js/containers/FormNoop.es';
