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

package com.liferay.saml.persistence.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.saml.persistence.model.SamlSpMessage;
import com.liferay.saml.persistence.service.base.SamlSpMessageLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	property = "model.class.name=com.liferay.saml.persistence.model.SamlSpMessage",
	service = AopService.class
)
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

		return samlSpMessagePersistence.update(samlSpMessage);
	}

	@Override
	public void deleteExpiredSamlSpMessages() {
		samlSpMessagePersistence.removeByLtExpirationDate(new Date());
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