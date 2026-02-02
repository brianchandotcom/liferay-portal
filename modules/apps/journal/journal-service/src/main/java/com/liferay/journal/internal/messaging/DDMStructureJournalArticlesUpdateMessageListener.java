/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.messaging;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.internal.constants.DDMDestinationNames;
import com.liferay.journal.internal.dynamic.data.mapping.util.JournalArticleDDMStructureThreadLocal;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Objects;

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
		DDMStructure ddmStructure = (DDMStructure)message.get("ddmStructure");
		DDMStructure originalDDMStructure = (DDMStructure)message.get(
			"originalDDMStructure");

		ActionableDynamicQuery actionableDynamicQuery =
			_journalArticleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmStructureIdProperty = PropertyFactoryUtil.forName(
					"DDMStructureId");

				dynamicQuery.add(
					ddmStructureIdProperty.eq(
						originalDDMStructure.getStructureId()));
			});
		actionableDynamicQuery.setCompanyId(
			originalDDMStructure.getCompanyId());
		actionableDynamicQuery.setParallel(true);

		ActionableDynamicQuery.PerformActionMethod<JournalArticle>
			performActionMethod = null;

		if (Objects.equals(
				ddmStructure.getDefinition(),
				originalDDMStructure.getDefinition())) {

			Indexer<JournalArticle> indexer =
				_indexerRegistry.nullSafeGetIndexer(JournalArticle.class);

			performActionMethod = indexer::reindex;
		}
		else {
			performActionMethod = journalArticle -> {
				try (SafeCloseable ctCollectionIdSafeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ddmStructure.getCtCollectionId());
					SafeCloseable journalArticleDDMStructureSafeCloseable =
						JournalArticleDDMStructureThreadLocal.
							setDDMStructureWithSafeCloseable(
								originalDDMStructure)) {

					_ddmFieldLocalService.updateDDMFormValues(
						ddmStructure.getStructureId(), journalArticle.getId(),
						_fieldsToDDMFormValuesConverter.convert(
							ddmStructure,
							_journalConverter.getDDMFields(
								ddmStructure, journalArticle.getContent())));
				}
			};
		}

		actionableDynamicQuery.setPerformActionMethod(performActionMethod);

		actionableDynamicQuery.performActions();
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