<#assign testCase = testCaseElement.attributeValue("test-case")>

<#assign x = testCase?last_index_of("#")>

<#if testCase?substring(x + 1) == "set-up">
	super.methodSetUp(commandName, true);
<#elseif testCase?substring(x + 1) == "tear-down">
	super.methodTearDown(commandName, true);
<#else>
	super.method${testCase?substring(x + 1)}(commandName, true);
</#if>