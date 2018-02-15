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

package com.liferay.poshi.runner.elements;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public abstract class PoshiNodeFactory {

	public static PoshiNode<?, ?> newPoshiNode(Node node) {
		PoshiNode<?, ?> newPoshiNode = null;

		if (node instanceof Comment) {
			newPoshiNode = _newPoshiComment((Comment)node);
		}

		if (node instanceof Element) {
			newPoshiNode = _newPoshiElement((Element)node);
		}

		if (newPoshiNode != null) {
			return newPoshiNode;
		}

		String nodeContent;

		try {
			nodeContent = Dom4JUtil.format(node);
		}
		catch (IOException ioe) {
			nodeContent = node.toString();
		}

		throw new RuntimeException("Unknown node\n" + nodeContent);
	}

	public static PoshiNode<?, ?> newPoshiNode(
		PoshiNode<?, ?> parentPoshiNode, String readableSyntax) {

		PoshiNode<?, ?> newPoshiNode = null;

		newPoshiNode = _newPoshiComment(readableSyntax);

		if (newPoshiNode != null) {
			return newPoshiNode;
		}

		newPoshiNode = _newPoshiElement(
			(PoshiElement)parentPoshiNode, readableSyntax);

		if (newPoshiNode != null) {
			return newPoshiNode;
		}

		throw new RuntimeException("Unknown readble syntax\n" + readableSyntax);
	}

	public static PoshiNode<?, ?> newPoshiNodeFromFile(String filePath) {
		File file = new File(filePath);

		try {
			String content = FileUtil.read(file);

			if (content.contains("<definition")) {
				Document document = Dom4JUtil.parse(content);

				Element rootElement = document.getRootElement();

				return newPoshiNode(rootElement);
			}

			return newPoshiNode(null, content);
		}
		catch (Exception e) {
			System.out.println("Unable to generate the Poshi element");

			e.printStackTrace();
		}

		return null;
	}

	private static PoshiComment _newPoshiComment(Comment comment) {
		for (PoshiComment poshiComment : _poshiComments) {
			PoshiComment newPoshiComment = poshiComment.clone(comment);

			if (newPoshiComment != null) {
				return newPoshiComment;
			}
		}

		return null;
	}

	private static PoshiComment _newPoshiComment(String readableSyntax) {
		for (PoshiComment poshiComment : _poshiComments) {
			PoshiComment newPoshiComment = poshiComment.clone(readableSyntax);

			if (newPoshiComment != null) {
				return newPoshiComment;
			}
		}

		return null;
	}

	private static PoshiElement _newPoshiElement(Element element) {
		for (PoshiElement poshiElement : _poshiElements) {
			PoshiElement newPoshiElement = poshiElement.clone(element);

			if (newPoshiElement != null) {
				return newPoshiElement;
			}
		}

		return null;
	}

	private static PoshiElement _newPoshiElement(
		PoshiElement parentPoshiElement, String readableSyntax) {

		for (PoshiElement poshiElement : _poshiElements) {
			PoshiElement newPoshiElement = poshiElement.clone(
				parentPoshiElement, readableSyntax);

			if (newPoshiElement != null) {
				return newPoshiElement;
			}
		}

		return null;
	}

	private static final List<PoshiComment> _poshiComments = new ArrayList<>();
	private static final List<PoshiElement> _poshiElements = new ArrayList<>();

	static {
		try {
			URL url = PoshiNode.class.getResource(
				PoshiNode.class.getSimpleName() + ".class");

			File poshiNodeClassFile = new File(url.toURI());

			File dir = poshiNodeClassFile.getParentFile();

			List<File> dirFiles = Arrays.asList(dir.listFiles());

			Collections.sort(dirFiles);

			for (File file : dirFiles) {
				String fileName = file.getName();

				if (fileName.startsWith("Base") ||
					fileName.startsWith("Poshi")) {

					continue;
				}

				Package pkg = PoshiNode.class.getPackage();

				int index = fileName.indexOf(".");

				String className = fileName.substring(0, index);

				Class<?> clazz = Class.forName(pkg.getName() + "." + className);

				PoshiNode<?, ?> poshiNode =
					(PoshiNode<?, ?>)clazz.newInstance();

				if (poshiNode instanceof PoshiComment) {
					_poshiComments.add((PoshiComment)poshiNode);

					continue;
				}

				if (poshiNode instanceof PoshiElement) {
					_poshiElements.add((PoshiElement)poshiNode);
				}
			}
		}
		catch (ClassNotFoundException | IllegalAccessException |
			   InstantiationException | URISyntaxException e) {

			throw new RuntimeException(e);
		}
	}

}