/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.search.asset.AssetSubtypeIdentifierBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, service = {})
public class SXPBlueprintModelListenerRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_infoCollectionProviderSXPBlueprintModelListener =
			new SXPBlueprintInfoCollectionProviderSXPBlueprintModelListener(
				_assetHelper, _assetSubtypeIdentifierBuilder,
				_blogsEntryLocalService, bundleContext,
				_calendarBookingLocalService, _classNameLocalService,
				_companyLocalService, _ddmStructureService,
				_dlFileEntryTypeLocalService, _dlAppLocalService, _groupService,
				_journalArticleService, _kbArticleLocalService,
				_objectDefinitionLocalService, _objectEntryLocalService,
				_searcher, _searchRequestBuilderFactory,
				_sxpBlueprintLocalService);

		_infoCollectionProviderSXPBlueprintModelListener.start();

		_serviceRegistration = bundleContext.registerService(
			ModelListener.class,
			_infoCollectionProviderSXPBlueprintModelListener, null);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		if (_infoCollectionProviderSXPBlueprintModelListener != null) {
			_infoCollectionProviderSXPBlueprintModelListener.stop();
		}
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetSubtypeIdentifierBuilder _assetSubtypeIdentifierBuilder;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private GroupService _groupService;

	private InfoCollectionProviderSXPBlueprintModelListener
		_infoCollectionProviderSXPBlueprintModelListener;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private ServiceRegistration<?> _serviceRegistration;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}