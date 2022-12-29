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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.service.base.SamlPeerBindingLocalServiceBaseImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public SamlPeerBinding fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId,
		OrderByComparator<SamlPeerBinding> orderByComparator) {

		List<SamlPeerBinding> list =
			samlPeerBindingLocalService.findByC_D_SNIF_SNINQ_SNIV_SPEI(
				companyId, deleted, samlNameIdFormat, samlNameIdNameQualifier,
				samlNameIdValue, samlPeerEntityId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public SamlPeerBinding fetchSamlPeerBinding(
		long companyId, String samlNameIdFormat, String samlNameIdNameQualifier,
		String samlNameIdValue, String samlSpEntityId) {

		return samlPeerBindingPersistence.fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
			companyId, false, samlNameIdFormat, samlNameIdNameQualifier,
			samlNameIdValue, samlSpEntityId, null);
	}

	public List<SamlPeerBinding> findByC_D_SNIF_SNINQ_SNIV_SPEI(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId) {

		return findByC_D_SNIF_SNINQ_SNIV_SPEI(
			companyId, deleted, samlNameIdFormat, samlNameIdNameQualifier,
			samlNameIdValue, samlPeerEntityId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<SamlPeerBinding> findByC_D_SNIF_SNINQ_SNIV_SPEI(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId, int start, int end,
		OrderByComparator<SamlPeerBinding> orderByComparator) {

		List<SamlPeerBinding> samlPeerBindings =
			samlPeerBindingPersistence.findByC_D_SNIV(
				companyId, deleted, samlNameIdValue, start, end,
				orderByComparator);

		return ListUtil.filter(
			samlPeerBindings,
			samlPeerBinding -> _validateFieldsSNIF_SNINQ_SPEI(
				samlPeerBinding, samlNameIdFormat, samlNameIdNameQualifier,
				samlPeerEntityId));
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
			samlPeerBinding -> _validateFieldsSNIF_SNINQ(
				samlPeerBinding, samlNameIdFormat, samlNameIdNameQualifier));
	}

	private boolean _validateFieldsSNIF_SNINQ(
		SamlPeerBinding samlPeerBinding, String samlNameIdFormat,
		String samlNameIdNameQualifier) {

		if (Validator.isNull(samlNameIdNameQualifier)) {
			samlNameIdNameQualifier = "";
		}

		if (Objects.equals(
			samlNameIdFormat, samlPeerBinding.getSamlNameIdFormat()) &&
			Objects.equals(
				samlNameIdNameQualifier,
				samlPeerBinding.getSamlNameIdNameQualifier())) {

			return true;
		}

		return false;
	}

	private boolean _validateFieldsSNIF_SNINQ_SPEI(
		SamlPeerBinding samlPeerBinding, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlPeerEntityId) {

		if (Validator.isNull(samlNameIdNameQualifier)) {
			samlNameIdNameQualifier = "";
		}

		if (Objects.equals(
			samlNameIdFormat, samlPeerBinding.getSamlNameIdFormat()) &&
			Objects.equals(
				samlPeerEntityId, samlPeerBinding.getSamlPeerEntityId()) &&
			Objects.equals(
				samlNameIdNameQualifier,
				samlPeerBinding.getSamlNameIdNameQualifier())) {

			return true;
		}

		return false;
	}

	@Reference
	private UserLocalService _userLocalService;

}