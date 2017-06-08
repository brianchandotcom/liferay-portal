package com.liferay.blogs.web.internal.toolbar.contributor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.BasePortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+BlogsPortletKeys.BLOGS,
			"mvc.path=-", "mvc.path=/view.jsp"
		},
		service = {
		    BlogsPortletToolbarContributor.class,
			PortletToolbarContributor.class
		}
)
public class BlogsPortletToolbarContributor
	extends BasePortletToolbarContributor {

	@Override
	protected List<MenuItem> getPortletTitleMenuItems(PortletRequest portletRequest, PortletResponse portletResponse) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (!_resourcePermissionChecker.checkResource(
				themeDisplay.getPermissionChecker(), scopeGroupId,
				ActionKeys.ADD_ENTRY)) {

			return Collections.emptyList();
		}

		List<MenuItem> menuItems = new ArrayList<>();
		
		try {

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
			
			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				portletRequest,portletDisplay.getId(),
				PortletRequest.RENDER_PHASE);
			portletURL.setParameter(
					"mvcRenderCommandName", "/blogs/edit_entry");
			portletURL.setParameter(
				"redirect", PortalUtil.getCurrentURL(portletRequest));
				
			URLMenuItem urlMenuItem = new URLMenuItem();					
			urlMenuItem.setLabel(
					LanguageUtil.get(
						PortalUtil.getHttpServletRequest(portletRequest),
						"add-blog-entry"));
			urlMenuItem.setURL(portletURL.toString());
			
			menuItems.add(urlMenuItem);

		}
		catch (Exception e) {
			_log.error("Unable to add blog entry menu item", e);
		}

		return menuItems;

	}
		
	@Reference(target = "(resource.name=com.liferay.blogs)", unbind = "-")
	protected void setResourcePermissionChecker(
		ResourcePermissionChecker resourcePermissionChecker) {

		_resourcePermissionChecker = resourcePermissionChecker;
	}
	
	private static final Log _log = LogFactoryUtil.getLog(
			BlogsPortletToolbarContributor.class);
	
	private ResourcePermissionChecker _resourcePermissionChecker;

}