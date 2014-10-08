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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class LiferayBulkOperationCapability implements BulkOperationCapability {

	public LiferayBulkOperationCapability(long repositoryId) {
		_repositoryId = repositoryId;
	}

	@Override
	public void execute(
			BulkOperationCapability.Filter<?> filter,
			RepositoryModelOperation repositoryModelOperation)
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
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

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
			DLFolderLocalServiceUtil.getActionableDynamicQuery();

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

	private final long _repositoryId;

	private static class FileEntryPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod {

		public FileEntryPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(Object object) throws PortalException {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			fileEntry.execute(_repositoryModelOperation);
		}

		private RepositoryModelOperation _repositoryModelOperation;

	}

	private static class FolderPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod {

		public FolderPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(Object object) throws PortalException {
			DLFolder dlFolder = (DLFolder)object;

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
				RestrictionsFactoryUtil.eq("repositoryId", _repositoryId));

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