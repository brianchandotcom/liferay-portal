package ${packagePath}.service.persistence.impl;

import ${apiPackagePath}.model.${entity.name};
import ${apiPackagePath}.service.persistence.${entity.name}Persistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

public class ${entity.name}FinderBaseImpl
	extends BasePersistenceImpl<${entity.name}> {

	public ${entity.name}FinderBaseImpl() {
		setModelClass(${entity.name}.class);

		<#if entity.badNamedColumnsList?size != 0>
			try {
				Field field = BasePersistenceImpl.class.getDeclaredField("_dbColumnNames");

				field.setAccessible(true);

				Map<String, String> dbColumnNames = new HashMap<String, String>();

				<#list entity.badNamedColumnsList as column>
					dbColumnNames.put("${column.name}", "${column.DBName}");
				</#list>

				field.set(this, dbColumnNames);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		</#if>
	}

	<#if entity.badNamedColumnsList?size != 0>
		@Override
		public Set<String> getBadColumnNames() {
			return get${entity.name}Persistence().getBadColumnNames();
		}
	</#if>

	<#if entity.hasColumns() && !stringUtil.equals(entity.name, "Counter")>
		/**
		 * Returns the ${entity.humanName} persistence.
		 *
		 * @return the ${entity.humanName} persistence
		 */
		public ${entity.name}Persistence get${entity.name}Persistence() {
			return ${entity.varName}Persistence;
		}

		/**
		 * Sets the ${entity.humanName} persistence.
		 *
		 * @param ${entity.varName}Persistence the ${entity.humanName} persistence
		 */
		public void set${entity.name}Persistence(${entity.name}Persistence ${entity.varName}Persistence) {
			this.${entity.varName}Persistence = ${entity.varName}Persistence;
		}
	</#if>

	<#if entity.hasColumns() && !stringUtil.equals(entity.name, "Counter")>
		@BeanReference(type = ${entity.name}Persistence.class)
		protected ${entity.name}Persistence ${entity.varName}Persistence;
	</#if>

	<#if entity.badNamedColumnsList?size != 0>
		private static final Log _log = LogFactoryUtil.getLog(${entity.name}FinderBaseImpl.class);
	</#if>

}