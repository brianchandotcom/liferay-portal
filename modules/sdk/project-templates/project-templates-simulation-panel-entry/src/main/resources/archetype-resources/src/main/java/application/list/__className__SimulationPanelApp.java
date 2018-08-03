package ${package}.application.list;

import com.liferay.application.list.BaseJSPPanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.product.navigation.simulation.application.list.SimulationPanelCategory;
import com.liferay.product.navigation.simulation.constants.ProductNavigationSimulationPortletKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=500",
		"panel.category.key=" + SimulationPanelCategory.SIMULATION
	},
	service = PanelApp.class
)
public class ${className}SimulationPanelApp extends BaseJSPPanelApp {

	@Override
	public String getJspPath() {
		return "/simulation_panel.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale.getLanguage());

		return LanguageUtil.get(resourceBundle, "simulation-sample");
	}

	@Override
	public String getPortletId() {
		return ProductNavigationSimulationPortletKeys.
			PRODUCT_NAVIGATION_SIMULATION;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (group.isControlPanel()) {
			return false;
		}

		return true;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + ProductNavigationSimulationPortletKeys.PRODUCT_NAVIGATION_SIMULATION + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=${artifactId})",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=${package})"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;


}