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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Branch;
import com.liferay.portal.kernel.xml.Comment;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.ProcessingInstruction;
import com.liferay.portal.kernel.xml.QName;

import java.util.Iterator;
import java.util.List;

import org.dom4j.CDATA;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Text;

/**
 * @author Brian Wing Shun Chan
 */
public class BranchImpl extends NodeImpl implements Branch {

	public BranchImpl(org.dom4j.Branch branch) {
		super(branch);

		_branch = branch;
	}

	@Override
	public void add(Comment comment) {
		CommentImpl commentImpl = (CommentImpl)comment;

		_branch.add(commentImpl.getWrappedComment());
	}

	@Override
	public void add(Element element) {
		ElementImpl elementImpl = (ElementImpl)element;

		_branch.add(elementImpl.getWrappedElement());
	}

	@Override
	public void add(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		_branch.add(nodeImpl.getWrappedNode());
	}

	@Override
	public void add(ProcessingInstruction processingInstruction) {
		ProcessingInstructionImpl processingInstructionImpl =
			(ProcessingInstructionImpl)processingInstruction;

		_branch.add(
			processingInstructionImpl.getWrappedProcessingInstruction());
	}

	@Override
	public Element addElement(QName qName) {
		QNameImpl qNameImpl = (QNameImpl)qName;

		return new ElementImpl(_branch.addElement(qNameImpl.getWrappedQName()));
	}

	@Override
	public Element addElement(String name) {
		return new ElementImpl(_branch.addElement(name));
	}

	@Override
	public Element addElement(String qualifiedName, String namespaceURI) {
		return new ElementImpl(_branch.addElement(qualifiedName, namespaceURI));
	}

	@Override
	public void appendContent(Branch branch) {
		BranchImpl branchImpl = (BranchImpl)branch;

		_branch.appendContent(branchImpl.getWrappedBranch());
	}

	@Override
	public void clearContent() {
		_branch.clearContent();
	}

	@Override
	public List<Node> content() {
		return SAXReaderImpl.toNewNodes(_branch.content());
	}

	@Override
	public Element elementByID(String elementID) {
		return new ElementImpl(_branch.elementByID(elementID));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BranchImpl)) {
			return false;
		}

		BranchImpl branchImpl = (BranchImpl)obj;

		org.dom4j.Branch branch = branchImpl.getWrappedBranch();

		return _branch.equals(branch);
	}

	public org.dom4j.Branch getWrappedBranch() {
		return _branch;
	}

	@Override
	public int hashCode() {
		return _branch.hashCode();
	}

	@Override
	public int indexOf(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		return _branch.indexOf(nodeImpl.getWrappedNode());
	}

	@Override
	public Node node(int index) {
		org.dom4j.Node node = _branch.node(index);

		if (node == null) {
			return null;
		}

		if (node instanceof CDATA) {
			return new CDATAImpl((CDATA)node);
		}
		else if (node instanceof org.dom4j.Comment) {
			return new CommentImpl((org.dom4j.Comment)node);
		}
		else if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else if (node instanceof Entity) {
			return new EntityImpl((Entity)node);
		}
		else if (node instanceof Namespace) {
			return new NamespaceImpl((Namespace)node);
		}
		else if (node instanceof Text) {
			return new TextImpl((Text)node);
		}

		return new NodeImpl(node);
	}

	@Override
	public int nodeCount() {
		return _branch.nodeCount();
	}

	@Override
	public Iterator<Node> nodeIterator() {
		return content().iterator();
	}

	@Override
	public void normalize() {
		_branch.normalize();
	}

	@Override
	public ProcessingInstruction processingInstruction(String target) {
		org.dom4j.ProcessingInstruction processingInstruction =
			_branch.processingInstruction(target);

		if (processingInstruction == null) {
			return null;
		}

		return new ProcessingInstructionImpl(processingInstruction);
	}

	@Override
	public List<ProcessingInstruction> processingInstructions() {
		return SAXReaderImpl.toNewProcessingInstructions(
			_branch.processingInstructions());
	}

	@Override
	public List<ProcessingInstruction> processingInstructions(String target) {
		return SAXReaderImpl.toNewProcessingInstructions(
			_branch.processingInstructions(target));
	}

	@Override
	public boolean remove(Comment comment) {
		CommentImpl commentImpl = (CommentImpl)comment;

		return _branch.remove(commentImpl.getWrappedComment());
	}

	@Override
	public boolean remove(Element element) {
		ElementImpl elementImpl = (ElementImpl)element;

		return _branch.remove(elementImpl.getWrappedElement());
	}

	@Override
	public boolean remove(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		return _branch.remove(nodeImpl.getWrappedNode());
	}

	@Override
	public boolean remove(ProcessingInstruction processingInstruction) {
		ProcessingInstructionImpl processingInstructionImpl =
			(ProcessingInstructionImpl)processingInstruction;

		return _branch.remove(
			processingInstructionImpl.getWrappedProcessingInstruction());
	}

	@Override
	public boolean removeProcessingInstruction(String target) {
		return _branch.removeProcessingInstruction(target);
	}

	@Override
	public void setContent(List<Node> content) {
		_branch.setContent(SAXReaderImpl.toOldNodes(content));
	}

	@Override
	public void setProcessingInstructions(
		List<ProcessingInstruction> processingInstructions) {

		_branch.setProcessingInstructions(
			SAXReaderImpl.toOldProcessingInstructions(processingInstructions));
	}

	@Override
	public String toString() {
		return _branch.toString();
	}

	private final org.dom4j.Branch _branch;

}