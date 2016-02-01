package ${apiPackagePath}.service;

<#assign entitiesHaveColumns = false>

<#list entities as entity>
	<#if entity.hasColumns()>
		<#assign entitiesHaveColumns = true>

		import ${apiPackagePath}.model.${entity.name}Clp;
	</#if>
</#list>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ProviderType
public class ClpSerializer {

	public static String getServletContextName() {
		if (Validator.isNotNull(_servletContextName)) {
			return _servletContextName;
		}

		synchronized (ClpSerializer.class) {
			if (Validator.isNotNull(_servletContextName)) {
				return _servletContextName;
			}

			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Class<?> portletPropsClass = classLoader.loadClass("com.liferay.util.portlet.PortletProps");

				Method getMethod = portletPropsClass.getMethod("get", new Class<?>[] {String.class});

				String portletPropsServletContextName = (String)getMethod.invoke(null, "${pluginName}-deployment-context");

				if (Validator.isNotNull(portletPropsServletContextName)) {
					_servletContextName = portletPropsServletContextName;
				}
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to locate deployment context from portlet properties");
				}
			}

			if (Validator.isNull(_servletContextName)) {
				try {
					String propsUtilServletContextName = PropsUtil.get("${pluginName}-deployment-context");

					if (Validator.isNotNull(propsUtilServletContextName)) {
						_servletContextName = propsUtilServletContextName;
					}
				}
				catch (Throwable t) {
					if (_log.isInfoEnabled()) {
						_log.info("Unable to locate deployment context from portal properties");
					}
				}
			}

			if (Validator.isNull(_servletContextName)) {
				_servletContextName = "${pluginName}";
			}

			return _servletContextName;
		}
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		<#if entitiesHaveColumns>
			Class<?> oldModelClass = oldModel.getClass();

			String oldModelClassName = oldModelClass.getName();

			<#list entities as entity>
				<#if entity.hasColumns()>
					if (oldModelClassName.equals(${entity.name}Clp.class.getName())) {
						return translateInput${entity.name}(oldModel);
					}
				</#if>
			</#list>
		</#if>

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	<#list entities as entity>
		<#if entity.hasColumns()>
			public static Object translateInput${entity.name}(BaseModel<?> oldModel) {
				${entity.name}Clp oldClpModel = (${entity.name}Clp)oldModel;

				BaseModel<?> newModel = oldClpModel.get${entity.name}RemoteModel();

				newModel.setModelAttributes(oldClpModel.getModelAttributes());

				return newModel;
			}
		</#if>
	</#list>

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		<#if entitiesHaveColumns>
			Class<?> oldModelClass = oldModel.getClass();

			String oldModelClassName = oldModelClass.getName();

			<#list entities as entity>
				<#if entity.hasColumns()>
					if (oldModelClassName.equals("${packagePath}.model.impl.${entity.name}Impl")) {
						return translateOutput${entity.name}(oldModel);
					}
					else if (oldModelClassName.endsWith("Clp")) {
						try {
							ClassLoader classLoader = ClpSerializer.class.getClassLoader();

							Method getClpSerializerClassMethod = oldModelClass.getMethod("getClpSerializerClass");

							Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

							Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

							Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput", BaseModel.class);

							Class<?> oldModelModelClass = oldModel.getModelClass();

							Method getRemoteModelMethod = oldModelClass.getMethod("get" + oldModelModelClass.getSimpleName() + "RemoteModel");

							Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

							BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null, oldRemoteModel);

							return newModel;
						}
						catch (Throwable t) {
							if (_log.isInfoEnabled()) {
								_log.info("Unable to translate " + oldModelClassName, t);
							}
						}
					}
				</#if>
			</#list>
		</#if>

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Throwable translateThrowable(Throwable throwable) {
		if (_useReflectionToTranslateThrowable) {
			try {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsyncByteArrayOutputStream);

				objectOutputStream.writeObject(throwable);

				objectOutputStream.flush();
				objectOutputStream.close();

				UnsyncByteArrayInputStream unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(unsyncByteArrayOutputStream.unsafeGetByteArray(), 0, unsyncByteArrayOutputStream.size());

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				ObjectInputStream objectInputStream = new ClassLoaderObjectInputStream(unsyncByteArrayInputStream, contextClassLoader);

				throwable = (Throwable)objectInputStream.readObject();

				objectInputStream.close();

				return throwable;
			}
			catch (ClassNotFoundException cnfe) {
				if (_log.isInfoEnabled()) {
					_log.info("Do not use reflection to translate throwable");
				}

				_useReflectionToTranslateThrowable = false;
			}
			catch (SecurityException se) {
				if (_log.isInfoEnabled()) {
					_log.info("Do not use reflection to translate throwable");
				}

				_useReflectionToTranslateThrowable = false;
			}
			catch (Throwable throwable2) {
				_log.error(throwable2, throwable2);

				return throwable2;
			}
		}

		Class<?> clazz = throwable.getClass();

		String className = clazz.getName();

		<#list exceptions as exception>
			if (className.equals("${apiPackagePath}.exception.${exception}Exception")) {
				return new ${apiPackagePath}.exception.${exception}Exception(throwable.getMessage(), throwable.getCause());
			}
		</#list>

		return throwable;
	}

	<#list entities as entity>
		<#if entity.hasColumns()>
			public static Object translateOutput${entity.name}(BaseModel<?> oldModel) {
				${entity.name}Clp newModel = new ${entity.name}Clp();

				newModel.setModelAttributes(oldModel.getModelAttributes());

				newModel.set${entity.name}RemoteModel(oldModel);

				return newModel;
			}

		</#if>
	</#list>

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);

	private static String _servletContextName;
	private static boolean _useReflectionToTranslateThrowable = true;

}