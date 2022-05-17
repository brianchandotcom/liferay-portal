import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;

import org.osgi.framework.BundleContext;

BundleContext bundleContext = SystemBundleUtil.getBundleContext();

def ldapUserImporter = bundleContext.getService(bundleContext.getServiceReference(LDAPUserImporter.class));

ldapUserImporter.importUsers();