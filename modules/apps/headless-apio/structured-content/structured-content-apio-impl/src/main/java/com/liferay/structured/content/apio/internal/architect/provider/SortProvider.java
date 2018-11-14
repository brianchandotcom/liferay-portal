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

package com.liferay.structured.content.apio.internal.architect.provider;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.structured.content.apio.internal.architect.filter.StructuredContentEntityModel;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Cristina González
 */
@Component(service = Provider.class)
public class SortProvider implements Provider<Sort> {

	@Override
	public Sort createContext(HttpServletRequest httpServletRequest) {
		String sortString = httpServletRequest.getParameter("sort");

		if (Validator.isNull(sortString)) {
			return Sort.emptySort();
		}

		return new Sort(_sortParser.parse(sortString));
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + StructuredContentEntityModel.NAME + ")",
		unbind = "unbind"
	)
	public void setSortParser(SortParser sortParser) {
		if (_log.isInfoEnabled()) {
			_log.info("Binding " + sortParser);
		}

		_sortParser = sortParser;
	}

	public void unbind(SortParser sortParser) {
		if (_log.isInfoEnabled()) {
			_log.info("Unbinding " + sortParser);
		}

		_sortParser = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(SortProvider.class);

	private SortParser _sortParser;

}