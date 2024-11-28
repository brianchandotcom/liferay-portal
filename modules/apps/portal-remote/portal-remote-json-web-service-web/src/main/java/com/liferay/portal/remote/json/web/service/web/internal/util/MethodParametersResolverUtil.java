/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodParameter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Igor Spasic
 */
public class MethodParametersResolverUtil {

	public static MethodParameter[] resolveMethodParameters(Method method) {
		MethodParameter[] methodParameters = _methodParameters.get(method);

		if (methodParameters != null) {
			return methodParameters;
		}

		try {
			List<MethodParameter> extractedParameterList =
				MethodParameterExtractor.getMethodParameters(
					method.getDeclaringClass(), method);

			methodParameters =
				new MethodParameter[extractedParameterList.size()];

			_methodParameters.put(
				method, extractedParameterList.toArray(methodParameters));
		}
		catch (PortalException portalException) {
			_log.error("Error extracting parameters ", portalException);
		}

		return methodParameters;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MethodParametersResolverUtil.class);

	private static final ConcurrentMap<AccessibleObject, MethodParameter[]>
		_methodParameters = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}