/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.struts;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.chain.ComposableRequestProcessor;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;
import org.apache.struts.tiles.DefinitionsFactory;
import org.apache.struts.tiles.DefinitionsFactoryConfig;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.DefinitionsUtil;
import org.apache.struts.tiles.TilesPlugin;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.tiles.TilesUtil;
import org.apache.struts.tiles.TilesUtilImpl;
import org.apache.struts.tiles.TilesUtilStrutsImpl;
import org.apache.struts.tiles.TilesUtilStrutsModulesImpl;
import org.apache.struts.util.RequestUtils;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalTilesPlugin implements PlugIn {

    /**
     * Commons Logging instance.
     */
    protected static Log log = LogFactory.getLog(TilesPlugin.class);

    /**
     * Is the factory module aware?
     */
    protected boolean moduleAware = false;

    /**
     * Tiles util implementation classname. This property can be set
     * by user in the plugin declaration.
     */
    protected String tilesUtilImplClassname = null;

    /**
     * Associated definition factory.
     */
    protected DefinitionsFactory definitionFactory = null;

    /**
     * The plugin config object provided by the ActionServlet initializing
     * this plugin.
     */
    protected PlugInConfig currentPlugInConfigObject=null;

    /**
     * Get the module aware flag.
     * @return <code>true</code>: user wants a single factory instance,
     * <code>false:</code> user wants multiple factory instances (one per module with Struts)
     */
    public boolean isModuleAware() {
        return moduleAware;
    }

    /**
     * Set the module aware flag.
     * This flag is only meaningful if the property <code>tilesUtilImplClassname</code> is not
     * set.
     * @param moduleAware <code>true</code>: user wants a single factory instance,
     * <code>false:</code> user wants multiple factory instances (one per module with Struts)
     */
    public void setModuleAware(boolean moduleAware) {
        this.moduleAware = moduleAware;
    }

    /**
     * <p>Receive notification that the specified module is being
     * started up.</p>
     *
     * @param servlet ActionServlet that is managing all the modules
     *  in this web application.
     * @param moduleConfig ModuleConfig for the module with which
     *  this plugin is associated.
     *
     * @exception ServletException if this <code>PlugIn</code> cannot
     *  be successfully initialized.
     */
    public void init(ActionServlet servlet, ModuleConfig moduleConfig)
        throws ServletException {

        // Create factory config object
        DefinitionsFactoryConfig factoryConfig =
            readFactoryConfig(servlet, moduleConfig);

        // Set the module name in the config. This name will be used to compute
        // the name under which the factory is stored.
        factoryConfig.setFactoryName(moduleConfig.getPrefix());

        // Set RequestProcessor class
        this.initRequestProcessorClass(moduleConfig);

        this.initTilesUtil();

        this.initDefinitionsFactory(servlet.getServletContext(), moduleConfig, factoryConfig);
    }

    /**
     * Set TilesUtil implementation according to properties 'tilesUtilImplClassname'
     * and 'moduleAware'.  These properties are taken into account only once. A
     * side effect is that only the values set in the first initialized plugin are
     * effectively taken into account.
     * @throws ServletException
     */
    private void initTilesUtil() throws ServletException {

        if (TilesUtil.isTilesUtilImplSet()) {
            return;
        }

        // Check if user has specified a TilesUtil implementation classname or not.
        // If no implementation is specified, check if user has specified one
        // shared single factory for all module, or one factory for each module.

        if (this.getTilesUtilImplClassname() == null) {

            if (isModuleAware()) {
                TilesUtil.setTilesUtil(new TilesUtilStrutsModulesImpl());
            } else {
                TilesUtil.setTilesUtil(new TilesUtilStrutsImpl());
            }

        } else { // A classname is specified for the tilesUtilImp, use it.
            try {
                TilesUtilStrutsImpl impl =
                    (TilesUtilStrutsImpl) RequestUtils
                        .applicationClass(getTilesUtilImplClassname())
                        .newInstance();
                TilesUtil.setTilesUtil(impl);

            } catch (ClassCastException ex) {
                throw new ServletException(
                    "Can't set TilesUtil implementation to '"
                        + getTilesUtilImplClassname()
                        + "'. TilesUtil implementation should be a subclass of '"
                        + TilesUtilStrutsImpl.class.getName()
                        + "'");

            } catch (Exception ex) {
                throw new ServletException(
                    "Can't set TilesUtil implementation.",
                    ex);
            }
        }

    }

    /**
     * Initialize the DefinitionsFactory this module will use.
     * @param servletContext
     * @param moduleConfig
     * @param factoryConfig
     * @throws ServletException
     */
    private void initDefinitionsFactory(
        ServletContext servletContext,
        ModuleConfig moduleConfig,
        DefinitionsFactoryConfig factoryConfig)
        throws ServletException {

        // Check if a factory already exist for this module
        definitionFactory =
            ((TilesUtilStrutsImpl) TilesUtil.getTilesUtil()).getDefinitionsFactory(
                servletContext,
                moduleConfig);

        if (definitionFactory != null) {
            log.info(
                "Factory already exists for module '"
                    + moduleConfig.getPrefix()
                    + "'. The factory found is from module '"
                    + definitionFactory.getConfig().getFactoryName()
                    + "'. No new creation.");

            return;
        }

        // Create configurable factory
        try {
            definitionFactory =
                TilesUtil.createDefinitionsFactory(
                    servletContext,
                    factoryConfig);

        } catch (DefinitionsFactoryException ex) {
            log.error(
                "Can't create Tiles definition factory for module '"
                    + moduleConfig.getPrefix()
                    + "'.");

            throw new ServletException(ex);
        }

        log.info(
            "Tiles definition factory loaded for module '"
                + moduleConfig.getPrefix()
                + "'.");
    }

    /**
     * End plugin.
     */
    public void destroy() {
        definitionFactory.destroy();
        definitionFactory = null;
    }

    /**
     * Create FactoryConfig and initialize it from web.xml and struts-config.xml.
     *
     * @param servlet ActionServlet that is managing all the modules
     *  in this web application.
     * @param config ModuleConfig for the module with which
     *  this plugin is associated.
     * @exception ServletException if this <code>PlugIn</code> cannot
     *  be successfully initialized.
     */
    protected DefinitionsFactoryConfig readFactoryConfig(
        ActionServlet servlet,
        ModuleConfig config)
        throws ServletException {

        // Create tiles definitions config object
        DefinitionsFactoryConfig factoryConfig = new DefinitionsFactoryConfig();
        // Get init parameters from web.xml files
        try {
            DefinitionsUtil.populateDefinitionsFactoryConfig(
                factoryConfig,
                servlet.getServletConfig());

        } catch (Exception ex) {
            if (log.isDebugEnabled()){
                log.debug("", ex);
            }
            ex.printStackTrace();
            throw new UnavailableException(
                "Can't populate DefinitionsFactoryConfig class from 'web.xml': "
                    + ex.getMessage());
        }

        // Get init parameters from struts-config.xml
        try {
            Map strutsProperties = findStrutsPlugInConfigProperties(servlet, config);
            factoryConfig.populate(strutsProperties);

        } catch (Exception ex) {
            if (log.isDebugEnabled()) {
                log.debug("", ex);
            }

            throw new UnavailableException(
                "Can't populate DefinitionsFactoryConfig class from '"
                    + config.getPrefix()
                    + "/struts-config.xml':"
                    + ex.getMessage());
        }

        return factoryConfig;
    }

    /**
     * Find original properties set in the Struts PlugInConfig object.
     * First, we need to find the index of this plugin. Then we retrieve the array of configs
     * and then the object for this plugin.
     * @param servlet ActionServlet that is managing all the modules
     *  in this web application.
     * @param config ModuleConfig for the module with which
     *  this plug in is associated.
     *
     * @exception ServletException if this <code>PlugIn</code> cannot
     *  be successfully initialized.
     */
    protected Map findStrutsPlugInConfigProperties(
        ActionServlet servlet,
        ModuleConfig config)
        throws ServletException {

        return currentPlugInConfigObject.getProperties();
    }

	protected void initRequestProcessorClass(ModuleConfig moduleConfig) {
	}

    /**
     * Set Tiles util implemention classname.
     * If this property is set, the flag <code>moduleAware</code> will not be used anymore.
     * @param tilesUtilImplClassname Classname.
     */
    public void setTilesUtilImplClassname(String tilesUtilImplClassname) {
        this.tilesUtilImplClassname = tilesUtilImplClassname;
    }

    /**
     * Get Tiles util implemention classname.
     * @return The classname or <code>null</code> if none is set.
     */
    public String getTilesUtilImplClassname() {
        return tilesUtilImplClassname;
    }

    /**
     * Method used by the ActionServlet initializing this plugin.
     * Set the plugin config object read from module config.
     * @param plugInConfigObject PlugInConfig.
     */
    public void setCurrentPlugInConfigObject(PlugInConfig plugInConfigObject) {
        this.currentPlugInConfigObject = plugInConfigObject;
    }

}