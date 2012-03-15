package com.liferay.portal.util;

import com.liferay.portal.kernel.util.HtmlUtil;

public class HtmlEscapableObject<T> extends EscapableObject<T> {

	public HtmlEscapableObject(T raw) {
		super(raw);
	}

	public HtmlEscapableObject(T raw, boolean escape) {
		super(raw, escape);
	}

	protected String escape(T t) {
		return HtmlUtil.escape(String.valueOf(_raw));
	}

}