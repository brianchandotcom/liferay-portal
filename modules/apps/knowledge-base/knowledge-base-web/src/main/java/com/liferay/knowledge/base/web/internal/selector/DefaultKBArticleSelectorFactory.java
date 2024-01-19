/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.selector;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = KBArticleSelectorFactory.class)
public class DefaultKBArticleSelectorFactory
	implements KBArticleSelectorFactory {

	@Override
	public KBArticleSelector getKBArticleSelector(long classNameId)
		throws PortalException {

		ClassName className = _classNameLocalService.getClassName(classNameId);

		return _kbArticleSelectors.get(className.getClassName());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_kbArticleSelectors.put(
			KBArticle.class.getName(),
			new KBArticleKBArticleSelector(_kbArticleService));
		_kbArticleSelectors.put(
			KBFolder.class.getName(),
			new KBFolderKBArticleSelector(
				_kbArticleService, _kbFolderService,
				_kbFolderLocalService.createKBFolder(
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID)));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private final Map<String, KBArticleSelector> _kbArticleSelectors =
		new HashMap<>();

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderLocalService _kbFolderLocalService;

	@Reference
	private KBFolderService _kbFolderService;

}