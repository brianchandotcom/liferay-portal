package com.liferay.portal.company.log.web.application.list;

import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.company.log.web.constants.PortalCompanyLogPanelCategoryKeys;
import com.liferay.portal.company.log.web.constants.PortalCompanyLogPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Keven Leone
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_SECURITY
	},
	service = PanelApp.class
)
public class PortalCompanyLogPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return PortalCompanyLogPortletKeys.PORTAL_COMPANY_LOG;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + PortalCompanyLogPortletKeys.PORTAL_COMPANY_LOG + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}