AUI.add(
	'liferay-kaleo-designer-templates',
	function(A) {
		var Template = A.Template;

		var TPL_SELECT_MULTIPLE = [
			'<tpl if="values.label !== undefined">',
			'<label class="{[A.TplSnippets.getClassName(values.auiLabelCssClass, values.labelCssClass)]}" for="{id}" id="{labelId}" name="{labelName}" style="{labelStyle}">{label}</label>',
			'</tpl>',
			'<select class="{[A.TplSnippets.getClassName(values.auiCssClass, values.cssClass)]}" <tpl if="values.disabled">disabled="disabled"</tpl> <tpl if="values.multiple">multiple="multiple"</tpl> id="{id}" name="{name}" style="{style}">',
			'<tpl for="options">',
			'<option value="{value}">{label}</option>',
			'</tpl>',
			'</select>'
		];

		Template.register('select-multiple', TPL_SELECT_MULTIPLE);
	},
	'',
	{
		requires: 'aui-tpl-snippets-deprecated'
	}
);