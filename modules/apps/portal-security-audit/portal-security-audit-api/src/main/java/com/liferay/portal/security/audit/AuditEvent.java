/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@ProviderType
public interface AuditEvent {

	public String getAdditionalInfo();

	public long getAuditEventId();

	public String getClassName();

	public String getClassPK();

	public String getClientHost();

	public String getClientIP();

	public long getCompanyId();

	public Date getCreateDate();

	public String getEventType();

	public long getGroupId();

	public String getMessage();

	public long getPrimaryKey();

	public String getServerName();

	public int getServerPort();

	public String getSessionID();

	public long getUserId();

	public String getUserName();

	public String getUserUuid();

	public void setAdditionalInfo(String additionalInfo);

	public void setAuditEventId(long auditEventId);

	public void setClassName(String className);

	public void setClassPK(String classPK);

	public void setClientHost(String clientHost);

	public void setClientIP(String clientIP);

	public void setCompanyId(long companyId);

	public void setCreateDate(Date createDate);

	public void setEventType(String eventType);

	public void setMessage(String message);

	public void setPrimaryKey(long primaryKey);

	public void setServerName(String serverName);

	public void setServerPort(int serverPort);

	public void setSessionID(String sessionID);

	public void setUserId(long userId);

	public void setUserName(String userName);

	public void setUserUuid(String userUuid);

	public AuditEvent toEscapedModel();

}