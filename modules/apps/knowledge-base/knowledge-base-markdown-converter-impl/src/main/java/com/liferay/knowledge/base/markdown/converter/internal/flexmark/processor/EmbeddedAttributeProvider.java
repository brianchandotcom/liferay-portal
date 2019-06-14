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
package com.liferay.knowledge.base.markdown.converter.internal.flexmark.processor;

import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 *
 * @author vladsch
 */
// NOTE: EmbeddedAttributeProvider is part of flexmark core HtmlRenderer as of version 0.40.18, but 
// the latest release is 0.40.16.
public class EmbeddedAttributeProvider implements AttributeProvider {

	@Override
	public void setAttributes(final Node node, final AttributablePart part, final Attributes attributes) {
		if (part == AttributablePart.NODE) {
			Node firstChild = node.getChildOfType(EmbeddedAttributeProvider.EmbeddedNodeAttributes.class);
			if (firstChild instanceof EmbeddedAttributeProvider.EmbeddedNodeAttributes) {
				attributes.addValues(((EmbeddedAttributeProvider.EmbeddedNodeAttributes) firstChild).attributes);
			}
		}
	}

	public static AttributeProviderFactory Factory() {
		return new IndependentAttributeProviderFactory() {
			@Override
			public AttributeProvider create(LinkResolverContext context) {
				//noinspection ReturnOfInnerClass
				return new EmbeddedAttributeProvider();
			}
		};
	}
//---- Internal Classes -----

	// so we can attach attributes to any node in the AST and have a generic attribute provider serve them up
	static class EmbeddedNodeAttributes extends Node {

		final Attributes attributes;

		public EmbeddedNodeAttributes(Node parent, Attributes attributes) {
			super(parent.getChars().subSequence(0, 0));
			this.attributes = attributes;
		}

		@Override
		public BasedSequence[] getSegments() {
			return Node.EMPTY_SEGMENTS;
		}

		@Override
		public void astString(final StringBuilder out, final boolean withExtra) {
			out.append(EmbeddedNodeAttributes.class.getSimpleName());
			out.append("[").append(getStartOffset()).append(", ").append(getEndOffset()).append("]");
			out.append(", attributes: ").append(attributes.toString());

			if (withExtra) {
				getAstExtra(out);
			}
		}

		@Override
		public void astExtraChars(final StringBuilder out) {
		}
	}
}
