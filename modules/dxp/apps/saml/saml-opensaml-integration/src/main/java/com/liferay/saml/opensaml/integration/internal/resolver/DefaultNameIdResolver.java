/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = "service.ranking:Integer=" + Integer.MIN_VALUE,
	service = NameIdResolver.class
)
public class DefaultNameIdResolver implements NameIdResolver {

	@Override
	public String resolve(
		User user, String entityId, String format, String spQualifierName,
		boolean allowCreate,
		NameIdResolverSAMLContext nameIdResolverSAMLContext) {

		return _getNameIdValue(user, entityId);
	}

	private String _getNameIdAttributeName(String entityId) {
		return _metadataManager.getNameIdAttribute(entityId);
	}

	private String _getNameIdValue(User user, String entityId) {
		String nameIdAttributeName = _getNameIdAttributeName(entityId);

		if (Validator.isNull(nameIdAttributeName)) {
			return user.getEmailAddress();
		}

		if (nameIdAttributeName.startsWith("expando:")) {
			String attributeName = nameIdAttributeName.substring(8);

			ExpandoBridge expandoBridge = user.getExpandoBridge();

			return _toString(expandoBridge.getAttribute(attributeName));
		}

		if (nameIdAttributeName.startsWith("static:")) {
			return nameIdAttributeName.substring(7);
		}

		return _toString(_beanProperties.getObject(user, nameIdAttributeName));
	}

	private String _toString(Object object) {
		if (object == null) {
			return StringPool.BLANK;
		}

		return object.toString();
	}

	@Reference
	private BeanProperties _beanProperties;

	@Reference
	private MetadataManager _metadataManager;

}