/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.asah;

import com.liferay.osb.faro.engine.client.model.Channel;
import com.liferay.osb.faro.engine.client.model.credentials.DummyCredentials;
import com.liferay.osb.faro.engine.client.model.credentials.TokenCredentials;
import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author André Miranda
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FaroChannelDisplay {

	public FaroChannelDisplay() {
	}

	public FaroChannelDisplay(Channel channel, FaroChannel faroChannel) {
		this(faroChannel);

		_groupIdCount = 0;

		for (Map<String, Object> dataSource : channel.getDataSources()) {
			List<String> groupIds = (List)dataSource.get("groupIds");

			_groupIdCount += groupIds.size();
		}

		Map<String, Object> embeddedResources = channel.getEmbeddedResources();

		if (MapUtil.isNotEmpty(embeddedResources)) {
			for (Map<String, Object> dataSource :
					(List<Map<String, Object>>)embeddedResources.get(
						"data-sources")) {

				Map<String, Object> credentials =
					(Map<String, Object>)dataSource.get("credentials");

				if (StringUtil.equals(
						String.valueOf(credentials.get("type")),
						DummyCredentials.TYPE) ||
					StringUtil.equals(
						String.valueOf(credentials.get("type")),
						TokenCredentials.TYPE)) {

					_tokenAuth = true;

					break;
				}
			}
		}
	}

	public FaroChannelDisplay(FaroChannel faroChannel) {
		_createTime = faroChannel.getCreateTime();
		_id = faroChannel.getChannelId();
		_name = faroChannel.getName();
		_permissionType = faroChannel.getPermissionType();
	}

	private long _createTime;
	private int _groupIdCount;
	private String _id;
	private String _name;
	private int _permissionType;
	private boolean _tokenAuth;

}