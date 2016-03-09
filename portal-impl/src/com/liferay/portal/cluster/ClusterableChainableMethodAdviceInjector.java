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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.spring.aop.ChainableMethodAdviceInjector;
import com.liferay.portal.util.PropsValues;

/**
 * @author Shuyang Zhou
 */
public class ClusterableChainableMethodAdviceInjector
	extends ChainableMethodAdviceInjector {

	@Override
	public void inject() {
		setInjectCondition(PropsValues.CLUSTER_LINK_ENABLED);
		setNewChainableMethodAdvice(new ClusterableAdvice());

		super.inject();

		if (SPIUtil.isSPI()) {
			setInjectCondition(true);
			setNewChainableMethodAdvice(new SPIClusterableAdvice());

			super.inject();
		}
	}

}