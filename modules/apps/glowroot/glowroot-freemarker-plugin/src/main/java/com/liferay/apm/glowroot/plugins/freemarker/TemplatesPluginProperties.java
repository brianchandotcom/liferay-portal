package com.liferay.apm.glowroot.plugins.freemarker;

import org.glowroot.agent.plugin.api.Agent;
import org.glowroot.agent.plugin.api.config.ConfigListener;
import org.glowroot.agent.plugin.api.config.ConfigService;

public class TemplatesPluginProperties {

	private static final ConfigService configService = Agent.getConfigService("liferay-templates-plugin");

	private static String templateInstrumentationLevel;

    static {
        configService.registerConfigListener(new TemplatesPluginConfigListener());
    }
    
    public static String templateInstrumentationLevel() {
    	return templateInstrumentationLevel;
    }
    
    private static class TemplatesPluginConfigListener implements ConfigListener {

        @Override
        public void onChange() {
            recalculateProperties();
        }

        private static void recalculateProperties() {
            templateInstrumentationLevel =
            		configService.getStringProperty("templateInstrumentationLevel").value();
        }
 
    }
}