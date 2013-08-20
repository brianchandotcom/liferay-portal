/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * @author Brian Wing Shun Chan
 */
public interface SocialActivityFinder {
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupUsers(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByOrganizationId(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByOrganizationUsers(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByRelation(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByRelationType(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserGroupsAndOrganizations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserOrganizations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countU_ByActivitySetId(long activitySetId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupUsers(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByOrganizationId(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByOrganizationUsers(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByRelation(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByRelationType(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserGroupsAndOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;
}