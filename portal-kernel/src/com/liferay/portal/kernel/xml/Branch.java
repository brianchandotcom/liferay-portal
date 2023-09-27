/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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