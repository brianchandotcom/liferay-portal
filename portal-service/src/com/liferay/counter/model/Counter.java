/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.counter.model;

import com.liferay.portal.model.SaveableModel;

/**
 * The extended model interface for the Counter service. Represents a row in the &quot;Counter&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CounterModel
 * @see com.liferay.counter.model.impl.CounterImpl
 * @see com.liferay.counter.model.impl.CounterModelImpl
 * @generated
 */
public interface Counter extends CounterModel, SaveableModel {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.counter.model.impl.CounterImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
}