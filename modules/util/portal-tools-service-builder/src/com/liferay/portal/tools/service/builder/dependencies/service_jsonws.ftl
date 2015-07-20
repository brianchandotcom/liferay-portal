package ${packagePath}.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import ${packagePath}.service.${entity.name}Service;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for ${entity.name}. This utility wraps
 * {@link ${packagePath}.service.impl.${entity.name}ServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author ${author}
 * @see ${entity.name}Service
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
@Component(
	immediate = true,
	property = {
		"json.web.service.context.name=${portletShortName?lower_case}",
		"json.web.service.context.path=${entity.name}"
	},
	service = ${entity.name}JsonService.class
)
@JSONWebService
@ProviderType
public class ${entity.name}JsonService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}ServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#list method.annotations as annotation>
				<#if (annotation.type == "com.liferay.portal.kernel.jsonwebservice.JSONWebService")>
					${serviceBuilder.annotationToString(annotation)}
				</#if>
			</#list>
			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>
			public

			<#if method.name = "dynamicQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "java.util.List<T>")>
				<T>
			</#if>

			${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

			<#list method.parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
					return
				</#if>

				_service.${method.name}(

				<#list method.parameters as parameter>
					${parameter.name}

					<#if parameter_has_next>
						,
					</#if>
				</#list>

				);
			}
		</#if>
	</#list>

	@Reference
	protected void setService(${entity.name}Service service) {
		_service = service;
	}

	private ${entity.name}Service _service;

}