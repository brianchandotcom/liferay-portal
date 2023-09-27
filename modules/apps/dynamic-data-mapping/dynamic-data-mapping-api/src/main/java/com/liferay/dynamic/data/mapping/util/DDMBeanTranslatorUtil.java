/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static com.liferay.dynamic.data.mapping.kernel.DDMForm translate(
		DDMForm ddmForm) {

		return getDDMBeanTranslator().translate(ddmForm);
	}

	public static DDMFormField translate(
		com.liferay.dynamic.data.mapping.kernel.DDMFormField ddmFormField) {

		return getDDMBeanTranslator().translate(ddmFormField);
	}

	public static com.liferay.dynamic.data.mapping.kernel.DDMFormField
		translate(DDMFormField ddmFormField) {

		return getDDMBeanTranslator().translate(ddmFormField);
	}

	public static DDMFormValues translate(
		com.liferay.dynamic.data.mapping.kernel.DDMFormValues ddmFormValues) {

		return getDDMBeanTranslator().translate(ddmFormValues);
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