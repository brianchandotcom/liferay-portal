package ${packagePath}.service.persistence.impl;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.persistence.${entity.name}Persistence;

<#if dependencyInjectorDS>
	import ${packagePath}.service.persistence.impl.constants.${portletShortName}PersistenceConstants;
</#if>

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

public <#if dependencyInjectorDS>abstract </#if>class ${entity.name}FinderBaseImpl
	extends BasePersistenceImpl<${entity.name}> {

	public ${entity.name}FinderBaseImpl() {
		setModelClass(${entity.name}.class);

		<#if entity.badEntityColumns?size != 0>
			Map<String, String> dbColumnNames = new HashMap<String, String>();

			<#list entity.badEntityColumns as badEntityColumn>
				dbColumnNames.put("${badEntityColumn.name}", "${badEntityColumn.DBName}");
			</#list>

			<#if serviceBuilder.isVersionLTE_7_1_0()>
				try {
					Field field = BasePersistenceImpl.class.getDeclaredField("_dbColumnNames");

					field.setAccessible(true);

					field.set(this, dbColumnNames);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}
			<#else>
				setDBColumnNames(dbColumnNames);
			</#if>
		</#if>
	}

	<#if entity.badEntityColumns?size != 0>
		@Override
		public Set<String> getBadColumnNames() {
			<#if dependencyInjectorDS>
				return ${entity.variableName}Persistence.getBadColumnNames();
			<#else>
				return get${entity.name}Persistence().getBadColumnNames();
			</#if>
		}
	</#if>

	<#if dependencyInjectorDS>
		<#include "persistence_references.ftl">
	<#elseif entity.hasEntityColumns() && entity.hasPersistence()>
		/**
		 * Returns the ${entity.humanName} persistence.
		 *
		 * @return the ${entity.humanName} persistence
		 */
		public ${entity.name}Persistence get${entity.name}Persistence() {
			return ${entity.variableName}Persistence;
		}

		/**
		 * Sets the ${entity.humanName} persistence.
		 *
		 * @param ${entity.variableName}Persistence the ${entity.humanName} persistence
		 */
		public void set${entity.name}Persistence(${entity.name}Persistence ${entity.variableName}Persistence) {
			this.${entity.variableName}Persistence = ${entity.variableName}Persistence;
		}
	</#if>

	<#if entity.hasEntityColumns() && entity.hasPersistence()>
		<#if dependencyInjectorDS>
			@Reference
		<#else>
			@BeanReference(type = ${entity.name}Persistence.class)
		</#if>

		protected ${entity.name}Persistence ${entity.variableName}Persistence;
	</#if>

	<#if entity.badEntityColumns?size != 0>
		private static final Log _log = LogFactoryUtil.getLog(${entity.name}FinderBaseImpl.class);
	</#if>

}