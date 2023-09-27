/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.field.transformer;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.template.info.field.transformer.TemplateNodeTransformer;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = TemplateNodeTransformerRegistry.class)
public class TemplateNodeTransformerRegistryImpl
	implements TemplateNodeTransformerRegistry {

	@Override
	public TemplateNodeTransformer getTemplateNodeTransformer(
		String className) {

		TemplateNodeTransformer templateNodeTransformer =
			_templateNodeTransformerServiceTrackerMap.getService(className);

		if (templateNodeTransformer != null) {
			return templateNodeTransformer;
		}

		return _templateNodeTransformerServiceTrackerMap.getService(
			_CLASS_NAME_ANY);
	}

	@Override
	public List<TemplateNodeTransformer> getTemplateNodeTransformers() {
		return new ArrayList(
			_templateNodeTransformerServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_templateNodeTransformerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<TemplateNodeTransformer>)
					(Class)TemplateNodeTransformer.class,
				null,
				(serviceReference, emitter) -> {
					try {
						List<String> classNames = StringUtil.asList(
							serviceReference.getProperty(
								"info.field.type.class.name"));

						for (String className : classNames) {
							emitter.emit(className);
						}

						if (classNames.isEmpty()) {
							emitter.emit(_CLASS_NAME_ANY);
						}
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				},
				new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	private static final String _CLASS_NAME_ANY = "<ANY>";

	private ServiceTrackerMap<String, TemplateNodeTransformer>
		_templateNodeTransformerServiceTrackerMap;

}