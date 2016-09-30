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

package com.liferay.social.office.upgrade.internal;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=executeAll",
		"osgi.command.function=hideTasksLayout",
		"osgi.command.function=removeTasksPortlet",
		"osgi.command.function=updateEventsDisplay",
		"osgi.command.function=updateTheme", "osgi.command.scope=socialOffice"
	},
	service = SocialOfficeUpgradeOSGiCommands.class
)
public class SocialOfficeUpgradeOSGiCommands {

	public void executeAll() throws PortalException {
		hideTasksLayout();
		removeTasksPortlet();
		updateEventsDisplay();
		updateTheme();
	}

	public void hideTasksLayout() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq("friendlyURL", "/so/tasks"));
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq("hidden", false));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				public void performAction(Layout layout)
					throws PortalException {

					layout.setHidden(true);

					_layoutLocalService.updateLayout(layout);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		System.out.printf(
			"[socialOffice:hideTasksLayout] %d layouts updated.%n",
			atomicInteger.get());
	}

	public void removeTasksPortlet() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_portletPreferencesLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.like(
							"portletId", "%tasksportlet%"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<PortletPreferences>() {

				public void performAction(PortletPreferences portletPreferences)
					throws PortalException {

					_portletPreferencesLocalService.deletePersistedModel(
						portletPreferences);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		System.out.printf(
			"[socialOffice:removeTasksPortlet] %d Tasks portlet preferences " +
				"deleted.%n",
			atomicInteger.get());
	}

	public void updateEventsDisplay() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.like(
							"typeSettings", "%1_WAR_eventsdisplayportlet%"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				public void performAction(Layout layout)
					throws PortalException {

					String newPortletId = PortletConstants.assemblePortletId(
						"com_liferay_calendar_web_portlet_CalendarPortlet",
						StringUtil.randomId());

					_updateLayout(layout, newPortletId);

					_updatePortletPreferences(layout, newPortletId);

					atomicInteger.incrementAndGet();
				}

				private void _addPreference(
					Element rootElement, String name, String value) {

					Element nameElement = SAXReaderUtil.createElement("name");

					nameElement.setText(name);

					Element valueElement = SAXReaderUtil.createElement("value");

					valueElement.setText(value);

					Element preferenceElement = SAXReaderUtil.createElement(
						"preference");

					preferenceElement.add(nameElement);
					preferenceElement.add(valueElement);

					rootElement.add(preferenceElement);
				}

				private void _addPreferences(
						PortletPreferences portletPreferences)
					throws PortalException {

					Document document;

					try {
						document = SAXReaderUtil.read(
							portletPreferences.getPreferences());
					}
					catch (DocumentException de) {
						throw new PortalException(de);
					}

					Element preferencesElement = document.getRootElement();

					_addPreference(
						preferencesElement, "displaySchedulerHeader", "false");
					_addPreference(
						preferencesElement, "showMonthView", "false");
					_addPreference(
						preferencesElement, "showAgendaView", "true");
					_addPreference(preferencesElement, "showWeekView", "false");
					_addPreference(preferencesElement, "showDayView", "false");
					_addPreference(preferencesElement, "defaultView", "agenda");
					_addPreference(
						preferencesElement, "displaySchedulerOnly", "true");
					_addPreference(
						preferencesElement, "showUserEvents", "false");

					portletPreferences.setPreferences(
						preferencesElement.asXML());
				}

				private void _updateLayout(Layout layout, String newPortletId) {
					String typeSettings = layout.getTypeSettings();

					typeSettings = typeSettings.replace(
						"1_WAR_eventsdisplayportlet", newPortletId);

					layout.setTypeSettings(typeSettings);

					_layoutLocalService.updateLayout(layout);
				}

				private void _updatePortletPreferences(
						Layout layout, String newPortletId)
					throws PortalException {

					List<PortletPreferences> preferencesList =
						_portletPreferencesLocalService.getPortletPreferences(
							layout.getPlid(), "1_WAR_eventsdisplayportlet");

					for (PortletPreferences preferences : preferencesList) {
						_addPreferences(preferences);

						preferences.setPortletId(newPortletId);

						_portletPreferencesLocalService.
							updatePortletPreferences(preferences);
					}
				}

			});

		actionableDynamicQuery.performActions();

		System.out.printf(
			"[socialOffice:updateEventsDisplay] %d Events Display instances " +
				"converted to Calendar.%n",
			atomicInteger.get());
	}

	public void updateTheme() throws PortalException {
		int layoutsCount = _updateLayoutTheme();

		System.out.printf(
			"[socialOffice:updateTheme] %d layouts updated.%n", layoutsCount);

		int layoutSetsCount = _updateLayoutSetTheme();

		System.out.printf(
			"[socialOffice:updateTheme] %d layout sets updated.%n",
			layoutSetsCount);
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	private int _updateLayoutSetTheme() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutSetLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq(
							"themeId", "so_WAR_sotheme"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LayoutSet>() {

				public void performAction(LayoutSet layoutSet)
					throws PortalException {

					layoutSet.setThemeId("classic_WAR_classictheme");

					_layoutSetLocalService.updateLayoutSet(layoutSet);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		return atomicInteger.get();
	}

	private int _updateLayoutTheme() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq(
							"themeId", "so_WAR_sotheme"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				public void performAction(Layout layout)
					throws PortalException {

					layout.setThemeId("classic_WAR_classictheme");

					_layoutLocalService.updateLayout(layout);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		return atomicInteger.get();
	}

	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}