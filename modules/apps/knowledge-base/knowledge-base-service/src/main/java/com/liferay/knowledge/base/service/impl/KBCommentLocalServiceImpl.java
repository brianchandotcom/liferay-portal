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

package com.liferay.knowledge.base.service.impl;

import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.knowledge.base.constants.AdminActivityKeys;
import com.liferay.knowledge.base.constants.KBCommentConstants;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.exception.KBCommentContentException;
import com.liferay.knowledge.base.internal.util.AdminSubscriptionSenderFactory;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBTemplateLocalService;
import com.liferay.knowledge.base.service.base.KBCommentLocalServiceBaseImpl;
import com.liferay.knowledge.base.util.comparator.KBCommentCreateDateComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.ratings.kernel.model.RatingsEntry;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 */
@Component(
	property = "model.class.name=com.liferay.knowledge.base.model.KBComment",
	service = AopService.class
)
public class KBCommentLocalServiceImpl extends KBCommentLocalServiceBaseImpl {

	@Override
	public KBComment addKBComment(
			long userId, long classNameId, long classPK, String content,
			int userRating, ServiceContext serviceContext)
		throws PortalException {

		// KB comment

		User user = userLocalService.getUser(userId);
		long groupId = serviceContext.getScopeGroupId();
		Date now = new Date();

		validate(content);

		long kbCommentId = counterLocalService.increment();

		KBComment kbComment = kbCommentPersistence.create(kbCommentId);

		kbComment.setUuid(serviceContext.getUuid());
		kbComment.setGroupId(groupId);
		kbComment.setCompanyId(user.getCompanyId());
		kbComment.setUserId(user.getUserId());
		kbComment.setUserName(user.getFullName());
		kbComment.setCreateDate(serviceContext.getCreateDate(now));
		kbComment.setModifiedDate(serviceContext.getModifiedDate(now));
		kbComment.setClassNameId(classNameId);
		kbComment.setClassPK(classPK);
		kbComment.setContent(content);
		kbComment.setUserRating(userRating);
		kbComment.setStatus(KBCommentConstants.STATUS_NEW);

		kbCommentPersistence.update(kbComment);

		// Social

		JSONObject extraDataJSONObject = _jSONFactory.createJSONObject();

		putTitle(extraDataJSONObject, kbComment);

		socialActivityLocalService.addActivity(
			userId, kbComment.getGroupId(), KBComment.class.getName(),
			kbCommentId, AdminActivityKeys.ADD_KB_COMMENT,
			extraDataJSONObject.toString(), 0);

		// Subscriptions

		notifySubscribers(userId, kbComment, serviceContext);

		return kbComment;
	}

	@Override
	public KBComment addKBComment(
			long userId, long classNameId, long classPK, String content,
			ServiceContext serviceContext)
		throws PortalException {

		int userRating = getUserRating(userId, classNameId, classPK);

		return addKBComment(
			userId, classNameId, classPK, content, userRating, serviceContext);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public KBComment deleteKBComment(KBComment kbComment)
		throws PortalException {

		// KB comment

		kbCommentPersistence.remove(kbComment);

		// Social

		socialActivityLocalService.deleteActivities(
			KBComment.class.getName(), kbComment.getKbCommentId());

		return kbComment;
	}

	@Override
	public KBComment deleteKBComment(long kbCommentId) throws PortalException {
		KBComment kbComment = kbCommentPersistence.findByPrimaryKey(
			kbCommentId);

		return kbCommentLocalService.deleteKBComment(kbComment);
	}

	@Override
	public void deleteKBComments(String className, long classPK)
		throws PortalException {

		List<KBComment> kbComments = kbCommentPersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK);

		for (KBComment kbComment : kbComments) {
			kbCommentLocalService.deleteKBComment(kbComment);
		}
	}

	@Override
	public KBComment getKBComment(long userId, String className, long classPK)
		throws PortalException {

		return kbCommentPersistence.findByU_C_C_Last(
			userId, classNameLocalService.getClassNameId(className), classPK,
			new KBCommentCreateDateComparator());
	}

	@Override
	public List<KBComment> getKBComments(
		long groupId, int status, int start, int end) {

		return kbCommentPersistence.findByG_S(groupId, status, start, end);
	}

	@Override
	public List<KBComment> getKBComments(
		long groupId, int status, int start, int end,
		OrderByComparator<KBComment> obc) {

		return kbCommentPersistence.findByG_S(groupId, status, start, end, obc);
	}

