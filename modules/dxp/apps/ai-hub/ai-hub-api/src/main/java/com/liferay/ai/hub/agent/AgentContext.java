/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.agent;

import java.util.Map;

/**
 * @author João Victor Alves
 */
public class AgentContext {

	public static AgentContext.Builder builder() {
		return new AgentContext.Builder();
	}

	public AgentContext(AgentContext.Builder builder) {
		_input = builder._input;
		_sseEventSinkKey = builder._sseEventSinkKey;
	}

	public Map<String, Object> getInput() {
		return _input;
	}

	public String getSseEventSinkKey() {
		return _sseEventSinkKey;
	}

	public static class Builder {

		public AgentContext build() {
			return new AgentContext(this);
		}

		public Builder input(Map<String, Object> input) {
			_input = input;

			return this;
		}

		public Builder sseEventSinkKey(String sseEventSinkKey) {
			_sseEventSinkKey = sseEventSinkKey;

			return this;
		}

		private Map<String, Object> _input;
		private String _sseEventSinkKey;

	}

	private final Map<String, Object> _input;
	private final String _sseEventSinkKey;

}