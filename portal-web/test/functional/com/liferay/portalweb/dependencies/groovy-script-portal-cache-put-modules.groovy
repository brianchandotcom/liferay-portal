import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import java.lang.reflect.Constructor;


try {
	ClassLoader testModuleClassLoader = ClassLoaderPool.getClassLoader("com.liferay.cluster.test.module_3.0.0");

	Class<?> clazz = testModuleClassLoader.loadClass("com.liferay.cluster.test.module.internel.ClusterTestClass");

	Constructor<?> constructor = clazz.getConstructor(String.class);

	PortalCache testCache = MultiVMPoolUtil.getPortalCache("test.cache");

	testCache.put("test.key", constructor.newInstance("test.value"));
}
catch (Exception e) {
	out.println(e);
}