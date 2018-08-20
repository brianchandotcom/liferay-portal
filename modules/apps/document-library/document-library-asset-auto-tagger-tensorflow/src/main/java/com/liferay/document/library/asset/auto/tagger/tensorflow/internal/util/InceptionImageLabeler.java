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

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.petra.process.GetLabelProbabilitiesProcessCallable;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * See the <a
 * href="https://github.com/tensorflow/tensorflow/blob/master/tensorflow/java/src/main/java/org/tensorflow/examples/LabelImage.java">org.tensorflow.examples.LabelImage</a>
 * class for more information.
 *
 * @author Alejandro Tardín
 */
@Component(service = InceptionImageLabeler.class)
public class InceptionImageLabeler {

	public List<String> label(
		byte[] imageBytes, String mimeType, float confidenceThreshold) {

		float[] labelProbabilities = _tensorflowProcess.run(
			new GetLabelProbabilitiesProcessCallable(imageBytes, mimeType));

		Stream<Integer> stream = _getBestIndexesStream(
			labelProbabilities, confidenceThreshold);

		return stream.map(
			i -> _labels[i]
		).collect(
			Collectors.toList()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		Bundle bundle = bundleContext.getBundle();

		_initializeLabels(bundle);
	}

	@Deactivate
	protected void deactivate() {
		_tensorflowProcess.stop();
	}

	private Stream<Integer> _getBestIndexesStream(
		float[] probabilities, float confidenceThreshold) {

		List<Integer> bestIndexes = new ArrayList<>();

		for (int i = 0; i < probabilities.length; i++) {
			if ((probabilities[i] >= confidenceThreshold) &&
				(i < _labels.length)) {

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

	private void _initializeLabels(Bundle bundle) throws IOException {
		_labels = StringUtil.splitLines(
			StringUtil.read(
				_getInputStream(
					bundle,
					"META-INF/tensorflow" +
						"/imagenet_comp_graph_label_strings.txt")));
	}

	private String[] _labels;

	@Reference
	private TensorflowProcess _tensorflowProcess;

}