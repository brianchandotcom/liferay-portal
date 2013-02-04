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
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
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
			attributesTransformer = _createAttributesTransformer();
		}

		_attributesTransformer = attributesTransformer;
	}

	public void afterPropertiesSet() {
		_attributesTransformer = _createAttributesTransformer();
	}

	private static AttributesTransformer _createAttributesTransformer() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " + PropsValues.LDAP_ATTRS_TRANSFORMER_IMPL);
		}

		AttributesTransformer attributesTransformer = null;

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		try {
			attributesTransformer =
				(AttributesTransformer)InstanceFactory.newInstance(
					classLoader, PropsValues.LDAP_ATTRS_TRANSFORMER_IMPL);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return attributesTransformer;
	}

	private static Log _log = LogFactoryUtil.getLog(
		AttributesTransformerFactory.class);

	private static volatile AttributesTransformer _attributesTransformer;

}