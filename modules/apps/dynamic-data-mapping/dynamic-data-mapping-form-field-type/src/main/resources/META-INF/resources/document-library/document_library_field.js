AUI.add(
	'liferay-ddm-form-field-document-library',
	function(A) {
		var Lang = A.Lang;

		var DocumentLibraryField = A.Component.create(
			{
				ATTRS: {
					fileEntryTitle: {
						value: ''
					},

					groupId: {
						value: 0
					},

					itemSelectorAuthToken: {
						value: ''
					},

					strings: {
						value: {
							select: Liferay.Language.get('select')
						}
					},

					type: {
						value: 'document_library'
					},

					value: {
						value: ''
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-document-library',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.bindContainerEvent('click', instance._handleButtonsClick, '> .form-group .btn')
						);
					},

					getDocumentLibrarySelectorURL: function() {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getLayoutRelativeControlPanelURL());

						portletURL.setParameter('criteria', 'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion');
						portletURL.setParameter('doAsGroupId', instance.get('groupId'));
						portletURL.setParameter('itemSelectedEventName', portletNamespace + 'selectDocumentLibrary');
						portletURL.setParameter('p_p_auth', instance.get('itemSelectorAuthToken'));
						portletURL.setParameter('refererGroupId', instance.get('groupId'));

						var criterionJSON = {
							desiredItemSelectorReturnTypes: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
						};

						portletURL.setParameter('0_json', JSON.stringify(criterionJSON));
						portletURL.setParameter('1_json', JSON.stringify(criterionJSON));

						var uploadCriterionJSON = {
							desiredItemSelectorReturnTypes: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
							URL: instance.getUploadURL()
						};

						portletURL.setParameter('2_json', JSON.stringify(uploadCriterionJSON));

						portletURL.setPortletId(Liferay.PortletKeys.ITEM_SELECTOR);
						portletURL.setPortletMode('view');
						portletURL.setWindowState('pop_up');

						return portletURL.toString();
					},

					getStringValue: function() {
						var instance = this;

						var value = instance.get('value');

						if (!Lang.isString(value)) {
							value = JSON.stringify(value);
						}

						return value;
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							DocumentLibraryField.superclass.getTemplateContext.apply(instance, arguments),
							{
								clearButtonVisible: instance._getClearButtonVisible(),
								fileEntryTitle: instance._getFileEntryTitle(),
								strings: instance.get('strings'),
								value: instance.getStringValue()
							}
						);
					},

					getUploadURL: function() {
						var instance = this;

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getLayoutRelativeURL());

						portletURL.setLifecycle(Liferay.PortletURL.ACTION_PHASE);
						portletURL.setParameter('javax.portlet.action', '/document_library/upload_file_entry');
						portletURL.setParameter('p_auth', Liferay.authToken);
						portletURL.setParameter('cmd', 'add_temp');
						portletURL.setParameter('refererGroupId', instance.get('groupId'));
						portletURL.setPortletId(Liferay.PortletKeys.DOCUMENT_LIBRARY);

						return portletURL.toString();
					},

					getValue: function() {
						var instance = this;

						return instance.get('value');
					},

					setValue: function(value) {
						var instance = this;

						instance.set('value', value);

						instance.render();
					},

					showErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						DocumentLibraryField.superclass.showErrorMessage.apply(instance, arguments);

						container.all('.form-feedback-indicator').appendTo(container.one('.form-group'));
					},

					_getClearButtonVisible: function() {
						var instance = this;

						var returnValue = false;
						var value = instance.getValue();

						if (value && value.title && value.uuid) {
							returnValue = true;
						}

						return returnValue;
					},

					_getFileEntryTitle: function() {
						var instance = this;

						var fileEntryTitle = instance.get('fileEntryTitle');
						var value = instance.getValue();

						if (!fileEntryTitle && value) {
							fileEntryTitle = value.title;
						}

						return fileEntryTitle;
					},

					_handleButtonsClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
							var currentTarget = event.currentTarget;

							if (currentTarget.test('.select-button')) {
								instance._handleSelectButtonClick(event);
							}
							else if (currentTarget.test('.clear-button')) {
								instance._handleClearButtonClick(event);
							}
						}
					},

					_handleClearButtonClick: function(event) {
						var instance = this;

						instance.setValue({});

						var errorMessage = instance.get('errorMessage');

						var required = instance.get('required');

						if (!errorMessage && required) {
							instance.set('errorMessage', Liferay.Language.get('this-field-is-required'));
						}

						instance.showErrorMessage();
					},

					_handleSelectButtonClick: function(event) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								eventName: portletNamespace + 'selectDocumentLibrary',
								on: {
									selectedItemChange: function(event) {
										var selectedItem = event.newVal;

										if (selectedItem) {
											instance.setValue(JSON.parse(selectedItem.value));
										}

										instance._fireStartedFillingEvent();
									},
									visibleChange: function(event) {
										if (event.newVal) {
											instance._fireFocusEvent();
										}
										else {
											instance.showErrorMessage();
											instance._fireBlurEvent();
										}
									}
								},
								url: instance.getDocumentLibrarySelectorURL()
							}
						);

						itemSelectorDialog.open();
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').DocumentLibrary = DocumentLibraryField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field', 'liferay-item-selector-dialog']
	}
);