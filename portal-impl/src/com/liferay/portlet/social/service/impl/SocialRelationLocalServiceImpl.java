/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.social.RelationUserIdException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.base.SocialRelationLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialRelationLocalServiceImpl
	extends SocialRelationLocalServiceBaseImpl {

	public SocialRelation addRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		if (userId1 == userId2) {
			throw new RelationUserIdException();
		}

		User user1 = userPersistence.findByPrimaryKey(userId1);
		User user2 = userPersistence.findByPrimaryKey(userId2);

		if (user1.getCompanyId() != user2.getCompanyId()) {
			throw new RelationUserIdException();
		}

		SocialRelation relation = socialRelationPersistence.fetchByU1_U2_T(
			userId1, userId2, type);

		if (relation == null) {
			long relationId = counterLocalService.increment();

			relation = socialRelationPersistence.create(relationId);

			relation.setCompanyId(user1.getCompanyId());
			relation.setCreateDate(System.currentTimeMillis());
			relation.setUserId1(userId1);
			relation.setUserId2(userId2);
			relation.setType(type);

			socialRelationPersistence.update(relation, false);
		}

		if (SocialRelationConstants.isTypeBi(type)) {
			SocialRelation biRelation =
				socialRelationPersistence.fetchByU1_U2_T(
					userId2, userId1, type);

			if (biRelation == null) {
				long biRelationId = counterLocalService.increment();

				biRelation = socialRelationPersistence.create(biRelationId);

				biRelation.setCompanyId(user1.getCompanyId());
				biRelation.setCreateDate(System.currentTimeMillis());
				biRelation.setUserId1(userId2);
				biRelation.setUserId2(userId1);
				biRelation.setType(type);

				socialRelationPersistence.update(biRelation, false);
			}
		}

		return relation;
	}

	public void deleteRelation(long relationId)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByPrimaryKey(
			relationId);

		deleteRelation(relation);
	}

	public void deleteRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByU1_U2_T(
			userId1, userId2, type);

		deleteRelation(relation);
	}

	public void deleteRelation(SocialRelation relation)
		throws PortalException, SystemException {

		socialRelationPersistence.remove(relation);

		if (SocialRelationConstants.isTypeBi(relation.getType())) {
			SocialRelation biRelation = socialRelationPersistence.findByU1_U2_T(
				relation.getUserId2(), relation.getUserId1(),
				relation.getType());

			socialRelationPersistence.remove(biRelation);
		}
	}

	public void deleteRelations(long userId) throws SystemException {
		socialRelationPersistence.removeByUserId1(userId);
		socialRelationPersistence.removeByUserId2(userId);
	}

	public List<SocialRelation> getInverseRelations(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU2_T(userId, type, start, end);
	}

	public int getInverseRelationsCount(long userId, int type)
		throws SystemException {

		return socialRelationPersistence.countByU2_T(userId, type);
	}

	public SocialRelation getRelation(long relationId)
		throws PortalException, SystemException {

		return socialRelationPersistence.findByPrimaryKey(relationId);
	}

	public SocialRelation getRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		return socialRelationPersistence.findByU1_U2_T(
			userId1, userId2, type);
	}

	public List<SocialRelation> getRelations(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU1_T(userId, type, start, end);
	}

	public List<SocialRelation> getRelations(
			long userId1, long userId2, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU1_U2(
			userId1, userId2, start, end);
	}

	public int getRelationsCount(long userId, int type) throws SystemException {
		return socialRelationPersistence.countByU1_T(userId, type);
	}

	public int getRelationsCount(long userId1, long userId2)
		throws SystemException {

		return socialRelationPersistence.countByU1_U2(userId1, userId2);
	}

	public boolean hasRelation(long userId1, long userId2, int type)
		throws SystemException {

		SocialRelation relation = socialRelationPersistence.fetchByU1_U2_T(
			userId1, userId2, type);

		if (relation == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isRelatable(long userId1, long userId2, int type)
		throws SystemException {

		if (userId1 == userId2) {
			return false;
		}

		User user1 = userPersistence.fetchByPrimaryKey(userId1);

		if ((user1 == null) || user1.isDefaultUser()) {
			return false;
		}

		User user2 = userPersistence.fetchByPrimaryKey(userId2);

		if ((user2 == null) || user2.isDefaultUser()) {
			return false;
		}

		return !hasRelation(userId1, userId2, type);
	}

}