	@Override
	public List<KBComment> getKBComments(
		long groupId, int start, int end, OrderByComparator<KBComment> obc) {

		return kbCommentPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<KBComment> getKBComments(
		long userId, String className, long classPK, int start, int end,
		OrderByComparator<KBComment> orderByComparator) {

		return kbCommentPersistence.findByU_C_C(
			userId, classNameLocalService.getClassNameId(className), classPK,
			start, end, orderByComparator);
	}

	@Override
	public List<KBComment> getKBComments(
		String className, long classPK, int status, int start, int end) {

		return getKBComments(
			className, classPK, status, start, end,
			new KBCommentCreateDateComparator());
	}

	@Override
	public List<KBComment> getKBComments(
		String className, long classPK, int status, int start, int end,
		OrderByComparator<KBComment> obc) {

		return kbCommentPersistence.findByC_C_S(
			classNameLocalService.getClassNameId(className), classPK, status,
			start, end, obc);
	}

	@Override
	public List<KBComment> getKBComments(
		String className, long classPK, int start, int end,
		OrderByComparator orderByComparator) {

		return kbCommentPersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK, start,
			end, orderByComparator);
	}

	@Override
	public List<KBComment> getKBComments(
		String className, long classPK, int[] status, int start, int end) {

		return kbCommentPersistence.findByC_C_S(
			classNameLocalService.getClassNameId(className), classPK, status,
			start, end, new KBCommentCreateDateComparator());
	}

	@Override
	public int getKBCommentsCount(long groupId, int status) {
		return kbCommentPersistence.countByG_S(groupId, status);
	}

	@Override
	public int getKBCommentsCount(long userId, String className, long classPK) {
		return kbCommentPersistence.countByU_C_C(
			userId, classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public int getKBCommentsCount(String className, long classPK) {
		return kbCommentPersistence.countByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public int getKBCommentsCount(String className, long classPK, int status) {
		return kbCommentPersistence.countByC_C_S(
			classNameLocalService.getClassNameId(className), classPK, status);
	}

	@Override
	public int getKBCommentsCount(
		String className, long classPK, int[] status) {

		return kbCommentPersistence.countByC_C_S(
			classNameLocalService.getClassNameId(className), classPK, status);
	}

	@Override
	public KBComment updateKBComment(
			long kbCommentId, long classNameId, long classPK, String content,
			int userRating, int status, ServiceContext serviceContext)
		throws PortalException {

		// KB comment

		validate(content);

		KBComment kbComment = kbCommentPersistence.findByPrimaryKey(
			kbCommentId);

		kbComment.setModifiedDate(serviceContext.getModifiedDate(null));
		kbComment.setClassNameId(classNameId);
		kbComment.setClassPK(classPK);
		kbComment.setContent(content);
		kbComment.setUserRating(userRating);
		kbComment.setStatus(status);

		kbCommentPersistence.update(kbComment);

		// Social

		JSONObject extraDataJSONObject = _jSONFactory.createJSONObject();

		putTitle(extraDataJSONObject, kbComment);

		socialActivityLocalService.addActivity(
			kbComment.getUserId(), kbComment.getGroupId(),
			KBComment.class.getName(), kbCommentId,
			AdminActivityKeys.UPDATE_KB_COMMENT, extraDataJSONObject.toString(),
			0);

		return kbComment;
	}

	@Override
	public KBComment updateKBComment(
			long kbCommentId, long classNameId, long classPK, String content,
			int status, ServiceContext serviceContext)
		throws PortalException {

		KBComment kbComment = kbCommentPersistence.findByPrimaryKey(
			kbCommentId);

		return updateKBComment(
			kbCommentId, classNameId, classPK, content,
			kbComment.getUserRating(), status, serviceContext);
	}

	@Override
	public KBComment updateStatus(
			long userId, long kbCommentId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		KBComment kbComment = kbCommentPersistence.findByPrimaryKey(
			kbCommentId);

		kbComment.setStatus(status);

		kbCommentPersistence.update(kbComment);

		notifySubscribers(userId, kbComment, serviceContext);

		return kbComment;
	}

	protected String getEmailKBArticleSuggestionNotificationBody(
		int status, KBGroupServiceConfiguration kbGroupServiceConfiguration) {

		if (status == KBCommentConstants.STATUS_COMPLETED) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionResolvedBody();
		}
		else if (status == KBCommentConstants.STATUS_IN_PROGRESS) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionInProgressBody();
		}
		else if (status == KBCommentConstants.STATUS_NEW) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionReceivedBody();
		}
		else {
			throw new IllegalArgumentException(
				String.format("Unknown suggestion status %s", status));
		}
	}

