/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.Serializable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for GenericMethodsEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see GenericMethodsEntryLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface GenericMethodsEntryLocalService extends BaseLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.GenericMethodsEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the generic methods entry local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link GenericMethodsEntryLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public <E extends Exception> void typeParameterAndBoundMethod(
			BiConsumer<String, E> biConsumer)
		throws E;

	public <T> void typeParameterMethod(Consumer<T> consumer) throws Exception;

	public <T, E extends Exception> List<T> typeParametersAndBoundMethod(
		BiFunction<Long, T, E> biFunction, BiConsumer<Long, E> biConsumer);

	public <N extends Number, E extends Exception> List<N>
		typeParametersAndBoundsMethod(
			BiFunction<Long, N, E> biFunction, BiConsumer<Long, N> biConsumer);

	public
		<N extends Number & ObjIntConsumer, E extends Exception & Serializable>
			List<N> typeParametersAndMultipleBoundsMethod(
				BiFunction<Long, N, E> biFunction,
				BiConsumer<Long, N> biConsumer);

}