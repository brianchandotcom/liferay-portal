/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

/**
 * See the <a
 * href="https://github.com/tensorflow/tensorflow/blob/master/tensorflow/java/src/main/java/org/tensorflow/examples/LabelImage.java">org.tensorflow.examples.LabelImage</a>
 * class for more information.
 *
 * @author Shuyang Zhou
 * @author Alejandro Tardín
 */
public class InceptionImageLabelerUtil {

	public static float[] getLabelProbabilities(
		byte[] imageBytes, String mimeType) {

		try (Tensor<Float> imageTensor = _normalizeImage(imageBytes, mimeType);
			Tensor<Float> resultTensor = _getOutputTensor(
				_imageLabelerGraph, imageTensor)) {

			long[] shape = resultTensor.shape();

			if ((resultTensor.numDimensions() != 2) || (shape[0] != 1)) {
				throw new RuntimeException(
					String.format(
						"Expected model to produce a [1 N] shaped tensor " +
							"where N is the number of labels, instead it " +
								"produced one with shape %s",
						Arrays.toString(shape)));
			}

			int numberOfLabels = (int)shape[1];

			return resultTensor.copyTo(new float[1][numberOfLabels])[0];
		}
	}

	public static void initializeModel(byte[] graphBytes) {
		if (_imageLabelerGraph != null) {
			return;
		}

		try {
			_imageLabelerGraph = new Graph();

			_imageLabelerGraph.importGraphDef(graphBytes);

			_imageNormalizerGraph = _buildGraph(
				(graphBuilder, input) -> graphBuilder.div(
					graphBuilder.sub(
						graphBuilder.resizeBilinear(
							graphBuilder.expandDims(
								input, graphBuilder.constant("make_batch", 0)),
							graphBuilder.constant(
								"size", new int[] {224, 224})),
						graphBuilder.constant("mean", 117F)),
					graphBuilder.constant("scale", 1F)),
				Float.class);
		}
		catch (Exception exception) {
			throw exception;
		}
	}

	private static <I, O> Graph _buildGraph(
		BiFunction<GraphBuilder, Output<I>, Output<O>> buildGraph,
		Class<I> inputClass) {

		Graph graph = new Graph();

		GraphBuilder graphBuilder = new GraphBuilder(graph);

		graphBuilder.rename(
			buildGraph.apply(
				graphBuilder, graphBuilder.placeholder("input", inputClass)),
			"output");

		return graph;
	}

	private static Tensor<Float> _decodeImage(
		byte[] imageBytes, String mimeType) {

		Graph imageDecoderGraph = _buildGraph(
			(graphBuilder, input) -> graphBuilder.cast(
				graphBuilder.decodeImage(input, mimeType, 3), Float.class),
			String.class);

		return _getOutputTensor(
			imageDecoderGraph, Tensor.create(imageBytes, String.class));
	}

	private static <I, O> Tensor<O> _getOutputTensor(
		Graph graph, Tensor<I> inputTensor) {

		try (Session session = new Session(graph)) {
			Session.Runner runner = session.runner();

			runner = runner.feed("input", inputTensor);
			runner = runner.fetch("output");

			List<Tensor<?>> tensors = runner.run();

			return (Tensor<O>)tensors.get(0);
		}
	}

	private static Tensor<Float> _normalizeImage(
		byte[] imageBytes, String mimeType) {

		try (Tensor<Float> tensor = _decodeImage(imageBytes, mimeType)) {
			return _getOutputTensor(_imageNormalizerGraph, tensor);
		}
	}

	private static Graph _imageLabelerGraph;
	private static Graph _imageNormalizerGraph;

}