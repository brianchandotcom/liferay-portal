/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Text;
import com.liferay.portal.kernel.xml.Visitor;

/**
 * @author Brian Wing Shun Chan
 */
public class TextImpl extends NodeImpl implements Text {

	public TextImpl(org.dom4j.Text text) {
		super(text);

		_text = text;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitText(this);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TextImpl)) {
			return false;
		}

		TextImpl textImpl = (TextImpl)object;

		org.dom4j.Text text = textImpl.getWrappedText();

		return _text.equals(text);
	}

	public org.dom4j.Text getWrappedText() {
		return _text;
	}

	@Override
	public int hashCode() {
		return _text.hashCode();
	}

	@Override
	public String toString() {
		return _text.toString();
	}

	private final org.dom4j.Text _text;

}