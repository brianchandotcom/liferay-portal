/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;

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

	public enum Command {

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