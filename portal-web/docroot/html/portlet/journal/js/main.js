AUI.add(
	'liferay-portlet-journal',
	function(A) {
		var D = A.DataType;
		var JSON = A.JSON;
		var Lang = A.Lang;

		var generateInstanceId = function() {
			var instanceId = '';

			var key = Liferay.Portlet.Journal.PROXY.instanceIdKey;

			for (var i = 0; i < 8; i++) {
				var pos = Math.floor(Math.random() * key.length);

				instanceId += key.substring(pos, pos + 1);
			}

			return instanceId;
		};

		var getUID = function() {
			return (++ A.Env._uidx);
		};

		var TPL_EDITOR_ELEMENT = '<div id="{name}" name="{name}"></div>';

		var TPL_FIELD_CONTAINER = '<div><li class="structure-field {cssClass}">' +
				'<span class="journal-article-close"></span>' +
				'<span class="folder">' +
					'<div class="field-container">' +
						'<input class="journal-article-localized" type="hidden" value="false" />' +
						'<div class="journal-article-move-handler"></div>' +
						'<label for="" class="journal-article-field-label"><span>{fieldLabel}</span></label>' +
						'<div class="journal-article-component-container"></div>' +
						'<span class="field field-choice journal-article-localized-checkbox">' +
							'<span class="field-content">' +
								'<span class="field-element field-label-right">' +
									'<input type="hidden" value="false" name="{portletNamespace}{instanceId}localized-checkbox">' +
									'<input type="checkbox" onclick="Liferay.Util.updateCheckboxValue(this); " name="{portletNamespace}{instanceId}localized-checkboxCheckbox" id="{portletNamespace}{instanceId}localized-checkboxCheckbox"> </span>' +
									'<label for="{portletNamespace}{instanceId}localized-checkboxCheckbox">{localizedLabelLanguage}</label>' +
								'</span>' +
							'</span>' +
						'<div class="alert alert-error journal-article-required-message">{requiredFieldLanguage}</div>' +
						'<div class="journal-article-buttons {articleButtonsRowCSSClass}">' +
							'<span class="field field-inline field-text journal-article-variable-name">' +
								'<span class="field-content">' +
									'<label for="{portletNamespace}{instanceId}variableName">{variableNameLanguage}</label>' +
									'<span class="field-element ">' +
										'<input type="text" size="25" value="{variableName}" name="{portletNamespace}variableName" id="{portletNamespace}{instanceId}variableName">' +
									'</span>' +
								'</span>' +
							'</span>' +
							'{editButtonTemplateHTML}' +
							'{repeatableButtonTemplateHTML}' +
						'</div>' +
					'</div>' +
					'<ul class="folder-droppable"></ul>' +
				'</span>' +
			'</li></div>';

		var TPL_HELPER = '<div id="{0}" class="journal-article-helper not-intersecting">' +
			'<div class="journal-component"></div>' +
			'<div class="forbidden-action"></div>' +
		'</div>';

		var TPL_INSTRUCTIONS_CONTAINER = '<div class="alert alert-info journal-article-instructions-container journal-article-instructions-message"></div>';

		var TPL_OPTION = '<option></option>';

		var TPL_PLACEHOLDER = '<div class="tree-placeholder tree-sub-placeholder"></div>';

		var TPL_STRUCTURE_FIELD_INPUT = '<input class="lfr-input-text" type="text" value="" size="40"/>';

		var TPL_TOOLTIP_IMAGE = '<img align="top" class="journal-article-instructions-container" src="' + themeDisplay.getPathThemeImages() + '/portlet/help.png" />';

		var fieldsDataSet = new A.DataSet();

		var Journal = function(portletNamespace, articleId) {
			var instance = this;

			instance.articleId = articleId;
			instance.timers = {};
			instance.portletNamespace = portletNamespace;

			instance._helperId = instance._getNamespacedId('journalArticleHelper', instance.portletNamespace, '');

			var helperHTML = Lang.sub(TPL_HELPER, [instance._helperId]);

			instance._helper = A.Node.create(helperHTML);

			instance._helper.appendTo(document.body);

			instance.acceptChildren = true;

			instance._initializeTagsSuggestionContent();
			instance._initializePageLoadFieldInstances();
			instance._attachDelegatedEvents();
			instance._attachEvents();
			instance._updateOriginalStructureXSD();
		};

		Journal.prototype = {
			addStructure: function(groupId, structureId, autoStructureId, name, description, xsd, callback) {
				var instance = this;

				var parentStructureId = '';

				var addGroupPermissions = true;
				var addGuestPermissions = true;

				var defaultLocale = instance.getDefaultLocale();

				var nameMap = {};

				nameMap[defaultLocale] = name;

				var descriptionMap = {};

				descriptionMap[defaultLocale] = (Lang.isString(description) && description) ? description : null;

				Liferay.Service(
					'/journalstructure/add-structure',
					{
						autoStructureId: autoStructureId,
						descriptionMap: JSON.stringify(descriptionMap),
						groupId: groupId,
						nameMap: JSON.stringify(nameMap),
						parentStructureId: parentStructureId,
						serviceContext: JSON.stringify(
								{
									addGroupPermissions: addGroupPermissions,
									addGuestPermissions: addGuestPermissions,
									scopeGroupId: groupId
								}
							),
						structureId: structureId,
						xsd: xsd
					},
					function(message) {
						if (Lang.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			buildHTMLEditor: function(fieldInstance) {
				var instance = this;

				var instanceId = fieldInstance.get('instanceId');
				var name = instance.portletNamespace + 'structure_el_' + instanceId + '_content';

				var editorHTML = Lang.sub(
					TPL_EDITOR_ELEMENT,
					{
						id: name,
						name: name
					}
				);

				fieldInstance.set('innerHTML', editorHTML);

				instance._loadEditor(fieldInstance, name);

				return editorHTML;
			},

			buildHTMLEditorURL: function(fieldInstance) {
				var instance = this;

				var url = Journal.PROXY.editorURL;

				url = url.replace(/LIFERAY_NAME/, A.guid());

				var editorImpl = Journal.PROXY.editorImpl;

				url = url.replace(/LIFERAY_SKIP_EDITOR/, Liferay.Util.isEditorPresent(editorImpl));

				return url;
			},

			canDrop: function(source) {
				var instance = this;

				var componentType = instance.getComponentType(source);

				var canDrop = true;

				if ((componentType == 'list') || (componentType == 'multi-list')) {
					canDrop = false;
				}
				else if (source.hasClass('repeated-field') || source.hasClass('parent-structure-field')) {
					canDrop = false;
				}

				return canDrop;
			},

			clearMessage: function(selector) {
				var instance = this;

				var journalMessage = A.one(selector);

				var timer = instance.timers[selector];

				if (timer) {
					timer.cancel();
				}

				journalMessage.hide();
			},

			closeEditFieldOptions: function() {
				var instance = this;

				if (instance.editContainerContextPanel) {
					instance.editContainerContextPanel.set('visible', false);
				}

				instance.unselectFields();
			},

			closeField: function(source) {
				var instance = this;

				if ((instance._hasParentField(source) || instance._hasFirstLevelFields()) &&
					confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-field-and-all-its-children'))) {

					var fieldInstance = instance.getFieldInstance(source);

					fieldInstance.destroy();

					instance.closeRepeatedSiblings(source);
					instance.closeEditFieldOptions();

					if (source.inDoc()) {
						source.remove();
					}
				}
			},

			closeRepeatedSiblings: function(source) {
				var instance = this;

				var fieldInstance = instance.getFieldInstance(source);

				if (!fieldInstance.get('repeated')) {
					var repeatedFields = instance.getRepeatedSiblings(fieldInstance);

					if (repeatedFields) {
						repeatedFields.remove();
					}
				}
			},

			createNestedList: function(nodes, options, events, components) {
				var instance = this;

				var applyEvents = function(nestedList) {
					A.each(
						events,
						function(item, index, collection) {
							if (index && Lang.isFunction(item)) {
								nestedList.on(index, item);
							}
						}
					);
				};

				var defaults = {
					dropOn: '.folder'
				};

				options = A.merge(defaults, options);

				if (!instance._nestedList) {
					instance._nestedList = new A.NestedList(options);

					applyEvents(instance._nestedList);
				}

				if (components && !instance._nestedListComponents) {
					instance._nestedListComponents = new A.NestedList(options);

					applyEvents(instance._nestedListComponents);
				}

				if (A.instanceOf(nodes, A.Node)) {
					nodes = A.all(nodes);
				}

				nodes.each(
					function(item, index, collection) {
						var nestedList = instance._nestedList;

						if (components) {
							nestedList = instance._nestedListComponents;
						}

						nestedList.add(item);
					}
				);
			},

			disableEditMode: function() {
				var instance = this;

				A.getBody().removeClass('portlet-journal-edit-mode');

				var editStructureButton = instance.getById('editStructureButton');
				var editStructureLink = instance.getById('editStructureLink');
				var journalComponentList = instance.getById('journalComponentList');
				var saveStructureButton = instance.getById('saveStructureButton');

				instance.closeEditFieldOptions();

				saveStructureButton.hide();
				journalComponentList.hide();

				var structureButtonText = Liferay.Language.get('edit');

				editStructureLink.show();

				editStructureButton.hide();

				A.all('input.journal-list-label').attr('disabled', 'disabled');

				if (instance.structureChange()) {
					var structureMessage = instance.getById('structureMessage');

					instance.showMessage(
						structureMessage,
						'alert',
						null,
						30000
					);
				}
			},

			disableFields: function() {
				var instance = this;

				var fieldsContainer = instance.getById('journalArticleContainer');

				fieldsContainer.all('input:not(:button)').attr('disabled', 'disabled');
				fieldsContainer.all('textarea, select').attr('disabled', 'disabled');
			},

			downloadArticleContent: function() {
				var instance = this;

				var downloadAction = themeDisplay.getPathMain() + '/journal/get_article_content';
				var auxForm = instance.getPrincipalForm('fm2');

				var articleContent = instance.getArticleContentXML();
				var xmlInput = instance.getByName(auxForm, 'xml', true);

				if (instance.structureChange()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance.openSaveStructureDialog();
					}
				}
				else {
					auxForm.attr('action', downloadAction);
					auxForm.attr('target', '_self');

					xmlInput.val(articleContent);

					submitForm(auxForm, null, false);
				}
			},

			editContainerNormalMode: function() {
				var instance = this;

				var editContainerWrapper = instance.getById('journalArticleEditFieldWrapper');

				editContainerWrapper.removeClass('save-mode');

				instance.editContainerModified = false;
			},

			editContainerSaveMode: function() {
				var instance = this;

				var editContainerWrapper = instance.getById('journalArticleEditFieldWrapper');

				editContainerWrapper.addClass('save-mode');

				instance.editContainerModified = true;
			},

			enableEditMode: function() {
				var instance = this;

				A.getBody().addClass('portlet-journal-edit-mode');

				var editStructureLink = instance.getById('editStructureLink');
				var journalComponentList = instance.getById('journalComponentList');
				var structureMessage = instance.getById('structureMessage');
				var structureTree = instance.getById('structureTree');

				instance.editContainerNormalMode();

				editStructureLink.hide();
				journalComponentList.show();

				structureTree.all('.journal-list-label').attr('disabled', '');

				instance.clearMessage(structureMessage);

				var editStructureButton = instance.getById('editStructureButton');
				var saveStructureButton = instance.getById('saveStructureButton');

				if (editStructureButton) {
					editStructureButton.show();
				}

				if (saveStructureButton) {
					saveStructure.show();
				}

				instance._attachEditStructureEvents();
			},

			enableFields: function() {
				var instance = this;

				var fieldsContainer = instance.getById('journalArticleContainer');

				fieldsContainer.all('input:not(:button)').attr('disabled', '');
				fieldsContainer.all('textarea, select').attr('disabled', '');
			},

			getArticleContentXML: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._getNamespacedId('#structureTree');
				var sourceRoots = A.all(structureTreeId + ' > li');
				var hasStructure = instance.hasStructure();

				var content;

				if (!hasStructure) {
					var item = sourceRoots.item(0);

					if (item) {
						var fieldInstance = instance.getFieldInstance(item);

						content = fieldInstance.getContent(item);
					}
				}
				else {
					var attributes = null;
					var availableLocales = [];
					var stillLocalized = false;
					var availableLocalesElements = A.all('[name=' + instance.portletNamespace + 'available_locales]');
					var defaultLocale = instance.getDefaultLocale();

					instance.getFields().each(
						function(item, index, collection) {
							var fieldInstance = instance.getFieldInstance(item);
							var isLocalized = fieldInstance.get('localized');

							if (isLocalized) {
								stillLocalized = true;
							}
						}
					);

					if (stillLocalized) {
						availableLocalesElements.each(
							function(item, index, collection) {
								var locale = item.val();

								if (locale) {
									availableLocales.push(locale);
								}
							}
						);

						attributes = {
							'available-locales': availableLocales.join(','),
							'default-locale': defaultLocale
						};
					}
					else {
						attributes = {
							'available-locales': defaultLocale,
							'default-locale': defaultLocale
						};
					}

					var root = instance._createDynamicNode('root', attributes);

					buffer.push(root.openTag);

					sourceRoots.each(
						function(item, index, collection) {
							instance._appendStructureTypeElementAndMetaData(item, buffer, true);
						}
					);

					buffer.push(root.closeTag);

					content = buffer.join('');
				}

				return content;
			},

			getById: function(id, namespace) {
				var instance = this;

				return A.one(
					instance._getNamespacedId(id, namespace)
				);
			},

			getByName: function(currentForm, name, withoutNamespace) {
				var instance = this;

				var inputName = withoutNamespace ? name : instance.portletNamespace + name;

				return A.one(currentForm).one('[name=' + inputName + ']');
			},

			getComponentType: function(source) {
				return source.attr('dataType');
			},

			getDefaultLocale: function() {
				var instance = this;

				var defaultLocale = instance.getById('defaultLocale');

				if (defaultLocale) {
					defaultLocale = defaultLocale.val();
				}

				return defaultLocale;
			},

			getEditButton: function(source) {
				var instance = this;

				return source.one('.edit-button');
			},

			getFieldInstance: function(source) {
				var instance = this;

				var id = source.get('id');

				return fieldsDataSet.item(id);
			},

			getFields: function() {
				var instance = this;

				var structureTreeId = instance._getNamespacedId('#structureTree');

				return A.all(structureTreeId + ' li');
			},

			getGroupId: function() {
				var instance = this;

				var groupId = themeDisplay.getScopeGroupId();

				if (instance.articleId) {
					var form = instance.getPrincipalForm();

					var inputGroupId = instance.getByName(form, 'groupId');
					var inputGroupIdVal = inputGroupId.val();

					if (inputGroupIdVal) {
						groupId = inputGroupIdVal;
					}
				}

				return groupId;
			},

			getParentStructureId: function() {
				var instance = this;

				var parentStructureEl = instance.getById('parentStructureId');

				var parentStructureId;

				if (parentStructureEl) {
					parentStructureId = parentStructureEl.val();
				}

				return parentStructureId;
			},

			getRepeatedSiblings: function(fieldInstance) {
				var instance = this;

				var structureTreeId = instance._getNamespacedId('#structureTree');
				var selector = structureTreeId + ' li[dataName=' + fieldInstance.get('variableName') + '].repeated-field';

				return A.all(selector);
			},

			getSaveDialog: function(openCallback) {
				var instance = this;

				if (!instance._saveDialog) {
					var saveStructureTemplateDialog = instance.getById('saveStructureTemplateDialog');
					var htmlTemplate = saveStructureTemplateDialog.html();
					var title = Liferay.Language.get('editing-structure-details');

					var form = instance.getPrincipalForm();

					var groupId = instance.getByName(form, 'groupId');
					var structureIdInput = instance.getByName(form, 'structureId');
					var structureNameInput = instance.getByName(form, 'structureName');
					var structureDescriptionInput = instance.getByName(form, 'structureDescription');
					var storedStructureXSD = instance.getByName(form, 'structureXSD');

					var saveCallback = function() {
						var dialogFields = instance._saveDialog.fields;

						instance.showMessage(
							dialogFields.messageElement,
							'info',
							Liferay.Language.get('waiting-for-an-answer')
						);

						var form = instance.getPrincipalForm();

						var structureIdInput = instance.getByName(form, 'structureId');
						var structureId = structureIdInput.val();

						if (!structureId) {
							var autoGenerateId = dialogFields.saveStructureAutogenerateIdCheckbox.get('checked');

							instance.addStructure(
								groupId.val(),
								dialogFields.dialogStructureId.val(),
								autoGenerateId,
								dialogFields.dialogStructureName.val(),
								dialogFields.dialogDescription.val(),
								dialogFields.contentXSD,
								serviceCallback
							);
						}
						else {
							instance.updateStructure(
								dialogFields.dialogStructureGroupId.val(),
								dialogFields.dialogStructureId.val(),
								instance.getParentStructureId(),
								dialogFields.dialogStructureName.val(),
								dialogFields.dialogDescription.val(),
								dialogFields.contentXSD,
								serviceCallback
							);
						}
					};

					instance._saveDialog = Liferay.Util.Window.getWindow(
						{
							dialog: {
								bodyContent: htmlTemplate,
								toolbars: {
									footer: [
										{
											label: Liferay.Language.get('save'),
											on: {
												click: saveCallback
											}
										},
										{
											label: Liferay.Language.get('cancel'),
											on: {
												click: function() {
													instance._saveDialog.hide();
												}
											}
										}
									]
								}
							},
							title: title
						}
					);

					instance._saveDialog.fields = {
						autoGenerateIdMessage: Liferay.Language.get('autogenerate-id'),
						contentXSD: '',
						dialogDescription: instance.getById('saveStructureStructureDescription'),
						dialogStructureGroupId: instance.getById('saveStructureStructureGroupId'),
						dialogStructureId: instance.getById('saveStructureStructureId'),
						dialogStructureName: instance.getById('saveStructureStructureName'),
						idInput: instance.getById('saveStructureStructureId'),
						loadDefaultStructure: instance.getById('loadDefaultStructure'),
						messageElement: instance.getById('saveStructureMessage'),
						saveStructureAutogenerateId: instance.getById('saveStructureAutogenerateId'),
						saveStructureAutogenerateIdCheckbox: instance.getById('saveStructureAutogenerateIdCheckbox'),
						showStructureIdContainer: instance.getById('showStructureIdContainer'),
						structureIdContainer: instance.getById('structureIdContainer'),
						structureNameLabel: instance.getById('structureNameLabel')
					};

					var dialogFields = instance._saveDialog.fields;

					var serviceCallback = function(message) {
						var exception = message.exception;

						if (!exception) {
							structureDescriptionInput.val(dialogFields.dialogDescription.val());
							structureIdInput.val(message.structureId);
							structureNameInput.val(dialogFields.dialogStructureName.val());
							storedStructureXSD.val(dialogFields.contentXSD);

							dialogFields.dialogStructureGroupId.val(message.structureGroupId);
							dialogFields.dialogStructureId.val(message.structureId);
							dialogFields.structureNameLabel.html(Liferay.Util.escapeHTML(dialogFields.dialogStructureName.val()));
							dialogFields.saveStructureAutogenerateIdCheckbox.hide();

							if (dialogFields.loadDefaultStructure) {
								dialogFields.loadDefaultStructure.show();
							}

							dialogFields.dialogStructureId.attr('disabled', 'disabled');

							instance.showMessage(
								dialogFields.messageElement,
								'success',
								Liferay.Language.get('your-request-processed-successfully')
							);

							var structureMessage = instance.getById('structureMessage');

							structureMessage.hide();
						}
						else {
							var errorMessage = instance._translateErrorMessage(exception);

							instance.showMessage(
								dialogFields.messageElement,
								'error',
								errorMessage
							);
						}
					};

					dialogFields.saveStructureAutogenerateIdCheckbox.on(
						'click',
						function(event) {
							var checkbox = event.target;
							var value = checkbox.get('checked');

							dialogFields.saveStructureAutogenerateId.val(value);

							if (value) {
								dialogFields.dialogStructureId.attr('disabled', 'disabled').val(dialogFields.autoGenerateIdMessage);
							}
							else {
								dialogFields.dialogStructureId.attr('disabled', '').val('');
							}
						}
					);

					dialogFields.showStructureIdContainer.on(
						'click',
						function(event) {
							dialogFields.structureIdContainer.toggle();

							event.halt();
						}
					);

					dialogFields.dialogStructureName.focus();
				}
				else {
					instance._saveDialog.show();
				}

				if (openCallback) {
					openCallback.apply(instance, [instance._saveDialog]);
				}
			},

			getSelectedField: function() {
				var instance = this;

				var selected = null;
				var fields = instance.getFields();

				if (fields) {
					selected = fields.filter('.selected');
				}

				return selected ? selected.item(0) : null;
			},

			getSourceByNode: function(node) {
				var instance = this;

				return node.ancestor('li', true);
			},

			getStructureXSD: function() {
				var instance = this;

				var buffer = [];
				var structureTreeId = instance._getNamespacedId('#structureTree');
				var sourceRoots = A.all(structureTreeId + ' > li.structure-field:not(.repeated-field)').filter(':not(.parent-structure-field)');

				var root = instance._createDynamicNode('root');

				buffer.push(root.openTag);

				A.each(
					sourceRoots,
					function(item, index, collection) {
						instance._appendStructureTypeElementAndMetaData(item, buffer);
					}
				);

				buffer.push(root.closeTag);

				return buffer.join('');
			},

			getTextAreaFields: function() {
				var instance = this;

				var structureTreeId = instance._getNamespacedId('#structureTree');

				var fields = A.all(structureTreeId + ' li[dataType=text_area] div.journal-article-component-container');

				return fields.filter(
					function(item, index, collection) {
						if (item.ancestor('li').attr('datatype') == 'text_area') {
							return item;
						}
					}
				);
			},

			getPrincipalFieldElement: function(source) {
				var instance = this;

				var componentContainer = source.one('div.journal-article-component-container');

				return componentContainer.one('input');
			},

			getPrincipalForm: function(formName) {
				var instance = this;

				return A.one('form[name=' + instance.portletNamespace + (formName || 'fm1') + ']');
			},

			getNodeTypeContent: function() {
				var instance = this;

				return instance.hasStructure() ? 'dynamic-content' : 'static-content';
			},

			hasStructure: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var structureId = instance.getByName(form, 'structureId');

				return structureId && structureId.val();
			},

			hasTemplate: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var templateId = instance.getByName(form, 'templateId');

				return templateId && templateId.val();
			},

			helperIntersecting: function() {
				var instance = this;

				instance._helper.removeClass('not-intersecting');
			},

			helperNotIntersecting: function(helper) {
				var instance = this;

				instance._helper.addClass('not-intersecting');
			},

			hideEditContainerMessage: function() {
				var instance = this;

				var selector = instance.getById('journalMessage');

				selector.hide();
			},

			loadDefaultStructure: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var structureIdInput = instance.getByName(form, 'structureId');
				var templateIdInput = instance.getByName(form, 'templateId');
				var contentInput = instance.getByName(form, 'content');

				structureIdInput.val('');
				templateIdInput.val('');
				contentInput.val('');

				submitForm(form, null, false, false);
			},

			loadEditFieldOptions: function(source) {
				var instance = this;

				var fieldInstance = instance.getFieldInstance(source);

				var check = function(hiddenField, checked) {
					var id = hiddenField.get('id');
					var checkbox = A.one('#' + id + 'Checkbox');

					var value = A.DataType.Boolean.parse(checked);

					hiddenField.val(value);

					if (checkbox) {
						checkbox.set('checked', value);
					}
				};

				if (fieldInstance) {
					var editContainerWrapper = instance.getById('#journalArticleEditFieldWrapper');
					var displayAsTooltip = instance.getById('displayAsTooltip');
					var repeatable = instance.getById('repeatable');
					var fieldType = instance.getById('fieldType');
					var indexType = instance.getById('indexType');
					var instructions = instance.getById('instructions');
					var predefinedValue = instance.getById('predefinedValue');
					var required = instance.getById('required');
					var fieldLabel = instance.getById('fieldLabel');

					fieldType.val(fieldInstance.get('fieldType'));
					indexType.val(fieldInstance.get('indexType'));

					check(displayAsTooltip, fieldInstance.get('displayAsTooltip'));
					check(repeatable, fieldInstance.get('repeatable'));
					check(required, fieldInstance.get('required'));

					fieldLabel.val(fieldInstance.get('fieldLabel'));
					instructions.val(fieldInstance.get('instructions'));
					predefinedValue.val(fieldInstance.get('predefinedValue'));

					var elements = editContainerWrapper.all('input[type=text], select:not([name$=fieldType]), textarea, input[type=checkbox]');

					if (fieldInstance.get('repeated') || fieldInstance.get('parentStructureId')) {
						elements.attr('disabled', 'disabled');
					}
					else {
						elements.attr('disabled', '');
					}
				}
			},

			normalizeValue: function(value) {
				var instance = this;

				if (Lang.isUndefined(value)) {
					value = '';
				}

				return value;
			},

			openPopupWindow: function(url, title, id) {
				var instance = this;

				Liferay.Util.openWindow(
					{
						id: instance.portletNamespace + id,
						title: title,
						uri: url
					}
				);
			},

			openSaveStructureDialog: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var structureIdInput = instance.getByName(form, 'structureId');
				var structureNameInput = instance.getByName(form, 'structureName');
				var structureDescriptionInput = instance.getByName(form, 'structureDescription');

				var structureId = structureIdInput.val();

				instance.getSaveDialog(
					function(dialog) {
						var dialogFields = dialog.fields;

						dialogFields.contentXSD = instance.getStructureXSD();

						dialogFields.dialogStructureName.val(structureNameInput.val());
						dialogFields.dialogDescription.val(structureDescriptionInput.val());
						dialogFields.dialogStructureId.attr('disabled', 'disabled').val(dialogFields.autoGenerateIdMessage);

						if (structureId) {
							dialogFields.saveStructureAutogenerateId.hide();
							dialogFields.dialogStructureId.val(structureIdInput.val());
						}

						dialog.show();

						dialog._setAlignCenter(true);
					}
				);
			},

			previewArticle: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var auxForm = instance.getPrincipalForm('fm2');
				var articleContent = instance.getArticleContentXML();

				if (instance.structureChange()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance.openSaveStructureDialog();
					}
				}
				else if (instance.hasStructure() && !instance.hasTemplate() && !instance.updateStructureDefaultValues()) {
					var templateMessage = Liferay.Language.get('please-add-a-template-to-render-this-structure');

					alert(templateMessage);

					instance.showMessage(
						'#selectTemplateMessage',
						'info',
						templateMessage,
						30000
					);

					var selectTemplateButton = instance.getById('selectTemplateButton');

					if (selectTemplateButton) {
						selectTemplateButton.focus();
					}
				}
				else {
					var defaultLocale = instance.getDefaultLocale();
					var typeInput = instance.getByName(form, 'type');
					var versionInput = instance.getByName(form, 'version');
					var structureIdInput = instance.getByName(form, 'structureId');
					var templateIdInput = instance.getByName(form, 'templateId');

					var previewURL = themeDisplay.getPathMain() + '/journal/view_article_content?cmd=preview&groupId=' + instance.getGroupId() + '&articleId=' + instance.articleId + '&version=' + versionInput.val() + '&languageId=' + defaultLocale + '&type=' + typeInput.val() + '&structureId=' + structureIdInput.val() + '&templateId=' + templateIdInput.val();

					auxForm.attr('action', previewURL);
					auxForm.attr('target', '_blank');

					var titleInput = instance.getByName(form, 'title_' + defaultLocale);
					var titleAuxFormInput = instance.getByName(auxForm, 'title', true);
					var xmlAuxFormInput = instance.getByName(auxForm, 'xml', true);

					titleAuxFormInput.val(titleInput.val());
					xmlAuxFormInput.val(articleContent);

					submitForm(auxForm, null, false);
				}
			},

			renderEditFieldOptions: function(source) {
				var instance = this;

				var editButton = instance.getEditButton(source);
				var fields = instance.getFields();

				instance.editContainerNormalMode();

				fields.removeClass('selected');
				source.addClass('selected');

				instance.hideEditContainerMessage();
				instance.loadEditFieldOptions(source);

				if (instance.editContainerContextPanel) {
					instance.editContainerContextPanel.refreshAlign();
				}
			},

			repeatField: function(source) {
				var instance = this;

				var _cloneFieldInstance = function(originalSource, newSource) {
					var fieldInstance = instance.getFieldInstance(originalSource).clone();
					var instanceId = generateInstanceId();

					newSource.addClass('repeated-field');
					newSource.removeClass('yui-dd-drop yui-dd-draggable');

					var newId = newSource.resetId().get('id');

					fieldsDataSet.add(newId, fieldInstance);
					fieldInstance.set('source', newSource);
					fieldInstance.set('instanceId', instanceId);

					var localizedCheckbox = newSource.one('.journal-article-localized-checkbox input');

					if (localizedCheckbox) {
						localizedCheckbox.attr('checked', false);
					}

					var fieldType = fieldInstance.get('fieldType');

					var componentContainer;

					if (fieldType == 'boolean') {
						componentContainer = newSource.one('.journal-article-component-container');

						componentContainer.one('input').attr('checked', false);
					}
					else if (fieldType == 'document_library' || fieldType == 'text') {
						componentContainer = newSource.one('.journal-article-component-container');

						componentContainer.one('input').val('');
					}
					else if (fieldType == 'image') {
						newSource.all('.journal-image-preview, .journal-image-show-hide').remove(true);
					}
					else if (fieldType == 'text_area') {
						var html = instance.buildHTMLEditor(fieldInstance);

						componentContainer = newSource.one('.journal-article-component-container');

						componentContainer.html(html);
					}
					else if (fieldType == 'text_box') {
						componentContainer = newSource.one('.journal-article-component-container');

						componentContainer.one('input').html('');
					}

					return fieldInstance;
				};

				var newSource = source.clone();

				source.placeAfter(newSource);

				_cloneFieldInstance(source, newSource);

				var children = newSource.all('.structure-field');

				children.each(
					function(item) {
						var fieldInstance = _cloneFieldInstance(item, item);
					}
				);
			},

			repositionEditFieldOptions: function() {
				var instance = this;

				var editContainerWrapper = instance.getById('journalArticleEditFieldWrapper');

				var isVisible = !editContainerWrapper.ancestor('.overlaycontextpanel-hidden');

				if (isVisible) {
					setTimeout(
						function() {
							var lastSelectedField = instance.getSelectedField();

							instance.renderEditFieldOptions(lastSelectedField);
						},
						200
					);
				}
			},

			saveArticle: function(cmd) {
				var instance = this;

				var form = instance.getPrincipalForm();

				if (instance.structureChange()) {
					if (confirm(Liferay.Language.get('you-should-save-the-structure-first'))) {
						instance.openSaveStructureDialog();
					}
				}
				else if (instance.hasStructure() && !instance.hasTemplate() && !instance.updateStructureDefaultValues()) {
					var templateMessage = Liferay.Language.get('please-add-a-template-to-render-this-structure');

					alert(templateMessage);

					instance.showMessage(
						'#selectTemplateMessage',
						'info',
						templateMessage,
						30000
					);

					var selectTemplateButton = instance.getById('selectTemplateButton');

					if (selectTemplateButton) {
						selectTemplateButton.focus();
					}
				}
				else {
					if (!cmd) {
						cmd = instance.articleId ? 'update' : 'add';
					}

					var articleIdInput = instance.getByName(form, 'articleId');
					var classNameIdInput = instance.getByName(form, 'classNameId');
					var cmdInput = instance.getByName(form, 'cmd');
					var contentInput = instance.getByName(form, 'content');
					var newArticleIdInput = instance.getByName(form, 'newArticleId');
					var workflowActionInput = instance.getByName(form, 'workflowAction');

					var classNameId = Liferay.Util.toNumber(classNameIdInput.val());

					var canSubmit = classNameId || instance.validateRequiredFields();

					if (canSubmit) {
						if (cmd == 'publish') {
							workflowActionInput.val(Liferay.Workflow.ACTION_PUBLISH);

							cmd = instance.articleId ? 'update' : 'add';
						}

						cmdInput.val(cmd);

						if (!instance.articleId) {
							articleIdInput.val(newArticleIdInput.val());
						}

						var content = instance.getArticleContentXML();

						contentInput.val(content);

						submitForm(form);
					}
				}
			},

			saveEditFieldOptions: function(source) {
				var instance = this;

				var fieldInstance = instance.getFieldInstance(source);

				if (fieldInstance) {
					var displayAsTooltip = instance.getById('displayAsTooltip');
					var displayAsTooltipCheckbox = instance.getById('displayAsTooltipCheckbox');
					var repeatable = instance.getById('repeatable');
					var repeatableCheckbox = instance.getById('repeatableCheckbox');
					var fieldType = instance.getById('fieldType');
					var indexType = instance.getById('indexType');
					var instructions = instance.getById('instructions');
					var predefinedValue = instance.getById('predefinedValue');
					var required = instance.getById('required');
					var requiredCheckbox = instance.getById('requiredCheckbox');
					var fieldLabel = instance.getById('fieldLabel');
					var localized = source.one('.journal-article-localized');

					var localizedValue = '';

					if (localized) {
						localizedValue = localized.val();
					}

					var journalMessage = instance.getById('journalMessage');

					A.each(
						{
							displayAsTooltip: displayAsTooltipCheckbox.attr('checked'),
							fieldType: fieldType.val(),
							indexType: indexType.val(),
							instructions: instructions.val(),
							localizedValue: localizedValue,
							predefinedValue: predefinedValue.val(),
							repeatable: repeatableCheckbox.attr('checked'),
							required: requiredCheckbox.attr('checked')
						},
						function(item, index, collection) {
							fieldInstance.set(index, item);
						}
					);

					instance.updateFieldLabelName(fieldInstance, fieldLabel.val());

					instance.showMessage(
						journalMessage,
						'success',
						Liferay.Language.get('your-request-processed-successfully')
					);

					instance.editContainerNormalMode();
				}
			},

			showMessage: function(selector, type, message, delay) {
				var instance = this;

				var journalMessage = A.one(selector);
				var className = 'save-structure-message alert alert-' + (type || 'success');

				journalMessage.attr('className', className);
				journalMessage.show();

				if (instance.editContainerContextPanel) {
					instance.editContainerContextPanel.refreshAlign();
				}

				if (message) {
					journalMessage.html(message);
				}

				instance.timers[selector] = A.later(
					delay || 5000,
					instance,
					function() {
						journalMessage.hide();

						if (instance.editContainerContextPanel) {
							instance.editContainerContextPanel.refreshAlign();
						}
					}
				);
			},

			structureChange: function(attribute) {
				var instance = this;

				var form = instance.getPrincipalForm();

				var storedStructureXSD = instance.getByName(form, 'structureXSD').val();

				var hasChanged = (storedStructureXSD != instance.getStructureXSD());

				return hasChanged;
			},

			translateArticle: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var cmdInput = instance.getByName(form, 'cmd');

				cmdInput.val('translate');

				var contentInput = instance.getByName(form, 'content');

				var content = instance.getArticleContentXML();

				contentInput.val(content);

				submitForm(form);
			},

			unselectFields: function() {
				var instance = this;

				var selected = instance.getSelectedField();

				if (selected) {
					selected.removeClass('selected');
				}
			},

			updateFieldLabelName: function(fieldInstance, fieldLabel) {
				var instance = this;

				var repeatedSiblings = instance.getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function(item, index, collection) {
						var repeatedFieldInstance = instance.getFieldInstance(item);

						repeatedFieldInstance.set('fieldLabel', fieldLabel);
					}
				);

				fieldInstance.set('fieldLabel', fieldLabel);
			},

			updateFieldVariableName: function(fieldInstance, variableName) {
				var instance = this;

				var repeatedSiblings = instance.getRepeatedSiblings(fieldInstance);

				repeatedSiblings.each(
					function(item, index, collection) {
						var repeatedFieldInstance = instance.getFieldInstance(item);

						repeatedFieldInstance.set('variableName', variableName);
					}
				);

				fieldInstance.set('variableName', variableName);
			},

			updateStructure: function(groupId, structureId, parentStructureId, name, description, xsd, callback) {
				var instance = this;

				var defaultLocale = instance.getDefaultLocale();

				var nameMap = {};

				nameMap[defaultLocale] = name;

				var descriptionMap = {};

				descriptionMap[defaultLocale] = (Lang.isString(description) && description) ? description : null;

				Liferay.Service(
					'/journalstructure/update-structure',
					{
						descriptionMap: JSON.stringify(descriptionMap),
						groupId: groupId,
						nameMap: JSON.stringify(nameMap),
						parentStructureId: parentStructureId || '',
						serviceContext: JSON.stringify(
							{
								scopeGroupId: groupId
							}
						),
						structureId: structureId,
						xsd: xsd
					},
					function(message) {
						if (Lang.isFunction(callback)) {
							callback(message);
						}
					}
				);
			},

			updateStructureDefaultValues: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var classNameId = instance.getByName(form, 'classNameId');

				return (classNameId && classNameId.val() > 0);
			},

			updateTextAreaVisibility: function(visibility) {
				var instance = this;

				var textAreaFields = instance.getTextAreaFields();

				if (textAreaFields) {
					textAreaFields.setStyle('visibility', visibility);
				}
			},

			validateRequiredFields: function() {
				var instance = this;

				var canSubmit = true;
				var firstEmptyField = null;

				var structureTreeId = instance._getNamespacedId('#structureTree');
				var fields = A.all(structureTreeId + ' li');
				var requiredFields = fields.filter('[dataRequired=true]');
				var fieldsConatainer = A.all(structureTreeId + ' li .field-container');

				fieldsConatainer.removeClass('required-field');

				A.each(
					requiredFields,
					function(item, index, collection) {
						var fieldInstance = instance.getFieldInstance(item);
						var content = fieldInstance.getContent(item);

						if (!content) {
							var fieldConatainer = item.one('.field-container');

							fieldConatainer.addClass('required-field');

							if (canSubmit) {
								firstEmptyField = instance.getPrincipalFieldElement(item);
							}

							canSubmit = false;
						}
					}
				);

				if (firstEmptyField) {
					firstEmptyField.focus();
				}

				return canSubmit;
			},

			_appendStructureChildren: function(source, buffer, generateArticleContent) {
				var instance = this;

				var selector = '> span.folder > ul > li';

				if (!generateArticleContent) {
					selector += '.structure-field:not(.repeated-field):not(.parent-structure-field)';
				}

				var children = source.all(selector);

				A.each(
					children,
					function(item, index, collection) {
						instance._appendStructureTypeElementAndMetaData(item, buffer, generateArticleContent);
					}
				);
			},

			_appendStructureTypeElementAndMetaData: function(source, buffer, generateArticleContent) {
				var instance = this;

				var fieldInstance = instance.getFieldInstance(source);

				if (fieldInstance) {
					var typeElement;
					var type = fieldInstance.get('fieldType');
					var indexType = fieldInstance.get('indexType');

					if (generateArticleContent) {
						var instanceId = fieldInstance.get('instanceId');

						if (!instanceId) {
							instanceId = generateInstanceId();
							fieldInstance.set('instanceId', instanceId);
						}

						typeElement = instance._createDynamicNode(
							'dynamic-element',
							{
								'instance-id': instanceId,
								name: Liferay.Util.escapeHTML(fieldInstance.get('variableName')),
								type: Liferay.Util.escapeHTML(type),
								'index-type': indexType
							}
						);
					}
					else {
						typeElement = instance._createDynamicNode(
							'dynamic-element',
							{
								name: Liferay.Util.escapeHTML(fieldInstance.get('variableName')),
								type: Liferay.Util.escapeHTML(type),
								'index-type': indexType,
								repeatable: fieldInstance.get('repeatable')
							}
						);
					}

					var dynamicContentAttrs = null;

					if (fieldInstance.get('localized')) {
						var localizedValue = fieldInstance.get('localizedValue');

						if (localizedValue !== 'false') {
							dynamicContentAttrs = {
								'language-id': localizedValue
							};
						}
					}

					var nodeTypeContent = instance.getNodeTypeContent();
					var typeContent = instance._createDynamicNode(nodeTypeContent, dynamicContentAttrs);
					var metadata = instance._createDynamicNode('meta-data');

					var entryInstructions = instance._createDynamicNode(
						'entry',
						{
							name: 'instructions'
						}
					);

					var entryRequired = instance._createDynamicNode(
						'entry',
						{
							name: 'required'
						}
					);

					var displayAsTooltip = instance._createDynamicNode(
						'entry',
						{
							name: 'displayAsTooltip'
						}
					);

					var label = instance._createDynamicNode(
						'entry',
						{
							name: 'label'
						}
					);

					var predefinedValue = instance._createDynamicNode(
						'entry',
						{
							name: 'predefinedValue'
						}
					);

					buffer.push(typeElement.openTag);

					if (!generateArticleContent) {
						instance._appendStructureFieldOptionsBuffer(source, buffer);
					}

					instance._appendStructureChildren(source, buffer, generateArticleContent);

					if (!generateArticleContent) {
						buffer.push(metadata.openTag);

						var displayAsTooltipVal = instance.normalizeValue(
							fieldInstance.get('displayAsTooltip')
						);

						buffer.push(
							displayAsTooltip.openTag,
							'<![CDATA[' + displayAsTooltipVal + ']]>',
							displayAsTooltip.closeTag
						);

						var requiredVal = instance.normalizeValue(
							fieldInstance.get('required')
						);

						buffer.push(
							entryRequired.openTag,
							'<![CDATA[' + requiredVal + ']]>',
							entryRequired.closeTag
						);

						var instructionsVal = instance.normalizeValue(
							fieldInstance.get('instructions')
						);

						buffer.push(
							entryInstructions.openTag,
							'<![CDATA[' + instructionsVal + ']]>',
							entryInstructions.closeTag
						);

						var fieldLabelVal = instance.normalizeValue(
							fieldInstance.get('fieldLabel')
						);

						buffer.push(
							label.openTag,
							'<![CDATA[' + fieldLabelVal + ']]>',
							label.closeTag
						);

						var predefinedValueVal = instance.normalizeValue(
							fieldInstance.get('predefinedValue')
						);

						buffer.push(
							predefinedValue.openTag,
							'<![CDATA[' + predefinedValueVal + ']]>',
							predefinedValue.closeTag,
							metadata.closeTag
						);
					}
					else if (generateArticleContent) {
						buffer.push(typeContent.openTag);

						var appendOptions = (type == 'list') || (type == 'multi-list');

						if (appendOptions) {
							instance._appendStructureFieldOptionsBuffer(source, buffer, generateArticleContent);
						}
						else {
							var content = fieldInstance.getContent(source) || '';

							buffer.push('<![CDATA[' + content + ']]>');
						}

						buffer.push(typeContent.closeTag);
					}

					buffer.push(typeElement.closeTag);
				}
			},

			_appendStructureFieldOptionsBuffer: function(source, buffer, generateArticleContent) {
				var instance = this;

				var fieldInstance = instance.getFieldInstance(source);
				var type = fieldInstance.get('fieldType');
				var optionsList = source.all('> .folder > .field-container > .journal-article-component-container > .journal-list-subfield option');

				if (optionsList) {
					A.each(
						optionsList,
						function(item, index, collection) {
							var optionKey = item.text();
							var optionValue = item.val();

							if (!generateArticleContent) {
								var typeElementOption = instance._createDynamicNode(
									'dynamic-element',
									{
										name: Liferay.Util.escapeHTML(optionKey),
										type: Liferay.Util.escapeHTML(optionValue),
										'repeatable': fieldInstance.get('repeatable')
									}
								);

								buffer.push(typeElementOption.openTag + typeElementOption.closeTag);
							}
							else {
								if (item.get('selected')) {
									var multiList = (type == 'multi-list');
									var option = instance._createDynamicNode('option');

									if (multiList) {
										buffer.push(option.openTag);
									}

									buffer.push('<![CDATA[' + Liferay.Util.escapeCDATA(optionValue) + ']]>');

									if (multiList) {
										buffer.push(option.closeTag);
									}
								}
							}
						}
					);
				}
			},

			_attachDelegatedEvents: function() {
				var instance = this;

				var container = instance.getById('journalArticleContainer');

				var addListItem = function(event) {
					var icon = event.currentTarget;
					var iconParent = icon.get('parentNode');
					var select = iconParent.get('parentNode').one('select');
					var keyInput = iconParent.one('input.journal-list-key');
					var key = keyInput.val();
					var valueInput = iconParent.one('input.journal-list-value');
					var value = valueInput.val();

					if (key && value) {
						var options = select.all('option');

						options.each(
							function(item, index, collection) {
								var keyText = Lang.trim(key);
								var itemText = Lang.trim(item.text());

								if (itemText.toLowerCase() == keyText.toLowerCase()) {
									item.remove();
								}
							}
						);

						var option = A.Node.create(TPL_OPTION).val(value).text(key);

						select.append(option);
						option.attr('selected', 'selected');
						keyInput.val('').focus();
						valueInput.val('value');
					}
					else {
						keyInput.focus();
					}
				};

				var keyPressAddItem = function(event) {
					var btnScope = event.currentTarget.get('parentNode').one('span.journal-add-field');

					if (event.isKey('ENTER')) {
						event.currentTarget = btnScope;

						addListItem.apply(event.currentTarget, arguments);
					}
				};

				var removeListItem = function(event) {
					var icon = event.currentTarget;
					var select = icon.get('parentNode').one('select').focus();
					var options = select.all('option');

					options.each(
						function(item, index, collection) {
							if (item.attr('selected')) {
								item.remove();
							}
						}
					);
				};

				container.delegate(
					'mouseenter',
					function(event) {
						var source = instance.getSourceByNode(event.currentTarget);

						source.addClass('repeatable-border');
					},
					'.repeatable-field-image'
				);

				container.delegate(
					'click',
					function(event) {
						var source = instance.getSourceByNode(event.currentTarget);

						instance.closeField(source);
					},
					'.repeatable-field-delete, span.journal-article-close'
				);

				container.delegate(
					'mouseleave',
					function(event) {
						var source = instance.getSourceByNode(event.currentTarget);

						source.removeClass('repeatable-border');
					},
					'.repeatable-field-image'
				);

				container.delegate(
					'click',
					function(event) {
						var editButton = event.currentTarget;

						var source = instance.getSourceByNode(editButton);

						instance.editContainerContextPanel.set('trigger', editButton);
						instance.editContainerContextPanel.show();
					},
					instance._getNamespacedId('#structureTree') + ' div.journal-article-buttons .edit-button'
				);

				container.delegate(
					'click',
					function(event) {
						var source = instance.getSourceByNode(event.currentTarget);

						instance.repeatField(source);
					},
					'.repeatable-field-add, .journal-article-buttons .repeatable-button'
				);

				container.delegate(
					'click',
					function(event) {
						var checkbox = event.currentTarget;
						var source = instance.getSourceByNode(checkbox);

						instance._updateLocaleState(source, checkbox);
					},
					'.journal-article-localized-checkbox input'
				);

				container.delegate('keypress', keyPressAddItem, '.journal-list-key, .journal-list-value');
				container.delegate('click', addListItem, '.journal-add-field');
				container.delegate('click', removeListItem, '.journal-delete-field');

				container.delegate(
					'click',
					function(event) {
						var button = event.currentTarget;
						var buttonValue = null;
						var imagePreview = button.ancestor('.journal-image-preview');
						var imageWrapper = imagePreview.one('.journal-image-wrapper');
						var imageDelete = instance.getByName(imagePreview, 'journalImageDelete');

						if (imageDelete.val() == '') {
							imageDelete.val('delete');
							imageWrapper.hide();

							buttonValue = Liferay.Language.get('cancel');
						}
						else {
							imageDelete.val('');
							imageWrapper.show();

							buttonValue = Liferay.Language.get('delete');
						}

						button.val(buttonValue);
					},
					'#' + instance.portletNamespace + 'journalImageDeleteButton'
				);

				container.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var imagePreviewDiv = link.get('parentNode').get('parentNode').one('.journal-image-preview');

						var showLabel = link.one('.show-label').show();
						var hideLabel = link.one('.hide-label').show();

						var visible = imagePreviewDiv.hasClass('hide');

						if (visible) {
							showLabel.hide();
							hideLabel.show();
						}
						else {
							showLabel.show();
							hideLabel.hide();
						}

						imagePreviewDiv.toggle();
					},
					'.journal-image-link'
				);

				container.delegate(
					'click',
					function(event) {
						var button = event.currentTarget;
						var input = button.ancestor('.journal-article-component-container').one('input');
						var selectUrl = button.attr('data-documentlibraryUrl');

						window[instance.portletNamespace + 'selectDocumentLibrary'] = function(url) {
							input.val(url);
						};

						instance.openPopupWindow(selectUrl, Liferay.Language.get('javax.portlet.title.20'), 'selectDocumentLibrary');
					},
					'.journal-documentlibrary-button'
				);

				container.delegate(
					'mouseover',
					function(event) {
						var image = event.currentTarget;
						var source = instance.getSourceByNode(image);
						var fieldInstance = instance.getFieldInstance(source);

						if (fieldInstance) {
							var instructions = fieldInstance.get('instructions');

							Liferay.Portal.ToolTip.show(this, Liferay.Util.escapeHTML(instructions));
						}
					},
					'img.journal-article-instructions-container'
				);

				var editContainerWrapper = instance.getById('journalArticleEditFieldWrapper');

				if (editContainerWrapper) {
					var editContainerSaveMode = instance.editContainerSaveMode;

					editContainerWrapper.delegate('click', editContainerSaveMode, 'input[type=checkbox]', instance);

					var closeEditField = instance.closeEditFieldOptions;

					editContainerWrapper.delegate('click', closeEditField, '.cancel-button', instance);
					editContainerWrapper.delegate('click', closeEditField, '.close-button', instance);

					editContainerWrapper.delegate(
						'click',
						function(event) {
							var source = instance.getSelectedField();

							instance.saveEditFieldOptions(source);
						},
						'.save-button'
					);
				}

				var variableNameSelector = '[name="' + instance.portletNamespace + 'variableName"]';

				container.delegate('keypress', A.bind('_onKeypressVariableName', instance), variableNameSelector);
				container.delegate('keyup', A.bind('_onKeyupVariableName', instance), variableNameSelector);

				instance._attachDelegatedEvents = Lang.emptyFn;
			},

			_attachEditContainerEvents: function(attribute) {
				var instance = this;

				var editContainerWrapper = instance.getById('journalArticleEditFieldWrapper');

				if (editContainerWrapper) {
					var editContainerCheckboxes = editContainerWrapper.all('input[type=checkbox]');
					var editContainerInputs = editContainerWrapper.all('input[type=text],select');
					var editContainerTextareas = editContainerWrapper.all('textarea');

					editContainerInputs.detach('change');
					editContainerInputs.detach('keypress');
					editContainerTextareas.detach('change');
					editContainerTextareas.detach('keypress');

					var editContainerSaveMode = instance.editContainerSaveMode;

					var changeEvents = ['change', 'keypress'];

					editContainerInputs.on(changeEvents, editContainerSaveMode, instance);
					editContainerTextareas.on(changeEvents, editContainerSaveMode, instance);
				}
			},

			_attachEditStructureEvents: function() {
				var instance = this;

				var journalComponentListId = instance._getNamespacedId('#journalComponentList');
				var structureTreeId = instance._getNamespacedId('#structureTree');

				var editContainerWrapper = instance.getById('journalArticleEditFieldWrapper');
				var fieldLabel = instance.getById('fieldLabel');

				var structureTree = A.one(structureTreeId);

				var placeholder = A.Node.create(TPL_PLACEHOLDER);

				var componentFields = A.all(journalComponentListId + ' .component-group .journal-component');
				var fields = A.all(structureTreeId + ' li.structure-field');

				instance.nestedListOptions = {
					dd: {
						handles: ['.journal-article-move-handler']
					},
					dropCondition: function(event) {
						var dropNode = event.drop.get('node');

						return instance.canDrop(dropNode);
					},
					dropOn: 'span.folder > ul.folder-droppable',
					helper: instance._helper,
					placeholder: placeholder,
					sortCondition: function(event) {
						var dropNode = event.drop.get('node');

						return dropNode.ancestor(structureTreeId);
					},
					sortOn: structureTreeId
				};

				instance.nestedListEvents = {
					'drag:start': function(event) {
						var helper = instance._helper;

						helper.setStyle('height', '100px');
						helper.setStyle('width', '450px');

						instance.updateTextAreaVisibility('hidden');
					},

					'drag:end': function(event) {
						instance._dropField();

						instance.updateTextAreaVisibility('visible');
					},

					'drag:out': function(event) {
						if (!instance.acceptChildren) {
							instance.helperIntersecting();
							instance.acceptChildren = true;
						}
					},

					'drag:over': function(event) {
						var dropNode = event.drop.get('node');

						instance.acceptChildren = instance.canDrop(dropNode);

						if (instance.acceptChildren) {
							instance.helperIntersecting();
						}
						else {
							instance.helperNotIntersecting();
						}
					}
				};

				instance.createNestedList(
					fields.filter(':not(.repeated-field)').filter(':not(.parent-structure-field)'),
					instance.nestedListOptions,
					instance.nestedListEvents
				);

				instance.componentFieldsOptions = {
					dropCondition: function(event) {
						var dropNode = event.drop.get('node');

						return instance.canDrop(dropNode);
					},
					dropOn: 'span.folder > ul.folder-droppable',
					helper: instance._helper,
					placeholder: placeholder,
					sortCondition: function(event) {
						var dropNode = event.drop.get('node');

						return dropNode.ancestor(structureTreeId);
					}
				};

				instance.componentFieldsEvents = {
					'drag:start': function(event) {
						var drag = event.target;
						var proxy = drag.get('dragNode');
						var source = drag.get('node');
						var languageName = source.text();
						var componentType = instance.getComponentType(source);
						var className = 'journal-component-' + instance._stripComponentType(componentType);
						var helper = instance._helper;
						var helperComponentIcon = instance._helper.all('div.journal-component');

						helper.setStyle('height', '25px');
						helper.setStyle('width', '200px');

						if (helperComponentIcon) {
							helperComponentIcon.addClass(className).html(languageName);
						}

						proxy.addClass('component-dragging');

						instance.updateTextAreaVisibility('hidden');

						instance.clonedSource = source.clone();

						source.placeBefore(instance.clonedSource);

						instance.clonedSource.attr('id', '');
						instance.clonedSource.guid();

						instance.clonedSource.show().setStyle('visibility', 'visible');
						instance.clonedSource.removeClass('hide');
						instance.clonedSource.addClass('dragging');

						instance.createNestedList(
							instance.clonedSource,
							instance.componentFieldsOptions,
							instance.componentFieldsEvents,
							true
						);
					},

					'drag:end': function(event) {
						var drag = event.target;
						var source = drag.get('node');
						var proxy = drag.get('dragNode');

						var componentType = instance.getComponentType(source);
						var className = 'journal-component-' + instance._stripComponentType(componentType);
						var helperComponentIcon = instance._helper.all('div.journal-component');

						proxy.removeClass('component-dragging');

						if (helperComponentIcon) {
							helperComponentIcon.removeClass(className).empty();
						}

						var addedComponent = structureTree.one('div.journal-component');

						if (addedComponent) {
							addedComponent.hide();

							var fieldInstance = instance._fieldInstanceFactory(componentType);

							if (fieldInstance.get('fieldType') == 'text_area') {
								instance.buildHTMLEditor(fieldInstance);
							}

							var htmlTemplate = instance._createFieldHTMLTemplate(fieldInstance);
							var newComponent = A.Node.create(htmlTemplate);

							addedComponent.placeBefore(newComponent);
							addedComponent.remove();

							var variableName = newComponent.attr('dataName');
							var randomInstanceId = newComponent.attr('dataInstanceId');

							fieldInstance.set('source', newComponent);
							fieldInstance.set('variableName', variableName);
							fieldInstance.set('instanceId', randomInstanceId);

							instance.createNestedList(
								newComponent,
								instance.nestedListOptions,
								instance.nestedListEvents
							);

							var id = newComponent.get('id');

							fieldsDataSet.add(id, fieldInstance);

							instance.repositionEditFieldOptions();

							var variableNameInput = instance.getById(randomInstanceId + 'variableName');

							if (variableNameInput) {
								Liferay.Util.focusFormField(variableNameInput);
								variableNameInput.select();
							}
						}
						else {
							source.remove();
						}

						instance.updateTextAreaVisibility('visible');

						if (instance.clonedSource) {
							var journalComponentList = instance.getById('#journalComponentList');

							instance.clonedSource.removeClass('dragging');

							if (journalComponentList.contains(source[0]) &&
								journalComponentList.contains(instance.clonedSource[0])) {

								source.remove();
							}
						}
					},

					'drag:out': instance.nestedListEvents['drag:out'],

					'drag:over': instance.nestedListEvents['drag:over']
				};

				instance.createNestedList(
					componentFields,
					instance.componentFieldsOptions,
					instance.componentFieldsEvents,
					true
				);

				if (editContainerWrapper) {
					editContainerWrapper.show();
				}

				instance.editContainerContextPanel = new A.OverlayContextPanel(
					{
						after: {
							hide: A.bind('unselectFields', instance),
							show: function() {
								A.later(
									0,
									instance,
									function() {
										Liferay.Util.focusFormField(fieldLabel);
									}
								);
							}
						},
						align: {
							points: ['lc', 'rc']
						},
						bodyContent: editContainerWrapper,
						trigger: new A.NodeList()
					}
				).render();

				instance.editContainerContextPanel.addTarget(instance);

				instance.on(
					'overlaycontextpanel:visibleChange',
					function(event) {
						if (event.newVal == true) {
							var trigger = event.target.get('trigger');

							trigger.each(
								function(item, index, collection) {
									var parentField = instance.getSourceByNode(item);

									instance.renderEditFieldOptions(parentField);
								}
							);
						}
					}
				);

				A.OverlayContextManager.remove(instance.editContainerContextPanel);

				instance._attachEditStructureEvents = Lang.emptyFn;

				var articleHeaderEdit = instance.getById('articleHeaderEdit');
				var editStructureButton = instance.getById('editStructureButton');
				var saveStructureButton = instance.getById('saveStructureButton');

				if (articleHeaderEdit) {
					articleHeaderEdit.delegate(
						'click',
						function(event) {
							Liferay.reset('controlPanelSidebarHidden');

							instance.disableEditMode();
						},
						instance._getNamespacedId('editStructureButton')
					);

					articleHeaderEdit.delegate('click', instance.openSaveStructureDialog, instance._getNamespacedId('saveStructureButton'), instance);

					articleHeaderEdit.delegate(
						'click',
						function(event) {
							event.preventDefault();

							instance.openSaveStructureDialog();
						},
						'.journal-save-structure-trigger',
						instance
					);
				}
			},

			_attachEvents: function() {
				var instance = this;

				var changeStructureButton = instance.getById('changeStructureButton');
				var downloadArticleContentButton = instance.getById('downloadArticleContentButton');
				var editStructureLink = instance.getById('editStructureLink');
				var loadDefaultStructureButton = instance.getById('loadDefaultStructure');
				var previewArticleButton = instance.getById('previewArticleButton');
				var publishButton = instance.getById('publishButton');
				var saveButton = instance.getById('saveButton');
				var translateButton = instance.getById('translateButton');

				if (changeStructureButton) {
					changeStructureButton.detach('click');

					changeStructureButton.on(
						'click',
						function(event) {
							event.preventDefault();

							var url = event.currentTarget.attr('href');

							instance.openPopupWindow(url, 'ChangeStructure', 'changeStruture');
						}
					);
				}

				if (downloadArticleContentButton) {
					downloadArticleContentButton.detach('click');

					downloadArticleContentButton.on(
						'click',
						function(event) {
							instance.downloadArticleContent();

							event.domEvent.preventDefault();
						}
					);
				}

				if (editStructureLink) {
					editStructureLink.detach('click');

					editStructureLink.on(
						'click',
						function(event) {
							Liferay.set('controlPanelSidebarHidden', true);

							instance._attachEditContainerEvents();

							instance.enableEditMode();
						}
					);
				}

				if (loadDefaultStructureButton) {
					loadDefaultStructureButton.detach('click');

					loadDefaultStructureButton.on(
						'click',
						function() {
							instance.loadDefaultStructure();
						}
					);
				}

				if (previewArticleButton) {
					previewArticleButton.detach('click');

					previewArticleButton.on(
						'click',
						function(event) {
							instance.previewArticle();

							event.domEvent.preventDefault();
						}
					);
				}
			},

			_createDynamicNode: function(nodeName, attributeMap) {
				var instance = this;

				var attrs = [];
				var typeElement = [];

				if (!nodeName) {
					nodeName = 'dynamic-element';
				}

				var typeElementModel = ['<', nodeName, (attributeMap ? ' ' : ''), , '>', ,'</', nodeName, '>'];

				A.each(
					attributeMap || {},
					function(item, index, collection) {
						if (item !== undefined) {
							attrs.push([index, '="', item, '" '].join(''));
						}
					}
				);

				typeElementModel[3] = attrs.join('').replace(/[\s]+$/g, '');
				typeElement = typeElementModel.join('').replace(/></, '>><<').replace(/ +>/, '>').split(/></);

				return {
					closeTag: typeElement[1],
					openTag: typeElement[0]
				};
			},

			_createFieldHTMLTemplate: function(field) {
				var instance = this;

				var fieldContainer = field.getFieldContainer();
				var fieldElementContainer = field.getFieldElementContainer();
				var innerHTML = field.get('innerHTML');
				var type = field.get('fieldType');

				fieldElementContainer.html(innerHTML);

				return fieldContainer.html();
			},

			_dropField: function() {
				var instance = this;

				instance.repositionEditFieldOptions();
			},

			_fieldInstanceFactory: function(options) {
				var instance = this;

				var type;

				if (Lang.isString(options)) {
					type = options;
					options = null;
				}
				else {
					type = options.fieldType;
				}

				options = options || {};

				var model = {
					'boolean': Journal.FieldModel.Boolean,
					'document_library': Journal.FieldModel.DocumentLibrary,
					'image': Journal.FieldModel.Image,
					'link_to_layout': Journal.FieldModel.LinkToPage,
					'list': Journal.FieldModel.List,
					'multi-list': Journal.FieldModel.MultiList,
					'selection_break': Journal.FieldModel.SelectionBreak,
					'text': Journal.FieldModel.Text,
					'text_area': Journal.FieldModel.TextArea,
					'text_box': Journal.FieldModel.TextBox
				};

				options = A.merge(model[type], options);

				var fieldInstance = new Journal.StructureField(
					options,
					instance.portletNamespace
				).render();

				fieldInstance.get('fieldLabel');

				return fieldInstance;
			},

			_getNamespacedId: function(id, namespace, prefix) {
				var instance = this;

				if (!Lang.isString(namespace)) {
					namespace = instance.portletNamespace;
				}

				if (!Lang.isString(prefix)) {
					prefix = '#';
				}

				id = id.replace(/^#/, '');

				return prefix + namespace + id;
			},

			_hasFirstLevelFields: function() {
				var instance = this;

				var firstLevelFields = A.all(instance._getNamespacedId('#structureTree') + '> li');

				return (firstLevelFields && firstLevelFields.size() > 1);
			},

			_hasParentField: function(source) {
				var instance = this;

				var fieldInstance = instance.getFieldInstance(source);

				var id = source.get('id');

				var node = A.one('#' + id);

				return node && node.ancestor().hasClass('folder-droppable');
			},

			_initializePageLoadFieldInstances: function() {
				var instance = this;

				var fields = instance.getFields();

				fields.each(
					function(item, index, collection) {
						var fieldInstance = instance.getFieldInstance(item);

						if (!fieldInstance) {
							var componentName = item.attr('dataName');
							var componentType = item.attr('dataType');
							var displayAsTooltip = item.attr('dataDisplayAsTooltip');
							var fieldLabel = item.attr('dataLabel');
							var indexType = item.attr('dataIndexType');
							var instanceId = item.attr('dataInstanceId');
							var instructions = item.attr('dataInstructions');
							var localized = item.one('.journal-article-localized');
							var parentValue = item.attr('dataParentStructureId');
							var predefinedValue = item.attr('dataPredefinedValue');
							var repeatable = item.attr('dataRepeatable');
							var required = item.attr('dataRequired');

							var localizedValue;

							if (localized) {
								localizedValue = localized.val();
							}

							var isLocalized = (String(localizedValue) != 'false');

							fieldInstance = instance._fieldInstanceFactory(
								{
									displayAsTooltip: displayAsTooltip,
									fieldLabel: fieldLabel,
									instanceId: instanceId,
									instructions: instructions,
									localized: isLocalized,
									localizedValue: localizedValue,
									parentStructureId: parentValue,
									predefinedValue: predefinedValue,
									repeatable: repeatable,
									required: required,
									source: item,
									fieldType: componentType,
									indexType: indexType,
									variableName: componentName
								}
							);
						}

						var id = item.get('id');

						fieldsDataSet.add(id, fieldInstance);
					}
				);
			},

			_initializeTagsSuggestionContent: function() {
				var instance = this;

				window[instance.portletNamespace + 'getSuggestionsContent'] = function() {
					var content = [];

					instance.getFields().each(
						function(item, index, collection) {
							var fieldInstance = instance.getFieldInstance(item);
							var fieldContent = fieldInstance.getContent(item);

							content.push(fieldContent);
						}
					);

					return content.join(' ');
				};
			},

			_loadEditor: function(fieldInstance, editorId) {
				var instance = this;

				var url = instance.buildHTMLEditorURL(fieldInstance);

				A.io.request(
					url,
					{
						method: 'GET',
						on: {
							success: function(event, id, obj) {
								var response = this.get('responseData');

								var editorNode = A.one('#' + editorId);

								editorNode.plug(A.Plugin.ParseContent);

								editorNode.setContent(response);
							}
						}
					}
				);
			},

			_onKeyupVariableName: function(event) {
				var instance = this;

				var variableNameInput = event.currentTarget;
				var source = instance.getSourceByNode(variableNameInput);
				var fieldInstance = instance.getFieldInstance(source);

				if (fieldInstance) {
					var variableNameValue = variableNameInput.val();

					instance.updateFieldVariableName(fieldInstance, variableNameValue);
				}
			},

			_onKeypressVariableName: function(event) {
				var instance = this;

				if (!event.isKeyInSet('BACKSPACE', 'DELETE', 'LEFT', 'RIGHT')) {
					var regex = /^[\w_-]$/;
					var typed = String.fromCharCode(event.keyCode);

					if (!regex.test(typed)) {
						event.halt();
					}
				}
			},

			_stripComponentType: function(type) {
				return type.toLowerCase().replace(/[^a-z]+/g, '');
			},

			_translateErrorMessage: function(exception) {
				var errorText = '';

				if (exception.indexOf('StructureXsdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-xsd');
				}
				else if (exception.indexOf('DuplicateStructureElementException') > -1) {
					errorText = Liferay.Language.get('please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)');
				}
				else if (exception.indexOf('DuplicateStructureIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-unique-id');
				}
				else if (exception.indexOf('StructureDescriptionException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-description');
				}
				else if (exception.indexOf('StructureIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-id');
				}
				else if (exception.indexOf('StructureInheritanceException') > -1) {
					errorText = Liferay.Language.get('this-structure-is-already-within-the-inheritance-path-of-the-selected-parent-please-select-another-parent-structure');
				}
				else if (exception.indexOf('StructureNameException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-name');
				}
				else if (exception.indexOf('NoSuchStructureException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-id');
				}
				else if (exception.indexOf('ArticleContentException') > -1) {
					errorText = Liferay.Language.get('please-enter-valid-content');
				}
				else if (exception.indexOf('ArticleIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-id');
				}
				else if (exception.indexOf('ArticleTitleException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-valid-name');
				}
				else if (exception.indexOf('DuplicateArticleIdException') > -1) {
					errorText = Liferay.Language.get('please-enter-a-unique-id');
				}

				return errorText;
			},

			_updateLocaleState: function(source, checkbox) {
				var instance = this;

				var isLocalized = checkbox.get('checked');
				var defaultLocale = instance.getDefaultLocale();
				var localizedValue = source.one('.journal-article-localized');

				var selectedLocale = defaultLocale;

				var setLocalizedValue = function(value) {
					if (localizedValue) {
						localizedValue.val(value);
					}
				};

				if (isLocalized) {
					setLocalizedValue(selectedLocale);
				}
				else if (!confirm(Liferay.Language.get('unchecking-this-field-will-remove-localized-data-for-languages-not-shown-in-this-view'))) {
					checkbox.attr('checked', true);

					setLocalizedValue(selectedLocale);
				}
				else {
					setLocalizedValue(false);
				}

				var fieldInstance = instance.getFieldInstance(source);

				fieldInstance.set('localized', checkbox.get('checked'));

				fieldInstance.setInstanceId(fieldInstance.get('instanceId'));
			},

			_updateOriginalStructureXSD: function() {
				var instance = this;

				var form = instance.getPrincipalForm();

				var currentXSD = instance.getStructureXSD();

				var structureXSDInput = instance.getByName(form, 'structureXSD');

				if (structureXSDInput) {
					structureXSDInput.val(currentXSD);
				}
			}
		};

		A.augment(Journal, A.EventTarget);

		var StructureField = A.Component.create(
			{
				ATTRS: {
					content: {
						validator: Lang.isString,
						value: ''
					},

					displayAsTooltip: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('displayAsTooltip', D.Boolean.parse(v));
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('displayAsTooltip', true);
						}
					},

					fieldLabel: {
						setter: function(v) {
							var instance = this;

							return instance.setFieldLabel(v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('fieldLabel', '');
						}
					},

					fieldType: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('fieldType', v);
						},
						validator: Lang.isString,
						value: ''
					},

					localized: {
						valueFn: function() {
							var instance = this;

							var localizedValue = instance.getLocalizedValue();

							return (String(localizedValue) == 'true');
						}
					},

					localizedValue: {
						getter: function() {
							var instance = this;

							return instance.getLocalizedValue();
						}
					},

					indexType: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('IndexType', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('IndexType', '');
						}
					},

					innerHTML: {
						validator: Lang.isString,
						value: TPL_STRUCTURE_FIELD_INPUT
					},

					instructions: {
						setter: function(v) {
							var instance = this;

							return instance.setInstructions(v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('instructions', '');
						}
					},

					instanceId: {
						setter: function(v) {
							var instance = this;

							return instance.setInstanceId(v);
						},
						valueFn: function() {
							var instance = this;

							var randomInstanceId = generateInstanceId();

							return instance.getAttribute('instanceId', randomInstanceId);
						}
					},

					optionsEditable: {
						validator: Lang.isBoolean,
						value: true
					},

					parentStructureId: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('parentStructureId', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('parentStructureId', '');
						}
					},

					predefinedValue: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('predefinedValue', v);
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('predefinedValue', '');
						}
					},

					repeatable: {
						setter: function(v) {
							var instance = this;

							return instance.setRepeatable(D.Boolean.parse(v));
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('repeatable', false);
						}
					},

					repeated: {
						getter: function() {
							var instance = this;

							return instance.get('source').hasClass('repeated-field');
						}
					},

					required: {
						setter: function(v) {
							var instance = this;

							return instance.setAttribute('required', D.Boolean.parse(v));
						},
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('required', false);
						}
					},

					source: {
						value: null
					},

					variableName: {
						setter: function(v) {
							var instance = this;

							return instance.setVariableName(v);
						},
						validator: Lang.isString,
						valueFn: function() {
							var instance = this;

							return instance.getAttribute('name');
						}
					}
				},

				EXTENDS: A.Widget,

				NAME: 'structurefield',

				constructor: function(config, portletNamespace) {
					var instance = this;

					instance._lazyAddAttrs = false;

					instance.portletNamespace = portletNamespace;

					StructureField.superclass.constructor.apply(this, arguments);
				},

				UI_ATTRS: ['optionsEditable'],

				prototype: {
					cloneableAttrs: [
						'displayAsTooltip',
						'fieldLabel',
						'fieldType',
						'indexType',
						'innerHTML',
						'instructions',
						'localized',
						'localizedValue',
						'predefinedValue',
						'repeatable',
						'required',
						'variableName'
					],

					initializer: function() {
						var instance = this;

						var propagateAttr = instance.propagateAttr;

						A.each(
							instance.cloneableAttrs,
							function(item, index, collection) {
								instance.after(item + 'Change', propagateAttr);
							}
						);
					},

					destructor: function() {
						var instance = this;

						var source = instance.get('source');

						var children = source.all('.structure-field');

						children.each(
							function(item, index, collection) {
								var fieldInstance = instance.getFieldInstance(item);

								if (fieldInstance) {
									fieldInstance.destroy();
								}
							}
						);

						var fieldType = instance.get('fieldType');

						if (fieldType == 'text_area') {
							var textarea = source.one('textarea');

							if (textarea) {
								var editorName = textarea.attr('name');
								var editorReference = window[editorName];

								if (editorReference && Lang.isFunction(editorReference.destroy)) {
									editorReference.destroy();
								}
							}
						}
					},

					canDrop: function() {
						var instance = this;

						return Journal.prototype.canDrop.apply(instance, arguments);
					},

					clone: function() {
						var instance = this;

						var options = {};
						var portletNamespace = instance.portletNamespace;

						A.each(
							instance.cloneableAttrs,
							function(item, index, collection) {
								options[item] = instance.get(item);
							}
						);

						options.source = null;

						return new StructureField(options, portletNamespace);
					},

					createInstructionsContainer: function(value) {
						return A.Node.create(TPL_INSTRUCTIONS_CONTAINER).html(Liferay.Util.escapeHTML(value));
					},

					createTooltipImage: function() {
						return A.Node.create(TPL_TOOLTIP_IMAGE);
					},

					getAttribute: function(key, defaultValue) {
						var instance = this;

						var value;
						var source = instance.get('source');

						if (source) {
							value = source.attr('data' + key);
						}

						if (Lang.isUndefined(value) && !Lang.isUndefined(defaultValue)) {
							value = defaultValue;
						}

						return value;
					},

					getByName: function() {
						var instance = this;

						return Journal.prototype.getByName.apply(instance, arguments);
					},

					getComponentType: function() {
						var instance = this;

						return Journal.prototype.getComponentType.apply(instance, arguments);
					},

					getContent: function(source) {
						var instance = this;

						var content;
						var type = instance.get('fieldType');
						var componentContainer = source.one('div.journal-article-component-container');

						var principalElement = componentContainer.one('input');

						if (type == 'boolean') {
							content = principalElement.attr('checked');
						}
						else if (type == 'text_area') {
							var editorName = source.one('textarea').attr('name');
							var editorReference = window[editorName];

							if (editorReference && Lang.isFunction(editorReference.getHTML)) {
								content = editorReference.getHTML();
							}
						}
						else if (type == 'multi-list') {
							var output = [];
							var options = principalElement.all('option');

							options.each(
								function(item, index, collection) {
									if (item.get('selected')) {
										var value = item.val();

										output.push(value);
									}
								}
							);

							content = output.join(',');
						}
						else if (type == 'image') {
							var imageDelete = instance.getByName(componentContainer, 'journalImageDelete');

							if (imageDelete && (imageDelete.val() == 'delete')) {
								content = 'delete';
							}
							else {
								var imageInput = componentContainer.one('.journal-image-field input');

								var imageInputValue = imageInput.val() || false;

								if (imageInputValue) {
									content = imageInputValue;
								}
								else {
									var imageContent = componentContainer.one('.journal-image-preview input.journal-image-preview-content');

									if (imageContent) {
										content = imageContent.val();
									}
								}
							}
						}
						else {
							if (principalElement) {
								content = principalElement.val();
							}
						}

						if ((type == 'list') || (type == 'multi-list') || (type == 'text') || (type == 'text_box')) {
							content = Liferay.Util.escapeCDATA(content);
						}

						instance.set('content', content);

						return content;
					},

					getFieldContainer: function() {
						var instance = this;

						if (!instance.fieldContainer) {
							var htmlTemplate = [];
							var fieldLabel = Liferay.Language.get('field');
							var localizedLabelLanguage = Liferay.Language.get('localizable');
							var requiredFieldLanguage = Liferay.Language.get('this-field-is-required');
							var variableNameLanguage = Liferay.Language.get('variable-name');

							var optionsEditable = instance.get('optionsEditable');

							var editButtonTemplate = instance.getById('editButtonTemplate');
							var editButtonTemplateHTML = '';

							if (editButtonTemplate) {
								editButtonTemplateHTML = editButtonTemplate.html();
							}

							var articleButtonsRowCSSClass = '';

							if (!optionsEditable) {
								articleButtonsRowCSSClass = 'hide';
							}

							var repeatableButtonTemplate = instance.getById('repeatableButtonTemplate');
							var repeatableButtonTemplateHTML = '';

							if (repeatableButtonTemplate) {
								repeatableButtonTemplateHTML = repeatableButtonTemplate.html();
							}

							var fieldType = instance.get('fieldType');
							var required = instance.get('required');
							var variableName = instance.get('variableName') + getUID();
							var randomInstanceId = generateInstanceId();

							htmlTemplate = Lang.sub(
								TPL_FIELD_CONTAINER,
								{
									articleButtonsRowCSSClass: articleButtonsRowCSSClass,
									cssClass: 'journal-structure-' + fieldType.replace(/_/g, '-'),
									editButtonTemplateHTML: editButtonTemplateHTML,
									fieldLabel: fieldLabel,
									localizedLabelLanguage: localizedLabelLanguage,
									instanceId: randomInstanceId,
									portletNamespace: instance.portletNamespace,
									repeatableButtonTemplateHTML: repeatableButtonTemplateHTML,
									requiredFieldLanguage: requiredFieldLanguage,
									variableName: variableName,
									variableNameLanguage: variableNameLanguage
								}
							);

							instance.fieldContainer = A.Node.create(htmlTemplate);

							var source = instance.fieldContainer.one('li');

							source.setAttribute('dataName', variableName);
							source.setAttribute('dataRequired', required);
							source.setAttribute('dataType', fieldType);
							source.setAttribute('dataInstanceId', randomInstanceId);

							if (!instance.canDrop(source)) {
								instance.fieldContainer.one('.folder-droppable').remove();
							}
						}

						return instance.fieldContainer;
					},

					getFieldElementContainer: function() {
						var instance = this;

						if (!instance.fieldElementContainer) {
							instance.fieldElementContainer = instance.getFieldContainer().one('div.journal-article-component-container');
						}

						return instance.fieldElementContainer;
					},

					getFieldInstance: function() {
						var instance = this;

						return Journal.prototype.getFieldInstance.apply(instance, arguments);
					},

					getFieldLabelElement: function() {
						var instance = this;

						var source = instance.get('source');

						if (!source) {
							source = instance.getFieldContainer().one('li');
						}

						return source.one('> .folder > .field-container .journal-article-field-label');
					},

					getLocalizedValue: function() {
						var instance = this;

						var source = instance.get('source');

						var input;

						if (source) {
							input = source.one('.journal-article-localized');
						}

						return input ? input.val() : 'false';
					},

					getRepeatedSiblings: function() {
						var instance = this;

						return Journal.prototype.getRepeatedSiblings.apply(instance, [instance]);
					},

					propagateAttr: function(event) {
						var instance = this;

						var siblings = instance.getRepeatedSiblings();

						if (siblings) {
							siblings.each(
								function(item, index, collection) {
									var fieldInstance = instance.getFieldInstance(item);

									if (fieldInstance) {
										fieldInstance.set(event.attrName, event.newVal);
									}
								}
							);
						}
					},

					setFieldLabel: function(value) {
						var instance = this;

						var fieldLabel = instance.getFieldLabelElement();

						if (!value) {
							value = instance.get('variableName');
						}

						if (fieldLabel) {
							fieldLabel.one('span').html(Liferay.Util.escapeHTML(value));

							instance.setAttribute('fieldLabel', value);
						}

						return value;
					},

					setInstanceId: function(value) {
						var instance = this;

						instance.setAttribute('instanceId', value);

						var type = instance.get('fieldType');
						var source = instance.get('source');

						if ((type == 'image') && source) {
							var isLocalized = instance.get('localized');
							var inputFileName = instance.portletNamespace + 'structure_image_' + value + '_' + instance.get('variableName');
							var inputFile = source.one('.journal-article-component-container [type=file]');

							if (isLocalized) {
								inputFileName += '_' + instance.get('localizedValue');
							}

							inputFile.attr('name', inputFileName);
						}

						return value;
					},

					setInstructions: function(value) {
						var instance = this;

						var source = instance.get('source');

						if (source) {
							var fieldInstance = instance.getFieldInstance(source);

							instance.setAttribute('instructions', value);

							if (fieldInstance) {
								var fieldContainer = source.one('> .folder > .field-container');
								var label = fieldInstance.getFieldLabelElement();
								var tooltipIcon = label.one('.journal-article-instructions-container');
								var journalInstructionsMessage = fieldContainer.one('.journal-article-instructions-message');
								var displayAsTooltip = fieldInstance.get('displayAsTooltip');

								if (tooltipIcon) {
									tooltipIcon.remove();
								}

								if (journalInstructionsMessage) {
									journalInstructionsMessage.remove();
								}

								if (value) {
									if (!displayAsTooltip) {
										var instructionsMessage = fieldInstance.createInstructionsContainer(value);
										var requiredMessage = fieldContainer.one('.journal-article-required-message');

										requiredMessage.placeAfter(instructionsMessage);
									}
									else {
										if (label) {
											label.append(fieldInstance.createTooltipImage());
										}
									}
								}
							}
						}

						return value;
					},

					setRepeatable: function(value) {
						var instance = this;

						var source = instance.get('source');

						instance.setAttribute('repeatable', value);

						if (source) {
							var fieldInstance = instance.getFieldInstance(source);
							var fieldContainer = source.one('> .folder > .field-container');
							var repeatableFieldImage = fieldContainer.one('.repeatable-field-image');
							var repeatableAddIcon = source.one('.journal-article-buttons .repeatable-button');

							if (repeatableFieldImage) {
								repeatableFieldImage.remove();
							}

							if (value) {
								var repeatableFieldImageModel = A.Node.create(
									A.one('#repeatable-field-image-model').html()
								);

								fieldContainer.append(repeatableFieldImageModel);

								if (repeatableAddIcon) {
									repeatableAddIcon.show();
								}
							}
							else {
								if (repeatableAddIcon) {
									repeatableAddIcon.hide();
								}
							}
						}

						return value;
					},

					setVariableName: function(value) {
						var instance = this;

						var fieldLabel = instance.getFieldLabelElement();

						if (fieldLabel) {
							var input = fieldLabel.get('parentNode').one('.journal-article-component-container input');

							if (input) {
								input.attr('id', value);

								fieldLabel.setAttribute('for', value);
							}

							instance.setAttribute('name', value);
						}

						return value;
					},

					setAttribute: function(key, value) {
						var instance = this;

						var source = instance.get('source');

						if (Lang.isArray(value)) {
							value = value[0];
						}

						if (source) {
							source.setAttribute('data' + key, value);
						}

						return value;
					},

					_uiSetOptionsEditable: function(val) {
						var instance = this;

						var source = instance.get('source');

						if (source) {
							var journalArticleButtons = source.one('.journal-article-buttons');

							if (journalArticleButtons) {
								if (val) {
									journalArticleButtons.show();
								}
								else {
									journalArticleButtons.hide();
								}
							}
						}
					},

					_getNamespacedId: Journal.prototype._getNamespacedId,

					getById: Journal.prototype.getById
				}
			}
		);

		Journal.StructureField = StructureField;

		Journal.FieldModel = {};

		var fieldModel = Journal.FieldModel;

		var registerFieldModel = function(namespace, type, variableName, optionsEditable) {
			var instance = this;

			var typeEl = A.one('#journalFieldModelContainer div[dataType="' + type + '"]');

			var innerHTML;

			if (typeEl) {
				innerHTML = typeEl.html();
			}

			fieldModel[namespace] = {
				fieldLabel: variableName,
				fieldType: type,
				innerHTML: innerHTML,
				optionsEditable: optionsEditable,
				variableName: variableName
			};
		};

		registerFieldModel('Text', 'text', 'TextField', true);
		registerFieldModel('TextArea', 'text_area', 'TextAreaField', true);
		registerFieldModel('TextBox', 'text_box', 'TextBoxField', true);
		registerFieldModel('Image', 'image', 'ImageField', true);
		registerFieldModel('DocumentLibrary', 'document_library', 'DocumentLibraryField', true);
		registerFieldModel('Boolean', 'boolean', 'BooleanField', true);
		registerFieldModel('List', 'list', 'ListField', true);
		registerFieldModel('MultiList', 'multi-list', 'MultiListField', true);
		registerFieldModel('LinkToPage', 'link_to_layout', 'LinkToPageField', true);
		registerFieldModel('SelectionBreak', 'selection_break', 'SelectionBreakField', false);

		Liferay.Portlet.Journal = Journal;
	},
	'',
	{
		requires: ['aui-base', 'aui-data-set-deprecated', 'aui-datatype', 'aui-dialog-iframe-deprecated', 'aui-io-request', 'aui-nested-list', 'aui-overlay-context-panel-deprecated', 'json', 'liferay-util-window']
	}
);