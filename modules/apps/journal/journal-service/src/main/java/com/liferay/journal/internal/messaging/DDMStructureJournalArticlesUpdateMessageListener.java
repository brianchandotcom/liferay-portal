/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.messaging;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.internal.constants.DDMDestinationNames;
import com.liferay.journal.internal.dynamic.data.mapping.helper.JournalDDMStructureHelper;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "destination.name=" + DDMDestinationNames.DDM_STRUCTURE_JOURNAL_ARTICLES_UPDATE,
	service = MessageListener.class
)
public class DDMStructureJournalArticlesUpdateMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				DDMDestinationNames.DDM_STRUCTURE_JOURNAL_ARTICLES_UPDATE);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		JournalDDMStructureHelper journalDDMStructureHelper =
			new JournalDDMStructureHelper(
				_ddmFieldLocalService, _fieldsToDDMFormValuesConverter,
				_indexerRegistry, _journalArticleLocalService,
				_journalConverter);

		journalDDMStructureHelper.updateDDMStructureJournalArticles(
			(DDMStructure)message.get("originalDDMStructure"),
			(DDMStructure)message.get("ddmStructure"));
	}

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalConverter _journalConverter;

	private ServiceRegistration<Destination> _serviceRegistration;

}