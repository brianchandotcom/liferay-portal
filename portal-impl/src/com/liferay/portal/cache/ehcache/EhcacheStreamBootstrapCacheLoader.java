package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
public class EhcacheStreamBootstrapCacheLoader implements BootstrapCacheLoader {

	public static void resetSkip() {
		_skipThreadLocal.remove();
	}

	public static void setSkip() {
		_skipThreadLocal.set(Boolean.TRUE);
	}

	@Override
	public Object clone() {
		return this;
	}

	@Override
	public boolean isAsynchronous() {
		return false;
	}

	@Override
	public void load(Ehcache cache) throws CacheException {
		if (_skipThreadLocal.get()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Bootstraping " + cache.getName());
		}

		try {
			EhcacheStreamBootstrapHelpUtil.acquireCachePeers(cache);
		}
		catch (Exception e) {
			throw new CacheException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapCacheLoader.class); private static ThreadLocal<Boolean> _skipThreadLocal =
		new InitialThreadLocal<Boolean>(
			EhcacheStreamBootstrapCacheLoader.class + "._skipThreadLocal",
			false);

}