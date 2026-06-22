/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for AssetVocabularyGroupRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRelServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AssetVocabularyGroupRelService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetVocabularyGroupRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the asset vocabulary group rel remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link AssetVocabularyGroupRelServiceUtil} if injection and service tracking are not available.
	 */
	public AssetVocabularyGroupRel addAssetVocabularyGroupRel(
			long groupId, long vocabularyId, int depotEntryType)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetVocabularyGroupRel>
			getAssetVocabularyGroupRelsByVocabularyId(long vocabularyId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public void setAssetVocabularyGroupRels(
			long vocabularyId, long[] groupIds, int depotEntryType)
		throws PortalException;

}
// LIFERAY-SERVICE-BUILDER-HASH:50115588