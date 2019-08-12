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

package com.liferay.wiki.engine.creole.internal.antlrwiki.translator;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.engine.creole.internal.antlrwiki.translator.internal.UnformattedHeadingTextVisitor;
import com.liferay.wiki.engine.creole.internal.antlrwiki.translator.internal.UnformattedLinksTextVisitor;
import com.liferay.wiki.engine.creole.internal.parser.ast.CollectionNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.HeadingNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.ImageNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.WikiPageNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.extension.TableOfContentsNode;
import com.liferay.wiki.engine.creole.internal.parser.ast.link.LinkNode;
import com.liferay.wiki.engine.creole.internal.parser.visitor.XhtmlTranslationVisitor;
import com.liferay.wiki.engine.creole.internal.util.WikiEngineCreoleComponentProvider;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Miguel Pastor
 */
public class XhtmlTranslator extends XhtmlTranslationVisitor {

	public String translate(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix, WikiPageNode wikiPageNode) {

		_page = page;
		_viewPageURL = viewPageURL;
		_editPageURL = editPageURL;
		_attachmentURLPrefix = attachmentURLPrefix;
		_rootWikiPageNode = wikiPageNode;

		return super.translate(wikiPageNode);
	}

	@Override
	public void visit(HeadingNode headingNode) {
		append("<h");
		append(headingNode.getLevel());

		String unformattedText = getUnformattedHeadingText(headingNode);

		String markup = getHeadingMarkup(
			_page.getTitle(), unformattedText, _headingCounts);

		append(" id=\"");
		append(markup);
		append("\">");

		int count = _headingCounts.getOrDefault(unformattedText, 0);

		_headingCounts.put(unformattedText, count + 1);

		traverse(headingNode.getChildASTNodes());

		append("</h");
		append(headingNode.getLevel());
		append(">");
	}

	@Override
	public void visit(ImageNode imageNode) {
		append("<img");

		if (imageNode.hasAltCollectionNode()) {
			append(" alt=\"");

			CollectionNode altCollectionNode = imageNode.getAltNode();

			traverse(altCollectionNode.getASTNodes());

			append(StringPool.QUOTE);
		}

		append(" src=\"");

		if (imageNode.isAbsoluteLink()) {
			append(HtmlUtil.escapeAttribute(imageNode.getLink()));
		}
		else {
			append(_attachmentURLPrefix);
			append(HtmlUtil.escapeAttribute(imageNode.getLink()));
		}

		append("\" />");
	}

	@Override
	public void visit(LinkNode linkNode) {
		String title = StringUtil.replace(
			linkNode.getLink(), CharPool.NO_BREAK_SPACE, StringPool.SPACE);

		WikiPage wikiPage = null;

		if ((title != null) && !linkNode.isAbsoluteLink()) {
			wikiPage = WikiPageLocalServiceUtil.fetchPage(
				_page.getNodeId(), title);
		}

		append("<a href=\"");

		appendHref(linkNode, title, wikiPage);

		append(StringPool.QUOTE);

		WikiEngineCreoleComponentProvider wikiEngineCreoleComponentProvider =
			WikiEngineCreoleComponentProvider.
				getWikiEngineCreoleComponentProvider();

		WikiGroupServiceConfiguration wikiGroupServiceConfiguration =
			wikiEngineCreoleComponentProvider.
				getWikiGroupServiceConfiguration();

		if (!linkNode.isAbsoluteLink() && (wikiPage == null) &&
			wikiGroupServiceConfiguration.enableHighlightCreoleFormat()) {

			append(" class=\"new-wiki-page\"");
		}

		append(">");

		if (linkNode.hasAltCollectionNode()) {
			CollectionNode altCollectionNode = linkNode.getAltCollectionNode();

			traverse(altCollectionNode.getASTNodes());
		}
		else {
			append(HtmlUtil.escape(linkNode.getLink()));
		}

		append("</a>");
	}

	@Override
	public void visit(TableOfContentsNode tableOfContentsNode) {
		TableOfContentsVisitor tableOfContentsVisitor =
			new TableOfContentsVisitor();

		_tableOfContentsHeadingCounts.clear();

		TreeNode<HeadingNode> tableOfContents = tableOfContentsVisitor.compose(
			_rootWikiPageNode);

		append("<div class=\"toc\">");
		append("<div class=\"collapsebox\">");
		append("<h4>");

		String title = tableOfContentsNode.getTitle();

		if (title == null) {
			title = "Table of Contents";
		}

		append(title);

		append(StringPool.NBSP);
		append("<a class=\"toc-trigger\" href=\"javascript:;\">[-]</a></h4>");
		append("<div class=\"toc-index\">");

		appendTableOfContents(tableOfContents, 1);

		append("</div>");
		append("</div>");
		append("</div>");
	}

