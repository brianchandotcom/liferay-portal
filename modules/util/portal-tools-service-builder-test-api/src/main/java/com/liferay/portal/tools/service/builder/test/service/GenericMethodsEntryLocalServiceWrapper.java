/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GenericMethodsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GenericMethodsEntryLocalService
 * @generated
 */
public class GenericMethodsEntryLocalServiceWrapper
	implements GenericMethodsEntryLocalService,
			   ServiceWrapper<GenericMethodsEntryLocalService> {

	public GenericMethodsEntryLocalServiceWrapper() {
		this(null);
	}

	public GenericMethodsEntryLocalServiceWrapper(
		GenericMethodsEntryLocalService genericMethodsEntryLocalService) {

		_genericMethodsEntryLocalService = genericMethodsEntryLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _genericMethodsEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public <E extends Exception> void typeParameterAndBoundMethod(
			java.util.function.BiConsumer<String, E> biConsumer)
		throws E {

		_genericMethodsEntryLocalService.typeParameterAndBoundMethod(
			biConsumer);
	}

	@Override
	public <T> void typeParameterMethod(java.util.function.Consumer<T> consumer)
		throws Exception {

		_genericMethodsEntryLocalService.typeParameterMethod(consumer);
	}

	@Override
	public <T, E extends Exception> java.util.List<T>
		typeParametersAndBoundMethod(
			java.util.function.BiFunction<Long, T, E> biFunction,
			java.util.function.BiConsumer<Long, E> biConsumer) {

		return _genericMethodsEntryLocalService.typeParametersAndBoundMethod(
			biFunction, biConsumer);
	}

	@Override
	public <N extends Number, E extends Exception> java.util.List<N>
		typeParametersAndBoundsMethod(
			java.util.function.BiFunction<Long, N, E> biFunction,
			java.util.function.BiConsumer<Long, N> biConsumer) {

		return _genericMethodsEntryLocalService.typeParametersAndBoundsMethod(
			biFunction, biConsumer);
	}

	@Override
	public
		<N extends Number & java.util.function.ObjIntConsumer,
		 E extends Exception & java.io.Serializable> java.util.List<N>
			 typeParametersAndMultipleBoundsMethod(
			 	java.util.function.BiFunction<Long, N, E> biFunction,
			 	java.util.function.BiConsumer<Long, N> biConsumer) {

		return _genericMethodsEntryLocalService.
			typeParametersAndMultipleBoundsMethod(biFunction, biConsumer);
	}

	@Override
	public GenericMethodsEntryLocalService getWrappedService() {
		return _genericMethodsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		GenericMethodsEntryLocalService genericMethodsEntryLocalService) {

		_genericMethodsEntryLocalService = genericMethodsEntryLocalService;
	}

	private GenericMethodsEntryLocalService _genericMethodsEntryLocalService;

}