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

package com.liferay.apio.architect.test.util.internal.writer;

import static com.liferay.apio.architect.test.util.writer.MockWriterUtil.getRequestInfo;

import com.liferay.apio.architect.impl.documentation.Documentation;
import com.liferay.apio.architect.impl.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.impl.routes.CollectionRoutesImpl;
import com.liferay.apio.architect.impl.routes.ItemRoutesImpl;
import com.liferay.apio.architect.impl.routes.NestedCollectionRoutesImpl;
import com.liferay.apio.architect.impl.writer.DocumentationWriter;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.representor.MockRepresentorCreator;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Provides methods that test {@link DocumentationMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockDocumentationWriter {

	/**
	 * Writes a {@link Documentation} object.
	 *
	 * @param  documentationMessageMapper the {@code DocumentationMessageMapper}
	 *         to use for writing the JSON object
	 * @return the {@code String} containing the JSON Object.
	 * @review
	 */
	public static String write(
		DocumentationMessageMapper documentationMessageMapper) {

		CollectionRoutes.Builder<String, Object> collectionBuilder =
			new CollectionRoutesImpl.BuilderImpl<>(
				"name", null,
				__ -> {
				},
				__ -> null);

		ItemRoutes.Builder itemBuilder = new ItemRoutesImpl.BuilderImpl<>(
			"name", null,
			__ -> {
			},
			__ -> null);

		NestedCollectionRoutes.Builder nestedBuilder =
			new NestedCollectionRoutesImpl.BuilderImpl<>(
				"name", null, __ -> null,
				__ -> {
				},
				__ -> null);

		Representor<RootModel> rootModelRepresentor =
			MockRepresentorCreator.createRootModelRepresentor(false);

		Map<String, Representor> root = Collections.singletonMap(
			"root", rootModelRepresentor);

		CollectionRoutes<String, Object> collectionRoutes =
			collectionBuilder.build();

		ItemRoutes itemRoutes = itemBuilder.build();

		NestedCollectionRoutes nestedCollectionRoutes = nestedBuilder.build();

		Documentation documentation = new Documentation(
			() -> Optional.of(() -> "Title"),
			() -> Optional.of(() -> "Description"), () -> root,
			() -> Collections.singletonMap("root", collectionRoutes),
			() -> Collections.singletonMap("root", itemRoutes),
			() -> Collections.singletonMap("root", nestedCollectionRoutes));

		DocumentationWriter documentationWriter = DocumentationWriter.create(
			builder -> builder.documentation(
				documentation
			).documentationMessageMapper(
				documentationMessageMapper
			).requestInfo(
				getRequestInfo()
			).build());

		return documentationWriter.write();
	}

	private MockDocumentationWriter() {
		throw new UnsupportedOperationException();
	}

}