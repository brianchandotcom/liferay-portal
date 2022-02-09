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

package com.liferay.commerce.discount.internal.model.listener;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class CommerceChannelRelModelListener
	extends BaseModelListener<CommerceChannelRel> {

	@Override
	public void onAfterCreate(CommerceChannelRel commerceChannelRel)
		throws ModelListenerException {

		_reindexCommerceDiscount(commerceChannelRel);
	}

	@Override
	public void onAfterRemove(CommerceChannelRel commerceChannelRel) {
		_reindexCommerceDiscount(commerceChannelRel);
	}

	private void _reindexCommerceDiscount(
		CommerceChannelRel commerceChannelRel) {

		try {
			String className = commerceChannelRel.getClassName();

			if (className.equals(CommerceDiscount.class.getName())) {
				_reindexCommerceDiscount(commerceChannelRel.getClassPK());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _reindexCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		indexer.reindex(CommerceDiscount.class.getName(), commerceDiscountId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelRelModelListener.class);

}