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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.model.BaseModel;

/**
 * @author     Raymond Augé
 * @author     Brian Wing Shun Chan
 * @deprecated As of 6.2.0, see LPS-30598.
 */
public class BatchSessionImpl implements BatchSession {

	public void delete(Session session, BaseModel<?> model)
		throws ORMException {

		if (!session.contains(model)) {
			model = (BaseModel<?>)session.get(
				model.getClass(), model.getPrimaryKeyObj());
		}

		session.delete(model);
	}

	public boolean isEnabled() {
		return _enabled.get();
	}

	public void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	public void update(Session session, BaseModel<?> model, boolean merge)
		throws ORMException {

		if (model.isNew()) {
			session.save(model);

			model.setNew(false);
		}
		else {
			session.merge(model);
		}
	}

	private static ThreadLocal<Boolean> _enabled =
		new InitialThreadLocal<Boolean>(
			BatchSessionImpl.class + "._enabled", false);

}