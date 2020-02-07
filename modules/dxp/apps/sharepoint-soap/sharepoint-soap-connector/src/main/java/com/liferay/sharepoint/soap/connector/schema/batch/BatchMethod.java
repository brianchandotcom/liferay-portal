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

package com.liferay.sharepoint.soap.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;

/**
 * @author Iván Zaera
 */
public class BatchMethod extends BaseNode {

	public BatchMethod(
		int batchMethodId, Command command, BatchField... batchFields) {

		_batchMethodId = batchMethodId;
		_command = command;
		_batchFields = batchFields;
	}

	public static enum Command {

		DELETE("Delete"), NEW("New"), UPDATE("Update");

		public String getProtocolValue() {
			return _protocolValue;
		}

		private Command(String protocolValue) {
			_protocolValue = protocolValue;
		}

		private final String _protocolValue;

	}

	@Override
	protected String getNodeName() {
		return "Method";
	}

	@Override
	protected void populate(Element element) {
		element.addAttribute("Cmd", _command.getProtocolValue());
		element.addAttribute("ID", String.valueOf(_batchMethodId));

		for (BatchField batchField : _batchFields) {
			batchField.attach(element);
		}
	}

	private final BatchField[] _batchFields;
	private final int _batchMethodId;
	private final Command _command;

}