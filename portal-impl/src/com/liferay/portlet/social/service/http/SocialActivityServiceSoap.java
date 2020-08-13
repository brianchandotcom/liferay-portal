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

package com.liferay.portlet.social.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.social.kernel.service.SocialActivityServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SocialActivityServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.social.kernel.model.SocialActivitySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.social.kernel.model.SocialActivity</code>, that is translated to a
 * <code>com.liferay.social.kernel.model.SocialActivitySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SocialActivityServiceSoap {

	/**
	 * Returns a range of all the activities done on assets identified by the
	 * class name ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param classNameId the target asset's class name ID
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getActivities(long classNameId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getActivities(
					classNameId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done on the asset identified by the
	 * class name ID and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param classNameId the target asset's class name ID
	 * @param classPK the primary key of the target asset
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getActivities(
				long mirrorActivityId, long classNameId, long classPK,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getActivities(
					mirrorActivityId, classNameId, classPK, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done on the asset identified by the
	 * class name and the class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param className the target asset's class name
	 * @param classPK the primary key of the target asset
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getActivities(
				long mirrorActivityId, String className, long classPK,
				int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getActivities(
					mirrorActivityId, className, classPK, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done on assets identified by the
	 * class name.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param className the target asset's class name
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getActivities(String className, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getActivities(
					className, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done on assets identified by the class
	 * name ID.
	 *
	 * @param classNameId the target asset's class name ID
	 * @return the number of matching activities
	 */
	public static int getActivitiesCount(long classNameId)
		throws RemoteException {

		try {
			int returnValue = SocialActivityServiceUtil.getActivitiesCount(
				classNameId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done on the asset identified by the
	 * class name ID and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param classNameId the target asset's class name ID
	 * @param classPK the primary key of the target asset
	 * @return the number of matching activities
	 */
	public static int getActivitiesCount(
			long mirrorActivityId, long classNameId, long classPK)
		throws RemoteException {

		try {
			int returnValue = SocialActivityServiceUtil.getActivitiesCount(
				mirrorActivityId, classNameId, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done on the asset identified by the
	 * class name and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param className the target asset's class name
	 * @param classPK the primary key of the target asset
	 * @return the number of matching activities
	 */
	public static int getActivitiesCount(
			long mirrorActivityId, String className, long classPK)
		throws RemoteException {

		try {
			int returnValue = SocialActivityServiceUtil.getActivitiesCount(
				mirrorActivityId, className, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done on assets identified by class name.
	 *
	 * @param className the target asset's class name
	 * @return the number of matching activities
	 */
	public static int getActivitiesCount(String className)
		throws RemoteException {

		try {
			int returnValue = SocialActivityServiceUtil.getActivitiesCount(
				className);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the activity identified by its primary key.
	 *
	 * @param activityId the primary key of the activity
	 * @return Returns the activity
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap
			getActivity(long activityId)
		throws RemoteException {

		try {
			com.liferay.social.kernel.model.SocialActivity returnValue =
				SocialActivityServiceUtil.getActivity(activityId);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getActivitySetActivities(long activitySetId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue =
					SocialActivityServiceUtil.getActivitySetActivities(
						activitySetId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done in the group.
	 *
	 * <p>
	 * This method only finds activities without mirrors.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getGroupActivities(long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getGroupActivities(
					groupId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done in the group.
	 *
	 * <p>
	 * This method only counts activities without mirrors.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @return the number of matching activities
	 */
	public static int getGroupActivitiesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue = SocialActivityServiceUtil.getGroupActivitiesCount(
				groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of activities done by users that are members of the
	 * group.
	 *
	 * <p>
	 * This method only finds activities without mirrors.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getGroupUsersActivities(long groupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getGroupUsersActivities(
					groupId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done by users that are members of the
	 * group.
	 *
	 * <p>
	 * This method only counts activities without mirrors.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @return the number of matching activities
	 */
	public static int getGroupUsersActivitiesCount(long groupId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getGroupUsersActivitiesCount(groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the activity that has the mirror activity.
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @return Returns the mirror activity
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap
			getMirrorActivity(long mirrorActivityId)
		throws RemoteException {

		try {
			com.liferay.social.kernel.model.SocialActivity returnValue =
				SocialActivityServiceUtil.getMirrorActivity(mirrorActivityId);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done in the organization. This
	 * method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param organizationId the primary key of the organization
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getOrganizationActivities(long organizationId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue =
					SocialActivityServiceUtil.getOrganizationActivities(
						organizationId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done in the organization. This method
	 * only counts activities without mirrors.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the number of matching activities
	 */
	public static int getOrganizationActivitiesCount(long organizationId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getOrganizationActivitiesCount(
					organizationId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done by users of the organization.
	 * This method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param organizationId the primary key of the organization
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getOrganizationUsersActivities(
				long organizationId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue =
					SocialActivityServiceUtil.getOrganizationUsersActivities(
						organizationId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done by users of the organization. This
	 * method only counts activities without mirrors.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the number of matching activities
	 */
	public static int getOrganizationUsersActivitiesCount(long organizationId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getOrganizationUsersActivitiesCount(
					organizationId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done by users in a relationship
	 * with the user identified by the user ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getRelationActivities(long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getRelationActivities(
					userId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done by users in a relationship of
	 * type <code>type</code> with the user identified by <code>userId</code>.
	 * This method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param type the relationship type
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getRelationActivities(long userId, int type, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getRelationActivities(
					userId, type, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done by users in a relationship with the
	 * user identified by userId.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	public static int getRelationActivitiesCount(long userId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getRelationActivitiesCount(userId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done by users in a relationship of type
	 * <code>type</code> with the user identified by <code>userId</code>. This
	 * method only counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @param type the relationship type
	 * @return the number of matching activities
	 */
	public static int getRelationActivitiesCount(long userId, int type)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getRelationActivitiesCount(
					userId, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done by the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getUserActivities(long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getUserActivities(
					userId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done by the user.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	public static int getUserActivitiesCount(long userId)
		throws RemoteException {

		try {
			int returnValue = SocialActivityServiceUtil.getUserActivitiesCount(
				userId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done in the user's groups. This
	 * method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getUserGroupsActivities(long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue = SocialActivityServiceUtil.getUserGroupsActivities(
					userId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done in user's groups. This method only
	 * counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	public static int getUserGroupsActivitiesCount(long userId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getUserGroupsActivitiesCount(userId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all the activities done in the user's groups and
	 * organizations. This method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getUserGroupsAndOrganizationsActivities(
				long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue =
					SocialActivityServiceUtil.
						getUserGroupsAndOrganizationsActivities(
							userId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done in user's groups and organizations.
	 * This method only counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	public static int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.
					getUserGroupsAndOrganizationsActivitiesCount(userId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns a range of all activities done in the user's organizations. This
	 * method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	public static com.liferay.social.kernel.model.SocialActivitySoap[]
			getUserOrganizationsActivities(long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.social.kernel.model.SocialActivity>
				returnValue =
					SocialActivityServiceUtil.getUserOrganizationsActivities(
						userId, start, end);

			return com.liferay.social.kernel.model.SocialActivitySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Returns the number of activities done in the user's organizations. This
	 * method only counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	public static int getUserOrganizationsActivitiesCount(long userId)
		throws RemoteException {

		try {
			int returnValue =
				SocialActivityServiceUtil.getUserOrganizationsActivitiesCount(
					userId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivityServiceSoap.class);

}