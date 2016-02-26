/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.util.Tuple;

import java.util.List;

/**
 * @author Julio Camarero
 */
public class LayoutPrototypeException extends PortalException {

	public LayoutPrototypeException() {
	}

	public LayoutPrototypeException(List<Tuple> missingLayoutPrototypes) {
		_missingLayoutPrototypes = missingLayoutPrototypes;
	}

	public LayoutPrototypeException(String msg) {
		super(msg);
	}

	public LayoutPrototypeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LayoutPrototypeException(Throwable cause) {
		super(cause);
	}

	public List<Tuple> getMissingLayoutPrototypes() {
		return _missingLayoutPrototypes;
	}

	public void setMissingLayoutPrototypes(
		List<Tuple> missingLayoutPrototypes) {

		_missingLayoutPrototypes = missingLayoutPrototypes;
	}

	private List<Tuple> _missingLayoutPrototypes;

}