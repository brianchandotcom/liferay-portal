Liferay.namespace('PortletSharing');

Liferay.provide(
	Liferay.PortletSharing,
	'showNetvibesInfo',
	function(netvibesURL) {
		var A = AUI();

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter('netvibesURL', netvibesURL);

		var dialog = Liferay.Util.Window.getWindow(
			{
				dialog: {
					destroyOnHide: true
				},
				title: Liferay.Language.get('add-to-netvibes')
			}
		);

		dialog.plug(
			A.Plugin.IO,
			{
				uri: portletURL.toString()
			}
		);
	},
	['aui-io-plugin-deprecated', 'liferay-portlet-url', 'liferay-util-window']
);

Liferay.provide(
	Liferay.PortletSharing,
	'showWidgetInfo',
	function(widgetURL) {
		var A = AUI();

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter('widgetURL', widgetURL);

		var dialog = Liferay.Util.Window.getWindow(
			{
				dialog: {
					destroyOnHide: true
				},
				title: Liferay.Language.get('add-to-any-website')
			}
		);

		dialog.plug(
			A.Plugin.IO,
			{
				uri: portletURL.toString()
			}
		);
	},
	['aui-io-plugin-deprecated', 'liferay-portlet-url', 'liferay-util-window']
);