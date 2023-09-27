/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.monitoring;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface DataSample {

	public void capture(RequestStatus requestStatus);

	public Map<String, String> getAttributes();

	public long getCompanyId();

	public String getDescription();

	public long getDuration();

	public long getGroupId();

	public String getName();

	public String getNamespace();

	public RequestStatus getRequestStatus();

	public long getTimeout();

	public String getUser();

	public void prepare();

	public void setAttributes(Map<String, String> attributes);

	public void setCompanyId(long companyId);

	public void setDescription(String description);

	public void setGroupId(long groupId);

	public void setName(String name);

	public void setNamespace(String namespace);

	public void setTimeout(long timeout);

	public void setUser(String user);

}