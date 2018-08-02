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

package com.liferay.apio.architect.impl.message.json;

import com.liferay.apio.architect.impl.entrypoint.EntryPoint;

/**
 * Maps {@link EntryPoint} data to its representation in a JSON object.
 * Instances of this interface work like events. The {@code
 * javax.ws.rs.ext.MessageBodyWriter} of the {@code EntryPoint} calls the {@code
 * EntryPointMessageMapper} methods. In each method, developers should only map
 * the provided part of the resource to its representation in a JSON object. To
 * enable this, each method receives a {@link JSONObjectBuilder}.
 *
 * <p>
 * The method {@link #onFinish} is called when the writer finishes writing the
 * entry point. Otherwise, the entry point message mapper's methods aren't
 * called in a particular order.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public interface EntryPointMessageMapper extends MessageMapper<EntryPoint> {

	/**
	 * Maps a resource URL to its JSON object representation.
	 *
	 * @param entryPointJSONObjectBuilder the JSON object builder for the entry
	 *        point
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param url the resource's URL
	 */
	public default void mapItemSelfURL(
		JSONObjectBuilder entryPointJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String resourceName,
		String url) {
	}

	/**
	 * Maps the entry point's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param url the entry point's URL
	 */
	public default void mapSelfURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the semantics to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param semantics Semantics of each member provided by the collection
	 */
	public default void mapSemantics(
		JSONObjectBuilder jsonObjectBuilder, String semantics) {
	}

	/**
	 * Finishes the item. This is the final entry point message mapper method
	 * the writer calls for the item.
	 *
	 * @param entryPointJSONObjectBuilder the JSON object builder for the entry
	 *        point
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 */
	public default void onFinishItem(
		JSONObjectBuilder entryPointJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder) {
	}

}