AUI.add(
	'liferay-ddm-form-builder-field-types-sidebar',
	function(A) {
		var CSS_PREFIX = A.getClassName('form', 'builder', 'field', 'types', 'sidebar');

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var FormBuilderFieldTypesSidebar = A.Component.create(
			{
				ATTRS: {
					builder: {
						value: null
					},
					fieldSets: {
						getter: '_getFieldSets'
					},
					fieldTypes: {
						getter: '_getFieldTypes'
					},
					icons: {
						valueFn: '_getIcons'
					},
					strings: {
						value: {
							basic: Liferay.Language.get('field-types-basic-elements'),
							customized: Liferay.Language.get('field-types-customized-elements'),
							element_set: Liferay.Language.get('element-sets'),
							elements: Liferay.Language.get('elements')
						}
					}
				},

				CSS_PREFIX: CSS_PREFIX,

				EXTENDS: Liferay.DDM.FormBuilderSidebar,

				NAME: 'liferay-ddm-form-builder-field-types-sidebar',

				prototype: {
					bindUI: function() {
						var instance = this;

						FormBuilderFieldTypesSidebar.superclass.bindUI.apply(instance, arguments);

						var boundingBox = instance.get('boundingBox');
						var navLink = '.' + CSS_PREFIX + ' .nav-tabs li';

						instance._eventHandlers.push(A.one('body').delegate('click', instance._bindTabAnimation.bind(instance), navLink));

						if (Liferay.Browser.isMobile()) {
							instance._eventHandlers.push(
								boundingBox.delegate('click', instance._afterFieldSetItemClick.bind(instance), '.lfr-ddm-form-builder-field-set-item'),
									boundingBox.delegate('click', instance._afterFieldTypeItemClick.bind(instance), '.lfr-ddm-form-builder-field-type-item')
							);
						}

						new A.TogglerDelegate(
							{
								animated: true,
								closeAllOnExpand: false,
								container: '.' + CSS_PREFIX + ' .field-types-content',
								content: '.list-group-body',
								expanded: true,
								header: '.list-group-header',
								transition: {
									duration: 0.5,
									easing: 'ease-in-out'
								}
							}
						);
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getTemplateContext: function() {
						var instance = this;

						var context = FormBuilderFieldTypesSidebar.superclass.getTemplateContext.apply(instance, arguments);

						return A.merge(
							context,
							{
								fieldSets: instance.get('fieldSets'),
								fieldTypes: instance.get('fieldTypes'),
								icons: instance.get('icons'),
								strings: instance.get('strings'),
								title: Liferay.Language.get('field-types-sidebar-title'),
								type: 'fieldTypes'
							}
						);
					},

					hasFocus: function(node) {
						var instance = this;

						var activeElement = A.one(node || document.activeElement);

						return instance.get('boundingBox').contains(activeElement);
					},

					_afterFieldSetItemClick: function(event) {
						var instance = this;

						var fieldSetId = event.currentTarget.attr('data-field-set-id');

						instance.get('builder')._getFieldSetDefinitionRetriever(
							fieldSetId,
							function(fieldSetDefinition) {
								instance.get('builder').createFieldSet(fieldSetDefinition);
								instance.get('builder')._traverseFormPages();
								instance.get('builder')._applyDragAndDrop();
								instance.close();
							}
						);
					},

					_afterFieldTypeItemClick: function(event) {
						var instance = this;
						var item = event.currentTarget;
						var result = FieldTypes.getAll().filter(
							function(fieldType) {
								return fieldType.get('name') === item.attr('data-field-type-name');
							}
						);

						instance.get('builder').createNewField(result[0]);
					},

					_afterPressEscapeKey: function() {
						var instance = this;

						if (instance.isOpen()) {
							instance.close();
						}
					},

					_bindTabAnimation: function(e) {
						var instance = this;
						var item = e.currentTarget;

						if (item.hasClass('active')) {
							return;
						}

						var oldItem = A.one('.' + CSS_PREFIX + ' .nav-tabs li.active');
						var oldTarget = A.one('.' + oldItem.attr('data-tab'));
						var target = A.one('.' + item.attr('data-tab'));

						oldItem.removeClass('active');
						oldTarget.hide();

						item.addClass('active');
						target.show();
					},

					_getFieldSets: function(fieldSets) {
						var instance = this;

						var types = [];

						fieldSets.forEach(
							function(fieldSet) {
								types.push(
									{
										description: fieldSet.get('description'),
										icon: window.DDMFieldTypesSidebar.render.Soy.toIncDom(Liferay.Util.getLexiconIconTpl(fieldSet.get('icon'))),
										id: fieldSet.get('id'),
										name: fieldSet.get('name')
									}
								);
							}
						);

						return types;
					},

					_getFieldTypes: function(fieldTypes) {
						var instance = this;

						var types = [];

						fieldTypes.forEach(
							function(fieldType) {
								types.push(
									{
										description: fieldType.get('description'),
										group: fieldType.get('group') || 'customized',
										icon: window.DDMFieldTypesSidebar.render.Soy.toIncDom(Liferay.Util.getLexiconIconTpl(fieldType.get('icon'))),
										label: fieldType.get('label'),
										name: fieldType.get('name')
									}
								);
							}
						);

						return _.groupBy(types, 'group');
					},

					_getIcons: function() {
						var instance = this;

						return {
							angleDown: window.DDMFieldTypesSidebar.render.Soy.toIncDom(Liferay.Util.getLexiconIconTpl('angle-down')),
							angleRight: window.DDMFieldTypesSidebar.render.Soy.toIncDom(Liferay.Util.getLexiconIconTpl('angle-right'))
						};
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderFieldTypesSidebar = FormBuilderFieldTypesSidebar;
	},
	'',
	{
		requires: ['aui-tabview', 'aui-toggler', 'liferay-ddm-form-builder-fieldset', 'liferay-ddm-form-builder-sidebar', 'liferay-ddm-form-renderer-types']
	}
);