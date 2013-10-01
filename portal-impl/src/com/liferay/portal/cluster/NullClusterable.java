/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.Clusterable;

import java.lang.annotation.Annotation;

/**
 * @author Shuyang Zhou
 */
public class NullClusterable implements Clusterable {

	public static final Clusterable NULL_CLUSTERABLE = new NullClusterable();

	@Override
	public Class<? extends ClusterInvokeAcceptor> acceptor() {
		return null;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return Clusterable.class;
	}

	@Override
	public boolean onMaster() {
		return false;
	}

	private NullClusterable() {
	}

}