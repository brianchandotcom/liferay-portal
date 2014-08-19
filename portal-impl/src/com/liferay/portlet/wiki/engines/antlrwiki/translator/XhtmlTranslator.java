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

package com.liferay.portlet.wiki.engines.antlrwiki.translator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.ast.extension.TableOfContentsNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.visitor.impl.XhtmlTranslationVisitor;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.engines.antlrwiki.translator.internal.UnformattedHeadingTextVisitor;
import com.liferay.portlet.wiki.engines.antlrwiki.translator.internal.UnformattedLinksTextVisitor;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.List;

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

		String markup = getHeadingMarkup(_page.getTitle(), unformattedText);

		append(" id=\"");
		append(markup);
		append("\">");

		traverse(headingNode.getChildASTNodes());

		append("<a class=\"hashlink\" href=\"");

		if (_viewPageURL != null) {
			append(_viewPageURL.toString());
		}

		append(StringPool.POUND);
		append(markup);
		append("\">#</a></h");
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
			append(imageNode.getLink());
		}
		else {
			append(_attachmentURLPrefix);
			append(imageNode.getLink());
		}

		append("\" />");
	}

	@Override
	public void visit(LinkNode linkNode) {
		append("<a href=\"");

		appendHref(linkNode);

		append("\">");

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

	protected void appendHref(LinkNode linkNode) {
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
			appendWikiHref(linkNode);
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
				append(_viewPageURL.toString());
			}

			append(StringPool.POUND);
			append(getHeadingMarkup(_page.getTitle(), content));
			append("\">");
			append(content);
			append("</a>");

			appendTableOfContents(treeNode, depth + 1);

			append("</li>");
		}

		append("</ol>");
	}

	protected void appendWikiHref(LinkNode linkNode) {
		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(
				_page.getNodeId(), linkNode.getLink());
		}
		catch (NoSuchPageException nspe) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String attachmentLink = searchLinkInAttachments(linkNode);

		if (attachmentLink != null) {

			// Attachment links take precedence over pages

			append(_attachmentURLPrefix + attachmentLink);

			return;
		}

		String pageTitle = linkNode.getLink();

		if ((page != null) && (_viewPageURL != null)) {
			_viewPageURL.setParameter("title", pageTitle);

			append(_viewPageURL.toString());

			_viewPageURL.setParameter("title", _page.getTitle());
		}
		else if (_editPageURL != null) {
			_editPageURL.setParameter("title", pageTitle);

			append(_editPageURL.toString());

			_editPageURL.setParameter("title", _page.getTitle());
		}
	}

	protected String getHeadingMarkup(String prefix, String text) {
		StringBundler sb = new StringBundler(5);

		sb.append(_HEADING_ANCHOR_PREFIX);
		sb.append(prefix);
		sb.append(StringPool.DASH);
		sb.append(text.trim());

		return StringUtil.replace(
			sb.toString(), StringPool.SPACE, StringPool.PLUS);
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
		}

		return null;
	}

	private static final String _HEADING_ANCHOR_PREFIX = "section-";

	private static final Log _log = LogFactoryUtil.getLog(
		XhtmlTranslator.class);

	private String _attachmentURLPrefix;
	private PortletURL _editPageURL;
	private WikiPage _page;
	private WikiPageNode _rootWikiPageNode;
	private PortletURL _viewPageURL;

}