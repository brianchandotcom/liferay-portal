/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.util.PortalInstances;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class IndexableAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Indexable indexable = (Indexable)annotations.get(Indexable.class);

		if (indexable == null) {
			return null;
		}

		Class<?> returnType = method.getReturnType();

		if (!BaseModel.class.isAssignableFrom(returnType)) {
			if (_log.isWarnEnabled()) {
				_log.warn(method + " does not have a valid return type");
			}

			return null;
		}

		return new IndexableContext(
			indexable.callbackKey(), returnType.getName(), indexable.type(),
			_getServiceContextParameterIndex(method),
			(Transactional)annotations.get(Transactional.class));
	}

	@Override
	protected void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {

		if (result == null) {
			return;
		}

		if (PortalInstances.isCurrentCompanyInDeletionProcess() ||
			IndexWriterHelperUtil.isIndexReadOnly()) {

			if (_log.isDebugEnabled()) {
				if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
					_log.debug(
						"Skip indexing because company delete is in process");
				}
				else if (IndexWriterHelperUtil.isIndexReadOnly()) {
					_log.debug("Skip indexing because the index is read only");
				}
			}

			return;
		}

		IndexableContext indexableContext =
			aopMethodInvocation.getAdviceMethodContext();

		String name = indexableContext._name;

		Indexer<Object> indexer = IndexerRegistryUtil.getIndexer(name);

		if (indexer != null) {
			_reindex(indexer, indexableContext, arguments, result);

			return;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				Indexer<Object> curIndexer = IndexerRegistryUtil.getIndexer(
					name);

				if (curIndexer == null) {
					return null;
				}

				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setWithSafeCloseable(companyId)) {

					_reindex(curIndexer, indexableContext, arguments, result);
				}

				return null;
			});
	}

	private void _doReindex(
			Indexer<Object> indexer, IndexableContext indexableContext,
			Object[] arguments, Object result)
		throws SearchException {

		if (IndexWriterHelperUtil.isIndexReadOnly(indexer.getClassName())) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping indexing read only index for " +
						indexer.getClassName());
			}

			return;
		}

		int serviceContextIndex = indexableContext._serviceContextIndex;

		if (serviceContextIndex >= 0) {
			ServiceContext serviceContext =
				(ServiceContext)arguments[serviceContextIndex];

			if ((serviceContext != null) &&
				!serviceContext.isIndexingEnabled()) {

				return;
			}
		}

		if (indexableContext._indexableType == IndexableType.DELETE) {
			indexer.delete(result);
		}
		else {
			Indexable.Callback callback = _callbacks.getService(
				indexableContext._callbackKey);

			if (callback == null) {
				indexer.reindex(result);
			}
			else {
				callback.reindex((BaseModel<?>)result);
			}
		}
	}

	private int _getServiceContextParameterIndex(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = parameterTypes.length - 1; i >= 0; i--) {
			if (ServiceContext.class.isAssignableFrom(parameterTypes[i])) {
				return i;
			}
		}

		return -1;
	}

	private void _reindex(
			Indexer<Object> indexer, IndexableContext indexableContext,
			Object[] arguments, Object result)
		throws SearchException {

		if (indexableContext._transactionAttribute == null) {
			_doReindex(indexer, indexableContext, arguments, result);

			return;
		}

		TransactionConfig.Builder transactionConfigBuilder =
			new TransactionConfig.Builder();

		TransactionConfig transactionConfig =
			transactionConfigBuilder.setIsolation(
				indexableContext._transactionAttribute.getIsolation()
			).setPropagation(
				indexableContext._transactionAttribute.getPropagation()
			).setReadOnly(
				indexableContext._transactionAttribute.isReadOnly()
			).setStrictReadOnly(
				indexableContext._transactionAttribute.isStrictReadOnly()
			).build();

		try {
			TransactionInvokerUtil.invoke(
				transactionConfig,
				() -> {
					_doReindex(indexer, indexableContext, arguments, result);

					return null;
				});
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexableAdvice.class);

	private static final ServiceTrackerMap<String, Indexable.Callback>
		_callbacks = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), Indexable.Callback.class,
			"key");

	private static class IndexableContext {

		private IndexableContext(
			String callbackKey, String name, IndexableType indexableType,
			int serviceContextIndex, Transactional transactional) {

			_callbackKey = callbackKey;
			_name = name;
			_indexableType = indexableType;
			_serviceContextIndex = serviceContextIndex;

			org.springframework.transaction.interceptor.TransactionAttribute
				transactionAttribute = TransactionAttributeBuilder.build(
					transactional);

			if (transactionAttribute == null) {
				_transactionAttribute = null;
			}
			else {
				_transactionAttribute = new TransactionAttributeAdapter(
					transactionAttribute);
			}
		}

		private final String _callbackKey;
		private final IndexableType _indexableType;
		private final String _name;
		private final int _serviceContextIndex;
		private final TransactionAttribute _transactionAttribute;

	}

}