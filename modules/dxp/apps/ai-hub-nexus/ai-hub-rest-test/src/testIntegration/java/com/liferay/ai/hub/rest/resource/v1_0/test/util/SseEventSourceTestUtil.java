/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test.util;

import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.PropsValues;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

/**
 * @author Feliphe Marinho
 */
public class SseEventSourceTestUtil {

	public static String open(
			List<CountDownLatch> countDownLatches, List<String> lines,
			String uri)
		throws Exception {

		String credentials =
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

		return open(
			"Basic " + Base64.encode(credentials.getBytes()), countDownLatches,
			lines, uri);
	}

	public static String open(
			String authorization, List<CountDownLatch> countDownLatches,
			List<String> lines, String uri)
		throws Exception {

		CountDownLatch openConnectionCountDownLatch = new CountDownLatch(2);

		HttpClient httpClient = HttpClient.newBuilder(
		).connectTimeout(
			Duration.ofSeconds(5)
		).build();

		CompletableFuture<HttpResponse<InputStream>> completableFuture =
			httpClient.sendAsync(
				HttpRequest.newBuilder(
				).header(
					"Accept", "text/event-stream"
				).header(
					"Authorization", authorization
				).uri(
					URI.create("http://localhost:8080/o/ai-hub/v1.0/" + uri)
				).GET(
				).build(),
				HttpResponse.BodyHandlers.ofInputStream());

		completableFuture.thenAccept(
			response -> {
				try (InputStream inputStream = response.body();
					BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream))) {

					String line = "";

					while ((line = bufferedReader.readLine()) != null) {
						if (line.isEmpty()) {
							continue;
						}

						openConnectionCountDownLatch.countDown();

						for (CountDownLatch countDownLatch : countDownLatches) {
							countDownLatch.countDown();
						}

						lines.add(line);
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});

		Assert.assertTrue(
			openConnectionCountDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 2, lines.size());
		Assert.assertEquals("event: Subscribe", lines.get(0));

		Set<String> sseEventSinksKeys = SseUtil.getSSEEventSinksKeys();

		Assert.assertNotNull(sseEventSinksKeys);

		Assert.assertEquals(
			sseEventSinksKeys.toString(), 1, sseEventSinksKeys.size());

		Iterator<String> iterator = sseEventSinksKeys.iterator();

		String sseEventSinkKey = iterator.next();

		Assert.assertEquals("data: " + sseEventSinkKey, lines.get(1));

		return sseEventSinkKey;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SseEventSourceTestUtil.class);

}