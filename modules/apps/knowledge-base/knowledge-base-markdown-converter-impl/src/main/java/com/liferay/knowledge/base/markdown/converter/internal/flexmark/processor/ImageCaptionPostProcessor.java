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

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

/**
 * 
 * @author Rich Sezov
 */
public class ImageCaptionPostProcessor extends NodePostProcessor {

	private static class ImageCaptionFactory extends NodePostProcessorFactory {
		private ImageCaptionFactory (DataHolder options) {
			super(false);

			addNodes(Image.class);
			addNodes(ImageRef.class);
		}

		@Override
		public NodePostProcessor create(Document document) {
			return new ImageCaptionPostProcessor();
		}
	}

	public static NodePostProcessorFactory Factory(DataHolder options) {
		return new ImageCaptionFactory(options);
	}

	@Override
        public void process(NodeTracker state, Node node) {
            BasedSequence paragraphText = BasedSequence.NULL;
            if (node instanceof ImageRef) { // [foo](http://example.com)
                ImageRef imageRef = (ImageRef) node;
                paragraphText = imageRef.isReferenceTextCombined() ? imageRef.getReference() : imageRef.getText();
            } else if (node instanceof Image) { // ![bar](http://example.com)
                Image image = (Image) node;
                paragraphText = image.getText();
            }

            if (!paragraphText.isBlank()) {
		    
	    if (paragraphText.toString().contains("Figure")) {
                Node paragraphParent = node.getAncestorOfType(Paragraph.class);

                // should always have a paragraph wrapper
                assert paragraphParent != null;

                // create a text element to hold the text
                Text text = new Text(PrefixedSubSequence.of(paragraphText.toString(), paragraphParent.getChars().subSequence(paragraphParent.getTextLength())));

                // create a paragraph for the text
                Paragraph paragraph = new Paragraph(text.getChars());
                
                // this will allows us add attributes in the AST without needing to modify the attribute provider
                Attributes attributes = new Attributes();
                attributes.addValue("class", "caption");

                paragraph.appendChild(new EmbeddedAttributeProvider.EmbeddedNodeAttributes(paragraph, attributes));
                paragraph.appendChild(text);
                paragraph.setCharsFromContent();

                paragraphParent.insertAfter(paragraph);
                paragraphParent.setCharsFromContent();

                state.nodeAddedWithChildren(paragraph);
            }
	    }
}
}
