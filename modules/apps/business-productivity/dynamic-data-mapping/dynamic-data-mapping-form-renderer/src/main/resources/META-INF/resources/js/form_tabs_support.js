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

				var tabView = instance.getTabView();

				if (tabView) {
					tabView.destroy();
				}
			},

			getTabView: function() {
				var instance = this;

				var tabView = instance._tabView;

				if (!tabView) {
					var tabs = instance.get('container').one('.lfr-ddm-form-tabs');

					if (tabs) {
						tabView = new A.TabView(
							{
								srcNode: tabs
							}
						);

						instance._tabView = tabView;
					}
				}

				return tabView;
			},

			_afterTabsRender: function() {
				var instance = this;

				var tabView = instance.getTabView();

				if (tabView) {
					tabView.render();
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