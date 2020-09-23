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

package com.liferay.commerce.product.item.selector.web.internal.search;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelItemSelectorChecker extends EmptyOnClickRowChecker {

	public CommerceChannelItemSelectorChecker(
		RenderResponse renderResponse, String className, long classPK,
		CommerceChannelRelService commerceChannelRelService) {

		super(renderResponse);

		List<CommerceChannelRel> commerceChannelRels =
			commerceChannelRelService.getCommerceChannelRels(
				className, classPK, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<CommerceChannelRel> stream = commerceChannelRels.stream();

		long[] commerceChannelRelIds = stream.mapToLong(
			CommerceChannelRel::getCommerceChannelId
		).toArray();

		_checkedCommerceChannelIds = SetUtil.fromArray(commerceChannelRelIds);
	}

	@Override
	public boolean isChecked(Object object) {
		CommerceChannel commerceChannel = (CommerceChannel)object;

		return _checkedCommerceChannelIds.contains(
			commerceChannel.getCommerceChannelId());
	}

	@Override
	public boolean isDisabled(Object object) {
		return isChecked(object);
	}

	private final Set<Long> _checkedCommerceChannelIds;

}