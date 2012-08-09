/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.bean;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Shuyang Zhou
 */
public class BeanReferenceRefreshUtil {

	public static void refresh(BeanFactory beanFactory) throws Exception {
		for (Map.Entry<Object, List<RefreshPoint>> entry :
			_registeredRefreshPoints.entrySet()) {

			Object targetBean = entry.getKey();

			for (RefreshPoint refreshPoint : entry.getValue()) {
				Field field = refreshPoint._field;
				String referencedBeanName = refreshPoint._referencedBeanName;

				Object oldReferencedBean = field.get(targetBean);
				Object newReferencedBean = beanFactory.getBean(
					referencedBeanName);

				if (oldReferencedBean != newReferencedBean) {
					field.set(targetBean, newReferencedBean);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Refreshed on bean : " + targetBean +
							", field : " + field + ", old value : " +
							oldReferencedBean + ", new value : " +
							newReferencedBean);
					}
				}
			}
		}

		_registeredRefreshPoints.clear();
	}

	public static void registerRefreshPoint(
		Object targetBean, Field field, String referencedBeanName) {

		List<RefreshPoint> refreshPoints = _registeredRefreshPoints.get(
			targetBean);

		if (refreshPoints == null) {
			refreshPoints = new ArrayList<RefreshPoint>();

			_registeredRefreshPoints.put(targetBean, refreshPoints);
		}

		refreshPoints.add(new RefreshPoint(field, referencedBeanName));
	}

	private static Log _log = LogFactoryUtil.getLog(
		BeanReferenceRefreshUtil.class);

	private static Map<Object, List<RefreshPoint>> _registeredRefreshPoints =
		new IdentityHashMap<Object, List<RefreshPoint>>();

	private static class RefreshPoint {

		public RefreshPoint(Field field, String referencedBeanName) {
			_field = field;
			_referencedBeanName = referencedBeanName;
		}

		private Field _field;
		private String _referencedBeanName;

	}

}