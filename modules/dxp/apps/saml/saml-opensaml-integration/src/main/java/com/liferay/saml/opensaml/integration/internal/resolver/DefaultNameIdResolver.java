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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
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
	immediate = true, property = "service.ranking:Integer=" + Integer.MIN_VALUE,
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

	@Reference(unbind = "-")
	public void setMetadataManager(MetadataManager metadataManager) {
		_metadataManager = metadataManager;
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

		return _toString(
			BeanPropertiesUtil.getObject(user, nameIdAttributeName));
	}

	private String _toString(Object object) {
		if (object == null) {
			return StringPool.BLANK;
		}

		return object.toString();
	}

	private MetadataManager _metadataManager;

}