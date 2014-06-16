AUI.add(
	'liferay-dockbar-add-page',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;

		var Dockbar = Liferay.Dockbar;

		var CSS_ACTIVE = 'active';

		var CSS_ACTIVE_SELECTOR = '.' + CSS_ACTIVE;

		var DATA_PROTOTYPE_ID_PK = 'data-prototype-id';

		var DATA_TYPE_PK = 'data-type';

		var SELECTOR_TOGGLER_CONTENT = '.toggler-content';

		var SELECTOR_TOGGLER_HEADER = '.toggler-header';

		var STR_ADD_PAGE_FORM = 'addPageFm';

		var STR_CANCEL_ADD_OPERATION = 'cancelAddOperation';

		var STR_HIDDEN_CHECKBOX = 'addLayoutHidden';

		var STR_ID = 'id';

		var STR_NAME = 'addLayoutName';

		var STR_NODE_LIST = 'nodeList';

		var STR_NODES = 'nodes';

		var STR_RESPONSE_DATA = 'responseData';

		var STR_TRANSITION = 'transition';

		var STR_VALUE = 'value';

		var AddPage = A.Component.create(
			{
				ATTRS: {
					createPageMessage: {
						validator: Lang.isString
					},

					parentLayoutId: {
						validator: Lang.isNumber
					},

					refresh: {
						validator: Lang.isBoolean
					},

					toggleOnCancel: {
						validator: Lang.isBoolean
					},

					transition: {
						validator: Lang.isObject,
						value: {
							duration: 0.2
						}
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: Dockbar.AddBase,

				NAME: 'addpage',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var nodeList = instance.get(STR_NODE_LIST);

						instance._togglerDelegate = new A.TogglerDelegate(
							{
								animated: true,
								closeAllOnExpand: true,
								container: nodeList,
								content: SELECTOR_TOGGLER_CONTENT,
								expanded: false,
								header: SELECTOR_TOGGLER_HEADER,
								transition: instance.get(STR_TRANSITION)
							}
						).plug(Liferay.TogglerKeyFilter);

						instance._addForm = instance.byId(STR_ADD_PAGE_FORM);

						instance._cancelButton = instance.byId(STR_CANCEL_ADD_OPERATION);

						instance._hiddenCheckbox = instance.byId(STR_HIDDEN_CHECKBOX);

						instance._nameInput = instance.byId(STR_NAME);

						instance._loadingMask = new A.LoadingMask(
							{
								strings: {
									loading: instance.get('createPageMessage')
								},
								target: instance._addForm
							}
						);

						instance._bindUI();
					},

					_addPage: function(event) {
						var instance = this;

						var addForm = instance._addForm;

						var formValidator = instance._getFormValidator(addForm);

						if (!formValidator.hasErrors()) {
							instance._disableFormElements();

							if (instance.get('refresh')) {
								submitForm(addForm);
							}
							else {
								event.preventDefault();

								A.io.request(
									addForm.get('action'),
									{
										after: {
											success: function(event, id, obj) {
												var responseData = this.get(STR_RESPONSE_DATA);

												instance._loadingMask.hide();

												var panel = addForm.ancestor();

												panel.empty();

												panel.plug(A.Plugin.ParseContent);

												panel.setContent(responseData);
											}
										},
										dataType: 'JSON',
										form: {
											id: addForm.get(STR_ID)
										}
									}
								);

								instance._loadingMask.show();
							}
						}
						else {
							AObject.some(
								formValidator.errors,
								function(item, index) {
									var field = formValidator.getField(index);

									field.scrollIntoView();
									field.focus();

									return true;
								}
							);
						}
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._addForm.on('submit', instance._addPage, instance),
							instance._cancelButton.on('click', instance._cancelAction, instance),
							instance._hiddenCheckbox.on('change', instance._updateNavigationProxy, instance),
							instance._nameInput.on('valuechange', instance._updateNavigationProxy, instance),
							instance._togglerDelegate.on('toggler:expandedChange', instance._updateActivePage, instance)
						);
					},

					_cancelAction: function(event) {
						var instance = this;

						if (instance.get('toggleOnCancel')) {
							event.preventDefault();

							Dockbar.toggleAddPanel();
						}
					},

					_disableFormElements: function() {
						var instance = this;

						var nodes = instance.get(STR_NODES);

						nodes.each(
							function(item, index) {
								var header = item.one(SELECTOR_TOGGLER_HEADER);

								var active = header.hasClass(CSS_ACTIVE);

								item.all('input, select, textarea').attr('disabled', !active);
							}
						);
					},

					_getFormValidator: function(formNode) {
						var instance = this;

						if (!instance._formValidator) {
							instance._formValidator = Liferay.Form.get(formNode.attr('id')).formValidator;
						}

						return instance._formValidator;
					},

					_updateActivePage: function(event) {
						var instance = this;

						var nodeList = instance.get(STR_NODE_LIST);

						if (event.newVal) {
							if (nodeList) {
								nodeList.all(CSS_ACTIVE_SELECTOR).removeClass(CSS_ACTIVE);
							}

							var header = event.target.get('header');

							if (header) {
								var selectedType = header.attr(DATA_TYPE_PK);

								var selectedPrototypeId = header.attr(DATA_PROTOTYPE_ID_PK);

								var selectedPageTemplate = header.one('input');

								selectedPageTemplate.attr('checked', true);

								header.addClass(CSS_ACTIVE);

								instance.byId('addLayoutType').set(STR_VALUE, selectedType);

								instance.byId('addLayoutPrototypeId').set(STR_VALUE, selectedPrototypeId);
							}
						}
					},

					_updateNavigationProxy: function(event) {
						var instance = this;

						Liferay.fire(
							'dockbaraddpage:previewPageTitle',
							{
								data: {
									hidden: instance._hiddenCheckbox.get('checked'),
									name: instance._nameInput.val(),
									parentLayoutId: instance.get('parentLayoutId')
								}
							}
						);
					}
				}
			}
		);

		Dockbar.AddPage = AddPage;
	},
	'',
	{
		requires: ['aui-loading-mask-deprecated', 'aui-parse-content', 'aui-toggler-delegate', 'liferay-dockbar', 'liferay-dockbar-add-base', 'liferay-dockbar-add-page-search', 'liferay-portlet-base', 'liferay-toggler-key-filter']
	}
);