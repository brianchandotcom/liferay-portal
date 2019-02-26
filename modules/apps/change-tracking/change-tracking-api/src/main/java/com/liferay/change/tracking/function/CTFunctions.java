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

package com.liferay.change.tracking.function;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Máté Thurzó
 */
public class CTFunctions {

	public static <T extends ClassedModel> Function<T, Optional<Group>>
		getFetchGroupFunction() {

		return classedModel -> {
			long groupId = BeanPropertiesUtil.getLongSilent(
				classedModel, "groupId");

			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			return Optional.ofNullable(group);
		};
	}

	public static <T extends ClassedModel> Function<T, String>
		getFetchSiteNameFunction() {

		return classedModel -> {
			Optional<Group> groupOptional = getFetchGroupFunction().apply(
				classedModel);

			if (!groupOptional.isPresent()) {
				return StringPool.BLANK;
			}

			try {
				Group group = groupOptional.get();

				return group.getDescriptiveName();
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to get group descriptive name for classed " +
							"model " + classedModel,
						pe);
				}
			}

			return StringPool.BLANK;
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(CTFunctions.class);

}