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

package com.liferay.segments.internal.context;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.DeviceDetectionUtil;
import com.liferay.portal.kernel.mobile.device.Dimensions;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.contributor.RequestContextContributor;
import com.liferay.segments.internal.odata.entity.ContextEntityModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = RequestContextMapper.class)
public class RequestContextMapper {

	public Context map(HttpServletRequest httpServletRequest) {
		Context context = new Context();

		context.put(
			Context.BROWSER, _browserSniffer.getBrowserId(httpServletRequest));
		context.put(Context.COOKIES, _getCookies(httpServletRequest));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Device device = DeviceDetectionUtil.detectDevice(httpServletRequest);

		Dimensions screenResolution = null;

		if ((device != null) &&
			!Objects.equals(device, UnknownDevice.getInstance())) {

			context.put(Context.DEVICE_BRAND, device.getBrand());
			context.put(Context.DEVICE_MODEL, device.getModel());

			screenResolution = device.getScreenResolution();
		}
		else {
			context.put(Context.DEVICE_BRAND, StringPool.BLANK);
			context.put(Context.DEVICE_MODEL, StringPool.BLANK);

			screenResolution = Dimensions.UNKNOWN;
		}

		context.put(
			Context.DEVICE_SCREEN_RESOLUTION_HEIGHT,
			(double)screenResolution.getHeight());
		context.put(
			Context.DEVICE_SCREEN_RESOLUTION_WIDTH,
			(double)screenResolution.getWidth());

		context.put(Context.LANGUAGE_ID, themeDisplay.getLanguageId());

		User user = themeDisplay.getUser();

		if ((user != null) && (user.getLastLoginDate() != null)) {
			Date lastLoginDate = user.getLastLoginDate();

			context.put(
				Context.LAST_SIGN_IN_DATE_TIME,
				ZonedDateTime.ofInstant(
					lastLoginDate.toInstant(), ZoneOffset.UTC));
		}
		else {
			context.put(
				Context.LAST_SIGN_IN_DATE_TIME,
				ZonedDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC));
		}

		context.put(Context.LOCAL_DATE, LocalDate.from(ZonedDateTime.now()));
		context.put(
			Context.REFERRER_URL,
			GetterUtil.getString(
				httpServletRequest.getHeader(HttpHeaders.REFERER)));
		context.put(Context.SIGNED_IN, themeDisplay.isSignedIn());
		context.put(
			Context.URL, _portal.getCurrentCompleteURL(httpServletRequest));

		String userAgent = GetterUtil.getString(
			httpServletRequest.getHeader(HttpHeaders.USER_AGENT));

		context.put(Context.USER_AGENT, userAgent);

		context.put(Context.USER_ID, _getUserId(httpServletRequest));

		for (RequestContextContributor requestContextContributor :
				_requestContextContributorServiceTrackerMap.values()) {

			requestContextContributor.contribute(context, httpServletRequest);
		}

		return context;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_requestContextContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, RequestContextContributor.class,
				"request.context.contributor.key",
				new RequestContextContributorServiceTrackerCustomizer(
					bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		_requestContextContributorServiceTrackerMap.close();
	}

	private String[] _getCookies(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();

		if (cookies == null) {
			return new String[0];
		}

		return Stream.of(
			cookies
		).map(
			c -> c.getName() + "=" + c.getValue()
		).toArray(
			String[]::new
		);
	}

	private String _getUserId(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();

		if (ArrayUtil.isEmpty(cookies)) {
			return StringPool.BLANK;
		}

		return Stream.of(
			cookies
		).filter(
			cookie -> Objects.equals(cookie.getName(), Context.USER_ID)
		).map(
			Cookie::getValue
		).findFirst(
		).orElse(
			StringPool.BLANK
		);
	}

	@Reference
	private BrowserSniffer _browserSniffer;

	@Reference
	private ContextRegistrar _contextRegistrar;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, RequestContextContributor>
		_requestContextContributorServiceTrackerMap;
	private final Map
		<ServiceReference<RequestContextContributor>,
		 ServiceRegistration<EntityModel>> _serviceRegistrations =
			new ConcurrentHashMap<>();

	private class RequestContextContributorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<RequestContextContributor, RequestContextContributor> {

		public RequestContextContributorServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public RequestContextContributor addingService(
			ServiceReference<RequestContextContributor> serviceReference) {

			String requestContextContributorKey = GetterUtil.getString(
				serviceReference.getProperty(
					"request.context.contributor.key"));
			String requestContextContributorType = GetterUtil.getString(
				serviceReference.getProperty(
					"request.context.contributor.type"));

			List<EntityField> customEntityFields = _addCustomEntityField(
				requestContextContributorKey, requestContextContributorType);

			_contextRegistrar.unregister();

			_contextRegistrar.register(
				new ContextEntityModel(customEntityFields));

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<RequestContextContributor> serviceReference,
			RequestContextContributor requestContextContributor) {

			removedService(serviceReference, requestContextContributor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<RequestContextContributor> serviceReference,
			RequestContextContributor requestContextContributor) {

			String requestContextContributorKey = GetterUtil.getString(
				serviceReference.getProperty(
					"request.context.contributor.key"));

			List<EntityField> customEntityFields = _removeCustomEntityField(
				requestContextContributorKey);

			_contextRegistrar.unregister();

			_contextRegistrar.register(
				new ContextEntityModel(customEntityFields));

			_bundleContext.ungetService(serviceReference);
		}

		private List<EntityField> _addCustomEntityField(
			String contextFieldKey, String contextFieldType) {

			EntityField entityField = null;

			if (contextFieldType.equals("boolean")) {
				entityField = new BooleanEntityField(
					contextFieldKey, locale -> contextFieldKey);
			}
			else if (contextFieldType.equals("date")) {
				entityField = new DateTimeEntityField(
					contextFieldKey,
					locale -> Field.getSortableFieldName(contextFieldKey),
					locale -> contextFieldKey);
			}
			else if (contextFieldType.equals("double")) {
				entityField = new DoubleEntityField(
					contextFieldKey, locale -> contextFieldKey);
			}
			else if (contextFieldType.equals("integer")) {
				entityField = new IntegerEntityField(
					contextFieldKey, locale -> contextFieldKey);
			}
			else {
				entityField = new StringEntityField(
					contextFieldKey, locale -> contextFieldKey);
			}

			_customEntityFields.put(contextFieldKey, entityField);

			return new ArrayList(_customEntityFields.values());
		}

		private List<EntityField> _removeCustomEntityField(
			String requestContextContributorKey) {

			_customEntityFields.remove(requestContextContributorKey);

			return new ArrayList(_customEntityFields.values());
		}

		private final BundleContext _bundleContext;
		private Map<String, EntityField> _customEntityFields = new HashMap();

	}

}