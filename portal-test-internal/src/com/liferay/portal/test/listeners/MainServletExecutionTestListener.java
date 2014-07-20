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

package com.liferay.portal.test.listeners;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.util.test.TestPropsValues;

import java.io.File;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
			SearchEngineUtil.removeCompany(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void runAfterTest(TestContext testContext) {
		Map<Class<?>, FieldBag> deleteAfterTestRunFieldBags =
			new HashMap<Class<?>, FieldBag>();

		Class<?> testClass = testContext.getClazz();

		while (testClass != null) {
			for (Field field : testClass.getDeclaredFields()) {
				DeleteAfterTestRun deleteAfterTestRun = field.getAnnotation(
					DeleteAfterTestRun.class);

				if (deleteAfterTestRun == null) {
					continue;
				}

				Class<?> fieldClass = field.getType();

				if (PersistedModel.class.isAssignableFrom(fieldClass)) {
					addField(deleteAfterTestRunFieldBags, fieldClass, field);

					continue;
				}

				if (fieldClass.isArray()) {
					if (!PersistedModel.class.isAssignableFrom(
							fieldClass.getComponentType())) {

						throw new IllegalArgumentException(
							"Unable to annotate field " + field +
								" because it is not an array of type " +
									PersistedModel.class.getName());
					}

					addField(
						deleteAfterTestRunFieldBags,
						fieldClass.getComponentType(), field);

					continue;
				}

				if (Collection.class.isAssignableFrom(fieldClass)) {
					try {
						field.setAccessible(true);

						Collection<?> collection = (Collection<?>)field.get(
							testContext.getInstance());

						if ((collection == null) || collection.isEmpty()) {
							continue;
						}

						Class<?> collectionType = getCollectionType(collection);

						if (collectionType == null) {
							throw new IllegalArgumentException(
								"Unable to annotate field " + field +
									" because it is not a collection of type " +
										PersistedModel.class.getName());
						}

						addField(
							deleteAfterTestRunFieldBags, collectionType, field);
					}
					catch (Exception e) {
						_log.error(
							"Unable to detect collection element type", e);
					}

					continue;
				}

				StringBundler sb = new StringBundler(6);

				sb.append("Unable to annotate field ");
				sb.append(field);
				sb.append(" because it is not type of ");
				sb.append(PersistedModel.class.getName());
				sb.append(" nor an array or collection of ");
				sb.append(PersistedModel.class.getName());

				throw new IllegalArgumentException(sb.toString());
			}

			testClass = testClass.getSuperclass();
		}

		Set<Map.Entry<Class<?>, FieldBag>> set =
			deleteAfterTestRunFieldBags.entrySet();

		Iterator<Map.Entry<Class<?>, FieldBag>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Class<?>, FieldBag> entry = iterator.next();

			Class<?> clazz = entry.getKey();

			if (_orderedClasses.contains(clazz)) {
				continue;
			}

			iterator.remove();

			removeField(entry.getValue(), testContext.getInstance());
		}

		for (Class<?> clazz : _orderedClasses) {
			FieldBag fieldBag = deleteAfterTestRunFieldBags.remove(clazz);

			if (fieldBag == null) {
				continue;
			}

			removeField(fieldBag, testContext.getInstance());
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

		ServletContextPool.put(StringPool.BLANK, mockServletContext);

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
		Map<Class<?>, FieldBag> deleteAfterTestRunFieldBags, Class<?> clazz,
		Field field) {

		FieldBag fieldBag = deleteAfterTestRunFieldBags.get(clazz);

		if (fieldBag == null) {
			fieldBag = new FieldBag(clazz);

			deleteAfterTestRunFieldBags.put(clazz, fieldBag);
		}

		field.setAccessible(true);

		fieldBag.addField(field);
	}

	protected Class<? extends PersistedModel> getCollectionType(
		Collection<?> collection) {

		Class<? extends PersistedModel> collectionType = null;

		for (Object object : collection) {
			Queue<Class<?>> classes = new LinkedList<Class<?>>();

			classes.add(object.getClass());

			Class<?> clazz = null;

			while ((clazz = classes.poll()) != null) {
				if (ArrayUtil.contains(
						clazz.getInterfaces(), PersistedModel.class)) {

					if (collectionType == null) {
						collectionType = (Class<? extends PersistedModel>)clazz;
					}
					else if (collectionType != clazz) {
						return null;
					}

					break;
				}

				classes.add(clazz.getSuperclass());
				classes.addAll(Arrays.asList(clazz.getInterfaces()));
			}
		}

		return collectionType;
	}

	protected String getResourceBasePath() {
		File file = new File("portal-web/docroot");

		return "file:" + file.getAbsolutePath();
	}

	protected void removeField(FieldBag fieldBag, Object instance) {
		try {
			Class<?> fieldClass = fieldBag.getFieldClass();

			PersistedModelLocalService persistedModelLocalService =
				PersistedModelLocalServiceRegistryUtil.
					getPersistedModelLocalService(fieldClass.getName());

			for (Field field : fieldBag.getFields()) {
				Object object = field.get(instance);

				if (object == null) {
					continue;
				}

				Class<?> objectClass = object.getClass();

				if (objectClass.isArray()) {
					for (PersistedModel persistedModel :
							(PersistedModel[])object) {

						if (persistedModel == null) {
							continue;
						}

						persistedModelLocalService.deletePersistedModel(
							persistedModel);
					}
				}
				else if (Collection.class.isAssignableFrom(objectClass)) {
					Collection<? extends PersistedModel> collection =
						(Collection<? extends PersistedModel>)object;

					for (PersistedModel persistedModel : collection) {
						persistedModelLocalService.deletePersistedModel(
							persistedModel);
					}
				}
				else {
					persistedModelLocalService.deletePersistedModel(
						(PersistedModel)object);
				}

				field.set(instance, null);
			}
		}
		catch (Exception e) {
			_log.error("Unable to delete", e);
		}
	}

	protected static MainServlet mainServlet;

	protected static class FieldBag {

		public FieldBag(Class<?> fieldClass) {
			_fieldClass = fieldClass;
		}

		public void addField(Field field) {
			_fields.add(field);
		}

		public Class<?> getFieldClass() {
			return _fieldClass;
		}

		public List<Field> getFields() {
			return _fields;
		}

		private Class<?> _fieldClass;
		private List<Field> _fields = new ArrayList<Field>();

	}

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
			Group.class, Company.class));

}