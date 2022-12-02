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

package com.liferay.alloy.mvc;

import com.liferay.alloy.mvc.internal.json.web.service.AlloyControllerInvokerManager;
import com.liferay.alloy.mvc.internal.json.web.service.AlloyMockUtil;
import com.liferay.alloy.mvc.internal.util.ConstantsBeanFactoryUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactoryUtil;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseAlloyControllerImpl implements AlloyController {

	public static final String TOUCH =
		BaseAlloyControllerImpl.class.getName() + "#TOUCH#";

	public static final String VIEW_PATH =
		BaseAlloyControllerImpl.class.getName() + "#VIEW_PATH";

	public static void setAuditedModel(
			BaseModel<?> baseModel, Company company, User user)
		throws Exception {

		if (!(baseModel instanceof AuditedModel) || (company == null) ||
			(user == null)) {

			return;
		}

		AuditedModel auditedModel = (AuditedModel)baseModel;

		if (baseModel.isNew()) {
			auditedModel.setCompanyId(company.getCompanyId());
			auditedModel.setUserId(user.getUserId());
			auditedModel.setUserName(user.getFullName());
			auditedModel.setCreateDate(new Date());
			auditedModel.setModifiedDate(auditedModel.getCreateDate());
		}
		else {
			auditedModel.setModifiedDate(new Date());
		}
	}

	public static void setAuditedModel(
			BaseModel<?> baseModel, HttpServletRequest httpServletRequest)
		throws Exception {

		if (!(baseModel instanceof AuditedModel) ||
			(httpServletRequest == null)) {

			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		setAuditedModel(
			baseModel, themeDisplay.getCompany(), themeDisplay.getUser());
	}

	public static void setAuditedModel(BaseModel<?> baseModel, User user)
		throws Exception {

		if (!(baseModel instanceof AuditedModel) || (user == null)) {
			return;
		}

		long companyId = CompanyLocalServiceUtil.getCompanyIdByUserId(
			user.getUserId());

		setAuditedModel(
			baseModel, CompanyLocalServiceUtil.getCompany(companyId), user);
	}

	public static void setLocalizedProperties(
			BaseModel<?> baseModel, HttpServletRequest httpServletRequest,
			Locale locale)
		throws Exception {

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		for (String propertyName : modelAttributes.keySet()) {
			boolean localized = ModelHintsUtil.isLocalized(
				baseModel.getModelClassName(), propertyName);

			if (!localized) {
				continue;
			}

			Class<?> baseModelClass = baseModel.getModelClass();

			String setMethodName =
				"set" + TextFormatter.format(propertyName, TextFormatter.G);

			Method setMethod = baseModelClass.getMethod(
				setMethodName, new Class<?>[] {String.class, Locale.class});

			String value = ParamUtil.getString(
				httpServletRequest, propertyName);

			setMethod.invoke(baseModel, value, locale);
		}
	}

	@Override
	public void afterPropertiesSet() {
		initClass();
		initServletVariables();
		initPortletVariables();
		initThemeDisplayVariables();
		initMethods();
		initPaths();
		initIndexer();
		initMessageListeners();

		registerAlloyController();
	}

	@Override
	public void execute() throws Exception {
		Method method = getMethod(actionPath);

		if (method == null) {
			if (log.isDebugEnabled()) {
				log.debug("No method found for action " + actionPath);
			}
		}

		if (!hasPermission()) {
			renderError(
				"you-do-not-have-permission-to-access-the-requested-resource");

			method = null;
		}

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			executeAction(method);
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			executeRender(method);
		}
		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			executeResource(method);
		}

		if ((alloyNotificationEventHelper != null) &&
			!viewPath.equals(VIEW_PATH_ERROR)) {

			alloyNotificationEventHelper.addUserNotificationEvents(
				httpServletRequest, controllerPath, actionPath,
				alloyNotificationEventHelperPayloadJSONObject);
		}
	}

	public BaseModel<?> fetchBaseModel(String className, long classPK)
		throws Exception {

		AlloyServiceInvoker alloyServiceInvoker = new AlloyServiceInvoker(
			className);

		return alloyServiceInvoker.fetchModel(classPK);
	}

	@Override
	public Portlet getPortlet() {
		return portlet;
	}

	@Override
	public HttpServletRequest getRequest() {
		return httpServletRequest;
	}

	@Override
	public String getResponseContent() {
		return responseContent;
	}

	@Override
	public ThemeDisplay getThemeDisplay() {
		return themeDisplay;
	}

	@Override
	public long increment() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	@Override
	public void indexModel(BaseModel<?> baseModel) throws Exception {
		if ((indexer != null) &&
			indexerClassName.equals(baseModel.getModelClassName())) {

			indexer.reindex(baseModel);
		}
		else {
			Indexer<BaseModel<?>> baseModelIndexer =
				(Indexer<BaseModel<?>>)IndexerRegistryUtil.getIndexer(
					baseModel.getModelClass());

			if (baseModelIndexer != null) {
				baseModelIndexer.reindex(baseModel);
			}
		}
	}

	@Override
	public void persistModel(BaseModel<?> baseModel) throws Exception {
		if (!(baseModel instanceof PersistedModel)) {
			return;
		}

		PersistedModel persistedModel = (PersistedModel)baseModel;

		persistedModel.persist();
	}

	@Override
	public void setModel(BaseModel<?> baseModel, Object... properties)
		throws Exception {

		if (baseModel.isNew()) {
			baseModel.setPrimaryKeyObj(increment());
		}

		setAuditedModel(baseModel);
		setGroupedModel(baseModel);
		setAttachedModel(baseModel);

		if ((properties.length % 2) != 0) {
			throw new IllegalArgumentException(
				"Properties length is not an even number");
		}

		for (int i = 0; i < properties.length; i += 2) {
			String propertyName = String.valueOf(properties[i]);
			Object propertyValue = properties[i + 1];

			BeanPropertiesUtil.setProperty(
				baseModel, propertyName, propertyValue);
		}
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String translate(String pattern, Object... arguments) {
		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		return LanguageUtil.format(
			portletConfig.getResourceBundle(locale), pattern, arguments);
	}

	@Override
	public void updateModel(BaseModel<?> baseModel, Object... properties)
		throws Exception {

		BeanPropertiesUtil.setProperties(baseModel, httpServletRequest);

		setLocalizedProperties(baseModel);

		updateModelIgnoreRequest(baseModel, properties);
	}

	@Override
	public void updateModelIgnoreRequest(
			BaseModel<?> baseModel, Object... properties)
		throws Exception {

		setModel(baseModel, properties);

		persistModel(baseModel);
	}

	protected void addOpenerSuccessMessage() {
		Map<String, String> data = (Map<String, String>)SessionMessages.get(
			httpServletRequest,
			portlet.getPortletId() +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA);

		if ((data == null) ||
			!GetterUtil.getBoolean(data.get("addSuccessMessage"))) {

			return;
		}

		addSuccessMessage();

		data.put("addSuccessMessage", StringPool.FALSE);

		SessionMessages.add(
			httpServletRequest,
			portlet.getPortletId() +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA,
			data);
	}

	protected void addSuccessMessage() {
		String successMessage = ParamUtil.getString(
			portletRequest, "successMessage");

		SessionMessages.add(portletRequest, "requestProcessed", successMessage);
	}

	protected MessageListener buildControllerMessageListener() {
		return null;
	}

	protected String buildIncludePath(String viewPath) {
		StringBundler sb = new StringBundler(5);

		sb.append("/alloy_mvc/jsp/");
		sb.append(portlet.getFriendlyURLMapping());
		sb.append("/views/");

		if (viewPath.equals(VIEW_PATH_ERROR)) {
			sb.append("error.jsp");

			return sb.toString();
		}

		sb.append(viewPath);
		sb.append(".jsp");

		return sb.toString();
	}

	protected Indexer<BaseModel<?>> buildIndexer() {
		return null;
	}

	protected String buildResponseContent(
			Object data, String message, int status)
		throws Exception {

		String responseContent = StringPool.BLANK;

		if (isRespondingTo("json")) {
			responseContent = JSONUtil.put(
				"data",
				() -> {
					if (data instanceof Exception) {
						return getStackTrace((Exception)data);
					}

					if (data instanceof JSONArray) {
						return (JSONArray)data;
					}

					if (data instanceof JSONObject) {
						return (JSONObject)data;
					}

					if (data != null) {
						return JSONFactoryUtil.createJSONObject(
							String.valueOf(data));
					}

					return null;
				}
			).put(
				"message", message
			).put(
				"status", status
			).toString();
		}

		return responseContent;
	}

	protected MessageListener buildSchedulerMessageListener() {
		return null;
	}

	protected void executeAction(Method method) throws Exception {
		executeResource(method);

		actionRequest.setAttribute(
			CALLED_PROCESS_ACTION, Boolean.TRUE.toString());

		if (Validator.isNotNull(viewPath)) {
			actionRequest.setAttribute(VIEW_PATH, viewPath);

			PortalUtil.copyRequestParameters(actionRequest, actionResponse);
		}
		else if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	protected void executeRender(Method method) throws Exception {
		boolean calledProcessAction = GetterUtil.getBoolean(
			(String)httpServletRequest.getAttribute(CALLED_PROCESS_ACTION));

		if (!calledProcessAction) {
			executeResource(method);

			addOpenerSuccessMessage();
		}

		if (Validator.isNull(responseContent)) {
			if (Validator.isNull(viewPath)) {
				viewPath = controllerPath + StringPool.SLASH + actionPath;
			}

			String includePath = buildIncludePath(viewPath);

			include(includePath);
		}

		touch();
	}

	protected void executeResource(Method method) throws Exception {
		try {
			if (method != null) {
				TransactionInvokerUtil.invoke(
					_transactionConfig, () -> method.invoke(this));
			}
		}
		catch (Throwable throwable) {
			Exception exception = null;

			if (throwable instanceof Exception) {
				exception = (Exception)throwable;
			}
			else {
				exception = new Exception(throwable);
			}

			Object[] arguments = null;
			String message = "an-unexpected-system-error-occurred";

			Throwable rootCauseThrowable = getRootCause(exception);

			if (rootCauseThrowable instanceof AlloyException) {
				AlloyException alloyException =
					(AlloyException)rootCauseThrowable;

				if (alloyException.log) {
					log.error(rootCauseThrowable, rootCauseThrowable);
				}

				if (ArrayUtil.isNotEmpty(alloyException.arguments)) {
					arguments = alloyException.arguments;
				}

				message = rootCauseThrowable.getMessage();
			}
			else {
				log.error(exception, exception);
			}

			renderError(
				HttpServletResponse.SC_BAD_REQUEST, exception, message,
				arguments);
		}
		finally {
			if (isRespondingTo()) {
				String contentType = httpServletResponse.getContentType();

				if (isRespondingTo("json")) {
					contentType = ContentTypes.APPLICATION_JSON;
				}

				writeResponse(responseContent, contentType);
			}
		}
	}

	protected Object getConstantsBean(Class<?> clazz) {
		return ConstantsBeanFactoryUtil.getConstantsBean(clazz);
	}

	protected String getControllerDestinationName() {
		return "liferay/alloy/controller/".concat(
			getMessageListenerGroupName());
	}

	protected String getMessageListenerGroupName() {
		return StringBundler.concat(
			portlet.getRootPortletId(), StringPool.SLASH, controllerPath);
	}

	protected Method getMethod(String methodName, Class<?>... parameterTypes) {
		return methodsMap.get(getMethodKey(methodName, parameterTypes));
	}

	protected String getMethodKey(
		String methodName, Class<?>... parameterTypes) {

		StringBundler sb = new StringBundler((parameterTypes.length * 2) + 2);

		sb.append(methodName);
		sb.append(StringPool.POUND);

		for (Class<?> parameterType : parameterTypes) {
			sb.append(parameterType.getName());
			sb.append(StringPool.POUND);
		}

		return sb.toString();
	}

	protected PortletURL getPortletURL(
			String controller, String action, PortletMode portletMode,
			String lifecycle)
		throws Exception {

		return getPortletURL(
			controller, action, portletMode, lifecycle,
			portletRequest.getWindowState(), null);
	}

	protected PortletURL getPortletURL(
			String controller, String action, PortletMode portletMode,
			String lifecycle, Object... parameters)
		throws Exception {

		return getPortletURL(
			controller, action, portletMode, lifecycle,
			portletRequest.getWindowState(), parameters);
	}

	protected PortletURL getPortletURL(
			String controller, String action, PortletMode portletMode,
			String lifecycle, WindowState windowState)
		throws Exception {

		return getPortletURL(
			controller, action, portletMode, lifecycle, windowState, null);
	}

	protected PortletURL getPortletURL(
			String controller, String action, PortletMode portletMode,
			String lifecycle, WindowState windowState, Object... parameters)
		throws Exception {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				httpServletRequest, portlet, themeDisplay.getLayout(),
				lifecycle)
		).setParameter(
			"action", action
		).setParameter(
			"controller", controller
		).setPortletMode(
			portletMode
		).setWindowState(
			windowState
		).buildPortletURL();

		if (parameters == null) {
			return portletURL;
		}

		if ((parameters.length % 2) != 0) {
			throw new IllegalArgumentException(
				"Parameters length is not an even number");
		}

		for (int i = 0; i < parameters.length; i += 2) {
			String parameterName = String.valueOf(parameters[i]);
			String parameterValue = String.valueOf(parameters[i + 1]);

			portletURL.setParameter(parameterName, parameterValue);
		}

		return portletURL;
	}

	protected Throwable getRootCause(Throwable throwable) {
		if (throwable.getCause() == null) {
			return throwable;
		}

		return getRootCause(throwable.getCause());
	}

	protected String getSchedulerDestinationName() {
		return "liferay/alloy/scheduler/".concat(getMessageListenerGroupName());
	}

	protected String getSchedulerJobName() {
		return getMessageListenerGroupName();
	}

	protected StorageType getSchedulerStorageType() {
		return StorageType.MEMORY_CLUSTERED;
	}

	protected Trigger getSchedulerTrigger() {
		return TriggerFactoryUtil.createTrigger(
			getSchedulerJobName(), getMessageListenerGroupName(), 1,
			TimeUnit.DAY);
	}

	protected Map<String, Serializable> getSearchAttributes(
			Object... attributes)
		throws Exception {

		if ((attributes.length == 0) || ((attributes.length % 2) != 0)) {
			throw new Exception("Arguments length is not an even number");
		}

		Map<String, Serializable> attributesMap = new HashMap<>();

		for (int i = 0; i < attributes.length; i += 2) {
			String name = String.valueOf(attributes[i]);

			Serializable value = (Serializable)attributes[i + 1];

			attributesMap.put(name, value);
		}

		return attributesMap;
	}

	protected String getStackTrace(Exception exception) {
		StringWriter stringWriter = new StringWriter();

		PrintWriter printWriter = new PrintWriter(stringWriter);

		exception.printStackTrace(printWriter);

		return stringWriter.toString();
	}

	protected boolean hasPermission() {
		if (permissioned &&
			!AlloyPermission.contains(
				themeDisplay, portlet.getRootPortletId(), controllerPath,
				actionPath)) {

			return false;
		}

		return true;
	}

	protected void include(String path) throws Exception {
		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(path);

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(portletRequest, portletResponse);
		}
		else {
			log.error(path + " is not a valid include");
		}
	}

	protected long increment(String name) throws Exception {
		return CounterLocalServiceUtil.increment(name);
	}

	protected void initClass() {
		clazz = getClass();

		classLoader = clazz.getClassLoader();
	}

	protected void initIndexer() {
		indexer = buildIndexer();

		if (indexer == null) {
			return;
		}

		indexerClassName = indexer.getSearchClassNames()[0];

		Indexer<?> existingIndexer = IndexerRegistryUtil.getIndexer(
			indexerClassName);

		if ((existingIndexer != null) && (existingIndexer == indexer)) {
			BaseAlloyIndexer baseAlloyIndexer = (BaseAlloyIndexer)indexer;

			alloyServiceInvoker = baseAlloyIndexer.getAlloyServiceInvoker();

			return;
		}

		alloyServiceInvoker = new AlloyServiceInvoker(indexerClassName);

		BaseAlloyIndexer baseAlloyIndexer = (BaseAlloyIndexer)indexer;

		baseAlloyIndexer.setAlloyServiceInvoker(alloyServiceInvoker);
		baseAlloyIndexer.setClassName(portlet.getModelClassName());

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		indexerServiceRegistration = bundleContext.registerService(
			Indexer.class, indexer,
			MapUtil.singletonDictionary(
				"javax.portlet.name", portlet.getPortletName()));
	}

	protected void initMessageListener(
		String destinationName, MessageListener messageListener,
		boolean enableScheduler) {

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(destinationName);

		if (destination != null) {
			Set<MessageListener> messageListeners =
				destination.getMessageListeners();

			for (MessageListener curMessageListener : messageListeners) {
				if (!(curMessageListener instanceof InvokerMessageListener)) {
					continue;
				}

				InvokerMessageListener invokerMessageListener =
					(InvokerMessageListener)curMessageListener;

				curMessageListener =
					invokerMessageListener.getMessageListener();

				if (messageListener == curMessageListener) {
					return;
				}

				Class<?> messageListenerClass = messageListener.getClass();

				String messageListenerClassName =
					messageListenerClass.getName();

				Class<?> curMessageListenerClass =
					curMessageListener.getClass();

				if (!messageListenerClassName.equals(
						curMessageListenerClass.getName())) {

					continue;
				}

				try {
					if (enableScheduler) {
						SchedulerEngineHelperUtil.unschedule(
							getSchedulerJobName(),
							getMessageListenerGroupName(),
							getSchedulerStorageType());
					}

					MessageBusUtil.unregisterMessageListener(
						destinationName, curMessageListener);
				}
				catch (Exception exception) {
					log.error(exception, exception);
				}

				break;
			}
		}
		else {
			DestinationConfiguration destinationConfiguration =
				new DestinationConfiguration(
					DestinationConfiguration.DESTINATION_TYPE_SERIAL,
					destinationName);

			Destination serialDestination =
				DestinationFactoryUtil.createDestination(
					destinationConfiguration);

			destinationServiceRegistrations.put(
				serialDestination.getName(),
				_bundleContext.registerService(
					Destination.class, serialDestination,
					MapUtil.singletonDictionary(
						"destination.name", serialDestination.getName())));
		}

		try {
			MessageBusUtil.registerMessageListener(
				destinationName, messageListener);

			if (enableScheduler) {
				SchedulerEngineHelperUtil.schedule(
					getSchedulerTrigger(), getSchedulerStorageType(), null,
					destinationName, null);
			}
		}
		catch (Exception exception) {
			log.error(exception, exception);
		}
	}

	protected void initMessageListeners() {
		controllerMessageListener = buildControllerMessageListener();

		if (controllerMessageListener != null) {
			initMessageListener(
				getControllerDestinationName(), controllerMessageListener,
				false);
		}

		schedulerMessageListener = buildSchedulerMessageListener();

		if (schedulerMessageListener != null) {
			initMessageListener(
				getSchedulerDestinationName(), schedulerMessageListener, true);
		}
	}

	protected void initMethods() {
		methodsMap = new HashMap<>();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			methodsMap.put(
				getMethodKey(method.getName(), method.getParameterTypes()),
				method);
		}
	}

	protected void initPaths() {
		controllerPath = ParamUtil.getString(httpServletRequest, "controller");

		if (Validator.isNull(controllerPath)) {
			Map<String, String> defaultRouteParameters =
				alloyPortlet.getDefaultRouteParameters();

			controllerPath = defaultRouteParameters.get("controller");
		}

		if (log.isDebugEnabled()) {
			log.debug("Controller path " + controllerPath);
		}

		actionPath = ParamUtil.getString(httpServletRequest, "action");

		if (Validator.isNull(actionPath)) {
			Map<String, String> defaultRouteParameters =
				alloyPortlet.getDefaultRouteParameters();

			actionPath = defaultRouteParameters.get("action");
		}

		if (log.isDebugEnabled()) {
			log.debug("Action path " + actionPath);
		}

		viewPath = GetterUtil.getString(
			(String)httpServletRequest.getAttribute(VIEW_PATH));

		httpServletRequest.removeAttribute(VIEW_PATH);

		if (log.isDebugEnabled()) {
			log.debug("View path " + viewPath);
		}

		format = ParamUtil.getString(httpServletRequest, "format");

		if (Validator.isNull(format)) {
			Map<String, String> defaultRouteParameters =
				alloyPortlet.getDefaultRouteParameters();

			format = defaultRouteParameters.get("format");
		}

		if (log.isDebugEnabled()) {
			log.debug("Format " + format);
		}

		if (mimeResponse != null) {
			portletURL = PortletURLBuilder.createRenderURL(
				mimeResponse
			).setParameter(
				"action", actionPath
			).setParameter(
				"controller", controllerPath
			).buildPortletURL();

			if (Validator.isNotNull(format)) {
				portletURL.setParameter("format", format);
			}

			if (log.isDebugEnabled()) {
				log.debug("Portlet URL " + portletURL);
			}
		}
	}

	protected void initPortletVariables() {
		liferayPortletConfig =
			(LiferayPortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		portletContext = liferayPortletConfig.getPortletContext();

		portlet = liferayPortletConfig.getPortlet();

		alloyPortlet = (AlloyPortlet)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_PORTLET);

		portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		portletResponse = (PortletResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);

		lifecycle = GetterUtil.getString(
			(String)httpServletRequest.getAttribute(
				PortletRequest.LIFECYCLE_PHASE));

		if (log.isDebugEnabled()) {
			log.debug("Lifecycle " + lifecycle);
		}

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			actionRequest = (ActionRequest)portletRequest;
			actionResponse = (ActionResponse)portletResponse;
		}
		else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
			eventRequest = (EventRequest)portletRequest;
			eventResponse = (EventResponse)portletResponse;
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			mimeResponse = (MimeResponse)portletResponse;
			renderRequest = (RenderRequest)portletRequest;
			renderResponse = (RenderResponse)portletResponse;
		}
		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			mimeResponse = (MimeResponse)portletResponse;
			resourceRequest = (ResourceRequest)portletRequest;
			resourceResponse = (ResourceResponse)portletResponse;
		}
	}

	protected void initServletVariables() {
		servletConfig = pageContext.getServletConfig();
		servletContext = pageContext.getServletContext();
		httpServletRequest = (HttpServletRequest)pageContext.getRequest();
		httpServletResponse = (HttpServletResponse)pageContext.getResponse();
	}

	protected void initThemeDisplayVariables() {
		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		company = themeDisplay.getCompany();
		locale = themeDisplay.getLocale();
		user = themeDisplay.getUser();
	}

	protected JSONSerializable invokeAlloyController(
			String controller, String lifecycle, String action,
			Object[] parameters)
		throws Exception {

		AlloyControllerInvokerManager alloyControllerInvokerManager =
			alloyPortlet.getAlloyInvokerManager();

		return alloyControllerInvokerManager.invokeAlloyController(
			controller, lifecycle, action, parameters);
	}

	protected boolean isRespondingTo() {
		return Validator.isNotNull(format);
	}

	protected boolean isRespondingTo(String format) {
		return StringUtil.equalsIgnoreCase(this.format, format);
	}

	@SuppressWarnings("unused")
	@Transactional(
		isolation = Isolation.PORTAL, propagation = Propagation.REQUIRES_NEW,
		rollbackFor = Exception.class
	)
	protected String processDataRequest(ActionRequest actionRequest)
		throws Exception {

		return null;
	}

	protected void redirectTo(PortletURL portletURL) {
		redirectTo(portletURL.toString());
	}

	protected void redirectTo(String redirect) {
		if (!lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			throw new IllegalArgumentException(
				"redirectTo can only be called during the action phase");
		}

		if (Validator.isNotNull(viewPath)) {
			throw new IllegalArgumentException(
				"redirectTo cannot be called if render has been called");
		}

		this.redirect = redirect;
	}

	protected void registerAlloyController() {
		alloyPortlet.registerAlloyController(this);
	}

	protected void render(String actionPath) {
		if (Validator.isNotNull(redirect)) {
			throw new IllegalArgumentException(
				"render cannot be called if redirectTo has been called");
		}

		viewPath = actionPath;
	}

	protected void renderError(
			int status, Exception exception, String pattern,
			Object... arguments)
		throws Exception {

		Throwable rootCauseThrowable = getRootCause(exception);

		if (isRespondingTo()) {
			responseContent = buildResponseContent(
				rootCauseThrowable, translate(pattern, arguments), status);

			return;
		}

		portletRequest.setAttribute("arguments", arguments);
		portletRequest.setAttribute(
			"data", getStackTrace((Exception)rootCauseThrowable));
		portletRequest.setAttribute("pattern", pattern);
		portletRequest.setAttribute("status", status);

		render(VIEW_PATH_ERROR);
	}

	protected void renderError(int status, String pattern, Object... arguments)
		throws Exception {

		AlloyException alloyException = new AlloyException(
			translate("unspecified-cause"));

		renderError(status, alloyException, pattern, arguments);
	}

	protected void renderError(String pattern, Object... arguments)
		throws Exception {

		renderError(HttpServletResponse.SC_BAD_REQUEST, pattern, arguments);
	}

	protected boolean respondWith(int status, String message, Object object)
		throws Exception {

		Object data = null;

		if (isRespondingTo("json")) {
			if (object instanceof AlloySearchResult) {
				AlloySearchResult alloySearchResult = (AlloySearchResult)object;

				Hits hits = alloySearchResult.getHits();

				Document[] documents = hits.getDocs();

				data = toJSONArray(documents);
			}
			else if (object instanceof Collection) {
				Collection<?> collection = (Collection<?>)object;

				Object[] objects = collection.toArray(new BaseModel[0]);

				data = toJSONArray(objects);
			}
			else if (object instanceof JSONArray) {
				data = object;
			}
			else if (object != null) {
				data = toJSONObject(object);
			}
		}

		responseContent = buildResponseContent(data, message, status);

		return true;
	}

	@SuppressWarnings("unused")
	protected boolean respondWith(Object object) throws Exception {
		return respondWith(HttpServletResponse.SC_OK, null, object);
	}

	protected boolean respondWith(String message) throws Exception {
		return respondWith(message, null);
	}

	protected boolean respondWith(String message, Object object)
		throws Exception {

		return respondWith(HttpServletResponse.SC_OK, message, object);
	}

	protected AlloySearchResult search(
			HttpServletRequest httpServletRequest,
			PortletRequest portletRequest, Map<String, Serializable> attributes,
			String keywords, Sort[] sorts)
		throws Exception {

		return search(
			httpServletRequest, portletRequest, null, attributes, keywords,
			sorts);
	}

	protected AlloySearchResult search(
			HttpServletRequest httpServletRequest,
			PortletRequest portletRequest,
			SearchContainer<? extends BaseModel<?>> searchContainer,
			Map<String, Serializable> attributes, String keywords, Sort[] sorts)
		throws Exception {

		return search(
			indexer, alloyServiceInvoker, httpServletRequest, portletRequest,
			searchContainer, attributes, keywords, sorts);
	}

	protected AlloySearchResult search(
			Indexer<?> indexer, AlloyServiceInvoker alloyServiceInvoker,
			HttpServletRequest httpServletRequest,
			PortletRequest portletRequest, Map<String, Serializable> attributes,
			String keywords, Sort[] sorts)
		throws Exception {

		return search(
			indexer, alloyServiceInvoker, httpServletRequest, portletRequest,
			null, attributes, keywords, sorts);
	}

	protected AlloySearchResult search(
			Indexer<?> indexer, AlloyServiceInvoker alloyServiceInvoker,
			HttpServletRequest httpServletRequest,
			PortletRequest portletRequest, Map<String, Serializable> attributes,
			String keywords, Sort[] sorts, int start, int end)
		throws Exception {

		if (indexer == null) {
			throw new Exception("No indexer found for " + controllerPath);
		}

		AlloySearchResult alloySearchResult = new AlloySearchResult();

		alloySearchResult.setAlloyServiceInvoker(alloyServiceInvoker);

		SearchContext searchContext = SearchContextFactory.getInstance(
			httpServletRequest);

		boolean andOperator = false;

		boolean advancedSearch = ParamUtil.getBoolean(
			httpServletRequest, "advancedSearch");

		if (advancedSearch) {
			andOperator = ParamUtil.getBoolean(
				httpServletRequest, "andOperator");
		}

		searchContext.setAndSearch(andOperator);

		if ((attributes != null) && !attributes.isEmpty()) {
			searchContext.setAttributes(attributes);
		}

		searchContext.setEnd(end);

		String modelClassName = indexer.getSearchClassNames()[0];

		int pos = modelClassName.indexOf(".model.");

		String simpleClassName = modelClassName.substring(pos + 7);

		String serviceClassName = StringBundler.concat(
			modelClassName.substring(0, pos), ".service.", simpleClassName,
			"LocalService");

		IdentifiableOSGiService identifiableOSGiService =
			IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
				serviceClassName);

		Class<?> serviceClass = identifiableOSGiService.getClass();

		Method createModelMethod = serviceClass.getMethod(
			"create" + simpleClassName, new Class<?>[] {long.class});

		Class<?> indexerClass = createModelMethod.getReturnType();

		if (!GroupedModel.class.isAssignableFrom(indexerClass)) {
			searchContext.setGroupIds(null);
		}
		else if (searchContext.getAttribute(Field.GROUP_ID) != null) {
			long groupId = GetterUtil.getLong(
				searchContext.getAttribute(Field.GROUP_ID));

			searchContext.setGroupIds(new long[] {groupId});
		}

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (ArrayUtil.isNotEmpty(sorts)) {
			searchContext.setSorts(sorts);
		}

		searchContext.setStart(start);

		alloySearchResult.setHits(indexer.search(searchContext));

		if (portletURL != null) {
			alloySearchResult.setPortletURL(
				portletURL, searchContext.getAttributes());
		}

		alloySearchResult.afterPropertiesSet();

		return alloySearchResult;
	}

	protected AlloySearchResult search(
			Indexer<?> indexer, AlloyServiceInvoker alloyServiceInvoker,
			HttpServletRequest httpServletRequest,
			PortletRequest portletRequest,
			SearchContainer<? extends BaseModel<?>> searchContainer,
			Map<String, Serializable> attributes, String keywords, Sort[] sorts)
		throws Exception {

		if (searchContainer == null) {
			searchContainer = new SearchContainer<>(
				portletRequest, portletURL, null, null);
		}

		return search(
			indexer, alloyServiceInvoker, httpServletRequest, portletRequest,
			attributes, keywords, sorts, searchContainer.getStart(),
			searchContainer.getEnd());
	}

	protected AlloySearchResult search(
			Map<String, Serializable> attributes, String keywords, Sort sort)
		throws Exception {

		return search(attributes, keywords, new Sort[] {sort});
	}

	protected AlloySearchResult search(
			Map<String, Serializable> attributes, String keywords, Sort[] sorts)
		throws Exception {

		return search(
			httpServletRequest, portletRequest, attributes, keywords, sorts);
	}

	protected AlloySearchResult search(
			Map<String, Serializable> attributes, String keywords, Sort[] sorts,
			int start, int end)
		throws Exception {

		return search(
			indexer, alloyServiceInvoker, httpServletRequest, portletRequest,
			attributes, keywords, sorts, start, end);
	}

	protected AlloySearchResult search(String keywords) throws Exception {
		return search(keywords, (Sort[])null);
	}

	protected AlloySearchResult search(String keywords, Sort sort)
		throws Exception {

		return search(keywords, new Sort[] {sort});
	}

	protected AlloySearchResult search(String keywords, Sort[] sorts)
		throws Exception {

		return search(null, keywords, sorts);
	}

	protected void setAlloyNotificationEventHelper(
		AlloyNotificationEventHelper alloyNotificationEventHelper) {

		this.alloyNotificationEventHelper = alloyNotificationEventHelper;

		alloyNotificationEventHelperPayloadJSONObject = null;
	}

	protected void setAlloyServiceInvokerClass(Class<?> clazz) {
		alloyServiceInvoker = new AlloyServiceInvoker(clazz.getName());
	}

	protected void setAttachedModel(BaseModel<?> baseModel) throws Exception {
		if (!(baseModel instanceof AttachedModel)) {
			return;
		}

		AttachedModel attachedModel = (AttachedModel)baseModel;

		long classNameId = 0;

		String className = ParamUtil.getString(httpServletRequest, "className");

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		if (classNameId > 0) {
			attachedModel.setClassNameId(classNameId);
		}

		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		if (classPK > 0) {
			attachedModel.setClassPK(classPK);
		}
	}

	protected void setAuditedModel(BaseModel<?> baseModel) throws Exception {
		setAuditedModel(baseModel, company, user);
	}

	protected void setGroupedModel(BaseModel<?> baseModel) throws Exception {
		if (!(baseModel instanceof GroupedModel) || !baseModel.isNew()) {
			return;
		}

		GroupedModel groupedModel = (GroupedModel)baseModel;

		groupedModel.setGroupId(themeDisplay.getScopeGroupId());
	}

	protected void setLocalizedProperties(BaseModel<?> baseModel)
		throws Exception {

		setLocalizedProperties(
			baseModel, httpServletRequest, httpServletRequest.getLocale());
	}

	protected void setLocalizedProperties(BaseModel<?> baseModel, Locale locale)
		throws Exception {

		setLocalizedProperties(baseModel, httpServletRequest, locale);
	}

	protected void setOpenerSuccessMessage() {
		SessionMessages.add(
			portletRequest,
			portlet.getPortletId() + SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
			portlet.getPortletId());

		SessionMessages.add(
			httpServletRequest,
			portlet.getPortletId() +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET_DATA,
			HashMapBuilder.put(
				"addSuccessMessage", StringPool.TRUE
			).build());
	}

	protected void setPermissioned(boolean permissioned) {
		this.permissioned = permissioned;
	}

	protected JSONArray toJSONArray(Object[] objects) throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Object object : objects) {
			jsonArray.put(toJSONObject(object));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(BaseModel<?> baseModel) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			jsonObject.put(
				String.valueOf(entry.getKey()),
				() -> {
					Object value = entry.getValue();

					if (value instanceof Boolean) {
						return (Boolean)value;
					}

					if (value instanceof Date) {
						return (Date)value;
					}

					if (value instanceof Double) {
						return (Double)value;
					}

					if (value instanceof Integer) {
						return (Integer)value;
					}

					if (value instanceof Long) {
						return (Long)value;
					}

					if (value instanceof Short) {
						return (Short)value;
					}

					return String.valueOf(value);
				});
		}

		return jsonObject;
	}

	protected JSONObject toJSONObject(Document document) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Field> fields = document.getFields();

		for (Map.Entry<String, Field> entry : fields.entrySet()) {
			Field field = entry.getValue();

			jsonObject.put(field.getName(), field.getValue());
		}

		return jsonObject;
	}

	protected JSONObject toJSONObject(Object object) throws Exception {
		if (object instanceof BaseModel<?>) {
			return toJSONObject((BaseModel<?>)object);
		}
		else if (object instanceof Document) {
			return toJSONObject((Document)object);
		}
		else if (object instanceof JSONObject) {
			return (JSONObject)object;
		}

		throw new AlloyException(
			"Unable to convert " + object + " to a JSON object");
	}

	protected void touch() throws Exception {
		Boolean touch = (Boolean)portletContext.getAttribute(
			TOUCH + portlet.getRootPortletId());

		if (touch != null) {
			return;
		}

		String touchPath =
			"/alloy_mvc/jsp/" + portlet.getFriendlyURLMapping() +
				"/views/touch.jsp";

		if (log.isDebugEnabled()) {
			log.debug(
				StringBundler.concat(
					"Touch ", portlet.getRootPortletId(), " by including ",
					touchPath));
		}

		portletContext.setAttribute(
			TOUCH + portlet.getRootPortletId(), Boolean.FALSE);

		include(touchPath);
	}

	protected void writeResponse(Object content, String contentType)
		throws Exception {

		HttpServletResponse httpServletResponse = this.httpServletResponse;

		if (!(httpServletResponse instanceof
				AlloyMockUtil.MockHttpServletResponse)) {

			httpServletResponse = PortalUtil.getHttpServletResponse(
				portletResponse);
		}

		httpServletResponse.setContentType(contentType);

		ServletResponseUtil.write(httpServletResponse, content.toString());
	}

	protected static final String CALLED_PROCESS_ACTION =
		BaseAlloyControllerImpl.class.getName() + "#CALLED_PROCESS_ACTION";

	protected static final String VIEW_PATH_ERROR = "VIEW_PATH_ERROR";

	protected static final Log log = LogFactoryUtil.getLog(
		BaseAlloyControllerImpl.class);

	protected String actionPath;
	protected ActionRequest actionRequest;
	protected ActionResponse actionResponse;
	protected AlloyNotificationEventHelper alloyNotificationEventHelper;
	protected JSONObject alloyNotificationEventHelperPayloadJSONObject;
	protected AlloyPortlet alloyPortlet;
	protected AlloyServiceInvoker alloyServiceInvoker;
	protected ClassLoader classLoader;
	protected Class<?> clazz;
	protected Company company;
	protected MessageListener controllerMessageListener;
	protected String controllerPath;
	protected Map<String, ServiceRegistration<Destination>>
		destinationServiceRegistrations = new ConcurrentHashMap<>();
	protected EventRequest eventRequest;
	protected EventResponse eventResponse;
	protected String format;
	protected HttpServletRequest httpServletRequest;
	protected HttpServletResponse httpServletResponse;
	protected Indexer<BaseModel<?>> indexer;
	protected String indexerClassName;
	protected ServiceRegistration<?> indexerServiceRegistration;
	protected String lifecycle;
	protected LiferayPortletConfig liferayPortletConfig;
	protected LiferayPortletResponse liferayPortletResponse;
	protected Locale locale;
	protected Map<String, Method> methodsMap;
	protected MimeResponse mimeResponse;
	protected PageContext pageContext;
	protected boolean permissioned;
	protected Portlet portlet;
	protected PortletContext portletContext;
	protected PortletRequest portletRequest;
	protected PortletResponse portletResponse;
	protected PortletURL portletURL;
	protected String redirect;
	protected RenderRequest renderRequest;
	protected RenderResponse renderResponse;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #httpServletRequest}
	 */
	@Deprecated
	protected HttpServletRequest request;

	protected ResourceRequest resourceRequest;
	protected ResourceResponse resourceResponse;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #httpServletResponse}
	 */
	@Deprecated
	protected HttpServletResponse response;

	protected String responseContent = StringPool.BLANK;
	protected MessageListener schedulerMessageListener;
	protected ServletConfig servletConfig;
	protected ServletContext servletContext;
	protected ThemeDisplay themeDisplay;
	protected User user;
	protected String viewPath;

	private static final BundleContext _bundleContext;
	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setIsolation(Isolation.PORTAL);
		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();

		Bundle bundle = FrameworkUtil.getBundle(BaseAlloyControllerImpl.class);

		_bundleContext = bundle.getBundleContext();
	}

}