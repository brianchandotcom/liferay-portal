/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentType;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Visitor;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentImpl extends BranchImpl implements Document {

	public DocumentImpl(org.dom4j.Document document) {
		super(document);

		_document = document;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitDocument(this);
	}

	@Override
	public Document addComment(String comment) {
		_document.addComment(comment);

		return this;
	}

	@Override
	public Document addDocumentType(
		String name, String publicId, String systemId) {

		_document.addDocType(name, publicId, systemId);

		return this;
	}

	@Override
	public Document clone() {
		return new DocumentImpl((org.dom4j.Document)_document.clone());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentImpl)) {
			return false;
		}

		DocumentImpl documentImpl = (DocumentImpl)object;

		org.dom4j.Document document = documentImpl.getWrappedDocument();

		return _document.equals(document);
	}

	@Override
	public DocumentType getDocumentType() {
		return new DocumentTypeImpl(_document.getDocType());
	}

	@Override
	public Element getRootElement() {
		return new ElementImpl(_document.getRootElement());
	}

	public org.dom4j.Document getWrappedDocument() {
		return _document;
	}

	@Override
	public String getXMLEncoding() {
		return _document.getXMLEncoding();
	}

	@Override
	public int hashCode() {
		return _document.hashCode();
	}

	@Override
	public void setRootElement(Element rootElement) {
		ElementImpl rootElementImpl = (ElementImpl)rootElement;

		_document.setRootElement(rootElementImpl.getWrappedElement());
	}

	@Override
	public void setXMLEncoding(String encoding) {
		_document.setXMLEncoding(encoding);
	}

	@Override
	public String toString() {
		return _document.toString();
	}

	private final org.dom4j.Document _document;

}