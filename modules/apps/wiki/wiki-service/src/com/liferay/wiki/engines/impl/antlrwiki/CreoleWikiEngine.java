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

package com.liferay.wiki.engines.impl.antlrwiki;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.wiki.engines.WikiEngine;
import com.liferay.wiki.engines.impl.antlrwiki.translator.XhtmlTranslator;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.parsers.creole.ast.ASTNode;
import com.liferay.wiki.parsers.creole.ast.WikiPageNode;
import com.liferay.wiki.parsers.creole.ast.link.LinkNode;
import com.liferay.wiki.parsers.creole.parser.Creole10Lexer;
import com.liferay.wiki.parsers.creole.parser.Creole10Parser;
import com.liferay.wiki.parsers.creole.visitor.impl.LinkNodeCollectorVisitor;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Miguel Pastor
 */
@Component(
	service = WikiEngine.class,
	property = {
		"enabled=true", "format=creole",
		"edit.page=/html/portlet/wiki/edit/wiki.jsp",
		"help.page=/html/portlet/wiki/help/creole.jsp",
		"help.url=http://www.wikicreole.org/wiki/Creole1.0"
	}
)
public class CreoleWikiEngine implements WikiEngine {

	@Override
	public String convert(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix) {

		XhtmlTranslator xhtmlTranslator = new XhtmlTranslator();

		return xhtmlTranslator.translate(
			page, viewPageURL, editPageURL, attachmentURLPrefix,
			parse(page.getContent()));
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		Map<String, Boolean> outgoingLinks = new HashMap<String, Boolean>();

		LinkNodeCollectorVisitor linkNodeCollectorVisitor =
			new LinkNodeCollectorVisitor();

		List<ASTNode> astNodes = linkNodeCollectorVisitor.collect(
			parse(page.getContent()));

		try {
			for (ASTNode astNode : astNodes) {
				LinkNode linkNode = (LinkNode)astNode;

				String title = linkNode.getLink();

				boolean existingLink = false;

				if (WikiPageLocalServiceUtil.getPagesCount(
						page.getNodeId(), title, true) > 0) {

					existingLink = true;
				}

				if (existingLink) {
					title = StringUtil.toLowerCase(title);
				}

				outgoingLinks.put(title, existingLink);
			}
		}
		catch (SystemException se) {
			throw new PageContentException(se);
		}

		return outgoingLinks;
	}

	@Override
	public boolean validate(long nodeId, String content) {
		return true;
	}

	protected Creole10Parser build(String creoleCode) {
		ANTLRStringStream antlrStringStream = new ANTLRStringStream(creoleCode);

		Creole10Lexer creole10Lexer = new Creole10Lexer(antlrStringStream);

		CommonTokenStream commonTokenStream = new CommonTokenStream(
			creole10Lexer);

		return new Creole10Parser(commonTokenStream);
	}

	protected WikiPageNode parse(String creoleCode) {
		Creole10Parser creole10Parser = build(creoleCode);

		try {
			creole10Parser.wikipage();
		}
		catch (RecognitionException re) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse:\n" + creoleCode, re);

				for (String error : creole10Parser.getErrors()) {
					_log.debug(error);
				}
			}
		}

		return creole10Parser.getWikiPageNode();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CreoleWikiEngine.class);

}