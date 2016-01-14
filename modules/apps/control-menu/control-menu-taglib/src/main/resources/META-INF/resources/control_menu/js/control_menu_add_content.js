AUI.add(
	'liferay-control-menu-add-content',
	function(A) {
		var ControlMenu = Liferay.ControlMenu;

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var STR_CLICK = 'click';

		var STR_RESPONSE_DATA = 'responseData';

		var AddContent = A.Component.create(
			{
				AUGMENTS: [ControlMenu.AddContentSearch, Liferay.PortletBase],

				EXTENDS: ControlMenu.AddBase,

				NAME: 'addcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;
						instance._delta = config.delta;
						instance._displayStyle = config.displayStyle;

						instance._addContentForm = instance.byId('addContentForm');
						instance._entriesPanel = instance.byId('entriesContainer');

						instance._entriesPanel.plug(A.Plugin.ParseContent);

						instance._bindUI();
					},

					_afterSuccess: function(event) {
						var instance = this;

						instance._entriesPanel.setContent(event.currentTarget.get(STR_RESPONSE_DATA));
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._entriesPanel.delegate(STR_CLICK, instance._addContent, SELECTOR_ADD_CONTENT_ITEM, instance),
							Liferay.on('AddContent:refreshContentList', instance._refreshContentList, instance),
							Liferay.on('showTab', instance._onShowTab, instance),
							Liferay.on(
								'AddContent:addPortlet',
								function(event) {
									instance.addPortlet(event.node, event.options);
								}
							)
						);
					},

					_onShowTab: function(event) {
						var instance = this;

						if (event.namespace.indexOf(instance.get('namespace')) === 0) {
							var index = event.selectedIndex;

							Liferay.Store('com.liferay.control.menu.web_addPanelTab', event.names[index]);
						}
					},

					_refreshContentList: function(event) {
						var instance = this;

						var delta = event.delta;

						if (delta) {
							instance._delta = delta;

							Liferay.Store('com.liferay.control.menu.web_addPanelNumItems', delta);
						}

						var displayStyle = event.displayStyle;

						if (displayStyle) {
							instance._displayStyle = displayStyle;

							Liferay.Store('com.liferay.control.menu.web_addPanelNumItems', displayStyle);
						}

						A.io.request(
							instance._addContentForm.getAttribute('action'),
							{
								after: {
									success: A.bind('_afterSuccess', instance)
								},
								data: instance.ns(
									{
										delta: instance._delta,
										displayStyle: instance._displayStyle,
										keywords: instance.get('inputNode').val()
									}
								)
							}
						);
					}
				}
			}
		);

		ControlMenu.AddContent = AddContent;
	},
	'',
	{
		requires: ['aui-parse-content', 'aui-io-request', 'liferay-control-menu', 'liferay-control-menu-add-base', 'liferay-control-menu-add-content-search']
	}
);