package ${package}.application.list;

import ${package}.constants.${className}PanelCategoryKeys;
import ${package}.constants.${className}PortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 */
@Component(
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + ${className}PanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class ${className}PanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return ${className}PortletKeys.${className.toUpperCase()};
	}

#if (${liferayVersion.startsWith("7.4")})
	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Reference(target = "(javax.portlet.name=" + ${className}PortletKeys.${className.toUpperCase()} + ")")
	private Portlet _portlet;
#else
	@Override
	@Reference(
		target = "(javax.portlet.name=" + ${className}PortletKeys.${className.toUpperCase()} + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}
#end

}