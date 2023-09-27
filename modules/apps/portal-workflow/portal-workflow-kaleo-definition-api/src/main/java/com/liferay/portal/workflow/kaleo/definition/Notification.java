/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class Notification {

	public Notification(
			String name, String description, String executionType,
			String template, String templateLanguage)
		throws KaleoDefinitionValidationException {

		_name = name;
		_description = description;

		if (Validator.isNotNull(executionType)) {
			_executionType = ExecutionType.parse(executionType);
		}
		else {
			_executionType = ExecutionType.ON_TIMER;
		}

		_template = template;
		_templateLanguage = TemplateLanguage.parse(templateLanguage);
	}

	public void addNotificationType(String notificationType)
		throws KaleoDefinitionValidationException {

		_notificationTypes.add(NotificationType.parse(notificationType));
	}

	public void addRecipients(Recipient recipient) {
		Set<Recipient> recipients = _recipientsMap.get(
			recipient.getNotificationReceptionType());

		if (recipients == null) {
			recipients = new HashSet<>();
		}

		recipients.add(recipient);

		_recipientsMap.put(
			recipient.getNotificationReceptionType(), recipients);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Notification)) {
			return false;
		}

		Notification notification = (Notification)object;

		if (Objects.equals(_name, notification._name)) {
			return true;
		}

		return true;
	}

	public String getDescription() {
		return _description;
	}

	public ExecutionType getExecutionType() {
		return _executionType;
	}

	public String getName() {
		return _name;
	}

	public Set<NotificationType> getNotificationTypes() {
		return _notificationTypes;
	}

	public Map<NotificationReceptionType, Set<Recipient>> getRecipientsMap() {
		return _recipientsMap;
	}

	public String getTemplate() {
		return _template;
	}

	public TemplateLanguage getTemplateLanguage() {
		return _templateLanguage;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	private final String _description;
	private final ExecutionType _executionType;
	private final String _name;
	private final Set<NotificationType> _notificationTypes = new HashSet<>();
	private final Map<NotificationReceptionType, Set<Recipient>>
		_recipientsMap = new HashMap<>();
	private final String _template;
	private final TemplateLanguage _templateLanguage;

}