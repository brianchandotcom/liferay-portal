AUI.add(
	'liferay-dockbar-add-application',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Layout = Liferay.Layout;
		var Portlet = Liferay.Portlet;

		var CSS_LFR_PORTLET_USED = 'lfr-portlet-used';

		var DATA_CLASS_NAME = 'data-class-name';

		var DATA_CLASS_PK = 'data-class-pk';

		var DATA_PORTLET_ID = 'data-portlet-id';

		var DATA_STYLE = 'data-style';

		var SELECTOR_BUTTON = '.btn';

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var STR_ACTION = 'action';

		var STR_CLICK = 'click';

		var STR_EMPTY = '';

		var STR_RESPONSE_DATA = 'responseData';

		var TPL_LOADING = '<div class="loading-animation" />';

		var AddApplication = A.Component.create(
			{
				AUGMENTS: [Dockbar.AddContentDragDrop, Dockbar.AddApplicationSearch, Liferay.PortletBase],

				EXTENDS: Dockbar.AddBase,

				NAME: 'addapplicationsearch',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addApplicationForm = instance.byId('addApplicationForm');
						instance._addPanelContainer = instance.byId('addPanelContainer');
						instance._closePanel = instance._addPanelContainer.one('#closePanel');
						instance._entriesContainer = instance.byId('entriesContainer');

						instance._bindUI();
					},

					_addApplication: function(event) {
						var instance = this;

						var portlet = event.currentTarget;

						instance._addPortlet(portlet);
					},

					_addPortlet: function(portlet, options) {
						var instance = this;

						var portletMetaData = instance._getPortletMetaData(portlet);

						if (!portletMetaData.portletUsed) {
							var portletId = portletMetaData.portletId;

							if (!portletMetaData.instanceable) {
								instance._disablePortletEntry(portletId);
							}

							var beforePortletLoaded = null;
							var placeHolder = A.Node.create(TPL_LOADING);

							if (options) {
								var item = options.item;

								item.placeAfter(placeHolder);
								item.remove(true);

								beforePortletLoaded = options.beforePortletLoaded;
							}
							else {
								var firstColumn = Layout.getActiveDropNodes().item(0);

								if (firstColumn) {
									var dropColumn = firstColumn.one(Layout.options.dropContainer);
									var referencePortlet = Layout.findReferencePortlet(dropColumn);

									if (referencePortlet) {
										referencePortlet.placeBefore(placeHolder);
									}
									else {
										if (dropColumn) {
											dropColumn.append(placeHolder);
										}
									}
								}
							}

							Portlet.add(
								{
									beforePortletLoaded: beforePortletLoaded,
									placeHolder: placeHolder,
									plid: portletMetaData.plid,
									portletData: portletMetaData.portletData,
									portletId: portletId,
									portletItemId: portletMetaData.portletItemId
								}
							);
						}
					},

					_afterSuccess: function(event) {
						var instance = this;

						instance._entriesContainer.setContent(event.currentTarget.get(STR_RESPONSE_DATA));
					},

					_bindUI: function() {
						var instance = this;

						instance._addPanelContainer.delegate(STR_CLICK, instance._addApplication, SELECTOR_ADD_CONTENT_ITEM, instance);

						Liferay.on('closePortlet', instance._onPortletClose, instance);

						Liferay.on('showTab', instance._onShowTab, instance);
					},

					_disablePortletEntry: function(portletId) {
						var instance = this;

						instance._eachPortletEntry(
							portletId,
							function(item, index) {
								item.addClass(CSS_LFR_PORTLET_USED);
							}
						);
					},

					_eachPortletEntry: function(portletId, callback) {
						var instance = this;

						var portlets = A.all('[data-portlet-id=' + portletId + ']');

						portlets.each(callback);
					},

					_enablePortletEntry: function(portletId) {
						var instance = this;

						instance._eachPortletEntry(
							portletId,
							function(item, index) {
								item.removeClass(CSS_LFR_PORTLET_USED);
							}
						);
					},

					_getPortletMetaData: function(portlet) {
						var instance = this;

						var portletMetaData = portlet._LFR_portletMetaData;

						if (!portletMetaData) {
							var classPK = portlet.attr(DATA_CLASS_PK);
							var className = portlet.attr(DATA_CLASS_NAME);

							var instanceable = (portlet.attr('data-instanceable') == 'true');
							var plid = portlet.attr('data-plid');

							var portletData = STR_EMPTY;

							if ((className != STR_EMPTY) && (classPK != STR_EMPTY)) {
								portletData = classPK + ',' + className;
							}

							var portletId = portlet.attr(DATA_PORTLET_ID);
							var portletItemId = portlet.attr('data-portlet-item-id');
							var portletUsed = portlet.hasClass(CSS_LFR_PORTLET_USED);

							portletMetaData = {
								instanceable: instanceable,
								plid: plid,
								portletData: portletData,
								portletId: portletId,
								portletItemId: portletItemId,
								portletUsed: portletUsed
							};

							portlet._LFR_portletMetaData = portletMetaData;
						}

						return portletMetaData;
					},

					_onShowTab: function(event) {
						var instance = this;

						if (event.namespace.indexOf(instance.get('namespace')) === 0) {
							var index = event.selectedIndex;

							Liferay.Store('liferay_addpanel_tab', event.names[index]);
						}
					},

					_onPortletClose: function(event) {
						var instance = this;

						var item = instance._addPanelContainer.one('.drag-content-item[data-plid=' + event.plid + '][data-portlet-id=' + event.portletId + '][data-instanceable=false]');

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
		requires: ['aui-io-request', 'liferay-dockbar', 'liferay-dockbar-add-base', 'liferay-dockbar-add-content-drag-drop', 'liferay-dockbar-add-application-search']
	}
);