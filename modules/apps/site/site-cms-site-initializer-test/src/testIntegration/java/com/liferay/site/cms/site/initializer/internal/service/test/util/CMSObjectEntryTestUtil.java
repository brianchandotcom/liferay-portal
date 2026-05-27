/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.service.test.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

/**
 * @author Jürgen Kappler
 */
public class CMSObjectEntryTestUtil {

	public static ObjectEntry addObjectEntry(
			long groupId, ObjectDefinition objectDefinition,
			long objectEntryFolderId)
		throws Exception {

		return ObjectEntryTestUtil.addObjectEntry(
			groupId, objectDefinition, objectEntryFolderId,
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build());
	}

	public static void moveObjectEntryToTrash(
			long groupId, ObjectEntry objectEntry)
		throws Exception {

		ObjectEntryLocalServiceUtil.moveObjectEntryToTrash(
			TestPropsValues.getUserId(), objectEntry,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

}