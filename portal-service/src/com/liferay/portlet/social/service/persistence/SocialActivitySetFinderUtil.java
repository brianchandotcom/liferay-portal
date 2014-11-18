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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class SocialActivitySetFinderUtil {
	public static int countByRelation(long userId) {
		return getFinder().countByRelation(userId);
	}

	public static int countByRelationType(long userId, int type) {
		return getFinder().countByRelationType(userId, type);
	}

	public static int countByUser(long userId) {
		return getFinder().countByUser(userId);
	}

	public static int countByUserGroups(long userId) {
		return getFinder().countByUserGroups(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findByRelation(
		long userId, int start, int end) {
		return getFinder().findByRelation(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findByRelationType(
		long userId, int type, int start, int end) {
		return getFinder().findByRelationType(userId, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findByUser(
		long userId, int start, int end) {
		return getFinder().findByUser(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findByUserGroups(
		long userId, int start, int end) {
		return getFinder().findByUserGroups(userId, start, end);
	}

	public static SocialActivitySetFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivitySetFinder)PortalBeanLocatorUtil.locate(SocialActivitySetFinder.class.getName());

			ReferenceRegistry.registerReference(SocialActivitySetFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SocialActivitySetFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SocialActivitySetFinderUtil.class,
			"_finder");
	}

	private static SocialActivitySetFinder _finder;
}