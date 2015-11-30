package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.${entity.name}LocalServiceUtil;

import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author ${author}
 * @deprecated As of 7.0.0, replaced by {@link ${entity.name}LocalServiceUtil#getActionableDynamicQuery()}
 * @generated
 */
@Deprecated
public abstract class ${entity.name}ActionableDynamicQuery extends DefaultActionableDynamicQuery {

	public ${entity.name}ActionableDynamicQuery() {
		setBaseLocalService(${entity.name}LocalServiceUtil.getService());

		<#if pluginName != "">
			setClassLoader(${packagePath}.service.ClpSerializer.class.getClassLoader());
		<#else>
			setClassLoader(PortalClassLoaderUtil.getClassLoader());
		</#if>

		setModelClass(${entity.name}.class);

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