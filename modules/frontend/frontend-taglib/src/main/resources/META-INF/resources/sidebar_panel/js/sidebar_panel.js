AUI.add(
	'liferay-sidebar-panel',
	function(A) {
		var Lang = A.Lang;

		var SidebarPanel = A.Component.create(
			{
				ATTRS: {
					resourceUrl: {
						validator: Lang.isString
					},

					searchContainerId: {
						validator: Lang.isString
					},

					targetNode: {
						setter: A.one
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferaysidebarpanel',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._searchContainerRegisterHandle = Liferay.on('search-container:registered', instance._onSearchContainerRegistered, instance);
					},

					destructor: function() {
						var instance = this;

						instance._detachSearchContainerRegisterHandle();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance._searchContainer.on('rowToggled', instance._onRowToggled, instance)
						];
					},

					_detachSearchContainerRegisterHandle: function() {
						var instance = this;

						var searchContainerRegisterHandle = instance._searchContainerRegisterHandle;

						if (searchContainerRegisterHandle) {
							searchContainerRegisterHandle.detach();

							instance._searchContainerRegisterHandle = null;
						}
					},

					_onRowToggled: function(event) {
						var instance = this;

						A.io.request(
							instance.get('resourceUrl'),
							{
								form: instance._searchContainer.getForm().getDOM(),
								on: {
									success: function(event, id, xhr) {
										var response = xhr.responseText;

										instance.get('targetNode').setContent(response);
									}
								}
							}
						);
					},

					_onSearchContainerRegistered: function(event) {
						var instance = this;

						var searchContainer = event.searchContainer;

						if (searchContainer.get('id') === instance.get('searchContainerId')) {
							instance._searchContainer = searchContainer;

							instance._detachSearchContainerRegisterHandle();

							instance._bindUI();
						}
					}
				}
			}
		);

		Liferay.SidebarPanel = SidebarPanel;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'liferay-portlet-base']
	}
);