import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import java.util.List;


PortalCache<String, String> testCache = MultiVMPoolUtil.getPortalCache("test.cache");

List<String> keys = testCache.getKeys();

if (keys.isEmpty()) {
	out.println("test.cache is empty");

	return;
}

for (String key : keys) {
	out.println(key  + "=" + testCache.get(key));
}