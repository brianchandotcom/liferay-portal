Liferay.namespace('PortletSharing');

Liferay.provide(
	Liferay.PortletSharing,
	'showNetvibesInfo',
	function(netvibesURL, basePortletURL) {
		var A = AUI();

		var portletURL = Liferay.PortletURL.createResourceURL();

		if (basePortletURL) {
			portletURL = Liferay.PortletURL.createURL(basePortletURL);
		}

		portletURL.setPortletId(Liferay.PortletKeys.PORTLET_SHARING);

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
	function(widgetURL, basePortletURL) {
		var A = AUI();

		var portletURL = Liferay.PortletURL.createResourceURL();

		if (basePortletURL) {
			portletURL = Liferay.PortletURL.createURL(basePortletURL);
		}

		portletURL.setPortletId(Liferay.PortletKeys.PORTLET_SHARING);

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