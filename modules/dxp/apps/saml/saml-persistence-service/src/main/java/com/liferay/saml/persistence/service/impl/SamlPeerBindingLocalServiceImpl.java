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
	public SamlPeerBinding fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId) {

		List<SamlPeerBinding> list =
			samlPeerBindingLocalService.findByC_D_SNIF_SNINQ_SNIV_SPEI(
				companyId, deleted, samlNameIdFormat, samlNameIdNameQualifier,
				samlNameIdValue, samlPeerEntityId);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public SamlPeerBinding fetchSamlPeerBinding(
		long companyId, String samlNameIdFormat, String samlNameIdNameQualifier,
		String samlNameIdValue, String samlSpEntityId) {

		return fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
			companyId, false, samlNameIdFormat, samlNameIdNameQualifier,
			samlNameIdValue, samlSpEntityId);
	}

	@Override
	public List<SamlPeerBinding> findByC_D_SNIF_SNINQ_SNIV_SPEI(
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
					GetterUtil.getString(samlPeerEntityId),
					samlPeerBinding.getSamlPeerEntityId()) &&
				Objects.equals(
					GetterUtil.getString(samlNameIdNameQualifier),
					samlPeerBinding.getSamlNameIdNameQualifier()));
	}

	public List<SamlPeerBinding> findByC_U_D_SNIF_SNINQ_SPEI(
		long companyId, long userId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlPeerEntityId) {

		List<SamlPeerBinding> samlPeerBindings =
			samlPeerBindingPersistence.findByC_U_D_SPEI(
				companyId, userId, deleted, samlPeerEntityId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

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