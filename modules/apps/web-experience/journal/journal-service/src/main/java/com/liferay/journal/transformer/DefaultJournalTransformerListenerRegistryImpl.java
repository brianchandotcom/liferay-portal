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

package com.liferay.journal.transformer;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.util.JournalTransformerListenerRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.TransformerListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = JournalTransformerListenerRegistry.class)
public class DefaultJournalTransformerListenerRegistryImpl
	implements JournalTransformerListenerRegistry {

	public List<TransformerListener> getTransformerListeners() {
		return _transformerListeners;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		target = "(javax.portlet.name=" + JournalPortletKeys.JOURNAL + ")",
		unbind = "unregisterTransformerListener"
	)
	public void registerTransformerListener(
		TransformerListener transformerListener) {

		_transformerListeners.add(transformerListener);
	}

	public void unregisterTransformerListener(
		TransformerListener transformerListener) {

		_transformerListeners.remove(transformerListener);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultJournalTransformerListenerRegistryImpl.class);

	private final List<TransformerListener> _transformerListeners =
		new CopyOnWriteArrayList<>();

}