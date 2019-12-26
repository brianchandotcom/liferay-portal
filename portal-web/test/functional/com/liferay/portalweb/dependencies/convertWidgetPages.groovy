import com.liferay.layout.util.BulkLayoutConverter
import com.liferay.portal.kernel.util.ArrayUtil
import com.liferay.registry.Registry
import com.liferay.registry.RegistryUtil
import org.osgi.framework.ServiceReference
import org.osgi.framework.BundleContext

companyId = com.liferay.portal.kernel.util.PortalUtil.getCompanyId(actionRequest)
group = com.liferay.portal.kernel.service.GroupLocalServiceUtil.getGroup(companyId, "Test Site Name");
groupId = group.getGroupId();

Registry registry = RegistryUtil.getRegistry()

BundleContext bundleContext = registry._bundleContext

ServiceReference serviceReference = bundleContext.getServiceReference(BulkLayoutConverter.class.getName())

BulkLayoutConverter bulkLayoutConverter = bundleContext.getService(serviceReference);


long[] plids = bulkLayoutConverter.getConvertibleLayoutPlids(groupId)

out.println("Convertible layouts before conversion:" + ArrayUtil.toStringArray(plids))

long[] convertedLayoutPlids = bulkLayoutConverter.convertLayouts(groupId)

out.println("Converted layouts:" + ArrayUtil.toStringArray(convertedLayoutPlids))

plids = bulkLayoutConverter.getConvertibleLayoutPlids(groupId)

out.println("Convertible layouts after conversion: " + ArrayUtil.toStringArray(plids))
