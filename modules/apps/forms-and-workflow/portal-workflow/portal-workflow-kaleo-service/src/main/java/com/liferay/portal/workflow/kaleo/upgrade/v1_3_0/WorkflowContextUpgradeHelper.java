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

package com.liferay.portal.workflow.kaleo.upgrade.v1_3_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.sql.PreparedStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lino Alves
 */
public abstract class BaseWorkflowContextUpgradeProcess extends UpgradeProcess {

	protected boolean isEntryClassNameRenamed(
		Map<String, Serializable> workflowContext) {

		String oldEntryClassName = (String)workflowContext.get(
			"entryClassName");

		if (classNamesMap.get(oldEntryClassName) != null) {
			return true;
		}

		return false;
	}

	protected String renamePortalJavaClassNames(String workflowContextJSON) {
		Matcher matcher = _javaClassPattern.matcher(workflowContextJSON);

		Set<String> oldSubs = new TreeSet<>();
		Set<String> newSubs = new TreeSet<>();

		while (matcher.find()) {
			String oldPortalJavaClassName = matcher.group(1);

			if (oldPortalJavaClassName.contains(".impl") ||
				oldPortalJavaClassName.contains(".kernel") ||
				oldSubs.contains(oldPortalJavaClassName)) {

				continue;
			}

			oldSubs.add("\"javaClass\":\"" + oldPortalJavaClassName + "\"");

			String newPortalJavaClassName = StringUtil.replace(
				oldPortalJavaClassName, "com.liferay.portal",
				"com.liferay.portal.kernel");

			newSubs.add("\"javaClass\":\"" + newPortalJavaClassName + "\"");

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Workflow context Java class name renamed " +
							"from \"%s\" to \"%s\"",
						oldPortalJavaClassName, newPortalJavaClassName));
			}
		}

		if (oldSubs.isEmpty()) {
			return workflowContextJSON;
		}

		return StringUtil.replace(
			workflowContextJSON, ArrayUtil.toStringArray(oldSubs),
			ArrayUtil.toStringArray(newSubs));
	}

	protected void replaceEntryClassName(
		Map<String, Serializable> workflowContext) {

		String oldEntryClassName = (String)workflowContext.get(
			"entryClassName");

		String newEntryClassName = classNamesMap.get(oldEntryClassName);

		if (newEntryClassName != null) {
			workflowContext.put("entryClassName", newEntryClassName);
		}
	}

	protected void updateWorkflowContext(
			String tableName, String primaryKeyName, long primaryKeyValue,
			String workflowContext)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update " + tableName + " set workflowContext = ? where " +
					primaryKeyName + " = ?")) {

			ps.setString(1, workflowContext);
			ps.setLong(2, primaryKeyValue);

			ps.executeUpdate();
		}
	}

	protected static final Map<String, String> classNamesMap = new HashMap<>();

	static {
		classNamesMap.put(
			"com.liferay.portal.model.Company",
			"com.liferay.portal.kernel.model.Company");
		classNamesMap.put(
			"com.liferay.portal.model.Group",
			"com.liferay.portal.kernel.model.Group");
		classNamesMap.put(
			"com.liferay.portal.model.LayoutRevision",
			"com.liferay.portal.kernel.model.LayoutRevision");
		classNamesMap.put(
			"com.liferay.portal.model.Role",
			"com.liferay.portal.kernel.model.Role");
		classNamesMap.put(
			"com.liferay.portal.model.User",
			"com.liferay.portal.kernel.model.User");
		classNamesMap.put(
			"com.liferay.portal.model.UserGroup",
			"com.liferay.portal.kernel.model.UserGroup");
		classNamesMap.put(
			"com.liferay.portlet.blogs.model.BlogsEntry",
			"com.liferay.blogs.kernel.model.BlogsEntry");
		classNamesMap.put(
			"com.liferay.portlet.documentlibrary.model.DLFileEntry",
			"com.liferay.document.library.kernel.model.DLFileEntry");
		classNamesMap.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecord",
			"com.liferay.dynamic.data.lists.model.DDLRecord");
		classNamesMap.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			"com.liferay.journal.model.JournalArticle");
		classNamesMap.put(
			"com.liferay.portlet.messageboards.model.MBDiscussion",
			"com.liferay.message.boards.kernel.model.MBDiscussion");
		classNamesMap.put(
			"com.liferay.portlet.messageboards.model.MBMessage",
			"com.liferay.message.boards.kernel.model.MBMessage");
		classNamesMap.put(
			"com.liferay.portlet.wiki.model.WikiPage",
			"com.liferay.wiki.model.WikiPage");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowContextUpgradeProcess.class);

	private static final Pattern _javaClassPattern = Pattern.compile(
		"\"javaClass\":\"(com.liferay.portal.[^\"]+)\"");

}