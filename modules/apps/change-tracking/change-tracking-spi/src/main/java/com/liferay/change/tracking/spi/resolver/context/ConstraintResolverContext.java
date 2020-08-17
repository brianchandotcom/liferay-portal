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

package com.liferay.change.tracking.spi.resolver.context;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Used in automatically resolving constraint conflicts between the source and
 * target models.
 *
 * @author Preston Crary
 * @see    com.liferay.change.tracking.spi.resolver.ConstraintResolver#resolveConflict(
 *         ConstraintResolverContext)
 */
@ProviderType
public interface ConstraintResolverContext<T extends CTModel<T>> {

	/**
	 * Returns the result of the unsafe supplier from within the target CT
	 * collection.
	 *
	 * @param  unsafeSupplier the unsafe supplier to call
	 * @return the result from the unsafe supplier
	 */
	public <R, E extends Throwable> R getInTarget(
			UnsafeSupplier<R, E> unsafeSupplier)
		throws E;

	/**
	 * Returns the source CT model has a unique constraint conflict with the
	 * target CT model.
	 *
	 * @return the source CT model
	 */
	public T getSourceCTModel();

	/**
	 * Returns the target CT model has a unique constraint conflict with the
	 * source CT model.
	 *
	 * @return the target CT model
	 */
	public T getTargetCTModel();

	/**
	 * Returns if the CT model is from source.
	 *
	 * @param  ctModel the model
	 * @return <code>true</code> if the model is from source; <code>false</code>
	 *         otherwise
	 */
	public boolean isSourceCTModel(CTModel<?> ctModel);

	/**
	 * Returns if the CT model is from target.
	 *
	 * @param  ctModel the model
	 * @return <code>true</code> if the model is from target; <code>false</code>
	 *         otherwise
	 */
	public boolean isTargetCTModel(CTModel<?> ctModel);

}