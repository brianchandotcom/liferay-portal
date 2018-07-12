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

import static com.liferay.apio.architect.operation.HTTPMethod.POST;
import static com.liferay.apio.architect.test.util.form.MockFormCreator.createForm;
import static com.liferay.apio.architect.test.util.writer.MockWriterUtil.getRequestInfo;

import com.liferay.apio.architect.impl.message.json.PageMessageMapper;
import com.liferay.apio.architect.impl.operation.OperationImpl;
import com.liferay.apio.architect.impl.pagination.PageImpl;
import com.liferay.apio.architect.impl.pagination.PaginationImpl;
import com.liferay.apio.architect.impl.writer.PageWriter;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.test.util.model.RootModel;
import com.liferay.apio.architect.test.util.writer.MockWriterUtil;
import com.liferay.apio.architect.uri.Path;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Provides methods that test {@code PageMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockPageWriter {

	/**
	 * Writes a Collection of {@link RootModel}, with the hierarchy of embedded
	 * models and multiple fields.
	 *
	 * @param  pageMessageMapper the {@link PageMessageMapper} to use for
	 *         writing the JSON object
	 * @return the {@code String} containing the JSON Object.
	 * @review
	 */
	public static String write(PageMessageMapper<RootModel> pageMessageMapper) {
		Collection<RootModel> items = Arrays.asList(
			() -> "1", () -> "2", () -> "3");

		PageItems<RootModel> pageItems = new PageItems<>(items, 9);

		Pagination pagination = new PaginationImpl(3, 2);

		Path path = new Path("name", "id");

		List<Operation> operations = Collections.singletonList(
			new OperationImpl(createForm("c", "p"), POST, "create-operation"));

		Page<RootModel> page = new PageImpl<>(
			"root", pageItems, pagination, path, operations);

		PageWriter<RootModel> pageWriter = PageWriter.create(
			builder -> builder.page(
				page
			).pageMessageMapper(
				pageMessageMapper
			).pathFunction(
				MockWriterUtil::identifierToPath
			).resourceNameFunction(
				__ -> Optional.of("models")
			).representorFunction(
				MockWriterUtil::getRepresentorOptional
			).requestInfo(
				getRequestInfo()
			).singleModelFunction(
				MockWriterUtil::getSingleModel
			).build());

		return pageWriter.write();
	}

	private MockPageWriter() {
		throw new UnsupportedOperationException();
	}

}