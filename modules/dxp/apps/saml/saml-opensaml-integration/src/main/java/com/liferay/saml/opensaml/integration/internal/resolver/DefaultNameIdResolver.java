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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;

import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDPolicy;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = {"companyId=0"}, service = NameIdResolver.class
)
public class DefaultNameIdResolver implements NameIdResolver {

	@Override
	public NameID resolve(
		User user, String entityId, NameIDPolicy nameIdPolicy) {

		String nameIdFormat = getNameIdFormat(entityId, nameIdPolicy);
		String nameIdValue = getNameIdValue(user, entityId);

		String spNameQualifier = null;

		if ((nameIdPolicy != null) &&
			Validator.isNotNull(nameIdPolicy.getSPNameQualifier())) {

			spNameQualifier = nameIdPolicy.getSPNameQualifier();
		}

		return OpenSamlUtil.buildNameId(
			nameIdFormat, null, spNameQualifier, nameIdValue);
	}

	@Reference(unbind = "-")
	public void setMetadataManager(MetadataManager metadataManager) {
		_metadataManager = metadataManager;
	}

	protected String getNameIdAttributeName(String entityId) {
		return _metadataManager.getNameIdAttribute(entityId);
	}

	protected String getNameIdFormat(
		String entityId, NameIDPolicy nameIdPolicy) {

		if ((nameIdPolicy != null) &&
			Validator.isNotNull(nameIdPolicy.getFormat())) {

			return nameIdPolicy.getFormat();
		}

		return _metadataManager.getNameIdFormat(entityId);
	}

	protected String getNameIdValue(User user, String entityId) {
		String nameIdAttributeName = getNameIdAttributeName(entityId);

		String nameIdValue = user.getEmailAddress();

		if (Validator.isNull(nameIdAttributeName)) {
			return nameIdValue;
		}

		if (nameIdAttributeName.startsWith("expando:")) {
			String attributeName = nameIdAttributeName.substring(8);

			ExpandoBridge expandoBridge = user.getExpandoBridge();

			nameIdValue = String.valueOf(
				expandoBridge.getAttribute(attributeName));
		}
		else if (nameIdAttributeName.startsWith("static:")) {
			nameIdValue = nameIdAttributeName.substring(7);
		}
		else {
			nameIdValue = String.valueOf(
				BeanPropertiesUtil.getObject(user, nameIdAttributeName));
		}

		return nameIdValue;
	}

	private MetadataManager _metadataManager;

}