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

package com.liferay.portal.spring.aop;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.spring.aop.AdvisedSupport;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;

/**
 * @author Preston Crary
 */
@ProviderType
public class AdvisedSupportUtil {

	public static AdvisedSupport getAdvisedSupport(Object proxy) {
		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			proxy);

		if (invocationHandler instanceof AdvisedSupportProxy) {
			AdvisedSupportProxy advisableSupportProxy =
				(AdvisedSupportProxy)invocationHandler;

			return advisableSupportProxy.getAdvisedSupport();
		}

		return null;
	}

	private AdvisedSupportUtil() {
	}

}