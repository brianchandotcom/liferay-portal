package _package_.application.list;

import _package_.constants._CLASS_PanelCategoryKeys;
import _package_.constants._CLASS_PortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + _CLASS_PanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class _CLASS_PanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return _CLASS_PortletKeys._CLASS_;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + _CLASS_PortletKeys._CLASS_ + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}