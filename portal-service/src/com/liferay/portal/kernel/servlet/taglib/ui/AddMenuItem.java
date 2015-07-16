package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Ambrín Chaudhary
 */
public class AddMenuItem {

	public String getTitle() { return _title; }

	public String getUrl() { return _url; }

	public void setTitle(String _title) { this._title = _title; }

	public void setUrl(String _url) { this._url = _url; }

	private String _title;
	private String _url;
}