/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.mediawiki;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.sql.Connection;

import org.jamwiki.model.Namespace;
import org.jamwiki.model.Topic;
import org.jamwiki.model.TopicType;

/**
 * @author Jonathan Potter
 */
public class LiferayDataHandler extends DummyDataHandler {

	@Override
	public Namespace lookupNamespace(
		String virtualWiki, String namespaceString) {

		String label = _fileNamespace.getLabel(virtualWiki);

		if (label.equalsIgnoreCase(namespaceString)) {
			return _fileNamespace;
		}
		else {
			return null;
		}
	}

	@Override
	public Namespace lookupNamespaceById(int namespaceId) {
		return Namespace.DEFAULT_NAMESPACES.get(namespaceId);
	}

	@Override
	public Topic lookupTopic(
		String virtualWiki, String topicName, boolean deleteOK,
		Connection conn) {

		Topic topic = new Topic(virtualWiki, topicName);

		topic.setTopicType(TopicType.IMAGE);

		return topic;
	}

	@Override
	public String lookupTopicName(String virtualWiki, String topicName) {
		long nodeId = getNodeId(virtualWiki);

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(
				nodeId, topicName, true);

			return page.getTitle();
		}
		catch (Exception e) {
		}

		return null;
	}

	protected long getNodeId(String virtualWiki) {
		String nodeId = virtualWiki.replaceAll("Special:Node:(\\d+)", "$1");

		return GetterUtil.getLong(nodeId);
	}

	private Namespace _fileNamespace = Namespace.DEFAULT_NAMESPACES.get(
		Namespace.FILE_ID);

}