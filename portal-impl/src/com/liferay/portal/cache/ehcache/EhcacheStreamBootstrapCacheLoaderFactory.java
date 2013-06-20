package com.liferay.portal.cache.ehcache;

import java.util.Properties;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;
public class EhcacheStreamBootstrapCacheLoaderFactory
	extends BootstrapCacheLoaderFactory {

	@Override
	public BootstrapCacheLoader createBootstrapCacheLoader(
		Properties properties) {

		return _bootstrapCacheLoader;
	}

	private static BootstrapCacheLoader _bootstrapCacheLoader =
		new EhcacheStreamBootstrapCacheLoader();

}