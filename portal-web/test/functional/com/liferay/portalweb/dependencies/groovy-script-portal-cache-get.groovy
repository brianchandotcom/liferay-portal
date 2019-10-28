import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import java.util.List;


PortalCache<String, String> testCache = PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.MULTI_VM, "test.cache");

List<String> keys = testCache.getKeys();

if (keys.isEmpty()) {
	out.println("test.cache is empty");

	return;
}

for (String key : keys) {
	out.println(key  + "=" + testCache.get(key));
}