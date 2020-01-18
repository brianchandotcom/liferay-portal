/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.search;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEventException;
import com.liferay.change.tracking.listener.CTEventListener;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.model.uid.UIDFactory;

import java.io.Serializable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
@Component(service = CTEventListener.class)
public class CTSearchEventListener implements CTEventListener {

	@Override
	public void onAfterCopy(
		CTCollection sourceCTCollection, CTCollection targetCTCollection) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try (SafeClosable safeClosable =
						CTCollectionThreadLocal.setCTCollectionId(
							targetCTCollection.getCtCollectionId())) {

					for (Map.Entry<String, List<CTEntry>> ctEntryEntry :
							_getCTEntryEntries(
								targetCTCollection.getCtCollectionId())) {

						_reindex(
							ctEntryEntry.getKey(), ctEntryEntry.getValue());
					}
				}

				return null;
			});
	}

	@Override
	public void onAfterPublish(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection == null) {
			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try (SafeClosable safeClosable =
						CTCollectionThreadLocal.setCTCollectionId(
							CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

					for (Map.Entry<String, List<CTEntry>> ctEntryEntry :
							_getCTEntryEntries(ctCollectionId)) {

						String className = ctEntryEntry.getKey();

						Indexer<?> indexer = _indexerRegistry.getIndexer(
							className);

						if (indexer == null) {
							continue;
						}

						List<CTEntry> ctEntries = ctEntryEntry.getValue();

						List<String> uids = new ArrayList<>(ctEntries.size());

						for (CTEntry ctEntry : ctEntries) {
							uids.add(
								_uidFactory.getUID(
									className, ctEntry.getModelClassPK(),
									CTConstants.CT_COLLECTION_ID_PRODUCTION));
						}

						_indexWriterHelper.deleteDocuments(
							indexer.getSearchEngineId(),
							ctCollection.getCompanyId(), uids,
							indexer.isCommitImmediately());

						_reindex(
							ctEntryEntry.getKey(), ctEntryEntry.getValue());
					}
				}

				return null;
			});
	}

	@Override
	public void onBeforeRemove(long ctCollectionId) throws CTEventException {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if ((ctCollection == null) ||
			(ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		for (Map.Entry<String, List<CTEntry>> ctEntryEntry :
				_getCTEntryEntries(ctCollectionId)) {

			String className = ctEntryEntry.getKey();

			Indexer<?> indexer = _indexerRegistry.getIndexer(className);

			if (indexer == null) {
				continue;
			}

			List<CTEntry> ctEntries = ctEntryEntry.getValue();

			List<String> uids = new ArrayList<>(ctEntries.size());

			for (CTEntry ctEntry : ctEntries) {
				if (ctEntry.getChangeType() !=
						CTConstants.CT_CHANGE_TYPE_DELETION) {

					uids.add(
						_uidFactory.getUID(
							className, ctEntry.getModelClassPK(),
							ctEntry.getCtCollectionId()));
				}
			}

			try {
				_indexWriterHelper.deleteDocuments(
					indexer.getSearchEngineId(), ctCollection.getCompanyId(),
					uids, indexer.isCommitImmediately());
			}
			catch (SearchException searchException) {
				throw new CTEventException(searchException);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CTService.class, null,
			(serviceReference, emitter) -> {
				try {
					CTService<?> ctService = bundleContext.getService(
						serviceReference);

					Class<?> clazz = ctService.getModelClass();

					emitter.emit(clazz.getName());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			},
			new CTServiceServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Collection<Map.Entry<String, List<CTEntry>>> _getCTEntryEntries(
		long ctCollectionId) {

		Map<Long, Map.Entry<String, List<CTEntry>>> ctEntryMap =
			new HashMap<>();

		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId)) {

			Map.Entry<String, List<CTEntry>> entry = ctEntryMap.computeIfAbsent(
				ctEntry.getModelClassNameId(),
				classNameId -> {
					try {
						ClassName className =
							_classNameLocalService.getClassName(classNameId);

						return new AbstractMap.SimpleImmutableEntry<>(
							className.getValue(), new ArrayList<>());
					}
					catch (PortalException portalException) {
						throw new SystemException(portalException);
					}
				});

			List<CTEntry> ctEntries = entry.getValue();

			ctEntries.add(ctEntry);
		}

		return ctEntryMap.values();
	}

	@SuppressWarnings("unchecked")
	private <T extends CTModel<T>> void _reindex(
			String className, List<CTEntry> ctEntries)
		throws SearchException {

		Indexer<T> indexer = _indexerRegistry.getIndexer(className);

		if (indexer == null) {
			return;
		}

		CTServiceHolder<T> ctServiceHolder =
			(CTServiceHolder<T>)_serviceTrackerMap.getService(
				indexer.getClassName());

		CTService<T> ctService = ctServiceHolder._ctService;

		if (ctService == null) {
			throw new SystemException(
				StringBundler.concat(
					"Service for ", indexer.getClassName(), " is missing"));
		}

		Set<Serializable> primaryKeys = new HashSet<>();

		for (CTEntry ctEntry : ctEntries) {
			if (ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_DELETION) {

				primaryKeys.add(ctEntry.getModelClassPK());
			}
		}

		if (primaryKeys.isEmpty()) {
			return;
		}

		ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Map<Serializable, T> models = ctPersistence.fetchByPrimaryKeys(
					primaryKeys);

				indexer.reindex(models.values());

				return null;
			});
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	private ServiceTrackerMap<String, CTServiceHolder<?>> _serviceTrackerMap;

	@Reference
	private UIDFactory _uidFactory;

	private static class CTServiceHolder<T extends CTModel<T>> {

		private CTServiceHolder(CTService<T> ctService) {
			_ctService = ctService;
		}

		private final CTService<T> _ctService;

	}

	private class CTServiceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<CTService, CTServiceHolder<?>> {

		@Override
		public CTServiceHolder<?> addingService(
			ServiceReference<CTService> serviceReference) {

			CTService<?> ctService = _bundleContext.getService(
				serviceReference);

			return new CTServiceHolder<>(ctService);
		}

		@Override
		public void modifiedService(
			ServiceReference<CTService> serviceReference,
			CTServiceHolder<?> ctServiceHolder) {
		}

		@Override
		public void removedService(
			ServiceReference<CTService> serviceReference,
			CTServiceHolder<?> ctServiceHolder) {

			_bundleContext.ungetService(serviceReference);
		}

		private CTServiceServiceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}