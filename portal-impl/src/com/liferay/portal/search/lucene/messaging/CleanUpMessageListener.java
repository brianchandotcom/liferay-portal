/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.util.ant.DeleteTask;

/**
 * @author Brian Wing Shun Chan
 */
public class CleanUpMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {

		// LEP-2180

		DeleteTask.deleteFiles(
			SystemProperties.get(SystemProperties.TMP_DIR),
			"LUCENE_liferay_com*.ljt", null);
	}

}