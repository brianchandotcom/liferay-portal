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

import com.liferay.portal.kernel.util.ContentTypes;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.OperationBuilder;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

/**
 * See the <a
 * href="https://github.com/tensorflow/tensorflow/blob/master/tensorflow/java/src/main/java/org/tensorflow/examples/LabelImage.java">org.tensorflow.examples.LabelImage</a>
 * class for more information.
 *
 * @author Alejandro Tardín
 */
public class GraphBuilder {

	public GraphBuilder(Graph graph) {
		_graph = graph;
	}

	public <T, U> Output<U> cast(Output<T> output, Class<U> clazz) {
		OperationBuilder operationBuilder = _graph.opBuilder("Cast", "Cast");

		return operationBuilder.addInput(
			output
		).setAttr(
			"DstT", DataType.fromClass(clazz)
		).build(
		).output(
			0
		);
	}

	public Output<Float> constant(String name, float value) {
		return constant(name, value, Float.class);
	}

	public Output<Integer> constant(String name, int value) {
		return constant(name, value, Integer.class);
	}

	public Output<Integer> constant(String name, int[] value) {
		return constant(name, value, Integer.class);
	}

	public <T> Output<T> constant(String name, Object value, Class<T> clazz) {
		try (Tensor<T> tensor = Tensor.create(value, clazz)) {
			OperationBuilder operationBuilder = _graph.opBuilder("Const", name);

			return operationBuilder.setAttr(
				"dtype", DataType.fromClass(clazz)
			).setAttr(
				"value", tensor
			).build(
			).output(
				0
			);
		}
	}

	public Output<UInt8> decodeImage(
		Output<String> contentsOutput, String mimeType, long channels) {

		String decodeOperationName = _getDecodeOperationName(mimeType);

		OperationBuilder operationBuilder = _graph.opBuilder(
			decodeOperationName, decodeOperationName);

		return operationBuilder.addInput(
			contentsOutput
		).setAttr(
			"channels", channels
		).build(
		).output(
			0
		);
	}

	public Output<Float> div(Output<Float> output1, Output<Float> output2) {
		return _binaryOp("Div", output1, output2);
	}

	public <T> Output<T> expandDims(
		Output<T> output, Output<Integer> dimOutput) {

		return _binaryOp3("ExpandDims", output, dimOutput);
	}

	public <T> Output<T> placeholder(String name, Class<T> clazz) {
		OperationBuilder operationBuilder = _graph.opBuilder(
			"Placeholder", name);

		return operationBuilder.setAttr(
			"dtype", DataType.fromClass(clazz)
		).build(
		).output(
			0
		);
	}

	public <T> Output<T> rename(Output<T> output, String name) {
		OperationBuilder operationBuilder = _graph.opBuilder("Identity", name);

		return operationBuilder.addInput(
			output
		).build(
		).output(
			0
		);
	}

	public <T> Output<Float> resizeBilinear(
		Output<T> imagesOutput, Output<Integer> sizeOutput) {

		return _binaryOp3("ResizeBilinear", imagesOutput, sizeOutput);
	}

	public <T> Output<T> sub(Output<T> output1, Output<T> output2) {
		return _binaryOp("Sub", output1, output2);
	}

	private <T> Output<T> _binaryOp(
		String type, Output<T> output1, Output<T> output2) {

		OperationBuilder operationBuilder = _graph.opBuilder(type, type);

		return operationBuilder.addInput(
			output1
		).addInput(
			output2
		).build(
		).output(
			0
		);
	}

	private <T, U, V> Output<T> _binaryOp3(
		String type, Output<U> output1, Output<V> output2) {

		OperationBuilder operationBuilder = _graph.opBuilder(type, type);

		return operationBuilder.addInput(
			output1
		).addInput(
			output2
		).build(
		).output(
			0
		);
	}

	private String _getDecodeOperationName(String mimeType) {
		if (ContentTypes.IMAGE_BMP.equals(mimeType)) {
			return "DecodeBmp";
		}
		else if (ContentTypes.IMAGE_JPEG.equals(mimeType)) {
			return "DecodeJpeg";
		}
		else if (ContentTypes.IMAGE_PNG.equals(mimeType)) {
			return "DecodePng";
		}

		return "DecodeJpeg";
	}

	private final Graph _graph;

}