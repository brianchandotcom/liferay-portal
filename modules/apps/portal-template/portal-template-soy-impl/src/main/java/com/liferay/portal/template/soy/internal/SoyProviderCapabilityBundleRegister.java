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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.template.soy.internal.util.SoyTemplateUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(service = SoyProviderCapabilityBundleRegister.class)
public class SoyProviderCapabilityBundleRegister {

	public static Bundle getTemplateBundle(String templateId) {
		Bundle bundle = _bundles.get(SoyTemplateUtil.getBundleId(templateId));

		if (bundle == null) {
			Collection<Bundle> bundles = _bundles.values();

			StringBundler sb = new StringBundler(bundles.size() * 2);

			for (Bundle registredBundle : _bundles.values()) {
				sb.append(registredBundle.getSymbolicName());
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			_log.error(String.format("Registred bundles %s", sb.toString()));

			throw new IllegalStateException(
				"There are no bundles providing " + templateId);
		}

		return bundle;
	}

	public void register(Bundle bundle) {
		_bundles.put(bundle.getBundleId(), bundle);
	}

	public void unregister(Bundle bundle) {
		_bundles.remove(bundle.getBundleId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyProviderCapabilityBundleRegister.class);

	private static final Map<Long, Bundle> _bundles = new ConcurrentHashMap<>();

}