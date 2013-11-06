<#assign testCase = testCaseElement.attributeValue("test-case")>

<#assign x = testCase?last_index_of("#")>

<#if testCase?substring(0, x) == "super" && extendedTestCase??>
	<#if testCase?substring(x + 1) == "set-up" && extendedRootElement.element("set-up")??>
		super.methodSetUp(commandName);
	<#elseif testCase?substring(x + 1) == "tear-down" && extendedRootElement.element("tear-down")??>
		super.methodTearDown(commandName);
	<#else>
		super.method${testCase?substring(x + 1)}(commandName);
	</#if>
</#if>