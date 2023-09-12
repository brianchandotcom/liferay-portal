/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.instance.lifecycle;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.notification.handler.NotificationHandler;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.internal.item.selector.SystemObjectEntryItemSelectorView;
import com.liferay.object.internal.notification.handler.ObjectDefinitionNotificationHandler;
import com.liferay.object.internal.notification.term.contributor.ObjectDefinitionNotificationTermEvaluator;
import com.liferay.object.internal.related.models.SystemObject1toMObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.SystemObjectMtoMObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.rest.context.path.RESTContextPathResolverImpl;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.rest.context.path.RESTContextPathResolver;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.EveryNodeEveryStartup;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class SystemObjectDefinitionManagerPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements EveryNodeEveryStartup {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Registered portal instance " + company);
		}

		for (SystemObjectDefinitionManager systemObjectDefinitionManager :
				_serviceTrackerList) {

			_apply(company.getCompanyId(), systemObjectDefinitionManager);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Activate " + bundleContext);
		}

		_bundleContext = bundleContext;

		_openingThreadLocal.set(Boolean.TRUE);

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SystemObjectDefinitionManager.class, null,
			new EagerServiceTrackerCustomizer
				<SystemObjectDefinitionManager,
				 SystemObjectDefinitionManager>() {

				@Override
				public SystemObjectDefinitionManager addingService(
					ServiceReference<SystemObjectDefinitionManager>
						serviceReference) {

					SystemObjectDefinitionManager
						systemObjectDefinitionManager =
							bundleContext.getService(serviceReference);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Adding service " + systemObjectDefinitionManager);
					}

					if (!_openingThreadLocal.get()) {
						_companyLocalService.forEachCompanyId(
							companyId -> _apply(
								companyId, systemObjectDefinitionManager));
					}

					return systemObjectDefinitionManager;
				}

				@Override
				public void modifiedService(
					ServiceReference<SystemObjectDefinitionManager>
						serviceReference,
					SystemObjectDefinitionManager
						systemObjectDefinitionManager) {
				}

				@Override
				public void removedService(
					ServiceReference<SystemObjectDefinitionManager>
						serviceReference,
					SystemObjectDefinitionManager
						systemObjectDefinitionManager) {

					bundleContext.ungetService(serviceReference);
				}

			});

		_openingThreadLocal.set(Boolean.FALSE);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private void _apply(
		long companyId,
		SystemObjectDefinitionManager systemObjectDefinitionManager) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Applying ", systemObjectDefinitionManager, " to company ",
					companyId));
		}

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					companyId, systemObjectDefinitionManager.getName());

			if ((objectDefinition == null) ||
				(objectDefinition.getVersion() !=
					systemObjectDefinitionManager.getVersion())) {

				ObjectFolder objectFolder =
					_objectFolderLocalService.addOrGetUncategorizedObjectFolder(
						companyId);

				objectDefinition =
					_objectDefinitionLocalService.
						addOrUpdateSystemObjectDefinition(
							companyId, objectFolder.getObjectFolderId(),
							systemObjectDefinitionManager);
			}

			_bundleContext.registerService(
				ItemSelectorView.class,
				new SystemObjectEntryItemSelectorView(
					_dtoConverterRegistry, _itemSelector,
					_itemSelectorViewDescriptorRenderer, objectDefinition,
					_objectFieldLocalService,
					_objectRelatedModelsProviderRegistry, _portal,
					_systemObjectDefinitionManagerRegistry, _userLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.selector.view.order", 500
				).build());
			_bundleContext.registerService(
				NotificationTermEvaluator.class,
				new ObjectDefinitionNotificationTermEvaluator(
					_listTypeLocalService, objectDefinition,
					_objectDefinitionLocalService, _objectEntryLocalService,
					_objectFieldLocalService, _objectRelationshipLocalService,
					_userLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"class.name", objectDefinition.getClassName()
				).build());
			_bundleContext.registerService(
				NotificationHandler.class,
				new ObjectDefinitionNotificationHandler(objectDefinition),
				HashMapDictionaryBuilder.<String, Object>put(
					"class.name", objectDefinition.getClassName()
				).build());
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new SystemObject1toMObjectRelatedModelsProviderImpl(
					objectDefinition, _objectDefinitionLocalService,
					_objectEntryLocalService, _objectFieldLocalService,
					_objectRelationshipLocalService,
					_persistedModelLocalServiceRegistry,
					systemObjectDefinitionManager,
					_systemObjectDefinitionManagerRegistry),
				null);
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new SystemObjectMtoMObjectRelatedModelsProviderImpl(
					objectDefinition, _objectDefinitionLocalService,
					_objectFieldLocalService, _objectRelationshipLocalService,
					_persistedModelLocalServiceRegistry,
					systemObjectDefinitionManager,
					_systemObjectDefinitionManagerRegistry),
				null);

			JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
				systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

			_bundleContext.registerService(
				RESTContextPathResolver.class,
				new RESTContextPathResolverImpl(
					"/o/" + jaxRsApplicationDescriptor.getRESTContextPath(),
					_objectScopeProviderRegistry.getObjectScopeProvider(
						objectDefinition.getScope()),
					true),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).build());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemObjectDefinitionManagerPortalInstanceLifecycleListener.class);

	private static final ThreadLocal<Boolean> _openingThreadLocal =
		new CentralizedThreadLocal<>(
			SystemObjectDefinitionManagerPortalInstanceLifecycleListener.class.
				getName() + "._openingThreadLocal",
			() -> Boolean.FALSE);

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.object.service)(release.schema.version>=1.0.0))"
	)
	private Release _release;

	private ServiceTrackerList<SystemObjectDefinitionManager>
		_serviceTrackerList;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}