	protected void appendAbsoluteHref(LinkNode linkNode) {
		append(HtmlUtil.escape(linkNode.getLink()));
	}

	protected void appendHref(
		LinkNode linkNode, String title, WikiPage wikiPage) {

		if (linkNode.getLink() == null) {
			UnformattedLinksTextVisitor unformattedLinksTextVisitor =
				new UnformattedLinksTextVisitor();

			linkNode.setLink(
				unformattedLinksTextVisitor.getUnformattedText(linkNode));
		}

		if (linkNode.isAbsoluteLink()) {
			appendAbsoluteHref(linkNode);
		}
		else {
			appendWikiHref(linkNode, wikiPage, title);
		}
	}

	protected void appendTableOfContents(
		TreeNode<HeadingNode> tableOfContents, int depth) {

		List<TreeNode<HeadingNode>> treeNodes = tableOfContents.getChildNodes();

		if ((treeNodes == null) || treeNodes.isEmpty()) {
			return;
		}

		append("<ol>");

		for (TreeNode<HeadingNode> treeNode : treeNodes) {
			append("<li class=\"toc-level-");
			append(depth);
			append("\">");

			HeadingNode headingNode = treeNode.getValue();

			String content = getUnformattedHeadingText(headingNode);

			append("<a class=\"wikipage\" href=\"");

			if (_viewPageURL != null) {
				String viewPageURLString = _viewPageURL.toString();

				int index = viewPageURLString.indexOf(StringPool.POUND);

				if (index != -1) {
					viewPageURLString = viewPageURLString.substring(0, index);
				}

				append(viewPageURLString);
			}

			append(StringPool.POUND);
			append(
				getHeadingMarkup(
					_page.getTitle(), content, _tableOfContentsHeadingCounts));
			append("\">");
			append(content);
			append("</a>");

			int count = _tableOfContentsHeadingCounts.getOrDefault(content, 0);

			_tableOfContentsHeadingCounts.put(content, count + 1);

			appendTableOfContents(treeNode, depth + 1);

			append("</li>");
		}

		append("</ol>");
	}

	protected void appendWikiHref(
		LinkNode linkNode, WikiPage wikiPage, String title) {

		String attachmentLink = searchLinkInAttachments(linkNode);

		if (attachmentLink != null) {

			// Attachment links take precedence over pages

			append(_attachmentURLPrefix + attachmentLink);

			return;
		}

		if ((wikiPage != null) && (_viewPageURL != null)) {
			_viewPageURL.setParameter("title", title);

			append(_viewPageURL.toString());

			_viewPageURL.setParameter("title", _page.getTitle());
		}
		else if (_editPageURL != null) {
			_editPageURL.setParameter("title", title);

			append(_editPageURL.toString());

			_editPageURL.setParameter("title", _page.getTitle());
		}
	}

	protected String getHeadingMarkup(
		String prefix, String text, Map<String, Integer> textCounts) {

		String postfix = StringPool.BLANK;

		if (textCounts.containsKey(text)) {
			postfix = StringPool.DASH + textCounts.get(text);
		}

		return StringUtil.replace(
			StringBundler.concat(
				_HEADING_ANCHOR_PREFIX, prefix, StringPool.DASH, text.trim(),
				postfix),
			CharPool.SPACE, CharPool.PLUS);
	}

	protected String getUnformattedHeadingText(HeadingNode headingNode) {
		UnformattedHeadingTextVisitor unformattedHeadingTextVisitor =
			new UnformattedHeadingTextVisitor();

		return unformattedHeadingTextVisitor.getUnformattedText(headingNode);
	}

	protected String searchLinkInAttachments(LinkNode linkNode) {
		try {
			for (FileEntry fileEntry : _page.getAttachmentsFileEntries()) {
				String title = fileEntry.getTitle();

				if (title.equals(linkNode.getLink())) {
					return title;
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	private static final String _HEADING_ANCHOR_PREFIX = "section-";

	private static final Log _log = LogFactoryUtil.getLog(
		XhtmlTranslator.class);

	private String _attachmentURLPrefix;
	private PortletURL _editPageURL;
	private final Map<String, Integer> _headingCounts = new HashMap<>();
	private WikiPage _page;
	private WikiPageNode _rootWikiPageNode;
	private final Map<String, Integer> _tableOfContentsHeadingCounts =
		new HashMap<>();
	private PortletURL _viewPageURL;

}