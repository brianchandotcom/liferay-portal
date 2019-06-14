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
package com.liferay.knowledge.base.markdown.converter.internal.flexmark;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;
import com.liferay.knowledge.base.markdown.converter.internal.flexmark.processor.ImageCaptionPostProcessorExtension;
import com.liferay.knowledge.base.markdown.converter.internal.flexmark.visitor.ImageVisitor;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.zip.ZipReader;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;
import java.util.Arrays;

/**
 * Implementation of Markdown parsing using Flexmark.
 * @author Rich Sezov
 */
public class LiferayFlexmarkConverter implements MarkdownConverter {
	
	public LiferayFlexmarkConverter() {
		MutableDataSet options = new MutableDataSet()
				.set(HtmlRenderer.GENERATE_HEADER_ID, true)
				.set(AsideExtension.EXTEND_TO_BLANK_LINE, false)
				.set(AsideExtension.IGNORE_BLANK_LINE, false)
				.set(AsideExtension.ALLOW_LEADING_SPACE, true)
				.set(AsideExtension.INTERRUPTS_PARAGRAPH, true)
				.set(AsideExtension.INTERRUPTS_ITEM_PARAGRAPH, true)
				.set(AsideExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH, true);

		options.set(Parser.EXTENSIONS, 
			Arrays.asList(TablesExtension.create(),
				AttributesExtension.create(),
				StrikethroughExtension.create(),
				AnchorLinkExtension.create(),
				AsideExtension.create(),
				DefinitionExtension.create(),
				FootnoteExtension.create(),
				MediaTagsExtension.create(),
				SuperscriptExtension.create(),
				TocExtension.create(),
				TypographicExtension.create(),
				YamlFrontMatterExtension.create(),
				ImageCaptionPostProcessorExtension.create()
				));
		_parser = Parser.builder(options).build();
		_renderer = HtmlRenderer.builder(options).build();

	}
 
	@Override
	public String convert(String markdown) throws IOException {

		String html = _renderer.render(_document);
		return html;
	}
	
	@Override
	public String convert(String markdown, KBArticle article, ZipReader zip, long userId) throws IOException {
		ImageVisitor imageVisitor = new ImageVisitor();
		imageVisitor.visit(_document, article, zip, userId);

		_imageNames = imageVisitor.getImages();
		String html = _renderer.render(_document);
		return html;

	}
	
	public String getURLTitle() {
		return _URLTitle;
	}
	
	public List <String>getImageNames() {
		return _imageNames;
	}

	public void parse (String markdown) throws IOException {
		
		_document = _parser.parse(markdown);
		AbstractYamlFrontMatterVisitor yamlVisitor = new AbstractYamlFrontMatterVisitor();
		yamlVisitor.visit(_document);
		Map<String, List<String>> data = yamlVisitor.getData();

		if (data.size() < 1) {
			throw new IOException ("YAML Headers missing from Markdown");
		}
		
		_URLTitle = data.get("header-id").get(0);
		
	}

	//private final LiferayFlexmarkProcessor _LiferayFlexmarkProcessor; 
	private final Parser _parser;
	private final HtmlRenderer _renderer;
	private Node _document;
	private String _URLTitle;
	private List<String> _imageNames;

}
