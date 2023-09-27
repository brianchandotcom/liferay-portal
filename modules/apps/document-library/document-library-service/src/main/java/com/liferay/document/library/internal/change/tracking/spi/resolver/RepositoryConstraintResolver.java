/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = ConstraintResolver.class)
public class RepositoryConstraintResolver
	implements ConstraintResolver<Repository> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-repository";
	}

	@Override
	public Class<Repository> getModelClass() {
		return Repository.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		if (_resolved) {
			return "duplicate-repository-was-removed";
		}

		return "duplicate-repository-conflict-was-not-automatically-resolved";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, RepositoryConstraintResolver.class);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "name", "portletId"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<Repository> constraintResolverContext)
		throws PortalException {

		Repository sourceRepository =
			constraintResolverContext.getSourceCTModel();
		Repository targetRepository =
			constraintResolverContext.getTargetCTModel();

		if (StringUtil.equals(
				sourceRepository.getName(),
				TempFileEntryUtil.class.getName()) &&
			StringUtil.equals(
				targetRepository.getName(),
				TempFileEntryUtil.class.getName())) {

			_repositoryLocalService.deleteRepository(sourceRepository);

			_resolved = true;
		}
		else {
			_resolved = false;
		}
	}

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	private boolean _resolved;

}