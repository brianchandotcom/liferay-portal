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

package com.liferay.saml.persistence.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.model.SamlPeerBinding;
import com.liferay.saml.persistence.service.persistence.SamlPeerBindingFinder;
import com.liferay.saml.persistence.service.persistence.SamlPeerBindingPersistence;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(service = SamlPeerBindingFinder.class)
public class SamlPeerBindingFinderImpl implements SamlPeerBindingFinder {

	public SamlPeerBinding fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
		long companyId, boolean deleted, String samlNameIdFormat,
		String samlNameIdNameQualifier, String samlNameIdValue,
		String samlPeerEntityId,
		OrderByComparator<SamlPeerBinding> orderByComparator) {

		List<SamlPeerBinding> list = findByC_D_SNIF_SNINQ_SNIV_SPEI(
			companyId, deleted, samlNameIdFormat, samlNameIdNameQualifier,
			samlNameIdValue, samlPeerEntityId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
			_samlPeerBindingPersistence.findByC_D_SNIV(
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
			_samlPeerBindingPersistence.findByC_U_D_SPEI(
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
	private SamlPeerBindingPersistence _samlPeerBindingPersistence;

}