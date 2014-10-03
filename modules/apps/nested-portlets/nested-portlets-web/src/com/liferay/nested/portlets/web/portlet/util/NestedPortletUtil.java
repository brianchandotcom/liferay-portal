package com.liferay.nested.portlets.web.portlet.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.RenderRequestImpl;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Peter Fellwock
 */
public class NestedPortletUtil {

	public static void defineObjects(
		PortletConfig portletConfig, RenderResponse renderResponse,
		RenderRequest renderRequest) {

		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)renderRequest;

		renderRequestImpl.defineObjects(portletConfig, renderResponse);
	}

	public static String getLayoutTemplateId(PortletPreferences preferences) {
		String layoutTemplateIdDefault = preferences.getValue(
			"nested.portlets.layout.template.default", StringPool.BLANK);

		String LayoutTemplateId = preferences.getValue(
			"layoutTemplateId", layoutTemplateIdDefault);

		return LayoutTemplateId;
	}

}