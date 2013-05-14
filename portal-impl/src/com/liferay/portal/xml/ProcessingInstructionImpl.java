/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ProcessingInstructionImpl)) {
			return false;
		}

		org.dom4j.ProcessingInstruction processingInstruction =
			((ProcessingInstructionImpl)obj).getWrappedProcessingInstruction();

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

	private org.dom4j.ProcessingInstruction _processingInstruction;

}