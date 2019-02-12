package ${configYAML.apiPackagePath}.resource.${versionDirName}.test;

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
public class ${schemaName}ResourceTest extends Base${schemaName}ResourceTestCase {

	<#if openAPIYAML.pathItems??>
		<#list openAPIYAML.pathItems?keys as path>
			<#assign pathItem = openAPIYAML.pathItems[path] />

			<#list javaTool.getOperations(pathItem) as operation>
				<#assign javaSignature = javaTool.getJavaSignature(configYAML, openAPIYAML, operation, path, pathItem, schemaName) />

				<#if !stringUtil.equals(javaSignature.returnType, schemaName) && !stringUtil.equals(javaSignature.returnType, "Page<${schemaName}>") && !stringUtil.endsWith(javaSignature.methodName, schemaName)>
					<#continue>
				</#if>

				<#assign content>
					@Test
					public void test${javaSignature.methodName?cap_first}() {
						Assert.assertTrue(Boolean.TRUE);
					}
				</#assign>

				<#list content?split("\n") as line>
					${line?replace("^\t\t\t\t", "", "r")}<#lt>
				</#list>
			</#list>
		</#list>
	</#if>

}