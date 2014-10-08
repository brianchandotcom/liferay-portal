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

			_refresh(beanFactory, entry.getKey(), entry.getValue());
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

	public interface PACL {

		public Object getNewReferencedBean(
			String referencedBeanName, BeanFactory beanFactory);

	}

	private static void _refresh(
			BeanFactory beanFactory, Object targetBean,
			List<RefreshPoint> refreshPoints)
		throws Exception {

		for (RefreshPoint refreshPoint : refreshPoints) {
			_refresh(beanFactory, targetBean, refreshPoint);
		}
	}

	private static void _refresh(
			BeanFactory beanFactory, Object targetBean,
			RefreshPoint refreshPoint)
		throws Exception {

		Field field = refreshPoint._field;

		Object oldReferenceBean = field.get(targetBean);

		String referencedBeanName = refreshPoint._referencedBeanName;

		Object newReferencedBean = _pacl.getNewReferencedBean(
			referencedBeanName, beanFactory);

		if (oldReferenceBean == newReferencedBean) {
			return;
		}

		field.set(targetBean, newReferencedBean);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Refreshed field " + field + " with old value " +
					oldReferenceBean + " with new value " + newReferencedBean +
						" on bean " + targetBean);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanReferenceRefreshUtil.class);

	private static final PACL _pacl = new NoPACL();
	private static final Map<Object, List<RefreshPoint>>
		_registeredRefreshPoints =
			new IdentityHashMap<Object, List<RefreshPoint>>();

	private static class NoPACL implements PACL {

		@Override
		public Object getNewReferencedBean(
			String referencedBeanName, BeanFactory beanFactory) {

			return beanFactory.getBean(referencedBeanName);
		}

	}

	private static class RefreshPoint {

		public RefreshPoint(Field field, String referencedBeanName) {
			_field = field;
			_referencedBeanName = referencedBeanName;
		}

		private final Field _field;
		private final String _referencedBeanName;

	}

}