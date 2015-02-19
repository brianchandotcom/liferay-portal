package com.liferay.portal.servlet.filters.audit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
public class AuditRequest extends HttpServletRequestWrapper {

	public AuditRequest(HttpServletRequest request) {
		super(request);
		_request = request;
	}

	@Override
	public String getRemoteAddr() {
		String ip = _request.getHeader("X-FORWARDED-FOR");

		if (ip != null) {
			return ip;
		}

		return super.getRemoteAddr();
	}

	private final HttpServletRequest _request;

}