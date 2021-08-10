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

package com.liferay.custom.elements.service.impl;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.base.CustomElementsSourceLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.custom.elements.model.CustomElementsSource",
	service = AopService.class
)
public class CustomElementsSourceLocalServiceImpl
	extends CustomElementsSourceLocalServiceBaseImpl {

	@Override
	public CustomElementsSource addCustomElementsSource(
			String htmlElementName, String name, String url,
			ServiceContext serviceContext)
		throws PortalException {

		long customElementsSourceId = counterLocalService.increment();

		CustomElementsSource customElementsSource =
			customElementsSourcePersistence.create(customElementsSourceId);

		customElementsSource.setUuid(serviceContext.getUuid());

		User user = userLocalService.getUser(serviceContext.getUserId());

		customElementsSource.setUserId(user.getUserId());
		customElementsSource.setUserName(user.getFullName());

		_setCustomElementsSourceFields(
			customElementsSource, htmlElementName, name, url);

		return customElementsSourcePersistence.update(customElementsSource);
	}

	@Override
	public List<CustomElementsSource> searchCustomElementsSources(
		String keywords, int start, int end, Sort sort) {

		if (Validator.isNull(keywords)) {
			return customElementsSourcePersistence.findAll(
				start, end, _getOrderByComparator(sort));
		}

		return customElementsSourcePersistence.findByName(
			keywords, start, end, _getOrderByComparator(sort));
	}

	@Override
	public int searchCustomElementsSourcesCount(String keywords) {
		if (Validator.isNull(keywords)) {
			return customElementsSourcePersistence.countAll();
		}

		return customElementsSourcePersistence.countByName(keywords);
	}

	@Override
	public CustomElementsSource updateCustomElementsSource(
			long customElementsSourceId, String htmlElementName, String name,
			String url, ServiceContext serviceContext)
		throws PortalException {

		CustomElementsSource customElementsSource =
			customElementsSourcePersistence.findByPrimaryKey(
				customElementsSourceId);

		_setCustomElementsSourceFields(
			customElementsSource, htmlElementName, name, url);

		return customElementsSourcePersistence.update(customElementsSource);
	}

	private OrderByComparator<CustomElementsSource> _getOrderByComparator(
		Sort sort) {

		if (sort == null) {
			return null;
		}

		return new OrderByComparator<CustomElementsSource>() {

			@Override
			public int compare(
				CustomElementsSource customElementsSource1,
				CustomElementsSource customElementsSource2) {

				Comparable<Object> value1 =
					(Comparable)BeanPropertiesUtil.getObject(
						customElementsSource1, sort.getFieldName());

				Comparable<Object> value2 =
					(Comparable)BeanPropertiesUtil.getObject(
						customElementsSource1, sort.getFieldName());

				if (sort.isReverse()) {
					return value2.compareTo(value1);
				}

				return value1.compareTo(value2);
			}

			@Override
			public String[] getOrderByFields() {
				return new String[] {sort.getFieldName()};
			}

		};
	}

	private void _setCustomElementsSourceFields(
		CustomElementsSource customElementsSource, String htmlElementName,
		String name, String url) {

		customElementsSource.setHtmlElementName(htmlElementName);
		customElementsSource.setName(name);
		customElementsSource.setUrl(url);
	}

}