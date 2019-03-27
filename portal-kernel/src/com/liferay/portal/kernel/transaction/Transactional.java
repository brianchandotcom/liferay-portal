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

package com.liferay.portal.kernel.transaction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When invoked by an AopInvocationHandler, the transaction advice is configured
 * by this annotation.
 *
 * All Liferay aspect annotations are aware of their scope. Interface aspect
 * annotations can be overwritten by their implementations. Class level aspect
 * annotations can be overwritten by method annotations.
 *
 * @author Brian Wing Shun Chan
 * @review
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Transactional {

	/**
	 * A flag that can be set to false if no transaction is needed, allowing
	 * for optimizations at runtime.
	 */
	public boolean enabled() default true;

	/**
	 * @see Isolation
	 */
	public Isolation isolation() default Isolation.DEFAULT;

	/**
	 * Exception classes that should not cause the transaction to be rolled
	 * back.
	 */
	public Class<? extends Throwable>[] noRollbackFor() default {};

	/**
	 * Exception names that should not cause the transaction to be rolled back.
	 */
	public String[] noRollbackForClassName() default {};

	/**
	 * @see Propagation
	 */
	public Propagation propagation() default Propagation.REQUIRED;

	/**
	 * A flag that can be set to true if the transaction is effectively
	 * read-only, allowing for optimizations at runtime.
	 */
	public boolean readOnly() default false;

	/**
	 * Exception classes that should cause the transaction to be rolled back.
	 */
	public Class<? extends Throwable>[] rollbackFor() default {};

	/**
	 * Exception names that should cause the transaction to be rolled back.
	 */
	public String[] rollbackForClassName() default {};

	/**
	 * The timeout for this transaction in seconds.
	 */
	public int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

}