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