/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.exception.NotificationRecipientSettingValueException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.type.BaseNotificationType;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.object.action.util.ObjectActionThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.EmailAddressValidatorFactory;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.transformer.TemplateNodeFactory;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = NotificationType.class)
public class EmailNotificationType extends BaseNotificationType {

	@Override
	public Set<String> getAllowedNotificationRecipientSettingsNames() {
		return SetUtil.fromArray(
			NotificationRecipientSettingConstants.NAME_BCC,
			NotificationRecipientSettingConstants.NAME_CC,
			NotificationRecipientSettingConstants.NAME_FROM,
			NotificationRecipientSettingConstants.NAME_FROM_NAME,
			NotificationRecipientSettingConstants.NAME_SINGLE_RECIPIENT,
			NotificationRecipientSettingConstants.NAME_TO);
	}

	@Override
	public String getFromName(NotificationQueueEntry notificationQueueEntry) {
		Map<String, Object> notificationRecipientSettingsMap =
			NotificationRecipientSettingUtil.
				getNotificationRecipientSettingsMap(notificationQueueEntry);

		return String.valueOf(
			notificationRecipientSettingsMap.get(
				NotificationRecipientSettingConstants.NAME_FROM_NAME));
	}

	@Override
	public String getRecipientSummary(
		NotificationQueueEntry notificationQueueEntry) {

		Map<String, Object> notificationRecipientSettingsMap =
			NotificationRecipientSettingUtil.
				getNotificationRecipientSettingsMap(notificationQueueEntry);

		return String.valueOf(
			notificationRecipientSettingsMap.get(
				NotificationRecipientSettingConstants.NAME_TO));
	}

	@Override
	public String getType() {
		return NotificationConstants.TYPE_EMAIL;
	}

	@Override
	public String getTypeLanguageKey() {
		return "email";
	}

	@Override
	public void resendNotification(
			NotificationQueueEntry notificationQueueEntry)
		throws PortalException {

		if (notificationQueueEntry.getStatus() !=
				NotificationQueueEntryConstants.STATUS_UNSENT) {

			notificationQueueEntry =
				notificationQueueEntryLocalService.updateStatus(
					notificationQueueEntry.getNotificationQueueEntryId(),
					NotificationQueueEntryConstants.STATUS_UNSENT);
		}

		sendNotification(notificationQueueEntry);
	}

