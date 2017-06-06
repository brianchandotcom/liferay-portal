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

package com.liferay.document.library.internal.capabilities;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class LiferayBulkOperationCapability implements BulkOperationCapability {

	public LiferayBulkOperationCapability(
		DocumentRepository documentRepository,
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter,
		DLFolderServiceAdapter dlFolderServiceAdapter) {

		_documentRepository = documentRepository;
		_dlFileEntryServiceAdapter = dlFileEntryServiceAdapter;
		_dlFolderServiceAdapter = dlFolderServiceAdapter;
	}

	@Override
	public void execute(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		executeOnAllFileEntries(filter, repositoryModelOperation);
		executeOnAllFolders(filter, repositoryModelOperation);
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		execute(null, repositoryModelOperation);
	}

	protected void executeOnAllFileEntries(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryServiceAdapter.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new RepositoryModelAddCriteriaMethod(filter));
		actionableDynamicQuery.setPerformActionMethod(
			new FileEntryPerformActionMethod(repositoryModelOperation));

		actionableDynamicQuery.performActions();
	}

	protected void executeOnAllFolders(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFolderServiceAdapter.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new RepositoryModelAddCriteriaMethod(filter));
		actionableDynamicQuery.setPerformActionMethod(
			new FolderPerformActionMethod(repositoryModelOperation));

		actionableDynamicQuery.performActions();
	}

	private static final Map<Class<? extends Field<?>>, String> _fieldNames =
		new HashMap<>();

	static {
		_fieldNames.put(Field.CreateDate.class, "createDate");
	}

	private final DLFileEntryServiceAdapter _dlFileEntryServiceAdapter;
	private final DLFolderServiceAdapter _dlFolderServiceAdapter;
	private final DocumentRepository _documentRepository;

	private static class FileEntryPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod<DLFileEntry> {

		public FileEntryPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(DLFileEntry dlFileEntry)
			throws PortalException {

			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			fileEntry.execute(_repositoryModelOperation);
		}

		private RepositoryModelOperation _repositoryModelOperation;

	}

	private static class FolderPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod<DLFolder> {

		public FolderPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(DLFolder dlFolder) throws PortalException {
			if (dlFolder.isMountPoint()) {
				return;
			}

			Folder folder = new LiferayFolder(dlFolder);

			folder.execute(_repositoryModelOperation);
		}

		private final RepositoryModelOperation _repositoryModelOperation;

	}

	private class RepositoryModelAddCriteriaMethod
		implements ActionableDynamicQuery.AddCriteriaMethod {

		public RepositoryModelAddCriteriaMethod(Filter<?> filter) {
			_filter = filter;
		}

		@Override
		public void addCriteria(DynamicQuery dynamicQuery) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"repositoryId", _documentRepository.getRepositoryId()));

			if (_filter != null) {
				addFilterCriteria(dynamicQuery);
			}
		}

		protected void addFilterCriteria(DynamicQuery dynamicQuery) {
			Class<? extends Field<?>> field = _filter.getField();

			String fieldName = _fieldNames.get(field);

			if (fieldName == null) {
				throw new UnsupportedOperationException(
					"Unsupported field " + field.getName());
			}

			Operator operator = _filter.getOperator();

			Object value = _filter.getValue();

			if (operator == Operator.EQ) {
				dynamicQuery.add(RestrictionsFactoryUtil.eq(fieldName, value));
			}
			else if (operator == Operator.LE) {
				dynamicQuery.add(RestrictionsFactoryUtil.le(fieldName, value));
			}
			else if (operator == Operator.LT) {
				dynamicQuery.add(RestrictionsFactoryUtil.lt(fieldName, value));
			}
			else if (operator == Operator.GE) {
				dynamicQuery.add(RestrictionsFactoryUtil.ge(fieldName, value));
			}
			else if (operator == Operator.GT) {
				dynamicQuery.add(RestrictionsFactoryUtil.gt(fieldName, value));
			}
		}

		private final Filter<?> _filter;

	}

}