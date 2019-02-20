package ${configYAML.apiPackagePath}.resource.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}
 *
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public interface ${schemaName}Resource {

	<#list freeMarkerTool.getJavaMethodSignatures(openAPIYAML, schemaName) as javaMethodSignature>
		<@compress single_line=true>
			public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(
				<#list javaMethodSignature.javaParameters as javaParameter>
					${javaParameter.parameterType} ${javaParameter.parameterName}

					<#if javaParameter_has_next>
						,
					</#if>
				</#list>
			) throws Exception;
		</@compress>

		${"\n"}<#lt>
	</#list>

	public void setContextCompany(Company contextCompany);

}