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

package com.liferay.layout.content.page.editor.internal.listener;

import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;

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
@Component(immediate = true, service = ContentPageEditorListenerTracker.class)
public class ContentPageEditorListenerTrackerImpl
	implements ContentPageEditorListenerTracker {

	public List<ContentPageEditorListener> getContentPageEditorListeners() {
		return new ArrayList<>(_contentPageEditorListeners);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setContentPageEditorListener(
		ContentPageEditorListener contentPageEditorListener) {

		_contentPageEditorListeners.add(contentPageEditorListener);
	}

	protected void unsetContentPageEditorListener(
		ContentPageEditorListener contentPageEditorListener) {

		_contentPageEditorListeners.remove(contentPageEditorListener);
	}

	private final List<ContentPageEditorListener> _contentPageEditorListeners =
		new CopyOnWriteArrayList<>();

}