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

package com.liferay.fragment.internal.listener;

import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentEntryLinkListenerTracker.class)
public class FragmentEntryLinkListenerTrackerImpl
	implements FragmentEntryLinkListenerTracker {

	@Override
	public List<FragmentEntryLinkListener> getFragmentEntryLinkListeners() {
		return new ArrayList<>(_fragmentEntryLinkListeners);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setFragmentEntryLinkListener(
		FragmentEntryLinkListener fragmentEntryLinkListener) {

		_fragmentEntryLinkListeners.add(fragmentEntryLinkListener);
	}

	protected void unsetFragmentEntryLinkListener(
		FragmentEntryLinkListener fragmentEntryLinkListener) {

		_fragmentEntryLinkListeners.remove(fragmentEntryLinkListener);
	}

	private final List<FragmentEntryLinkListener> _fragmentEntryLinkListeners =
		new CopyOnWriteArrayList<>();

}