	protected String getEmailKBArticleSuggestionNotificationSubject(
		int status, KBGroupServiceConfiguration kbGroupServiceConfiguration) {

		if (status == KBCommentConstants.STATUS_COMPLETED) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionResolvedSubject();
		}
		else if (status == KBCommentConstants.STATUS_IN_PROGRESS) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionInProgressSubject();
		}
		else if (status == KBCommentConstants.STATUS_NEW) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionReceivedSubject();
		}
		else {
			throw new IllegalArgumentException(
				String.format("Unknown suggestion status %s", status));
		}
	}

	protected KBGroupServiceConfiguration getKBGroupServiceConfiguration(
			long groupId)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			KBGroupServiceConfiguration.class,
			new GroupServiceSettingsLocator(groupId, KBConstants.SERVICE_NAME));
	}

	protected int getUserRating(long userId, long classNameId, long classPK)
		throws PortalException {

		ClassName className = classNameLocalService.getClassName(classNameId);

		RatingsEntry ratingsEntry = ratingsEntryLocalService.fetchEntry(
			userId, className.getValue(), classPK);

		if (ratingsEntry == null) {
			return KBCommentConstants.USER_RATING_NONE;
		}

		if (ratingsEntry.getScore() > 0) {
			return KBCommentConstants.USER_RATING_LIKE;
		}

		return KBCommentConstants.USER_RATING_DISLIKE;
	}

	protected boolean isSuggestionStatusChangeNotificationEnabled(
		int status, KBGroupServiceConfiguration kbGroupServiceConfiguration) {

		if (status == KBCommentConstants.STATUS_COMPLETED) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionResolvedEnabled();
		}
		else if (status == KBCommentConstants.STATUS_IN_PROGRESS) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionInProgressEnabled();
		}
		else if (status == KBCommentConstants.STATUS_NEW) {
			return kbGroupServiceConfiguration.
				emailKBArticleSuggestionReceivedEnabled();
		}

		return false;
	}

	protected void notifySubscribers(
			long userId, KBComment kbComment, ServiceContext serviceContext)
		throws PortalException {

		KBGroupServiceConfiguration kbGroupServiceConfiguration =
			getKBGroupServiceConfiguration(kbComment.getGroupId());

		if (!isSuggestionStatusChangeNotificationEnabled(
				kbComment.getStatus(), kbGroupServiceConfiguration)) {

			return;
		}

		String fromName = kbGroupServiceConfiguration.emailFromName();
		String fromAddress = kbGroupServiceConfiguration.emailFromAddress();

		String subject = getEmailKBArticleSuggestionNotificationSubject(
			kbComment.getStatus(), kbGroupServiceConfiguration);
		String body = getEmailKBArticleSuggestionNotificationBody(
			kbComment.getStatus(), kbGroupServiceConfiguration);

		KBArticle kbArticle = _kbArticleLocalService.getLatestKBArticle(
			kbComment.getClassPK(), WorkflowConstants.STATUS_APPROVED);

		String kbArticleContent = StringUtil.replace(
			kbArticle.getContent(), new String[] {"href=\"/", "src=\"/"},
			new String[] {
				"href=\"" + serviceContext.getPortalURL() + "/",
				"src=\"" + serviceContext.getPortalURL() + "/"
			});

		SubscriptionSender subscriptionSender =
			AdminSubscriptionSenderFactory.createSubscriptionSender(
				kbArticle, serviceContext);

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(kbArticle.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$ARTICLE_CONTENT$]", kbArticleContent, false);
		subscriptionSender.setContextAttribute(
			"[$COMMENT_CONTENT$]", kbComment.getContent(), false);
		subscriptionSender.setContextCreatorUserPrefix("ARTICLE");
		subscriptionSender.setCreatorUserId(kbArticle.getUserId());
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedContextAttributeWithFunction(
			"[$COMMENT_CREATE_DATE$]",
			locale -> _getFormattedKBCommentCreateDate(kbComment, locale),
			false);
		subscriptionSender.setMailId("kb_article", kbArticle.getKbArticleId());
		subscriptionSender.setPortletId(serviceContext.getPortletId());
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(kbArticle.getGroupId());
		subscriptionSender.setSubject(subject);

		User user = userLocalService.getUser(kbComment.getUserId());

		subscriptionSender.addRuntimeSubscribers(
			user.getEmailAddress(), user.getFullName());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void putTitle(JSONObject jsonObject, KBComment kbComment) {
		KBArticle kbArticle = null;
		KBTemplate kbTemplate = null;

		String className = kbComment.getClassName();

		try {
			if (className.equals(KBArticle.class.getName())) {
				kbArticle = _kbArticleLocalService.getLatestKBArticle(
					kbComment.getClassPK(), WorkflowConstants.STATUS_APPROVED);

				jsonObject.put("title", kbArticle.getTitle());
			}
			else if (className.equals(KBTemplate.class.getName())) {
				kbTemplate = _kbTemplateLocalService.getKBTemplate(
					kbComment.getClassPK());

				jsonObject.put("title", kbTemplate.getTitle());
			}
		}
		catch (Exception e) {
			_log.error("Unable to put title", e);
		}
	}

	protected void validate(String content) throws PortalException {
		if (Validator.isNull(content)) {
			throw new KBCommentContentException();
		}
	}

	private String _getFormattedKBCommentCreateDate(
		KBComment kbComment, Locale locale) {

		DateFormat dateFormat = DateFormatFactoryUtil.getDate(locale);

		return dateFormat.format(kbComment.getCreateDate());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBCommentLocalServiceImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jSONFactory;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private KBTemplateLocalService _kbTemplateLocalService;

}