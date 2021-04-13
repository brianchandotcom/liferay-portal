/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.petra.portlet.url.builder;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Hugo Huijser
 */
public class PortletURLBuilder {

	public static PortletURLStep create(PortletURL portletURL) {
		return new PortletURLStep(portletURL);
	}

	public static PortletURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new PortletURLStep(liferayPortletResponse.createActionURL());
	}

	public static PortletURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse, MimeResponse.Copy copy) {

		return new PortletURLStep(liferayPortletResponse.createActionURL(copy));
	}

	public static PortletURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new PortletURLStep(
			liferayPortletResponse.createActionURL(portletName));
	}

	public static PortletURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		MimeResponse.Copy copy) {

		return new PortletURLStep(
			liferayPortletResponse.createActionURL(portletName, copy));
	}

	public static PortletURLStep createActionURL(MimeResponse mimeResponse) {
		return new PortletURLStep(mimeResponse.createActionURL());
	}

	public static PortletURLStep createActionURL(
		MimeResponse mimeResponse, MimeResponse.Copy copy) {

		return new PortletURLStep(mimeResponse.createActionURL(copy));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle, boolean includeLinkToLayoutUuid) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle, includeLinkToLayoutUuid));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle, MimeResponse.Copy copy) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle, copy));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle, copy, includeLinkToLayoutUuid));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, String lifecycle) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(lifecycle));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		String lifecycle) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(
				portletName, lifecycle));
	}

	public static PortletURLStep createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		String lifecycle, MimeResponse.Copy copy) {

		return new PortletURLStep(
			liferayPortletResponse.createLiferayPortletURL(
				portletName, lifecycle, copy));
	}

	public static PortletURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new PortletURLStep(liferayPortletResponse.createRenderURL());
	}

	public static PortletURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse, MimeResponse.Copy copy) {

		return new PortletURLStep(liferayPortletResponse.createRenderURL(copy));
	}

	public static PortletURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new PortletURLStep(
			liferayPortletResponse.createRenderURL(portletName));
	}

	public static PortletURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		MimeResponse.Copy copy) {

		return new PortletURLStep(
			liferayPortletResponse.createRenderURL(portletName, copy));
	}

	public static PortletURLStep createRenderURL(MimeResponse mimeResponse) {
		return new PortletURLStep(mimeResponse.createRenderURL());
	}

	public static PortletURLStep createRenderURL(
		MimeResponse mimeResponse, MimeResponse.Copy copy) {

		return new PortletURLStep(mimeResponse.createRenderURL(copy));
	}

	public static class PortletURLStep
		implements ActionNameStep, AfterActionNameStep, AfterMVCPathStep,
				   AfterMVCRenderCommandNameStep, AfterParameterStep,
				   AfterPortletModeStep, AfterRedirectStep, AfterSecureStep,
				   AfterWindowStateStep, BuildStep, MVCPathStep,
				   MVCRenderCommandNameStep, ParameterStep, PortletModeStep,
				   RedirectStep, SecureStep, WindowStateStep {

		public PortletURLStep(PortletURL portletURL) {
			_portletURL = portletURL;
		}

		@Override
		public PortletURL build() {
			return _portletURL;
		}

		@Override
		public String buildString() {
			return _portletURL.toString();
		}

		@Override
		public AfterActionNameStep setActionName(String value) {
			setParameter(ActionRequest.ACTION_NAME, value);

			return this;
		}

		@Override
		public AfterActionNameStep setActionName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			setParameter(ActionRequest.ACTION_NAME, valueUnsafeSupplier);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(String value) {
			setParameter("mvcPath", value);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			setParameter("mvcPath", valueUnsafeSupplier);

			return this;
		}

		@Override
		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			String value) {

			setParameter("mvcRenderCommandName", value);

			return this;
		}

		@Override
		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			setParameter("mvcRenderCommandName", valueUnsafeSupplier);

			return this;
		}

		@Override
		public AfterParameterStep setParameter(String name, Object value) {
			setParameter(name, String.valueOf(value));

			return this;
		}

		@Override
		public AfterParameterStep setParameter(String name, String value) {
			_portletURL.setParameter(name, value);

			return this;
		}

		@Override
		public AfterParameterStep setParameter(String name, String... values) {
			_portletURL.setParameter(name, values);

			return this;
		}

		@Override
		public AfterParameterStep setParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			try {
				Object value = valueUnsafeSupplier.get();

				if (value == null) {
					return this;
				}

				if (value instanceof String[]) {
					setParameter(key, (String[])value);
				}
				else {
					setParameter(key, String.valueOf(value));
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		@Override
		public AfterParameterStep setParameters(
			Map<String, String[]> parameters) {

			_portletURL.setParameters(parameters);

			return this;
		}

		@Override
		public AfterPortletModeStep setPortletMode(PortletMode portletMode) {
			try {
				_portletURL.setPortletMode(portletMode);
			}
			catch (PortletModeException portletModeException) {
				throw new SystemException(portletModeException);
			}

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(String value) {
			setParameter("redirect", value);

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			setParameter("redirect", valueUnsafeSupplier);

			return this;
		}

		@Override
		public AfterSecureStep setSecure(boolean secure) {
			try {
				_portletURL.setSecure(secure);
			}
			catch (PortletSecurityException portletSecurityException) {
				throw new SystemException(portletSecurityException);
			}

			return this;
		}

		@Override
		public AfterWindowStateStep setWindowState(WindowState windowState) {
			try {
				_portletURL.setWindowState(windowState);
			}
			catch (WindowStateException windowStateException) {
				throw new SystemException(windowStateException);
			}

			return this;
		}

		private final PortletURL _portletURL;

	}

	public interface ActionNameStep {

		public AfterActionNameStep setActionName(String value);

		public AfterActionNameStep setActionName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface AfterActionNameStep
		extends BuildStep, ParameterStep, PortletModeStep, RedirectStep,
				SecureStep, WindowStateStep {
	}

	public interface AfterMVCPathStep
		extends ActionNameStep, BuildStep, MVCRenderCommandNameStep,
				ParameterStep, PortletModeStep, RedirectStep, SecureStep,
				WindowStateStep {
	}

	public interface AfterMVCRenderCommandNameStep
		extends ActionNameStep, BuildStep, ParameterStep, PortletModeStep,
				RedirectStep, SecureStep, WindowStateStep {
	}

	public interface AfterParameterStep
		extends BuildStep, ParameterStep, PortletModeStep, SecureStep,
				WindowStateStep {
	}

	public interface AfterPortletModeStep
		extends BuildStep, SecureStep, WindowStateStep {
	}

	public interface AfterRedirectStep
		extends BuildStep, ParameterStep, PortletModeStep, SecureStep,
				WindowStateStep {
	}

	public interface AfterSecureStep extends BuildStep, WindowStateStep {
	}

	public interface AfterWindowStateStep extends BuildStep {
	}

	public interface BuildStep {

		public PortletURL build();

		public String buildString();

	}

	public interface MVCPathStep {

		public AfterMVCPathStep setMVCPath(String value);

		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface MVCRenderCommandNameStep {

		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			String value);

		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface ParameterStep {

		public AfterParameterStep setParameter(String name, Object value);

		public AfterParameterStep setParameter(String name, String value);

		public AfterParameterStep setParameter(String name, String... values);

		public AfterParameterStep setParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

		public AfterParameterStep setParameters(
			Map<String, String[]> parameters);

	}

	public interface PortletModeStep {

		public AfterPortletModeStep setPortletMode(PortletMode portletMode);

	}

	public interface RedirectStep {

		public AfterRedirectStep setRedirect(String value);

		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface SecureStep {

		public AfterSecureStep setSecure(boolean secure);

	}

	@FunctionalInterface
	public interface UnsafeSupplier<T, E extends Throwable> {

		public T get() throws E;

	}

	public interface WindowStateStep {

		public AfterWindowStateStep setWindowState(WindowState windowState);

	}

}