AUI.add(
	'liferay-dockbar-add-application',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Layout = Liferay.Layout;

		var CSS_LFR_PORTLET_USED = 'lfr-portlet-used';

		var DATA_PORTLET_ID = 'data-portlet-id';

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var SELECTOR_BUTTON = '.btn';

		var SELECTOR_CONTENT_ITEM = '.lfr-content-item';

		var STR_ACTION = 'action';

		var STR_CLICK = 'click';

		var STR_ENTER_DOWN = 'down:ENTER';

		var STR_KEY = 'key';

		var STR_RESPONSE_DATA = 'responseData';

		var AddApplication = A.Component.create(
			{
				AUGMENTS: [Dockbar.AddApplicationSearch, Liferay.PortletBase],

				EXTENDS: Dockbar.AddBase,

				NAME: 'addapplication',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addApplicationForm = instance.byId('addApplicationForm');
						instance._entriesPanel = instance.byId('applicationList');

						var togglerDelegate = Liferay.component(instance.ns('addApplicationPanelContainer'));

						if (togglerDelegate) {
							togglerDelegate.plug(
								Liferay.TogglerInteraction,
								{
									children: '.lfr-content-item',
									parents: '.lfr-content-category'
								}
							);
						}

						instance._bindUI();
					},

					_addApplication: function(event) {
						var instance = this;

						var portlet = event.currentTarget;

						if (event.type === STR_KEY) {
							portlet = portlet.one(SELECTOR_ADD_CONTENT_ITEM);
						}

						instance.addPortlet(portlet);
					},

					_bindUI: function() {
						var instance = this;

						instance._entriesPanel.delegate(STR_CLICK, instance._addApplication, SELECTOR_ADD_CONTENT_ITEM, instance);

						instance._entriesPanel.delegate(STR_KEY, instance._addApplication, STR_ENTER_DOWN, SELECTOR_CONTENT_ITEM, instance);

						Liferay.on('closePortlet', instance._onPortletClose, instance);
					},

					_onPortletClose: function(event) {
						var instance = this;

						var item = instance._entriesPanel.one('.drag-content-item[data-plid=' + event.plid + '][data-portlet-id=' + event.portletId + '][data-instanceable=false]');

						if (item && item.hasClass(CSS_LFR_PORTLET_USED)) {
							var portletId = item.attr(DATA_PORTLET_ID);

							instance._enablePortletEntry(portletId);
						}
					}
				}
			}
		);

		Dockbar.AddApplication = AddApplication;
	},
	'',
	{
		requires: ['key-event', 'liferay-dockbar', 'liferay-dockbar-add-base', 'liferay-dockbar-add-application-search', 'liferay-toggler-interaction']
	}
);