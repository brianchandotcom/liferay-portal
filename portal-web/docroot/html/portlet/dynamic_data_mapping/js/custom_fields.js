AUI.add(
	'liferay-portlet-dynamic-data-mapping-custom-fields',
	function(A) {
		var AArray = A.Array;

		var FormBuilderTextField = A.FormBuilderTextField;
		var FormBuilderTypes = A.FormBuilder.types;

		var Lang = A.Lang;

		var booleanParse = A.DataType.Boolean.parse;
		var camelize = Liferay.Util.camelize;
		var trim = A.Lang.trim;

		var STR_BLANK = '';

		var STR_DASH = '-';

		var STR_SPACE = ' ';

		var TPL_BUTTON = '<div class="field-labels-inline">' +
							'<input type="button" value="' + A.Escape.html(Liferay.Language.get('select')) + '" />' +
						'<div>';

		var TPL_GEOLOCATION = '<div class="field-labels-inline">' +
									'<input type="button" value="' + A.Escape.html(Liferay.Language.get('geolocate')) + '" />' +
								'<div>';

		var TPL_LINK_TO_PAGE = '<div class="lfr-ddm-link-to-page">' +
								'<a href="javascript:;">' + Liferay.Language.get('link') + '</a>' +
							'</div>';

		var TPL_PARAGRAPH = '<p></p>';

		var TPL_SEPARATOR = '<div class="separator"></div>';

		var TPL_TEXT_HTML = '<textarea class="form-builder-field-node lfr-ddm-text-html"></textarea>';

		var TPL_WCM_IMAGE = '<div class="lfr-wcm-image"></div>';

		var applyStyles = function(node, styleContent) {
			var styles = styleContent.replace(/\n/g, STR_BLANK).split(';');

			node.setStyle(STR_BLANK);

			AArray.each(
				styles,
				function(item, index) {
					var rule = item.split(':');

					if (rule.length == 2) {
						var key = camelize(rule[0]);
						var value = trim(rule[1]);

						node.setStyle(key, value);
					}
				}
			);
		};

		var DLFileEntryCellEditor = A.Component.create(
			{
				EXTENDS: A.BaseCellEditor,

				NAME: 'document-library-file-entry-cell-editor',

				prototype: {
					ELEMENT_TEMPLATE: '<input type="hidden" />',

					getElementsValue: function() {
						var instance = this;

						return instance.get('value');
					},

					_defInitToolbarFn: function() {
						var instance = this;

						DLFileEntryCellEditor.superclass._defInitToolbarFn.apply(instance, arguments);

						instance.toolbar.add(
							{
								label: Liferay.Language.get('choose'),
								on: {
									click: A.bind('_onClickChoose', instance)
								}
							},
							1
						);

						instance.toolbar.add(
							{
								label: Liferay.Language.get('clear'),
								on: {
									click: A.bind('_onClickClear', instance)
								}
							},
							2
						);
					},

					_onClickChoose: function() {
						var instance = this;

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						portletURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
						portletURL.setParameter('eventName', 'selectDocumentLibrary');
						portletURL.setParameter('groupId', themeDisplay.getScopeGroupId());
						portletURL.setParameter('refererPortletName', '167');
						portletURL.setParameter('struts_action', '/document_selector/view');
						portletURL.setPortletId('200');
						portletURL.setWindowState('pop_up');

						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true
								},
								eventName: 'selectDocumentLibrary',
								id: 'selectDocumentLibrary',
								title: Liferay.Language.get('javax.portlet.title.20'),
								uri: portletURL.toString()
							},
							function(event) {
								instance._selectFileEntry(event.url, event.uuid, event.groupid, event.title, event.version);
							}
						);
					},

					_onClickClear: function() {
						var instance = this;

						instance.set('value', STR_BLANK);
					},

					_selectFileEntry: function(url, uuid, groupId, title, version) {
						var instance = this;

						instance.set(
							'value',
							JSON.stringify(
								{
									groupId: groupId,
									title: title,
									uuid: uuid,
									version: version
								}
							)
						);
					},

					_syncFileLabel: function(title, url) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var linkNode = contentBox.one('a');

						if (!linkNode) {
							linkNode = A.Node.create('<a></a>');

							contentBox.prepend(linkNode);
						}

						linkNode.setAttribute('href', url);
						linkNode.setContent(Liferay.Util.escapeHTML(title));
					},

					_uiSetValue: function(val) {
						var instance = this;

						if (val) {
							Liferay.FormBuilder.Util.getFileEntry(
								val,
								function(fileEntry) {
									var url = Liferay.FormBuilder.Util.getFileEntryURL(fileEntry);

									instance._syncFileLabel(fileEntry.title, url);
								}
							);
						}
						else {
							instance._syncFileLabel(STR_BLANK, STR_BLANK);

							val = STR_BLANK;
						}

						instance.elements.val(val);
					}
				}
			}
		);

		var LinkToPageCellEditor = A.Component.create(
			{
				EXTENDS: A.DropDownCellEditor,

				NAME: 'link-to-page-cell-editor',

				prototype: {
					OPT_GROUP_TEMPLATE: '<optgroup label="{label}">{options}</optgroup>',

					renderUI: function(val) {
						var instance = this;

						var options = {};

						LinkToPageCellEditor.superclass.renderUI.apply(instance, arguments);

						A.io.request(
							themeDisplay.getPathMain() + '/layouts_admin/get_layouts',
							{
								after: {
									success: function() {
										var	response = A.JSON.parse(this.get('responseData'));

										if (response && response.layouts) {
											instance._createOptionElements(response.layouts, options, STR_BLANK);

											instance.set('options', options);
										}
									}
								},
								data: {
									cmd: 'getAll',
									expandParentLayouts: true,
									groupId: themeDisplay.getScopeGroupId(),
									p_auth: Liferay.authToken,
									paginate: false
								}
							}
						);
					},

					_createOptionElements: function(layouts, options, prefix) {
						var instance = this;

						AArray.each(
							layouts,
							function(item, index) {
								options[prefix + item.name] = {
									groupId: item.groupId,
									layoutId: item.layoutId,
									name: item.name,
									privateLayout: item.privateLayout
								};

								if (item.hasChildren) {
									instance._createOptionElements(
										item.children.layouts,
										options,
										prefix + STR_DASH + STR_SPACE
									);
								}
							}
						);
					},

					_createOptions: function(val) {
						var instance = this;

						var privateOptions = [];
						var publicOptions = [];

						A.each(
							val,
							function(item, index) {
								var values = {
									id: A.guid(),
									label: index,
									value: Liferay.Util.escapeHTML(JSON.stringify(item))
								};

								var optionsArray = publicOptions;

								if (item.privateLayout) {
									optionsArray = privateOptions;
								}

								optionsArray.push(
									Lang.sub(instance.OPTION_TEMPLATE, values)
								);
							}
						);

						var optGroupTemplate = instance.OPT_GROUP_TEMPLATE;

						var publicOptGroup = Lang.sub(
							optGroupTemplate,
							{
								label: Liferay.Language.get('public-pages'),
								options: publicOptions.join(STR_BLANK)
							}
						);

						var privateOptGroup = Lang.sub(
							optGroupTemplate,
							{
								label: Liferay.Language.get('private-pages'),
								options: privateOptions.join(STR_BLANK)
							}
						);

						var elements = instance.elements;

						elements.setContent(publicOptGroup + privateOptGroup);

						instance.options = elements.all('option');
					},

					_uiSetValue: function(val) {
						var instance = this;

						var options = instance.options;

						if (options && options.size()) {
							options.set('selected', false);

							if (Lang.isValue(val)) {
								var selLayout = Liferay.FormBuilder.Util.parseJSON(val);

								options.each(
									function(item, index) {
										var curLayout = Liferay.FormBuilder.Util.parseJSON(item.attr('value'));

										if ((curLayout.groupId === selLayout.groupId) &&
											(curLayout.layoutId === selLayout.layoutId) &&
											(curLayout.privateLayout === selLayout.privateLayout)) {

											item.set('selected', true);
										}
									}
								);
							}
						}

						return val;
					}
				}
			}
		);

		Liferay.FormBuilder.CUSTOM_CELL_EDITORS = {};

		var customCellEditors = [
			DLFileEntryCellEditor,
			LinkToPageCellEditor
		];

		AArray.each(
			customCellEditors,
			function(item, index) {
				Liferay.FormBuilder.CUSTOM_CELL_EDITORS[item.NAME] = item;
			}
		);

		var LiferayFormBuilderField = function() {
		};

		LiferayFormBuilderField.ATTRS = {
			autoGeneratedName: {
				setter: booleanParse,
				value: true
			},

			indexType: {
				value: 'keyword'
			},

			localizable: {
				setter: booleanParse,
				value: true
			},

			name: {
				setter: Liferay.FormBuilder.normalizeKey,
				valueFn: function() {
					var instance = this;

					return A.FormBuilderField.buildFieldName(instance.get('label'));
				}
			},

			repeatable: {
				setter: booleanParse,
				value: false
			}
		};

		A.Base.mix(A.FormBuilderField, [LiferayFormBuilderField]);

		var FormBuilderProto = A.FormBuilderField.prototype;

		var originalGetPropertyModel = FormBuilderProto.getPropertyModel;

		FormBuilderProto.getPropertyModel = function() {
			var instance = this;

			var model = originalGetPropertyModel.call(instance);

			var type = instance.get('type');

			var indexTypeOptions = {
				'': Liferay.Language.get('no'),
				'keyword': Liferay.Language.get('yes')
			};

			if ((type == 'ddm-text-html') || (type == 'text') || (type == 'textarea')) {
				indexTypeOptions = {
					'': Liferay.Language.get('not-indexable'),
					'keyword': Liferay.Language.get('indexable-keyword'),
					'text': Liferay.Language.get('indexable-text')
				};
			}

			var booleanOptions = {
				'false': Liferay.Language.get('no'),
				'true': Liferay.Language.get('yes')
			};

			return model.concat(
				[
					{
						attributeName: 'indexType',
						editor: new A.RadioCellEditor(
							{
								options: indexTypeOptions
							}
						),
						formatter: function(val) {
							return indexTypeOptions[val.data.value];
						},
						name: Liferay.Language.get('indexable')
					},
					{
						attributeName: 'localizable',
						editor: new A.RadioCellEditor(
							{
								options: booleanOptions
							}
						),
						formatter: function(val) {
							return booleanOptions[val.data.value];
						},
						name: Liferay.Language.get('localizable')
					},
					{
						attributeName: 'repeatable',
						editor: new A.RadioCellEditor(
							{
								options: booleanOptions
							}
						),
						formatter: function(val) {
							return booleanOptions[val.data.value];
						},
						name: Liferay.Language.get('repeatable')
					}
				]
			);
		};

		var DDMDateField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'date'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderTextField,

				NAME: 'ddm-date',

				prototype: {
					renderUI: function() {
						var instance = this;

						DDMDateField.superclass.renderUI.apply(instance, arguments);

						instance.datePicker = new A.DatePicker(
							{
								calendar: {
									locale: Liferay.ThemeDisplay.getLanguageId(),
									strings: {
										next: Liferay.Language.get('next'),
										none: Liferay.Language.get('none'),
										previous: Liferay.Language.get('previous'),
										today: Liferay.Language.get('today')
									}
								},
								trigger: instance.get('templateNode')
							}
						).render();
					},

					getPropertyModel: function() {
						var instance = this;

						var model = DDMDateField.superclass.getPropertyModel.apply(instance, arguments);

						AArray.each(
							model,
							function(item, index, collection) {
								var attributeName = item.attributeName;

								if (attributeName === 'predefinedValue') {
									collection[index] = {
										attributeName: attributeName,
										editor: new A.DateCellEditor(
											{
												dateFormat: '%m/%d/%Y'
											}
										),
										name: Liferay.Language.get('predefined-value')
									};
								}
							}
						);

						return model;
					}
				}
			}
		);

		var DDMDecimalField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'double'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderTextField,

				NAME: 'ddm-decimal'
			}
		);

		var DDMDocumentLibraryField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'document-library'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderField,

				NAME: 'ddm-documentlibrary',

				prototype: {
					getHTML: function() {
						return TPL_BUTTON;
					},

					getPropertyModel: function() {
						var instance = this;

						var model = DDMDocumentLibraryField.superclass.getPropertyModel.apply(instance, arguments);

						AArray.each(
							model,
							function(item, index) {
								var attributeName = item.attributeName;

								if (attributeName === 'predefinedValue') {
									item.editor = new DLFileEntryCellEditor();

									item.formatter = function(obj) {
										var data = obj.data;

										var label = STR_BLANK;

										var value = data.value;

										if (value !== STR_BLANK) {
											label = '(' + Liferay.Language.get('file') + ')';
										}

										return label;
									};
								}
								else if (attributeName === 'type') {
									item.formatter = instance._defaultFormatter;
								}
							}
						);

						return model;
					},

					_defaultFormatter: function() {
						var instance = this;

						return 'documents-and-media';
					},

					_uiSetValue: function() {
						return Liferay.Language.get('select');
					}

				}

			}
		);

		var DDMGeolocationField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'geolocation'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderTextField,

				NAME: 'ddm-geolocation',

				prototype: {
					getHTML: function() {
						return TPL_GEOLOCATION;
					}
				}
			}
		);

		var DDMImageField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'image'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderField,

				NAME: 'ddm-image',

				prototype: {
					getHTML: function() {
						return TPL_WCM_IMAGE;
					}
				}
			}
		);

		var DDMIntegerField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'integer'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderTextField,

				NAME: 'ddm-integer'
			}
		);

		var DDMNumberField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'number'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: A.FormBuilderTextField,

				NAME: 'ddm-number'
			}
		);

		var DDMParagraphField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: undefined
					},

					fieldNamespace: {
						value: 'ddm'
					},

					showLabel: {
						readOnly: true,
						value: true
					},

					style: {
						value: STR_BLANK
					}
				},

				EXTENDS: A.FormBuilderField,

				NAME: 'ddm-paragraph',

				UI_ATTRS: ['label', 'style'],

				prototype: {
					getHTML: function() {
						return TPL_PARAGRAPH;
					},

					getPropertyModel: function() {
						var instance = this;

						return [
							{
								attributeName: 'type',
								editor: false,
								name: Liferay.Language.get('type')
							},
							{
								attributeName: 'label',
								editor: new A.TextAreaCellEditor(),
								name: Liferay.Language.get('text')
							},
							{
								attributeName: 'style',
								editor: new A.TextAreaCellEditor(),
								name: Liferay.Language.get('style')
							}
						];
					},

					_uiSetLabel: function(val) {
						var instance = this;

						instance.get('templateNode').setContent(val);
					},

					_uiSetStyle: function(val) {
						var instance = this;

						var templateNode = instance.get('templateNode');

						applyStyles(templateNode, val);
					}
				}
			}
		);

		var DDMSeparatorField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: undefined
					},

					fieldNamespace: {
						value: 'ddm'
					},

					showLabel: {
						value: false
					},

					style: {
						value: STR_BLANK
					}
				},

				EXTENDS: A.FormBuilderField,

				NAME: 'ddm-separator',

				UI_ATTRS: ['style'],

				prototype: {
					getHTML: function() {
						return TPL_SEPARATOR;
					},

					getPropertyModel: function() {
						var instance = this;

						var model = DDMSeparatorField.superclass.getPropertyModel.apply(instance, arguments);

						model.push(
							{
								attributeName: 'style',
								editor: new A.TextAreaCellEditor(),
								name: Liferay.Language.get('style')
							}
						);

						return model;
					},

					_uiSetStyle: function(val) {
						var instance = this;

						var templateNode = instance.get('templateNode');

						applyStyles(templateNode, val);
					}
				}
			}
		);

		var DDMHTMLTextField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'html'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: FormBuilderTextField,

				NAME: 'ddm-text-html',

				prototype: {
					getHTML: function() {
						return TPL_TEXT_HTML;
					}
				}
			}
		);

		var DDMLinkToPageField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'link-to-page'
					},

					fieldNamespace: {
						value: 'ddm'
					}
				},

				EXTENDS: FormBuilderTextField,

				NAME: 'ddm-link-to-page',

				prototype: {
					getHTML: function() {
						return TPL_LINK_TO_PAGE;
					}
				}
			}
		);

		var plugins = [
			DDMDateField,
			DDMDecimalField,
			DDMDocumentLibraryField,
			DDMGeolocationField,
			DDMImageField,
			DDMIntegerField,
			DDMLinkToPageField,
			DDMNumberField,
			DDMParagraphField,
			DDMSeparatorField,
			DDMHTMLTextField
		];

		AArray.each(
			plugins,
			function(item, index) {
				FormBuilderTypes[item.NAME] = item;
			}
		);
	},
	'',
	{
		requires: ['liferay-portlet-dynamic-data-mapping']
	}
);