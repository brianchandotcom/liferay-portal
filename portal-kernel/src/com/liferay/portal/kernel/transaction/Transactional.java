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
 * Configures the transaction advice for methods that have this annotation when
 * they're invoked by an
 * <code>com.liferay.portal.spring.aop.AopInvocationHandler</code>.
 *
 * <ul>
 * <li>
 * All Liferay aspect annotations are aware of their scope.
 * </li>
 * <li>
 * Interface aspect annotations can be overwritten by their implementations.
 * </li>
 * <li>
 * Class level aspect annotations can be overwritten by method annotations.
 * </li>
 * </ul>
 *
 * @author Brian Wing Shun Chan
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Transactional {

	/**
	 * Whether a transaction is needed. Disabling transactions for a method
	 * optimizes run time performance.
	 */
	public boolean enabled() default true;

	/**
	 * Returns the {@link Isolation} setting.
	 */
	public Isolation isolation() default Isolation.DEFAULT;

	/**
	 * Returns exception classes that should not cause the transaction to be
	 * rolled back.
	 */
	public Class<? extends Throwable>[] noRollbackFor() default {};

	/**
	 * Returns exception names that should not cause the transaction to be
	 * rolled back.
	 */
	public String[] noRollbackForClassName() default {};

	/**
	 * Returns the {@link Propagation} setting.
	 */
	public Propagation propagation() default Propagation.REQUIRED;

	/**
	 * Whether the the transaction is effectively read-only, allowing for
	 * optimizations at run time.
	 */
	public boolean readOnly() default false;

	/**
	 * Returns exception classes that cause the transaction to be rolled back.
	 */
	public Class<? extends Throwable>[] rollbackFor() default {};

	/**
	 * Returns exception names that cause the transaction to be rolled back.
	 */
	public String[] rollbackForClassName() default {};

	/**
	 * Returns the transaction's timeout in seconds.
	 */
	public int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

}