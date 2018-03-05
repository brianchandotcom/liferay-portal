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

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.util.AdminHelper;
import com.liferay.knowledge.base.util.comparator.KBArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.diff.DiffHtmlUtil;
import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.portal.kernel.diff.DiffVersionsInfo;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;

/**
 * @author Lance Ji
 */
public class AdminHelperImpl implements AdminHelper {

	@Override
	public String[] escapeSections(String[] sections) {
		if (ArrayUtil.isEmpty(sections)) {
			return new String[0];
		}

		sections = ArrayUtil.clone(sections);

		for (int i = 0; i < sections.length; i++) {
			sections[i] = StringPool.UNDERLINE.concat(sections[i]).concat(
				StringPool.UNDERLINE);
		}

		return sections;
	}

	@Override
	public DiffVersionsInfo getDiffVersionsInfo(
		long groupId, long kbArticleResourcePrimKey, int sourceVersion,
		int targetVersion) {

		double previousVersion = 0;
		double nextVersion = 0;

		List<KBArticle> kbArticles = _kbArticleService.getKBArticleVersions(
			groupId, kbArticleResourcePrimKey,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new KBArticleVersionComparator());

		for (KBArticle curKBArticle : kbArticles) {
			if ((curKBArticle.getVersion() < sourceVersion) &&
				(curKBArticle.getVersion() > previousVersion)) {

				previousVersion = curKBArticle.getVersion();
			}

			if ((curKBArticle.getVersion() > targetVersion) &&
				((curKBArticle.getVersion() < nextVersion) ||
				 (nextVersion == 0))) {

				nextVersion = curKBArticle.getVersion();
			}
		}

		List<DiffVersion> diffVersions = new ArrayList<>();

		for (KBArticle curKBArticle : kbArticles) {
			DiffVersion diffVersion = new DiffVersion(
				curKBArticle.getUserId(), curKBArticle.getVersion(),
				curKBArticle.getModifiedDate());

			diffVersions.add(diffVersion);
		}

		return new DiffVersionsInfo(diffVersions, nextVersion, previousVersion);
	}

	@Override
	public String getKBArticleDiff(
			long resourcePrimKey, int sourceVersion, int targetVersion,
			String param)
		throws Exception {

		if (sourceVersion < KBArticleConstants.DEFAULT_VERSION) {
			sourceVersion = KBArticleConstants.DEFAULT_VERSION;
		}

		if (sourceVersion == targetVersion) {
			KBArticle kbArticle = _kbArticleLocalService.getKBArticle(
				resourcePrimKey, targetVersion);

			return BeanPropertiesUtil.getString(kbArticle, param);
		}

		KBArticle sourceKBArticle = _kbArticleLocalService.getKBArticle(
			resourcePrimKey, sourceVersion);
		KBArticle targetKBArticle = _kbArticleLocalService.getKBArticle(
			resourcePrimKey, targetVersion);

		String sourceHtml = BeanPropertiesUtil.getString(
			sourceKBArticle, param);
		String targetHtml = BeanPropertiesUtil.getString(
			targetKBArticle, param);

		String diff = DiffHtmlUtil.diff(
			new UnsyncStringReader(sourceHtml),
			new UnsyncStringReader(targetHtml));

		Source source = new Source(diff);

		OutputDocument outputDocument = new OutputDocument(source);

		for (Element element : source.getAllElements()) {
			StringBundler sb = new StringBundler(4);

			Attributes attributes = element.getAttributes();

			if (attributes == null) {
				continue;
			}

			Attribute changeTypeAttribute = attributes.get("changeType");

			if (changeTypeAttribute != null) {
				String changeTypeValue = changeTypeAttribute.getValue();

				if (changeTypeValue.contains("diff-added-image")) {
					sb.append("border: 10px solid #CFC; ");
				}
				else if (changeTypeValue.contains("diff-changed-image")) {
					sb.append("border: 10px solid #C6C6FD; ");
				}
				else if (changeTypeValue.contains("diff-removed-image")) {
					sb.append("border: 10px solid #FDC6C6; ");
				}
			}

			Attribute classAttribute = attributes.get("class");

			if (classAttribute != null) {
				String classValue = classAttribute.getValue();

				if (classValue.contains("diff-html-added")) {
					sb.append("background-color: #CFC; ");
				}
				else if (classValue.contains("diff-html-changed")) {
					sb.append("background-color: #C6C6FD; ");
				}
				else if (classValue.contains("diff-html-removed")) {
					sb.append("background-color: #FDC6C6; text-decoration: ");
					sb.append("line-through; ");
				}
			}

			if (Validator.isNull(sb.toString())) {
				continue;
			}

			Attribute styleAttribute = attributes.get("style");

			if (styleAttribute != null) {
				sb.append(GetterUtil.getString(styleAttribute.getValue()));
			}

			Map<String, String> map = outputDocument.replace(attributes, false);

			map.put("style", sb.toString());
		}

		return outputDocument.toString();
	}

	@Override
	public String[] unescapeSections(String sections) {
		String[] sectionsArray = StringUtil.split(sections);

		for (int i = 0; i < sectionsArray.length; i++) {
			String section = sectionsArray[i];

			if (StringUtil.startsWith(section, StringPool.UNDERLINE) &&
				StringUtil.endsWith(section, StringPool.UNDERLINE)) {

				sectionsArray[i] = section.substring(1, section.length() - 1);
			}
		}

		return sectionsArray;
	}

	@BeanReference(type = KBArticleLocalService.class)
	private KBArticleLocalService _kbArticleLocalService;

	@BeanReference(type = KBArticleService.class)
	private KBArticleService _kbArticleService;

}