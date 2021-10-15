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

interface Field {
	name: string;
	type: FieldTypeName;
}

interface FieldType {
	label: string;
	name: FieldTypeName;
}

type FieldTypeName =
	| 'checkbox_multiple'
	| 'captcha'
	| 'checkbox'
	| 'color'
	| 'date'
	| 'document_library'
	| 'fieldset'
	| 'grid'
	| 'help_text'
	| 'image'
	| 'key_value'
	| 'localizable_text'
	| 'multi_language_option_select'
	| 'numeric'
	| 'numeric_input_mask'
	| 'object_field'
	| 'object-relationship'
	| 'options'
	| 'password'
	| 'paragraph'
	| 'redirect_button'
	| 'radio'
	| 'rich_text'
	| 'search_location'
	| 'separator'
	| 'select'
	| 'text'
	| 'validation';

declare module 'data-engine-js-components-web' {
	function useConfig(): {fieldTypes: FieldType[]};
	function useForm(): ({
		payload,
		type,
	}: {
		payload?: any;
		type: string;
	}) => void;
}
