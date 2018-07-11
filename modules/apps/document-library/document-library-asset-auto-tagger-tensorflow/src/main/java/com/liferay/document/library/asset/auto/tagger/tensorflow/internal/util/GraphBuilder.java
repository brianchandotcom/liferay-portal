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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

/**
 * @author Alejandro Tardín
 */
public class GraphBuilder {

	public GraphBuilder(Graph graph) {
		_graph = graph;
	}

	public <T, U> Output<U> cast(Output<T> value, Class<U> type) {
		return _graph.opBuilder("Cast", "Cast")
			.addInput(value)
			.setAttr("DstT", DataType.fromClass(type))
			.build()
			.output(0);
	}

	public Output<Float> constant(String name, float value) {
		return this.constant(name, value, Float.class);
	}

	public Output<Integer> constant(String name, int value) {
		return this.constant(name, value, Integer.class);
	}

	public Output<Integer> constant(String name, int[] value) {
		return this.constant(name, value, Integer.class);
	}

	public <T> Output<T> constant(String name, Object value, Class<T> type) {
		try (Tensor<T> tensor = Tensor.create(value, type)) {
			return _graph.opBuilder("Const", name)
				.setAttr("dtype", DataType.fromClass(type))
				.setAttr("value", tensor)
				.build()
				.output(0);
		}
	}

	public Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
		return _graph.opBuilder("DecodeJpeg", "DecodeJpeg")
			.addInput(contents)
			.setAttr("channels", channels)
			.build()
			.output(0);
	}

	public Output<Float> div(Output<Float> x, Output<Float> y) {
		return binaryOp("Div", x, y);
	}

	public <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
		return binaryOp3("ExpandDims", input, dim);
	}

	public <T> Output<T> placeholder(String name, Class<T> type) {
		return _graph.opBuilder("Placeholder", name)
			.setAttr("dtype", DataType.fromClass(type))
			.build()
			.output(0);
	}

	public <T> Output<Float> resizeBilinear(
		Output<T> images, Output<Integer> size) {

		return binaryOp3("ResizeBilinear", images, size);
	}

	public <T> Output<T> sub(Output<T> x, Output<T> y) {
		return binaryOp("Sub", x, y);
	}

	private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
		return _graph.opBuilder(
			type, type).addInput(in1).addInput(in2).build().output(0);
	}

	private <T, U, V> Output<T> binaryOp3(
		String type, Output<U> in1, Output<V> in2) {

		return _graph.opBuilder(
			type, type).addInput(in1).addInput(in2).build().output(0);
	}

	private final Graph _graph;

}