package ${packagePath}.service.persistence.impl;

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.persistence.${entity.name}Persistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

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

	<#if entity.hasColumns() && entity.name != "Counter">

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

	<#if entity.badNamedColumnsList?size != 0>
		@Override
		protected Set<String> getBadColumnNames() {
			return get${entity.name}Persistence().getBadColumnNames();
		}
	</#if>

	<#if entity.hasColumns() && entity.name != "Counter">
		@BeanReference(type = ${entity.name}Persistence.class)
		protected ${entity.name}Persistence ${entity.varName}Persistence;

	</#if>

}