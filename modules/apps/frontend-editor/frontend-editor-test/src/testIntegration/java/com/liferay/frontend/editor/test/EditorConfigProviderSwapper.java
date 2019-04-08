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

package com.liferay.frontend.editor.test;

import com.liferay.portal.editor.configuration.EditorConfigProvider;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.Closeable;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public class EditorConfigProviderSwapper implements Closeable {

	public EditorConfigProviderSwapper(
		EditorConfigurationFactory editorConfigurationFactory,
		final List<Class<?>> classes) {

		_editorConfigurationFactory = editorConfigurationFactory;

		_editorConfigProvider = ReflectionTestUtil.getFieldValue(
			editorConfigurationFactory, "_editorConfigProvider");

		ReflectionTestUtil.setFieldValue(
			editorConfigurationFactory, "_editorConfigProvider",
			new EditorConfigProvider() {

				@Override
				protected void visitEditorContributors(
					final Consumer<EditorConfigContributor> consumer,
					String portletName, String editorConfigKey,
					String editorName) {

					super.visitEditorContributors(
						editorConfigContributor -> {
							if (classes.contains(
									editorConfigContributor.getClass())) {

								consumer.accept(editorConfigContributor);
							}
						},
						portletName, editorConfigKey, editorName);
				}

			});
	}

	@Override
	public void close() {
		ReflectionTestUtil.setFieldValue(
			_editorConfigurationFactory, "_editorConfigProvider",
			_editorConfigProvider);
	}

	private final EditorConfigProvider _editorConfigProvider;
	private final EditorConfigurationFactory _editorConfigurationFactory;

}