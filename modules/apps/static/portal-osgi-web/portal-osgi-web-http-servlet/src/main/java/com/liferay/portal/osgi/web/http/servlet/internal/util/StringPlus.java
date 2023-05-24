/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class StringPlus {

	@SuppressWarnings("unchecked")
	public static String[] from(Object object) {
		if (object instanceof String) {
			return new String[] {(String)object};
		}
		else if (object instanceof String[]) {
			return (String[])object;
		}
		else if (object instanceof Collection) {
			Collection<?> collection = (Collection<?>)object;

			Iterator<?> iterator = collection.iterator();

			if (!collection.isEmpty() && (iterator.next() instanceof String)) {
				List<String> list = new ArrayList<>((Collection<String>)object);

				return list.toArray(new String[0]);
			}
		}

		return new String[0];
	}

}