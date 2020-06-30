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

package com.liferay.change.tracking.internal.resolver;

import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;

/**
 * @author Preston Crary
 */
public class ConstraintResolverContextImpl<T extends CTModel<T>>
	implements ConstraintResolverContext<T> {

	public ConstraintResolverContextImpl(
		CTService<T> ctService, long sourceCTCollectionId,
		long targetCTCollectionId) {

		_ctService = ctService;
		_sourceCTCollectionId = sourceCTCollectionId;
		_targetCTCollectionId = targetCTCollectionId;
	}

	@Override
	public <R, E extends Throwable> R getInTarget(
			UnsafeSupplier<R, E> unsafeSupplier)
		throws E {

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_targetCTCollectionId)) {

			return unsafeSupplier.get();
		}
	}

	@Override
	public T getSourceCTModel() {
		return _ctService.updateWithUnsafeFunction(
			ctPersistence -> ctPersistence.fetchByPrimaryKey(
				_sourcePrimaryKey));
	}

	@Override
	public T getTargetCTModel() {
		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_targetCTCollectionId)) {

			return _ctService.updateWithUnsafeFunction(
				ctPersistence -> ctPersistence.fetchByPrimaryKey(
					_targetPrimaryKey));
		}
	}

	@Override
	public boolean isSourceCTModel(CTModel<?> ctModel) {
		if (ctModel.getCtCollectionId() == _sourceCTCollectionId) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTargetCTModel(CTModel<?> ctModel) {
		if (ctModel.getCtCollectionId() == _targetCTCollectionId) {
			return true;
		}

		return false;
	}

	public void setPrimaryKeys(long sourcePrimaryKey, long targetPrimaryKey) {
		_sourcePrimaryKey = sourcePrimaryKey;
		_targetPrimaryKey = targetPrimaryKey;
	}

	private final CTService<T> _ctService;
	private final long _sourceCTCollectionId;
	private long _sourcePrimaryKey;
	private final long _targetCTCollectionId;
	private long _targetPrimaryKey;

}