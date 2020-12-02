package ${packagePath}.service.persistence.impl.constants;

import com.liferay.petra.string.StringBundler;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 * @generated
 */
@Component(immediate = true, service = {})
public class ${portletShortName}PersistenceConstants {

	public static final String BUNDLE_SYMBOLIC_NAME = "${apiPackagePath}.service";

	public static final String ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER = "(origin.bundle.symbolic.name=" + BUNDLE_SYMBOLIC_NAME + ")";

	public static final String SERVICE_CONFIGURATION_FILTER = "(&" + ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER + "(name=service))";

	@Activate
	protected void activate(BundleContext bundleContext) {
		Bundle bundle = bundleContext.getBundle();

		if (!BUNDLE_SYMBOLIC_NAME.equals(bundle.getSymbolicName())) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Incorrect ", Constants.BUNDLE_SYMBOLICNAME, " for bundle ",
					bundle.getSymbolicName()));
		}
	}

}