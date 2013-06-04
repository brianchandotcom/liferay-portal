AUI.add(
	'liferay-dockbar-add-page',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var CSS_ACTIVE = 'active';

		var CSS_ACTIVE_SELECTOR = '.' + CSS_ACTIVE;

		var DATA_PROTOTYPE_ID_PK = 'data-prototype-id';

		var DATA_TYPE_PK = 'data-type';

		var SELECTOR_TOGGLER_CONTENT = '.toggler-content';

		var SELECTOR_TOGGLER_HEADER = '.toggler-header';

		var STR_ADD_PAGE_FORM = 'addPageFm';

		var STR_CANCEL_ADD_OPERATOIN = 'cancelAddOperation';

		var STR_NODE_LIST = 'nodeList';

		var STR_NODES = 'nodes';

		var STR_RESPONSE_DATA = 'responseData';

		var AddPage = A.Component.create(
			{
				AUGMENTS: [Dockbar.AddPageSearch, Liferay.PortletBase],

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
								transition: {
									duration: 0.2
								}
							}
						);

						instance._addForm = instance.byId(STR_ADD_PAGE_FORM);

						instance._cancelButton = instance.byId(STR_CANCEL_ADD_OPERATOIN);

						instance._toggleLayoutTypeFields();

						instance._bindUI();
					},

					_bindUI: function() {
						var instance = this;

						instance._togglerDelegate.on('toggler:expandedChange', instance._updateActivePage, instance);

						instance._cancelButton.on('click', instance._cancelAction, instance);

						instance._addForm.on('submit', instance._addPage, instance);
					},

					_addPage: function(event) {
						var instance = this;

						event.preventDefault();

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

										var panel = instance._addForm.ancestor();

										panel.plug(A.Plugin.ParseContent);

										instance.destroy();

										panel.setContent(response);
									}
								}
							}
						);
					},

					_cancelAction: function(event) {
						event.preventDefault();

						Dockbar.loadPanel();
					},

					_updateActivePage: function(event) {
						var instance = this;

						var nodeList = instance.get(STR_NODE_LIST);

						if (event.newVal === true) {

							if (nodeList) {
								nodeList.all(CSS_ACTIVE_SELECTOR).removeClass(CSS_ACTIVE);
							}

							var header = event.target.get('header');

							if (header) {
								var selectedType = header.attr(DATA_TYPE_PK);

								var selectedPrototypeId = header.attr(DATA_PROTOTYPE_ID_PK);

								header.addClass(CSS_ACTIVE);

								instance.byId('type').set('value', selectedType);

								instance.byId('layoutPrototypeId').set('value', selectedPrototypeId);
							}

							instance._toggleLayoutTypeFields();
						}
					},

					_toggleLayoutTypeFields: function() {
						var instance = this;

						var nodes = instance.get(STR_NODES);

						nodes.each(
							function(item, index, collection) {
								var header = item.one(SELECTOR_TOGGLER_HEADER);

								var active = header.hasClass(CSS_ACTIVE);

								var disabled = !active;

								item.all('input, select, textarea').set('disabled', disabled);
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
		requires: ['aui-parse-content', 'liferay-dockbar', 'liferay-dockbar-add-base', 'liferay-dockbar-add-page-search']
	}
);