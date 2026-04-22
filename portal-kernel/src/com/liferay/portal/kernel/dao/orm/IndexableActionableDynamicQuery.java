/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.security.auth.CompanyInheritableThreadLocalCallable;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.util.PropsValues;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author Andrew Betts
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IndexableActionableDynamicQuery {

	public void performActions() {
		if (_performActionUnsafeFunction == null) {
			throw new IllegalStateException(
				"Perform action unsafe function is null");
		}

		_hasBackgroundTask = BackgroundTaskThreadLocal.hasBackgroundTask();

		if (_hasBackgroundTask && (_total == -1)) {
			_total = performCount();
		}

		List<Document> documents = new ArrayList<>();
		List<Future<?>> futures = new ArrayList<>();

		Throwable throwable1 = null;

		try {
			long previousPrimaryKey = -1;

			while (true) {
				long lastPrimaryKey;

				try {
					lastPrimaryKey = _performActions(
						_createPaginatedDynamicQuery(previousPrimaryKey),
						documents, futures);
				}
				finally {
					_indexInterval(documents);
				}

				if (lastPrimaryKey < 0) {
					break;
				}

				previousPrimaryKey = lastPrimaryKey;
			}
		}
		catch (Throwable throwable2) {
			throwable1 = throwable2;
		}
		finally {
			_actionsCompleted(futures, throwable1);
		}
	}

	public long performCount() {
		String cacheKey = "performCount#" + _modelClass.getName();

		if (_cacheKeySuffix != null) {
			cacheKey = cacheKey + _cacheKeySuffix;
		}

		Long cachedCount = ReindexCacheThreadLocal.getGlobalReindexCache(
			() -> -1, cacheKey, count -> _performCount());

		if (cachedCount != null) {
			return cachedCount;
		}

		return _performCount();
	}

	public void setAddCriteriaMethod(
		Consumer<DynamicQuery> addCriteriaConsumer) {

		_addCriteriaConsumer = addCriteriaConsumer;
	}

	public void setBaseLocalService(BaseLocalService baseLocalService) {
		_baseLocalService = baseLocalService;

		Class<?> clazz = _baseLocalService.getClass();

		try {
			_dynamicQueryMethod = clazz.getMethod(
				"dynamicQuery", DynamicQuery.class);
			_dynamicQueryCountMethod = clazz.getMethod(
				"dynamicQueryCount", DynamicQuery.class, Projection.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new SystemException(noSuchMethodException);
		}
	}

	public void setCacheKeySuffix(String cacheKeySuffix) {
		_cacheKeySuffix = "#" + cacheKeySuffix;
	}

	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setInterval(int interval) {
		_interval = interval;
	}

	public void setModelClass(Class<?> modelClass) {
		_modelClass = modelClass;
	}

	public <T extends BaseModel<T>> void setPerformActionMethod(
		UnsafeFunction<T, Document, PortalException>
			performActionUnsafeFunction) {

		_performActionUnsafeFunction = performActionUnsafeFunction;
	}

	public void setPrimaryKeyPropertyName(String primaryKeyPropertyName) {
		_primaryKeyPropertyName = primaryKeyPropertyName;
	}

	private void _actionsCompleted(
		List<Future<?>> futures, Throwable throwable) {

		for (Future<?> future : futures) {
			try {
				future.get();
			}
			catch (ExecutionException executionException) {
				if (throwable == null) {
					throwable = executionException.getCause();
				}
				else {
					throwable.addSuppressed(executionException.getCause());
				}
			}
			catch (InterruptedException interruptedException) {
				if (throwable == null) {
					throwable = interruptedException;
				}
				else {
					throwable.addSuppressed(interruptedException);
				}
			}
		}

		try {
			if (throwable != null) {
				ReflectionUtil.throwException(throwable);
			}

			IndexWriterHelper indexWriterHelper =
				_indexWriterHelperProxySnapshot.get();

			indexWriterHelper.commit(_companyId);
		}
		catch (SearchException searchException) {
			ReflectionUtil.throwException(searchException);
		}
		finally {
			if (_hasBackgroundTask) {
				ReindexStatusMessageSenderUtil.sendStatusMessage(
					_modelClass.getName(), _total, _total);
			}
		}
	}

	private void _addCriteria(DynamicQuery dynamicQuery) {
		if (_addCriteriaConsumer != null) {
			_addCriteriaConsumer.accept(dynamicQuery);
		}
	}

	private void _addDefaultCriteria(DynamicQuery dynamicQuery) {
		if (!PropsValues.DATABASE_PARTITION_ENABLED && (_companyId > 0)) {
			Property property = PropertyFactoryUtil.forName("companyId");

			dynamicQuery.add(property.eq(_companyId));
		}
	}

	private void _addDocument(Document document, List<Document> documents)
		throws PortalException {

		if (document == null) {
			return;
		}

		documents.add(document);

		if (documents.size() >= _interval) {
			_indexInterval(documents);
		}
	}

	private DynamicQuery _createDynamicQuery() {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_modelClass, _classLoader);

		_addDefaultCriteria(dynamicQuery);

		_addCriteria(dynamicQuery);

		return dynamicQuery;
	}

	private DynamicQuery _createPaginatedDynamicQuery(long previousPrimaryKey) {
		DynamicQuery dynamicQuery = _createDynamicQuery();

		Property property = PropertyFactoryUtil.forName(
			_primaryKeyPropertyName);

		dynamicQuery.add(property.gt(previousPrimaryKey));

		dynamicQuery.addOrder(OrderFactoryUtil.asc(_primaryKeyPropertyName));
		dynamicQuery.setLimit(0, _interval);

		return dynamicQuery;
	}

	private void _dispatchPerformActions(
		List<Object> objects, List<Future<?>> futures) {

		long backgroundTaskId = BackgroundTaskThreadLocal.getBackgroundTaskId();
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		ExecutorService executorService =
			SearchEngineHelperUtil.getDocumentsProducerExecutorService();

		futures.add(
			executorService.submit(
				ReindexCacheThreadLocal.wrapCallable(
					new CompanyInheritableThreadLocalCallable<>(
						() -> {
							try (SafeCloseable safeCloseable1 =
									BackgroundTaskThreadLocal.
										setBackgroundTaskIdWithSafeCloseable(
											backgroundTaskId);
								SafeCloseable safeCloseable2 =
									CTCollectionThreadLocal.
										setCTCollectionIdWithSafeCloseable(
											ctCollectionId);
								SafeCloseable safeCloseable3 =
									SearchContext.openBatchMode(false)) {

								List<Document> documents = new ArrayList<>();

								_performActionsWithDefaultCTSQLMode(
									objects, documents);

								_indexInterval(documents);
							}
							catch (Throwable throwable) {
								ReflectionUtil.throwException(throwable);
							}

							return null;
						}))));
	}

	private void _indexInterval(List<Document> documents)
		throws PortalException {

		if (documents.isEmpty()) {
			return;
		}

		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.updateDocuments(_companyId, documents, false);

		if (_hasBackgroundTask) {
			ReindexStatusMessageSenderUtil.sendStatusMessage(
				_modelClass.getName(), _count.addAndGet(documents.size()),
				_total);
		}

		documents.clear();
	}

	private long _performActions(
			DynamicQuery dynamicQuery, List<Document> documents,
			List<Future<?>> futures)
		throws Throwable {

		List<Object> objects = (List<Object>)_dynamicQueryMethod.invoke(
			_baseLocalService, dynamicQuery);

		if (objects.isEmpty()) {
			return -1L;
		}

		long lastPrimaryKey = -1L;

		if (objects.size() >= _interval) {
			BaseModel<?> baseModel = (BaseModel<?>)objects.get(
				objects.size() - 1);

			lastPrimaryKey = (Long)baseModel.getPrimaryKeyObj();

			if (SearchContext.isBatchMode()) {
				_dispatchPerformActions(objects, futures);

				return lastPrimaryKey;
			}
		}

		_performActionsWithDefaultCTSQLMode(objects, documents);

		return lastPrimaryKey;
	}

	private void _performActionsWithCTCollection(
			List<Object> objects, List<Document> documents)
		throws Throwable {

		long currentCTCollectionId =
			CTCollectionThreadLocal.getCTCollectionId();

		for (Object object : objects) {
			long ctCollectionId = 0;

			if (object instanceof CTModel) {
				CTModel<?> ctModel = (CTModel<?>)object;

				ctCollectionId = ctModel.getCtCollectionId();
			}

			if (ctCollectionId == currentCTCollectionId) {
				_addDocument(
					(Document)_performActionUnsafeFunction.apply(object),
					documents);
			}
			else {
				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ctCollectionId)) {

					_addDocument(
						(Document)_performActionUnsafeFunction.apply(object),
						documents);
				}
			}
		}
	}

	private void _performActionsWithDefaultCTSQLMode(
			List<Object> objects, List<Document> documents)
		throws Throwable {

		CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
			CTSQLModeThreadLocal.getCTSQLMode();

		if (ctSQLMode == CTSQLModeThreadLocal.CTSQLMode.DEFAULT) {
			_performActionsWithCTCollection(objects, documents);
		}
		else {
			try (SafeCloseable safeCloseable =
					CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT)) {

				_performActionsWithCTCollection(objects, documents);
			}
		}
	}

	private long _performCount() {
		try {
			return (Long)_dynamicQueryCountMethod.invoke(
				_baseLocalService, _createDynamicQuery(),
				ProjectionFactoryUtil.rowCount());
		}
		catch (Exception exception) {
			throw ReflectionUtil.<RuntimeException>throwException(exception);
		}
	}

	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperProxySnapshot = new Snapshot<>(
			IndexableActionableDynamicQuery.class, IndexWriterHelper.class);

	private Consumer<DynamicQuery> _addCriteriaConsumer;
	private BaseLocalService _baseLocalService;
	private String _cacheKeySuffix;
	private ClassLoader _classLoader;
	private long _companyId;
	private final AtomicLong _count = new AtomicLong();
	private Method _dynamicQueryCountMethod;
	private Method _dynamicQueryMethod;
	private boolean _hasBackgroundTask;
	private int _interval = Indexer.DEFAULT_INTERVAL;
	private Class<?> _modelClass;

	@SuppressWarnings("rawtypes")
	private UnsafeFunction _performActionUnsafeFunction;

	private String _primaryKeyPropertyName;
	private long _total = -1;

}