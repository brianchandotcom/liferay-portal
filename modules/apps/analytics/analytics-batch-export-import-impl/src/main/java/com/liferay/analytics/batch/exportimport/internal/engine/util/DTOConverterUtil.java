/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.batch.exportimport.internal.engine.util;

import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class DTOConverterUtil {

	public static <E, D> List<D> toDTOs(
			Collection<? extends E> collection, DTOConverter<E, D> dtoConverter)
		throws Exception {

		List<Future<D>> futures = new ArrayList<>(collection.size());

		ExecutorService executorService =
			SystemExecutorServiceUtil.getExecutorService();

		for (E e : collection) {
			futures.add(executorService.submit(() -> dtoConverter.toDTO(e)));
		}

		List<D> dtos = new ArrayList<>();

		for (Future<D> future : futures) {
			dtos.add(future.get());
		}

		return dtos;
	}

}