<?xml version="1.0"?>
<!DOCTYPE resource-action-mapping PUBLIC "-//Liferay//DTD Resource Action Mapping 7.4.0//EN" "http://www.liferay.com/dtd/liferay-resource-action-mapping_7_4_0.dtd">

<resource-action-mapping>
	<model-resource>
		<model-name>[$MODEL_NAME$]</model-name>
		<portlet-ref>
			<portlet-name>[$PORTLET_NAME$]</portlet-name>
		</portlet-ref>
		<permissions>
			<supports>
				[$PERMISSIONS_SUPPORTS$]
			</supports>
			<guest-unsupported>
				[$PERMISSIONS_GUEST_UNSUPPORTED$]
			</guest-unsupported>
		</permissions>
	</model-resource>
</resource-action-mapping>