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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PollsVoteLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PollsVoteLocalService
 * @generated
 */
public class PollsVoteLocalServiceWrapper
	implements PollsVoteLocalService, ServiceWrapper<PollsVoteLocalService> {

	public PollsVoteLocalServiceWrapper(
		PollsVoteLocalService pollsVoteLocalService) {

		_pollsVoteLocalService = pollsVoteLocalService;
	}

	/**
	 * Adds the polls vote to the database. Also notifies the appropriate model listeners.
	 *
	 * @param pollsVote the polls vote
	 * @return the polls vote that was added
	 */
	@Override
	public com.liferay.polls.model.PollsVote addPollsVote(
		com.liferay.polls.model.PollsVote pollsVote) {

		return _pollsVoteLocalService.addPollsVote(pollsVote);
	}

	@Override
	public com.liferay.polls.model.PollsVote addVote(
			long userId, long questionId, long choiceId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pollsVoteLocalService.addVote(
			userId, questionId, choiceId, serviceContext);
	}

	/**
	 * Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	 *
	 * @param voteId the primary key for the new polls vote
	 * @return the new polls vote
	 */
	@Override
	public com.liferay.polls.model.PollsVote createPollsVote(long voteId) {
		return _pollsVoteLocalService.createPollsVote(voteId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pollsVoteLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote that was removed
	 * @throws PortalException if a polls vote with the primary key could not be found
	 */
	@Override
	public com.liferay.polls.model.PollsVote deletePollsVote(long voteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pollsVoteLocalService.deletePollsVote(voteId);
	}

	/**
	 * Deletes the polls vote from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pollsVote the polls vote
	 * @return the polls vote that was removed
	 */
	@Override
	public com.liferay.polls.model.PollsVote deletePollsVote(
		com.liferay.polls.model.PollsVote pollsVote) {

		return _pollsVoteLocalService.deletePollsVote(pollsVote);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _pollsVoteLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _pollsVoteLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _pollsVoteLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _pollsVoteLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _pollsVoteLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _pollsVoteLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.polls.model.PollsVote fetchPollsVote(long voteId) {
		return _pollsVoteLocalService.fetchPollsVote(voteId);
	}

	/**
	 * Returns the polls vote matching the UUID and group.
	 *
	 * @param uuid the polls vote's UUID
	 * @param groupId the primary key of the group
	 * @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public com.liferay.polls.model.PollsVote fetchPollsVoteByUuidAndGroupId(
		String uuid, long groupId) {

		return _pollsVoteLocalService.fetchPollsVoteByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.polls.model.PollsVote fetchQuestionUserVote(
		long questionId, long userId) {

		return _pollsVoteLocalService.fetchQuestionUserVote(questionId, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _pollsVoteLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.polls.model.PollsVote> getChoiceVotes(
		long choiceId, int start, int end) {

		return _pollsVoteLocalService.getChoiceVotes(choiceId, start, end);
	}

	@Override
	public int getChoiceVotesCount(long choiceId) {
		return _pollsVoteLocalService.getChoiceVotesCount(choiceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _pollsVoteLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _pollsVoteLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _pollsVoteLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pollsVoteLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the polls vote with the primary key.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote
	 * @throws PortalException if a polls vote with the primary key could not be found
	 */
	@Override
	public com.liferay.polls.model.PollsVote getPollsVote(long voteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pollsVoteLocalService.getPollsVote(voteId);
	}

	/**
	 * Returns the polls vote matching the UUID and group.
	 *
	 * @param uuid the polls vote's UUID
	 * @param groupId the primary key of the group
	 * @return the matching polls vote
	 * @throws PortalException if a matching polls vote could not be found
	 */
	@Override
	public com.liferay.polls.model.PollsVote getPollsVoteByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _pollsVoteLocalService.getPollsVoteByUuidAndGroupId(
			uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.polls.model.PollsVote> getPollsVotes(
		int start, int end) {

		return _pollsVoteLocalService.getPollsVotes(start, end);
	}

	/**
	 * Returns all the polls votes matching the UUID and company.
	 *
	 * @param uuid the UUID of the polls votes
	 * @param companyId the primary key of the company
	 * @return the matching polls votes, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.polls.model.PollsVote>
		getPollsVotesByUuidAndCompanyId(String uuid, long companyId) {

		return _pollsVoteLocalService.getPollsVotesByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.polls.model.PollsVote>
		getPollsVotesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.polls.model.PollsVote> orderByComparator) {

		return _pollsVoteLocalService.getPollsVotesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of polls votes.
	 *
	 * @return the number of polls votes
	 */
	@Override
	public int getPollsVotesCount() {
		return _pollsVoteLocalService.getPollsVotesCount();
	}

	@Override
	public java.util.List<com.liferay.polls.model.PollsVote> getQuestionVotes(
		long questionId, int start, int end) {

		return _pollsVoteLocalService.getQuestionVotes(questionId, start, end);
	}

	@Override
	public int getQuestionVotesCount(long questionId) {
		return _pollsVoteLocalService.getQuestionVotesCount(questionId);
	}

	/**
	 * Updates the polls vote in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param pollsVote the polls vote
	 * @return the polls vote that was updated
	 */
	@Override
	public com.liferay.polls.model.PollsVote updatePollsVote(
		com.liferay.polls.model.PollsVote pollsVote) {

		return _pollsVoteLocalService.updatePollsVote(pollsVote);
	}

	@Override
	public PollsVoteLocalService getWrappedService() {
		return _pollsVoteLocalService;
	}

	@Override
	public void setWrappedService(PollsVoteLocalService pollsVoteLocalService) {
		_pollsVoteLocalService = pollsVoteLocalService;
	}

	private PollsVoteLocalService _pollsVoteLocalService;

}