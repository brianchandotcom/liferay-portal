<#assign propertiesContainer = seleniumBuilderContext.getPropContainer() />
<#list propertiesContainer as propertyContainerElement>
	<#if propertyContainerElement.attributeValue("root") == "definition" >
		${propertyContainerElement.attributeValue("testCaseName")}TestCase.all.${propertyContainerElement.attributeValue("name")}=${propertyContainerElement.attributeValue("value")}
	<#else>
		${propertyContainerElement.attributeValue("testCaseName")}TestCase.test${propertyContainerElement.attributeValue("rootName")}.${propertyContainerElement.attributeValue("name")}=${propertyContainerElement.attributeValue("value")}
	</#if>
</#list>