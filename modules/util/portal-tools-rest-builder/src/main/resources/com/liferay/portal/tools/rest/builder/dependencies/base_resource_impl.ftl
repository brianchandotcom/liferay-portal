package ${configYAML.apiPackagePath}.internal.resource.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import ${configYAML.apiPackagePath}.resource.${versionDirName}.${schemaName}Resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceImpl implements ${schemaName}Resource {

	<#list javaTool.getJavaSignatures(openAPIYAML, schemaName) as javaSignature>
		@Override
		<@compress single_line=true>
			public ${javaSignature.returnType} ${javaSignature.methodName}(
				<#list javaSignature.javaParameters as javaParameter>
					${javaParameter.parameterType} ${javaParameter.parameterName}

					<#if javaParameter_has_next>
						,
					</#if>
				</#list>
			) throws Exception {
		</@compress>

		<#if stringUtil.equals(javaSignature.returnType, "Response")>
			Response.ResponseBuilder responseBuilder = Response.ok();

			return responseBuilder.build();
		<#elseif javaSignature.returnType?contains("Page<")>
			return Page.of(Collections.emptyList());
		<#else>
			return new ${javaSignature.returnType}();
		</#if>

		}
	</#list>

	protected Response buildNoContentResponse() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}