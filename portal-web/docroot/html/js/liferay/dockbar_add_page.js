AUI.add(
	'liferay-dockbar-add-page',
	function(A) {
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

		var STR_HIDDEN_CHECKBOX = 'hiddenCheckbox';

		var STR_NAME = 'name';

		var STR_NODE_LIST = 'nodeList';

		var STR_NODES = 'nodes';

		var STR_RESPONSE_DATA = 'responseData';

		var STR_TRANSITION = 'transition';

		var STR_VALUE = 'value';

		var AddPage = A.Component.create(
			{
				AUGMENTS: [Dockbar.AddPageSearch, Liferay.PortletBase],

				EXTENDS: Dockbar.AddBase,

				NAME: 'addpage',

				ATTRS: {
					createPageMessage: {
						validator: Lang.isString
					},
					parentLayoutId: {
						validator: Lang.isNumber
					},
					transition: {
						validator: Lang.isObject,
						value: {
							duration: 0.2
						}
					}
				},

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

					_bindUI: function() {
						var instance = this;

						instance._addForm.on('submit', instance._addPage, instance);

						instance._cancelButton.on('click', instance._cancelAction, instance);

						instance._hiddenCheckbox.on('change', instance._updateNavigationProxy, instance);

						instance._nameInput.on('input', instance._updateNavigationProxy, instance);

						instance._togglerDelegate.on('toggler:expandedChange', instance._updateActivePage, instance);
					},

					_addPage: function(event) {
						var instance = this;

						event.preventDefault();

						var nodes = instance.get(STR_NODES);

						nodes.each(
							function(item, index, collection) {
								var header = item.one(SELECTOR_TOGGLER_HEADER);

								var active = header.hasClass(CSS_ACTIVE);

								item.all('input, select, textarea').set('disabled', !active);
							}
						);

						A.io.request(
							instance._addForm.get('action'),
							{
								dataType: 'json',
								form: {
									id: instance._addForm.get('id')
								},
								after: {
									success: function(event, id, obj) {
										var response = this.get(STR_RESPONSE_DATA);

										instance._loadingMask.hide();

										var panel = instance._addForm.ancestor();

										panel.empty();

										panel.plug(A.Plugin.ParseContent);

										panel.setContent(response);
									}
								}
							}
						);

						instance._loadingMask.show();
					},

					_cancelAction: function(event) {
						event.preventDefault();

						Dockbar.loadPanel();
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

								instance.byId('type').set(STR_VALUE, selectedType);

								instance.byId('layoutPrototypeId').set(STR_VALUE, selectedPrototypeId);
							}
						}
					},

					_updateNavigationProxy: function(event) {
						var instance = this;

						Liferay.fire('dockbaraddpage:previewPageTitle',
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
		requires: ['aui-loading-mask-deprecated', 'aui-parse-content', 'aui-toggler-delegate', 'liferay-dockbar', 'liferay-dockbar-add-base', 'liferay-dockbar-add-page-search', 'liferay-toggler-key-filter']
	}
);