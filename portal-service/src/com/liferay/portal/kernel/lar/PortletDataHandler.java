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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.model.Portlet;

import javax.portlet.PortletPreferences;

/**
 * A <code>PortletDataHandler</code> is a special class capable of exporting and
 * importing portlet specific data to a Liferay Archive file (LAR) when a site's
 * layouts are exported or imported. <code>PortletDataHandler</code>s are
 * defined by placing a <code>portlet-data-handler-class</code> element in the
 * <code>portlet</code> section of the <b>liferay-portlet.xml</b> file.
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Bruno Farache
 */
public interface PortletDataHandler {

	/**
	 * Returns the portlet's preferences with default data added.
	 *
	 * @param  portletDataContext the context of the data addition
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @return a modified version of the portlet preferences that should be
	 *         saved, or <code>null</code> if the data handler made no changes
	 *         to the portlet preferences
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletPreferences addDefaultData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	/**
	 * Deletes the data created by the portlet. It can optionally return a
	 * modified version of the portlet preferences if it contains references to
	 * data that no longer exists.
	 *
	 * @param  portletDataContext the context of the data deletion
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @return a modified version of the portlet preferences that should be
	 *         saved, or <code>null</code> if the data handler made no changes
	 *         to the portlet preferences
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	/**
	 * Returns a string of data to be placed in the &lt;portlet-data&gt; section
	 * of the LAR file. This data will be passed as the <code>data</code>
	 * parameter of <code>importData()</code>.
	 *
	 * @param  portletDataContext the context of the data export
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @return a string of data to be placed in the LAR, which can be, but not
	 *         limited to XML, or <code>null</code> if no portlet data is to be
	 *         written out
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public DataLevel getDataLevel();

	/**
	 * Returns an array of the portlet preferences that reference data. These
	 * preferences should only be updated if the referenced data is imported.
	 *
	 * @return an array of the portlet preferences that reference data
	 */
	public String[] getDataPortletPreferences();

	public StagedModelType[] getDeletionSystemEventStagedModelTypes();

	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet)
		throws Exception;

	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet, long plid)
		throws Exception;

	/**
	 * Returns an array of the controls defined for this data handler. These
	 * controls enable the developer to create fine grained controls over export
	 * behavior. The controls are rendered in the export UI.
	 *
	 * @return an array of the controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException;

	/**
	 * Returns an array of the metadata controls defined for this data handler.
	 * These controls enable the developer to create fine grained controls over
	 * export behavior of metadata such as tags, categories, ratings or
	 * comments. The controls are rendered in the export UI.
	 *
	 * @return an array of the metadata controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getExportMetadataControls()
		throws PortletDataException;

	/**
	 * Returns the number of entities defined for this data handler that are
	 * available for export according to the provided manifest summary, or
	 * <code>-1</code> if no entities are included in the manifest summary.
	 *
	 * @param  manifestSummary the manifest summary listing the number of
	 *         exportable entities
	 * @return the number of entities that are available for export according to
	 *         the manifest summary, or <code>-1</code> if no entities are
	 *         included in the manifest summary
	 */
	public long getExportModelCount(ManifestSummary manifestSummary);

	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary);

	public PortletDataHandlerControl[] getImportConfigurationControls(
		String[] configurationPortletOptions);

	/**
	 * Returns an array of the controls defined for this data handler. These
	 * controls enable the developer to create fine grained controls over import
	 * behavior. The controls are rendered in the import UI.
	 *
	 * @return an array of the controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException;

	/**
	 * Returns an array of the metadata controls defined for this data handler.
	 * These controls enable the developer to create fine grained controls over
	 * import behavior of metadata such as tags, categories, ratings or
	 * comments. The controls are rendered in the export UI.
	 *
	 * @return an array of the metadata controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getImportMetadataControls()
		throws PortletDataException;

	public String getPortletId();

	public String getServiceName();

	/**
	 * Handles any special processing of the data when the portlet is imported
	 * into a new layout. Can optionally return a modified version of
	 * <code>preferences</code> to be saved in the new portlet.
	 *
	 * @param  portletDataContext the context of the data import
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @param  data the string data that was returned by
	 *         <code>exportData()</code>
	 * @return a modified version of the portlet preferences that should be
	 *         saved, or <code>null</code> if the data handler made no changes
	 *         to the portlet preferences
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException;

	public boolean isDataAlwaysStaged();

	public boolean isDataLocalized();

	public boolean isDataPortalLevel();

	public boolean isDataPortletInstanceLevel();

	public boolean isDataSiteLevel();

	public boolean isDisplayPortlet();

	/**
	 * Returns whether the data exported by this handler should be included by
	 * default when publishing to live. This should only be <code>true</code>
	 * for data that is meant to be managed in an staging environment such as
	 * CMS content, but not for data meant to be input by users such as wiki
	 * pages or message board posts.
	 *
	 * @return <code>true</code> if the data exported by this handler should be
	 *         included by default when publishing to live; <code>false</code>
	 *         otherwise
	 */
	public boolean isPublishToLiveByDefault();

	/**
	 * Returns <code>true</code> if the data handler stops operations and rolls
	 * back their transactions on operations throwing exceptions.
	 *
	 * @return <code>true</code> if the data handler stops operations and rolls
	 *         back their transactions on operations throwing exceptions;
	 *         <code>false</code> otherwise
	 */
	public boolean isRollbackOnException();

	public boolean isSupportsDataStrategyCopyAsNew();

	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException;

	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public void setPortletId(String portletId);

}