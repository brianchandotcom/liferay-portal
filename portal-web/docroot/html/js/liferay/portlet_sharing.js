Liferay.namespace('PortletSharing');

Liferay.provide(
	Liferay.PortletSharing,
	'showNetvibesInfo',
	function(netvibesURL) {
		var A = AUI();

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter('netvibesURL', netvibesURL);

		var dialog = new A.Dialog(
			{
				align: Liferay.Util.Window.ALIGN_CENTER,
				destroyOnClose: true,
				modal: true,
				title: Liferay.Language.get('add-to-netvibes'),
				width: 550
			}
		).render();

		dialog.plug(
			A.Plugin.IO,
			{
				uri: portletURL.toString()
			}
		);
	},
	['aui-dialog', 'liferay-portlet-url']
);

Liferay.provide(
	Liferay.PortletSharing,
	'showWidgetInfo',
	function(widgetURL) {
		var A = AUI();

		var portletURL = Liferay.PortletURL.createResourceURL();

		portletURL.setPortletId(133);

		portletURL.setParameter('widgetURL', widgetURL);

		var dialog = new A.Dialog(
			{
				align: Liferay.Util.Window.ALIGN_CENTER,
				destroyOnClose: true,
				modal: true,
				title: Liferay.Language.get('add-to-any-website'),
				width: 550
			}
		).render();

		dialog.plug(
			A.Plugin.IO,
			{
				uri: portletURL.toString()
			}
		);
	},
	['aui-dialog', 'liferay-portlet-url']
);