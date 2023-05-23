/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 460639
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.util;


/**
 * @author Raymond Augé
 */
public class BooleanPlus {

	public static boolean from(Object object, boolean defaultValue) {
		if (object instanceof Boolean) {
			return ((Boolean)object).booleanValue();
		}
		else if (object instanceof String) {
			if (Boolean.TRUE.toString().equalsIgnoreCase((String)object)) {
				return true;
			}
			else if (Boolean.FALSE.toString().equalsIgnoreCase((String)object)) {
				return false;
			}
		}

		return defaultValue;
	}

}