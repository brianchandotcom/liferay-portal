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

package com.liferay.commerce.product.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CPFriendlyURLEntryException extends PortalException {

	public static final int ADJACENT_SLASHES = 3;

	public static final int ENDS_WITH_SLASH = 2;

	public static final int INVALID_CHARACTERS = 4;

	public static final int TOO_DEEP = 5;

	public static final int TOO_LONG = 1;

	public CPFriendlyURLEntryException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}