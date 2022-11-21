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

package com.liferay.portal.search.elasticsearch7.internal.index.contributor;

import com.liferay.portal.search.spi.model.index.contributor.IndexContributor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adam Brandizzi
 */
@Component(service = IndexContributorsHolder.class)
public class IndexContributorsHolder {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addIndexContributor(IndexContributor indexContributor) {
		for (IndexContributorReceiver indexContributorReceiver :
				_indexContributorReceivers) {

			indexContributorReceiver.addIndexContributor(indexContributor);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addIndexContributorReceiver(
		IndexContributorReceiver indexContributorReceiver) {

		_indexContributorReceivers.add(indexContributorReceiver);

		for (IndexContributor indexContributor : _indexContributors) {
			indexContributorReceiver.addIndexContributor(indexContributor);
		}
	}

	protected void removeIndexContributor(IndexContributor indexContributor) {
		_indexContributors.remove(indexContributor);

		for (IndexContributorReceiver indexContributorReceiver :
				_indexContributorReceivers) {

			indexContributorReceiver.removeIndexContributor(indexContributor);
		}
	}

	protected void removeIndexContributorReceiver(
		IndexContributorReceiver indexContributorReceiver) {

		_indexContributorReceivers.remove(indexContributorReceiver);
	}

	private final List<IndexContributorReceiver> _indexContributorReceivers =
		new CopyOnWriteArrayList<>();
	private final List<IndexContributor> _indexContributors =
		new CopyOnWriteArrayList<>();

}