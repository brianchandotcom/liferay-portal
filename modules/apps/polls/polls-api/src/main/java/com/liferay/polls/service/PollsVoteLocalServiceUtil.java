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

package com.liferay.polls.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for PollsVote. This utility wraps
 * {@link com.liferay.polls.service.impl.PollsVoteLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVoteLocalService
 * @see com.liferay.polls.service.base.PollsVoteLocalServiceBaseImpl
 * @see com.liferay.polls.service.impl.PollsVoteLocalServiceImpl
 * @generated
 */
@ProviderType
public class PollsVoteLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.polls.service.impl.PollsVoteLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the polls vote to the database. Also notifies the appropriate model listeners.
	*
	* @param pollsVote the polls vote
	* @return the polls vote that was added
	*/
	public static com.liferay.polls.model.PollsVote addPollsVote(
		com.liferay.polls.model.PollsVote pollsVote) {
		return getService().addPollsVote(pollsVote);
	}

	public static com.liferay.polls.model.PollsVote addVote(long userId,
		long questionId, long choiceId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addVote(userId, questionId, choiceId, serviceContext);
	}

	/**
	* Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	*
	* @param voteId the primary key for the new polls vote
	* @return the new polls vote
	*/
	public static com.liferay.polls.model.PollsVote createPollsVote(long voteId) {
		return getService().createPollsVote(voteId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the polls vote from the database. Also notifies the appropriate model listeners.
	*
	* @param pollsVote the polls vote
	* @return the polls vote that was removed
	*/
	public static com.liferay.polls.model.PollsVote deletePollsVote(
		com.liferay.polls.model.PollsVote pollsVote) {
		return getService().deletePollsVote(pollsVote);
	}

	/**
	* Deletes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote that was removed
	* @throws PortalException if a polls vote with the primary key could not be found
	*/
	public static com.liferay.polls.model.PollsVote deletePollsVote(long voteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePollsVote(voteId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.polls.model.impl.PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.polls.model.impl.PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.polls.model.PollsVote fetchPollsVote(long voteId) {
		return getService().fetchPollsVote(voteId);
	}

	/**
	* Returns the polls vote matching the UUID and group.
	*
	* @param uuid the polls vote's UUID
	* @param groupId the primary key of the group
	* @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	*/
	public static com.liferay.polls.model.PollsVote fetchPollsVoteByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchPollsVoteByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.polls.model.PollsVote> getChoiceVotes(
		long choiceId, int start, int end) {
		return getService().getChoiceVotes(choiceId, start, end);
	}

	public static int getChoiceVotesCount(long choiceId) {
		return getService().getChoiceVotesCount(choiceId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the polls vote with the primary key.
	*
	* @param voteId the primary key of the polls vote
	* @return the polls vote
	* @throws PortalException if a polls vote with the primary key could not be found
	*/
	public static com.liferay.polls.model.PollsVote getPollsVote(long voteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPollsVote(voteId);
	}

	/**
	* Returns the polls vote matching the UUID and group.
	*
	* @param uuid the polls vote's UUID
	* @param groupId the primary key of the group
	* @return the matching polls vote
	* @throws PortalException if a matching polls vote could not be found
	*/
	public static com.liferay.polls.model.PollsVote getPollsVoteByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPollsVoteByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the polls votes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.polls.model.impl.PollsVoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @return the range of polls votes
	*/
	public static java.util.List<com.liferay.polls.model.PollsVote> getPollsVotes(
		int start, int end) {
		return getService().getPollsVotes(start, end);
	}

	/**
	* Returns all the polls votes matching the UUID and company.
	*
	* @param uuid the UUID of the polls votes
	* @param companyId the primary key of the company
	* @return the matching polls votes, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.polls.model.PollsVote> getPollsVotesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getPollsVotesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of polls votes matching the UUID and company.
	*
	* @param uuid the UUID of the polls votes
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of polls votes
	* @param end the upper bound of the range of polls votes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching polls votes, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.polls.model.PollsVote> getPollsVotesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.polls.model.PollsVote> orderByComparator) {
		return getService()
				   .getPollsVotesByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of polls votes.
	*
	* @return the number of polls votes
	*/
	public static int getPollsVotesCount() {
		return getService().getPollsVotesCount();
	}

	public static java.util.List<com.liferay.polls.model.PollsVote> getQuestionVotes(
		long questionId, int start, int end) {
		return getService().getQuestionVotes(questionId, start, end);
	}

	public static int getQuestionVotesCount(long questionId) {
		return getService().getQuestionVotesCount(questionId);
	}

	public static com.liferay.polls.model.PollsVote getVote(long questionId,
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getVote(questionId, userId);
	}

	/**
	* Updates the polls vote in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param pollsVote the polls vote
	* @return the polls vote that was updated
	*/
	public static com.liferay.polls.model.PollsVote updatePollsVote(
		com.liferay.polls.model.PollsVote pollsVote) {
		return getService().updatePollsVote(pollsVote);
	}

	public static PollsVoteLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(PollsVoteLocalService service) {
	}

	private static ServiceTracker<PollsVoteLocalService, PollsVoteLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(PollsVoteLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<PollsVoteLocalService, PollsVoteLocalService>(bundle.getBundleContext(),
				PollsVoteLocalService.class, null);

		_serviceTracker.open();
	}
}