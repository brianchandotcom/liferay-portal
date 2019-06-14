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

package com.liferay.knowledge.base.markdown.converter.internal.flexmark.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.knowledge.base.importer.util.KBArticleImporterUtil;
import com.liferay.knowledge.base.markdown.converter.internal.flexmark.util.FlexmarkUtil;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.zip.ZipReader;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Visitor to replace image sources with uploaded Doc Library entries. 
 * @author Rich Sezov
 */
public class ImageVisitor {

	public ImageVisitor() {
		_images = new ArrayList<String>();
		_logger = Logger.getLogger(ImageVisitor.class.getName());
	}
	public void visit (Node node) {
		_visitor.visit(node);
	}
	
	public void visit(Node node, KBArticle article, ZipReader zip, long userId) {
		_kbArticle = article;
		_zip = zip;
		_userId = userId;
		visit (node);
	}

	public void visit (Image image) {
		BasedSequence url = image.getUrl();
		BasedSequence[] urlparts = url.split("/");
		BasedSequence fileName = urlparts[urlparts.length -1];
		String file = fileName.toString();
		try {
			
			FileEntry entry = KBArticleImporterUtil.addImageFileEntry(file, _userId, _kbArticle, _zip);
			String imageSrc = DLUtil.getPreviewURL(
						entry, entry.getFileVersion(), null,
						StringPool.BLANK);

			BasedSequence newUrl = FlexmarkUtil.StringtoBS(imageSrc);

			image.setUrl(newUrl);
			
		} catch (PortalException pe) {
			_logger.log(Level.SEVERE, "PortalException while trying to add images.");
		}

		_images.add(file);
		_visitor.visitChildren(image);
	}

	public List <String>getImages() {
		return _images;
	}

	NodeVisitor _visitor = new NodeVisitor (new VisitHandler<Image>(Image.class, new Visitor<Image>() {
		@Override
		public void visit (Image image) {
			ImageVisitor.this.visit(image);
		}
	}));

	private List <String>_images;
	private KBArticle _kbArticle;
	private ZipReader _zip;
	private long _userId;
	private static Logger _logger;

	
}
