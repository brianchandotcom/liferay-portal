<?xml version="1.0"?>
<!DOCTYPE resource-action-mapping PUBLIC "-//Liferay//DTD Resource Action Mapping 7.3.0//EN" "http://www.liferay.com/dtd/liferay-resource-action-mapping_7_3_0.dtd">

<resource-action-mapping>
	<model-resource>
		<model-name>com.liferay.translation.[$LANGUAGE_ID$]</model-name>
		<portlet-ref>
			<portlet-name>com_liferay_translation_web_internal_portlet_TranslationPortlet</portlet-name>
		</portlet-ref>
		<weight>[$WEIGHT$]</weight>
		<permissions>
			<supports>
				<action-key>TRANSLATE</action-key>
			</supports>
			<guest-defaults />
		</permissions>
	</model-resource>
</resource-action-mapping>