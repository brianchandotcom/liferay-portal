/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.util.Base64;

import java.nio.ByteBuffer;

/**
 * @author Shuyang Zhou
 */
public class AddressSerializerUtil {

	public static Address deserialize(String serializedAddress) {
		byte[] bytes = Base64.decode(serializedAddress);

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(bytes));

		try {
			return deserializer.readObject();
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(
				"Unable to deserialize address " + serializedAddress,
				classNotFoundException);
		}
	}

	public static String serialize(Address address) {
		Serializer serializer = new Serializer();

		serializer.writeObject(address);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return Base64.encode(byteBuffer.array());
	}

}