package ${packagePath}.service;

<#list entities as entity>
	<#if entity.hasColumns()>
		import ${packagePath}.model.${entity.name}Clp;
	</#if>
</#list>

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public static void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		<#list entities as entity>
			<#if entity.hasColumns()>
				if (oldModelClassName.equals(${entity.name}Clp.class.getName())) {
					return translateInput${entity.name}(oldModel);
				}
			</#if>
		</#list>

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
				${entity.name}Clp oldCplModel = (${entity.name}Clp)oldModel;

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(_classLoader);

					try {
						Class<?> newModelClass = Class.forName("${packagePath}.model.impl.${entity.name}Impl", true, _classLoader);

						Object newModel = newModelClass.newInstance();

						<#list entity.regularColList as column>
							Method method${column_index} = newModelClass.getMethod("set${column.methodName}", new Class[] {
								<#if column.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj(column.type)}.TYPE
								<#else>
									${column.type}.class
								</#if>
							});

							<#if column.isPrimitiveType()>
								${serviceBuilder.getPrimitiveObj(column.type)}
							<#else>
								${column.type}
							</#if>

							value${column_index} =

							<#if column.isPrimitiveType()>
								new ${serviceBuilder.getPrimitiveObj(column.type)}(
							</#if>

							oldCplModel.get${column.methodName}()

							<#if column.isPrimitiveType()>
								)
							</#if>

							;

							method${column_index}.invoke(newModel, value${column_index});
						</#list>

						return newModel;
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}

				return oldModel;
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
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		<#list entities as entity>
			<#if entity.hasColumns()>
				if (oldModelClassName.equals("${packagePath}.model.impl.${entity.name}Impl")) {
					return translateOutput${entity.name}(oldModel);
				}
			</#if>
		</#list>

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

	<#list entities as entity>
		<#if entity.hasColumns()>
			public static Object translateOutput${entity.name}(BaseModel<?> oldModel) {
				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				try {
					currentThread.setContextClassLoader(_classLoader);

					try {
						${entity.name}Clp newModel = new ${entity.name}Clp();

						Class<?> oldModelClass = oldModel.getClass();

						<#list entity.regularColList as column>
							Method method${column_index} = oldModelClass.getMethod("get${column.methodName}");

							<#if column.isPrimitiveType()>
								${serviceBuilder.getPrimitiveObj(column.type)}
							<#else>
								${column.type}
							</#if>

							value${column_index} =

							(

							<#if column.isPrimitiveType()>
								${serviceBuilder.getPrimitiveObj(column.type)}
							<#else>
								${column.type}
							</#if>

							)

							method${column_index}.invoke(oldModel, (Object[])null);

							newModel.set${column.methodName}(value${column_index});

							<#if (column.name == "resourcePrimKey") && entity.isResourcedModel()>
								Method methodIsResourceMain = oldModelClass.getMethod("isResourceMain");

								Boolean resourceMain = (Boolean)methodIsResourceMain.invoke(oldModel, (Object[])null);

								newModel.setResourceMain(resourceMain);
							</#if>
						</#list>

						return newModel;
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}
				finally {
					currentThread.setContextClassLoader(contextClassLoader);
				}

				return oldModel;
			}

		</#if>
	</#list>

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);

	private static ClassLoader _classLoader;
	private static String _servletContextName;

}