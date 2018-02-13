package ${packagePath}.uad.constants;

/**
 * @author ${author}
 * @generated
 */
public class ${portletShortName}UADConstants {

	<#list entities as entity>
		<#if entity.isUADEnabled()>
			public static final String CLASS_NAME_${textFormatter.format(textFormatter.format(entity.name, 7), 0)} = "${apiPackagePath}.model.${entity.name}";
		</#if>
	</#list>

	public static final String UAD_ENTITY_SET_NAME = "${portletShortName}";

	<#list entities as entity>
		<#if entity.isUADEnabled()>
			public static final String[] USER_ID_FIELD_NAMES_${textFormatter.format(textFormatter.format(entity.name, 7), 0)} =
				{<#list entity.UADUserIdColumnNames as uadUserIdColumnName>"${uadUserIdColumnName}"<#sep>, </#sep></#list>};
		</#if>
	</#list>

}