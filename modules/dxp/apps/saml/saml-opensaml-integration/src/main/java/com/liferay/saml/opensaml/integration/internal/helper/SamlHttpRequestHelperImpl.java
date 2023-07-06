/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.helper.SamlHttpRequestHelper;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.runtime.SamlException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Santos
 */
@Component(service = SamlHttpRequestHelper.class)
public class SamlHttpRequestHelperImpl implements SamlHttpRequestHelper {

	@Override
	public String getEntityDescriptorString(
			HttpServletRequest httpServletRequest)
		throws SamlException {

		try {
			return OpenSamlUtil.marshall(
				_metadataManager.getEntityDescriptor(httpServletRequest));
		}
		catch (Exception exception) {
			throw new SamlException(exception);
		}
	}

	@Override
	public String getRequestPath(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		String contextPath = httpServletRequest.getContextPath();

		if (Validator.isNotNull(contextPath) &&
			!contextPath.equals(StringPool.SLASH)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		return HttpComponentsUtil.removePathParameters(requestURI);
	}

	@Reference
	private MetadataManager _metadataManager;

}