package com.liferay.apm.glowroot.plugins.freemarker;

import org.glowroot.agent.plugin.api.Agent;
import org.glowroot.agent.plugin.api.config.ConfigListener;
import org.glowroot.agent.plugin.api.config.ConfigService;

public class TemplatesPluginProperties {

	private static final ConfigService configService = Agent.getConfigService("liferay-templates-plugin");
	
	private static boolean captureAsOuterTransaction;
	private static boolean captureTemplateScriptInTransaction;
	
    static {
        configService.registerConfigListener(new TemplatesPluginConfigListener());
    }
    
    public static boolean captureAsOuterTransaction() {
        return captureAsOuterTransaction;
    }
    
    public static boolean captureTemplateScriptInTransaction() {
        return captureTemplateScriptInTransaction;
    }
    
    private static class TemplatesPluginConfigListener implements ConfigListener {

        @Override
        public void onChange() {
            recalculateProperties();
        }

        private static void recalculateProperties() {
            captureAsOuterTransaction =
                    configService.getBooleanProperty("captureAsOuterTransaction").value();
            
            captureTemplateScriptInTransaction =
                    configService.getBooleanProperty("captureTemplateScriptInTransaction").value();
        }
 
    }
}