/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.url.builder.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.url.builder.facet.BuildableAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.CDNAwareAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.CacheAwareAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.facet.PathProxyAwareAbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.internal.util.CacheHelper;
import com.liferay.portal.url.builder.internal.util.URLUtil;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class BundleResourceAbsolutePortalURLBuilderImplBase<T>
	implements BuildableAbsolutePortalURLBuilder,
			   CacheAwareAbsolutePortalURLBuilder<T>,
			   CDNAwareAbsolutePortalURLBuilder<T>,
			   PathProxyAwareAbsolutePortalURLBuilder<T> {

	public BundleResourceAbsolutePortalURLBuilderImplBase(
		Bundle bundle, CacheHelper cacheHelper, String cdnHost,
		String pathModule, String pathProxy, String relativeURL) {

		if (!relativeURL.startsWith(StringPool.SLASH)) {
			relativeURL = StringPool.SLASH + relativeURL;
		}

		_bundle = bundle;
		_cacheHelper = cacheHelper;
		_cdnHost = cdnHost;
		_pathProxy = pathProxy;
		_relativeURL = relativeURL;

		_bundlePathPrefix = URLUtil.getBundlePathPrefix(bundle, pathModule);
	}

	@Override
	public String build() {
		StringBundler sb = new StringBundler();

		URLUtil.appendURL(
			sb, _cdnHost, _ignoreCDNHost, _ignorePathProxy, _bundlePathPrefix,
			_pathProxy, _relativeURL);

		sb.append(StringPool.QUESTION);

		_cacheHelper.appendCacheParam(sb, _bundle, _cachePolicy, _relativeURL);

		return sb.toString();
	}

	@Override
	public T cache(CachePolicy cachePolicy) {
		_cachePolicy = cachePolicy;

		return (T)this;
	}

	@Override
	public T ignoreCDNHost() {
		_ignoreCDNHost = true;

		return (T)this;
	}

	@Override
	public T ignorePathProxy() {
		_ignorePathProxy = true;

		return (T)this;
	}

	private final Bundle _bundle;
	private final String _bundlePathPrefix;
	private final CacheHelper _cacheHelper;
	private CachePolicy _cachePolicy = CachePolicy.UNTIL_CHANGED;
	private final String _cdnHost;
	private boolean _ignoreCDNHost;
	private boolean _ignorePathProxy;
	private final String _pathProxy;
	private final String _relativeURL;

}