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

package com.liferay.knowledge.base.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBArticleService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleService
 * @generated
 */
@ProviderType
public class KBArticleServiceWrapper implements KBArticleService,
	ServiceWrapper<KBArticleService> {
	public KBArticleServiceWrapper(KBArticleService kbArticleService) {
		_kbArticleService = kbArticleService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _kbArticleService.getOSGiServiceIdentifier();
	}

	@Override
	public KBArticleService getWrappedService() {
		return _kbArticleService;
	}

	@Override
	public void setWrappedService(KBArticleService kbArticleService) {
		_kbArticleService = kbArticleService;
	}

	private KBArticleService _kbArticleService;
}