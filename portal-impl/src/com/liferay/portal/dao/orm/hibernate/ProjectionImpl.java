/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Projection;

/**
 * @author Brian Wing Shun Chan
 */
public class ProjectionImpl implements Projection {

	public ProjectionImpl(org.hibernate.criterion.Projection projection) {
		_projection = projection;
	}

	public org.hibernate.criterion.Projection getWrappedProjection() {
		return _projection;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{_projection=", _projection, "}");
	}

	private final org.hibernate.criterion.Projection _projection;

}