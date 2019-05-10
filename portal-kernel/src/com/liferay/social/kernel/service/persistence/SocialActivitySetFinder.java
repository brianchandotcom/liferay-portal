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

package com.liferay.social.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface SocialActivitySetFinder {

	public int countByOrganizationId(long organizationId);

	public int countByRelation(long userId);

	public int countByRelationType(long userId, int type);

	public int countByUser(long userId);

	public int countByUserGroups(long userId);

	public java.util.List<com.liferay.social.kernel.model.SocialActivitySet>
		findByOrganizationId(long organizationId, int start, int end);

	public java.util.List<com.liferay.social.kernel.model.SocialActivitySet>
		findByRelation(long userId, int start, int end);

	public java.util.List<com.liferay.social.kernel.model.SocialActivitySet>
		findByRelationType(long userId, int type, int start, int end);

	public java.util.List<com.liferay.social.kernel.model.SocialActivitySet>
		findByUser(long userId, int start, int end);

	public java.util.List<com.liferay.social.kernel.model.SocialActivitySet>
		findByUserGroups(long userId, int start, int end);

}