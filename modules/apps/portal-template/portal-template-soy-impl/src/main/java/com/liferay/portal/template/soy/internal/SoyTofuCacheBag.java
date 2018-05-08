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

package com.liferay.portal.template.soy.internal;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.msgs.SoyMsgBundle;
import com.google.template.soy.tofu.SoyTofu;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Basto
 */
public class SoyTofuCacheBag {

	public SoyTofuCacheBag(SoyFileSet soyFileSet, SoyTofu soyTofu) {
		_soyFileSet = soyFileSet;
		_soyTofu = soyTofu;
	}

	public SoyMsgBundle getMessageBundle(Locale locale) {
		return _soyMsgBundleCache.get(locale);
	}

	public SoyFileSet getSoyFileSet() {
		return _soyFileSet;
	}

	public SoyTofu getSoyTofu() {
		return _soyTofu;
	}

	public void putMessageBundle(Locale locale, SoyMsgBundle soyMsgBundle) {
		_soyMsgBundleCache.put(locale, soyMsgBundle);
	}

	private final SoyFileSet _soyFileSet;
	private final Map<Locale, SoyMsgBundle> _soyMsgBundleCache =
		new HashMap<>();
	private final SoyTofu _soyTofu;

}