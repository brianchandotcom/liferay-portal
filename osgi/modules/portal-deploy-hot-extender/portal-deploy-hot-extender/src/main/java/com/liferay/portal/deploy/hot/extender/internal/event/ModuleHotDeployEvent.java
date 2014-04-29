package com.liferay.portal.deploy.hot.extender.internal.event;

import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import org.osgi.framework.Bundle;

import javax.servlet.ServletContext;
import java.util.Dictionary;

/**
 * @author Miguel Pastor
 */
public class ModuleHotDeployEvent extends HotDeployEvent {

	public ModuleHotDeployEvent(
		ServletContext servletContext, ClassLoader contextClassLoader,
		Bundle bundle) {

		super(servletContext, contextClassLoader);

		_bundle = bundle;
	}

	public String getBundleHeader(String headerName) {
		Dictionary<String, String> headers = _bundle.getHeaders();

		return headers.get(headerName);
	}

	public boolean hasBundleHeader(String headerName) {
		Dictionary<String, String> headers = _bundle.getHeaders();

		return headers.get(headerName) != null;
	}

	private final Bundle _bundle;

}
