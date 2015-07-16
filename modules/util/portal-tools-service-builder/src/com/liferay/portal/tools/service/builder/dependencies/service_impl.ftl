package ${packagePath}.service.impl;

import ${packagePath}.service.${entity.name}${sessionTypeName}Service;
import ${packagePath}.service.base.${entity.name}${sessionTypeName}ServiceBaseImpl;

import aQute.bnd.annotation.ProviderType;

<#if sessionTypeName == "Local">
/**
 * The implementation of the ${entity.humanName} local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link ${packagePath}.service.${entity.name}LocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.base.${entity.name}LocalServiceBaseImpl
 * @see ${packagePath}.service.${entity.name}LocalServiceUtil
 */
<#else>
/**
 * The implementation of the ${entity.humanName} remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link ${packagePath}.service.${entity.name}Service} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.base.${entity.name}ServiceBaseImpl
 * @see ${packagePath}.service.${entity.name}ServiceUtil
 */
</#if>

@ProviderType
public class ${entity.name}${sessionTypeName}ServiceImpl extends ${entity.name}${sessionTypeName}ServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
<#if sessionTypeName == "Local">
	 * Never reference this class directly. Always use {@link ${packagePath}.service.${entity.name}LocalServiceUtil} to access the ${entity.humanName} local service.
<#else>
	 * Never reference this class directly. Always use {@link ${packagePath}.service.${entity.name}ServiceUtil} to access the ${entity.humanName} remote service.
</#if>
	 */
}