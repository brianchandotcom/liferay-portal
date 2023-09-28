package com.liferay.apm.glowroot.proxy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
	protected void service(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			PermissionChecker permissionChecker = _getPermissionChecker(
				httpServletRequest);

			if (!permissionChecker.isOmniadmin()) {
				throw new PrincipalException.MustBeCompanyAdmin(
					permissionChecker.getUserId());
			}

			GzipEncodingRequestWrapper wrapper = new GzipEncodingRequestWrapper(
				httpServletRequest);

			super.service(wrapper, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private PermissionChecker _getPermissionChecker(
		HttpServletRequest httpServletRequest)
		throws Exception {

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			throw new PrincipalException.MustBeAuthenticated(0);
		}

		return _permissionCheckerFactory.create(user);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GlowrootProxyServlet.class);

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;
	
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
}
