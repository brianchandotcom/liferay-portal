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
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PropsValues;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

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

		if (size >= getInterval()) {
			indexInterval();
		}
		else if ((size % _STATUS_INTERVAL) == 0) {
			sendStatusMessage(size);
		}
	}

	public ActionableDynamicQuery.AddCriteriaMethod getAddCriteriaMethod() {
		return _addCriteriaMethod;
	}

	public ActionableDynamicQuery.AddOrderCriteriaMethod
		getAddOrderCriteriaMethod() {

		return _addOrderCriteriaMethod;
	}

	public ActionableDynamicQuery.PerformActionMethod<?>
		getPerformActionMethod() {

		return _performActionMethod;
	}

	public ActionableDynamicQuery.PerformCountMethod getPerformCountMethod() {
		return _performCountMethod;
	}

	public boolean isParallel() {
		return _parallel;
	}

	public void performActions() {
		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			try {
				_total = performCount();
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		try {
			try {
				long previousPrimaryKey = -1;

				while (true) {
					long lastPrimaryKey = doPerformActions(previousPrimaryKey);

					if (lastPrimaryKey < 0) {
						break;
					}

					intervalCompleted(previousPrimaryKey, lastPrimaryKey);

					previousPrimaryKey = lastPrimaryKey;
				}
			}
			finally {
				_offset = 0;

				actionsCompleted();
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		finally {
			_count = _total;

			sendStatusMessage();
		}
	}

	public long performCount() throws PortalException {
		if (_performCountMethod != null) {
			return _performCountMethod.performCount();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_modelClass, _classLoader);

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		return (Long)executeDynamicQuery(
			_dynamicQueryCountMethod, dynamicQuery, getCountProjection());
	}

	public void setAddCriteriaMethod(
		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod) {

		_addCriteriaMethod = addCriteriaMethod;
	}

	public void setAddOrderCriteriaMethod(
		ActionableDynamicQuery.AddOrderCriteriaMethod addOrderCriteriaMethod) {

		_addOrderCriteriaMethod = addOrderCriteriaMethod;
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

	public void setGroupIdPropertyName(String groupIdPropertyName) {
		_groupIdPropertyName = groupIdPropertyName;
	}

	public void setInterval(int interval) {
		_interval = interval;
	}

	public void setModelClass(Class<?> modelClass) {
		_modelClass = modelClass;
	}

	public void setParallel(boolean parallel) {
		if (_parallel == parallel) {
			return;
		}

		_parallel = parallel;

		if (parallel) {
			_documents = new ConcurrentLinkedDeque<>();
		}
	}

	public void setPerformActionMethod(
		ActionableDynamicQuery.PerformActionMethod<?> performActionMethod) {

		_performActionMethod = performActionMethod;
	}

	public void setPerformCountMethod(
		ActionableDynamicQuery.PerformCountMethod performCountMethod) {

		_performCountMethod = performCountMethod;
	}

	public void setPrimaryKeyPropertyName(String primaryKeyPropertyName) {
		_primaryKeyPropertyName = primaryKeyPropertyName;
	}

	public void setTransactionConfig(TransactionConfig transactionConfig) {
		_transactionConfig = transactionConfig;
	}

	protected void actionsCompleted() throws PortalException {
		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.commit(getCompanyId());
	}

	protected void addCriteria(DynamicQuery dynamicQuery) {
		if (_addCriteriaMethod != null) {
			_addCriteriaMethod.addCriteria(dynamicQuery);
		}
	}

	protected void addDefaultCriteria(DynamicQuery dynamicQuery) {
		if (!PropsValues.DATABASE_PARTITION_ENABLED && (_companyId > 0)) {
			Property property = PropertyFactoryUtil.forName("companyId");

			dynamicQuery.add(property.eq(_companyId));
		}

		if (_groupId > 0) {
			Property property = PropertyFactoryUtil.forName(
				_groupIdPropertyName);

			dynamicQuery.add(property.eq(_groupId));
		}
	}

	protected void addOrderCriteria(DynamicQuery dynamicQuery) {
		if (_addOrderCriteriaMethod == null) {
			dynamicQuery.addOrder(
				OrderFactoryUtil.asc(_primaryKeyPropertyName));
		}
		else {
			_addOrderCriteriaMethod.addOrderCriteria(dynamicQuery);
		}
	}

	protected void doPerformActions(List<Object> objects) throws Exception {
		CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
			CTSQLModeThreadLocal.getCTSQLMode();

		if (ctSQLMode == CTSQLModeThreadLocal.CTSQLMode.DEFAULT) {
			_performActions(objects);
		}
		else {
			try (SafeCloseable safeCloseable =
					CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
						CTSQLModeThreadLocal.CTSQLMode.DEFAULT)) {

				_performActions(objects);
			}
		}
	}

	protected long doPerformActions(long previousPrimaryKey)
		throws PortalException {

		try {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
				_modelClass, _classLoader);

			if (_addOrderCriteriaMethod == null) {
				Property property = PropertyFactoryUtil.forName(
					_primaryKeyPropertyName);

				dynamicQuery.add(property.gt(previousPrimaryKey));

				dynamicQuery.setLimit(0, _interval);
			}
			else {
				dynamicQuery.setLimit(_offset, _interval + _offset);
			}

			addDefaultCriteria(dynamicQuery);

			addCriteria(dynamicQuery);

			addOrderCriteria(dynamicQuery);

			try {
				TransactionConfig transactionConfig = getTransactionConfig();

				if (transactionConfig == null) {
					return _performActions(dynamicQuery);
				}

				return TransactionInvokerUtil.invoke(
					transactionConfig, () -> _performActions(dynamicQuery));
			}
			catch (Throwable throwable) {
				if (throwable instanceof PortalException) {
					throw (PortalException)throwable;
				}

				if (throwable instanceof SystemException) {
					throw (SystemException)throwable;
				}

				throw new SystemException(throwable);
			}
		}
		finally {
			indexInterval();
		}
	}

	protected Object executeDynamicQuery(
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

	protected long getCompanyId() {
		return _companyId;
	}

	protected Projection getCountProjection() {
		return ProjectionFactoryUtil.rowCount();
	}

	protected int getInterval() {
		return _interval;
	}

	protected Class<?> getModelClass() {
		return _modelClass;
	}

	protected TransactionConfig getTransactionConfig() {
		return _transactionConfig;
	}

	protected void indexInterval() throws PortalException {
		if ((_documents == null) || _documents.isEmpty()) {
			return;
		}

		IndexWriterHelper indexWriterHelper =
			_indexWriterHelperProxySnapshot.get();

		indexWriterHelper.updateDocuments(getCompanyId(), _documents, false);

		_count += _documents.size();

		_documents.clear();

		sendStatusMessage();
	}

	/**
	 * @throws PortalException
	 */
	protected void intervalCompleted(long startPrimaryKey, long endPrimaryKey)
		throws PortalException {
	}

	protected void performAction(Object object) throws PortalException {
		if (_performActionMethod != null) {
			_performActionMethod.performAction(object);
		}
	}

	protected void sendStatusMessage() {
		sendStatusMessage(0);
	}

	protected void sendStatusMessage(long documentIntervalCount) {
		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		Class<?> modelClass = getModelClass();

		ReindexStatusMessageSenderUtil.sendStatusMessage(
			modelClass.getName(), _count + documentIntervalCount, _total);
	}

	private long _performActions(DynamicQuery dynamicQuery) throws Exception {
		List<Object> objects = (List<Object>)executeDynamicQuery(
			_dynamicQueryMethod, dynamicQuery);

		_offset += objects.size();

		if (objects.isEmpty()) {
			return -1L;
		}

		long lastPrimaryKey = -1L;

		if (objects.size() >= _interval) {
			BaseModel<?> baseModel = (BaseModel<?>)objects.get(
				objects.size() - 1);

			lastPrimaryKey = (Long)baseModel.getPrimaryKeyObj();
		}

		doPerformActions(objects);

		return lastPrimaryKey;
	}

	private void _performActions(List<Object> objects) throws Exception {
		long currentCTCollectionId =
			CTCollectionThreadLocal.getCTCollectionId();

		for (Object object : objects) {
			long ctCollectionId = 0;

			if (object instanceof CTModel) {
				CTModel<?> ctModel = (CTModel<?>)object;

				ctCollectionId = ctModel.getCtCollectionId();
			}

			if (ctCollectionId == currentCTCollectionId) {
				performAction(object);
			}
			else {
				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ctCollectionId)) {

					performAction(object);
				}
			}
		}
	}

	private static final long _STATUS_INTERVAL = 100;

	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperProxySnapshot = new Snapshot<>(
			IndexableActionableDynamicQuery.class, IndexWriterHelper.class);

	private ActionableDynamicQuery.AddCriteriaMethod _addCriteriaMethod;
	private ActionableDynamicQuery.AddOrderCriteriaMethod
		_addOrderCriteriaMethod;
	private BaseLocalService _baseLocalService;
	private ClassLoader _classLoader;
	private long _companyId;
	private long _count;
	private Collection<Document> _documents = new ArrayList<>();
	private Method _dynamicQueryCountMethod;
	private Method _dynamicQueryMethod;
	private long _groupId;
	private String _groupIdPropertyName = "groupId";
	private int _interval = Indexer.DEFAULT_INTERVAL;
	private Class<?> _modelClass;
	private int _offset;
	private boolean _parallel;

	@SuppressWarnings("rawtypes")
	private ActionableDynamicQuery.PerformActionMethod _performActionMethod;

	private ActionableDynamicQuery.PerformCountMethod _performCountMethod;
	private String _primaryKeyPropertyName;
	private long _total;
	private TransactionConfig _transactionConfig;

}