/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

AUI.add(
	'liferay-kaleo-designer-templates',
	(A) => {
		var Template = A.Template;

		var TPL_SELECT_MULTIPLE = [
			'<tpl if="values.label !== undefined">',
			'<label class="{[A.TplSnippets.getClassName(values.auiLabelCssClass, values.labelCssClass)]}" for="{id}" id="{labelId}" name="{labelName}" style="{labelStyle}">{label}</label>',
			'</tpl>',
			'<select class="{[A.TplSnippets.getClassName(values.auiCssClass, values.cssClass)]}" <tpl if="values.disabled">disabled="disabled"</tpl> <tpl if="values.multiple">multiple="multiple"</tpl> id="{id}" name="{name}" style="{style}">',
			'<tpl for="options">',
			'<option value="{value}">{label}</option>',
			'</tpl>',
			'</select>',
		];

		Template.register('select-multiple', TPL_SELECT_MULTIPLE);
	},
	'',
	{
		requires: 'aui-tpl-snippets-deprecated',
	}
);
