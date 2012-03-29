/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Tuple;

import java.util.List;

/**
 * @author Julio Camarero
 */
public class PrototypeException extends PortalException {

	public PrototypeException() {
		super();
	}

	public PrototypeException(String msg) {
		super(msg);
	}

	public PrototypeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PrototypeException(Throwable cause) {
		super(cause);
	}

	public PrototypeException(List<Tuple> missingPrototypes) {
		super();

		_missingPrototypes = missingPrototypes;
	}

	public List<Tuple> getMissingPrototypes() {
		return _missingPrototypes;
	}

	public void setMissingPrototypes(List<Tuple> missingPrototypes) {
		_missingPrototypes = missingPrototypes;
	}

	private List<Tuple> _missingPrototypes;

}