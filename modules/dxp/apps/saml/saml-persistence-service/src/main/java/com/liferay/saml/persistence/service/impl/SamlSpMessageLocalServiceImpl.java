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

package com.liferay.saml.persistence.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.saml.persistence.model.SamlSpMessage;
import com.liferay.saml.persistence.service.base.SamlSpMessageLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class SamlSpMessageLocalServiceImpl
	extends SamlSpMessageLocalServiceBaseImpl {

	@Override
	public SamlSpMessage addSamlSpMessage(
		String samlIdpEntityId, String samlIdpResponseKey, Date expirationDate,
		ServiceContext serviceContext) {

		long samlSpMessageId = counterLocalService.increment(
			SamlSpMessage.class.getName());

		SamlSpMessage samlSpMessage = samlSpMessagePersistence.create(
			samlSpMessageId);

		samlSpMessage.setCompanyId(serviceContext.getCompanyId());
		samlSpMessage.setCreateDate(new Date());
		samlSpMessage.setSamlIdpEntityId(samlIdpEntityId);
		samlSpMessage.setSamlIdpResponseKey(samlIdpResponseKey);
		samlSpMessage.setExpirationDate(expirationDate);

		samlSpMessagePersistence.update(samlSpMessage);

		return samlSpMessage;
	}

	@Override
	public void deleteExpiredSamlSpMessages() {
		samlSpMessagePersistence.removeByExpirationDate(new Date());
	}

	@Override
	public SamlSpMessage fetchSamlSpMessage(
		String samlIdpEntityId, String samlIdpResponseKey) {

		return samlSpMessagePersistence.fetchBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);
	}

	@Override
	public SamlSpMessage getSamlSpMessage(
			String samlIdpEntityId, String samlIdpResponseKey)
		throws PortalException {

		return samlSpMessagePersistence.findBySIEI_SIRK(
			samlIdpEntityId, samlIdpResponseKey);
	}

}