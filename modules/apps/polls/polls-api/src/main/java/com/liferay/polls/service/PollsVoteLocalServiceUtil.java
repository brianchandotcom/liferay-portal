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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.polls.model.PollsVote;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for PollsVote. This utility wraps
 * <code>com.liferay.polls.service.impl.PollsVoteLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVoteLocalService
 * @generated
 */
public class PollsVoteLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.polls.service.impl.PollsVoteLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the polls vote to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PollsVoteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param pollsVote the polls vote
	 * @return the polls vote that was added
	 */
	public static PollsVote addPollsVote(PollsVote pollsVote) {
		return getService().addPollsVote(pollsVote);
	}

	public static PollsVote addVote(
			long userId, long questionId, long choiceId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addVote(
			userId, questionId, choiceId, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	 *
	 * @param voteId the primary key for the new polls vote
	 * @return the new polls vote
	 */
	public static PollsVote createPollsVote(long voteId) {
		return getService().createPollsVote(voteId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PollsVoteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote that was removed
	 * @throws PortalException if a polls vote with the primary key could not be found
	 */
	public static PollsVote deletePollsVote(long voteId)
		throws PortalException {

		return getService().deletePollsVote(voteId);
	}

	/**
	 * Deletes the polls vote from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PollsVoteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param pollsVote the polls vote
	 * @return the polls vote that was removed
	 */
	public static PollsVote deletePollsVote(PollsVote pollsVote) {
		return getService().deletePollsVote(pollsVote);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.polls.model.impl.PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.polls.model.impl.PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static PollsVote fetchPollsVote(long voteId) {
		return getService().fetchPollsVote(voteId);
	}

	/**
	 * Returns the polls vote matching the UUID and group.
	 *
	 * @param uuid the polls vote's UUID
	 * @param groupId the primary key of the group
	 * @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	public static PollsVote fetchPollsVoteByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchPollsVoteByUuidAndGroupId(uuid, groupId);
	}

	public static PollsVote fetchQuestionUserVote(
		long questionId, long userId) {

		return getService().fetchQuestionUserVote(questionId, userId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<PollsVote> getChoiceVotes(
		long choiceId, int start, int end) {

		return getService().getChoiceVotes(choiceId, start, end);
	}

	public static int getChoiceVotesCount(long choiceId) {
		return getService().getChoiceVotesCount(choiceId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the polls vote with the primary key.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote
	 * @throws PortalException if a polls vote with the primary key could not be found
	 */
	public static PollsVote getPollsVote(long voteId) throws PortalException {
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
	public static PollsVote getPollsVoteByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getPollsVoteByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.polls.model.impl.PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of polls votes
	 */
	public static List<PollsVote> getPollsVotes(int start, int end) {
		return getService().getPollsVotes(start, end);
	}

	/**
	 * Returns all the polls votes matching the UUID and company.
	 *
	 * @param uuid the UUID of the polls votes
	 * @param companyId the primary key of the company
	 * @return the matching polls votes, or an empty list if no matches were found
	 */
	public static List<PollsVote> getPollsVotesByUuidAndCompanyId(
		String uuid, long companyId) {

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
	public static List<PollsVote> getPollsVotesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return getService().getPollsVotesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of polls votes.
	 *
	 * @return the number of polls votes
	 */
	public static int getPollsVotesCount() {
		return getService().getPollsVotesCount();
	}

	public static List<PollsVote> getQuestionVotes(
		long questionId, int start, int end) {

		return getService().getQuestionVotes(questionId, start, end);
	}

	public static int getQuestionVotesCount(long questionId) {
		return getService().getQuestionVotesCount(questionId);
	}

	/**
	 * Updates the polls vote in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PollsVoteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param pollsVote the polls vote
	 * @return the polls vote that was updated
	 */
	public static PollsVote updatePollsVote(PollsVote pollsVote) {
		return getService().updatePollsVote(pollsVote);
	}

	public static PollsVoteLocalService getService() {
		return _service;
	}

	private static volatile PollsVoteLocalService _service;

}