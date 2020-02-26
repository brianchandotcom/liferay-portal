package com.liferay.ratings.taglib.servlet.taglib;

import com.liferay.ratings.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Ambrín Chaudhary
 */
public class RatingsTag extends IncludeTag {

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getType(HttpServletRequest httpServletRequest) {
		return _type;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("liferay-ratings:ratings:type", _type);
	}

	private static final String _PAGE = "/page.jsp";

	private String _type;

}