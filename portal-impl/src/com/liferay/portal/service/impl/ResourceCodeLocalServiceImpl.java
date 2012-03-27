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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.service.base.ResourceCodeLocalServiceBaseImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceCodeLocalServiceImpl
	extends ResourceCodeLocalServiceBaseImpl {

	public ResourceCode addResourceCode(long companyId, String name, int scope)
		throws SystemException {

		long codeId = counterLocalService.increment(
			ResourceCode.class.getName());

		ResourceCode resourceCode = resourceCodePersistence.create(codeId);

		resourceCode.setCompanyId(companyId);
		resourceCode.setName(name);
		resourceCode.setScope(scope);

		try {
			resourceCodePersistence.update(resourceCode, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {companyId=" + companyId + ", name=" +
						name + ", scope=" + scope + "}");
			}

			resourceCode = resourceCodePersistence.fetchByC_N_S(
				companyId, name, scope, false);

			if (resourceCode == null) {
				throw se;
			}
		}

		return resourceCode;
	}

	public ResourceCode getResourceCode(long companyId, String name, int scope)
		throws SystemException {

		// Always cache the resource code. This table exists to improve
		// performance. Create the resource code if one does not exist.

		if (Validator.isNull(name)) {
			name = StringPool.BLANK;
		}

		String key = encodeKey(companyId, name, scope);

		ResourceCode resourceCode = _resourceCodes.get(key);

		if (resourceCode == null) {
			resourceCode = resourceCodePersistence.fetchByC_N_S(
				companyId, name, scope);

			if (resourceCode == null) {
				resourceCode = resourceCodeLocalService.addResourceCode(
					companyId, name, scope);
			}

			_resourceCodes.put(key, resourceCode);
		}

		return resourceCode;
	}

	protected String encodeKey(long companyId, String name, int scope) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringUtil.toHexString(companyId));
		sb.append(StringPool.POUND);
		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(scope));

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(
		ResourceCodeLocalServiceImpl.class);

	private static Map<String, ResourceCode> _resourceCodes =
		new ConcurrentHashMap<String, ResourceCode>();

}