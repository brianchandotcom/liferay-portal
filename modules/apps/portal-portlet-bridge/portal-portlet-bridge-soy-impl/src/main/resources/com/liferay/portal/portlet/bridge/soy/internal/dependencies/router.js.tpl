window.portletRouter = new SoyPortletRouter.default(
	{
		context: $CONTEXT,
		element: '#$ELEMENT_ID',
		friendlyURLMapping: '$FRIENDLY_URL_MAPPING',
		friendlyURLPrefix: $FRIENDLY_URL_PREFIX,
		friendlyURLRoutes: $FRIENDLY_URL_ROUTES,
		mvcRenderCommandNames: $MVC_RENDER_COMMAND_NAMES,
		portletId: '$PORTLET_ID',
		portletNamespace: '$PORTLET_NAMESPACE',
		portletWrapper: '#$PORTLET_WRAPPER_ID'
	}
);

Liferay.fire('soyPortletReady', { portletNamespace: '$PORTLET_NAMESPACE' });