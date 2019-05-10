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

package com.liferay.portal.kernel.xml;

import java.util.Iterator;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Branch extends Node {

	public void add(Comment comment);

	public void add(Element element);

	public void add(Node node);

	public void add(ProcessingInstruction processingInstruction);

	public Element addElement(QName qName);

	public Element addElement(String name);

	public Element addElement(String qualifiedName, String namespaceURI);

	public void appendContent(Branch branch);

	public void clearContent();

	public List<Node> content();

	public Element elementByID(String elementID);

	public int indexOf(Node node);

	public Node node(int index);

	public int nodeCount();

	public Iterator<Node> nodeIterator();

	public void normalize();

	public ProcessingInstruction processingInstruction(String target);

	public List<ProcessingInstruction> processingInstructions();

	public List<ProcessingInstruction> processingInstructions(String target);

	public boolean remove(Comment comment);

	public boolean remove(Element element);

	public boolean remove(Node node);

	public boolean remove(ProcessingInstruction processingInstruction);

	public boolean removeProcessingInstruction(String target);

	public void setContent(List<Node> content);

	public void setProcessingInstructions(
		List<ProcessingInstruction> processingInstructions);

}