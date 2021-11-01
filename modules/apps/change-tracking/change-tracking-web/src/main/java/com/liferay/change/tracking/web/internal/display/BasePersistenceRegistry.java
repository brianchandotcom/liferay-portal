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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;
import com.liferay.portal.spring.transaction.TransactionExecutor;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = BasePersistenceRegistry.class)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BasePersistenceRegistry {

	public <T extends BaseModel<T>> T fetchBaseModel(
		long classNameId, long primaryKey) {

		return _applyBasePersistence(
			classNameId,
			basePersistence -> (T)basePersistence.fetchByPrimaryKey(
				primaryKey));
	}

	public <T extends BaseModel<T>> Map<Serializable, T> fetchBaseModelMap(
		long classNameId, Set<Long> primaryKeys) {

		return _applyBasePersistence(
			classNameId,
			basePersistence ->
				(Map<Serializable, T>)basePersistence.fetchByPrimaryKeys(
					(Set)primaryKeys));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_tableReferenceDefinitionServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<TableReferenceDefinition<?>>)
					(Class<?>)TableReferenceDefinition.class,
				null,
				(serviceReference, emitter) -> {
					TableReferenceDefinition<?> tableReferenceDefinition =
						bundleContext.getService(serviceReference);

					BasePersistence<?> basePersistence =
						tableReferenceDefinition.getBasePersistence();

					emitter.emit(
						_classNameLocalService.getClassNameId(
							basePersistence.getModelClass()));
				});

		_transactionExecutorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, TransactionExecutor.class, null,
				(serviceReference, emitter) -> {
					Bundle bundle = serviceReference.getBundle();

					emitter.emit(bundle.getBundleId());
				});
	}

	@Deactivate
	protected void deactivate() {
		_transactionExecutorServiceTrackerMap.close();

		_tableReferenceDefinitionServiceTrackerMap.close();
	}

	private <T extends BaseModel<T>, R> R _applyBasePersistence(
		long classNameId, Function<BasePersistence<T>, R> function) {

		TableReferenceDefinition<?> tableReferenceDefinition =
			_tableReferenceDefinitionServiceTrackerMap.getService(classNameId);

		BasePersistence<T> basePersistence =
			(BasePersistence<T>)tableReferenceDefinition.getBasePersistence();

		TransactionExecutor transactionExecutor = _portalTransactionExecutor;

		Bundle bundle = FrameworkUtil.getBundle(basePersistence.getClass());

		if (bundle != null) {
			transactionExecutor =
				_transactionExecutorServiceTrackerMap.getService(
					bundle.getBundleId());

			if (transactionExecutor == null) {
				throw new IllegalStateException(
					"No TransactionExecutor for " + tableReferenceDefinition);
			}
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			return transactionExecutor.execute(
				_transactionAttributeAdapter,
				() -> function.apply(basePersistence));
		}
		catch (Throwable throwable) {
			return ReflectionUtil.throwException(throwable);
		}
	}

	private static final TransactionExecutor _portalTransactionExecutor =
		(TransactionExecutor)PortalBeanLocatorUtil.locate(
			"transactionExecutor");
	private static final TransactionAttributeAdapter
		_transactionAttributeAdapter = new TransactionAttributeAdapter(
			TransactionAttributeBuilder.build(
				BasePersistenceRegistry.class.getAnnotation(
					Transactional.class)));

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, TableReferenceDefinition<?>>
		_tableReferenceDefinitionServiceTrackerMap;
	private ServiceTrackerMap<Long, TransactionExecutor>
		_transactionExecutorServiceTrackerMap;

}