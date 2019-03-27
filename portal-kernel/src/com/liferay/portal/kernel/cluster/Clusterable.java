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

package com.liferay.portal.kernel.cluster;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When invoked by an AopInvocationHandler, Methods annotated with Clusterable
 * are invoked across the cluster. By default the method is invoked on all
 * active nodes in the cluster.
 *
 * All Liferay aspect annotations are aware of their scope. Interface aspect
 * annotations can be overwritten by their implementations. Class level aspect
 * annotations can be overwritten by method annotations.
 *
 * @author Shuyang Zhou
 * @review
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Clusterable {

	/**
	 * A ClusterInvokeAcceptor can filter out invocation on members of the node
	 * in case they are not ready or capable of handling the invocation.
	 */
	public Class<? extends ClusterInvokeAcceptor> acceptor()
		default ClusterInvokeAcceptor.class;

	/**
	 * Indicates if only the master node should be invoked. The result of the
	 * method invocation is deserialized and returned.
	 */
	public boolean onMaster() default false;

}