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

package com.liferay.portal.lpkg.deployer.internal;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.lpkg.deployer.LPKGVerifyException;
import com.liferay.portal.target.platform.indexer.IndexValidator;
import com.liferay.portal.target.platform.indexer.IndexValidatorFactory;
import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.IndexerFactory;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = LPKGIndexValidator.class)
public class LPKGIndexValidator {

	public void setLpkgDeployer(LPKGDeployer lpkgDeployer) {
		_lpkgDeployer = lpkgDeployer;
	}

	public void validate(List<File> lpkgFiles) throws Exception {
		long start = System.currentTimeMillis();

		List<URI> targetPlatformIndexURIs = _getTargetPlatformIndexURIs();
		List<URI> lpkgIndexURIs = _indexLPKGFiles(lpkgFiles);

		IndexValidator indexValidator = _indexValidatorFactory.create(
			targetPlatformIndexURIs);

		try {
			List<String> messages = indexValidator.validate(lpkgIndexURIs);

			if (!messages.isEmpty()) {
				StringBundler sb = new StringBundler((messages.size() * 3) + 1);

				sb.append("LPKG validation failed with {");

				for (String message : messages) {
					sb.append("[");
					sb.append(message);
					sb.append("], ");
				}

				sb.setIndex(sb.index() - 1);

				sb.append("]}");

				throw new LPKGVerifyException(sb.toString());
			}
		}
		finally {
			_cleanUp(targetPlatformIndexURIs);
			_cleanUp(lpkgIndexURIs);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - start;

				_log.info(
					String.format(
						"LPKG validation time %02d:%02ds",
						MILLISECONDS.toMinutes(duration),
						MILLISECONDS.toSeconds(duration % Time.MINUTE)));
			}
		}
	}

	private void _cleanUp(List<URI> uris) throws MalformedURLException {
		for (URI uri : uris) {
			_bytesURLProtocolSupport.removeData(uri.toURL());
		}
	}

	private List<URI> _getTargetPlatformIndexURIs() throws Exception {
		List<File> files = new ArrayList<>();

		Map<Bundle, List<Bundle>> deployedLPKGBundles =
			_lpkgDeployer.getDeployedLPKGBundles();

		for (Bundle bundle : deployedLPKGBundles.keySet()) {
			files.add(new File(bundle.getLocation()));
		}

		List<URI> uris = _indexLPKGFiles(files);

		Indexer indexer = _indexerFactory.createTargetPlatformIndexer();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		indexer.index(unsyncByteArrayOutputStream);

		URL url = _bytesURLProtocolSupport.putData(
			"liferay-target-platform",
			unsyncByteArrayOutputStream.toByteArray());

		uris.add(url.toURI());

		return uris;
	}

	private List<URI> _indexLPKGFiles(List<File> lpkgFiles) throws Exception {
		List<URI> uris = new ArrayList<>();

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try {
			for (File lpkgFile : lpkgFiles) {
				Indexer indexer = _indexerFactory.createLPKGIndexer(lpkgFile);

				indexer.index(unsyncByteArrayOutputStream);

				String name = lpkgFile.getName();

				URL url = _bytesURLProtocolSupport.putData(
					name.substring(0, name.length() - 5),
					unsyncByteArrayOutputStream.toByteArray());

				unsyncByteArrayOutputStream.reset();

				uris.add(url.toURI());
			}
		}
		catch (Exception e) {
			_cleanUp(uris);

			throw e;
		}

		return uris;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGIndexValidator.class);

	@Reference
	private BytesURLProtocolSupport _bytesURLProtocolSupport;

	@Reference
	private IndexerFactory _indexerFactory;

	@Reference
	private IndexValidatorFactory _indexValidatorFactory;

	private LPKGDeployer _lpkgDeployer;

}