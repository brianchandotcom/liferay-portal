/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.impl;

import com.liferay.client.extension.exception.ClientExtensionEntryNameException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.client.extension.service.base.ClientExtensionEntryLocalServiceBaseImpl;
import com.liferay.client.extension.type.deployer.CETDeployer;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.client.extension.model.ClientExtensionEntry",
	service = AopService.class
)
public class ClientExtensionEntryLocalServiceImpl
	extends ClientExtensionEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ClientExtensionEntry addClientExtensionEntry(
			String externalReferenceCode, long userId, String description,
			Map<Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws PortalException {

		_validateName(nameMap);
		_validateTypeSettings(typeSettings, null, type);

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryPersistence.create(
				counterLocalService.increment());

		clientExtensionEntry.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(userId);

		clientExtensionEntry.setCompanyId(user.getCompanyId());
		clientExtensionEntry.setUserId(user.getUserId());
		clientExtensionEntry.setUserName(user.getFullName());

		clientExtensionEntry.setDescription(description);
		clientExtensionEntry.setNameMap(nameMap);
		clientExtensionEntry.setProperties(properties);
		clientExtensionEntry.setSourceCodeURL(sourceCodeURL);
		clientExtensionEntry.setType(type);
		clientExtensionEntry.setTypeSettings(typeSettings);
		clientExtensionEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		clientExtensionEntry.setStatusByUserId(userId);
		clientExtensionEntry.setStatusDate(new Date());

		clientExtensionEntry = clientExtensionEntryPersistence.update(
			clientExtensionEntry);

		_addResources(clientExtensionEntry);

		return _startWorkflowInstance(userId, clientExtensionEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ClientExtensionEntry addOrUpdateClientExtensionEntry(
			String externalReferenceCode, long userId, String description,
			Map<Locale, String> nameMap, String properties,
			String sourceCodeURL, String type, String typeSettings)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryLocalService.
				fetchClientExtensionEntryByExternalReferenceCode(
					externalReferenceCode, user.getCompanyId());

		if (clientExtensionEntry != null) {
			return clientExtensionEntryLocalService.updateClientExtensionEntry(
				userId, clientExtensionEntry.getClientExtensionEntryId(),
				description, nameMap, properties, sourceCodeURL, typeSettings);
		}

		return addClientExtensionEntry(
			externalReferenceCode, userId, description, nameMap, properties,
			sourceCodeURL, type, typeSettings);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ClientExtensionEntry deleteClientExtensionEntry(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		clientExtensionEntryPersistence.remove(clientExtensionEntry);

		_resourceLocalService.deleteResource(
			clientExtensionEntry.getCompanyId(),
			ClientExtensionEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			clientExtensionEntry.getClientExtensionEntryId());

		_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
			clientExtensionEntry.getCompanyId(),
			clientExtensionEntry.getExternalReferenceCode());

		clientExtensionEntryLocalService.undeployClientExtensionEntry(
			clientExtensionEntry);

		return clientExtensionEntry;
	}

	@Override
	public ClientExtensionEntry deleteClientExtensionEntry(
			long clientExtensionEntryId)
		throws PortalException {

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryPersistence.findByPrimaryKey(
				clientExtensionEntryId);

		return deleteClientExtensionEntry(clientExtensionEntry);
	}

	@Clusterable
	@Override
	public void deployClientExtensionEntry(
			ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		undeployClientExtensionEntry(clientExtensionEntry);

		_serviceRegistrationsMap.put(
			clientExtensionEntry.getClientExtensionEntryId(),
			_cetDeployer.deploy(_cetFactory.create(clientExtensionEntry)));
	}

	@Override
	public List<ClientExtensionEntry> getClientExtensionEntries(
		long companyId, int start, int end) {

		return clientExtensionEntryPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<ClientExtensionEntry> getClientExtensionEntries(
		long companyId, String type, int start, int end) {

		return clientExtensionEntryPersistence.findByC_T(
			companyId, type, start, end);
	}

	@Override
	public int getClientExtensionEntriesCount(long companyId) {
		return clientExtensionEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getClientExtensionEntriesCount(long companyId, String type) {
		return clientExtensionEntryPersistence.countByC_T(companyId, type);
	}

	@Override
	public List<ClientExtensionEntry> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, start, end, sort);

		Indexer<ClientExtensionEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(ClientExtensionEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<ClientExtensionEntry> clientExtensionEntries =
				_getClientExtensionEntries(hits);

			if (clientExtensionEntries != null) {
				return clientExtensionEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws PortalException {

		Indexer<ClientExtensionEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(ClientExtensionEntry.class);

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				List<ClientExtensionEntry> clientExtensionEntries =
					clientExtensionEntryLocalService.getClientExtensionEntries(
						companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (ClientExtensionEntry clientExtensionEntry :
						clientExtensionEntries) {

					try {
						deployClientExtensionEntry(clientExtensionEntry);
					}
					catch (PortalException portalException) {
						ReflectionUtil.throwException(portalException);
					}
				}
			});
	}

	@Clusterable
	@Override
	public void undeployClientExtensionEntry(
		ClientExtensionEntry clientExtensionEntry) {

		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMap.remove(
				clientExtensionEntry.getClientExtensionEntryId());

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ClientExtensionEntry updateClientExtensionEntry(
			long userId, long clientExtensionEntryId, String description,
			Map<Locale, String> nameMap, String properties,
			String sourceCodeURL, String typeSettings)
		throws PortalException {

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryPersistence.findByPrimaryKey(
				clientExtensionEntryId);

		_validateTypeSettings(
			typeSettings, clientExtensionEntry.getTypeSettings(),
			clientExtensionEntry.getType());

		clientExtensionEntryLocalService.undeployClientExtensionEntry(
			clientExtensionEntry);

		clientExtensionEntry.setDescription(description);
		clientExtensionEntry.setNameMap(nameMap);
		clientExtensionEntry.setProperties(properties);
		clientExtensionEntry.setSourceCodeURL(sourceCodeURL);
		clientExtensionEntry.setTypeSettings(typeSettings);
		clientExtensionEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		clientExtensionEntry.setStatusByUserId(userId);
		clientExtensionEntry.setStatusDate(new Date());

		clientExtensionEntry = clientExtensionEntryPersistence.update(
			clientExtensionEntry);

		return _startWorkflowInstance(userId, clientExtensionEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ClientExtensionEntry updateStatus(
			long userId, long clientExtensionEntryId, int status)
		throws PortalException {

		ClientExtensionEntry clientExtensionEntry =
			clientExtensionEntryPersistence.findByPrimaryKey(
				clientExtensionEntryId);

		if (status == clientExtensionEntry.getStatus()) {
			return clientExtensionEntry;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			clientExtensionEntryLocalService.deployClientExtensionEntry(
				clientExtensionEntry);
		}
		else if (clientExtensionEntry.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

			clientExtensionEntryLocalService.undeployClientExtensionEntry(
				clientExtensionEntry);
		}

		User user = _userLocalService.getUser(userId);

		clientExtensionEntry.setStatus(status);
		clientExtensionEntry.setStatusByUserId(user.getUserId());
		clientExtensionEntry.setStatusByUserName(user.getFullName());
		clientExtensionEntry.setStatusDate(new Date());

		return clientExtensionEntryPersistence.update(clientExtensionEntry);
	}

	private void _addResources(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		_resourceLocalService.addResources(
			clientExtensionEntry.getCompanyId(), 0,
			clientExtensionEntry.getUserId(),
			ClientExtensionEntry.class.getName(),
			clientExtensionEntry.getClientExtensionEntryId(), false, true,
			true);
	}

	private SearchContext _buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				Field.URL, keywords
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private List<ClientExtensionEntry> _getClientExtensionEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<ClientExtensionEntry> clientExtensionEntries = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long clientExtensionEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			ClientExtensionEntry clientExtensionEntry =
				clientExtensionEntryPersistence.fetchByPrimaryKey(
					clientExtensionEntryId);

			if (clientExtensionEntry == null) {
				clientExtensionEntries = null;

				Indexer<ClientExtensionEntry> indexer =
					IndexerRegistryUtil.getIndexer(ClientExtensionEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				clientExtensionEntries.add(clientExtensionEntry);
			}
		}

		return clientExtensionEntries;
	}

	private ClientExtensionEntry _startWorkflowInstance(
			long userId, ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			clientExtensionEntry.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			clientExtensionEntry.getCompanyId(), company.getGroupId(), userId,
			ClientExtensionEntry.class.getName(),
			clientExtensionEntry.getClientExtensionEntryId(),
			clientExtensionEntry, serviceContext, new HashMap<>());
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		if (Validator.isBlank(nameMap.get(LocaleUtil.getDefault()))) {
			throw new ClientExtensionEntryNameException();
		}
	}

	private void _validateTypeSettings(
			String newTypeSettings, String oldTypeSettings, String type)
		throws PortalException {

		UnicodeProperties newTypeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).load(
				newTypeSettings
			).build();

		UnicodeProperties oldTypeSettingsUnicodeProperties = null;

		if (oldTypeSettings != null) {
			oldTypeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).load(
				oldTypeSettings
			).build();
		}

		_cetFactory.validate(
			newTypeSettingsUnicodeProperties, oldTypeSettingsUnicodeProperties,
			type);
	}

	@Reference
	private CETDeployer _cetDeployer;

	@Reference
	private CETFactory _cetFactory;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	private final Map<Long, List<ServiceRegistration<?>>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}