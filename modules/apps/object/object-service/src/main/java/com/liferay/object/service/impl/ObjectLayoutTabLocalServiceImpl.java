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

package com.liferay.object.service.impl;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.object.internal.layout.tab.screen.navigation.category.ObjectLayoutTabScreenNavigationCategory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.base.ObjectLayoutTabLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectLayoutTab",
	service = AopService.class
)
public class ObjectLayoutTabLocalServiceImpl
	extends ObjectLayoutTabLocalServiceBaseImpl {

	@Override
	public ObjectLayoutTab addObjectLayoutTab(
		User user, long objectLayoutId, long objectRelationshipId,
		Map<Locale, String> nameMap, int priority) {

		ObjectLayoutTab objectLayoutTab = objectLayoutTabPersistence.create(
			counterLocalService.increment());

		objectLayoutTab.setCompanyId(user.getCompanyId());
		objectLayoutTab.setUserId(user.getUserId());
		objectLayoutTab.setUserName(user.getFullName());
		objectLayoutTab.setObjectLayoutId(objectLayoutId);
		objectLayoutTab.setObjectRelationshipId(objectRelationshipId);
		objectLayoutTab.setNameMap(nameMap);
		objectLayoutTab.setPriority(priority);

		return objectLayoutTabPersistence.update(objectLayoutTab);
	}

	@Override
	public void deleteObjectLayoutObjectLayoutTabs(long objectLayoutId) {
		List<ObjectLayoutTab> objectLayoutTabs =
			getObjectLayoutObjectLayoutTabs(objectLayoutId);

		objectLayoutTabPersistence.removeByObjectLayoutId(objectLayoutId);

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrationMap.get(
					_getServiceRegistrationMapKey(objectLayoutTab));

			if (serviceRegistration == null) {
				return;
			}

			serviceRegistration.unregister();

			_serviceRegistrationMap.remove(
				_getServiceRegistrationMapKey(objectLayoutTab));
		}
	}

	@Override
	public List<ObjectLayoutTab> getObjectLayoutObjectLayoutTabs(
		long objectLayoutId) {

		return objectLayoutTabPersistence.findByObjectLayoutId(objectLayoutId);
	}

	@Override
	public void registryObjectLayoutTabScreenNavigationCategories(
		ObjectDefinition objectDefinition,
		List<ObjectLayoutTab> objectLayoutTabs) {

		Iterator<ObjectLayoutTab> objectLayoutTabIterator =
			ListUtil.reverseIterator(objectLayoutTabs);

		while (objectLayoutTabIterator.hasNext()) {
			ObjectLayoutTab objectLayoutTab = objectLayoutTabIterator.next();

			_serviceRegistrationMap.computeIfAbsent(
				_getServiceRegistrationMapKey(objectLayoutTab),
				serviceRegistrationMapKey -> _bundleContext.registerService(
					new String[] {
						ScreenNavigationCategory.class.getName(),
						ScreenNavigationEntry.class.getName()
					},
					new ObjectLayoutTabScreenNavigationCategory(
						objectDefinition, objectLayoutTab),
					HashMapDictionaryBuilder.<String, Object>put(
						"screen.navigation.category.order:Integer",
						objectLayoutTab.getObjectLayoutTabId()
					).put(
						"screen.navigation.entry.order:Integer",
						objectLayoutTab.getObjectLayoutId()
					).build()));
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private String _getServiceRegistrationMapKey(
		ObjectLayoutTab objectLayoutTab) {

		return StringBundler.concat(
			objectLayoutTab.getCompanyId(), StringPool.POUND,
			objectLayoutTab.getObjectLayoutTabId());
	}

	private BundleContext _bundleContext;
	private final Map<String, ServiceRegistration<?>> _serviceRegistrationMap =
		new ConcurrentHashMap<>();

}