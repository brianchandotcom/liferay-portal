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

package com.liferay.search.experiences.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.search.experiences.model.SXPElement;

import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for SXPElement. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SXPElementServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SXPElementService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.search.experiences.service.impl.SXPElementServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the sxp element remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SXPElementServiceUtil} if injection and service tracking are not available.
	 */
	public SXPElement addSXPElement(
			Map<Locale, String> descriptionMap, String elementDefinitionJSON,
			boolean readOnly, String schemaVersion,
			Map<Locale, String> titleMap, int type,
			ServiceContext serviceContext)
		throws PortalException;

	public SXPElement deleteSXPElement(long sxpElementId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SXPElement getSXPElement(long sxpElementId) throws PortalException;

	public SXPElement updateSXPElement(
			long sxpElementId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, String schemaVersion, boolean hidden,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException;

}