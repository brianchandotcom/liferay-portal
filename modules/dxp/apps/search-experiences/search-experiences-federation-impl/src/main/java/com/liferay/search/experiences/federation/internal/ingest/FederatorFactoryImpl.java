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

import com.liferay.search.experiences.federation.ingestion.Federator;
import com.liferay.search.experiences.federation.ingestion.FederatorFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(enabled = false, immediate = true, service = FederatorFactory.class)
public class FederatorFactoryImpl implements FederatorFactory {

	@Override
	public Federator.Builder builder() {
		return new FederatorImpl.FederatorBuilderImpl(ingestorsHolder);
	}

	@Reference
	protected IngestorsHolder ingestorsHolder;

}