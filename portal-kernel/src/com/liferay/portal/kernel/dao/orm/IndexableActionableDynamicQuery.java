/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.lang.SafeCloseable;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

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

		long size = _documents.size();

		if (size >= _interval) {
			_indexInterval();
		}
		else if ((size % _STATUS_INTERVAL) == 0) {
			_sendStatusMessage(size);
		}
	}

	public void performActions() {
		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			try {
				_total = _performCount();
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
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
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		finally {
			_count = _total;

			_sendStatusMessage();
		}
	}

	public void setAddCriteriaMethod(
		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod) {

		_addCriteriaMethod = addCriteriaMethod;
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

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setInterval(int interval) {
		_interval = interval;
	}

	public void setModelClass(Class<?> modelClass) {
		_modelClass = modelClass;
	}

	public void setPerformActionMethod(
		ActionableDynamicQuery.PerformActionMethod<?> performActionMethod) {

		_performActionMethod = performActionMethod;
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
		if (_addCriteriaMethod != null) {
			_addCriteriaMethod.addCriteria(dynamicQuery);
		}
	}

	private void _addDefaultCriteria(DynamicQuery dynamicQuery) {
		if (!PropsValues.DATABASE_PARTITION_ENABLED && (_companyId > 0)) {
			Property property = PropertyFactoryUtil.forName("companyId");

			dynamicQuery.add(property.eq(_companyId));
		}

		if (_groupId > 0) {
			Property property = PropertyFactoryUtil.forName("groupId");

			dynamicQuery.add(property.eq(_groupId));
		}
	}

	private Object _executeDynamicQuery(
			Method dynamicQueryMethod, Object... arguments)
		throws PortalException {

		try {
			return dynamicQueryMethod.invoke(_baseLocalService, arguments);
		}
		catch (InvocationTargetException invocationTargetException) {
			Throwable throwable = invocationTargetException.getCause();

			if (throwable instanceof PortalException) {
				throw (PortalException)throwable;
			}
			else if (throwable instanceof SystemException) {
				throw (SystemException)throwable;
			}

			throw new SystemException(invocationTargetException);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

	private void _performAction(Object object) throws PortalException {
		if (_performActionMethod != null) {
			_performActionMethod.performAction(object);
		}
	}

	private long _performActions(DynamicQuery dynamicQuery) throws Exception {
		List<Object> objects = (List<Object>)_executeDynamicQuery(
			_dynamicQueryMethod, dynamicQuery);

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

	private void _performActions(List<Object> objects) throws Exception {
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

	private long _performActions(long previousPrimaryKey)
		throws PortalException {

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

			try {
				return _performActions(dynamicQuery);
			}
			catch (PortalException | SystemException exception) {
				throw exception;
			}
			catch (Exception exception) {
				throw new SystemException(exception);
			}
		}
		finally {
			_indexInterval();
		}
	}

	private void _performActionsWithCT(List<Object> objects) throws Exception {
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

	private long _performCount() throws PortalException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_modelClass, _classLoader);

		_addDefaultCriteria(dynamicQuery);

		_addCriteria(dynamicQuery);

		return (Long)_executeDynamicQuery(
			_dynamicQueryCountMethod, dynamicQuery,
			ProjectionFactoryUtil.rowCount());
	}

	private void _sendStatusMessage() {
		_sendStatusMessage(0);
	}

	private void _sendStatusMessage(long documentIntervalCount) {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			_modelClass.getName(), _count + documentIntervalCount, _total);
	}

	private static final long _STATUS_INTERVAL = 100;

	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperProxySnapshot = new Snapshot<>(
			IndexableActionableDynamicQuery.class, IndexWriterHelper.class);

	private ActionableDynamicQuery.AddCriteriaMethod _addCriteriaMethod;
	private BaseLocalService _baseLocalService;
	private ClassLoader _classLoader;
	private long _companyId;
	private long _count;
	private final List<Document> _documents = new ArrayList<>();
	private Method _dynamicQueryCountMethod;
	private Method _dynamicQueryMethod;
	private long _groupId;
	private int _interval = Indexer.DEFAULT_INTERVAL;
	private Class<?> _modelClass;

	@SuppressWarnings("rawtypes")
	private ActionableDynamicQuery.PerformActionMethod _performActionMethod;

	private String _primaryKeyPropertyName;
	private long _total;

}