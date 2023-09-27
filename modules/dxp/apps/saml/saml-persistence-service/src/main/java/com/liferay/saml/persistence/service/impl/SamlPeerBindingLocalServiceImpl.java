/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.service.base.SamlPeerBindingLocalServiceBaseImpl;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = "model.class.name=com.liferay.saml.persistence.model.SamlPeerBinding",
	service = AopService.class
)
public class SamlPeerBindingLocalServiceImpl
	extends SamlPeerBindingLocalServiceBaseImpl {

	@Override
	public SamlPeerBinding addSamlPeerBinding(
			long userId, String samlNameIdFormat,
			String samlNameIdNameQualifier, String samlNameIdSpNameQualifier,
			String samlNameIdSpProvidedId, String samlNameIdValue,
			String samlPeerEntityId)
		throws PortalException {

		User user = _userLocalService.getUserById(userId);

		SamlPeerBinding samlPeerBinding = samlPeerBindingPersistence.create(
			counterLocalService.increment(SamlPeerBinding.class.getName()));

		samlPeerBinding.setCompanyId(user.getCompanyId());
		samlPeerBinding.setUserId(user.getUserId());
		samlPeerBinding.setUserName(user.getFullName());
		samlPeerBinding.setSamlNameIdFormat(samlNameIdFormat);
		samlPeerBinding.setSamlNameIdSpNameQualifier(samlNameIdSpNameQualifier);
		samlPeerBinding.setSamlNameIdSpProvidedId(samlNameIdSpProvidedId);
		samlPeerBinding.setSamlNameIdValue(samlNameIdValue);
		samlPeerBinding.setSamlPeerEntityId(samlPeerEntityId);

		return samlPeerBindingPersistence.update(samlPeerBinding);
	}

	@Override
	public SamlPeerBinding fetchSamlPeerBinding(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId) {

		List<SamlPeerBinding> samlPeerBindings = getSamlPeerBindings(
			companyId, deleted, samlNameIdFormat, samlNameIdNameQualifier,
			samlNameIdValue, samlPeerEntityId);

		if (!samlPeerBindings.isEmpty()) {
			return samlPeerBindings.get(0);
		}

		return null;
	}

	@Override
	public List<SamlPeerBinding> getSamlPeerBindings(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId) {

		List<SamlPeerBinding> samlPeerBindings =
			samlPeerBindingPersistence.findByC_D_SNIV(
				companyId, deleted, samlNameIdValue, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		return ListUtil.filter(
			samlPeerBindings,
			samlPeerBinding ->
				Objects.equals(
					GetterUtil.getString(samlNameIdFormat),
					samlPeerBinding.getSamlNameIdFormat()) &&
				Objects.equals(
					GetterUtil.getString(samlNameIdNameQualifier),
					samlPeerBinding.getSamlNameIdNameQualifier()) &&
				Objects.equals(
					GetterUtil.getString(samlPeerEntityId),
					samlPeerBinding.getSamlPeerEntityId()));
	}

	@Override
	public List<SamlPeerBinding> getUserSamlPeerBindings(
			long userId, boolean deleted, String samlNameIdFormat,
			String samlNameIdNameQualifier, String samlPeerEntityId)
		throws PortalException {

		User user = _userLocalService.getUserById(userId);

		List<SamlPeerBinding> samlPeerBindings =
			samlPeerBindingPersistence.findByC_U_D_SPEI(
				user.getCompanyId(), userId, deleted, samlPeerEntityId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return ListUtil.filter(
			samlPeerBindings,
			samlPeerBinding ->
				Objects.equals(
					GetterUtil.getString(samlNameIdFormat),
					samlPeerBinding.getSamlNameIdFormat()) &&
				Objects.equals(
					GetterUtil.getString(samlNameIdNameQualifier),
					samlPeerBinding.getSamlNameIdNameQualifier()));
	}

	@Reference
	private UserLocalService _userLocalService;

}