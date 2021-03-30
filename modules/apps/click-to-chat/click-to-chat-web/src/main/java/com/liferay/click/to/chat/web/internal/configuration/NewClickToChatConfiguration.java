package com.liferay.click.to.chat.web.internal.configuration;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class NewClickToChatConfiguration implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getJspPath() {
		return "/system_settings/click_to_chat.jsp";
	}

	@Override
	public String getKey() {
		return ClickToChatConfiguration.class.getName();
	}
	
	
	@Override
	public String getCategoryKey() {
		return "click-to-chat-configuration";
	}

	@Override
	public String getName(Locale locale) {
		return "general";
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "click_to_chat/click_to_chat_form";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.click.to.chat.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}
