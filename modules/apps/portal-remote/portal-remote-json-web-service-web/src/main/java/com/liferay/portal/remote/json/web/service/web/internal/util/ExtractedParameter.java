package com.liferay.portal.remote.json.web.service.web.internal.util;

public class ExtractedParameter {
	private final String _name;
	private final String _signature;

	public ExtractedParameter(String name, String signature) {
		_name = name;
		_signature = signature;
	}

	public String getName() {
		return _name;
	}

	public String getSignature() {
		return _signature;
	}
}
