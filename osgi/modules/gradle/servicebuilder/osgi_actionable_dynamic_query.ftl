package ${packagePath}.service.persistence.impl;

import ${packagePath}.model.impl.${entity.name}Impl;
import ${packagePath}.service.${entity.name}LocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author ${author}
 * @generated
 */
public abstract class ${entity.name}ActionableDynamicQuery
	extends BaseActionableDynamicQuery {

	public ${entity.name}ActionableDynamicQuery() throws SystemException {
		setBaseLocalService(${entity.name}LocalServiceUtil.getService());
		setClass(${entity.name}Impl.class);

		setClassLoader(${entity.name}Impl.class.getClassLoader());

		<#if entity.hasPrimitivePK()>
			setPrimaryKeyPropertyName("${entity.PKVarName}");
		<#else>
			<#assign pkList = entity.getPKList()>

			<#assign pkColumn = pkList?first>

			setPrimaryKeyPropertyName("primaryKey.${pkColumn.name}");

			<#list entity.getPKList() as pkColumn>
				<#if pkColumn.name == "groupId">
					setGroupIdPropertyName("primaryKey.groupId");
				</#if>
			</#list>
		</#if>
	}

}