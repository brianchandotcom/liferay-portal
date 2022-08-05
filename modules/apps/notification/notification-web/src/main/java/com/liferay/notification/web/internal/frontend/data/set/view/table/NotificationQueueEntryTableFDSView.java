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

package com.liferay.notification.web.internal.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.notification.web.internal.constants.NotificationFDSNames;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "frontend.data.set.name=" + NotificationFDSNames.NOTIFICATION_QUEUE_ENTRIES,
	service = FDSView.class
)
public class NotificationQueueEntryTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		fdsTableSchemaBuilder.add(
			"subject", "subject"
		).add(
			"triggerBy", "trigger-by"
		).add(
			"fromName", "from"
		).add(
			"toName", "to"
		);

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-159052"))) {
			fdsTableSchemaBuilder.add(
				"status", "status",
				fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
					"notificationQueueEntryStatusDataRenderer"));
		}
		else {
			fdsTableSchemaBuilder.add(
				"sent", "status",
				fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
					"notificationQueueEntryStatusDataRenderer"));
		}

		return fdsTableSchemaBuilder.build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}