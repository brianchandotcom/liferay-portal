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

import com.liferay.portal.kernel.util.TranslatedList;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Branch;
import com.liferay.portal.kernel.xml.CDATA;
import com.liferay.portal.kernel.xml.Comment;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Entity;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.ProcessingInstruction;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.Text;
import com.liferay.portal.kernel.xml.XPath;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class NodeList<E, F> extends TranslatedList<E, F> {

	public NodeList(List<E> newList, List<F> oldList) {
		super(newList, oldList);
	}

	@Override
	protected TranslatedList<E, F> newInstance(
		List<E> newList, List<F> oldList) {

		return new NodeList<>(newList, oldList);
	}

	@Override
	protected F toOldObject(E o) {
		if (o instanceof Attribute) {
			AttributeImpl attributeImpl = (AttributeImpl)o;

			return (F)attributeImpl.getWrappedAttribute();
		}
		else if (o instanceof CDATA) {
			CDATAImpl cdataImpl = (CDATAImpl)o;

			return (F)cdataImpl.getWrappedCDATA();
		}
		else if (o instanceof Comment) {
			CommentImpl commentImpl = (CommentImpl)o;

			return (F)commentImpl.getWrappedComment();
		}
		else if (o instanceof Document) {
			DocumentImpl documentImpl = (DocumentImpl)o;

			return (F)documentImpl.getWrappedDocument();
		}
		else if (o instanceof Element) {
			ElementImpl elementImpl = (ElementImpl)o;

			return (F)elementImpl.getWrappedElement();
		}
		else if (o instanceof Entity) {
			EntityImpl entityImpl = (EntityImpl)o;

			return (F)entityImpl.getWrappedEntity();
		}
		else if (o instanceof Namespace) {
			NamespaceImpl namespaceImpl = (NamespaceImpl)o;

			return (F)namespaceImpl.getWrappedNamespace();
		}
		else if (o instanceof ProcessingInstruction) {
			ProcessingInstructionImpl processingInstructionImpl =
				(ProcessingInstructionImpl)o;

			return (F)
				processingInstructionImpl.getWrappedProcessingInstruction();
		}
		else if (o instanceof QName) {
			QNameImpl qNameImpl = (QNameImpl)o;

			return (F)qNameImpl.getWrappedQName();
		}
		else if (o instanceof Text) {
			TextImpl textImpl = (TextImpl)o;

			return (F)textImpl.getWrappedText();
		}
		else if (o instanceof XPath) {
			XPathImpl xPathImpl = (XPathImpl)o;

			return (F)xPathImpl.getWrappedXPath();
		}
		else if (o instanceof Branch) {
			BranchImpl branchImpl = (BranchImpl)o;

			return (F)branchImpl.getWrappedBranch();
		}
		else if (o instanceof Node) {
			NodeImpl nodeImpl = (NodeImpl)o;

			return (F)nodeImpl.getWrappedNode();
		}

		Class<?> clazz = o.getClass();

		throw new IllegalArgumentException(clazz.getName());
	}

}