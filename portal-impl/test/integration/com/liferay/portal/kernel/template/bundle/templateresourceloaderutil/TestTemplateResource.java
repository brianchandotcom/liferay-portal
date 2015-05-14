package com.liferay.portal.kernel.template.bundle.templateresourceloaderutil;

import com.liferay.portal.kernel.template.TemplateResource;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
public class TestTemplateResource implements TemplateResource {

	public static final String TEST_TEMPLATE_RESOURCE_TEMPLATE_ID =
		"TEST_TEMPLATE_RESOURCE_TEMPLATE_ID";

	@Override
	public long getLastModified() {
		return 0;
	}

	@Override
	public Reader getReader() {
		return null;
	}

	@Override
	public String getTemplateId() {
		return TEST_TEMPLATE_RESOURCE_TEMPLATE_ID;
	}

	@Override
	public void readExternal(ObjectInput in) {
		return;
	}

	@Override
	public void writeExternal(ObjectOutput out) {
		return;
	}

}