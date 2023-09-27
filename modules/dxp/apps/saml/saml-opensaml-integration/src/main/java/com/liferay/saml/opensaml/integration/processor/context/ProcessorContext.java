/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.processor.context;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface ProcessorContext<M extends BaseModel<M>> {

	public <T extends BaseModel<T>> Bind<T> bind(
		Function<M, T> modelGetterFunction, int processingIndex,
		String publicIdentifier, UpdateFunction<T> updateFunction);

	public Bind<M> bind(int processingIndex, UpdateFunction<M> updateFunction);

	public <V> V getValue(Class<V> clazz, String fieldExpression);

	public <V> V[] getValueArray(Class<V> clazz, String fieldExpression);

	public boolean isDefined(Class<?> clazz, String fieldExpression);

	public interface Bind<T extends BaseModel<T>> {

		public void handleUnsafeStringArray(
			String fieldExpression,
			UnsafeBiConsumer<T, String[], ?> unsafeBiConsumer);

		public void mapBoolean(
			String fieldExpression, BiConsumer<T, Boolean> biConsumer);

		public void mapBooleanArray(
			String fieldExpression, BiConsumer<T, boolean[]> biConsumer);

		public void mapLong(
			String fieldExpression, BiConsumer<T, Long> biConsumer);

		public void mapLongArray(
			String fieldExpression, BiConsumer<T, long[]> biConsumer);

		public void mapString(
			String fieldExpression, BiConsumer<T, String> biConsumer);

		public void mapStringArray(
			String fieldExpression, BiConsumer<T, String[]> biConsumer);

		public void mapUnsafeString(
			String fieldExpression,
			UnsafeBiConsumer<T, String, ?> unsafeBiConsumer);

	}

	public interface UpdateFunction<T> {

		public T update(T oldModel, T newModel, ServiceContext serviceContext)
			throws PortalException;

	}

}