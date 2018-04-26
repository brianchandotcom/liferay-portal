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

package com.liferay.portal.resiliency.spi.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SPIDefinition service. Represents a row in the &quot;SPIDefinition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Michael C. Han
 * @see SPIDefinitionModel
 * @see com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionImpl
 * @see com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionImpl")
@ProviderType
public interface SPIDefinition extends SPIDefinitionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SPIDefinition, Long> SPI_DEFINITION_ID_ACCESSOR =
		new Accessor<SPIDefinition, Long>() {
			@Override
			public Long get(SPIDefinition spiDefinition) {
				return spiDefinition.getSpiDefinitionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SPIDefinition> getTypeClass() {
				return SPIDefinition.class;
			}
		};

	public void deleteBaseDir();

	public String getAgentClassName();

	public String getBaseDir();

	public String getJavaExecutable();

	public int getMaxRestartAttempts();

	public int getMaxThreads();

	public int getMinThreads();

	public String getNotificationRecipients();

	public long getPingInterval();

	public String getPortalProperties();

	public long getRegisterTimeout();

	public int getRestartAttempts();

	public long getShutdownTimeout();

	public com.liferay.portal.kernel.resiliency.spi.SPI getSPI();

	public String getStatusLabel();

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public String getTypeSettingsProperty(String key);

	public String getTypeSettingsProperty(String key, String defaultValue);

	public boolean isAlive();

	public void setMaxRestartAttempts(int maxRestartAttempts);

	public void setNotificationRecipients(String notificationRecipients);

	public void setPortalProperties(String portalProperties);

	public void setRestartAttempts(int restartAttempts);

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);
}