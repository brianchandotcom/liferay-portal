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

package org.jsoup.nodes;

import java.io.IOException;

import org.jsoup.parser.Tag;

/**
 * @author Pavel Savinov
 */
public class FreemarkerCompatibleElement extends Element {

	public FreemarkerCompatibleElement(String tag) {
		super(tag);
	}

	@Override
	public void outerHtmlHead(
			Appendable appendable, int depth, Document.OutputSettings out)
		throws IOException {

		Tag tag = tag();
		Tag parentTag = null;

		Attributes attributes = attributes();

		Element parentElement = parent();

		if (parentElement != null) {
			parentTag = parentElement.tag();
		}

		if (out.prettyPrint() &&
			(tag.formatAsBlock() ||
			 ((parentElement != null) && parentTag.formatAsBlock()) ||
			 out.outline())) {

			if (appendable instanceof StringBuilder) {
				if (((StringBuilder)appendable).length() > 0) {
					indent(appendable, depth, out);
				}
			}
			else {
				indent(appendable, depth, out);
			}
		}

		appendable.append("<");
		appendable.append(tagName());

		attributes.html(appendable, out);

		if (childNodes.isEmpty() && tag().isSelfClosing()) {
			if ((out.syntax() == Document.OutputSettings.Syntax.html) &&
				tag.isEmpty()) {

				appendable.append('>');
			}
			else {
				appendable.append(" />");
			}
		}
		else {
			appendable.append(">");
		}
	}

	@Override
	public void outerHtmlTail(
			Appendable appendable, int depth, Document.OutputSettings out)
		throws IOException {

		Tag tag = tag();

		if (!(childNodes.isEmpty() && tag.isSelfClosing())) {
			if (out.prettyPrint() && !childNodes.isEmpty() &&
				(tag.formatAsBlock() ||
				 (out.outline() &&
				 ((childNodes.size() > 1) ||
				 ((childNodes.size() == 1) &&
					!(childNodes.get(0) instanceof TextNode)))))) {

				indent(appendable, depth, out);
			}

			appendable.append("</");
			appendable.append(tagName());
			appendable.append(">");
		}
	}

}