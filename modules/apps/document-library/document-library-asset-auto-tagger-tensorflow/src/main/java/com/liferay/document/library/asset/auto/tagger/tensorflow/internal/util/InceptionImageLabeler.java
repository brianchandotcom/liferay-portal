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
 * @author Alejandro Tardín
 */
@Component(service = InceptionImageLabeler.class)
public class InceptionImageLabeler {

	public List<String> label(byte[] imageBytes) {
		float[] labelProbabilities = _getLabelProbabilities(imageBytes);

		return _bestIndexes(labelProbabilities).stream().map(
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
		_initializeNormalizeImageGraph();
	}

	@Deactivate
	protected void deactivate() {
		_normalizeImageGraph.close();
		_labelerGraph.close();
		_normalizeImageOutput = null;
	}

	private List<Integer> _bestIndexes(float[] probabilities) {
		List<Integer> bestIndexes = new ArrayList<>();

		for (int i = 1; i < probabilities.length; ++i) {
			if (probabilities[i] > .1) {
				bestIndexes.add(i);
			}
		}

		return bestIndexes;
	}

	private InputStream _getInputStream(Bundle bundle, String path)
		throws IOException {

		URL url = bundle.getResource(path);

		return url.openStream();
	}

	private float[] _getLabelProbabilities(byte[] imageBytes) {
		try (Session session = new Session(_labelerGraph);
			Tensor<Float> image = _normalizeImage(imageBytes);
			Tensor<Float> result =
				session.runner().feed(
				"input", image
				).fetch(
					"output"
				).run().get(0).expect(
					Float.class
				)) {

			final long[] shape = result.shape();

			if (result.numDimensions() != 2 || shape[0] != 1) {
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

	private void _initializeLabelerGraph(Bundle bundle) throws IOException {
		byte[] graphDef = FileUtil.getBytes(
			_getInputStream(
				bundle, "META-INF/model/tensorflow_inception_graph.pb"));

		_labelerGraph = new Graph();
		_labelerGraph.importGraphDef(graphDef);
	}

	private void _initializeLabels(Bundle bundle) throws IOException {
		_labels = StringUtil.splitLines(
			StringUtil.read(
				_getInputStream(
					bundle,
					"META-INF/model/imagenet_comp_graph_label_strings.txt")));
	}

	private void _initializeNormalizeImageGraph() {
		_normalizeImageGraph = new Graph();
		GraphBuilder builder = new GraphBuilder(_normalizeImageGraph);

		final int height = 224;
		final int width = 224;
		final float mean = 117f;
		final float scale = 1f;

		final Output<String> input = builder.placeholder("input", String.class);
		_normalizeImageOutput = builder.div(
			builder.sub(
				builder.resizeBilinear(
					builder.expandDims(
						builder.cast(builder.decodeJpeg(input, 3), Float.class),
						builder.constant("make_batch", 0)),
					builder.constant("size", new int[] {height, width})),
				builder.constant("mean", mean)),
			builder.constant("scale", scale));
	}

	private Tensor<Float> _normalizeImage(byte[] imageBytes) {
		try (Session session = new Session(_normalizeImageGraph);
			Tensor tensor = Tensor.create(imageBytes, String.class)) {

			return session.runner().feed(
				"input", tensor
			).fetch(
				_normalizeImageOutput.op().name()
			).run().get(0).expect(
				Float.class
			);
		}
	}

	private Graph _labelerGraph;
	private String[] _labels;
	private Graph _normalizeImageGraph;
	private Output<Float> _normalizeImageOutput;

}