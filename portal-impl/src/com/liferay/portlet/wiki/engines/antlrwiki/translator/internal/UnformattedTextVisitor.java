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

package com.liferay.portlet.wiki.engines.antlrwiki.translator.internal;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.portal.parsers.creole.ast.UnformattedTextNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.visitor.impl.BaseASTVisitor;

/**
 * @author Miguel Pastor
 */
public abstract class UnformattedTextVisitor extends BaseASTVisitor {

	public String getText() {
		return _sb.toString();
	}

	@Override
	public void visit(BoldTextNode boldTextNode) {
		if (boldTextNode.getContent() != null) {
			write(boldTextNode.getContent());
		}
		else {
			super.visit(boldTextNode);
		}
	}

	@Override
	public void visit(FormattedTextNode formattedTextNode) {
		if (formattedTextNode.getContent() != null) {
			write(formattedTextNode.getContent());
		}
		else {
			super.visit(formattedTextNode);
		}
	}

	@Override
	public void visit(ItalicTextNode italicTextNode) {
		if (italicTextNode.getContent() != null) {
			write(italicTextNode.getContent());
		}
		else {
			super.visit(italicTextNode);
		}
	}

	@Override
	public void visit(LinkNode linkNode) {
		String link = linkNode.getLink();

		if (link != null) {
			write(link);
		}

		super.visit(linkNode);
	}

	@Override
	public void visit(NoWikiSectionNode noWikiSectionNode) {
		write(noWikiSectionNode.getContent());
	}

	@Override
	public void visit(UnformattedTextNode unformattedTextNode) {
		if (unformattedTextNode.hasContent()) {
			write(unformattedTextNode.getContent());
		}
		else {
			super.visit(unformattedTextNode);
		}
	}

	protected void write(String text) {
		_sb.append(text);
	}

	private final StringBundler _sb = new StringBundler();

}