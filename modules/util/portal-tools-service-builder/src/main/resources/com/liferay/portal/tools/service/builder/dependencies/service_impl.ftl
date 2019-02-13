package ${packagePath}.service.impl;

import ${apiPackagePath}.service.${entity.name}${sessionTypeName}Service;
import ${packagePath}.service.base.${entity.name}${sessionTypeName}ServiceBaseImpl;

<#if stringUtil.equals(sessionTypeName, "Local")>
/**
 * The implementation of the ${entity.humanName} local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>${apiPackagePath}.service.${entity.name}LocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.base.${entity.name}LocalServiceBaseImpl
 */
<#else>
/**
 * The implementation of the ${entity.humanName} remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>${apiPackagePath}.service.${entity.name}Service</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.base.${entity.name}ServiceBaseImpl
 */
</#if>

public class ${entity.name}${sessionTypeName}ServiceImpl extends ${entity.name}${sessionTypeName}ServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
<#if stringUtil.equals(sessionTypeName, "Local")>
	 * Never reference this class directly. Use <code>${apiPackagePath}.service.${entity.name}LocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>${apiPackagePath}.service.${entity.name}LocalServiceUtil</code>.
<#else>
	 * Never reference this class directly. Always use <code>${apiPackagePath}.service.${entity.name}ServiceUtil</code> to access the ${entity.humanName} remote service.
</#if>
	 */
}