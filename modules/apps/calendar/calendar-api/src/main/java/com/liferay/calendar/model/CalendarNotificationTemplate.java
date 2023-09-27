/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CalendarNotificationTemplate service. Represents a row in the &quot;CalendarNotificationTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.calendar.model.impl.CalendarNotificationTemplateImpl"
)
@ProviderType
public interface CalendarNotificationTemplate
	extends CalendarNotificationTemplateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.calendar.model.impl.CalendarNotificationTemplateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CalendarNotificationTemplate, Long>
		CALENDAR_NOTIFICATION_TEMPLATE_ID_ACCESSOR =
			new Accessor<CalendarNotificationTemplate, Long>() {

				@Override
				public Long get(
					CalendarNotificationTemplate calendarNotificationTemplate) {

					return calendarNotificationTemplate.
						getCalendarNotificationTemplateId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CalendarNotificationTemplate> getTypeClass() {
					return CalendarNotificationTemplate.class;
				}

			};

	public com.liferay.portal.kernel.util.UnicodeProperties
		getNotificationTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			notificationTypeSettingsUnicodeProperties);

}