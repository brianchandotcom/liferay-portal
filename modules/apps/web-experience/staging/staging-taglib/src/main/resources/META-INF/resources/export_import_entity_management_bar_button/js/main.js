AUI.add(
	'liferay-export-import-management-bar-button',
	function(A) {
		var Lang = A.Lang;

		var ExportImportManagementBarButton = A.Component.create(
			{
				ATTRS: {
					actionNamespace : {
						validator: Lang.isString()
					},

					cmd : {
						validator: Lang.isString()
					},

					exportImportEntityUrl : {
						validator: Lang.isString()
					},

					searchContainerId: {
						validator: Lang.isString
					},

					searchContainerMappingId: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'exportImportManagementBarButton',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var namespace = instance.NS;

						var searchContainer = Liferay.SearchContainer.get(namespace + instance.get('searchContainerId'));

						instance._searchContainer = searchContainer;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							Liferay.on(instance.ns(instance.get('cmd')), instance._exportImportEntity, instance)
						];
					},

					_exportImportEntity: function() {
						var instance = this;

						var searchContainer = instance._searchContainer.plug(A.Plugin.SearchContainerSelect);

						var selectedRows = searchContainer.select.getAllSelectedElements();

						var namespace = instance.NS;

						var searchContainerMapping = A.one('#' + namespace + instance.get('searchContainerMappingId'));

						var form = $("#hrefFm");
						form.attr("method", "POST");

						selectedRows._nodes.forEach(
							function(selectedElement) {
								var node = searchContainerMapping.one('div[data-rowpk=' + selectedElement.value + ']');

								var input = $("<input>")
									.attr("type", "hidden")
									.attr("name", instance.get('actionNamespace') + "exportingEntities")
									.val(node.attr('data-classNameId') + '#' + node.attr('data-groupId') + "#" + node.attr('data-uuid'));

								form.append(input);
							});

						submitForm(form, instance.get('exportImportEntityUrl'));
					}
				}
			}
		);

		Liferay.ExportImportManagementBarButton = ExportImportManagementBarButton;
	},
	'',
	{
		requires: ['aui-component', 'liferay-search-container', 'liferay-search-container-select']
	}
);