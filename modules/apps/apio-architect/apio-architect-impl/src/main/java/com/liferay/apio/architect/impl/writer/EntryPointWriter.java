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

package com.liferay.apio.architect.impl.writer;

import static com.liferay.apio.architect.impl.url.URLCreator.createCollectionURL;

import com.liferay.apio.architect.impl.entrypoint.EntryPoint;
import com.liferay.apio.architect.impl.message.json.EntryPointMessageMapper;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.impl.request.RequestInfo;
import com.liferay.apio.architect.impl.url.ApplicationURL;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Writes the API entrypoint.
 *
 * @author Alejandro Hernández
 * @author Zoltán Takács
 * @review
 */
public class EntryPointWriter {

	/**
	 * Writes the handled {@link EntryPoint} to a string.
	 *
	 * @return the representation of the {@code EntryPoint}
	 */
	public String write() {
		ApplicationURL applicationURL = _requestInfo.getApplicationURL();

		_entryPointMessageMapper.mapSelfURL(
			_jsonObjectBuilder, applicationURL.get());

		List<String> resourceNames = _entryPoint.getResourceNames();

		for (String resourceName : resourceNames) {
			JSONObjectBuilder itemJsonObjectBuilder = new JSONObjectBuilder();

			String url = createCollectionURL(applicationURL, resourceName);

			_entryPointMessageMapper.mapItemSelfURL(
				_jsonObjectBuilder, itemJsonObjectBuilder, resourceName, url);

			_getCollectionItemType(resourceName, itemJsonObjectBuilder);

			_entryPointMessageMapper.onFinishItem(
				_jsonObjectBuilder, itemJsonObjectBuilder);
		}

		_entryPointMessageMapper.onFinish(_jsonObjectBuilder, _entryPoint);

		return _jsonObjectBuilder.build();
	}

	/**
	 * Creates {@code BatchResultWriter} instances.
	 */
	public interface Builder {

		/**
		 * Adds information about the entry point being written to the builder.
		 *
		 * @param  entryPoint the entry point being written
		 * @return the updated builder
		 */
		public static EntryPointMessageMapperStep entryPoint(
			EntryPoint entryPoint) {

			return entryPointMessageMapper -> requestInfo -> typeFunction -> ()
				-> new EntryPointWriter(
					entryPoint, entryPointMessageMapper, requestInfo,
					typeFunction);
		}

		public interface BuildStep {

			/**
			 * Constructs and returns a {@code EntryPointWriter} instance with
			 * the information provided to the builder.
			 *
			 * @return the {@code EntryPointWriter} instance
			 */
			public EntryPointWriter build();

		}

		public interface EntryPointMessageMapperStep {

			/**
			 * Adds information to the builder about the {@link
			 * EntryPointMessageMapper}.
			 *
			 * @param  entryPointMessageMapper the {@code
			 *         EntryPointMessageMapper}
			 * @return the updated builder
			 */
			public RequestInfoStep entryPointMessageMapper(
				EntryPointMessageMapper entryPointMessageMapper);

		}

		public interface RequestInfoStep {

			/**
			 * Adds information to the builder about the request.
			 *
			 * @param  requestInfo the information obtained from the request.
			 *         This can be created by using a {@link
			 *         RequestInfo.Builder}
			 * @return the updated builder
			 */
			public TypeFunctionStep requestInfo(RequestInfo requestInfo);

		}

		public interface TypeFunctionStep {

			/**
			 * Adds information to the builder about the type of the Collection
			 *
			 * @param  typeFunction
			 * @return the updated builder
			 */
			public BuildStep typeFunction(
				Function<String, Optional<String>> typeFunction);

		}

	}

	private EntryPointWriter(
		EntryPoint entryPoint, EntryPointMessageMapper entryPointMessageMapper,
		RequestInfo requestInfo,
		Function<String, Optional<String>> typeFunction) {

		_entryPoint = entryPoint;
		_entryPointMessageMapper = entryPointMessageMapper;
		_requestInfo = requestInfo;
		_typeFunction = typeFunction;

		_jsonObjectBuilder = new JSONObjectBuilder();
	}

	private void _getCollectionItemType(
		String resourceName, JSONObjectBuilder itemJsonObjectBuilder) {

		_typeFunction.apply(
			resourceName
		).ifPresent(
			type -> _entryPointMessageMapper.mapSemantics(
				itemJsonObjectBuilder, type)
		);
	}

	private final EntryPoint _entryPoint;
	private final EntryPointMessageMapper _entryPointMessageMapper;
	private final JSONObjectBuilder _jsonObjectBuilder;
	private final RequestInfo _requestInfo;
	private final Function<String, Optional<String>> _typeFunction;

}