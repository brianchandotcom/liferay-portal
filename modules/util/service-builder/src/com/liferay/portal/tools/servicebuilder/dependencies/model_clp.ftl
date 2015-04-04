package ${packagePath}.model;

import ${packagePath}.service.ClpSerializer;

<#if entity.hasLocalService() && entity.hasColumns()>
	import ${packagePath}.service.${entity.name}LocalServiceUtil;
</#if>

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@ProviderType
public class ${entity.name}Clp extends BaseModelImpl<${entity.name}> implements ${entity.name} {

	public ${entity.name}Clp() {
	}

	@Override
	public Class<?> getModelClass() {
		return ${entity.name}.class;
	}

	@Override
	public String getModelClassName() {
		return ${entity.name}.class.getName();
	}

	@Override
	public ${entity.PKClassName} getPrimaryKey() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(

			<#list entity.PKList as column>
				_${column.name}

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			return _${entity.PKList[0].name};
		</#if>
	}

	@Override
	public void setPrimaryKey(${entity.PKClassName} primaryKey) {
		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				set${column.methodName}(primaryKey.${column.name});
			</#list>
		<#else>
			set${entity.PKList[0].methodName}(primaryKey);
		</#if>
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(

			<#list entity.PKList as column>
				_${column.name}

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			return _${entity.PKList[0].name};
		</#if>
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(

		<#if entity.hasPrimitivePK()>
			((${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")})
		<#else>
			(${entity.PKClassName})
		</#if>

		primaryKeyObj

		<#if entity.hasPrimitivePK()>
			)${serviceBuilder.getPrimitiveObjValue(serviceBuilder.getPrimitiveObj("${entity.PKClassName}"))}
		</#if>

		);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		<#list entity.regularColList as column>
			attributes.put("${column.name}", get${column.methodName}());
		</#list>

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		<#list entity.regularColList as column>
			<#if column.isPrimitiveType()>
				${serviceBuilder.getPrimitiveObj(column.type)}
			<#else>
				${column.genericizedType}
			</#if>

			${column.name} =

			<#if column.isPrimitiveType()>
				(${serviceBuilder.getPrimitiveObj(column.type)})
			<#else>
				(${column.genericizedType})
			</#if>

			attributes.get("${column.name}");

			if (${column.name} != null) {
				set${column.methodName}(${column.name});
			}
		</#list>

		_entityCacheEnabled = GetterUtil.getBoolean("entityCacheEnabled");
		_finderCacheEnabled = GetterUtil.getBoolean("finderCacheEnabled");
	}

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			@Override
			public String getClassName() {
				if (getClassNameId() <= 0) {
					return StringPool.BLANK;
				}

				return PortalUtil.getClassName(getClassNameId());
			}

			@Override
			public void setClassName(String className) {
				long classNameId = 0;

				if (Validator.isNotNull(className)) {
					classNameId = PortalUtil.getClassNameId(className);
				}

				setClassNameId(classNameId);
			}
		</#if>

		@Override
		public ${column.genericizedType} get${column.methodName}() {
			return _${column.name};
		}

		<#if column.localized>
			@Override
			public String get${column.methodName}(Locale locale) {
				String languageId = LocaleUtil.toLanguageId(locale);

				return get${column.methodName}(languageId);
			}

			@Override
			public String get${column.methodName}(Locale locale, boolean useDefault) {
				String languageId = LocaleUtil.toLanguageId(locale);

				return get${column.methodName}(languageId, useDefault);
			}

			@Override
			public String get${column.methodName}(String languageId) {
				return LocalizationUtil.getLocalization(get${column.methodName}(), languageId);
			}

			@Override
			public String get${column.methodName}(String languageId, boolean useDefault) {
				return LocalizationUtil.getLocalization(get${column.methodName}(), languageId, useDefault);
			}

			@Override
			public String get${column.methodName}CurrentLanguageId() {
				return _${column.name}CurrentLanguageId;
			}

			@Override
			public String get${column.methodName}CurrentValue() {
				Locale locale = getLocale(_${column.name}CurrentLanguageId);

				return get${column.methodName}(locale);
			}

			@Override
			public Map<Locale, String> get${column.methodName}Map() {
				return LocalizationUtil.getLocalizationMap(get${column.methodName}());
			}
		</#if>

		<#if column.type== "boolean">
			@Override
			public ${column.type} is${column.methodName}() {
				return _${column.name};
			}
		</#if>

		@Override
		public void set${column.methodName}(${column.genericizedType} ${column.name}) {
			_${column.name} = ${column.name};

			if (_${entity.varName}RemoteModel != null) {
				try {
					Class<?> clazz = _${entity.varName}RemoteModel.getClass();

					Method method = clazz.getMethod("set${column.methodName}", ${column.type}.class);

					method.invoke(_${entity.varName}RemoteModel, ${column.name});
				}
				catch (Exception e) {
					throw new UnsupportedOperationException(e);
				}
			}
		}

		<#if column.localized>
			@Override
			public void set${column.methodName}(String ${column.name}, Locale locale) {
				set${column.methodName}(${column.name}, locale, LocaleUtil.getDefault());
			}

			@Override
			public void set${column.methodName}(String ${column.name}, Locale locale, Locale defaultLocale) {
				String languageId = LocaleUtil.toLanguageId(locale);
				String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

				if (Validator.isNotNull(${column.name})) {
					set${column.methodName}(LocalizationUtil.updateLocalization(get${column.methodName}(), "${column.methodName}", ${column.name}, languageId, defaultLanguageId));
				}
				else {
					set${column.methodName}(LocalizationUtil.removeLocalization(get${column.methodName}(), "${column.methodName}", languageId));
				}
			}

			@Override
			public void set${column.methodName}CurrentLanguageId(String languageId) {
				_${column.name}CurrentLanguageId = languageId;
			}

			@Override
			public void set${column.methodName}Map(Map<Locale, String> ${column.name}Map) {
				set${column.methodName}Map(${column.name}Map, LocaleUtil.getDefault());
			}

			@Override
			public void set${column.methodName}Map(Map<Locale, String> ${column.name}Map, Locale defaultLocale) {
				if (${column.name}Map == null) {
					return;
				}

				ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				try {
					if (contextClassLoader != portalClassLoader) {
						currentThread.setContextClassLoader(portalClassLoader);
					}

					set${column.methodName}(LocalizationUtil.updateLocalization(${column.name}Map, get${column.methodName}(), "${column.methodName}", LocaleUtil.toLanguageId(defaultLocale)));
				}
				finally {
					if (contextClassLoader != portalClassLoader) {
						currentThread.setContextClassLoader(contextClassLoader);
					}
				}
			}
		</#if>

		<#if (column.name == "resourcePrimKey") && entity.isResourcedModel()>
			@Override
			public boolean isResourceMain() {
				return _resourceMain;
			}

			public void setResourceMain(boolean resourceMain) {
				_resourceMain = resourceMain;
			}
		</#if>

		<#if column.userUuid>
			@Override
			public String get${column.methodUserUuidName}() {
				try {
					User user = UserLocalServiceUtil.getUserById(get${column.methodName}());

					return user.getUuid();
				}
				catch (PortalException pe) {
					return StringPool.BLANK;
				}
			}

			@Override
			public void set${column.methodUserUuidName}(String ${column.userUuidName}) {
			}
		</#if>
	</#list>

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && !(entity.isResourcedModel() && (method.name == "isResourceMain") && (method.parameters?size == 0))>
			@Override
			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#--<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>-->

			{
				try {
					String methodName = "${method.name}";

					Class<?>[] parameterTypes = new Class<?>[] {
						<#list parameters as parameter>
							${parameter.type.getValue()}.class
							<#if parameter_has_next>
								,
							</#if>
						</#list>
					};

					Object[] parameterValues = new Object[] {
						<#list parameters as parameter>
							${parameter.name}
							<#if parameter_has_next>
								,
							</#if>
						</#list>
					};

					<#if serviceBuilder.getTypeGenericsName(method.returns) != "void">
						<#assign returnTypeObj = serviceBuilder.getPrimitiveObj(serviceBuilder.getTypeGenericsName(method.returns))>

						${returnTypeObj} returnObj = (${returnTypeObj})
					</#if>

					invokeOnRemoteModel(methodName, parameterTypes, parameterValues);

					<#if serviceBuilder.getTypeGenericsName(method.returns) != "void">
						return returnObj;
					</#if>
				}
				catch (Exception e) {
					throw new UnsupportedOperationException(e);
				}
			}
		</#if>
	</#list>

	<#if entity.isContainerModel()>
		<#assign hasParentContainerModelId = entity.hasColumn("parentContainerModelId")>

		<#list entity.columnList as column>
			<#if column.isContainerModel() && (column.name != "containerModelId")>
				public long getContainerModelId() {
					return get${column.methodName}();
				}

				public void setContainerModelId(long containerModelId) {
					_${column.name} = containerModelId;
				}
			</#if>

			<#if column.isParentContainerModel() && (column.name != "parentContainerModelId")>
				<#assign hasParentContainerModelId = true>

				public long getParentContainerModelId() {
					return get${column.methodName}();
				}

				public void setParentContainerModelId(long parentContainerModelId) {
					_${column.name} = parentContainerModelId;
				}
			</#if>
		</#list>

		public String getContainerModelName() {
			<#if entity.hasColumn("name")>
				return String.valueOf(getName());
			<#else>
				return String.valueOf(getContainerModelId());
			</#if>
		}

		<#if !hasParentContainerModelId>
			public long getParentContainerModelId() {
				return 0;
			}

			public void setParentContainerModelId(long parentContainerModelId) {
			}
		</#if>
	</#if>

	<#if entity.isStagedModel()>
		@Override
		public StagedModelType getStagedModelType() {
			<#if entity.isTypedModel()>
				return new StagedModelType(PortalUtil.getClassNameId(${entity.name}.class.getName()), getClassNameId());
			<#else>
				return new StagedModelType(PortalUtil.getClassNameId(${entity.name}.class.getName()));
			</#if>
		}
	</#if>

	<#if entity.isTrashEnabled()>
		<#if !entity.isWorkflowEnabled()>
			@Override
			public int getStatus() {
				return 0;
			}
		</#if>

		@Override
		public TrashEntry getTrashEntry() throws PortalException {
			if (!isInTrash()) {
				return null;
			}

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(), getTrashEntryClassPK());

			if (trashEntry != null) {
				return trashEntry;
			}

			TrashHandler trashHandler = getTrashHandler();

			if (!Validator.isNull(trashHandler.getContainerModelClassName())) {
				ContainerModel containerModel = null;

				try {
					containerModel = trashHandler.getParentContainerModel(this);
				}
				catch (NoSuchModelException nsme) {
	            	return null;
				}

				while (containerModel != null) {
					if (containerModel instanceof TrashedModel) {
						TrashedModel trashedModel = (TrashedModel)containerModel;

						return trashedModel.getTrashEntry();
					}

					trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashHandler.getContainerModelClassName());

					if (trashHandler == null) {
						return null;
					}

					containerModel = trashHandler.getContainerModel(containerModel.getParentContainerModelId());
				}
			}

			return null;
		}

		@Override
		public long getTrashEntryClassPK() {
			return getPrimaryKey();
		}

		@Override
		public TrashHandler getTrashHandler() {
			return TrashHandlerRegistryUtil.getTrashHandler(getModelClassName());
		}

		@Override
		public boolean isInTrash() {
			if (getStatus() == WorkflowConstants.STATUS_IN_TRASH) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isInTrashContainer() {
			TrashHandler trashHandler = getTrashHandler();

			if ((trashHandler == null) || Validator.isNull(trashHandler.getContainerModelClassName())) {
				return false;
			}

			try {
				ContainerModel containerModel = trashHandler.getParentContainerModel(this);

				if (containerModel == null) {
					return false;
				}

				if (containerModel instanceof TrashedModel) {
					return ((TrashedModel)containerModel).isInTrash();
				}
			}
			catch (Exception e) {
			}

			return false;
		}

		@Override
		public boolean isInTrashExplicitly() {
			if (!isInTrash()) {
				return false;
			}

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(), getTrashEntryClassPK());

			if (trashEntry != null) {
				return true;
			}

			return false;
		}

		@Override
		public boolean isInTrashImplicitly() {
			if (!isInTrash()) {
				return false;
			}

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(getModelClassName(), getTrashEntryClassPK());

			if (trashEntry != null) {
				return false;
			}

			return true;
		}
	</#if>

	<#if entity.isTreeModel()>
		<#assign pkColumn = entity.getPKList()?first>

		<#if entity.hasColumn("parent" + pkColumn.methodName)>
			@Override
			@SuppressWarnings("unused")
			public String buildTreePath() throws PortalException {
				try {
					return (String)invokeOnRemoteModel("buildTreePath", new Class<?>[0], new Object[0]);
				}
				catch (Exception e) {
					throw new UnsupportedOperationException(e);
				}
			}
		</#if>

		@Override
		public void updateTreePath(String treePath) {
			try {
				_treePath = treePath;

				invokeOnRemoteModel("updateTreePath", new Class<?>[] {String.class}, new Object[] {treePath});
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	</#if>

	<#if entity.isWorkflowEnabled()>
		/**
		 * @deprecated As of 6.1.0, replaced by {@link #isApproved}
		 */
		@Deprecated
		@Override
		public boolean getApproved() {
			return isApproved();
		}

		@Override
		public boolean isApproved() {
			if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isDenied() {
			if (getStatus() == WorkflowConstants.STATUS_DENIED) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isDraft() {
			if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isExpired() {
			if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isInactive() {
			if (getStatus() == WorkflowConstants.STATUS_INACTIVE) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isIncomplete() {
			if (getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isPending() {
			if (getStatus() == WorkflowConstants.STATUS_PENDING) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isScheduled() {
			if (getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
				return true;
			}
			else {
				return false;
			}
		}
	</#if>

	public BaseModel<?> get${entity.name}RemoteModel() {
		return _${entity.varName}RemoteModel;
	}

	public void set${entity.name}RemoteModel(BaseModel<?> ${entity.varName}RemoteModel) {
		_${entity.varName}RemoteModel = ${entity.varName}RemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName, Class<?>[] parameterTypes, Object[] parameterValues) throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _${entity.varName}RemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName, remoteParameterTypes);

		Object returnValue = method.invoke(_${entity.varName}RemoteModel, remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	<#if entity.hasLocalService() && entity.hasColumns()>
		@Override
		public void persist() {
			if (this.isNew()) {
				${entity.name}LocalServiceUtil.add${entity.name}(this);
			}
			else {
				${entity.name}LocalServiceUtil.update${entity.name}(this);
			}
		}
	</#if>

	<#if entity.isLocalizedModel()>
		@Override
		public String[] getAvailableLanguageIds() {
			Set<String> availableLanguageIds = new TreeSet<String>();

			<#list entity.regularColList as column>
				<#if column.localized>
					Map<Locale, String> ${column.name}Map = get${column.methodName}Map();

					for (Map.Entry<Locale, String> entry : ${column.name}Map.entrySet()) {
						Locale locale = entry.getKey();
						String value = entry.getValue();

						if (Validator.isNotNull(value)) {
							availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
						}
					}
				</#if>
			</#list>

			return availableLanguageIds.toArray(new String[availableLanguageIds.size()]);
		}

		@Override
		public String getDefaultLanguageId() {
			<#list entity.regularColList as column>
				<#if column.localized>
					String xml = get${column.methodName}();

					if (xml == null) {
						return StringPool.BLANK;
					}

					<#if entity.isGroupedModel()>
						Locale defaultLocale = LocaleUtil.getSiteDefault();
					<#else>
						Locale defaultLocale = LocaleUtil.getDefault();
					</#if>

					return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
					<#break>
				</#if>
			</#list>
		}

		@Override
		public void prepareLocalizedFieldsForImport() throws LocaleException {
			prepareLocalizedFieldsForImport(null);
		}

		@Override
		@SuppressWarnings("unused")
		public void prepareLocalizedFieldsForImport(Locale defaultImportLocale) throws LocaleException {
			<#if entity.isGroupedModel()>
				Locale defaultLocale = LocaleUtil.getSiteDefault();
			<#else>
				Locale defaultLocale = LocaleUtil.getDefault();
			</#if>

			String modelDefaultLanguageId = getDefaultLanguageId();

			<#list entity.regularColList as column>
				<#if column.localized>
					String ${column.name} = get${column.methodName}(defaultLocale);

					if (Validator.isNull(${column.name})) {
						set${column.methodName}(get${column.methodName}(modelDefaultLanguageId), defaultLocale);
					}
					else {
					  set${column.methodName}(get${column.methodName}(defaultLocale), defaultLocale, defaultLocale);
					}
				</#if>
			</#list>
		}
	</#if>

	@Override
	public ${entity.name} toEscapedModel() {
		return (${entity.name})ProxyUtil.newProxyInstance(${entity.name}.class.getClassLoader(), new Class[] {${entity.name}.class}, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		${entity.name}Clp clone = new ${entity.name}Clp();

		<#list entity.regularColList as column>
			clone.set${column.methodName}(

			<#if column.EJBName??>
				(${column.EJBName})get${column.methodName}().clone()
			<#else>
				get${column.methodName}()
			</#if>

			);
		</#list>

		return clone;
	}

	@Override
	public int compareTo(${entity.name} ${entity.varName}) {
		<#if entity.isOrdered()>
			int value = 0;

			<#list entity.order.columns as column>
				<#if column.isPrimitiveType()>
					<#if column.type == "boolean">
						<#assign ltComparator = "==">
						<#assign gtComparator = "!=">
					<#else>
						<#assign ltComparator = "<">
						<#assign gtComparator = ">">
					</#if>

					if (get${column.methodName}() ${ltComparator} ${entity.varName}.get${column.methodName}()) {
						value = -1;
					}
					else if (get${column.methodName}() ${gtComparator} ${entity.varName}.get${column.methodName}()) {
						value = 1;
					}
					else {
						value = 0;
					}
				<#else>
					<#if column.type == "Date">
						value = DateUtil.compareTo(get${column.methodName}(), ${entity.varName}.get${column.methodName}());
					<#else>
						<#if column.isCaseSensitive()>
							value = get${column.methodName}().compareTo(${entity.varName}.get${column.methodName}());
						<#else>
							value = get${column.methodName}().compareToIgnoreCase(${entity.varName}.get${column.methodName}());
						</#if>
					</#if>
				</#if>

				<#if !column.isOrderByAscending()>
					value = value * -1;
				</#if>

				if (value != 0) {
					return value;
				}
			</#list>

			return 0;
		<#else>
			${entity.PKClassName} primaryKey = ${entity.varName}.getPrimaryKey();

			<#if entity.hasPrimitivePK()>
				if (getPrimaryKey() < primaryKey) {
					return -1;
				}
				else if (getPrimaryKey() > primaryKey) {
					return 1;
				}
				else {
					return 0;
				}
			<#else>
				return getPrimaryKey().compareTo(primaryKey);
			</#if>
		</#if>
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ${entity.name}Clp)) {
			return false;
		}

		${entity.name}Clp ${entity.varName} = (${entity.name}Clp)obj;

		${entity.PKClassName} primaryKey = ${entity.varName}.getPrimaryKey();

		<#if entity.hasPrimitivePK()>
			if (getPrimaryKey() == primaryKey) {
		<#else>
			if (getPrimaryKey().equals(primaryKey)) {
		</#if>

			return true;
		}
		else{
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		<#if entity.hasPrimitivePK(false)>
			<#if entity.PKClassName == "int">
				return getPrimaryKey();
			<#else>
				return (int)getPrimaryKey();
			</#if>
		<#else>
			return getPrimaryKey().hashCode();
		</#if>
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(${entity.regularColList?size * 2 + 1});

		<#list entity.regularColList as column>
			<#if column_index == 0>
				sb.append("{${column.name}=");
				sb.append(get${column.methodName}());
			<#elseif column_has_next>
				sb.append(", ${column.name}=");
				sb.append(get${column.methodName}());
			<#else>
				sb.append(", ${column.name}=");
				sb.append(get${column.methodName}());
				sb.append("}");
			</#if>
		</#list>

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(${entity.regularColList?size * 3 + 4});

		sb.append("<model><model-name>");
		sb.append("${packagePath}.model.${entity.name}");
		sb.append("</model-name>");

		<#list entity.regularColList as column>
			sb.append("<column><column-name>${column.name}</column-name><column-value><![CDATA[");
			sb.append(get${column.methodName}());
			sb.append("]]></column-value></column>");
		</#list>

		sb.append("</model>");

		return sb.toString();
	}

	<#list entity.regularColList as column>
		private ${column.genericizedType} _${column.name};

		<#if column.localized>
			private String _${column.name}CurrentLanguageId;
		</#if>

		<#if (column.name == "resourcePrimKey") && entity.isResourcedModel()>
			private boolean _resourceMain;
		</#if>
	</#list>

	private BaseModel<?> _${entity.varName}RemoteModel;
	private Class<?> _clpSerializerClass = ${packagePath}.service.ClpSerializer.class;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;

}