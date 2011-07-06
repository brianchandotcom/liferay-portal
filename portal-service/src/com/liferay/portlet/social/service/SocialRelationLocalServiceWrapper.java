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

package com.liferay.portlet.social.service;

/**
 * <p>
 * This class is a wrapper for {@link SocialRelationLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationLocalService
 * @generated
 */
public class SocialRelationLocalServiceWrapper
	implements SocialRelationLocalService {
	public SocialRelationLocalServiceWrapper(
		SocialRelationLocalService socialRelationLocalService) {
		_socialRelationLocalService = socialRelationLocalService;
	}

	/**
	* Adds the social relation to the database. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation
	* @return the social relation that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation addSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.addSocialRelation(socialRelation);
	}

	/**
	* Creates a new social relation with the primary key. Does not add the social relation to the database.
	*
	* @param relationId the primary key for the new social relation
	* @return the new social relation
	*/
	public com.liferay.portlet.social.model.SocialRelation createSocialRelation(
		long relationId) {
		return _socialRelationLocalService.createSocialRelation(relationId);
	}

	/**
	* Deletes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param relationId the primary key of the social relation
	* @throws PortalException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteSocialRelation(relationId);
	}

	/**
	* Deletes the social relation from the database. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteSocialRelation(socialRelation);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social relation with the primary key.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation
	* @throws PortalException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getSocialRelation(relationId);
	}

	/**
	* Returns a range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getSocialRelations(start, end);
	}

	/**
	* Returns the number of social relations.
	*
	* @return the number of social relations
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialRelationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getSocialRelationsCount();
	}

	/**
	* Updates the social relation in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation
	* @return the social relation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.updateSocialRelation(socialRelation);
	}

	/**
	* Updates the social relation in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialRelation the social relation
	* @param merge whether to merge the social relation with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social relation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.updateSocialRelation(socialRelation,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialRelationLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialRelationLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.addRelation(userId1, userId2, type);
	}

	public void deleteRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelation(relationId);
	}

	public void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelation(userId1, userId2, type);
	}

	public void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelation(relation);
	}

	public void deleteRelations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelations(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getInverseRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getInverseRelations(userId, type,
			start, end);
	}

	public int getInverseRelationsCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getInverseRelationsCount(userId, type);
	}

	public com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelation(relationId);
	}

	public com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelation(userId1, userId2, type);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelations(userId, type, start, end);
	}

	public int getRelationsCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelationsCount(userId, type);
	}

	public boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.hasRelation(userId1, userId2, type);
	}

	public boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.isRelatable(userId1, userId2, type);
	}

	public SocialRelationLocalService getWrappedSocialRelationLocalService() {
		return _socialRelationLocalService;
	}

	public void setWrappedSocialRelationLocalService(
		SocialRelationLocalService socialRelationLocalService) {
		_socialRelationLocalService = socialRelationLocalService;
	}

	private SocialRelationLocalService _socialRelationLocalService;
}