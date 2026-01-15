/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.agent;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

/**
 * @author João Victor Alves
 */
public class AgentContext {

	public static AgentContext.Builder builder() {
		return new AgentContext.Builder();
	}

	public AgentContext(AgentContext.Builder builder) {
		_companyId = builder._companyId;
		_dtoConverterContext = builder._dtoConverterContext;
		_groupId = builder._groupId;
		_input = builder._input;
		_serviceContext = builder._serviceContext;
		_sseEventSinkKey = builder._sseEventSinkKey;
		_userId = builder._userId;
		_userToken = builder._userToken;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public DTOConverterContext getDTOConverterContext() {
		return _dtoConverterContext;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Map<String, Object> getInput() {
		return _input;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public String getSseEventSinkKey() {
		return _sseEventSinkKey;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserToken() {
		return _userToken;
	}

	public static class Builder {

		public AgentContext build() {
			return new AgentContext(this);
		}

		public Builder companyId(long companyId) {
			_companyId = companyId;

			return this;
		}

		public Builder dtoConverterContext(
			DTOConverterContext dtoConverterContext) {

			_dtoConverterContext = dtoConverterContext;

			return this;
		}

		public Builder groupId(long groupId) {
			_groupId = groupId;

			return this;
		}

		public Builder input(Map<String, Object> input) {
			_input = input;

			return this;
		}

		public Builder serviceContext(ServiceContext serviceContext) {
			_serviceContext = serviceContext;

			return this;
		}

		public Builder sseEventSinkKey(String sseEventSinkKey) {
			_sseEventSinkKey = sseEventSinkKey;

			return this;
		}

		public Builder userId(long userId) {
			_userId = userId;

			return this;
		}

		public Builder userToken(String userToken) {
			_userToken = userToken;

			return this;
		}

		private long _companyId;
		private DTOConverterContext _dtoConverterContext;
		private long _groupId;
		private Map<String, Object> _input;
		private ServiceContext _serviceContext;
		private String _sseEventSinkKey;
		private long _userId;
		private String _userToken;

	}

	private final long _companyId;
	private final DTOConverterContext _dtoConverterContext;
	private final long _groupId;
	private final Map<String, Object> _input;
	private final ServiceContext _serviceContext;
	private final String _sseEventSinkKey;
	private final long _userId;
	private final String _userToken;

}