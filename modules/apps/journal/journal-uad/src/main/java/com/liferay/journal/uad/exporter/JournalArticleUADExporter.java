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

package com.liferay.journal.uad.exporter;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@Component(immediate = true, service = UADExporter.class)
public class JournalArticleUADExporter extends BaseJournalArticleUADExporter {

	@Override
	protected String toXmlString(JournalArticle journalArticle) {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.journal.model.JournalArticle");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>id</column-name><column-value><![CDATA[");
		sb.append(journalArticle.getId());
		sb.append("]]></column-value></column>");
		sb.append("<column><column-name>statusByUserId</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(journalArticle.getStatusByUserId());
		sb.append("]]></column-value></column>");
		sb.append("<column><column-name>statusByUserName</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(journalArticle.getStatusByUserName());
		sb.append("]]></column-value></column>");
		sb.append("<column><column-name>userId</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(journalArticle.getUserId());
		sb.append("]]></column-value></column>");
		sb.append("<column><column-name>userName</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(journalArticle.getUserName());
		sb.append("]]></column-value></column>");
		sb.append("<column><column-name>urlTitle</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(journalArticle.getUrlTitle());
		sb.append("]]></column-value></column>");
		sb.append("<column><column-name>content</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(
			StringUtil.replace(
				journalArticle.getContent(), "]]><", "]]]]><![CDATA[><"));
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

}