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

package com.liferay.fragment.processor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = FragmentEntryProcessorRegistry.class)
public class FragmentEntryProcessorRegistry {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "unregisterFragmentEntryProcessor"
	)
	public void registerFragmentEntryProcessor(
		FragmentEntryProcessor fragmentEntryProcessor) {

		_fragmentEntryProcessors.add(fragmentEntryProcessor);
	}

	public void unregisterFragmentEntryProcessor(
		FragmentEntryProcessor fragmentEntryProcessor) {

		_fragmentEntryProcessors.remove(fragmentEntryProcessor);
	}

	public void validateFragmentEntryHtml(String html) throws PortalException {
		if (Validator.isNull(html)) {
			return;
		}

		for (FragmentEntryProcessor fragmentEntryProcessor :
				_fragmentEntryProcessors) {

			fragmentEntryProcessor.validateFragmentEntryHtml(html);
		}
	}

	private final List<FragmentEntryProcessor> _fragmentEntryProcessors =
		new CopyOnWriteArrayList<>();

}