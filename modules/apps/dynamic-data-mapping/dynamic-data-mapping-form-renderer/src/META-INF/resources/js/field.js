AUI.add(
	'liferay-ddm-form-renderer-field',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;
		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-form-field-repeatable-add-button, .lfr-ddm-form-field-repeatable-delete-button';

		var TPL_REPEATABLE_ADD = '<a class="icon-plus-sign lfr-ddm-form-field-repeatable-add-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_DELETE = '<a class="hide icon-minus-sign lfr-ddm-form-field-repeatable-delete-button" href="javascript:;"></a>';

		var Field = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one,
						valueFn: '_valueContainer'
					},

					definition: {
						value: {}
					},

					fields: {
						value: []
					},

					fieldType: {
						value: ''
					},

					form: {
					},

					instanceId: {
						valueFn: '_valueInstanceId'
					},

					label: {
						getter: '_getLabel'
					},

					locale: {
						value: themeDisplay.getLanguageId()
					},

					localizable: {
						getter: '_getLocalizable'
					},

					name: {
						getter: '_getName'
					},

					parent: {
						value: {}
					},

					portletNamespace: {
						value: ''
					},

					repeatable: {
						getter: '_getRepeatable',
						readOnly: true
					},

					repeatedIndex: {
						valueFn: '_valueRepeatedIndex'
					},

					value: {
						value: ''
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer-field',

				prototype: {
					renderUI: function() {
						var instance = this;

						var container = instance.get('container');

						if (!container.inDoc()) {
							instance.renderTemplate();

							var parent = instance.get('parent');

							container.appendTo(parent.get('container'));
						}

						if (instance.get('repeatable')) {
							instance.renderRepeatableUI();
						}
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandlers = [
							instance.get('container').delegate('click', instance._handleToolbarClick, SELECTOR_REPEAT_BUTTONS, instance),
							instance.get('form').after(['liferay-form-field:remove', 'liferay-form-field:repeat'], A.bind(instance._syncRepeatableField, instance))
						];
					},

					syncUI: function() {
						var instance = this;

						if (instance.get('repeatable')) {
							instance.syncRepeatablelUI();
						}
					},

					destructor: function() {
						var instance = this;

						instance.get('container').remove();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getInputNode: function() {
						var instance = this;

						var qualifiedName = instance.getQualifiedName().replace(/\$/ig, '\\$');

						return instance.get('container').one('[name=' + qualifiedName + ']');
					},

					getLabelNode: function() {
						var instance = this;

						return instance.get('container').one('label');
					},

					getQualifiedName: function() {
						var instance = this;

						return [
							instance.get('portletNamespace'),
							'ddm$$',
							instance.get('name'),
							'$',
							instance.get('instanceId'),
							'$',
							instance.get('repeatedIndex'),
							'$$',
							instance.get('locale')
						].join('');
					},

					getRepeatedSiblings: function() {
						var instance = this;

						return instance.getSiblings().filter(
							function(item) {
								return item.get('name') === instance.get('name');
							}
						);
					},

					getSiblings: function() {
						var instance = this;

						return instance.get('parent').get('fields');
					},

					getTemplate: function() {
						var instance = this;

						return FieldTypes.getFieldTypeTemplate(
							instance.get('fieldType'),
							instance.getTemplateContext()
						);
					},

					getTemplateContext: function() {
						var instance = this;

						var value = instance.get('value');

						if (instance.get('localizable')) {
							value = value[instance.get('locale')];
						}

						return A.merge(
							instance.get('definition'),
							{
								childElementsHTML: '',
								label: instance.get('label'),
								name: instance.getQualifiedName(),
								placeholder: '',
								value: value || ''
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return Lang.String.unescapeHTML(inputNode.val());
					},

					remove: function() {
						var instance = this;

						var siblings = instance.getSiblings();

						var index = siblings.indexOf(instance);

						siblings.splice(index, 1);

						instance.get('container').remove(true);

						instance.fire(
							'remove',
							{
								field: instance
							}
						);

						instance.destroy();
					},

					render: function() {
						var instance = this;

						instance.renderUI();
						instance.bindUI();
						instance.syncUI();

						return instance;
					},

					renderRepeatableUI: function() {
						var instance = this;

						var container = instance.get('container');

						container.append(TPL_REPEATABLE_ADD);
						container.append(TPL_REPEATABLE_DELETE);
					},

					renderTemplate: function() {
						var instance = this;

						var container = instance.get('container');

						var parent = instance.get('parent');

						var template = instance.getTemplate();

						container.html(template);
					},

					repeat: function() {
						var instance = this;

						var fieldType = instance.get('fieldType');

						var siblings = instance.getSiblings();

						var index = siblings.indexOf(instance);

						var form = instance.get('form');

						var fieldTypeInstance = FieldTypes.get(fieldType);

						var fieldClassName = fieldTypeInstance.get('className');

						var fieldClass = Util.getFieldClass(fieldClassName);

						var field = new fieldClass(
							{
								definition: instance.get('definition'),
								fieldType: fieldType,
								form: form,
								parent: instance.get('parent'),
								portletNamespace: instance.get('portletNamespace'),
								repeatedIndex: index + 1
							}
						);

						field.addTarget(form);

						siblings.splice(index + 1, 0, field);

						field.render();

						instance.get('container').insert(field.get('container'), 'after');

						instance.fire(
							'repeat',
							{
								field: field
							}
						);

						return field;
					},

					syncRepeatablelUI: function() {
						var instance = this;

						var container = instance.get('container');

						var siblings = instance.getRepeatedSiblings();

						container.one('.lfr-ddm-form-field-repeatable-delete-button').toggle(instance.get('repeatedIndex') > 0);
					},

					toJSON: function() {
						var instance = this;

						var fieldJSON = {
							instanceId: instance.get('instanceId'),
							name: instance.get('name')
						};

						if (instance.get('localizable')) {
							var valueMap = {};

							valueMap[instance.get('locale')] = instance.getValue();

							fieldJSON.value = valueMap;
						}
						else {
							fieldJSON.value = instance.getValue();
						}

						var fields = instance.get('fields');

						if (fields.length) {
							fieldJSON.nestedFieldValues = AArray.invoke(fields, 'toJSON');
						}

						return fieldJSON;
					},

					_getLabel: function() {
						var instance = this;

						var definition = instance.get('definition');

						var label = definition.name;

						if (definition.label && definition[instance.get('locale')]) {
							label = definition[instance.get('locale')];
						}

						return label;
					},

					_getLocalizable: function() {
						var instance = this;

						return !!instance.get('definition').localizable;
					},

					_getName: function() {
						var instance = this;

						return instance.get('definition').name;
					},

					_getRepeatable: function() {
						var instance = this;

						return instance.get('definition').repeatable === true;
					},

					_handleToolbarClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.hasClass('lfr-ddm-form-field-repeatable-add-button')) {
							instance.repeat();
						}
						else if (currentTarget.hasClass('lfr-ddm-form-field-repeatable-delete-button')) {
							instance.remove();
						}

						event.stopPropagation();
					},

					_syncRepeatableField: function(event) {
						var instance = this;

						if (instance.get('repeatable')) {
							var fieldNode = instance.getInputNode();

							instance.set('repeatedIndex', instance._valueRepeatedIndex());

							fieldNode.attr('name', instance.getQualifiedName());

							instance.syncRepeatablelUI();
						}
					},

					_valueContainer: function() {
						var instance = this;

						return A.Node.create('<div class="lfr-ddm-form-field-container"></div>');
					},

					_valueInstanceId: function() {
						var instance = this;

						return Util.generateInstanceId(8);
					},

					_valueRepeatedIndex: function() {
						var instance = this;

						var form = instance.get('form');

						var repeatedIndex = -1;

						if (A.instanceOf(form, Renderer.Form)) {
							var found = false;

							form.getFieldNodes().each(
								function(item) {
									if (!found) {
										var fieldNode = item.one('.field-wrapper');

										var fieldQualifiedName = fieldNode.getData('fieldname');

										var fieldInstanceId = Util.getInstanceIdFromQualifiedName(fieldQualifiedName);

										if (fieldInstanceId === instance.get('instanceId')) {
											found = true;
										}

										var fieldName = Util.getFieldNameFromQualifiedName(fieldQualifiedName);

										if (fieldName === instance.get('name')) {
											repeatedIndex++;
										}
									}
								}
							);
						}

						return repeatedIndex;
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Field = Field;
	},
	'',
	{
		requires: ['aui-form-builder-field-base', 'aui-form-field', 'liferay-ddm-form-renderer-field-types']
	}
);