	@Override
	public void resendNotifications(int status, String type)
		throws PortalException {

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntryLocalService.getNotificationEntries(
					type, status)) {

			resendNotification(notificationQueueEntry);
		}
	}

	@Override
	public void sendNotification(NotificationContext notificationContext)
		throws PortalException {

		long groupId = 0;

		User user = userLocalService.getUser(notificationContext.getUserId());

		Group userGroup = user.getGroup();

		if (userGroup != null) {
			groupId = userGroup.getGroupId();
		}

		siteDefaultLocale = portal.getSiteDefaultLocale(groupId);

		userLocale = user.getLocale();

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		String body = _formatBody(
			notificationTemplate.getBodyMap(), groupId, notificationContext);
		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();
		String subject = formatLocalizedContent(
			notificationTemplate.getSubjectMap(), notificationContext);

		Map<String, String> evaluatedNotificationRecipientSettings =
			HashMapBuilder.put(
				NotificationRecipientSettingConstants.NAME_BCC,
				formatContent(
					NotificationRecipientSettingConstants.NAME_BCC,
					notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				NotificationRecipientSettingConstants.NAME_CC,
				formatContent(
					NotificationRecipientSettingConstants.NAME_CC,
					notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				NotificationRecipientSettingConstants.NAME_FROM,
				formatContent(
					NotificationRecipientSettingConstants.NAME_FROM,
					notificationContext,
					notificationRecipient.getNotificationRecipientId())
			).put(
				NotificationRecipientSettingConstants.NAME_FROM_NAME,
				() -> {
					NotificationRecipientSetting notificationRecipientSetting =
						notificationRecipientSettingLocalService.
							fetchNotificationRecipientSetting(
								notificationRecipient.
									getNotificationRecipientId(),
								NotificationRecipientSettingConstants.
									NAME_FROM_NAME);

					return formatLocalizedContent(
						notificationRecipientSetting.getValueMap(),
						notificationContext);
				}
			).put(
				NotificationRecipientSettingConstants.NAME_SINGLE_RECIPIENT,
				() -> {
					NotificationRecipientSetting notificationRecipientSetting =
						notificationRecipientSettingLocalService.
							fetchNotificationRecipientSetting(
								notificationRecipient.
									getNotificationRecipientId(),
								NotificationRecipientSettingConstants.
									NAME_SINGLE_RECIPIENT);

					if (notificationRecipientSetting == null) {
						return Boolean.TRUE.toString();
					}

					return notificationRecipientSetting.getValue();
				}
			).put(
				NotificationRecipientSettingConstants.NAME_TO,
				() -> {
					NotificationRecipientSetting notificationRecipientSetting =
						notificationRecipientSettingLocalService.
							fetchNotificationRecipientSetting(
								notificationRecipient.
									getNotificationRecipientId(),
								NotificationRecipientSettingConstants.NAME_TO);

					String to = notificationRecipientSetting.getValue(
						user.getLocale());

					if (Validator.isNull(to)) {
						to = notificationRecipientSetting.getValue(
							siteDefaultLocale);
					}

					return _formatTo(
						formatLocalizedContent(to, notificationContext));
				}
			).build();

		String validEmailAddresses = _getValidEmailAddresses(
			user.getCompanyId(),
			evaluatedNotificationRecipientSettings.get(
				NotificationRecipientSettingConstants.NAME_TO));

		if (!GetterUtil.getBoolean(
				evaluatedNotificationRecipientSettings.get(
					NotificationRecipientSettingConstants.
						NAME_SINGLE_RECIPIENT))) {

			prepareNotificationContext(
				user, body, notificationContext,
				HashMapBuilder.putAll(
					evaluatedNotificationRecipientSettings
				).put(
					NotificationRecipientSettingConstants.NAME_TO,
					validEmailAddresses
				).build(),
				subject);

			sendNotification(
				notificationQueueEntryLocalService.addNotificationQueueEntry(
					notificationContext));

			return;
		}

		for (String emailAddress : StringUtil.split(validEmailAddresses)) {
			User emailAddressUser = userLocalService.fetchUserByEmailAddress(
				user.getCompanyId(), emailAddress);

			if (emailAddressUser == null) {
				emailAddressUser = userLocalService.getGuestUser(
					CompanyThreadLocal.getCompanyId());
			}

			prepareNotificationContext(
				emailAddressUser, body, notificationContext,
				HashMapBuilder.putAll(
					evaluatedNotificationRecipientSettings
				).put(
					NotificationRecipientSettingConstants.NAME_TO, emailAddress
				).build(),
				subject);

			sendNotification(
				notificationQueueEntryLocalService.addNotificationQueueEntry(
					notificationContext));
		}
	}

	@Override
	public void sendNotification(
		NotificationQueueEntry notificationQueueEntry) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					Map<String, Object> notificationRecipientSettingsMap =
						NotificationRecipientSettingUtil.
							getNotificationRecipientSettingsMap(
								notificationQueueEntry);

					MailMessage mailMessage = new MailMessage(
						new InternetAddress(
							String.valueOf(
								notificationRecipientSettingsMap.get(
									NotificationRecipientSettingConstants.
										NAME_FROM)),
							String.valueOf(
								notificationRecipientSettingsMap.get(
									NotificationRecipientSettingConstants.
										NAME_FROM_NAME))),
						notificationQueueEntry.getSubject(),
						notificationQueueEntry.getBody(), true);

					_addFileAttachments(
						mailMessage,
						notificationQueueEntry.getNotificationQueueEntryId());

					mailMessage.setBCC(
						_toInternetAddresses(
							String.valueOf(
								notificationRecipientSettingsMap.get(
									NotificationRecipientSettingConstants.
										NAME_BCC))));
					mailMessage.setCC(
						_toInternetAddresses(
							String.valueOf(
								notificationRecipientSettingsMap.get(
									NotificationRecipientSettingConstants.
										NAME_CC))));
					mailMessage.setTo(
						_toInternetAddresses(
							String.valueOf(
								notificationRecipientSettingsMap.get(
									NotificationRecipientSettingConstants.
										NAME_TO))));

					MessageBusUtil.sendMessage(
						DestinationNames.MAIL, mailMessage);

					notificationQueueEntryLocalService.updateStatus(
						notificationQueueEntry.getNotificationQueueEntryId(),
						NotificationQueueEntryConstants.STATUS_SENT);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}

					if (notificationQueueEntry.getStatus() !=
							NotificationQueueEntryConstants.STATUS_FAILED) {

						notificationQueueEntryLocalService.updateStatus(
							notificationQueueEntry.
								getNotificationQueueEntryId(),
							NotificationQueueEntryConstants.STATUS_FAILED);
					}
				}

				return null;
			});
	}

	@Override
	public Object[] toRecipients(
		List<NotificationRecipientSetting> notificationRecipientSettings) {

		return new Object[] {
			NotificationRecipientSettingUtil.toMap(
				notificationRecipientSettings)
		};
	}

	@Override
	public void validateNotificationQueueEntry(
			NotificationContext notificationContext)
		throws PortalException {

		super.validateNotificationQueueEntry(notificationContext);

		_validateNotificationRecipientSettings(
			NotificationRecipientSettingUtil.toMap(
				notificationContext.getNotificationRecipientSettings()));
	}

	@Override
	public void validateNotificationTemplate(
			NotificationContext notificationContext)
		throws PortalException {

		super.validateNotificationTemplate(notificationContext);

		_validateNotificationRecipientSettings(
			NotificationRecipientSettingUtil.toMap(
				notificationContext.getNotificationRecipientSettings()));
	}

	private void _addFileAttachments(
		MailMessage mailMessage, long notificationQueueEntryId) {

		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				_notificationQueueEntryAttachmentLocalService.
					getNotificationQueueEntryNotificationQueueEntryAttachments(
						notificationQueueEntryId)) {

			try {
				FileEntry fileEntry =
					_portletFileRepository.getPortletFileEntry(
						notificationQueueEntryAttachment.getFileEntryId());

				mailMessage.addFileAttachment(
					FileUtil.createTempFile(fileEntry.getContentStream()),
					fileEntry.getFileName());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}
	}

	private String _formatBody(
			Map<Locale, String> bodyMap, long groupId,
			NotificationContext notificationContext)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		if (Objects.equals(
				NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
				notificationTemplate.getEditorType())) {

			return formatLocalizedContent(bodyMap, notificationContext);
		}

		StringWriter stringWriter = new StringWriter();

		String body = notificationTemplate.getBody(userLocale);

		if (Validator.isNull(body)) {
			body = notificationTemplate.getBody(siteDefaultLocale);
		}

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource(
				NotificationTemplate.class.getName() + StringPool.POUND +
					notificationTemplate.getNotificationTemplateId(),
				body),
			!PropsValues.NOTIFICATION_EMAIL_TEMPLATE_ENABLED);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(siteDefaultLocale);

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				notificationContext.getClassName());
		PersistedModelLocalService persistedModelLocalService =
			PersistedModelLocalServiceRegistryUtil.
				getPersistedModelLocalService(
					notificationContext.getClassName());

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(
				_groupLocalService.getGroup(groupId),
				notificationContext.getUserId()));

		try {
			InfoItemFieldValues infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(
					persistedModelLocalService.getPersistedModel(
						notificationContext.getClassPK()));

			for (InfoFieldValue<Object> infoFieldValue :
					infoItemFieldValues.getInfoFieldValues()) {

				InfoField<?> infoField = infoFieldValue.getInfoField();

				if (StringUtil.startsWith(
						infoField.getName(),
						PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

					continue;
				}

				TemplateNode templateNode =
					_templateNodeFactory.createTemplateNode(
						infoFieldValue, themeDisplay);

				template.put(infoField.getName(), templateNode);
				template.put(infoField.getUniqueId(), templateNode);
			}

			HttpServletRequest httpServletRequest =
				ObjectActionThreadLocal.getHttpServletRequest();

			if (httpServletRequest != null) {
				template.put(
					"portalURL", portal.getPortalURL(httpServletRequest));
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		template.processTemplate(stringWriter);

		return stringWriter.toString();
	}

	private String _formatTo(String to) {
		if (Validator.isNull(to)) {
			return StringPool.BLANK;
		}

		Set<String> emailAddresses = new HashSet<>();

		Matcher matcher = _emailAddressPattern.matcher(to);

		while (matcher.find()) {
			emailAddresses.add(matcher.group());
		}

		return StringUtil.merge(emailAddresses);
	}

	private ServiceContext _getServiceContext(Group group, long userId) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setLanguageId(
			_language.getLanguageId(siteDefaultLocale));
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private String _getValidEmailAddresses(
		long companyId, String emailAddresses) {

		StringBundler sb = new StringBundler();

		for (String emailAddress : StringUtil.split(emailAddresses)) {
			EmailAddressValidator emailAddressValidator =
				EmailAddressValidatorFactory.getInstance();

			if (!emailAddressValidator.validate(companyId, emailAddress)) {
				if (_log.isInfoEnabled()) {
					_log.info("Invalid email address " + emailAddress);
				}

				continue;
			}

			if (sb.index() > 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(emailAddress);
		}

		return sb.toString();
	}

	private InternetAddress[] _toInternetAddresses(String string)
		throws Exception {

		List<InternetAddress> internetAddresses = new ArrayList<>();

		for (String internetAddressString : StringUtil.split(string)) {
			internetAddresses.add(new InternetAddress(internetAddressString));
		}

		return internetAddresses.toArray(new InternetAddress[0]);
	}

	private void _validateNotificationRecipientSettings(
			Map<String, Object> notificationRecipientSettingsMap)
		throws PortalException {

		if (Validator.isNull(
				notificationRecipientSettingsMap.get(
					NotificationRecipientSettingConstants.NAME_FROM))) {

			throw new NotificationRecipientSettingValueException.
				FromMustNotBeNull();
		}

		if (Validator.isNull(
				notificationRecipientSettingsMap.get(
					NotificationRecipientSettingConstants.NAME_FROM_NAME))) {

			throw new NotificationRecipientSettingValueException.
				FromNameMustNotBeNull();
		}

		if (Validator.isNull(
				notificationRecipientSettingsMap.get(
					NotificationRecipientSettingConstants.NAME_TO))) {

			throw new NotificationRecipientSettingValueException.
				ToMustNotBeNull();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmailNotificationType.class);

	private static final Pattern _emailAddressPattern = Pattern.compile(
		"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
			"(?:\\w(?:[\\w-]*\\w)?\\.)+(\\w(?:[\\w-]*\\w))");

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private TemplateNodeFactory _templateNodeFactory;

}