package com.liferay.ratings.taglib.servlet.taglib;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ambrin Chaudhary
 */

public class RatingsTag extends IncludeTag {

	public void setType(String type) {
		_type = type;
	}

	protected String getType(HttpServletRequest httpServletRequest) {
		if (Validator.isNotNull(_type)) {
			return _type;
		}
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ratings:ratings:type", _type);
	}

	private static final String _PAGE = "/page.jsp";
	private String _type;
}
