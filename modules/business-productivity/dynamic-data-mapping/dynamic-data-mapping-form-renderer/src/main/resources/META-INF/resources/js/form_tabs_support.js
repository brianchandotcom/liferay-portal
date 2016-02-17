AUI.add(
	'liferay-ddm-form-renderer-tabs',
	function(A) {
		var FormTabsSupport = function() {
		};

		FormTabsSupport.prototype = {
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
			}
		};

		Liferay.namespace('DDM.Renderer').FormTabsSupport = FormTabsSupport;
	},
	'',
	{
		requires: ['aui-tabview']
	}
);