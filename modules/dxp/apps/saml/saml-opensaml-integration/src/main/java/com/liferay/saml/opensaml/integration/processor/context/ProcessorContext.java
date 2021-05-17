/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.processor.context;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Stian Sigvartsen
 */
public interface ProcessorContext<M extends BaseModel<M>> {

	public Bind<M> bind(int processingIndex, UpdateFunction<M> updateFunction);

	public <T extends BaseModel<T>> Bind<T> bind(
		String publicIdentifier, Function<M, T> modelGetter,
		int processingIndex, UpdateFunction<T> updateFunction);

	public <V> V getValue(String fieldExpression, Class<V> clazz);

	public <V> V[] getValueArray(String fieldExpression, Class<V> clazz);

	public interface Bind<T extends BaseModel<T>> {

		public void handleUnsafeStringArray(
			String fieldExpression, UnsafeBiConsumer<T, String[], ?> consumer);

		public void mapBoolean(
			String fieldExpression, BiConsumer<T, Boolean> consumer);

		public void mapBooleanArray(
			String fieldExpression, BiConsumer<T, boolean[]> consumer);

		public void mapLong(
			String fieldExpression, BiConsumer<T, Long> consumer);

		public void mapLongArray(
			String fieldExpression, BiConsumer<T, long[]> consumer);

		public void mapString(
			String fieldExpression, BiConsumer<T, String> consumer);

		public void mapStringArray(
			String fieldExpression, BiConsumer<T, String[]> consumer);

		public void mapUnsafeString(
			String fieldExpression, UnsafeBiConsumer<T, String, ?> consumer);

	}

	public interface UpdateFunction<T> {

		public T update(T oldModel, T newModel, ServiceContext serviceContext)
			throws PortalException;

	}

}