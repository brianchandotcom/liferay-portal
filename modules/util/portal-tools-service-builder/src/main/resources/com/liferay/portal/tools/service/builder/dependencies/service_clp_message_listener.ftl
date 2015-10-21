package ${packagePath}.service.messaging;

import ${packagePath}.service.ClpSerializer;

<#list entities as entity>
	<#if entity.hasLocalService()>
		import ${packagePath}.service.${entity.name}LocalServiceUtil;
	</#if>

	<#if entity.hasRemoteService()>
		import ${packagePath}.service.${entity.name}ServiceUtil;
	</#if>
</#list>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

@ProviderType
public class ClpMessageListener extends BaseMessageListener {

	public static String getServletContextName() {
		return ClpSerializer.getServletContextName();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");
		String servletContextName = message.getString("servletContextName");

		if (command.equals("undeploy") &&
			servletContextName.equals(getServletContextName())) {

			<#list entities as entity>
				<#if entity.hasLocalService()>
					${entity.name}LocalServiceUtil.clearService();
				</#if>

				<#if entity.hasRemoteService()>
					${entity.name}ServiceUtil.clearService();
				</#if>
			</#list>
		}
	}

}