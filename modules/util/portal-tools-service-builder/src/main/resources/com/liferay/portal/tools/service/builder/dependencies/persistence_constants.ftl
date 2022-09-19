package ${packagePath}.service.persistence.impl.constants;

import com.liferay.petra.string.StringBundler;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * @author ${author}
 * @generated
 */
public class ${portletShortName}PersistenceConstants {

	public static final String BUNDLE_SYMBOLIC_NAME = "${apiPackagePath}.service";

	public static final String ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER = "(origin.bundle.symbolic.name=" + BUNDLE_SYMBOLIC_NAME + ")";

	public static final String SERVICE_CONFIGURATION_FILTER = "(&" + ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER + "(name=service))";

}