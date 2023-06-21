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

package com.liferay.search.experiences.service.impl;

import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.exception.SXPBlueprintTitleException;
import com.liferay.search.experiences.internal.info.collection.provider.SXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.base.SXPBlueprintLocalServiceBaseImpl;
import com.liferay.search.experiences.validator.SXPBlueprintValidator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.search.experiences.model.SXPBlueprint",
	service = AopService.class
)
public class SXPBlueprintLocalServiceImpl
	extends SXPBlueprintLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint addSXPBlueprint(
			String externalReferenceCode, long userId, String configurationJSON,
			Map<Locale, String> descriptionMap, String elementInstancesJSON,
			String schemaVersion, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(titleMap, serviceContext);

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.create(
			counterLocalService.increment());

		sxpBlueprint.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(userId);

		sxpBlueprint.setCompanyId(user.getCompanyId());
		sxpBlueprint.setUserId(user.getUserId());
		sxpBlueprint.setUserName(user.getFullName());

		sxpBlueprint.setConfigurationJSON(configurationJSON);
		sxpBlueprint.setDescriptionMap(descriptionMap);
		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);
		sxpBlueprint.setSchemaVersion(schemaVersion);
		sxpBlueprint.setTitleMap(titleMap);
		sxpBlueprint.setVersion(
			String.format(
				"%.1f",
				GetterUtil.getFloat(sxpBlueprint.getVersion(), 0.9F) + 0.1));
		sxpBlueprint.setStatus(WorkflowConstants.STATUS_APPROVED);
		sxpBlueprint.setStatusByUserId(user.getUserId());
		sxpBlueprint.setStatusDate(serviceContext.getModifiedDate(null));

		sxpBlueprint = sxpBlueprintPersistence.update(sxpBlueprint);

		_resourceLocalService.addModelResources(sxpBlueprint, serviceContext);

		_registerCollectionProvider(sxpBlueprint);

		return sxpBlueprint;
	}

	@Override
	public void deleteCompanySXPBlueprints(long companyId)
		throws PortalException {

		List<SXPBlueprint> sxpBlueprints =
			sxpBlueprintPersistence.findByCompanyId(companyId);

		for (SXPBlueprint sxpBlueprint : sxpBlueprints) {
			sxpBlueprintLocalService.deleteSXPBlueprint(sxpBlueprint);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.findByPrimaryKey(
			sxpBlueprintId);

		return deleteSXPBlueprint(sxpBlueprint);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SXPBlueprint deleteSXPBlueprint(SXPBlueprint sxpBlueprint)
		throws PortalException {

		sxpBlueprint = sxpBlueprintPersistence.remove(sxpBlueprint);

		_resourceLocalService.deleteResource(
			sxpBlueprint, ResourceConstants.SCOPE_INDIVIDUAL);

		_unregisterCollectionProvider(sxpBlueprint);

		return sxpBlueprint;
	}

	@Override
	public List<SXPBlueprint> getSXPBlueprints(long companyId) {
		return sxpBlueprintPersistence.findByCompanyId(companyId);
	}

	@Override
	public int getSXPBlueprintsCount(long companyId) {
		return sxpBlueprintPersistence.countByCompanyId(companyId);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				List<SXPBlueprint> sxpBlueprints =
					sxpBlueprintLocalService.getSXPBlueprints(companyId);

				for (SXPBlueprint sxpBlueprint : sxpBlueprints) {
					_registerCollectionProvider(sxpBlueprint);
				}
			});
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint updateStatus(
			long userId, long sxpBlueprintId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.findByPrimaryKey(
			sxpBlueprintId);

		if (sxpBlueprint.getStatus() == status) {
			return sxpBlueprint;
		}

		sxpBlueprint.setStatus(status);

		User user = _userLocalService.getUser(userId);

		sxpBlueprint.setStatusByUserId(user.getUserId());
		sxpBlueprint.setStatusByUserName(user.getFullName());

		sxpBlueprint.setStatusDate(serviceContext.getModifiedDate(null));

		_registerCollectionProvider(sxpBlueprint);

		return sxpBlueprintPersistence.update(sxpBlueprint);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SXPBlueprint updateSXPBlueprint(
			long userId, long sxpBlueprintId, String configurationJSON,
			Map<Locale, String> descriptionMap, String elementInstancesJSON,
			String schemaVersion, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(titleMap, serviceContext);

		SXPBlueprint sxpBlueprint = sxpBlueprintPersistence.findByPrimaryKey(
			sxpBlueprintId);

		sxpBlueprint.setConfigurationJSON(configurationJSON);
		sxpBlueprint.setDescriptionMap(descriptionMap);
		sxpBlueprint.setElementInstancesJSON(elementInstancesJSON);
		sxpBlueprint.setTitleMap(titleMap);
		sxpBlueprint.setVersion(
			String.format(
				"%.1f",
				GetterUtil.getFloat(sxpBlueprint.getVersion(), 0.9F) + 0.1));

		_registerCollectionProvider(sxpBlueprint);

		return updateSXPBlueprint(sxpBlueprint);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _registerCollectionProvider(SXPBlueprint sxpBlueprint) {
		_unregisterCollectionProvider(sxpBlueprint);

		ServiceRegistration<InfoCollectionProvider>
			infoCollectionProviderServiceRegistration =
				_bundleContext.registerService(
					InfoCollectionProvider.class,
					new SXPBlueprintInfoCollectionProvider(
						_assetHelper, _groupLocalService, _searcher,
						_searchRequestBuilderFactory, sxpBlueprint),
					HashMapDictionaryBuilder.<String, Object>put(
						"company.id", sxpBlueprint.getCompanyId()
					).put(
						"item.class.name",
						"com.liferay.asset.kernel.model.AssetEntry"
					).build());

		_serviceRegistrations.put(
			sxpBlueprint.getExternalReferenceCode(),
			infoCollectionProviderServiceRegistration);
	}

	private void _unregisterCollectionProvider(SXPBlueprint sxpBlueprint) {
		_serviceRegistrations.computeIfPresent(
			sxpBlueprint.getExternalReferenceCode(),
			(key, serviceRegistration) -> {
				serviceRegistration.unregister();

				return null;
			});
	}

	private void _validate(
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws SXPBlueprintTitleException {

		if (!GetterUtil.getBoolean(
				serviceContext.getAttribute(
					SXPBlueprintLocalServiceImpl.class.getName() +
						"#_validate"),
				true)) {

			return;
		}

		_sxpBlueprintValidator.validate(titleMap);
	}

	@Reference
	private AssetHelper _assetHelper;

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private final Map<String, ServiceRegistration<?>> _serviceRegistrations =
		Collections.synchronizedMap(new LinkedHashMap<>());

	@Reference
	private SXPBlueprintValidator _sxpBlueprintValidator;

	@Reference
	private UserLocalService _userLocalService;

}