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

package com.liferay.portal.test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.util.test.TestPropsValues;

import java.io.File;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Miguel Pastor
 */
public class MainServletExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		ServiceTestUtil.destroyServices();

		try {
			LuceneHelperUtil.delete(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		Map<Class<?>, List<Field>> deleteAfterTestRunFields =
			new HashMap<Class<?>, List<Field>>();

		Class<?> testClass = testContext.getClazz();

		Object instance = testContext.getInstance();

		while (testClass != null) {
			for (Field field : testClass.getDeclaredFields()) {
				DeleteAfterTestRun deleteAfterTestRun = field.getAnnotation(
					DeleteAfterTestRun.class);

				if (deleteAfterTestRun == null) {
					continue;
				}

				Class<?> fieldClass = field.getType();

				Class<? extends PersistedModel> elementType =
					deleteAfterTestRun.elementType();

				if (elementType == PersistedModel.class) {
					if (!PersistedModel.class.isAssignableFrom(fieldClass)) {
						throw new IllegalArgumentException(
							"Unable to annotate field " + field +
								" because it is not of type " +
									PersistedModel.class);
					}

					addField(deleteAfterTestRunFields, fieldClass, field);

					continue;
				}

				if (fieldClass.isArray()) {
					if (!elementType.isAssignableFrom(
							fieldClass.getComponentType())) {

						throw new IllegalArgumentException(
							"Unable to annotate field " + field +
								" because it is not an array of type " +
									elementType);
					}

					addField(deleteAfterTestRunFields, elementType, field);

					continue;
				}

				if (Collection.class.isAssignableFrom(fieldClass)) {
					try {
						field.setAccessible(true);

						Collection<?> collection = (Collection<?>)field.get(
							instance);

						if (collection == null) {
							continue;
						}

						for (Object obj : collection) {
							if (!elementType.isAssignableFrom(obj.getClass())) {
								throw new IllegalArgumentException(
									"Unable to annotate field " + field +
										" because it is not a Collection " +
											"of type " + elementType);
							}
						}
					}
					catch (Exception e) {
						_log.error(
							"Unable to detect Collection element type", e);
					}

					addField(deleteAfterTestRunFields, elementType, field);

					continue;
				}

				throw new IllegalArgumentException(
					"Unable to annotate field " + field +
						" with element type " + elementType +
							" because it is not an array or collect of type " +
								PersistedModel.class);
			}

			testClass = testClass.getSuperclass();
		}

		Set<Map.Entry<Class<?>, List<Field>>> set =
			deleteAfterTestRunFields.entrySet();

		Iterator<Map.Entry<Class<?>, List<Field>>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Class<?>, List<Field>> entry = iterator.next();

			Class<?> clazz = entry.getKey();

			if (_orderedClasses.contains(clazz)) {
				continue;
			}

			iterator.remove();

			for (Field field : entry.getValue()) {
				removeField(field, instance);
			}
		}

		for (Class<?> clazz : _orderedClasses) {
			List<Field> fields = deleteAfterTestRunFields.remove(clazz);

			if (fields == null) {
				continue;
			}

			for (Field field : fields) {
				removeField(field, instance);
			}
		}
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		ServiceTestUtil.initServices();

		ServiceTestUtil.initPermissions();

		if (mainServlet != null) {
			return;
		}

		MockServletContext mockServletContext =
			new AutoDeployMockServletContext(
				getResourceBasePath(), new FileSystemResourceLoader());

		MockServletConfig mockServletConfig = new MockServletConfig(
			mockServletContext);

		mainServlet = new MainServlet();

		try {
			mainServlet.init(mockServletConfig);
		}
		catch (ServletException se) {
			throw new RuntimeException(
				"The main servlet could not be initialized");
		}
	}

	protected void addField(
		Map<Class<?>, List<Field>> deleteAfterTestRunFields, Class<?> clazz,
		Field field) {

		List<Field> fields = deleteAfterTestRunFields.get(clazz);

		if (fields == null) {
			fields = new ArrayList<Field>();

			deleteAfterTestRunFields.put(clazz, fields);
		}

		field.setAccessible(true);

		fields.add(field);
	}

	protected String getResourceBasePath() {
		File file = new File("portal-web/docroot");

		return "file:" + file.getAbsolutePath();
	}

	protected void removeField(Field field, Object instance) {
		try {
			Object obj = field.get(instance);

			if (obj == null) {
				return;
			}

			Class<?> fieldClass = field.getType();

			PersistedModelLocalService persistedModelLocalService = null;

			DeleteAfterTestRun deleteAfterTestRun = field.getAnnotation(
				DeleteAfterTestRun.class);

			Class<? extends PersistedModel> elementType =
				deleteAfterTestRun.elementType();

			if (elementType == PersistedModel.class) {
				persistedModelLocalService =
					PersistedModelLocalServiceRegistryUtil.
						getPersistedModelLocalService(fieldClass.getName());
			}
			else {
				persistedModelLocalService =
					PersistedModelLocalServiceRegistryUtil.
						getPersistedModelLocalService(elementType.getName());
			}

			if (fieldClass.isArray()) {
				for (PersistedModel persistedModel : (PersistedModel[])obj) {
					if (persistedModel == null) {
						continue;
					}

					persistedModelLocalService.deletePersistedModel(
						persistedModel);
				}
			}
			else if (Collection.class.isAssignableFrom(fieldClass)) {
				Collection<? extends PersistedModel> collection =
					(Collection<? extends PersistedModel>)obj;

				for (PersistedModel persistedModel : collection) {
					persistedModelLocalService.deletePersistedModel(
						persistedModel);
				}
			}
			else {
				persistedModelLocalService.deletePersistedModel(
					(PersistedModel)obj);
			}

			field.set(instance, null);
		}
		catch (Exception e) {
			_log.error("Unable to delete", e);
		}
	}

	protected static MainServlet mainServlet;

	protected class AutoDeployMockServletContext extends MockServletContext {

		public AutoDeployMockServletContext(
			String resourceBasePath, ResourceLoader resourceLoader) {

			super(resourceBasePath, resourceLoader);
		}

		/**
		 * @see com.liferay.portal.server.capabilities.TomcatServerCapabilities
		 */
		protected Boolean autoDeploy = Boolean.TRUE;

	}

	private static Log _log = LogFactoryUtil.getLog(
		MainServletExecutionTestListener.class);

	private static Set<Class<?>> _orderedClasses = new LinkedHashSet<Class<?>>(
		Arrays.asList(
			User.class, Organization.class, Role.class, UserGroup.class,
			Group.class));

}