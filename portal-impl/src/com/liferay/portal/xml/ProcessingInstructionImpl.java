/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.ProcessingInstruction;
import com.liferay.portal.kernel.xml.Visitor;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ProcessingInstructionImpl
	extends NodeImpl implements ProcessingInstruction {

	public ProcessingInstructionImpl(
		org.dom4j.ProcessingInstruction processingInstruction) {

		super(processingInstruction);

		_processingInstruction = processingInstruction;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitProcessInstruction(this);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProcessingInstructionImpl)) {
			return false;
		}

		ProcessingInstructionImpl processingInstructionImpl =
			(ProcessingInstructionImpl)object;

		org.dom4j.ProcessingInstruction processingInstruction =
			processingInstructionImpl.getWrappedProcessingInstruction();

		return _processingInstruction.equals(processingInstruction);
	}

	@Override
	public String getTarget() {
		return _processingInstruction.getTarget();
	}

	@Override
	public String getText() {
		return _processingInstruction.getText();
	}

	@Override
	public String getValue(String name) {
		return _processingInstruction.getValue(name);
	}

	@Override
	public Map<String, String> getValues() {
		return _processingInstruction.getValues();
	}

	public org.dom4j.ProcessingInstruction getWrappedProcessingInstruction() {
		return _processingInstruction;
	}

	@Override
	public int hashCode() {
		return _processingInstruction.hashCode();
	}

	@Override
	public boolean removeValue(String name) {
		return _processingInstruction.removeValue(name);
	}

	@Override
	public void setTarget(String target) {
		_processingInstruction.setTarget(target);
	}

	@Override
	public void setValue(String name, String value) {
		_processingInstruction.setValue(name, value);
	}

	@Override
	public void setValues(Map<String, String> data) {
		_processingInstruction.setValues(data);
	}

	@Override
	public String toString() {
		return _processingInstruction.toString();
	}

	private final org.dom4j.ProcessingInstruction _processingInstruction;

}