/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.internal.ingest;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.search.experiences.federation.ingestion.Federator;

import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class FederatorImpl implements Federator {

	public FederatorImpl(IngestorsHolder ingestorsHolder) {
		_ingestorsHolder = ingestorsHolder;
	}

	@Override
	public void federate() {
		RuntimeException runtimeException = new RuntimeException();

		Stream<Ingestor> stream = _ingestorsHolder.stream();

		stream.forEach(
			ingestor -> {
				try {
					ingestor.ingest();
				}
				catch (Exception exception) {
					runtimeException.addSuppressed(exception);
				}
			});

		if (ArrayUtil.isNotEmpty(runtimeException.getSuppressed())) {
			throw runtimeException;
		}
	}

	public static class FederatorBuilderImpl implements Federator.Builder {

		public FederatorBuilderImpl(IngestorsHolder ingestorsHolder) {
			_ingestorsHolder = ingestorsHolder;
		}

		@Override
		public Federator build() {
			return new FederatorImpl(_ingestorsHolder);
		}

		private final IngestorsHolder _ingestorsHolder;

	}

	private final IngestorsHolder _ingestorsHolder;

}