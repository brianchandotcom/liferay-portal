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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

/**
 * https://github.com/tensorflow/tensorflow/blob/master/tensorflow/java/src/main/java/org/tensorflow/examples/LabelImage.java
 *
 * @author Alejandro Tardín
 */
@Component(service = InceptionImageLabeler.class)
public class InceptionImageLabeler {

	public List<String> label(byte[] imageBytes, float confidenceThreshold) {
		float[] labelProbabilities = _getLabelProbabilities(imageBytes);

		Stream<Integer> indexesStream = _bestIndexes(
			labelProbabilities, confidenceThreshold);

		return indexesStream.map(
			i -> _labels[i]
		).collect(
			Collectors.toList()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		Bundle bundle = bundleContext.getBundle();

		_initializeLabelerGraph(bundle);
		_initializeLabels(bundle);

		_initializeImageNormalizerGraph();
	}

	@Deactivate
	protected void deactivate() {
		_imageNormalizerGraph.close();
		_imageLabelerGraph.close();
	}

	private Stream<Integer> _bestIndexes(
		float[] probabilities, float confidenceThreshold) {

		List<Integer> bestIndexes = new ArrayList<>();

		for (int i = 1; i < probabilities.length; ++i) {
			if (probabilities[i] > confidenceThreshold) {
				bestIndexes.add(i);
			}
		}

		return bestIndexes.stream();
	}

	private InputStream _getInputStream(Bundle bundle, String path)
		throws IOException {

		URL url = bundle.getResource(path);

		return url.openStream();
	}

	private float[] _getLabelProbabilities(byte[] imageBytes) {
		try (Tensor<Float> image = _normalizeImage(imageBytes);
			Tensor<Float> result = _getOutput(_imageLabelerGraph, image)) {

			long[] shape = result.shape();

			if ((result.numDimensions() != 2) || (shape[0] != 1)) {
				throw new RuntimeException(
					String.format(
						"Expected model to produce a [1 N] shaped tensor " +
							"where N is the number of labels, instead it " +
								"produced one with shape %s",
						Arrays.toString(shape)));
			}

			int numberOfLabels = (int)shape[1];

			return result.copyTo(new float[1][numberOfLabels])[0];
		}
	}

	private Tensor<Float> _getOutput(Graph graph, Tensor<Float> input) {
		try (Session session = new Session(graph)) {
			Session.Runner runner = session.runner();

			runner = runner.feed("input", input);
			runner = runner.fetch("output");

			List<Tensor<?>> tensors = runner.run();

			Tensor<?> tensor = tensors.get(0);

			return tensor.expect(Float.class);
		}
	}

	private void _initializeImageNormalizerGraph() {
		_imageNormalizerGraph = new Graph();

		GraphBuilder builder = new GraphBuilder(_imageNormalizerGraph);

		Output<String> input = builder.placeholder("input", String.class);

		builder.rename(
			builder.div(
				builder.sub(
					builder.resizeBilinear(
						builder.expandDims(
							builder.cast(
								builder.decodeJpeg(input, 3), Float.class),
							builder.constant("make_batch", 0)),
						builder.constant("size", new int[] {224, 224})),
					builder.constant("mean", 117F)),
				builder.constant("scale", 1F)),
			"output"
		);
	}

	private void _initializeLabelerGraph(Bundle bundle) throws IOException {
		byte[] graphDef = FileUtil.getBytes(
			_getInputStream(
				bundle, "META-INF/model/tensorflow_inception_graph.pb"));

		_imageLabelerGraph = new Graph();

		_imageLabelerGraph.importGraphDef(graphDef);
	}

	private void _initializeLabels(Bundle bundle) throws IOException {
		_labels = StringUtil.splitLines(
			StringUtil.read(
				_getInputStream(
					bundle,
					"META-INF/model/imagenet_comp_graph_label_strings.txt")));
	}

	private Tensor<Float> _normalizeImage(byte[] imageBytes) {
		try (Tensor tensor = Tensor.create(imageBytes, String.class)) {
			return _getOutput(_imageNormalizerGraph, tensor);
		}
	}

	private Graph _imageLabelerGraph;
	private Graph _imageNormalizerGraph;
	private String[] _labels;

}