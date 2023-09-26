package com.liferay.apm.glowroot.proxy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.osgi.service.component.annotations.Component;

@Component(
	    immediate = true,
	    property = {
	        "osgi.http.whiteboard.context.path=/",
	        "osgi.http.whiteboard.servlet.pattern=/glowroot/*",
	        "servlet.init.targetUri=http://localhost:4000/o/glowroot"
	    },
	    service = Servlet.class
	)
public class GlowrootProxyServlet extends ProxyServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ServletException, IOException {

		long userId;
		Exception exception;
		try {
			userId = _getUser(httpServletRequest).getUserId();
			if(OmniadminUtil.isOmniadmin(userId)) {
				GzipEncodingRequestWrapper wrapper = new GzipEncodingRequestWrapper(httpServletRequest);
				super.service(wrapper, httpServletResponse);
				return;
			} else {
				exception = new PortalException("Only omniadmin can access Glowroot APM");
			}		
		} catch (Exception e) {
			exception = new PortalException("Only omniadmin can access Glowroot APM", e);
		}
		PortalUtil.sendError(HttpServletResponse.SC_FORBIDDEN, exception, httpServletRequest, httpServletResponse);
		
	}
	
	// Need to remove the Accept-Encoding header because gzip breaks the ProxyServlet
	private class GzipEncodingRequestWrapper extends HttpServletRequestWrapper {
	    
		public GzipEncodingRequestWrapper(HttpServletRequest request) {
	        super(request);
	    }

	    private Set<String> headerNameSet;

	    @Override
	    public Enumeration<String> getHeaderNames() {
	        if (headerNameSet == null) {
	            // first time this method is called, cache the wrapped request's header names:
	            headerNameSet = new HashSet<>();
	            Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
	            while (wrappedHeaderNames.hasMoreElements()) {
	                String headerName = wrappedHeaderNames.nextElement();
	                if (!"Accept-Encoding".equalsIgnoreCase(headerName)) {
	                    headerNameSet.add(headerName);
	                }
	            }
	        }
	        return Collections.enumeration(headerNameSet);
	    }

	    @Override
	    public Enumeration<String> getHeaders(String name) {
	        if ("Accept-Encoding".equalsIgnoreCase(name)) {
	            return Collections.<String>emptyEnumeration();
	        }
	        return super.getHeaders(name);
	    }

	    @Override
	    public String getHeader(String name) {
	        if ("Accept-Encoding".equalsIgnoreCase(name)) {
	            return null;
	        }
	        return super.getHeader(name);
	    }
	}
	
	private static User _getUser(HttpServletRequest httpServletRequest)
			throws Exception {

		HttpSession httpSession = httpServletRequest.getSession();

		if (PortalSessionThreadLocal.getHttpSession() == null) {
			PortalSessionThreadLocal.setHttpSession(httpSession);
		}

		User user = PortalUtil.getUser(httpServletRequest);

		if (user != null) {
			return user;
		}

		String userIdString = (String)httpSession.getAttribute("j_username");
		String password = (String)httpSession.getAttribute("j_password");

		if ((userIdString != null) && (password != null)) {
			long userId = GetterUtil.getLong(userIdString);

			return UserLocalServiceUtil.getUser(userId);
		}

		Company company = CompanyLocalServiceUtil.getCompany(
			PortalUtil.getCompanyId(httpServletRequest));

		return company.getGuestUser();
	}
	
}
