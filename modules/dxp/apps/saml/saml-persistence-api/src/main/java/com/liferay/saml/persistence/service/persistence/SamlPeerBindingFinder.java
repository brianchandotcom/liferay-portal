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

package com.liferay.saml.persistence.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Mika Koivisto
 * @generated
 */
@ProviderType
public interface SamlPeerBindingFinder {

	public com.liferay.saml.persistence.model.SamlPeerBinding
		fetchByC_D_SNIF_SNINQ_SNIV_SPEI_First(
			long companyId, boolean deleted, String samlNameIdFormat,
			String samlNameIdNameQualifier, String samlNameIdValue,
			String samlPeerEntityId,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.saml.persistence.model.SamlPeerBinding>
					orderByComparator);

	public java.util.List<com.liferay.saml.persistence.model.SamlPeerBinding>
		findByC_D_SNIF_SNINQ_SNIV_SPEI(
			long companyId, boolean deleted, String samlNameIdFormat,
			String samlNameIdNameQualifier, String samlNameIdValue,
			String samlPeerEntityId);

	public java.util.List<com.liferay.saml.persistence.model.SamlPeerBinding>
		findByC_D_SNIF_SNINQ_SNIV_SPEI(
			long companyId, boolean deleted, String samlNameIdFormat,
			String samlNameIdNameQualifier, String samlNameIdValue,
			String samlPeerEntityId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.saml.persistence.model.SamlPeerBinding>
					orderByComparator);

	public java.util.List<com.liferay.saml.persistence.model.SamlPeerBinding>
		findByC_U_D_SNIF_SNINQ_SPEI(
			long companyId, long userId, boolean deleted,
			String samlNameIdFormat, String samlNameIdNameQualifier,
			String samlPeerEntityId);

}