AUI.add(
	'liferay-ddm-form-renderer-tabs',
	function(A) {
		var FormTabsSupport = function() {
		};

		FormTabsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance.after('render', instance._afterTabsRender);
			},

			getTabView: function() {
				var instance = this;

				if (!instance.tabView) {
					var container = instance.get('container');

					instance.tabView = new A.TabView(
						{
							srcNode: container.one('.lfr-ddm-form-tabs')
						}
					);
				}

				return instance.tabView;
			},

			_afterTabsRender: function() {
				var instance = this;

				var tabView = instance.getTabView();

				tabView.render();
			}
		};

		Liferay.namespace('DDM.Renderer').FormTabsSupport = FormTabsSupport;
	},
	'',
	{
		requires: ['aui-tabview']
	}
);