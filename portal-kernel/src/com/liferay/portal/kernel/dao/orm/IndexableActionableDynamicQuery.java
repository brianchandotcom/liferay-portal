/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.function.UnsafeConsumer;
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
import com.liferay.portal.kernel.search.background.task.ReindexStatusMessageSenderUtil;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.util.PropsValues;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Andrew Betts
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IndexableActionableDynamicQuery {

	public void addDocument(Document document) throws PortalException {
		if (document == null) {
			return;
		}

		_documents.add(document);

		if (_documents.size() >= _interval) {
			_indexInterval();
		}
	}

	public void performActions() {
		if (_performActionUnsafeConsumer == null) {
			throw new IllegalStateException(
				"performActionUnsafeConsumer is null");
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			try {
				_total = _performCount();
			}
			catch (Exception exception) {
				ReflectionUtil.throwException(exception);
			}
		}

		try {
			try {
				long previousPrimaryKey = -1;

				while (true) {
					long lastPrimaryKey = _performActions(previousPrimaryKey);

					if (lastPrimaryKey < 0) {
						break;
					}

					previousPrimaryKey = lastPrimaryKey;
				}
			}
			finally {
				_actionsCompleted();
			}
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}
		finally {
			_count = _total;

			_sendStatusMessage();
		}
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

	public void setPerformActionMethod(
		UnsafeConsumer<?, PortalException> performActionUnsafeConsumer) {

		_performActionUnsafeConsumer = performActionUnsafeConsumer;
	}

	public void setPrimaryKeyPropertyName(String primaryKeyPropertyName) {
		_primaryKeyPropertyName = primaryKeyPropertyName;
	}

	private void _actionsCompleted() throws PortalException {
		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.commit(_companyId);
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

	private void _indexInterval() throws PortalException {
		if (_documents.isEmpty()) {
			return;
		}

		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.updateDocuments(_companyId, _documents, false);

		_count += _documents.size();

		_documents.clear();

		_sendStatusMessage();
	}

	private void _performAction(Object object) throws Throwable {
		_performActionUnsafeConsumer.accept(object);
	}

	private long _performActions(DynamicQuery dynamicQuery) throws Throwable {
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
		}

		_performActions(objects);

		return lastPrimaryKey;
	}

	private void _performActions(List<Object> objects) throws Throwable {
		CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
			CTSQLModeThreadLocal.getCTSQLMode();

		if (ctSQLMode == CTSQLModeThreadLocal.CTSQLMode.DEFAULT) {
			_performActionsWithCT(objects);
		}
		else {
			try (SafeCloseable safeCloseable =
					CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT)) {

				_performActionsWithCT(objects);
			}
		}
	}

	private long _performActions(long previousPrimaryKey) throws Throwable {
		try {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
				_modelClass, _classLoader);

			Property property = PropertyFactoryUtil.forName(
				_primaryKeyPropertyName);

			dynamicQuery.add(property.gt(previousPrimaryKey));

			dynamicQuery.setLimit(0, _interval);

			_addDefaultCriteria(dynamicQuery);

			_addCriteria(dynamicQuery);

			dynamicQuery.addOrder(
				OrderFactoryUtil.asc(_primaryKeyPropertyName));

			return _performActions(dynamicQuery);
		}
		finally {
			_indexInterval();
		}
	}

	private void _performActionsWithCT(List<Object> objects) throws Throwable {
		long currentCTCollectionId =
			CTCollectionThreadLocal.getCTCollectionId();

		for (Object object : objects) {
			long ctCollectionId = 0;

			if (object instanceof CTModel) {
				CTModel<?> ctModel = (CTModel<?>)object;

				ctCollectionId = ctModel.getCtCollectionId();
			}

			if (ctCollectionId == currentCTCollectionId) {
				_performAction(object);
			}
			else {
				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ctCollectionId)) {

					_performAction(object);
				}
			}
		}
	}

	private long _performCount() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_modelClass, _classLoader);

		_addDefaultCriteria(dynamicQuery);

		_addCriteria(dynamicQuery);

		return (Long)_dynamicQueryCountMethod.invoke(
			_baseLocalService, dynamicQuery, ProjectionFactoryUtil.rowCount());
	}

	private void _sendStatusMessage() {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			_modelClass.getName(), _count, _total);
	}

	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperProxySnapshot = new Snapshot<>(
			IndexableActionableDynamicQuery.class, IndexWriterHelper.class);

	private Consumer<DynamicQuery> _addCriteriaConsumer;
	private BaseLocalService _baseLocalService;
	private ClassLoader _classLoader;
	private long _companyId;
	private long _count;
	private final List<Document> _documents = new ArrayList<>();
	private Method _dynamicQueryCountMethod;
	private Method _dynamicQueryMethod;
	private int _interval = Indexer.DEFAULT_INTERVAL;
	private Class<?> _modelClass;

	@SuppressWarnings("rawtypes")
	private UnsafeConsumer _performActionUnsafeConsumer;

	private String _primaryKeyPropertyName;
	private long _total;

}