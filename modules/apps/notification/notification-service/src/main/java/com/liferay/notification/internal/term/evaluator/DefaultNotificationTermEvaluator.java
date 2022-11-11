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

package com.liferay.notification.internal.term.evaluator;

import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(service = DefaultNotificationTermEvaluator.class)
public class DefaultNotificationTermEvaluator
	implements NotificationTermEvaluator {

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map)) {
			return termName;
		}

		Map<String, String> termValues = (Map<String, String>)object;

		return termValues.get(termName);
	}

}