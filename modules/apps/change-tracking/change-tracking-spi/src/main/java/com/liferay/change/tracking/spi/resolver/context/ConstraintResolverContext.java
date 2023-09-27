/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	 * Returns the result of the unsafe supplier from within the target change
	 * tracking collection.
	 *
	 * @param  unsafeSupplier the unsafe supplier to call
	 * @return the result from the unsafe supplier
	 */
	public <R, E extends Throwable> R getInTarget(
			UnsafeSupplier<R, E> unsafeSupplier)
		throws E;

	/**
	 * Returns the source change tracking model that has a unique constraint
	 * conflict with the target change tracking model.
	 *
	 * @return the source change tracking model
	 */
	public T getSourceCTModel();

	/**
	 * Returns the target change tracking model that has a unique constraint
	 * conflict with the source change tracking model.
	 *
	 * @return the target change tracking model
	 */
	public T getTargetCTModel();

	/**
	 * Returns whether the change tracking model is from the source.
	 *
	 * @param  ctModel the change tracking model to check
	 * @return whether the model is from the source
	 */
	public boolean isSourceCTModel(CTModel<?> ctModel);

	/**
	 * Returns whether the change tracking model is from the target.
	 *
	 * @param  ctModel the change tracking model to check
	 * @return whether the model is from the target
	 */
	public boolean isTargetCTModel(CTModel<?> ctModel);

}