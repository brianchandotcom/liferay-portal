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

package com.liferay.object.internal.action.trigger.util;

import com.liferay.object.action.trigger.ObjectActionTrigger;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ObjectActionTriggerUtil {

	public static List<ObjectActionTrigger> getDefaultObjectActionTriggers() {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-166918"))) {
			return ListUtil.filter(
				_defaultObjectActionTriggers,
				objectActionTrigger -> !StringUtil.equals(
					objectActionTrigger.getKey(),
					ObjectActionTriggerConstants.KEY_STANDALONE));
		}

		return _defaultObjectActionTriggers;
	}

	public static List<ObjectActionTrigger> sort(
		List<ObjectActionTrigger> objectActionTriggers) {

		objectActionTriggers.sort(
			(ObjectActionTrigger objectActionTrigger1,
			 ObjectActionTrigger objectActionTrigger2) -> {

				String key1 = objectActionTrigger1.getKey();
				String key2 = objectActionTrigger2.getKey();

				return key1.compareTo(key2);
			});

		return objectActionTriggers;
	}

	private static final List<ObjectActionTrigger>
		_defaultObjectActionTriggers = Collections.unmodifiableList(
			sort(
				Arrays.asList(
					new ObjectActionTrigger(
						ObjectActionTriggerConstants.KEY_ON_AFTER_ADD),
					new ObjectActionTrigger(
						ObjectActionTriggerConstants.
							KEY_ON_AFTER_ATTACHMENT_DOWNLOAD),
					new ObjectActionTrigger(
						ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE),
					new ObjectActionTrigger(
						ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE),
					new ObjectActionTrigger(
						ObjectActionTriggerConstants.KEY_STANDALONE))));

}