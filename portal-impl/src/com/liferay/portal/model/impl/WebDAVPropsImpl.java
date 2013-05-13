/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Alexander Chow
 */
public class WebDAVPropsImpl extends WebDAVPropsBaseImpl {

	public WebDAVPropsImpl() {
	}

	@Override
	public void addProp(String name, String prefix, String uri)
		throws Exception {

		Namespace namespace = WebDAVUtil.createNamespace(prefix, uri);

		QName qname = SAXReaderUtil.createQName(name, namespace);

		Element root = _removeExisting(qname);

		root.addElement(qname);
	}

	@Override
	public void addProp(String name, String prefix, String uri, String text)
		throws Exception {

		Namespace namespace = WebDAVUtil.createNamespace(prefix, uri);

		QName qname = SAXReaderUtil.createQName(name, namespace);

		Element root = _removeExisting(qname);

		root.addElement(qname).addText(text);
	}

	@Override
	public String getProps() {
		String props = super.getProps();

		if (Validator.isNull(props)) {
			return _PROPS;
		}
		else {
			return props;
		}
	}

	@Override
	public Set<QName> getPropsSet() throws Exception {
		Set<QName> propsSet = new HashSet<QName>();

		Document doc = _getPropsDocument();

		Element root = doc.getRootElement();

		for (Element el : root.elements()) {
			String prefix = el.getNamespacePrefix();
			String uri = el.getNamespaceURI();

			Namespace namespace = WebDAVUtil.createNamespace(prefix, uri);

			propsSet.add(SAXReaderUtil.createQName(el.getName(), namespace));
		}

		return propsSet;
	}

	@Override
	public String getText(String name, String prefix, String uri)
		throws Exception {

		Namespace namespace = WebDAVUtil.createNamespace(prefix, uri);

		QName qname = SAXReaderUtil.createQName(name, namespace);

		Document doc = _getPropsDocument();

		Element root = doc.getRootElement();

		Element prop = root.element(qname);

		return prop.getText();
	}

	@Override
	public void removeProp(String name, String prefix, String uri)
		throws Exception {

		Namespace namespace = WebDAVUtil.createNamespace(prefix, uri);

		QName qname = SAXReaderUtil.createQName(name, namespace);

		_removeExisting(qname);
	}

	@Override
	public void store() throws Exception {
		if (_document != null) {
			String xml = _document.formattedString(StringPool.FOUR_SPACES);

			setProps(xml);

			_document = null;
		}
	}

	private Document _getPropsDocument() throws DocumentException {
		if (_document == null) {
			_document = SAXReaderUtil.read(getProps());
		}

		return _document;
	}

	private Element _removeExisting(QName qname) throws Exception {
		Document doc = _getPropsDocument();

		Element root = doc.getRootElement();

		Iterator<Element> itr = root.elements(qname).iterator();

		while (itr.hasNext()) {
			Element el = itr.next();

			root.remove(el);
		}

		return root;
	}

	private static final String _PROPS = "<properties />";

	private Document _document = null;

}