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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.reports.engine.console.model.Definition;

import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for Definition. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DefinitionServiceUtil
 * @see com.liferay.portal.reports.engine.console.service.base.DefinitionServiceBaseImpl
 * @see com.liferay.portal.reports.engine.console.service.impl.DefinitionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=reports", "json.web.service.context.path=Definition"}, service = DefinitionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DefinitionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DefinitionServiceUtil} to access the definition remote service. Add custom service methods to {@link com.liferay.portal.reports.engine.console.service.impl.DefinitionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public Definition addDefinition(long groupId, Map<Locale, String> nameMap,
		Map<Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName, InputStream inputStream,
		ServiceContext serviceContext) throws PortalException;

	public Definition deleteDefinition(long definitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Definition getDefinition(long definitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Definition> getDefinitions(long groupId, String definitionName,
		String description, String sourceId, String reportName,
		boolean andSearch, int start, int end,
		OrderByComparator orderByComparator) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDefinitionsCount(long groupId, String definitionName,
		String description, String sourceId, String reportName,
		boolean andSearch);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public Definition updateDefinition(long definitionId,
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
		long sourceId, String reportParameters, String fileName,
		InputStream inputStream, ServiceContext serviceContext)
		throws PortalException;
}