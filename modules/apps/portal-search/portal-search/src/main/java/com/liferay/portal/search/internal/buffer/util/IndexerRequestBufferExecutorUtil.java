/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.buffer.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.indexer.BaseModelRetriever;
import com.liferay.portal.search.internal.buffer.BufferOverflowThreadLocal;
import com.liferay.portal.search.internal.buffer.IndexerRequest;
import com.liferay.portal.search.internal.buffer.IndexerRequestBuffer;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class IndexerRequestBufferExecutorUtil {

	public static void execute(IndexerRequestBuffer indexerRequestBuffer) {
		execute(indexerRequestBuffer, indexerRequestBuffer.size());
	}

	public static void execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests) {

		TransactionAttribute transactionAttribute =
			indexerRequestBuffer.getTransactionAttribute();

		if (transactionAttribute == null) {
			_execute(indexerRequestBuffer, numRequests);

			return;
		}

		TransactionConfig.Builder transactionConfigBuilder =
			new TransactionConfig.Builder();

		TransactionConfig transactionConfig =
			transactionConfigBuilder.setIsolation(
				transactionAttribute.getIsolation()
			).setPropagation(
				transactionAttribute.getPropagation()
			).setReadOnly(
				transactionAttribute.isReadOnly()
			).setStrictReadOnly(
				transactionAttribute.isStrictReadOnly()
			).build();

		try {
			TransactionInvokerUtil.invoke(
				transactionConfig,
				() -> {
					_execute(indexerRequestBuffer, numRequests);

					return null;
				});
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}
	}

	private static void _execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests) {

		Collection<IndexerRequest> completedIndexerRequests = new ArrayList<>();

		if (_log.isDebugEnabled()) {
			Collection<IndexerRequest> indexerRequests =
				indexerRequestBuffer.getIndexerRequests();

			_log.debug(
				StringBundler.concat(
					"Indexer request buffer size ", indexerRequests.size(),
					" to execute ", numRequests, " requests"));
		}

		int i = 0;

		Collection<IndexerRequest> indexerRequests =
			indexerRequestBuffer.getIndexerRequests();

		if (!BufferOverflowThreadLocal.isOverflowMode() &&
			(indexerRequests.size() > 1)) {

			indexerRequests = _mergeIndexerRequests(indexerRequests);
		}

		for (IndexerRequest indexerRequest : indexerRequests) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Executing indexer request ", i++, ": ",
						indexerRequest));
			}

			try {
				indexerRequest.execute();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to execute index request " + indexerRequest,
						exception);
				}
			}

			if (BufferOverflowThreadLocal.isOverflowMode()) {
				completedIndexerRequests.add(indexerRequest);

				if (completedIndexerRequests.size() == numRequests) {
					break;
				}
			}
		}

		if (BufferOverflowThreadLocal.isOverflowMode()) {
			for (IndexerRequest indexerRequest : completedIndexerRequests) {
				indexerRequestBuffer.remove(indexerRequest);
			}
		}
		else {
			indexerRequestBuffer.clear();

			IndexWriterHelper indexWriterHelper =
				_indexWriterHelperSnapshot.get();

			if (indexWriterHelper == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Index writer helper is null");
				}

				return;
			}

			try {
				indexWriterHelper.commit();
			}
			catch (SearchException searchException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to commit search engine", searchException);
				}
			}
		}
	}

	private static List<IndexerRequest> _mergeIndexerRequests(
		Collection<IndexerRequest> indexerRequests) {

		List<IndexerRequest> mergedIndexerRequests = new ArrayList<>();

		Map<Indexer<?>, Map<Method, Set<ClassedModel>>>
			methodClassedModelSetMap = new HashMap<>();

		BaseModelRetriever baseModelRetriever =
			_baseModelRetrieverSnapshot.get();

		for (IndexerRequest indexerRequest : indexerRequests) {
			Method method = indexerRequest.getMethod();

			String methodName = method.getName();

			if (!methodName.equals("reindex")) {
				mergedIndexerRequests.add(indexerRequest);

				continue;
			}

			ClassedModel classedModel = null;

			try {
				BaseModel<?> baseModel = baseModelRetriever.fetchBaseModel(
					indexerRequest.getModelClassName(),
					indexerRequest.getModelPrimaryKey());

				if (baseModel == null) {
					mergedIndexerRequests.add(indexerRequest);

					continue;
				}

				classedModel = (ClassedModel)baseModel;
			}
			catch (SystemException systemException) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to fetch base model", systemException);
				}

				mergedIndexerRequests.add(indexerRequest);

				continue;
			}

			MethodKey methodKey = new MethodKey(
				Indexer.class, "reindex", Collection.class);

			try {
				method = methodKey.getMethod();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get reindex(Collection) method", exception);
				}
			}

			Map<Method, Set<ClassedModel>> classedModelSetMap =
				methodClassedModelSetMap.get(indexerRequest.getIndexer());

			if (MapUtil.isEmpty(classedModelSetMap)) {
				classedModelSetMap = new HashMap<>();
			}

			Set<ClassedModel> classedModels = classedModelSetMap.get(method);

			if (SetUtil.isEmpty(classedModels)) {
				classedModels = new HashSet<>();
			}

			classedModels.add(classedModel);

			classedModelSetMap.put(method, classedModels);

			methodClassedModelSetMap.put(
				indexerRequest.getIndexer(), classedModelSetMap);
		}

		for (Map.Entry<Indexer<?>, Map<Method, Set<ClassedModel>>> entry1 :
				methodClassedModelSetMap.entrySet()) {

			Map<Method, Set<ClassedModel>> classedModelSetMap =
				entry1.getValue();
			Indexer<?> indexer = entry1.getKey();

			for (Map.Entry<Method, Set<ClassedModel>> entry2 :
					classedModelSetMap.entrySet()) {

				Set<ClassedModel> classedModels = entry2.getValue();
				Method method = entry2.getKey();

				mergedIndexerRequests.add(
					new IndexerRequest(method, classedModels, indexer));
			}
		}

		return mergedIndexerRequests;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRequestBufferExecutorUtil.class);

	private static final Snapshot<BaseModelRetriever>
		_baseModelRetrieverSnapshot = new Snapshot<>(
			IndexerRequestBufferExecutorUtil.class, BaseModelRetriever.class,
			null, true);
	private static final Snapshot<IndexWriterHelper>
		_indexWriterHelperSnapshot = new Snapshot<>(
			IndexerRequestBufferExecutorUtil.class, IndexWriterHelper.class,
			null, true);

}