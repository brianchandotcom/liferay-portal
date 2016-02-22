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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Leonardo Barros
 */
public class DDMBeanTranslatorUtil {

	public static DDMForm translate(
		com.liferay.dynamic.data.mapping.kernel.DDMForm ddmForm) {

		return getDDMBeanTranslator().translate(ddmForm);
	}

	public static DDMFormField translate(
		com.liferay.dynamic.data.mapping.kernel.DDMFormField ddmFormField) {

		return getDDMBeanTranslator().translate(ddmFormField);
	}

	public static DDMFormValues translate(
		com.liferay.dynamic.data.mapping.kernel.DDMFormValues ddmFormValues) {

		return getDDMBeanTranslator().translate(ddmFormValues);
	}

	public static com.liferay.dynamic.data.mapping.kernel.DDMForm translate(
		DDMForm ddmForm) {

		return getDDMBeanTranslator().translate(ddmForm);
	}

	public static com.liferay.dynamic.data.mapping.kernel.DDMFormField
		translate(DDMFormField ddmFormField) {

		return getDDMBeanTranslator().translate(ddmFormField);
	}

	public static com.liferay.dynamic.data.mapping.kernel.DDMFormValues
		translate(DDMFormValues ddmFormValues) {

		return getDDMBeanTranslator().translate(ddmFormValues);
	}

	protected static DDMBeanTranslator getDDMBeanTranslator() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker<DDMBeanTranslator, DDMBeanTranslator>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DDMBeanTranslatorUtil.class),
			DDMBeanTranslator.class);

}