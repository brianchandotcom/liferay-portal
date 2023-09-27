/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.pegdown;

import com.liferay.knowledge.base.markdown.converter.internal.pegdown.ast.PicWithCaptionNode;

import org.parboiled.Rule;
import org.parboiled.common.ArrayBuilder;

/**
 * Provides rules for parsing pictures with captions, "side-bars", and in-line
 * images.
 *
 * @author James Hinkey
 */
public class LiferayParser extends Parser {

	public LiferayParser(Integer options) {
		super(options);
	}

	public LiferayParser(
		Integer options, ParseRunnerProvider parseRunnerProvider) {

		super(options, parseRunnerProvider);
	}

	@Override
	public Rule Block() {
		ArrayBuilder<Rule> arrayBuilder = new ArrayBuilder<>();

		arrayBuilder.add(BlockQuote(), Verbatim());

		if (ext(ABBREVIATIONS)) {
			arrayBuilder.addNonNulls(Abbreviation());
		}

		arrayBuilder.add(
			pictureWithCaption(), Reference(), HorizontalRule(), Heading(),
			OrderedList(), BulletList(), HtmlBlock());

		if (ext(TABLES)) {
			arrayBuilder.addNonNulls(Table());
		}

		if (ext(DEFINITIONS)) {
			arrayBuilder.addNonNulls(DefinitionList());
		}

		if (ext(FENCED_CODE_BLOCKS)) {
			arrayBuilder.addNonNulls(FencedCodeBlock());
		}

		arrayBuilder.add(Para(), Inlines());

		return Sequence(ZeroOrMore(BlankLine()), FirstOf(arrayBuilder.get()));
	}

	public Rule pictureWithCaption() {
		return NodeSequence(
			TestNot(OneOrMore(CharEntity())), '!', Label(), Spn1(), '(', Sp(),
			LinkSource(), Spn1(), FirstOf(LinkTitle(), push("")), Sp(), ')',
			TestNot(OneOrMore(Spacechar())), TestNot(OneOrMore(CharEntity())),
			drop(), push(new PicWithCaptionNode(popAsString(), popAsNode())));
	}

}