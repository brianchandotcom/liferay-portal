/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class AttributesTransformerFactory {

	public static AttributesTransformer getInstance() {
		return _attributesTransformer;
	}

	public static void setInstance(
		AttributesTransformer attributesTransformer) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(attributesTransformer));
		}

		if (attributesTransformer == null) {
			_attributesTransformer = _originalAttributesTransformer;
		}
		else {
			_attributesTransformer = attributesTransformer;
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " + PropsValues.LDAP_ATTRS_TRANSFORMER_IMPL);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalAttributesTransformer =
			(AttributesTransformer)InstanceFactory.newInstance(
				classLoader, PropsValues.LDAP_ATTRS_TRANSFORMER_IMPL);

		_attributesTransformer = _originalAttributesTransformer;
	}

	private static Log _log = LogFactoryUtil.getLog(
		AttributesTransformerFactory.class);

	private static volatile AttributesTransformer _attributesTransformer;
	private static AttributesTransformer _originalAttributesTransformer;

}