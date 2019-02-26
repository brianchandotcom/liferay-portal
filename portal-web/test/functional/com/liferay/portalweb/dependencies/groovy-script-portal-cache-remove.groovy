import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;


PortalCache<String, String> testCache = MultiVMPoolUtil.getPortalCache("test.cache");

testCache.remove("test.key");