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

package com.liferay.oauth.service;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for OAuthApplication. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface OAuthApplicationService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.oauth.service.impl.OAuthApplicationServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the o auth application remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link OAuthApplicationServiceUtil} if injection and service tracking are not available.
	 */
	public OAuthApplication addOAuthApplication(
			String name, String description, int accessLevel,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteLogo(long oAuthApplicationId) throws PortalException;

	public OAuthApplication deleteOAuthApplication(long oAuthApplicationId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public OAuthApplication updateLogo(
			long oAuthApplicationId, InputStream inputStream)
		throws PortalException;

	public OAuthApplication updateOAuthApplication(
			long oAuthApplicationId, String name, String description,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			ServiceContext serviceContext)
		throws PortalException;

}