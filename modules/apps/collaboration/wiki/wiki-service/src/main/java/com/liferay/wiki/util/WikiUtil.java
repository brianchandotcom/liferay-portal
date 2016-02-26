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

package com.liferay.wiki.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.util.comparator.PageVersionComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class WikiUtil {

	public static String getAttachmentURLPrefix(
		String mainPath, long plid, long nodeId, String title) {

		StringBundler sb = new StringBundler(8);

		sb.append(mainPath);
		sb.append("/wiki/get_page_attachment?p_l_id=");
		sb.append(plid);
		sb.append("&nodeId=");
		sb.append(nodeId);
		sb.append("&title=");
		sb.append(HttpUtil.encodeURL(title));
		sb.append("&fileName=");

		return sb.toString();
	}

	public static DiffVersionsInfo getDiffVersionsInfo(
		long nodeId, String title, double sourceVersion, double targetVersion,
		HttpServletRequest request) {

		double previousVersion = 0;
		double nextVersion = 0;

		List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
			nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new PageVersionComparator(true));

		for (WikiPage page : pages) {
			if ((page.getVersion() < sourceVersion) &&
				(page.getVersion() > previousVersion)) {

				previousVersion = page.getVersion();
			}

			if ((page.getVersion() > targetVersion) &&
				((page.getVersion() < nextVersion) || (nextVersion == 0))) {

				nextVersion = page.getVersion();
			}
		}

		List<DiffVersion> diffVersions = new ArrayList<>();

		for (WikiPage page : pages) {
			String extraInfo = StringPool.BLANK;

			if (page.isMinorEdit()) {
				extraInfo = LanguageUtil.get(request, "minor-edit");
			}

			DiffVersion diffVersion = new DiffVersion(
				page.getUserId(), page.getVersion(), page.getModifiedDate(),
				page.getSummary(), extraInfo);

			diffVersions.add(diffVersion);
		}

		return new DiffVersionsInfo(diffVersions, nextVersion, previousVersion);
	}

	public static List<String> getNodeNames(List<WikiNode> nodes) {
		List<String> nodeNames = new ArrayList<>(nodes.size());

		for (WikiNode node : nodes) {
			nodeNames.add(node.getName());
		}

		return nodeNames;
	}

	public static List<WikiNode> getNodes(
		List<WikiNode> nodes, String[] hiddenNodes,
		PermissionChecker permissionChecker) {

		nodes = ListUtil.copy(nodes);

		Arrays.sort(hiddenNodes);

		Iterator<WikiNode> itr = nodes.iterator();

		while (itr.hasNext()) {
			WikiNode node = itr.next();

			if (!(Arrays.binarySearch(hiddenNodes, node.getName()) < 0) ||
				!WikiNodePermissionChecker.contains(
					permissionChecker, node, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return nodes;
	}

	public static List<WikiNode> orderNodes(
		List<WikiNode> nodes, String[] visibleNodeNames) {

		if (ArrayUtil.isEmpty(visibleNodeNames)) {
			return nodes;
		}

		nodes = ListUtil.copy(nodes);

		List<WikiNode> orderedNodes = new ArrayList<>(nodes.size());

		for (String visibleNodeName : visibleNodeNames) {
			for (WikiNode node : nodes) {
				if (node.getName().equals(visibleNodeName)) {
					orderedNodes.add(node);

					nodes.remove(node);

					break;
				}
			}
		}

		orderedNodes.addAll(nodes);

		return orderedNodes;
	}

	public static String processContent(String content) {
		content = StringUtil.replace(content, "</p>", "</p>\n");
		content = StringUtil.replace(content, "</br>", "</br>\n");
		content = StringUtil.replace(content, "</div>", "</div>\n");

		return content;
	}

}