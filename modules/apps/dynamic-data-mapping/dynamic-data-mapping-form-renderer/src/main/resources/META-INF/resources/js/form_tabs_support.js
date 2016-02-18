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

			destructor: function() {
				var instance = this;

				var tabView = instance.tabView;

				if (tabView) {
					tabView.destroy();
				}
			},

			_afterTabsRender: function() {
				var instance = this;

				var container = instance.get('container');

				var tabs = container.one('.lfr-ddm-form-tabs');

				if (!instance.tabView && tabs) {
					instance.tabView = new A.TabView(
						{
							srcNode: tabs
						}
					).render();
				}
			}
		};

		Liferay.namespace('DDM.Renderer').FormTabsSupport = FormTabsSupport;
	},
	'',
	{
		requires: ['aui-tabview']
	}
);