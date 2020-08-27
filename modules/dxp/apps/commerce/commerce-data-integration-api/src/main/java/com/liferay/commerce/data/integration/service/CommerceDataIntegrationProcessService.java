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

package com.liferay.commerce.data.integration.service;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceDataIntegrationProcess. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceDataIntegrationProcess"
	},
	service = CommerceDataIntegrationProcessService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceDataIntegrationProcessService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.data.integration.service.impl.CommerceDataIntegrationProcessServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce data integration process remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceDataIntegrationProcessServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceDataIntegrationProcess addCommerceDataIntegrationProcess(
			long userId, String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException;

	public void deleteCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceDataIntegrationProcess fetchCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceDataIntegrationProcess getCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceDataIntegrationProcess>
			getCommerceDataIntegrationProcesses(
				long companyId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceDataIntegrationProcessesCount(long companyId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceDataIntegrationProcess updateCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId, String name,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException;

	public CommerceDataIntegrationProcess
			updateCommerceDataIntegrationProcessTrigger(
				long commerceDataIntegrationProcessId, boolean active,
				String cronExpression, int startDateMonth, int startDateDay,
				int startDateYear, int startDateHour, int startDateMinute,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd)
		throws PortalException;

}