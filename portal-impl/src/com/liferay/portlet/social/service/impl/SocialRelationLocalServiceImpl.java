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
 * The social relation local service. This service provides methods to handle
 * unidirectional or bidirectional relations between users.
 *
 * <p>
 * Relations between users can be unidirectional or bidirectional. The type of
 * relation is determined by an integer constant. Some example relations are
 * <i>co-worker, friend, romantic partner, sibling, spouse, child, enemy,
 * follower, parent, subordinate, supervisor</i>.
 * </p>
 *
 * <p>
 * The two users participating in the relation are designated as User1 and
 * User2. In case of unidirectional relations User1 should always be the subject
 * of the relation. You can use the following English sentence to find out which
 * user to use as User1 and which to use as User2:
 * </p>
 *
 * <p>
 * User1 is <i>&lt;relation&gt;</i> of User2 (e.g. User1 is parent of User2;
 * User1 is supervisor of User2)
 * </p>
 *
 * <p>
 * For bidirectional relations, the service automatically generates the inverse
 * relation.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRelationLocalServiceImpl
	extends SocialRelationLocalServiceBaseImpl {

	/**
	 * Adds a social relation between the two users to the database.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @return the social relation
	 * @throws PortalException if the users could not be found, if the users
	 *         were not from the same company, or if either of the users was the
	 *         default user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

			socialRelationPersistence.update(relation);
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

				socialRelationPersistence.update(biRelation);
			}
		}

		return relation;
	}

	/**
	 * Removes the relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  relationId the primary key of the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteRelation(long relationId)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByPrimaryKey(
			relationId);

		deleteRelation(relation);
	}

	/**
	 * Removes the matching relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the relation's type
	 * @throws PortalException if the relation or its inverse relation (if
	 *         applicable) could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByU1_U2_T(
			userId1, userId2, type);

		deleteRelation(relation);
	}

	/**
	 * Removes the relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  relation the relation to be removed
	 * @throws PortalException if the relation is bidirectional and its inverse
	 *         relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Removes all relations involving the user from the database.
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteRelations(long userId) throws SystemException {
		socialRelationPersistence.removeByUserId1(userId);
		socialRelationPersistence.removeByUserId2(userId);
	}

	/**
	 * Removes all relations between User1 and User2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @throws PortalException if the inverse relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteRelations(long userId1, long userId2)
		throws PortalException, SystemException {

		List<SocialRelation> relations = socialRelationPersistence.findByU1_U2(
			userId1, userId2);

		for (SocialRelation relation : relations) {
			deleteRelation(relation);
		}
	}

	/**
	 * Returns a range of all the inverse relations of the given type for which
	 * the user is User2 of the relation.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  type the relation's type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching relations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialRelation> getInverseRelations(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU2_T(userId, type, start, end);
	}

	/**
	 * Returns the number of inverse relations of the given type for which the
	 * user is User2 of the relation.
	 *
	 * @param  userId the primary key of the user
	 * @param  type the relation's type
	 * @return the number of matching relations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getInverseRelationsCount(long userId, int type)
		throws SystemException {

		return socialRelationPersistence.countByU2_T(userId, type);
	}

	/**
	 * Returns the relation identified by its primary key.
	 *
	 * @param  relationId the primary key of the relation
	 * @return Returns the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRelation getRelation(long relationId)
		throws PortalException, SystemException {

		return socialRelationPersistence.findByPrimaryKey(relationId);
	}

	/**
	 * Returns the relation of the given type between User1 and User2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the relation's type
	 * @return Returns the relation
	 * @throws PortalException if the relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialRelation getRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		return socialRelationPersistence.findByU1_U2_T(userId1, userId2, type);
	}

	/**
	 * Returns a range of all the relations of the given type where the user is
	 * the subject of the relation.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  type the relation's type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of relations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialRelation> getRelations(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU1_T(userId, type, start, end);
	}

	/**
	 * Returns a range of all the relations between User1 and User2.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of relations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialRelation> getRelations(
			long userId1, long userId2, int start, int end)
		throws SystemException {

		return socialRelationPersistence.findByU1_U2(
			userId1, userId2, start, end);
	}

	/**
	 * Returns the number of relations of the given type where the user is the
	 * subject of the relation.
	 *
	 * @param  userId the primary key of the user
	 * @param  type the relation's type
	 * @return the number of relations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getRelationsCount(long userId, int type) throws SystemException {
		return socialRelationPersistence.countByU1_T(userId, type);
	}

	/**
	 * Returns the number of relations between User1 and User2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @return the number of relations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getRelationsCount(long userId1, long userId2)
		throws SystemException {

		return socialRelationPersistence.countByU1_U2(userId1, userId2);
	}

	/**
	 * Returns <code>true</code> if a relation of the given type exists where
	 * the user with primary key <code>userId1</code> is User1 of the relation
	 * and the user with the primary key <code>userId2</code> is User2 of the
	 * relation.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the relation's type
	 * @return <code>true</code> if the relation exists; <code>false</code>
	 *         otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Returns <code>true</code> if the users can be in a relation of the given
	 * type where the user with primary key <code>userId1</code> is User1 of the
	 * relation and the user with the primary key <code>userId2</code> is User2
	 * of the relation.
	 *
	 * <p>
	 * This method returns <code>false</code> if User1 and User2 are the same,
	 * if either user is the default user, or if a matching relation already
	 * exists.
	 * </p>
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the relation's type
	 * @return <code>true</code> if the two users can be in a new relation of
	 *         the given type; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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