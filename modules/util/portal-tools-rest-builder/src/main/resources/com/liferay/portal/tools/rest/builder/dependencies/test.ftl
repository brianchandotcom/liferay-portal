package ${configYAML.apiPackagePath}.test.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.response.Response;

import java.net.MalformedURLException;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author ${configYAML.author}
 */
@Ignore
@RunAsClient
@RunWith(Arquillian.class)
public class ${schemaName}Test extends Base${schemaName}TestCase {

	<#if openAPIYAML.pathItems??>
		<#list openAPIYAML.pathItems?keys as path>
			<#assign pathItem = openAPIYAML.pathItems[path] />

			<#list javaTool.getOperations(pathItem) as operation>
				<#assign javaSignature = javaTool.getJavaSignature(configYAML, openAPIYAML, operation, path, pathItem, schemaName) />

				<#if !stringUtil.equals(javaSignature.returnType, schemaName) && !stringUtil.equals(javaSignature.returnType, "Page<${schemaName}>") && !stringUtil.endsWith(javaSignature.methodName, schemaName)>
					<#continue>
				</#if>

				<#assign content>
					<#if stringUtil.equals(javaSignature.methodName, "get${schemaName}")>
						@Test
						public void test${javaSignature.methodName?cap_first}()
							throws JsonProcessingException, MalformedURLException {

							Response response = create${schemaName}(new${schemaName}());

							${schemaName} expected${schemaName} = response.as(${schemaName}.class);

							response = get${schemaName}("/${schemaPath}/" + expected${schemaName}.getId());

							${schemaName} actual${schemaName} = response.as(${schemaName}.class);

							assertThat(actual${schemaName}, expected${schemaName});
						}
					<#else>
						@Test
						public void test${javaSignature.methodName?cap_first}()
							throws JsonProcessingException, MalformedURLException {

							Assert.assertTrue(Boolean.TRUE);
						}
					</#if>
				</#assign>

				<#list content?split("\n") as line>
					${line?replace("^\t\t\t\t\t", "", "r")}<#lt>
				</#list>
			</#list>
		</#list>
	</#if>

	@Override
	protected Response create${schemaName}(${schemaName} ${schemaVarName})
		throws JsonProcessingException, MalformedURLException {

		return create${schemaName}("", ${schemaVarName});
	}

	@Override
	protected ${schemaName} new${schemaName}() {
		return new ${schemaName}();
	}

}