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

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = SimilarResultsContributorsHolder.class)
public class SimilarResultsContributorsHolderImpl
	implements SimilarResultsContributorsHolder {

	@Override
	public Stream<SimilarResultsContributor> stream() {
		return _similarResultsContributors.stream();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addSimilarResultsContributor(
		SimilarResultsContributor similarResultsContributor) {

		_similarResultsContributors.add(similarResultsContributor);
	}

	protected void removeSimilarResultsContributor(
		SimilarResultsContributor similarResultsContributor) {

		_similarResultsContributors.remove(similarResultsContributor);
	}

	private final Collection<SimilarResultsContributor>
		_similarResultsContributors = new CopyOnWriteArrayList<>();

}